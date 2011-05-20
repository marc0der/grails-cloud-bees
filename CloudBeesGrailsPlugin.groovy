class CloudBeesGrailsPlugin {
    // the plugin version
    def version = "0.1.1"
    // the version or versions of Grails the plugin is designed for
    def grailsVersion = "1.3.0 > *"
    // the other plugins this plugin depends on
    def dependsOn = [:]
    // resources that are excluded from plugin packaging
    def pluginExcludes = [
            "grails-app/",
			"lib/",
			"web-app/"
    ]

    def author = "Marco Vermeulen"
    def authorEmail = "vermeulen.mp@gmail.com"
    def title = "CloudBees"
    def description = '''\\
Adds a set of scripts that allow standalone integration with the CloudBees client API.
'''

    // URL to the plugin's documentation
    def documentation = "http://grails.org/plugin/cloud-bees"

    def doWithWebDescriptor = { xml -> }

    def doWithSpring = { }

    def doWithDynamicMethods = { ctx -> }

    def doWithApplicationContext = { applicationContext -> }

    def onChange = { event -> }

    def onConfigChange = { event -> }
}
