package core

import core.model._
import core.DrawingService.DrawingProgram
import scala.io.StdIn
import scala.util.control.Breaks.{break, breakable}
import org.slf4j.{Logger, LoggerFactory}

object App extends Controller {

  def main(args: Array[String]): Unit = {

    lazy val logger: Logger = LoggerFactory.getLogger(this.getClass)
    implicit val program: DrawingProgram = new DrawingProgram()

    logger.info(
      """
        |
        |  ___    ____    __    __ __    __  ____    _____
        | / __\  / __ \  /  \  / / \ \  / / / __ \  / ___/
        |/ /__  / /_/ / / /\ \/ /   \ \/ / / /_/ / (__  )
        |\___/ /_/ /_/ /_/  \__/     \__/ /_/ /_/ /____/
        |
        |By Hugo
        |
        |Introduce the following commands:
        |1. Create Canvas: C 20 4
        |2. Draw Horizontal Line: L 1 2 6 2
        |3. Draw Vertical Line: L 6 3 6 4
        |4. Draw Square: R 14 1 18 3
        |5. Fill area: B 10 3 o
        |
        |""".stripMargin)

    breakable {
      while (true) {
        val inputTokens: List[String] = StdIn.readLine().split(" ").filter(_.nonEmpty).toList
        val command: String = inputTokens.head
        val coordinates: List[String] = inputTokens.tail

        implicit val numberInputs: Int = coordinates.length
        lazy val in1: String = coordinates(0)
        lazy val in2: String = coordinates(1)
        lazy val in3: String = coordinates(2)
        lazy val in4: String = coordinates(3)

        command match {
          case "C" =>
            CreateCanvas(in1, in2).safeRun
          case "L" =>
            DrawLine(in1, in2, in3, in4).safeRun
          case "R" =>
            DrawSquare(in1, in2, in3, in4).safeRun
          case "B" =>
            FillArea(in1, in2, in3).safeRun
          case "Q" =>
            logger.info("Gracefully closing program.")
            break()
          case _ =>
            logger.info("Command is not implemented.")
        }
      }
    }
  }
}
