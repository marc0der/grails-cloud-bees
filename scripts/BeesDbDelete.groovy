import net.stax.api.StaxClientException

includeTargets << grailsScript("Init")
includeTargets << new File("${cloudBeesPluginDir}/scripts/_CheckConfig.groovy")
includeTargets << new File("${cloudBeesPluginDir}/scripts/_StaxHelper.groovy")
includeTargets << new File("${cloudBeesPluginDir}/scripts/_BeesCommon.groovy")

USAGE = '''
grails bees-db-delete <dbId>
'''

target(beesDbDelete: "Delete a MySQL database.") {
	depends(checkConfig, prepareClient)
	
	String dbId = getRequiredArg(0)
	
	def response
	try {
		response = staxClient.databaseDelete(dbId)
		
	} catch (StaxClientException sce) {
		printSeparator()
		println "Database not deleted: $sce.message"
		printSeparator()
		exit(0)
	}
	
	printSeparator()
	println "Database $dbId deleted successfully: $response.deleted"
	printSeparator()
}

setDefaultTarget(beesDbDelete)
