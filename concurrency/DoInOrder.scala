/*
  Exercises from "Scala for the impatient"
*/
package concurrency 

import com.twitter.util.{Future, Await}
import concurrency.Funcs._

/* Exercise 17.2:

Write a function doInOrder that, given two functions f: T=>Future[U] and
g: U=>Future[V], produces a function T=>Future[V] that, for a given t,
eventually yields g(f(t))
*/
object DoInOrder {
  def doInOrder[T, U, V](
      f: T => Future[U],
      g: U => Future[V]): T => Future[V] = {
    
    def retf(x: T): Future[V] = {
      f(x).flatMap(u => g(u))
    }

    retf(_)
  }

  // Through curry-ing.
  def doInOrder2[T, U, V](
      f: T => Future[U],
      g: U => Future[V])(x: T): Future[V] = {
    f(x).flatMap(u => g(u))
  }



  def main(args: Array[String]): Unit = {
    println("DoInOrder")

    val f: Int => Future[String] = intToString
    val g: String => Future[Char] = firstLetter

    val df: Int => Future[Char] = doInOrder(f, g)
    println(Await.result(df(1345)))

    val df2: Int => Future[Char] = doInOrder2(f, g)
    println(Await.result(df2(325)))
  }
}


