job("Build and create Docker container") {
    gradlew("openjdk:11", "clean build jibDockerBuild docker")
}
