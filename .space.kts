job("Build") {
    gradlew("openjdk:11", "clean", "build", "jibDockerBuild", "docker")
}

job("create Docker container") {
    docker {
        gradlew("openjdk:11", "jibDockerBuild", "docker")
    }
}
