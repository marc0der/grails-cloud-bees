includeTargets << grailsScript("Init")
includeTargets << new File("${cloudBeesPluginDir}/scripts/_CheckConfig.groovy")
includeTargets << new File("${cloudBeesPluginDir}/scripts/_BeesHelper.groovy")
includeTargets << new File("${cloudBeesPluginDir}/scripts/_BeesCommon.groovy")

USAGE = '''
grails bees-db-snapshot-create [dbId] [title]
	dbId  : the database name (defaults to application name)
	title : the snapshot title (defaults to a timestamp)
'''

target(beesDbSnapshotCreate: "Create a database snapshot.") {
    depends(checkConfig, prepareClient)

    String dbId = buildDbId(0)
    String title = buildDbSnapshotTitle(1)

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
