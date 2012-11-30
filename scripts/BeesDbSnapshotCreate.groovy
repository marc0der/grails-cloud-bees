includeTargets << grailsScript("Init")
includeTargets << grailsScript("_GrailsBootstrap")
includeTargets << new File("${cloudBeesPluginDir}/scripts/_CheckConfig.groovy")
includeTargets << new File("${cloudBeesPluginDir}/scripts/_BeesHelper.groovy")
includeTargets << new File("${cloudBeesPluginDir}/scripts/_BeesCommon.groovy")

USAGE = '''
grails bees-db-snapshot-create [title] [dbId]
	title : the snapshot title (defaults to a timestamp)
	dbId        : the database name (defaults first to environment database name, then application name)
'''

target(beesDbSnapshotCreate: "Create a database snapshot.") {
    depends(checkConfig, prepareClient, loadApp, configureApp)

    String title = buildDbSnapshotTitle(0)
    String dbId = buildDbId(1, appCtx)

    def response
    try {
        response = beesClient.databaseSnapshotCreate(dbId, title)

    } catch (Exception e) {
        dealWith e
    }

    event "StatusFinal", ["Database snapshot created successfully."]
    event "StatusFinal", ["    ID      : $response.id"]
    event "StatusFinal", ["    Title   : $response.title"]
    event "StatusFinal", ["    Created : $response.created"]

}

setDefaultTarget(beesDbSnapshotCreate)
