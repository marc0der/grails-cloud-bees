getRequiredArg = { int index = 0 ->
	def argsList = argsMap.params
	String value = argsList[index]
	if (value && (value != 'help')) {
		return value
	}
	println "\nUsage (optionals in square brackets):\n$USAGE"
	exit(0)
}

printSeparator = {
	println "-------------------------------------------------------------------------"
}
