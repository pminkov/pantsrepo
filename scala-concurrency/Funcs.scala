package concurrency

import com.twitter.util.Future

object Funcs {
  def intToString(x: Int): Future[String] = {
    Future {
      x.toString
    }
  }

  def stars(x: Int): Future[String] = {
    Future {
      "*" * x
    }
  }

  def firstLetter(x: String): Future[Char] = {
    Future {
      x(0)
    }
  }

  def square(x: Int): Future[Int] = {
    Future {
      x * x
    }
  }

  def double(x: Int): Future[Int] = {
    Future {
      2 * x
    }
  }

  def incr(x: Int): Future[Int] = {
    Future {
      x + 1
    }
  }
}
