package me.y9san9.mysequence

fun main() {
  val sequence = mySequence {
    var number = 0
    while (true) {
      yield(number++)
    }
  }
  println(sequence.take(5).toList())
}
