# RawHTTP Blog Post examples

This repository contains the source code used in the RawHTTP blog post:

[Announcing RawHTTP - A JVM library for handling raw HTTP](https://sites.google.com/a/athaydes.com/renato-athaydes/posts/announcingrawhttp-ajvmlibraryforhandlingrawhttp).

## Building

```
gradlew fatJar
```

## Running

```
java -jar build/libs/rawhttp-tutorial-all-deps-1.0.jar basic
```

The above command runs the `basic` example.

Options are:

* basic
* raw
* client
* fix
* response
* file

See the [source code](src/main/kotlin/examples.kt) for details.