# programmierpraktikum-abschlussprojekt-the-avaders

[![Build Status](https://travis-ci.org/ProPra16/programmierpraktikum-abschlussprojekt-the-avaders.svg?branch=master)](https://travis-ci.org/ProPra16/programmierpraktikum-abschlussprojekt-the-avaders)

## Build Standalone Distribution

To create a standalone distribution as a zip file, run:

```sh
./gradlew distZip
```

The zip distribution is placed under `build/distributions`.

To run the program from the distribution, extract the zip file, and run the launch script for your system in the `bin` folder. On Windows, use the Batch script (`.bat` extension). On OS X, Linux, etc., use the shell script (no extension).

## Javadoc

Javadocs may be generated using Gradle:

```sh
./gradlew javadoc
```

The generated documentation can be found under `build/docs/javadoc`.
