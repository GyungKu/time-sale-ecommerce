dependencies {
    implementation(project(":order-service:order-domain"))
    implementation(project(":common:common-exception"))
    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.springframework:spring-tx")
}