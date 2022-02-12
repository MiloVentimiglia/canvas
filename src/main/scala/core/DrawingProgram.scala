package core

import scala.annotation.tailrec
import scala.collection.mutable
import scala.util.Random

object DrawingProgram {

  type CanvasAxialPosition = Int
  type CanvasHorizontalLine = String
  type Canvas = mutable.HashMap[CanvasAxialPosition, CanvasHorizontalLine]
  type Coordinates = (Int, Int)

  val LINEMARKER = "x"
  val WHITESPACE = " "
  val TOP_FRAME = "-"
  val SIDE_FRAME = "|"
  val EMPTY_COORDINATE = "EMPTY"
  val COLORED_COORDINATE = "ALREADY_COLORED"
  val BLOCKED_COORDINATE = "BLOCKED"

}

class DrawingProgram extends Helpers {

  import DrawingProgram._

  var canvas: Canvas = mutable.HashMap[CanvasAxialPosition, CanvasHorizontalLine]()

  def canvasHeight: Int = canvas.size
  def canvasWidth: Int = canvas(0).length

  def createCanvas(width: Int, height: Int): DrawingProgram = {
    val cleanCanvas: Canvas = new Canvas()
    val heightAdjusted = height + 2

    for (h <- 0 until heightAdjusted) {
      if (h == 0 || h == heightAdjusted - 1) {
        cleanCanvas(h) = TOP_FRAME * width
      } else {
        cleanCanvas(h) = SIDE_FRAME + WHITESPACE * (width - 2) + SIDE_FRAME
      }
    }
    this.canvas = cleanCanvas
    this
  }

  def displayCanvas: DrawingProgram = {
    for (y <- 0 until canvasHeight) {
      println(canvas(y))
    }
    this
  }

  def drawHorizontalLine(x1: Int, y1: Int, x2: Int): DrawingProgram = {
    val line: String = canvas(y1)
    val lineToAdd: String = LINEMARKER * (x2 - x1 + 1)
    canvas(y1) = line.substring(0, x1) + lineToAdd + line.substring(x2 + 1, canvasWidth)

    this.canvas = canvas
    this
  }

  def drawVerticalLine(x1: Int, y1: Int, x2: Int, y2: Int): DrawingProgram = {
    for (y <- y1 until y2 + 1) {
      val line: String = canvas(y)
      val lineToAdd: String = LINEMARKER
      canvas(y) = line.substring(0, x1) + lineToAdd + line.substring(x2 + 1, canvasWidth)
    }

    this.canvas = canvas
    this
  }

  def drawSquare(x1: Int, y1: Int, x2: Int, y2: Int): DrawingProgram = {

    for (y <- y1 until y2 + 1) {
      if (y == y1 || y == y2) {
        val line: String = canvas(y)
        val lineToAdd: String = LINEMARKER * (x2 - x1 + 1)
        canvas(y) = line.substring(0, x1) + lineToAdd + line.substring(x2 + 1, canvasWidth)
      } else {
        val line: String = canvas(y)
        val lineToAdd: String = LINEMARKER + WHITESPACE * (x2 - x1 - 1) + LINEMARKER
        canvas(y) = line.substring(0, x1) + lineToAdd + line.substring(x2 + 1, canvasWidth)
      }
    }

    this.canvas = canvas
    this
  }

  @tailrec
  final def fillingArea(x: Int, y: Int, color: String, iter: Double = 1.0, acc: Double = 1.0): Unit = {

    /**
     * Approach (Stochastic Random Walk):
     * iter - number of iterations
     * acc - accumulator with the number of coordinates which have been colored during execution
     *
     * 1. Pick initial point in the canvas
     * 2. Discover in which directions it is possible to move into by looking into the neighboring coordinates.
     * 3. Move into next position. When all the adjacent positions have been already colored, then pick one at random.
     * 4. If new position is not colored yet, add "color" and increment accumulator.
     *    Otherwise move to the next possible position without incrementing the accumulator.
     * 5. Iterate until the ratio between the number of iterations and the accumulator is above 3.
     */

    val asymptoticRatio: Double = iter / acc

    if (asymptoticRatio < 10) {
      val newIter = iter + 1
      var newAcc = acc
      val adjacentPoints: List[Coordinates] = getAdjacentPointsCoordinates(x, y)
      val possibleDirections: Map[Coordinates, String] = getPossibleDirections(adjacentPoints, color)
      val nextPoint: (Coordinates, String) = getNextPoint(possibleDirections)

      val newCoordinates = nextPoint._1
      val newX = newCoordinates._1
      val newY = newCoordinates._2
      val flag = nextPoint._2

      if (flag == EMPTY_COORDINATE) {newAcc += 1}
      if (!isAlreadyColored(newX, color, canvas(newY))) fillingPoint(newCoordinates, color)

      fillingArea(newX, newY, color, newIter, newAcc)
    }
  }

  private def getAdjacentPointsCoordinates(x: Int, y: Int): List[Coordinates] = {
    List(
      (x - 1, y + 1),
      (x, y + 1),
      (x + 1, y + 1),
      (x - 1, y),
      (x + 1, y),
      (x - 1, y - 1),
      (x, y - 1),
      (x + 1, y - 1),
    )
  }

  private def getPossibleDirections(list: List[Coordinates], filling: String): Map[Coordinates, String] = {
    var adjacentPointInfo = Map[Coordinates, String]()

    list.foreach { coord =>
      val x = coord._1
      val y = coord._2
      val currentCanvasColour: String = canvas(y)(x).toString

      if (currentCanvasColour == WHITESPACE) {
        adjacentPointInfo = adjacentPointInfo + (coord -> EMPTY_COORDINATE)
      } else if (currentCanvasColour == filling) {
        adjacentPointInfo = adjacentPointInfo + (coord -> COLORED_COORDINATE)
      }
    }
    adjacentPointInfo
  }

  private def getNextPoint(possibleMoves: Map[Coordinates, String]): (Coordinates, String) = {
    val possibleMovesToEmptyCoordinates: Seq[Coordinates] =
      possibleMoves.toList.filter(_._2.contains(EMPTY_COORDINATE)).map(_._1)

    val possibleMovesToColoredCoordinates: Seq[Coordinates] =
      possibleMoves.toList.filter(_._2.contains(COLORED_COORDINATE)).map(_._1)

    if (possibleMovesToEmptyCoordinates.nonEmpty) {
      val r = new Random()
      val size = possibleMovesToEmptyCoordinates.length
      val index = r.nextInt(size)
      (possibleMovesToEmptyCoordinates(index), EMPTY_COORDINATE)

    } else {
      val r = new Random()
      val size = possibleMovesToColoredCoordinates.length
      val index = r.nextInt(size)
      (possibleMovesToColoredCoordinates(index), COLORED_COORDINATE)
    }
  }

  private def fillingPoint(nextPoint: Coordinates, filling: String): Unit = {
    val x = nextPoint._1
    val y = nextPoint._2

    val line: String = canvas(y)
    canvas(y) = line.substring(0, x) + filling + line.substring(x + 1, canvasWidth)
    this.canvas = canvas
  }

}