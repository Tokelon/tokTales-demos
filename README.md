# tokTales Engine - Demos

> Contains demos and sample code for **[tokTales Engine](https://github.com/Tokelon/tokTales)**.

| Project Name | Description | Build |
| ------------ | ----------- | ----- |
| tokTales-helloworld | A simple demo that loads a font and renders "Hello World" as text. | ![tokTales-helloworld CI](https://github.com/Tokelon/tokTales-demos/workflows/tokTales-helloworld%20CI/badge.svg) |

## Building

The build system used is **[Gradle](https://docs.gradle.org/current/userguide/userguide.html)**.

### Prerequisites

- An internet connection for downloading Gradle and project dependencies
- **[Java Development Kit](https://jdk.java.net/)** version 8 or higher for running Gradle
- **[Android SDK](https://developer.android.com/studio)** when building Android projects

### Building a project

To build a demo project, navigate into the project directory and use the Gradle Wrapper to execute the `build` task.  
For example, to download and build the **tokTales-helloworld** project, run the following in a shell (Windows).

    git clone https://github.com/Tokelon/tokTales-demos.git
    cd tokTales-helloworld
    .\gradlew build

### Targeting a specific platform

If a project supports multiple platforms, you can choose to target a single platform by specifying a subproject path in the Gradle Wrapper command. This will cause only subprojects required for that platform to be built.  
For example, to build the **tokTales-helloworld** project for the Desktop platform, run the following in a shell (Windows).

    cd tokTales-helloworld
    .\gradlew :tokTales-helloworld-desktop:build

This can be useful since you are not required to have the tools for all platforms installed when you only need a specific one.

## Running

To run a demo project, you need to build the subprojects that are required for the platform you are targeting.  
See [Building Prerequisites](#prerequisites) for general requirements on building.

### Desktop

To run a demo project on the Desktop platform, use the Gradle Wrapper to execute the `run` task.  
For example, to run the **tokTales-helloworld** project on Desktop, run the following in a shell (Windows).

    cd tokTales-helloworld
    .\gradlew :tokTales-helloworld-desktop:run

Note that the `run` task will cause the project to be build if it was not built already.

### Android

To run a demo project on the Android platform, use the Android SDK tools or manually install the APK file, generated during the build, on the target device.
