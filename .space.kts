job("Build") {
    gradlew("openjdk:11", "clean", "build")
}

job("create Docker container") {
    docker {
        gradlew("openjdk:11", "jibDockerBuild", "docker")
    }
}
