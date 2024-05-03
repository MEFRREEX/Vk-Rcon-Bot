plugins {
    `java-library`
    `maven-publish`
    kotlin("jvm") version "1.9.21"
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

group = "com.mefrreex.vkbot"
description = "vkrconbot"
version = "1.5.0"
java.sourceCompatibility = JavaVersion.VERSION_17

repositories {
    mavenLocal()
    maven("https://repo1.maven.org/maven2")
    maven("https://repo.maven.apache.org/maven2/")
}

dependencies {
    api("com.vk.api:sdk:1.0.14")
    api("org.yaml:snakeyaml:1.21")
}

tasks.build {
    dependsOn(tasks.shadowJar)
}

tasks.withType<JavaCompile>() {
    options.encoding = "UTF-8"
}

tasks.withType<Javadoc>() {
    options.encoding = "UTF-8"
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