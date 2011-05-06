includeTargets << grailsScript("Init")
includeTargets << new File("${cloudBeesPluginDir}/scripts/_CheckConfig.groovy")
includeTargets << new File("${cloudBeesPluginDir}/scripts/_BeesHelper.groovy")
includeTargets << new File("${cloudBeesPluginDir}/scripts/_BeesCommon.groovy")

USAGE = '''
grails bees-app-info <appId>
	appId : the application id (in the form user/appname)
'''

target(beesAppInfo: "Returns the basic information about an application.") {
	depends(checkConfig, prepareClient)
	
	String appId = getRequiredArg()
	def info
	try {
		info = beesClient.applicationInfo(appId)
		
	} catch (Exception e) {
		dealWith e
	}
	
	printSeparator()
	println "Application title : $info.title"
	println "          created : $info.created"
	println "             urls : $info.urls"
	println "           status : $info.status"
	printSeparator()
}

setDefaultTarget(beesAppInfo)
