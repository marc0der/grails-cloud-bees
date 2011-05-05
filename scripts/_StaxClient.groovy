import net.stax.api.StaxClient

target(prepareClient: "Prepare the StaxClient."){
	println "Preparing the StaxClient..."
    staxClient = new StaxClient(
		config.cloudbees.api.url, 
		config.cloudbees.api.key, 
		config.cloudbees.api.secret, 
		'xml', '1.0'
	)
}