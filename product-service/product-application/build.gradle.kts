dependencies {
    implementation(project(":product-service:product-domain"))

    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.springframework:spring-tx")
    implementation("org.springframework.boot:spring-boot-starter-aspectj")
}