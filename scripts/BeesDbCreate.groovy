includeTargets << grailsScript("Init")
includeTargets << new File("${cloudBeesPluginDir}/scripts/_CheckConfig.groovy")
includeTargets << new File("${cloudBeesPluginDir}/scripts/_BeesHelper.groovy")
includeTargets << new File("${cloudBeesPluginDir}/scripts/_BeesCommon.groovy")

USAGE = '''
grails bees-db-create [dbId] [username] [password]
	dbId     : the database name (defaults to application name)
	username : the database username (deafaults to application name)
	password : the database password (deafaults to changeme!)
'''

target(beesDbCreate: "Create a new MySQL database.") {
	depends(checkConfig, prepareClient)
	if(usage()) return

	String dbId = buildDbId()
	String username = getOptionalArg(1) ?: dbId
	String password = getOptionalArg(2) ?: 'changeme!'
	String account = grailsSettings.config.cloudbees.account
	
	def response
	try {
		response = beesClient.databaseCreate(account, dbId, username, password)
		
	} catch (Exception e) {
		dealWith e
	}
	
	event "StatusFinal", ["Database created successfully."]
	event "StatusFinal", ["Database ID : $response.databaseId"]

}

setDefaultTarget(beesDbCreate)
