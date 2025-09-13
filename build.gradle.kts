plugins {
    id("java")
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    implementation("org.apache.xmlgraphics:fop:2.9")
    implementation ("org.apache.velocity:velocity-engine-core:2.3")
}

tasks.test {
    useJUnitPlatform()
}