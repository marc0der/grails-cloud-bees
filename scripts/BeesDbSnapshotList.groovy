includeTargets << grailsScript("Init")
includeTargets << new File("${cloudBeesPluginDir}/scripts/_CheckConfig.groovy")
includeTargets << new File("${cloudBeesPluginDir}/scripts/_BeesHelper.groovy")
includeTargets << new File("${cloudBeesPluginDir}/scripts/_BeesCommon.groovy")

USAGE = '''
grails bees-db-snapshot-list [dbId]
	dbId  : the database name (defaults to application name)
'''

target(beesDbSnapshotList: "Lists all database snapshots for a database.") {
    depends(checkConfig, prepareClient)

    String dbId = buildDbId(0)

    def responseList
    try {
        responseList = beesClient.databaseSnapshotList(dbId)

    } catch (Exception e) {
        dealWith e
    }

    event "StatusFinal", ["Database snapshots:"]
    for(response in responseList.snapshots){
        event "StatusFinal", ["    $response.id : $response.created : $response.title"]
    }
}

setDefaultTarget(beesDbSnapshotList)
