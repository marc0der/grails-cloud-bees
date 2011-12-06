import com.cloudbees.api.BeesClient
import com.cloudbees.api.BeesClientException

target(prepareClient: "Prepare the BeesClient"){
	event "StatusUpdate", ["Preparing the BeesClient"]
	def apiURL = grailsSettings.config.cloudbees.api.url ?: 'https://api.cloudbees.com/api'
	beesClient = new BeesClient(
		apiURL, 
		grailsSettings.config.cloudbees.api.key, 
		grailsSettings.config.cloudbees.api.secret, 
		'xml', '1.0'
	)
	beesClient.verbose = false
}

dealWith = { bce ->
	event "StatusError", ["Error: $bce.message"]
	exit(0)
}

buildDataSourceFragment = { info ->
	"""
        dataSource {
            pooled = true
            driverClassName = "com.mysql.jdbc.Driver"
            username = "${info.username}"
            password = "${info.password}"
            url = "jdbc:mysql://${info.master}/${info.name}"
            loggingSql = false

            properties {
                maxActive = 25
                maxIdle = 5
                minIdle = 1
                initialSize = 1
                minEvictableIdleTimeMillis = 60000
                timeBetweenEvictionRunsMillis = 60000
                maxWait = 10000
                validationQuery = "select 1"
            }
        }
	"""
}
