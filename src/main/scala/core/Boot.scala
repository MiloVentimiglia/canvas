package core

import scala.io.StdIn
import core.drawingservice.DrawingProgram
import core.fillareaservice.FillAreaProgram
import scala.util.control.Breaks.breakable
import org.slf4j.{Logger, LoggerFactory}

object Boot extends Controller with Implicits {

  def main(args: Array[String]): Unit = {

    lazy val logger: Logger = LoggerFactory.getLogger(this.getClass)
    implicit val ProgramService: DrawingProgram = new DrawingProgram()
    implicit val FillAreaServices: FillAreaProgram = new FillAreaProgram(ProgramService)

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
      while (true) controller(StdIn.readLine())
    }
  }
}
