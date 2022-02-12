package core

import core.model._
import org.slf4j.{Logger, LoggerFactory}
import scala.util.{Failure, Success, Try}

object Controller extends Helpers {

  def isCoordinatesTypesCorrect(implicit inputs: List[String]): Boolean =
    inputs.map(_.safeToInt).forall(_.isDefined)

  def isYCoordinatesOrdered(implicit inputs: List[String]): Boolean =
    Try(inputs(3).toInt > inputs(1).toInt).isSuccess

  def isXCoordinatesOrdered(implicit inputs: List[String]): Boolean =
    Try(inputs(2).toInt > inputs(0).toInt).isSuccess

  def isDiagonalLine(implicit inputs: List[String]): Boolean =
    inputs.to(Set).size == inputs.length

  def isInsideCanvas(implicit program: DrawingProgram, inputs: List[String]): Boolean = {

    val inSafe = inputs.map(_.safeToInt)

    val XWithinLimits: Either[Throwable, Boolean] =
      Try{
        (inSafe(0).get > 0) && (inSafe(2).get < program.canvasWidth)
      }.toEither

    val YWithinLimits: Either[Throwable, Boolean] = Try(
      (inSafe(1).get > 0) && (inSafe(3).get < program.canvasHeight)
    ).toEither

    val limits = for {
      a <- XWithinLimits
      b <- YWithinLimits
    } yield (a, b)

    !limits.toString.contains("false")
  }

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


trait Controller extends Helpers {

  import Controller._
  lazy val logger: Logger = LoggerFactory.getLogger(this.getClass)

  trait IOMessage[T] {
    def isInputCorrect: Boolean
    def safeRun: Unit
  }


  implicit class CreateCanvasSafe(val input: CreateCanvas)(implicit program: DrawingProgram, numberInputs: Int) extends IOMessage[Feature] {
    implicit val args: List[String] = List(input.width, input.height)
    override def isInputCorrect: Boolean = isCoordinatesTypesCorrect

    override def safeRun: Unit = {
      if (isInputCorrect) {
        val width = input.width.toInt
        val height = input.height.toInt

        program.createCanvas(width, height)
        program.displayCanvas

      } else {
        logger.info("Coordinates are not numerical.")
      }
    }
  }


  implicit class DrawLineSafe(val input: DrawLine)(implicit program: DrawingProgram, numberInputs: Int) extends IOMessage[Feature] {

    implicit val args: List[String] = List(input.x1, input.y1, input.x2, input.y2)
    override def isInputCorrect: Boolean = (!isCanvasEmpty) & (!isDiagonalLine) & isCoordinatesTypesCorrect

    override def safeRun: Unit = {
     if (isInputCorrect) {
        val x1: Int = input.x1.toInt
        val y1: Int = input.y1.toInt
        val x2: Int = input.x2.toInt
        val y2: Int = input.y2.toInt

        if ((y1 == y2) && isXCoordinatesOrdered){
          program.drawHorizontalLine(x1, y1, x2)
          program.displayCanvas

        } else if ((x1 == x2) && isYCoordinatesOrdered) {
          program.drawVerticalLine(x1, y1, x2, y2)
          program.displayCanvas
        }

      } else {
        logger.info("Arguments are not correctly introduced or canvas does not exist.")
      }
    }
  }


  implicit class DrawSquareSafe(val input: DrawSquare)(implicit program: DrawingProgram, numberInputs: Int) extends IOMessage[Feature] {
    implicit val args: List[String] = List(input.x1, input.y1, input.x2, input.y2)
    override def isInputCorrect: Boolean = (!isCanvasEmpty) && isXCoordinatesOrdered && isYCoordinatesOrdered

    override def safeRun: Unit = {
      if (isInputCorrect) {
        val x1: Int = input.x1.toInt
        val y1: Int = input.y1.toInt
        val x2: Int = input.x2.toInt
        val y2: Int = input.y2.toInt

        program.drawSquare(x1, y1, x2, y2)
        logger.info("Square has been drawn.")
        program.displayCanvas
      } else {
        logger.info("Arguments are not correctly introduced or canvas does not exist.")
      }
    }
  }


  implicit class FillAreaSafe(val input: FillArea)(implicit program: DrawingProgram, implicit val numberInputs: Int) extends IOMessage[Feature] {
    implicit val args: List[String] = List(input.x, input.y, input.colour)
    override def isInputCorrect: Boolean = (!isCanvasEmpty) & isInsideCanvas

    override def safeRun: Unit = {
      if (isInputCorrect) {
        val x1: Int = input.x.toInt
        val y1: Int = input.y.toInt
        val colour: String = input.colour

        program.fillingArea(x1, y1, colour)
        logger.info("Area has been filled.")
        program.displayCanvas
      } else {
        logger.info("Arguments are not correctly introduced or canvas does not exist.")
      }
    }
  }

}




