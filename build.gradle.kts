import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    id("java-library")
    id("com.github.johnrengelman.shadow") version "7.1.2"
    id("net.minecrell.plugin-yml.bukkit") version "0.5.1"
    id("xyz.jpenilla.run-paper") version "1.0.6"
}

group = "com.eternalcode.lobby"
version = "1.0.0-SNAPSHOT"

repositories {
    gradlePluginPortal()
    mavenCentral()
    maven { url = uri("https://papermc.io/repo/repository/maven-public/") }
    maven { url = uri("https://repo.panda-lang.org/releases") }
    maven { url = uri("https://repo.dmulloy2.net/repository/public/") }
    maven { url = uri("https://repo.extendedclip.com/content/repositories/placeholderapi/") }
    maven { url = uri("https://repo.simplix.dev/repository/simplixsoft-public/") }
    maven { url = uri("https://mvn.exceptionflug.de/repository/exceptionflug-public/") }
    maven { url = uri("https://repo.eternalcode.pl/releases") }
}

dependencies {
    // spigot api & paperlib
    compileOnly("org.spigotmc:spigot-api:1.18.2-R0.1-SNAPSHOT")
    api("io.papermc:paperlib:1.0.7")

    // Kyori
    implementation("net.kyori:adventure-platform-bukkit:4.1.0")
    implementation("net.kyori:adventure-text-minimessage:4.10.1")

    // LiteCommands
    implementation("dev.rollczi.litecommands:bukkit:2.0.0-pre15")

    // cdn configs
    implementation("net.dzikoysk:cdn:1.13.22")

    // expressible
    implementation("org.panda-lang:expressible:1.1.19")

    // triumph
    implementation("dev.triumphteam:triumph-gui:3.1.2")

    // PlaceholderAPI
    compileOnly("me.clip:placeholderapi:2.11.1")
}


tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

tasks {
    runServer {
        minecraftVersion("1.18.2")
    }
}

bukkit {
    main = "com.eternalcode.lobby.LobbyPlugin"
    apiVersion = "1.18"
    prefix = "EternalMC-Lobby"
    author = "vLucky"
    name = "EternalMC-Lobby"
    description = "A lobby plugin by EternalCodeTeam (vLucky)"
    version = "${project.version}"
    depend = listOf(
        "PlaceholderAPI"
    )
}


tasks.withType<ShadowJar> {
    archiveFileName.set("EternalLobby v${project.version} (MC 1.18.2).jar")

    exclude("org/intellij/lang/annotations/**")
    exclude("org/jetbrains/annotations/**")
    exclude("javax/**")
    exclude("META-INF/**")

    mergeServiceFiles()
    minimize()

    relocate("net.dzikoysk", "com.eternalcode.lobby.libs.net.dzikoysk")
    relocate("dev.rollczi", "com.eternalcode.lobby.libs.dev.rollczi")
    relocate("org.panda_lang", "com.eternalcode.lobby.libs.panda")
    relocate("panda", "com.eternalcode.vlucky.lobby.libs.panda")
    relocate("com.google.gson", "com.eternalcode.lobby.libs.com.google.gson")
    relocate("io.papermc.lib", "com.eternalcode.lobby.libs.io.papermc.lib")
    relocate("dev.triumphteam", "com.eternalcode.lobby.libs.dev.triumphteam")
    relocate("net.kyori", "com.eternalcode.lobby.libs.net.kyori")
}
