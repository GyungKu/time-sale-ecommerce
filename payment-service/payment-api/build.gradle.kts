plugins {
    id("org.springframework.boot")
}

dependencies {
    implementation(project(":payment-service:payment-application"))
    implementation(project(":payment-service:payment-domain"))
    implementation(project(":payment-service:payment-infrastructure"))
    implementation(project(":common:common-security"))

    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-validation")
}