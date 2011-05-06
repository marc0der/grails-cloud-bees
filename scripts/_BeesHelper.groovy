import com.cloudbees.api.BeesClient
import com.cloudbees.api.BeesClientException

target(prepareClient: "Prepare the BeesClient."){
	println "Preparing the BeesClient..."
    beesClient = new BeesClient(
		config.cloudbees.api.url, 
		config.cloudbees.api.key, 
		config.cloudbees.api.secret, 
		'xml', '1.0'
	)
}

dealWith = { bce ->
	printSeparator()
	println "Error: $bce.message"
	printSeparator()
	exit(0)
}