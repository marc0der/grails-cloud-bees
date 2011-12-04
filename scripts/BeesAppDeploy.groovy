import com.cloudbees.api.HashWriteProgress

includeTargets << grailsScript("Init")
includeTargets << new File("${cloudBeesPluginDir}/scripts/_CheckConfig.groovy")
includeTargets << new File("${cloudBeesPluginDir}/scripts/_BeesHelper.groovy")
includeTargets << new File("${cloudBeesPluginDir}/scripts/_BeesCommon.groovy")

USAGE = '''
grails bees-app-deploy <appId> <tag> <war>
	appId : the application id (in the form user/appname)
	tag   : the release tag or version
	war   : the path to the warfile (absolute or relative)
'''

target(beesAppDeploy: "Deploy a new version of an application using a WAR archive file.") {
	depends(checkConfig, prepareClient)
	
	String appId = getRequiredArg(0)
	if(!appId) return
	
	String tag = getRequiredArg(1)
	if(!tag) return
	
	String war = getRequiredArg(2)
	if(!war) return
	
	def response
	def progress = new HashWriteProgress()
	try {
		response = beesClient.applicationDeployWar(appId, null, tag, war, null, true, progress)
		
	} catch (Exception e) {
		dealWith e
	}
	
	event "StatusFinal", ["Application uploaded successfully to: $response.url"]
}

setDefaultTarget(beesAppDeploy)
