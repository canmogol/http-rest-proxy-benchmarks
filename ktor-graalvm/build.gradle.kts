plugins {
    application
    kotlin("jvm") version "1.7.20"
    id("org.graalvm.buildtools.native") version "0.9.11"
}

group = "io.ktor"
version = "0.0.1"
application {
    mainClass.set("io.ktorgraal.ApplicationKt")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("ch.qos.logback:logback-classic:1.2.11")
    implementation("io.ktor:ktor-server-core-jvm:2.2.1")
    implementation("io.ktor:ktor-server-cio-jvm:2.2.1")
}

graalvmNative {
    binaries {
        named("main") {
            fallback.set(false)
            verbose.set(true)

            buildArgs.add("--initialize-at-build-time=io.ktor,kotlin,ch.qos.logback,org.slf4j.impl,org.slf4j")

            buildArgs.add("-H:+InstallExitHandlers")
            buildArgs.add("-H:+ReportUnsupportedElementsAtRuntime")
            buildArgs.add("-H:+ReportExceptionStackTraces")

            imageName.set("graal-server")
        }
    }
}