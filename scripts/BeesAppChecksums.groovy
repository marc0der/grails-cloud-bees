includeTargets << grailsScript("Init")
includeTargets << new File("${cloudBeesPluginDir}/scripts/_CheckConfig.groovy")
includeTargets << new File("${cloudBeesPluginDir}/scripts/_BeesHelper.groovy")
includeTargets << new File("${cloudBeesPluginDir}/scripts/_BeesCommon.groovy")

USAGE = '''
grails bees-app-checksums <appId>
'''

target(beesAppChecksums: "Returns the checksums for an application.") {
	depends(checkConfig, prepareClient)
	
	String appId = getRequiredArg()
	def response
	try{
		response = beesClient.applicationCheckSums(appId)
		
	} catch (Exception e){
		dealWith e
	}
		
	printSeparator()
	println "Application CheckSums:"
	printSeparator()
	def checksums = response.checkSums
	checksums.each { key, value ->
		println "  $key : $value"
	}
	
	if(!checksums) println "No checksums found. Is $appId a valid application?"
	
	printSeparator()
}

setDefaultTarget(beesAppChecksums)
