import net.stax.api.StaxClientException

includeTargets << grailsScript("Init")
includeTargets << new File("${cloudBeesPluginDir}/scripts/_CheckConfig.groovy")
includeTargets << new File("${cloudBeesPluginDir}/scripts/_StaxHelper.groovy")
includeTargets << new File("${cloudBeesPluginDir}/scripts/_BeesCommon.groovy")

USAGE = '''
grails bees-app-info <appId>
'''

target(beesAppInfo: "Returns the basic information about an application.") {
	depends(checkConfig, prepareClient)
	
	String appId = getRequiredArg()
	def info
	try {
		info = staxClient.applicationInfo(appId)
		
	} catch (StaxClientException sce) {
		printSeparator()
		println "Error: $sce.message"
		printSeparator()
		exit(0)
	}
	
	printSeparator()
	println "Application name : $info.title"
	println "              id : $info.id"
	println "         created : $info.created"
	println "            urls : $info.urls"
	println "          status : $info.status"
	printSeparator()
}

setDefaultTarget(beesAppInfo)
