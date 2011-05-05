grails.project.class.dir = 'target/classes'
grails.project.test.class.dir = 'target/test-classes'
grails.project.test.reports.dir = 'target/test-reports'
grails.project.docs.output.dir = 'docs' // for backwards-compatibility, the docs are checked into gh-pages branch

grails.project.dependency.resolution = {

	inherits 'global'

	log 'warn'

	repositories {
		grailsPlugins()
		grailsHome()
		grailsCentral()

		mavenRepo 'http://repository.codehaus.org'
		mavenCentral()
		mavenRepo 'http://maven.springframework.org/snapshot'
	}

	dependencies {
		runtime('commons-httpclient:commons-httpclient:3.1')
		runtime('com.thoughtworks.xstream:xstream:1.3.1')
	}

	plugins {
		build(':maven-publisher:0.7.5') {
			export = false
		}
	}
}
