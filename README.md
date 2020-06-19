Downloading
==========
Download the source code with

    $ git clone https://github.com/PureGero/JustPlots.git

A folder will be created called `JustPlots` with the source code inside

IDEs
====
Setup an Eclipse workspace with

    $ ./gradlew eclipse

Setup an IntelliJ workspace with

    $ ./graldew idea

Compiling
=========
Compile the source with gradle:

    $ ./gradlew build

Or on widows:

    > gradlew build

The plugin jar will be found in `build/libs`. Enjoy!

JustPlots as a dependency
=========================
Add the following into your build.gradle:

```
repositories {
  maven {
    url "https://raw.githubusercontent.com/PureGero/JustPlots/repository/"
  }
}

dependencies {
  compileOnly "just.plots:justplots:0.9.1"
}
```