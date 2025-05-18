import org.gradle.internal.os.OperatingSystem

plugins {
    application
    java
}

application {
    mainClass.set("game_states.GameStates")
}

val nativeDirs = mapOf(
    "linux" to "libs/natives-linux",
    "windows" to "libs/natives-windows",
    "macos" to "libs/natives-macos"
)

val osList = listOf("linux", "windows", "macos")

dependencies {
    implementation(files("libs/slick.jar"))
    implementation(files("libs/lwjgl.jar"))
    implementation(files("libs/lwjgl_util.jar"))
    implementation(files("libs/jorbis-0.0.15.jar"))
    implementation(files("libs/jogg-0.0.7.jar"))
}

// Main build task for all OSes
tasks.register("buildAll") {
    group = "distribution"
    dependsOn("jar")

    doLast {
        osList.forEach { os ->
            val outputDir = layout.buildDirectory.dir("dist/$os").get().asFile
            outputDir.mkdirs()

            val jarFile = tasks.named<Jar>("jar").get().archiveFile.get().asFile
            jarFile.copyTo(outputDir.resolve("T90Invaders.jar"), overwrite = true)

            // Copy dependencies
            listOf(
                "slick.jar",
                "lwjgl.jar",
                "lwjgl_util.jar",
                "jorbis-0.0.15.jar",
                "jogg-0.0.7.jar"
            ).forEach { file ->
                file("libs/$file").copyTo(outputDir.resolve(file), overwrite = true)
            }

            // Copy natives
            val nativePath = nativeDirs[os] ?: throw GradleException("Missing native path for $os")
            copy {
                from(file(nativePath))
                into(outputDir)
            }

            // Copy assets
            val assets = file("assets")
            if (assets.exists()) {
                copy {
                    from(assets)
                    into(File(outputDir, "assets"))
                }
            }

            // Create launcher
            when (os) {
                "linux", "macos" -> {
                    val script = outputDir.resolve("run.sh")
                    script.writeText(
                        """
                        #!/bin/bash
                        java -Djava.library.path=. -cp "T90Invaders.jar:slick.jar:lwjgl.jar:lwjgl_util.jar:jorbis-0.0.15.jar:jogg-0.0.7.jar" ${application.mainClass.get()}
                        """.trimIndent()
                    )
                    script.setExecutable(true)
                }

                "windows" -> {
                    outputDir.resolve("run.bat").writeText(
                        """
                        java -Djava.library.path=. -cp "T90Invaders.jar;slick.jar;lwjgl.jar;lwjgl_util.jar;jorbis-0.0.15.jar;jogg-0.0.7.jar" ${application.mainClass.get()}
                        """.trimIndent()
                    )
                }
            }

            println("Packaged for $os -> ${outputDir.absolutePath}")
        }
    }
}
