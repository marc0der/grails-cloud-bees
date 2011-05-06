includeTargets << grailsScript("Init")
includeTargets << new File("${cloudBeesPluginDir}/scripts/_CheckConfig.groovy")
includeTargets << new File("${cloudBeesPluginDir}/scripts/_BeesHelper.groovy")
includeTargets << new File("${cloudBeesPluginDir}/scripts/_BeesCommon.groovy")

USAGE = '''
grails bees-db-delete <dbId>
'''

target(beesDbDelete: "Delete a MySQL database.") {
	depends(checkConfig, prepareClient)
	
	String dbId = getRequiredArg(0)
	
	def response
	try {
		response = beesClient.databaseDelete(dbId)
		
	} catch (Exception e) {
		dealWith e
	}
	
	printSeparator()
	println "Database $dbId deleted successfully: $response.deleted"
	printSeparator()
}

setDefaultTarget(beesDbDelete)
