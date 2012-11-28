includeTargets << grailsScript("Init")
includeTargets << new File("${cloudBeesPluginDir}/scripts/_CheckConfig.groovy")
includeTargets << new File("${cloudBeesPluginDir}/scripts/_BeesHelper.groovy")
includeTargets << new File("${cloudBeesPluginDir}/scripts/_BeesCommon.groovy")

USAGE = '''
grails bees-db-snapshot-delete <snapshotId> [dbId]
    snapshotId  : the snapshot id (retrieved by snapshot list)
	dbId        : the database name (defaults to application name)
'''

target(beesDbSnapshotDelete: "Delete a database snapshot.") {
    depends(checkConfig, prepareClient)

    String snapshotId = getRequiredArg(0)
    String dbId = buildDbId(1)

    def response
    try {
        response = beesClient.databaseSnapshotDelete(dbId, snapshotId)

    } catch (Exception e) {
        dealWith e
    }

    event "StatusFinal", ["Database snapshot deleted successfully: $response.deleted"]

}

setDefaultTarget(beesDbSnapshotDelete)
