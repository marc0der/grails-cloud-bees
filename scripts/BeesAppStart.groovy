includeTargets << grailsScript("Init")
includeTargets << new File("${cloudBeesPluginDir}/scripts/_CheckConfig.groovy")
includeTargets << new File("${cloudBeesPluginDir}/scripts/_BeesHelper.groovy")
includeTargets << new File("${cloudBeesPluginDir}/scripts/_BeesCommon.groovy")

USAGE = '''
grails bees-app-start <appId>
	appId : the application id (in the form user/appname)
'''

target(beesAppStart: "Starts all deployed instances of an application.") {
	depends(checkConfig, prepareClient)
	
	String appId = getRequiredArg()
	def response
	try {
		response = beesClient.applicationStart(appId)
		
	} catch (Exception e) {
		dealWith e
	}
	
	printSeparator()
	println "Application started successfully. status: $response.status"
	printSeparator()
}

setDefaultTarget(beesAppStart)
