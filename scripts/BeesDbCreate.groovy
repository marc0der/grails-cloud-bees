includeTargets << grailsScript("Init")
includeTargets << new File("${cloudBeesPluginDir}/scripts/_CheckConfig.groovy")
includeTargets << new File("${cloudBeesPluginDir}/scripts/_BeesHelper.groovy")
includeTargets << new File("${cloudBeesPluginDir}/scripts/_BeesCommon.groovy")

USAGE = '''
grails bees-db-create <domain> <dbId> <username> <password>
	domain   : the domain or owner
	dbId     : the database name
	username : the database username
	password : the database password
'''

target(beesDbCreate: "Create a new MySQL database.") {
	depends(checkConfig, prepareClient)
	
	String domain = getRequiredArg(0)
	String dbId = getRequiredArg(1)
	String username = getRequiredArg(2)
	String password = getRequiredArg(3)
	
	def response
	try {
		response = beesClient.databaseCreate(domain, dbId, username, password)
		
	} catch (Exception e) {
		dealWith e
	}
	
	printSeparator()
	println "Database created successfully."
	println "Database ID : $response.databaseId"
	printSeparator()
}

setDefaultTarget(beesDbCreate)
