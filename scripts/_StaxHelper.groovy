import net.stax.api.StaxClient
import net.stax.api.StaxClientException

target(prepareClient: "Prepare the StaxClient."){
	println "Preparing the StaxClient..."
    staxClient = new StaxClient(
		config.cloudbees.api.url, 
		config.cloudbees.api.key, 
		config.cloudbees.api.secret, 
		'xml', '1.0'
	)
}

cloudBeesCall = { method, params ->
	if(params instanceof String){
		params = [params]
	}
    String paramString = ""
    params.each { param ->
        paramString += "$param "
    }
    def response
	try {
		response = staxClient."$method"(paramString)

	} catch (StaxClientException sce) {
		printSeparator()
		println "Error: $sce.message"
		printSeparator()
		exit(0)
	}
	return response
}
