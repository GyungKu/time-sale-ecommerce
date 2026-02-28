plugins {
    id("org.springframework.boot")
}

dependencies {
    implementation(project(":order-service:order-application"))
    implementation(project(":order-service:order-domain"))
    implementation(project(":order-service:order-infrastructure"))
    implementation(project(":common:common-security"))
    implementation(project(":common:common-proto"))

    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("net.devh:grpc-server-spring-boot-starter:3.1.0.RELEASE")
}