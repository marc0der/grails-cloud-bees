includeTargets << grailsScript("Init")
includeTargets << new File("${cloudBeesPluginDir}/scripts/_CheckConfig.groovy")
includeTargets << new File("${cloudBeesPluginDir}/scripts/_BeesHelper.groovy")
includeTargets << new File("${cloudBeesPluginDir}/scripts/_BeesCommon.groovy")

USAGE = '''
grails bees-app-list
'''

target(beesAppList: "Returns the list of applications available to your account.") {
	depends(checkConfig, prepareClient)
	
	def response
	try{
		response = beesClient.applicationList()
		
	} catch (Exception e){
		dealWith e
	}
	def infos = response.applications
	
	event "StatusFinal", ["Application List:"]

	infos.each { info ->
		event "StatusFinal", ["    $info.title ($info.status)"]
	}

	if(!infos) event "StatusFinal", ["No applications found."]

}

setDefaultTarget(beesAppList)
