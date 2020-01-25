/*
  Solutions to exercises from the "Futures" chapter of
  "Scala for the impatient"
*/

package concurrency

trait CanRun {
  def run(): Unit
}

object Runner {
  val classes = List[CanRun](
    DoInOrder, // 2
    Sequential, // 3
    DoTogether, // 4
    JoinFutures // 5
  )

  def main(args: Array[String]): Unit = {
    if (args.length != 1) {
      println("Run as ./pants run target -- className")
    } else {
      val className = args(0)

      classes
        .find(kl => kl.getClass.getName contains className)
        .foreach(kl => {
          println("Running: " + className)

          kl.run()
        })
    }
  }
}


