includeTargets << grailsScript("Init")
includeTargets << new File("${cloudBeesPluginDir}/scripts/_CheckConfig.groovy")
includeTargets << new File("${cloudBeesPluginDir}/scripts/_BeesHelper.groovy")
includeTargets << new File("${cloudBeesPluginDir}/scripts/_BeesCommon.groovy")

USAGE = '''
grails bees-db-info [dbId]
	dbId     : the database name (defaults to the application name)
'''

target(beesDbInfo: "Returns information about connecting to a database.") {
	depends(checkConfig, prepareClient)

	String dbId = buildDbId()
	
	def info
	try {
		info = beesClient.databaseInfo(dbId, true)
		
	} catch (Exception e) {
		dealWith e
	}
		
	event "StatusFinal", ["Database Name : $info.name"]
	event "StatusFinal", ["      created : $info.created"]
	event "StatusFinal", ["        owner : $info.owner"]
	event "StatusFinal", ["     username : $info.username"]
	event "StatusFinal", ["     password : $info.password"]
	event "StatusFinal", ["       master : $info.master"]
	event "StatusFinal", ["         port : $info.port"]
	event "StatusFinal", ["       slaves : $info.slaves"]
	event "StatusFinal", ["       status : $info.status"]

	def dataSourceFragment = buildDataSourceFragment(info)
	event "StatusFinal", ["\n\nThe following datasource block may be added to your DataSource.groovy:"]
	event "StatusFinal", ["$dataSourceFragment"]
	
}

setDefaultTarget(beesDbInfo)
