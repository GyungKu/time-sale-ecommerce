// domain 모듈은 실행 가능한 애플리케이션이 아니므로 bootJar 비활성화
tasks.bootJar { enabled = false }
tasks.jar { enabled = true }

dependencies {
    // Entity 매핑을 위한 JPA
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
}