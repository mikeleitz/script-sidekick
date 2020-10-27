job("build and publish") {
    container("gradle:6.1.1-jre11") {
        kotlinScript { api ->
            api.gradle("build")
            try {
                api.gradle("publish")
            } catch (ex: Exception) {
                println("Publishing failed")
            }
        }
    }
}
