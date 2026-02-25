plugins {
    id("org.springframework.boot")
}

dependencies {
    // Use Case 호출을 위해 application 모듈 참조
    implementation(project(":user-service:user-application"))
    implementation(project(":user-service:user-infrastructure"))
    implementation(project(":common:common-exception"))

    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-validation")
}