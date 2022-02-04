package core

import scala.io.StdIn
import scala.util.control.Breaks.{break, breakable}
import org.slf4j.{Logger, LoggerFactory}

object Boot extends Helpers {

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
        |5. Fill area: B 10 3
        |
        |""".stripMargin)

    breakable {
      while (true) {
        val inputTokens: List[String] = StdIn.readLine().split(" ").filter(_.nonEmpty).toList
        val command: String = inputTokens.head
        val coordinates: List[String] = inputTokens.tail
        val numberInputs = coordinates.length

        command match {
          case "C" =>
            if (numberInputs != 2) {
              logger.info("Number of inputs is not correct. Introduce values again (C 20 4).")
            } else if (!checkCoordinatesTypes(coordinates)) {
              logger.info("Some coordinates inputs are not integers.")
            } else {

              val width: Int = coordinates(0).toInt
              val height: Int = coordinates(1).toInt

              program.createCanvas(width, height)
              logger.info("Canvas has been created")
              program.displayCanvas()
            }

          case "L" =>
            if (isCanvasEmpty) {
              logger.info("Create canvas first. C 20 4")
            } else if (numberInputs != 4) {
              logger.info("Number of inputs is not correct. Introduce values again.")
            } else if (!checkCoordinatesTypes(coordinates)) {
              logger.info("Some coordinates inputs are not integers.")
            } else {

              val x1: Int = coordinates(0).toInt
              val y1: Int = coordinates(1).toInt
              val x2: Int = coordinates(2).toInt
              val y2: Int = coordinates(3).toInt

              if (!isInsideCanvas(List(x1, x2), List(y1, y2), program.canvasWidth, program.canvasHeight)) {
                logger.info("Coordinates are out of the canvas.")
              } else if ((x1 != x2) && (y1 != y2)) {
                logger.info("Only horizontal and vertical lines are supported.")
              } else if (x2 < x1) {
                logger.info("x1 < x2. Horizontal coordinates are swapped.")
              } else if (y2 < y1) {
                logger.info("y2 < y1. Vertical coordinates are swapped.")
              } else if (y1 == y2) {
                program.drawHorizontalLine(x1, x2, y1)
                logger.info("Horizontal line has been drawn.")
                program.displayCanvas()
              } else {
                program.drawVerticalLine(x1, x2, y1, y2)
                logger.info("Vertical line has been drawn.")
                program.displayCanvas()
              }
            }

          case "R" =>
            if (isCanvasEmpty) {
              logger.info("Create canvas first. C 20 4")
            } else if (numberInputs != 4) {
              logger.info("Inputs are not correct. Introduce values again (R 14 1 18 3).")
            } else if (!checkCoordinatesTypes(coordinates)) {
              logger.info("Some coordinates inputs are not integers.")
            } else {

              val x1: Int = coordinates(0).toInt
              val y1: Int = coordinates(1).toInt
              val x2: Int = coordinates(2).toInt
              val y2: Int = coordinates(3).toInt

              if (!isInsideCanvas(List(x1, x2), List(y1, y2), program.canvasWidth, program.canvasHeight)) {
                logger.info("Coordinates are out of the canvas.")
              } else if (x2 < x1) {
                logger.info("x1 < x2. Horizontal coordinates are swapped.")
              } else if (y2 < y1) {
                logger.info("y2 < y1. Vertical coordinates are swapped.")
              }
              else {
                program.drawSquare(x1, x2, y1, y2)
                logger.info("Square has been drawn")
                program.displayCanvas()
              }
            }

          case "B" =>
            if (isCanvasEmpty) {
              logger.info("Create canvas first. C 20 4")
            } else if (!checkCoordinatesTypes(coordinates.slice(0, 1))) {
              logger.info("Some coordinates inputs are not integers.")
            } else if (numberInputs != 3) {
              logger.info("Number of inputs is not correct. Introduce values again.")
            } else {

              val x: Int = coordinates(0).toInt
              val y: Int = coordinates(1).toInt
              val color: String = coordinates(2)

              if (!isInsideCanvas(List(x), List(y), program.canvasWidth, program.canvasHeight)) {
                logger.info("Choose an initial point within the Canvas.")
              } else if (isAlreadyColored(x, y, color)) {
                logger.info("Choose an initial point without color.")
              } else {
                program.fillingArea(x, y, color)
                program.displayCanvas()
              }
            }

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
