plugins {
    kotlin("jvm") version "1.9.21"
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

dependencies {
    api("com.github.MEFRREEX:configuration:main-SNAPSHOT")
    api(project(":api"))
}

tasks.withType<Jar> {
    archiveFileName.set("Vk-Rcon-Bot-${project.version}.jar")
    manifest {
        attributes["Main-Class"] = "com.mefrreex.vkbot.BootstrapKt"
    }
}

kotlin {
    jvmToolchain(17)
}

tasks.build {
    dependsOn(tasks.shadowJar)
}