dependencies {
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
