plugins {
    id("org.springframework.boot")
}

dependencies {
    implementation(project(":product-service:product-application"))
    implementation(project(":product-service:product-domain"))
    implementation(project(":product-service:product-infrastructure"))
    implementation(project(":common:common-proto"))

    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("net.devh:grpc-server-spring-boot-starter:3.1.0.RELEASE")
}