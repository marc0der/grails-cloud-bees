includeTargets << grailsScript("Init")
includeTargets << new File("${cloudBeesPluginDir}/scripts/_CheckConfig.groovy")
includeTargets << new File("${cloudBeesPluginDir}/scripts/_BeesHelper.groovy")
includeTargets << new File("${cloudBeesPluginDir}/scripts/_BeesCommon.groovy")

USAGE = '''
grails bees-db-set-password [dbId]
    password : the new password
	dbId     : the database name (defaults to the application name)
'''

target(beesDbSetPassword: "Updates the database password.") {
    depends(checkConfig, prepareClient)

    String password = getRequiredArg(0)
    String dbId = buildDbId(1)

    def response
    try {
        response = beesClient.databaseSetPassword(dbId, password)

    } catch (Exception e) {
        dealWith e
    }

    event "StatusFinal", ["Database password changed: $response.success)"]

}

setDefaultTarget(beesDbSetPassword)
