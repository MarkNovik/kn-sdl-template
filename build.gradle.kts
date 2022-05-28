plugins {
    kotlin("multiplatform") version "1.7.0-RC"
}

group = "me.mark"
version = "1.0"

repositories {
    mavenCentral()
}

kotlin {
    val hostOs = System.getProperty("os.name")
    val isMingwX64 = hostOs.startsWith("Windows")
    val nativeTarget = when {
        hostOs == "Mac OS X" -> macosX64("native")
        hostOs == "Linux" -> linuxX64("native")
        isMingwX64 -> mingwX64("native")
        else -> throw GradleException("Host OS is not supported in Kotlin/Native.")
    }

    nativeTarget.apply {
        binaries {
            compilations.getByName("main") {
                kotlinOptions {
                    freeCompilerArgs += "-Xbinary=memoryModel=experimental"
                }
                cinterops {
                    val sdl by creating {
                        defFile(project.file("src/nativeInterop/cinterop/sdl.def"))
                    }
                }
            }
            executable {
                entryPoint = "main"
            }
        }
    }
    sourceSets {
        val nativeMain by getting
        val nativeTest by getting
    }
}
