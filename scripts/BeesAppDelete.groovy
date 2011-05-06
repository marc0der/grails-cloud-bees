import net.stax.api.StaxClientException

includeTargets << grailsScript("Init")
includeTargets << new File("${cloudBeesPluginDir}/scripts/_CheckConfig.groovy")
includeTargets << new File("${cloudBeesPluginDir}/scripts/_StaxHelper.groovy")
includeTargets << new File("${cloudBeesPluginDir}/scripts/_BeesCommon.groovy")

USAGE = '''
grails bees-app-delete <appId>
'''

target(beesAppDelete: "Delete an application.") {
	depends(checkConfig, prepareClient)
	
	String appId = getRequiredArg(0)
	
	def response
	try {
		response = staxClient.applicationDelete(appId)
		
	} catch (StaxClientException sce) {
		printSeparator()
		println "Application not deleted: $sce.message"
		printSeparator()
		exit(0)
	}
	
	printSeparator()
	println "Application $appId deleted successfully: $response.deleted"
	printSeparator()
}

setDefaultTarget(beesAppDelete)
