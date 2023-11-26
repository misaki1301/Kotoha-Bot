import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "2.7.15"
    id("io.spring.dependency-management") version "1.0.15.RELEASE"
    id("org.jetbrains.kotlin.jvm") version "1.9.0"
    //kotlin("jvm") version "1.9"
    //kotlin("kapt") version "1.9"
    kotlin("plugin.spring") version "1.6.21"
    kotlin("plugin.jpa") version "1.6.21"
}

java {
    sourceCompatibility = JavaVersion.VERSION_11
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

extra["springCloudVersion"] = "Hoxton.SR9"
group = "com.shibuyaxpress"
version = "0.0.1-SNAPSHOT"

repositories {
    mavenCentral()
    jcenter()
    maven("https://m2.dv8tion.net/releases")
    maven("https://jitpack.io")
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:1.9.0")
    implementation("org.springframework.boot:spring-boot-starter-data-rest")
    implementation("org.springframework.boot:spring-boot-starter-web")
    developmentOnly("org.springframework.boot:spring-boot-devtools")
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.0")
    //implementation("javax.xml.bind:jaxb-api:2.3.1")
    // kotlin
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    // MYSQL
    // runtimeOnly("mysql:mysql-connector-java")
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    //saucenao bot
    implementation("com.github.insanusmokrassar:SauceNaoAPI:0.4.4")
    //discord bot
    implementation("com.jagrosh:jda-utilities:3.0.5")
    implementation("net.dv8tion:JDA:4.4.1_353")
    //audio player ok
    implementation("com.github.walkyst:lavaplayer-fork:1.4.3")
    // retrofit
    implementation("com.squareup.retrofit2:retrofit:2.6.4")
    implementation("com.squareup.okhttp3:okhttp:3.14.8")
    //moshi dependencies
    implementation("com.squareup.moshi:moshi-kotlin:1.13.0")
    //kapt( "com.squareup.moshi:moshi-kotlin-codegen:1.13.0")
    /*//KTOR
    implementation("io.ktor:ktor-client-apache:1.2.4")*/
    // Kotlin Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")
    implementation("com.squareup.retrofit2:converter-moshi:2.6.4")
    implementation("com.jakewharton.retrofit:retrofit2-kotlin-coroutines-adapter:0.9.2")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-json-org:2.15.2")
    annotationProcessor("com.fasterxml.jackson.core:jackson-annotations:2.15.2")
    implementation("com.fasterxml.jackson.core:jackson-core:2.15.2")

}


dependencyManagement {
    imports {
        mavenBom("org.springframework.cloud:spring-cloud-dependencies:${property("springCloudVersion")}")
    }
}


tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "11"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}