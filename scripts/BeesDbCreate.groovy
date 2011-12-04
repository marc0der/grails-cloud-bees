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
	if(!domain) return
	
	String dbId = getRequiredArg(1)
	if(!dbId) return
	
	String username = getRequiredArg(2)
	if(!username) return
	
	String password = getRequiredArg(3)
	if(!password) return
	
	def response
	try {
		response = beesClient.databaseCreate(domain, dbId, username, password)
		
	} catch (Exception e) {
		dealWith e
	}
	
	event "StatusFinal", ["Database created successfully."]
	event "StatusFinal", ["Database ID : $response.databaseId"]

}

setDefaultTarget(beesDbCreate)
