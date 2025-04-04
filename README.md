# Kotlin Sequence Builder

## Why?

Kotlin Sequences use `kotlin.coroutines` inside sequence builder:

```kotlin
val sequence = sequence {
  var n = 0
  while (true) {
    yield(i++) // This is SUSPEND function
  }
}
```

However, you can easily access sequence itself without any suspence:

```kotlin
fun main() {
  val sequence = /* ... */
  sequence.forEach { element -> // forEach is NOT SUSPEND function
    println(element)
  }
}
```

So, this breaks Kotlin, doesn't it? How can we call suspend function `emit`
from non-suspend function `forEach`?

And we can't even use `launch` to start coroutine since it lives inside
`kotlinx.coroutines`, while sequence is inside the kotlin stdlib.

How can we even use coroutines in Kotlin **without** `kotlinx.coroutines`?

This repo is the answer to this question. It implements `MySequence` class which
is a simplified implementation of how the real sequence works.

## Where?

Basically this file: [MySequence.kt](src/main/kotlin/MySequence.kt)

## Run

Use Gradle for this: `./gradlew run`

This will run [Main.kt](src/main/kotlin/Main.kt)

