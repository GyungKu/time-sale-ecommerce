dependencies {
    implementation(project(":product-service:product-domain"))
    implementation(project(":product-service:product-application"))
    implementation(project(":common:common-exception"))

    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.postgresql:postgresql")
    runtimeOnly("com.h2database:h2")
    implementation("org.redisson:redisson-spring-boot-starter:4.1.0")
}