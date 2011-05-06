includeTargets << grailsScript("Init")
includeTargets << new File("${cloudBeesPluginDir}/scripts/_CheckConfig.groovy")
includeTargets << new File("${cloudBeesPluginDir}/scripts/_BeesHelper.groovy")
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
		beesClient.tailLog(appId, log, System.out)
		
	} catch (Exception e) {
		dealWith e
	}
	
}

setDefaultTarget(beesAppTail)
