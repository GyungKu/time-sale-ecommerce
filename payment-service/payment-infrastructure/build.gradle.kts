dependencies {
    implementation(project(":payment-service:payment-domain"))
    implementation(project(":payment-service:payment-application"))
    implementation(project(":common:common-proto"))
    implementation(project(":common:common-kafka"))

    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.postgresql:postgresql")
    runtimeOnly("com.h2database:h2")
    implementation("org.redisson:redisson-spring-boot-starter:4.1.0")

    implementation("org.springframework.cloud:spring-cloud-starter-openfeign")
    implementation("net.devh:grpc-client-spring-boot-starter:3.1.0.RELEASE")

    implementation("org.springframework.kafka:spring-kafka")
}