package me.y9san9.mysequence

import kotlin.coroutines.Continuation
import kotlin.coroutines.EmptyCoroutineContext
import kotlin.coroutines.resume
import kotlin.coroutines.startCoroutine
import kotlin.coroutines.suspendCoroutine

class MySequence<out T>(
  private val block: suspend MySequenceScope<T>.() -> Unit,
) : Sequence<T> {
  override fun iterator(): Iterator<T> {
    return MyIterator(block)
  }

  private class MyIterator<T>(
    private var block: (suspend MySequenceScope<T>.() -> Unit)? = null,
  ) : Iterator<T>, MySequenceScope<T>, Continuation<Unit> {
    private var element: T? = null
    private var continuation: Continuation<Unit>? = null
    private var coroutineFinished = false

    override fun hasNext(): Boolean {
      prepare()
      return !coroutineFinished
    }

    override fun next(): T {
      prepare()
      ready = false
      @Suppress("UNCHECKED_CAST")
      return element as T
    }

    private var ready = false

    private fun prepare() {
      if (ready) return
      continuation?.resume(Unit)
      block?.startCoroutine(this, this)
      block = null
      ready = true
    }

    override suspend fun yield(element: T) {
      this.element = element
      stop()
    }

    private suspend fun stop() {
      suspendCoroutine { continuation ->
        this.continuation = continuation
      }
    }

    override val context = EmptyCoroutineContext

    override fun resumeWith(result: Result<Unit>) {
      coroutineFinished = true
    }
  }
}

