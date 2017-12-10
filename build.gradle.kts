import org.gradle.jvm.tasks.Jar
import org.gradle.script.lang.kotlin.KotlinBuildScript
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

group = "com.athaydes.rawhttp"
version = "1.0"

plugins {
    kotlin("jvm") version "1.2.0"
}

val kotlin_version: String by extra

repositories {
    mavenCentral()
}

dependencies {
    compile(kotlin("stdlib"))
    compile("com.athaydes.rawhttp:rawhttp-core:1.1.0")
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

tasks {
    task("fatJar", type = Jar::class) {
        baseName = "${project.name}-all-deps"
        manifest {
            attributes(mapOf(
                    "Implementation-Title" to "RawHTTP-Tutorial",
                    "Main-Class" to "ExamplesKt"
            ))
        }
        from(configurations.runtime.map({ if (it.isDirectory) it else zipTree(it) }))
        with(tasks["jar"] as CopySpec)
    }
}
