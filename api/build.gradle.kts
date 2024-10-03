plugins {
    kotlin("jvm") version "1.9.21"
}

tasks.withType<Jar> {
    archiveFileName.set("Vk-Rcon-Bot-API-${project.version}.jar")
}

kotlin {
    jvmToolchain(17)
}