plugins {
    id("org.springframework.boot")
}

dependencies {
    implementation(project(":user-service:user-application"))
    implementation(project(":user-service:user-infrastructure"))
    implementation(project(":common:common-exception"))
    implementation(project(":common:common-security"))

    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-validation")
}