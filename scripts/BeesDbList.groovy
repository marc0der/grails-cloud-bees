includeTargets << grailsScript("Init")
includeTargets << new File("${cloudBeesPluginDir}/scripts/_CheckConfig.groovy")
includeTargets << new File("${cloudBeesPluginDir}/scripts/_BeesHelper.groovy")
includeTargets << new File("${cloudBeesPluginDir}/scripts/_BeesCommon.groovy")

USAGE = '''
grails bees-db-list
'''

target(beesDbList: "Returns a list of all the databases associated with your account.") {
	depends(checkConfig, prepareClient)
	if(usage()) return
	
	def response 
	try {
		response = beesClient.databaseList()
	
	} catch (Exception e) {
		dealWith e
	}
	
	def infos = response.databases
	
	event "StatusFinal", ["Database List:"]
	
	infos.each { info ->
		event "StatusFinal", ["    $info.name ($info.status)"]
	}
	
	if(!infos) event "StatusFinal", ["    No Databases found."]
	
}

setDefaultTarget(beesDbList)
