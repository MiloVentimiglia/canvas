package core

import core.model._
import core.DrawingService.DrawingProgram
import core.FillAreaService.FillAreaProgram


trait Implicits {

  import Implicits._

  trait IOMessage[T] {
    def isInputCorrect: Boolean
    def safeRun: Unit
  }

  implicit class CreateCanvasSafe(val input: CreateCanvas)
                                 (implicit program: DrawingProgram) extends IOMessage[Feature] {
    implicit val args: List[String] = List(input.width, input.height)
    override def isInputCorrect: Boolean = isCoordinatesTypesCorrect && isCoordinatesPositiveInt

    override def safeRun: Unit = {
      if (isInputCorrect) {
        val width = input.width.toInt
        val height = input.height.toInt

        program.createCanvas(width, height)
        program.displayCanvas

      } else {
        println("Coordinates are not positive numerical values.")
      }
    }
  }


  implicit class DrawLineSafe(val input: DrawLine)
                             (implicit program: DrawingProgram) extends IOMessage[Feature] {
    implicit val args: List[String] = List(input.x1, input.y1, input.x2, input.y2)
    override def isInputCorrect: Boolean =
      (!isCanvasEmpty) && (!isDiagonalLine) && isCoordinatesTypesCorrect && isCoordinatesPositiveInt

    override def safeRun: Unit = {
      if (isInputCorrect) {
        val x1: Int = input.x1.toInt
        val y1: Int = input.y1.toInt
        val x2: Int = input.x2.toInt
        val y2: Int = input.y2.toInt

        if ((y1 == y2) && isXCoordinatesOrdered) {
          program.drawHorizontalLine(x1, y1, x2)
          program.displayCanvas
        } else if ((x1 == x2) && isYCoordinatesOrdered) {
          program.drawVerticalLine(x1, y1, x2, y2)
          program.displayCanvas
        } else {
          println("Arguments are swapped.")
        }
      } else {
        println("Arguments are not correctly introduced or canvas does not exist.")
      }
    }
  }


  implicit class DrawSquareSafe(val input: DrawSquare)
                               (implicit program: DrawingProgram) extends IOMessage[Feature] {
    implicit val args: List[String] = List(input.x1, input.y1, input.x2, input.y2)
    override def isInputCorrect: Boolean =
      (!isCanvasEmpty) && isXCoordinatesOrdered && isYCoordinatesOrdered && isCoordinatesPositiveInt

    override def safeRun: Unit = {
      if (isInputCorrect) {
        val x1: Int = input.x1.toInt
        val y1: Int = input.y1.toInt
        val x2: Int = input.x2.toInt
        val y2: Int = input.y2.toInt

        program.drawSquare(x1, y1, x2, y2)
        program.displayCanvas
      } else {
        println("Arguments are not correctly introduced or canvas does not exist.")
      }
    }
  }


  implicit class FillAreaSafe(val input: FillArea)
                             (implicit program: DrawingProgram, fillService: FillAreaProgram) extends IOMessage[Feature] {
    implicit val coordinates: List[String]  = List(input.x, input.y)
    implicit val colour: String  = input.colour

    override def isInputCorrect: Boolean =
      !isCanvasEmpty && isCoordinatesTypesCorrect && isCoordinatesPositiveInt && isColourCorrect && isStartNodeBlank

    override def safeRun: Unit = {
      if (isInputCorrect) {

        val x1: Int = input.x.toInt
        val y1: Int = input.y.toInt
        val colour: String = input.colour

        fillService.fillingArea(x1, y1, colour)
        program.displayCanvas
      } else {
        println("Arguments are not correctly introduced or canvas does not exist.")
      }
    }
  }
}

object Implicits {

  def isCanvasEmpty(implicit program: DrawingProgram): Boolean =
    program.canvas.isEmpty

  def isCoordinatesTypesCorrect(implicit inputs: List[String]): Boolean =
    inputs.map(_.safeToInt).forall(_.isDefined)

  def isCoordinatesPositiveInt(implicit inputs: List[String]): Boolean =
    inputs.map(_.safeToInt).map(x => x.getOrElse(0) > 0).reduce(_ && _)

  def isYCoordinatesOrdered(implicit inputs: List[String]): Boolean =
    try {inputs(3).toInt > inputs(1).toInt} catch {case _: Exception => false; case _ => true}

  def isXCoordinatesOrdered(implicit inputs: List[String]): Boolean =
    try {inputs(2).toInt > inputs(0).toInt} catch {case _: Exception => false; case _ => true}

  def isDiagonalLine(implicit inputs: List[String]): Boolean =
    inputs.to(Set).size == inputs.length

  def isColourCorrect(implicit colour: String): Boolean =
    colour.matches("[^x|-]")

  def isStartNodeBlank(implicit inputs: List[String], colour: String, program: DrawingProgram): Boolean =
    program.canvas(inputs(1).toInt)(inputs(0).toInt).isWhitespace

  implicit class RichOptionConvert(val s: String) extends AnyVal {
    def safeToInt: Option[Int] =
      try {
        Some(s.toInt)
      } catch {
        case _: NullPointerException => None
        case _: NumberFormatException => None
      }
  }
}


