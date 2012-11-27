import com.cloudbees.api.BeesClient

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
            dbCreate = "update"
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

buildParamXmlFile = { csv ->
    def xmlFile = File.createTempFile("config-", ".xml")

    if(!csv.contains("=")){
        event "StatusError", ["Invalid parameter. Use key=value format."]
        exit 0
    }

    def params = csv.tokenize(',')
    event "StatusFinal", ["Configuration Parameter:"]
    xmlFile << "<config>"
    params.each { parameter ->
        def name = parameter.split('=')[0]
        def value = parameter.split('=')[1]
        event "StatusFinal", ["    $name : $value"]
        xmlFile << "<param name=\"$name\" value=\"$value\"/>"
    }
    xmlFile << "</config>"
    xmlFile
}