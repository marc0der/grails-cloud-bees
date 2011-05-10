includeTargets << grailsScript("Init")
includeTargets << new File("${cloudBeesPluginDir}/scripts/_CheckConfig.groovy")
includeTargets << new File("${cloudBeesPluginDir}/scripts/_BeesHelper.groovy")
includeTargets << new File("${cloudBeesPluginDir}/scripts/_BeesCommon.groovy")

USAGE = '''
grails bees-db-list
'''

target(beesDbList: "Returns a list of all the databases associated with your account.") {
	depends(checkConfig, prepareClient)
	
	def response 
	try {
		response = beesClient.databaseList()
	
	} catch (Exception e) {
		dealWith e
	}
	
	def infos = response.databases
	
	printSeparator()
	println "Database List:"
	printSeparator()
	
	infos.each { info ->
		println "$info.name ($info.status)"
	}
	
	if(!infos) println "No Databases found."
	
	printSeparator()
}

setDefaultTarget(beesDbList)
