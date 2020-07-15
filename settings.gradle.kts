rootProject.name = "em"

pluginManagement {
    repositories {
        // maven { url = uri("http://172.16.31.95:8081/repository/maven/") }
        maven { url = uri("http://localhost:8081/repository/maven/") }
    }
}

include(":service")
include(":emulator")
