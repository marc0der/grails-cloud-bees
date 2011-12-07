includeTargets << grailsScript("Init")
includeTargets << new File("${cloudBeesPluginDir}/scripts/_CheckConfig.groovy")
includeTargets << new File("${cloudBeesPluginDir}/scripts/_BeesHelper.groovy")
includeTargets << new File("${cloudBeesPluginDir}/scripts/_BeesCommon.groovy")

USAGE = '''
grails bees-db-delete [dbId]
	dbId     : the database name (defaults to the application name)
'''

target(beesDbDelete: "Delete a MySQL database.") {
	depends(checkConfig, prepareClient)
	if(usage()) return
	
	String dbId = buildDbId()
	
	def response
	try {
		response = beesClient.databaseDelete(dbId)
		
	} catch (Exception e) {
		dealWith e
	}
	
	event "StatusFinal", ["Database $dbId deleted successfully: $response.deleted"]

}

setDefaultTarget(beesDbDelete)
