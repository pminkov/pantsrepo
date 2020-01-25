/*
  Exercises from "Scala for the impatient"
*/
package concurrency 

import com.twitter.util.{Future, Await}
import concurrency.Funcs._

/*
  Exercise 17.5: Write a function that receives a sequence of futures
  and returns a future that eventually yields a sequence of all results.
*/
object JoinFutures extends CanRun {
  def joinFutures[T](futures: List[Future[T]]): Future[List[T]] = {
    if (futures.length == 1) {
      futures.head.map(x => List(x))
    } else {
      futures.head.flatMap(v => joinFutures(futures.tail).map(li => li :+ v))
    }
  }

  def run(): Unit = {
    val f1 = intToString(55)
    val f2 = stars(6)

    val futures: List[Future[String]] = List(f1, f2)
    val joined: Future[List[String]] = joinFutures(futures)
    println(Await.result(joined))
  }
}
