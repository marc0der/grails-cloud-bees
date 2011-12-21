import grails.util.Metadata

includeTargets << grailsScript("Init")
includeTargets << new File("${cloudBeesPluginDir}/scripts/_CheckConfig.groovy")
includeTargets << new File("${cloudBeesPluginDir}/scripts/_BeesHelper.groovy")
includeTargets << new File("${cloudBeesPluginDir}/scripts/_BeesCommon.groovy")

USAGE = '''
grails bees-app-restart [appId]
	appId : the application id (defaults to appname)
'''

target(beesAppRestart: "Restarts all deployed instances of an application.") {
	depends(checkConfig, prepareClient)
	
	String appId = buildAppId()
	
	def response
	try {
		response = beesClient.applicationRestart(appId)
		
	} catch (Exception e) {
		dealWith e
	}
	event "StatusFinal", ["Application $appId restarted successfully: $response.restarted"]

}

setDefaultTarget(beesAppRestart)
