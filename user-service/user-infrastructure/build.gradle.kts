dependencies {
    // 도메인(Repository 인터페이스 등)과 애플리케이션 계층 참조
    implementation(project(":user-service:user-domain"))
    implementation(project(":user-service:user-application"))

    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.postgresql:postgresql")
    runtimeOnly("com.h2database:h2")
    implementation("org.springframework.security:spring-security-crypto")
}