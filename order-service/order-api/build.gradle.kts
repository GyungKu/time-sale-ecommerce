plugins {
    id("org.springframework.boot")
}

dependencies {
    implementation(project(":order-service:order-application"))
    implementation(project(":order-service:order-domain"))
    implementation(project(":order-service:order-infrastructure"))
    implementation(project(":common:common-security"))

    implementation(project(":common:common-exception"))
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-validation")
}