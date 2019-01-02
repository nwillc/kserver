import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val cliktVersion = "1.6.0"
val coverageThreshold = 0.98
val jacocoToolVersion = "0.8.2"
val ktorVersion = "1.0.1"
val tinyLogVersion = "1.3.5"
val versionsPluginVersion = "2.2.0"

plugins {
    application
    jacoco
    kotlin("jvm") version "1.3.11"
    id("com.github.nwillc.vplugin") version "2.3.0"
    id("io.gitlab.arturbosch.detekt") version "1.0.0.RC9.2"
    id("com.github.ngyewch.git-version") version "0.2"
    id("org.jmailen.kotlinter") version "1.20.1"
}

group = "com.github.nwillc"
version = gitVersion.gitVersionInfo.gitVersionName.substring(1)

logger.lifecycle("${project.name} $version")

repositories {
    jcenter()
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation("io.ktor:ktor-server-core:$ktorVersion")
    implementation("io.ktor:ktor-server-netty:$ktorVersion")
    implementation("io.ktor:ktor-html-builder:$ktorVersion")
    implementation("com.github.nwillc:ksvg:$versionsPluginVersion")
    implementation("com.github.ajalt:clikt:$cliktVersion")
    runtime("org.tinylog:slf4j-binding:$tinyLogVersion")
}

application {
  mainClassName = "com.github.nwillc.kserver.MainKt"
}

detekt {
    input = files("src/main/kotlin")
    filters = ".*/build/.*"
}

gitVersion {
    gitTagPrefix = "v"
}

jacoco {
    toolVersion = jacocoToolVersion
}

tasks {
    named<Jar>("jar") {
        manifest.attributes["Main-Class"] = "com.github.nwillc.kserver.ServerKt"
        from(Callable { configurations["runtimeClasspath"].map { if (it.isDirectory) it else zipTree(it) } })
    }
    withType<JacocoReport> {
        dependsOn("test")
        reports {
            xml.apply {
                isEnabled = true
            }
            html.apply {
                isEnabled = true
            }
        }
    }
    withType<KotlinCompile> {
        kotlinOptions.jvmTarget = JavaVersion.VERSION_1_8.toString()
    }
}
