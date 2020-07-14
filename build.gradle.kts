// repositories {
//     maven { url = uri("http://172.16.31.95:8081//repository/maven/") }
// }

plugins {
    java
    id("org.springframework.boot") version "2.3.1.RELEASE"
    id("io.spring.dependency-management") version "1.0.9.RELEASE"
}

group = "com.viettel.ems"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    //region LOMBOK
    val lombokV = "org.projectlombok:lombok:1.18.12"
    compileOnly(lombokV)
    annotationProcessor(lombokV)
    testCompileOnly(lombokV)
    testAnnotationProcessor(lombokV)
    //endregion

    val springBoot = "org.springframework.boot:spring-boot"
    val springBootV = "2.3.1.RELEASE"

    implementation("$springBoot-starter:$springBootV")
    implementation("$springBoot-starter-web:$springBootV")
    implementation("$springBoot-starter-webflux:$springBootV")
    implementation("$springBoot-starter-jdbc:$springBootV")
    implementation("$springBoot-starter-data-jpa:$springBootV")

    implementation("org.snmp4j:snmp4j:3.4.1")
    implementation("org.snmp4j:snmp4j-agent:3.3.4")

    implementation("mysql:mysql-connector-java:8.0.19")
}

