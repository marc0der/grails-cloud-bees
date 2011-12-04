//prepare system properties and grailsEnv variable
System.setProperty "grails.env.set", "true"
System.setProperty "grails.env", "test"
grailsEnv = "test"

includeTargets << grailsScript("Init")
includeTargets << grailsScript("TestApp")

target(main: "Runs the test-app script overriding environment to be test.") {
	event "StatusUpdate", ["Environment set to ${System.getProperty('grails.env')} for testing."]
	depends("default")
}

setDefaultTarget(main)
