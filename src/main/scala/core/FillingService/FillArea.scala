package core.FillingService
//
//class FillArea {
//
//  @tailrec
//  final def fillingArea(x: Int, y: Int, color: String, iter: Double = 1.0, acc: Double = 1.0): Unit = {
//
//    /**
//     * Approach (Stochastic Random Walk):
//     * iter - number of iterations
//     * acc - accumulator with the number of coordinates which have been colored during execution
//     *
//     * 1. Pick initial point in the canvas
//     * 2. Discover in which directions it is possible to move into by looking into the neighboring coordinates.
//     * 3. Move into next position. When all the adjacent positions have been already colored, then pick one at random.
//     * 4. If new position is not colored yet, add "color" and increment accumulator.
//     *    Otherwise move to the next possible position without incrementing the accumulator.
//     * 5. Iterate until the ratio between the number of iterations and the accumulator is above 3.
//     */
//
//    val asymptoticRatio: Double = iter / acc
//
//    if (asymptoticRatio < 10) {
//      val newIter = iter + 1
//      var newAcc = acc
//      val adjacentPoints: List[Coordinates] = getAdjacentPointsCoordinates(x, y)
//      val possibleDirections: Map[Coordinates, String] = getPossibleDirections(adjacentPoints, color)
//      val nextPoint: (Coordinates, String) = getNextPoint(possibleDirections)
//
//      val newCoordinates = nextPoint._1
//      val newX = newCoordinates._1
//      val newY = newCoordinates._2
//      val flag = nextPoint._2
//
//      if (flag == EMPTY_COORDINATE) {newAcc += 1}
//      if (!isAlreadyColored(newX, color, canvas(newY))) fillingPoint(newCoordinates, color)
//
//      fillingArea(newX, newY, color, newIter, newAcc)
//    }
//  }
//
//  private def getAdjacentPointsCoordinates(x: Int, y: Int): List[Coordinates] = {
//    List(
//      (x - 1, y + 1),
//      (x, y + 1),
//      (x + 1, y + 1),
//      (x - 1, y),
//      (x + 1, y),
//      (x - 1, y - 1),
//      (x, y - 1),
//      (x + 1, y - 1),
//    )
//  }
//
//  private def getPossibleDirections(list: List[Coordinates], filling: String): Map[Coordinates, String] = {
//    var adjacentPointInfo = Map[Coordinates, String]()
//
//    list.foreach { coord =>
//      val x = coord._1
//      val y = coord._2
//      val currentCanvasColour: String = canvas(y)(x).toString
//
//      if (currentCanvasColour == WHITESPACE) {
//        adjacentPointInfo = adjacentPointInfo + (coord -> EMPTY_COORDINATE)
//      } else if (currentCanvasColour == filling) {
//        adjacentPointInfo = adjacentPointInfo + (coord -> COLORED_COORDINATE)
//      }
//    }
//    adjacentPointInfo
//  }
//
//  private def getNextPoint(possibleMoves: Map[Coordinates, String]): (Coordinates, String) = {
//    val possibleMovesToEmptyCoordinates: Seq[Coordinates] =
//      possibleMoves.toList.filter(_._2.contains(EMPTY_COORDINATE)).map(_._1)
//
//    val possibleMovesToColoredCoordinates: Seq[Coordinates] =
//      possibleMoves.toList.filter(_._2.contains(COLORED_COORDINATE)).map(_._1)
//
//    if (possibleMovesToEmptyCoordinates.nonEmpty) {
//      val r = new Random()
//      val size = possibleMovesToEmptyCoordinates.length
//      val index = r.nextInt(size)
//      (possibleMovesToEmptyCoordinates(index), EMPTY_COORDINATE)
//
//    } else {
//      val r = new Random()
//      val size = possibleMovesToColoredCoordinates.length
//      val index = r.nextInt(size)
//      (possibleMovesToColoredCoordinates(index), COLORED_COORDINATE)
//    }
//  }
//
//  private def fillingPoint(nextPoint: Coordinates, filling: String): Unit = {
//    val x = nextPoint._1
//    val y = nextPoint._2
//
//    val line: String = canvas(y)
//    canvas(y) = line.substring(0, x) + filling + line.substring(x + 1, canvasWidth)
//    this.canvas = canvas
//  }
//
//}
