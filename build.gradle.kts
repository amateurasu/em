fun nexus(ip: String) = uri("http://$ip:8081/repository/maven/")

repositories {
    // maven { url = nexus("172.16.31.95") }
    maven { url = nexus("localhost") }
}

plugins {
    java
    id("org.springframework.boot") version "2.3.1.RELEASE"
    id("io.spring.dependency-management") version "1.0.9.RELEASE"
}

group = "com.viettel.ems"
version = "1.0-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

subprojects {
    apply(plugin = "java")
    apply(plugin = "application")

    repositories {
        // maven { url = nexus("172.16.31.95") }
        maven { url = nexus("localhost") }
    }

    dependencies {
        val lombokV = "org.projectlombok:lombok:1.18.12"
        compileOnly(lombokV)
        annotationProcessor(lombokV)
        testCompileOnly(lombokV)
        testAnnotationProcessor(lombokV)
    }
}
