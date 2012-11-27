includeTargets << grailsScript("Init")
includeTargets << new File("${cloudBeesPluginDir}/scripts/_CheckConfig.groovy")
includeTargets << new File("${cloudBeesPluginDir}/scripts/_BeesHelper.groovy")
includeTargets << new File("${cloudBeesPluginDir}/scripts/_BeesCommon.groovy")

USAGE = '''
grails bees-config-list [type] [appId]
    type  : global or application (defaults to application)
    appId : application id (defaults to application name)
'''

target(beesConfigList: "Returns the list of configuration parameters for the application.") {
	depends(checkConfig, prepareClient)

    String type = getConfigType(0)
    String appId = buildAppId(1)
    String account = buildOrgId()
	String resource = (type == "application") ? appId : account

	def response
	try{
		response = beesClient.configurationParameters(resource, type)
		
	} catch (Exception e){
		dealWith e
	}
	def configuration = response.configuration
	
	event "StatusFinal", ["Configuration Parameters:"]

	if(configuration){
		def config = new XmlSlurper().parseText(configuration)
		for(param in config.param) {
			event "StatusFinal", ["    ${param.@name} : ${param.@value}"]
		}
	} else {
		event "StatusFinal", ["    No configuration found."]
	}

}

setDefaultTarget(beesConfigList)
