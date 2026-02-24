tasks.bootJar { enabled = false }
tasks.jar { enabled = true }

dependencies {
    implementation(project(":user-domain"))

    // 트랜잭션(@Transactional) 등 스프링 코어 기능 사용
    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.springframework:spring-tx")
}