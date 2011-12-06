import grails.util.Metadata

includeTargets << grailsScript("Init")
includeTargets << new File("${cloudBeesPluginDir}/scripts/_CheckConfig.groovy")
includeTargets << new File("${cloudBeesPluginDir}/scripts/_BeesHelper.groovy")
includeTargets << new File("${cloudBeesPluginDir}/scripts/_BeesCommon.groovy")

USAGE = '''
grails bees-app-stop [appId]
	appId : the application id (defaults to appname)
'''

target(beesAppStop: "Stops all deployed instances of an application.") {
	depends(checkConfig, prepareClient)
	
	if(usage()) return
	
	String appId = buildAppId()
	
	def response
	try {
		response = beesClient.applicationStop(appId)
		
	} catch (Exception e) {
		dealWith e
	}
	
	event "StatusFinal", ["Application $appId stopped successfully. status: $response.status"]
}

setDefaultTarget(beesAppStop)
