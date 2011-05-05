import net.stax.api.StaxClientException

includeTargets << grailsScript("Init")
includeTargets << new File("${cloudBeesPluginDir}/scripts/_CheckConfig.groovy")
includeTargets << new File("${cloudBeesPluginDir}/scripts/_StaxHelper.groovy")
includeTargets << new File("${cloudBeesPluginDir}/scripts/_BeesCommon.groovy")

USAGE = '''
grails bees-db-info <dbId>
'''

target(beesDbInfo: "Returns information about connecting to a database.") {
	depends(checkConfig, prepareClient)
	
	String dbId = getRequiredArg()
	def info
	try {
		info = staxClient.databaseInfo(dbId, true)
		
	} catch (StaxClientException sce) {
		printSeparator()
		println "Error: $sce.message"
		printSeparator()
		exit(0)
	}
	
	printSeparator()
	println "Database Name : $info.name"
	println "      created : $info.created"
	println "        owner : $info.owner"
	println "     username : $info.username"
	println "     password : $info.password"
	println "       master : $info.master"
	println "         port : $info.port"
	println "       slaves : $info.slaves"
	println "       status : $info.status"
	printSeparator()
}

setDefaultTarget(beesDbInfo)
