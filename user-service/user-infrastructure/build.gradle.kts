dependencies {
    implementation(project(":user-domain"))
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.postgresql:postgresql")
    runtimeOnly("com.h2database:h2")
}