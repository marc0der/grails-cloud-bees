import net.stax.api.StaxClientException

includeTargets << grailsScript("Init")
includeTargets << new File("${basedir}/scripts/_CheckConfig.groovy")
includeTargets << new File("${basedir}/scripts/_StaxHelper.groovy")
includeTargets << new File("${basedir}/scripts/_BeesCommon.groovy")

USAGE = '''
grails bees-db-create <domain> <dbId> <username> <password>
'''

target(beesDbCreate: "Create a new MySQL database.") {
	depends(checkConfig, prepareClient)
	
	String domain = getRequiredArg(0)
	String dbId = getRequiredArg(1)
	String username = getRequiredArg(2)
	String password = getRequiredArg(3)
	
	def response
	try {
		response = staxClient.databaseCreate(domain, dbId, username, password)
		
	} catch (StaxClientException sce) {
		printSeparator()
		println "Database not created: $sce.message"
		printSeparator()
		exit(0)
	}
	
	printSeparator()
	println "Database created successfully."
	println "Database ID : $response.databaseId"
	printSeparator()
}

setDefaultTarget(beesDbCreate)
