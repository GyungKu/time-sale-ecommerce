dependencies {
    implementation(project(":order-service:order-domain"))
    implementation(project(":order-service:order-application"))
    implementation(project(":common:common-exception"))
    implementation(project(":common:common-proto"))

    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.postgresql:postgresql")
    runtimeOnly("com.h2database:h2")

    implementation("org.springframework.cloud:spring-cloud-starter-openfeign")
    implementation("net.devh:grpc-client-spring-boot-starter:3.1.0.RELEASE")
}