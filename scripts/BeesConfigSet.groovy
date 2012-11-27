includeTargets << grailsScript("Init")
includeTargets << new File("${cloudBeesPluginDir}/scripts/_CheckConfig.groovy")
includeTargets << new File("${cloudBeesPluginDir}/scripts/_BeesHelper.groovy")
includeTargets << new File("${cloudBeesPluginDir}/scripts/_BeesCommon.groovy")

USAGE = '''
grails bees-config-set <name1=value1,name2=value2> [type] [appId]
    name   : the name of the configuration parameter
    value  : the value of the configuraiton parameter
    type   : global or application
    appId  : application id (defaults to application name)
'''

target(beesConfigSet: "Sets configuration parameter globally or for an application.") {
    depends(checkConfig, prepareClient)

    def type = getConfigType(1)
    def appId = buildAppId(2)
    def account = buildOrgId()
    def resource = (type == "application") ? appId : account

    def parameter = getRequiredArg(0)
    def xmlFile = buildParamXmlFile(parameter)

    def response
    try{
        event "StatusFinal", ["Setting parameter on $type scope for $resource"]
        response = beesClient.configurationParametersUpdate(resource, type, xmlFile)

    } catch (Exception e){
        dealWith e

    } finally {
        xmlFile.delete()
    }

    if(response.status){
        event "StatusFinal", ["Status: ${response.status}"]
    } else {
        event "StatusError", ["No status found."]
    }

}

setDefaultTarget(beesConfigSet)
