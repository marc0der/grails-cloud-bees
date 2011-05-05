includeTargets << grailsScript("_GrailsPackage")

target(checkConfig: "Ensure that the configuration is set."){
	depends(compile, createConfig)

	println "Checking config..."
	if(!(config.cloudbees.api.url && config.cloudbees.api.key && config.cloudbees.api.secret)){
		println "-------------------------------------------------------------------------"
		println "Plugin not configured!"
		println "Please add appropriate configuration to the Config.groovy:"
		if(!config.cloudbees.api.url) println "The API url    : cloudbees.api.url"
		if(!config.cloudbees.api.key) println "The API key    : cloudbees.api.key"
		if(!config.cloudbees.api.secret) println "The API secret : cloudbees.api.secret"
		println "-------------------------------------------------------------------------"
		exit(0)
	}
}
