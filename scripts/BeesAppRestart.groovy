includeTargets << grailsScript("Init")
includeTargets << new File("${cloudBeesPluginDir}/scripts/_CheckConfig.groovy")
includeTargets << new File("${cloudBeesPluginDir}/scripts/_BeesHelper.groovy")
includeTargets << new File("${cloudBeesPluginDir}/scripts/_BeesCommon.groovy")

USAGE = '''
grails bees-app-restart <appId>
	appId : the application id (in the form user/appname)
'''

target(beesAppRestart: "Restarts all deployed instances of an application.") {
	depends(checkConfig, prepareClient)
	
	String appId = getRequiredArg()
	if(!appId) return
	
	def response
	try {
		response = beesClient.applicationRestart(appId)
		
	} catch (Exception e) {
		dealWith e
	}
	event "StatusFinal", ["Application restarted successfully: $response.restarted"]

}

setDefaultTarget(beesAppRestart)
