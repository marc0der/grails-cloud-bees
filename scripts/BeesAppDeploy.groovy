import net.stax.api.HashWriteProgress
import net.stax.api.StaxClientException

includeTargets << grailsScript("Init")
includeTargets << new File("${cloudBeesPluginDir}/scripts/_CheckConfig.groovy")
includeTargets << new File("${cloudBeesPluginDir}/scripts/_StaxHelper.groovy")
includeTargets << new File("${cloudBeesPluginDir}/scripts/_BeesCommon.groovy")

USAGE = '''
grails bees-app-deploy <appId> <release tag> WAR_ARCHIVE_FILE
'''

target(beesAppDeploy: "Deploy a new version of an application using a WAR archive file.") {
	depends(checkConfig, prepareClient)
	
	String appId = getRequiredArg(0)
	String tag = getRequiredArg(1)
	String war = getRequiredArg(2)
	
	def response
	def progress = new HashWriteProgress()
	try {
		response = staxClient.applicationDeployWar(appId, null, tag, war, null, true, progress)
		
	} catch (StaxClientException sce) {
		printSeparator()
		println "Application not created: $sce.message"
		printSeparator()
		exit(0)
	}
	
	printSeparator()
	println "Application uploaded successfully to: $response.url"
	printSeparator()
}

setDefaultTarget(beesAppDeploy)
