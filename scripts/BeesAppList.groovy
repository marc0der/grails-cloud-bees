import net.stax.api.StaxClientException

includeTargets << grailsScript("Init")
includeTargets << new File("${cloudBeesPluginDir}/scripts/_CheckConfig.groovy")
includeTargets << new File("${cloudBeesPluginDir}/scripts/_StaxHelper.groovy")
includeTargets << new File("${cloudBeesPluginDir}/scripts/_BeesCommon.groovy")

USAGE = '''
grails bees-app-list
'''

target(beesAppList: "Returns the list of applications available to your account.") {
	depends(checkConfig, prepareClient)
	
	def response 
	try {
		response = staxClient.applicationList()
	
	} catch (StaxClientException sce) {
		printSeparator()
		println "Error: $sce.message"
		printSeparator()
		exit(0)
	}
	
	def infos = response.applications
	
	printSeparator()
	infos.each { info ->
		println "Application title : $info.name"
		println "               id : $info.id"
		println "           status : $info.status"
		printSeparator()
	}
}

setDefaultTarget(beesAppList)
