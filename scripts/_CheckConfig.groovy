includeTargets << grailsScript("_GrailsPackage")

target(checkConfig: "Ensure that the configuration is set."){
	depends(compile, createConfig)

	event("StatusUpdate", ["Checking CloudBees config"])
	if(!(grailsSettings.config.cloudbees.account && grailsSettings.config.cloudbees.api.key && grailsSettings.config.cloudbees.api.secret)){
		event "StatusError",  ["CloudBees Plugin not configured!"]
		event "StatusError",  ["Please add appropriate configuration for:"]
		if(!grailsSettings.config.cloudbees.account) event("StatusError", ["The Account    : cloudbees.account"])
		if(!grailsSettings.config.cloudbees.api.key) event("StatusError", ["The API key    : cloudbees.api.key"])
		if(!grailsSettings.config.cloudbees.api.secret) event("StatusError", ["The API secret : cloudbees.api.secret"])
		exit(0)
	}
}
