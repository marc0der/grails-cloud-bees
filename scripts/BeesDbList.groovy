import net.stax.api.StaxClientException

includeTargets << grailsScript("Init")
includeTargets << new File("${basedir}/scripts/_CheckConfig.groovy")
includeTargets << new File("${basedir}/scripts/_StaxHelper.groovy")
includeTargets << new File("${basedir}/scripts/_BeesCommon.groovy")

USAGE = '''
grails bees-db-list
'''

target(beesDbList: "Returns a list of all the databases associated with your account.") {
	depends(checkConfig, prepareClient)
	
	def response 
	try {
		response = staxClient.databaseList()
	
	} catch (StaxClientException sce) {
		printSeparator()
		println "Error: $sce.message"
		printSeparator()
		exit(0)
	}
	
	def infos = response.databases
	
	printSeparator()
	infos.each { info ->
		println "Database Name: $info.name"
		println "        owner: $info.owner"
		println "      created: $info.created"
		println "       status: $info.status"
		printSeparator()
	}
}

setDefaultTarget(beesDbList)
