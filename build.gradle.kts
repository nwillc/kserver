import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val coverageThreshold = 0.98
val jacocoToolVersion = "0.8.2"
val ktorVersion = "1.0.1"
val logbackVersion = "1.2.3"

plugins {
    application
    jacoco
    kotlin("jvm") version "1.3.11"
    id("com.github.nwillc.vplugin") version "2.3.0"
    id("io.gitlab.arturbosch.detekt") version "1.0.0.RC9.2"
    id("com.github.ngyewch.git-version") version "0.2"
    id("org.jmailen.kotlinter") version "1.20.1"
    id("com.github.johnrengelman.plugin-shadow") version "2.0.3"
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
  //  implementation("ch.qos.logback:logback-classic:$logbackVersion")
    implementation("com.github.nwillc:ksvg:2.1.8")
    runtime("org.slf4j:slf4j-jdk14:1.7.5")
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
        manifest.attributes["Main-Class"] = "com.github.nwillc.kserver.MainKt"
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
        kotlinOptions.jvmTarget = "1.8"
    }

}