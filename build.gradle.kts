plugins {
  id("org.gradle.java-library")
  id("org.gradle.checkstyle")
  id("maven-publish")

  id("io.freefair.lombok") version "6.6.3"
  id("com.github.johnrengelman.shadow") version "8.1.0"
}

group = "com.github.Mr-EmPee"
version = "0.0.7"
var basePackage = "ml.empee.simplemenu"

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

java {
  withSourcesJar()
  withJavadocJar()

  toolchain {
    languageVersion.set(JavaLanguageVersion.of(17))
  }
}

tasks.named<Test>("test") {
  useJUnitPlatform()
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

  java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
  }
}
