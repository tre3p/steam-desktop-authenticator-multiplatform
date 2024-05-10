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
        }
    }
}


compose.desktop {
    application {
        mainClass = "com.tre3p.sdamp.MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Exe, TargetFormat.Deb)
            packageName = "SDAMP"
            packageVersion = "2.0.0"

            macOS {
                iconFile.set(project.file("icons/macos.icns"))
            }

            windows {
                iconFile.set(project.file("icons/win.ico"))
            }

            linux {
                iconFile.set(project.file("icons/linux.png"))
            }
        }
    }
}
