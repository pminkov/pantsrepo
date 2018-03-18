/*
  Exercises from "Scala for the impatient"
*/
package concurrency 

import com.twitter.util.{Future, Await}
import concurrency.Funcs._

/*
  Exercise 17.3: Repeat the preceding exercise for any
  sequence of functions of type T => Future[T]
*/
object Sequential {
  def runSequential[T](
      funcs: T => Future[T]*)(x: T): Future[T] = {
    if (funcs.length == 1) {
      funcs(0)(x)
    } else {
      val ret: Future[T] = 
        funcs(0)(x).flatMap(res => runSequential[T](funcs.tail:_*)(res))
      ret
    }
  }

  def main(args: Array[String]): Unit = {
    println("Sequential")
    // sf(5) = ((5 * 2) + 1) ** 2
    val sf: Int => Future[Int] = runSequential(double, incr, square)

    println(Await.result(sf(5)))
  }
}

