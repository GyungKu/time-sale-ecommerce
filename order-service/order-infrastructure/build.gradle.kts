dependencies {
    implementation(project(":order-service:order-domain"))
    implementation(project(":order-service:order-application"))

    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.postgresql:postgresql")
    runtimeOnly("com.h2database:h2")
}