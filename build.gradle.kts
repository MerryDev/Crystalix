plugins {
    id("java")
    alias(libs.plugins.shadow)
    alias(libs.plugins.resourceFactory)
    alias(libs.plugins.runPaper)
}

group = "net.crystalix"
version = project.property("version") as String

repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/")
    maven("https://libraries.minecraft.net")
}

dependencies {
    compileOnly(libs.paper)
    compileOnly(libs.brigadier)
    implementation(libs.bundles.cloud)
    implementation(libs.jacksonbukkit)
}

tasks {
    shadowJar {
        val mapping = mapOf(
            "org.incendo.cloud" to "cloud",
            "de.eldoria.jacksonbukkit" to "jacksonbukkit"
        )
        val base = "net.crystalix.teleport.libs"
        for ((pattern, name) in mapping) relocate(pattern, "$base.$name")
    }

    build {
        dependsOn(shadowJar)
    }

    // Testserver in der IDE mit der Version 1.21.11 laufen lassen
    runServer {
        minecraftVersion("1.21.11")
    }
}

// Automatische Generierung der plugin.yml
bukkitPluginYaml {
    main = "$group.teleport.TeleportPlugin"
    author = "Merry"
    apiVersion = "1.21"
}