import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val coverageThreshold = 0.98
val jacocoToolVersion = "0.8.2"
val ktorVersion = "1.0.1"

plugins {
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
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation("io.ktor:ktor-server-core:$ktorVersion")
    implementation("io.ktor:ktor-server-netty:$ktorVersion")
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
    jacocoTestCoverageVerification {
        violationRules {
            rule {
                limit {
                    minimum = coverageThreshold.toBigDecimal()
                }
            }
        }
    }
    named("check") {
        dependsOn(":jacocoTestCoverageVerification")
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