includeTargets << grailsScript("Init")
includeTargets << new File("${cloudBeesPluginDir}/scripts/_CheckConfig.groovy")
includeTargets << new File("${cloudBeesPluginDir}/scripts/_BeesHelper.groovy")
includeTargets << new File("${cloudBeesPluginDir}/scripts/_BeesCommon.groovy")

USAGE = '''
grails bees-app-delete [appId]
	appId : the application id (defaults to appname)
'''

target(beesAppDelete: "Delete an application.") {
	depends(checkConfig, prepareClient)
	
	String appId = buildAppId()
	
	def response
	try {
		response = beesClient.applicationDelete(appId)
		
	} catch (Exception e) {
		dealWith e
	}
	
	event "StatusFinal", ["Application $appId deleted successfully: $response.deleted"]
	
}

setDefaultTarget(beesAppDelete)
