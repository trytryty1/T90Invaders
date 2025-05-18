import org.gradle.internal.os.OperatingSystem

plugins {
    application
    java
}

application {
    mainClass.set("game_states.GameStates") // Replace with your actual main class
}

// Define nativePath depending on the OS
val nativePath = when {
    OperatingSystem.current().isLinux -> "libs/natives-linux"
    OperatingSystem.current().isWindows -> "libs/natives-windows"
    OperatingSystem.current().isMacOsX -> "libs/natives-macos"
    else -> throw GradleException("Unsupported OS")
}

// Add Slick2D and LWJGL jars
dependencies {
    implementation(files("libs/slick.jar"))
    implementation(files("libs/lwjgl.jar"))
    implementation(files("libs/lwjgl_util.jar"))
    implementation(files("libs/jorbis-0.0.15.jar"))
    implementation(files("libs/jogg-0.0.7.jar"))
}

// Pass -Djava.library.path to JVM when running
tasks.named<JavaExec>("run") {
    jvmArgs = listOf("-Djava.library.path=$nativePath")
}
