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

cloudBeesInvoke = { method ->
	try {
		response = beesClient."$method"()

	} catch (BeesClientException bce) {
		printSeparator()
		println "Error: $bce.message"
		printSeparator()
		exit(0)
	}
	return response
}

cloudBeesInvokeParams = { method, params ->
	if(params instanceof String){
		params = [params]
	}
    String paramString = ""
    params.each { param ->
        paramString += "$param "
    }
    def response
	try {
		response = beesClient."$method"(paramString)

	} catch (BeesClientException bce) {
		printSeparator()
		println "Error: $bce.message"
		printSeparator()
		exit(0)
	}
	return response
}
