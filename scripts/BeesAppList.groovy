includeTargets << grailsScript("Init")
includeTargets << new File("${cloudBeesPluginDir}/scripts/_CheckConfig.groovy")
includeTargets << new File("${cloudBeesPluginDir}/scripts/_BeesHelper.groovy")
includeTargets << new File("${cloudBeesPluginDir}/scripts/_BeesCommon.groovy")

USAGE = '''
grails bees-app-list
'''

target(beesAppList: "Returns the list of applications available to your account.") {
	depends(checkConfig, prepareClient)
	
	def response
	try{
		response = beesClient.applicationList()
		
	} catch (Exception e){
		dealWith e
	}
	def infos = response.applications
	
	printSeparator()
	println "Application List:"
	printSeparator()

	infos.each { info ->
		println "$info.title ($info.status)"
	}

	if(!infos) println "No applications found."

	printSeparator()
}

setDefaultTarget(beesAppList)
