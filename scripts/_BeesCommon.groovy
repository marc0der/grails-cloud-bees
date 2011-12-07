import grails.util.Metadata

usage = {
	if(argsMap.params[0] == 'help'){
		event "StatusFinal", ["\nUsage (optionals in square brackets):\n$USAGE"]
		return true
		
	} else {
		return false
	}
}

buildAppId = {
	String account = grailsSettings.config.cloudbees.account
	String optionalName = getOptionalArg(0)
	String configAppName = Metadata.current.'app.name'
	String appName = optionalName ?: configAppName.toLowerCase()
	"${account}/${appName}"
}

buildDbId = {
	String optionalName = getOptionalArg(0)
	String configAppName = Metadata.current.'app.name'
	return (optionalName ?: configAppName.toLowerCase())
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