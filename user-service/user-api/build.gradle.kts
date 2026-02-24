// api 모듈은 애플리케이션 진입점이므로 bootJar 활성화
tasks.bootJar { enabled = true }
tasks.jar { enabled = false }

dependencies {
    // Use Case 호출을 위해 application 모듈 참조 (infrastructure는 런타임에 주입됨)
    implementation(project(":user-application"))
    runtimeOnly(project(":user-infrastructure"))

    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-validation")
}