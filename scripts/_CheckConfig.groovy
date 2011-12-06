includeTargets << grailsScript("_GrailsPackage")

target(checkConfig: "Ensure that the configuration is set."){
	depends(compile, createConfig)

	event("StatusUpdate", ["Checking CloudBees config"])
	if(!(config.cloudbees.account && config.cloudbees.api.key && config.cloudbees.api.secret)){
		event "StatusError",  ["CloudBees Plugin not configured!"]
		event "StatusError",  ["Please add appropriate configuration for:"]
		if(!config.cloudbees.account) event("StatusError", ["The Account    : cloudbees.account"])
		if(!config.cloudbees.api.key) event("StatusError", ["The API key    : cloudbees.api.key"])
		if(!config.cloudbees.api.secret) event("StatusError", ["The API secret : cloudbees.api.secret"])
		exit(0)
	}
}
