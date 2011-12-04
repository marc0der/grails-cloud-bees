includeTargets << grailsScript("Init")
includeTargets << new File("${cloudBeesPluginDir}/scripts/_CheckConfig.groovy")
includeTargets << new File("${cloudBeesPluginDir}/scripts/_BeesHelper.groovy")
includeTargets << new File("${cloudBeesPluginDir}/scripts/_BeesCommon.groovy")

USAGE = '''
grails bees-app-tail <appId> LOGNAME
	appId   : the application id (in the form user/appname)
    LOGNAME : server, access or error
'''

target(beesAppTail: "Establishes a persistent connection to the application logs.") {
	depends(checkConfig, prepareClient)
	
	String appId = getRequiredArg(0)
	if(!appId) return
	
	String log = getRequiredArg(1)
	if(!log) return
	
	try {
		event "StatusFinal", ["Tailing log $log on $appId"]
		beesClient.tailLog(appId, log, System.out)
		
	} catch (Exception e) {
		dealWith e
	}
	
}

setDefaultTarget(beesAppTail)
