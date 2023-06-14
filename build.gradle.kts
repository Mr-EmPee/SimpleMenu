plugins {
  id("org.gradle.java-library")
  id("org.gradle.checkstyle")
  id("maven-publish")

  id("io.freefair.lombok") version "6.6.3"
  id("com.github.johnrengelman.shadow") version "8.1.0"
}

group = "com.github.Mr-EmPee"
version = "0.0.1"
var basePackage = "ml.empee.simplemenu"

repositories {
  maven("https://jitpack.io")
  maven("https://oss.sonatype.org/content/repositories/snapshots/")
  maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
  mavenCentral()
}

dependencies {
  compileOnly("org.spigotmc:spigot-api:1.8.8-R0.1-SNAPSHOT")
}

java {
  withSourcesJar()
  withJavadocJar()

  sourceCompatibility = JavaVersion.VERSION_1_8
  targetCompatibility = JavaVersion.VERSION_1_8
}

publishing {
  publications {
    create<MavenPublication>("maven") {
      from(components["java"])
    }
  }
}

checkstyle {
  toolVersion = "10.10.0"
  configFile = file("$projectDir/checkstyle.xml")
}

tasks {
  shadowJar {
    isEnableRelocation = true
    relocationPrefix = "$basePackage.relocations"
  }

  javadoc {
    options.encoding = Charsets.UTF_8.name()
  }

  processResources {
    filteringCharset = Charsets.UTF_8.name()
  }

  compileJava {
    options.encoding = Charsets.UTF_8.name()
    options.release.set(17)
  }
}
