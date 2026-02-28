subprojects {
    dependencies {
        if (project.name != "user-infrastructure") {
            implementation(project(":common:common-exception"))
        }
    }
}