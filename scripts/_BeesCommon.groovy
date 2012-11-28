import grails.util.Metadata

buildOrgId = {
    grailsSettings.config.cloudbees.account
}

buildAppId = { int index = 0 ->
	String account = grailsSettings.config.cloudbees.account
	String optionalName = getOptionalArg(index)
	String configAppName = Metadata.current.'app.name'
	String appName = optionalName ?: configAppName.toLowerCase()
	"${account}/${appName}"
}

buildDbId = { int index = 0 ->
	String optionalName = getOptionalArg(index)
	String configAppName = Metadata.current.'app.name'
	return (optionalName ?: configAppName.toLowerCase())
}

buildDbSnapshotTitle = { int index = 0 ->
    String optionalTitle = getOptionalArg(index)
    String defaultTitle = new Date().format("YYYY-MM-dd HH:mm:ss")
    return (optionalTitle ?: defaultTitle)
}

buildAppTag = {
	String appVersion = Metadata.current.'app.version'
	String optionalTag = getOptionalArg(1)
	optionalTag ?: appVersion
}

getRequiredArg = { int index = 0 ->
	def argsList = argsMap.params
	String value = argsList[index]
	if (value && (value != 'help')) {
		return value
	}
	event "StatusFinal", ["\nUsage (optionals in square brackets):\n$USAGE"]
	return ""
}

getOptionalArg = { int index = 0 ->
	def argsList = argsMap.params
	String value = argsList[index]
	return value
}

getConfigType = { int index = 0 ->
    String type = getOptionalArg(index)
    if(! ['application', 'global'].contains(type)){
        event "StatusError", ["Use a valid type: application/global"]
        exit 0

    } else {
        event "StatusFinal", ["Getting $type configuration."]
    }

    type
}
