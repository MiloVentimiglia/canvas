package core.FillAreaService

import scala.util.Random
import scala.annotation.tailrec
import core.DrawingService.DrawingProgram
import core.DrawingService.DrawingProgram._

final class FillAreaProgram(val program: DrawingProgram) {

  /**
   * Fill an enclosed area with a specified colour
   * @param x; Initial X coordinate in the canvas
   * @param y; Initial Y coordinate in the canvas
   * @param iter; Iteration number
   * @param iter; Iteration number
   * @param slideDuration  sliding interval of the window (i.e., the interval after which
   *                       the new DStream will generate RDDs); must be a multiple of this
   *                       DStream's batching interval
   */

  @tailrec
  def fillingArea(x: Int, y: Int, colour: String, iter: Double = 1.0, acc: Double = 1.0): Unit = {

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

    if (ratio(iter, acc) < 10) {
      var newAcc = acc
      val adjacentPoints: List[Coordinates] = adjacentCoord(x, y)
      val possibleDirections: Map[Coordinates, String] = scanAdjacentCoord(adjacentPoints, colour)
      val nextPoint: (Coordinates, String) = getNextPoint(possibleDirections)

      val newCoordinates = nextPoint._1
      val newX = newCoordinates._1
      val newY = newCoordinates._2
      val flag = nextPoint._2

      if (flag == EMPTY_COORDINATE) {newAcc += 1}
      if (!isAlreadyColored(newX, colour, program.canvas(newY))) fillingPoint(newCoordinates, colour)

      fillingArea(newX, newY, colour, incIter(iter), newAcc)
    }
  }

  private def ratio(iter: Double, acc: Double): Double = iter / acc

  private def incIter(iter: Double): Double = iter + 1

  private def adjacentCoord(x: Int, y: Int): List[Coordinates] = {
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

  private def scanAdjacentCoord(list: List[Coordinates], colour: String): Map[Coordinates, String] = {
    var pointsColour = Map[Coordinates, String]()
    list.foreach { coord =>
      val x = coord._1
      val y = coord._2
      val currentColour: String = program.canvas(y)(x).toString

      if (currentColour == WHITESPACE) {
        pointsColour = pointsColour + (coord -> EMPTY_COORDINATE)
      } else if (currentColour == colour) {
        pointsColour = pointsColour + (coord -> COLORED_COORDINATE)
      }
    }
    pointsColour
  }

  private def getNextPoint(
        possibleMoves: Map[Coordinates, String]): (Coordinates, String) = {

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

    val line: String = program.canvas(y)
    program.canvas(y) = line.substring(0, x) + filling + line.substring(x + 1, program.canvasWidth)
  }

  private def isAlreadyColored(x: Int, colour: String, line: String): Boolean = {
    val colorAtXY: String = line.substring(x)
    if (colorAtXY == colour) true else false
  }

}
