includeTargets << grailsScript("Init")
includeTargets << new File("${cloudBeesPluginDir}/scripts/_CheckConfig.groovy")
includeTargets << new File("${cloudBeesPluginDir}/scripts/_BeesHelper.groovy")
includeTargets << new File("${cloudBeesPluginDir}/scripts/_BeesCommon.groovy")

USAGE = '''
grails bees-app-delete <appId>
'''

target(beesAppDelete: "Delete an application.") {
	depends(checkConfig, prepareClient)
	
	String appId = getRequiredArg(0)
	def response
	try {
		response = beesClient.applicationDelete(appId)
		
	} catch (Exception e) {
		dealWith e
	}
	
	printSeparator()
	println "Application $appId deleted successfully: $response.deleted"
	printSeparator()
}

setDefaultTarget(beesAppDelete)
