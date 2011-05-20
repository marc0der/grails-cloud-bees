includeTargets << grailsScript("Init")
includeTargets << grailsScript("TestApp")

target(main: "Runs the test-app script overriding environment to be test.") {
	def environment = "test"
	println "Override ${System.getProperty('grails.env')} environment with $environment"
	System.setProperty "grails.env", environment
	depends("default")
}

setDefaultTarget(main)
