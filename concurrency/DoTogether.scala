package concurrency

import com.twitter.util.{Future, Await}
import concurrency.Funcs._

object DoTogether {
  def doTogether[T, U, V](
      f: T => Future[U],
      g: T => Future[V])(t: T): Future[(U, V)] = {
    val ret: Future[(U, V)] = for (u <- f(t); v <- g(t))
      yield (u, v)

    ret
  }

  def doTogether2[T, U, V](
      f: T => Future[U],
      g: T => Future[V])(t: T): Future[(U, V)] = {
    f(t).flatMap(u => g(t).map(v => (u, v)))
  }

  def main(args: Array[String]): Unit = {
    println(Await.result(doTogether(stars, square)(5)))
    println(Await.result(doTogether2(stars, square)(3)))
  }
}
