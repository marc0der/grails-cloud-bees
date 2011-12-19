includeTargets << grailsScript("Init")
includeTargets << new File("${cloudBeesPluginDir}/scripts/_CheckConfig.groovy")
includeTargets << new File("${cloudBeesPluginDir}/scripts/_BeesHelper.groovy")
includeTargets << new File("${cloudBeesPluginDir}/scripts/_BeesCommon.groovy")

USAGE = '''
grails bees-app-checksums [appId]
	appId : the application id (defaults to appname)
'''

target(beesAppChecksums: "Returns the checksums for an application.") {
	depends(checkConfig, prepareClient)
	
	if(usage()) return
	
	String appId = buildAppId()
	
	def response
	try{
		response = beesClient.applicationCheckSums(appId)
		
	} catch (Exception e){
		dealWith e
	}
		
	event "StatusFinal", ["Application CheckSums for ${appId}:"]

	def checksums = response.checkSums
	checksums.each { key, value ->
		event "StatusFinal", ["    ${value.toString()?.padLeft(10)} : $key"]
	}
	
	if(!checksums) event("StatusFinal", ["    No checksums found. Is $appId a valid application?"])
}

setDefaultTarget(beesAppChecksums)
