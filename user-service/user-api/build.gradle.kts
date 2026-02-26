plugins {
    id("org.springframework.boot")
}

dependencies {
    implementation(project(":user-service:user-application"))
    runtimeOnly(project(":user-service:user-infrastructure"))
    implementation(project(":common:common-exception"))

    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-validation")
}