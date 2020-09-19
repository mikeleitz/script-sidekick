job("Build") {
    gradlew("openjdk:11", "clean", "build")
}

job("create Docker container") {
    docker {
        build {
            file = "ui/Dockerfile"
        }

        push("leadtechnologist.registry.jetbrains.space/mydocker/lickety-script-ui") {
            tag = "0.0.1-SNAPSHOT"
        }
    }
}
