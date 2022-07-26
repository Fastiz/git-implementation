import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.7.10"
    application
}

group = "me.fastiz"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("org.jetbrains.kotlin:kotlin-test:1.7.10")
    testImplementation("io.mockk:mockk:1.12.4")
    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.7.10")
}

tasks.test {
    useJUnit()
}

tasks.withType<KotlinCompile>() {
    kotlinOptions.jvmTarget = "18"
}

application {
    mainClass.set("MainKt")
}