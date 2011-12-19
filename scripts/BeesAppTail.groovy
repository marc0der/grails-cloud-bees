import grails.util.Metadata

includeTargets << grailsScript("Init")
includeTargets << new File("${cloudBeesPluginDir}/scripts/_CheckConfig.groovy")
includeTargets << new File("${cloudBeesPluginDir}/scripts/_BeesHelper.groovy")
includeTargets << new File("${cloudBeesPluginDir}/scripts/_BeesCommon.groovy")

USAGE = '''
grails bees-app-tail [appId] [LOGNAME] 
    appId : the application id (in the form user/appname)
    LOGNAME : server, access or error (defaults to server)
'''

target(beesAppTail: "Establishes a persistent connection to the application logs.") {
	depends(checkConfig, prepareClient)
	
	if(usage()) return
	
	String log = getOptionalArg(1) ?: 'server'
	
	String appId = buildAppId()
		
	try {
		event "StatusFinal", ["Tailing log $log on $appId"]
		beesClient.tailLog(appId, log, System.out)
		
	} catch (Exception e) {
		dealWith e
	}
	
}

setDefaultTarget(beesAppTail)
