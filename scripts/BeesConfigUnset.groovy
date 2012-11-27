includeTargets << grailsScript("Init")
includeTargets << new File("${cloudBeesPluginDir}/scripts/_CheckConfig.groovy")
includeTargets << new File("${cloudBeesPluginDir}/scripts/_BeesHelper.groovy")
includeTargets << new File("${cloudBeesPluginDir}/scripts/_BeesCommon.groovy")

USAGE = '''
grails bees-config-unset [type] [appId]
    type   : global or application
    appId  : application id (defaults to application name)
'''                                                                      `

target(beesConfigSet: "Sets configuration parameter globally or for an application.") {
    depends(checkConfig, prepareClient)

    def type = getConfigType(0)
    def appId = buildAppId(1)
    def account = buildOrgId()
    def resource = (type == "application") ? appId : account

    def response
    try{
        event "StatusFinal", ["Unsetting all parameters on $type scope for $resource"]
        response = beesClient.configurationParametersDelete(resource, type)

    } catch (Exception e){
        dealWith e

    }

    if(response.status){
        event "StatusFinal", ["Status: ${response.status}"]
    } else {
        event "StatusError", ["No status found."]
    }

}

setDefaultTarget(beesConfigSet)
