import net.stax.api.StaxClientException

includeTargets << grailsScript("Init")
includeTargets << new File("${cloudBeesPluginDir}/scripts/_CheckConfig.groovy")
includeTargets << new File("${cloudBeesPluginDir}/scripts/_StaxHelper.groovy")
includeTargets << new File("${cloudBeesPluginDir}/scripts/_BeesCommon.groovy")

USAGE = '''
grails bees-app-restart <appId>
'''

target(beesAppRestart: "Restarts all deployed instances of an application.") {
	depends(checkConfig, prepareClient)
	
	String appId = getRequiredArg()
	def response
	try {
		response = staxClient.applicationRestart(appId)
		
	} catch (StaxClientException sce) {
		printSeparator()
		println "Error: $sce.message"
		printSeparator()
		exit(0)
	}
	
	printSeparator()
	println "Application restarted successfully: $response.restarted"
	printSeparator()
}

setDefaultTarget(beesAppRestart)
