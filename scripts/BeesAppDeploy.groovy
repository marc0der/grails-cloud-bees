import grails.util.Metadata
import com.cloudbees.api.HashWriteProgress

includeTargets << grailsScript("Init")
includeTargets << new File("${cloudBeesPluginDir}/scripts/_CheckConfig.groovy")
includeTargets << new File("${cloudBeesPluginDir}/scripts/_BeesHelper.groovy")
includeTargets << new File("${cloudBeesPluginDir}/scripts/_BeesCommon.groovy")

USAGE = '''
grails bees-app-deploy [appId] [tag]
        appId : application id (defaults to application name)
        tag   : release tag (defaults to application version)
'''

target(beesAppDeploy: "Deploy a new version of an application using a WAR archive file.") {
	depends(checkConfig, prepareClient)
	
	if(usage()) return
	
	String appId = buildAppId()
	String tag = buildAppTag()
	
	String configAppName = Metadata.current.'app.name'
	String configAppVersion = Metadata.current.'app.version'
	String warName = "${grailsSettings.baseDir}/target/${configAppName}-${configAppVersion}.war"
	File warFile = new File(warName)
	if(! (warFile.exists() && warFile.file) ){
		event "StatusError", ["WAR file is not build! First issue the grails war command before deploying."]
		return
	}
	
	def response
	def progress = new HashWriteProgress()
	try {
		event "StatusFinal", ["Deploying $appId tagged at version $tag"]
		response = beesClient.applicationDeployWar(appId, null, tag, warName, null, true, progress)
		
	} catch (Exception e) {
		dealWith e
	}
	
	event "StatusFinal", ["Application uploaded successfully to: $response.url"]
}

setDefaultTarget(beesAppDeploy)