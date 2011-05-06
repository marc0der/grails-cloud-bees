import net.stax.api.StaxClientException

includeTargets << grailsScript("Init")
includeTargets << new File("${cloudBeesPluginDir}/scripts/_CheckConfig.groovy")
includeTargets << new File("${cloudBeesPluginDir}/scripts/_StaxHelper.groovy")
includeTargets << new File("${cloudBeesPluginDir}/scripts/_BeesCommon.groovy")

USAGE = '''
grails bees-app-checksums <appId>
'''

target(beesAppChecksums: "Returns the checksums for an application.") {
	depends(checkConfig, prepareClient)
	
	String appId = getRequiredArg()
	def response
	try {
		response = staxClient.applicationCheckSums(appId)
		
	} catch (StaxClientException sce) {
		printSeparator()
		println "Error: $sce.message"
		printSeparator()
		exit(0)
	}
	
	printSeparator()
	println "Application CheckSums:"
	printSeparator()
	def checksums = response.checkSums
	checksums.each { key, value ->
		println "  $key : $value"
	}
	printSeparator()
}

setDefaultTarget(beesAppChecksums)
