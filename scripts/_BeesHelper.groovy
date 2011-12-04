import com.cloudbees.api.BeesClient
import com.cloudbees.api.BeesClientException

target(prepareClient: "Prepare the BeesClient"){
	event "StatusUpdate", ["Preparing the BeesClient"]
	beesClient = new BeesClient(
		config.cloudbees.api.url, 
		config.cloudbees.api.key, 
		config.cloudbees.api.secret, 
		'xml', '1.0'
	)
	beesClient.verbose = false
}

dealWith = { bce ->
	event "StatusError", ["Error: $bce.message"]
	exit(0)
}
