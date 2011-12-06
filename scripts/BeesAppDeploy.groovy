import grails.util.Metadata;

import com.cloudbees.api.HashWriteProgress

includeTargets << grailsScript("Init")
includeTargets << new File("${cloudBeesPluginDir}/scripts/_CheckConfig.groovy")
includeTargets << new File("${cloudBeesPluginDir}/scripts/_BeesHelper.groovy")
includeTargets << new File("${cloudBeesPluginDir}/scripts/_BeesCommon.groovy")

USAGE = '''
grails bees-app-deploy [tag]
	tag : optional release tag (defaults to application version)
'''

target(beesAppDeploy: "Deploy a new version of an application using a WAR archive file.") {
	depends(checkConfig, prepareClient)
	
	if(argsMap.params[0] == 'help'){
		event "StatusFinal", ["\nUsage (optionals in square brackets):\n$USAGE"]
		return
	}
	
	if(!config.cloudbees.account){
		event "StatusError", ["Apropriate configuration for Account should be added: config.cloudbees.account"]
		return
	}
	
	String account = config.cloudbees.account
	String appName = Metadata.current.'app.name'
	String appId = config.cloudbees.appid ?: appName
	String appVersion = Metadata.current.'app.version'
	String tag = argsMap.params[0] ?: appVersion
	
	String application = "${account}/${appId}"
	
	String warName = "${grailsSettings.baseDir}/target/${appName}-${appVersion}.war"
	File warFile = new File(warName)
	if(! (warFile.exists() && warFile.file) ){
		event "StatusError", ["WAR file is not build! First issue the grails war command before deploying."]
		return
	}
	
	def response
	def progress = new HashWriteProgress()
	try {
		response = beesClient.applicationDeployWar(application, null, tag, warName, null, true, progress)
		
	} catch (Exception e) {
		dealWith e
	}
	
	event "StatusFinal", ["Application uploaded successfully to: $response.url"]
}

setDefaultTarget(beesAppDeploy)