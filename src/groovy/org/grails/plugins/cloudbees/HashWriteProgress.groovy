package org.grails.plugins.cloudbees

import com.cloudbees.api.UploadProgress

class HashWriteProgress implements UploadProgress {
	def uploadComplete = false
	def hashMarkCount = 0

	void handleBytesWritten(long deltaCount, long totalWritten, long totalToSend) {
        
		if(uploadComplete) return
        
        int totalMarks = (int)(totalWritten/(totalToSend/100f))
		
        while(hashMarkCount < totalMarks) {
            hashMarkCount++
            if(hashMarkCount % 25 == 0) {
                if(hashMarkCount < 100) {
                    println String.format("uploaded %d%%", hashMarkCount)
                } else {
                    //upload completed (or will very soon)
                    uploadComplete = true
                    println "upload completed"
                    println "deploying application to server(s)..."
                }
            }
        }
	}

}
