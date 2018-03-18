package concurrency

import com.twitter.util.{Future, Await}

object HelloConcurrency {
  def main(args: Array[String]): Unit = {
    println("Wait for it...")
    val f = Future {
      Thread.sleep(3000)
      println("Hello, " + System.getProperty("user.name"))
    }

    Await.ready(f)
  }
}
