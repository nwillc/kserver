# kserver

A skeletal Kotlin microservice based on Ktor building with Gradle 5.0.

## Features

 - Build
   - Gradle 5.0 Kotlin DSL
   - Lint plugin
   - Detekt plugin
   - Jacoco plugin
   - No plugin fat jar
   - Git tag based versioning
 - Application
   - Ktor/Netty 
   - Clikt based command line option support
   - SLF4J tinylog logging

## Build

```bash
$ ./gradlew clean jar
```

## Run

```bash
$ java -jar build/libs/*.jar
```
