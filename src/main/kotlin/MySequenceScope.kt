package me.y9san9.mysequence

interface MySequenceScope<T> {
  suspend fun yield(element: T)
}

fun <T> mySequence(
  block: suspend MySequenceScope<T>.() -> Unit
): MySequence<T> {
  return MySequence<T>(block)
}

