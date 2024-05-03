import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.jetbrainsCompose)
}

group = "com.tre3p"
version = "0.1.0"

kotlin {
    jvm("desktop")
    
    sourceSets {
        val desktopMain by getting
        desktopMain.dependencies {
            implementation(compose.desktop.currentOs)
            implementation(project(":sdamp-api"))
            implementation("com.darkrockstudios:mpfilepicker:3.1.0")
        }
    }
}


compose.desktop {
    application {
        mainClass = "com.tre3p.sdamp.MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Exe, TargetFormat.Deb)
            packageName = "SDAMP"
            packageVersion = "1.0.0"
        }
    }
}
