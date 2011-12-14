includeTargets << grailsScript("Init")
includeTargets << new File("${cloudBeesPluginDir}/scripts/_CheckConfig.groovy")
includeTargets << new File("${cloudBeesPluginDir}/scripts/_BeesHelper.groovy")
includeTargets << new File("${cloudBeesPluginDir}/scripts/_BeesCommon.groovy")

USAGE = '''
grails bees-app-info [appId]
    appId : application id (defaults to application name)
'''

target(beesAppInfo: "Returns the basic information about an application.") {
	depends(checkConfig, prepareClient)
	
	String appId = buildAppId()
	
	def info
	try {
		info = beesClient.applicationInfo(appId)
		
	} catch (Exception e) {
		dealWith e
	}
	
	event "StatusFinal", ["Application title : $info.title"]
	event "StatusFinal", ["          created : $info.created"]
	event "StatusFinal", ["             urls : $info.urls"]
	event "StatusFinal", ["           status : $info.status"]
}

setDefaultTarget(beesAppInfo)
