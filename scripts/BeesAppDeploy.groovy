import grails.util.Metadata

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
	
	def parameters = [:]
	
	def permgenSize = grailsSettings.config.cloudbees?.permgensize
	if(permgenSize){
		parameters.put "jvmPermSize", permgenSize
		event "StatusFinal", ["Application PermGen size set to ${parameters.jvmPermSize}MB"]
	}
	
	def response
	def progress = classLoader.loadClass("org.grails.plugins.cloudbees.HashWriteProgress", true).newInstance()
	try {
		event "StatusFinal", ["Deploying $appId tagged at version $tag"]
		response = beesClient.applicationDeployArchive(appId, null, tag, warName, null, "war", true, parameters, progress)
		
	} catch (Exception e) {
		dealWith e
	}
	
	event "StatusFinal", ["Application uploaded successfully to: $response.url"]
	
}

setDefaultTarget(beesAppDeploy)
