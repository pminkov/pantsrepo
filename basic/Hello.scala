package basic

object HelloWorld {
  def main(args: Array[String]): Unit = {
    println("Hello, " + System.getProperty("user.name"))
  }
}
