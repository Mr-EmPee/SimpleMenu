plugins {
  id("org.gradle.java-library")
  id("org.gradle.checkstyle")
  id("org.gradle.maven-publish")

  id("io.freefair.lombok") version "6.6.3"
}

group = "mr.empee"
version = "develop"

repositories {
  maven("https://oss.sonatype.org/content/repositories/snapshots/") //Spigot
  maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/") //Spigot
  maven("https://repo.dmulloy2.net/repository/public/") //ProcolLib

  mavenCentral()
}

dependencies {
  compileOnly("org.spigotmc:spigot-api:1.8.8-R0.1-SNAPSHOT")
  compileOnly("com.comphenix.protocol:ProtocolLib:5.0.0")

  testImplementation("org.junit.jupiter:junit-jupiter-api:5.10.0")
  testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.10.0")
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
  javadoc {
    options.encoding = Charsets.UTF_8.name()
  }

  processResources {
    filteringCharset = Charsets.UTF_8.name()
  }

  test {
    useJUnitPlatform()
  }

  java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11

    withSourcesJar()
    withJavadocJar()

    toolchain {
      languageVersion.set(JavaLanguageVersion.of(17))
    }
  }
}
