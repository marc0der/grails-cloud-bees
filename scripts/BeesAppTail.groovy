import net.stax.api.StaxClientException

includeTargets << grailsScript("Init")
includeTargets << new File("${cloudBeesPluginDir}/scripts/_CheckConfig.groovy")
includeTargets << new File("${cloudBeesPluginDir}/scripts/_StaxHelper.groovy")
includeTargets << new File("${cloudBeesPluginDir}/scripts/_BeesCommon.groovy")

USAGE = '''
grails bees-app-tail <appId> LOGNAME
  LOGNAME: server, access or error
'''

target(beesAppTail: "Establishes a persistent connection to the application logs.") {
	depends(checkConfig, prepareClient)
	
	String appId = getRequiredArg(0)
	String log = getRequiredArg(1)
	try {
		response = staxClient.tailLog(appId, log, System.out)
		
	} catch (StaxClientException sce) {
		printSeparator()
		println "Error: $sce.message"
		printSeparator()
		exit(0)
	}
	
}

setDefaultTarget(beesAppTail)
