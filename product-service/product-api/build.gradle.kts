plugins {
    id("org.springframework.boot")
}

dependencies {
    implementation(project(":product-service:product-application"))
    implementation(project(":product-service:product-domain"))
    runtimeOnly(project(":product-service:product-infrastructure"))

    implementation(project(":common:common-exception"))
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-validation")
}