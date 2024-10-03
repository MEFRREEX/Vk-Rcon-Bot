plugins {
    `java-library`
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

java.sourceCompatibility = JavaVersion.VERSION_17

tasks.build {
    dependsOn(tasks.shadowJar)
}

allprojects {
    group = "com.mefrreex.vkbot"
    description = "vkrconbot"
    version = "1.5.1"
}

subprojects {

    apply {
        plugin("java-library")
    }

    repositories {
        mavenLocal()
        maven("https://repo1.maven.org/maven2")
        maven("https://repo.maven.apache.org/maven2/")
        maven("https://jitpack.io")
    }

    dependencies {
        api("com.vk.api:sdk:1.0.14")
    }

    tasks.withType<JavaCompile> {
        options.encoding = "UTF-8"
    }

    tasks.withType<Javadoc> {
        options.encoding = "UTF-8"
    }
}