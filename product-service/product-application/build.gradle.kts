dependencies {
    implementation(project(":product-service:product-domain"))
    implementation(project(":common:common-exception"))
    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.springframework:spring-tx")
}