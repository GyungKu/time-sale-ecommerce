dependencies {
    implementation(project(":user-service:user-domain"))
    implementation(project(":common:common-exception"))

    // 트랜잭션(@Transactional) 등 스프링 코어 기능 사용
    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.springframework:spring-tx")
}