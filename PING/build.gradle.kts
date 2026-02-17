plugins {
    id("java")
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    implementation ("org.pcap4j:pcap4j-core:1.7.3")
    implementation ("org.pcap4j:pcap4j-packetfactory-static:1.7.3")
    // SLF4J API
    implementation ("org.slf4j:slf4j-api:1.7.32")  // или последнюю версию

    // Logback Classic
    implementation ("ch.qos.logback:logback-classic:1.2.6")  // или последнюю версию

    // Logback Core
    implementation ("ch.qos.logback:logback-core:1.2.6")  // или последнюю версию

}

tasks.test {
    useJUnitPlatform()
}