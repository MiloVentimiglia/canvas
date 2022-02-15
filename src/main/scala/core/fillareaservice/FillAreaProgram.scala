package core.fillareaservice

import scala.util.{Failure, Random, Success, Try}
import scala.annotation.tailrec
import core.drawingservice.DrawingProgram
import core.drawingservice.DrawingProgram._

final class FillAreaProgram(val program: DrawingProgram) {

  import FillAreaProgram._

  @tailrec
  def fillingArea(x: Int, y: Int, colour: String, iteration: Double = 1.0, acc: Double = 1.0): Unit = {
    if (threshold(acc, iteration) > 1E-03) {

      var newAcc: Double = acc
      val newIter: Double = inc(iteration)

      val nodes: List[Coordinates] = nodesCoordinates(x, y)
      val nodesColours: Map[Coordinates, String] = mapNodes(nodes, colour)
      val nextPoint: (Coordinates, String) = getNextPoint(nodesColours)

      val newX: Int = nextPoint._1._1
      val newY: Int = nextPoint._1._2
      val fill: String = nextPoint._2

      if (fill == EMPTY_NODE) {
        newAcc += 1
        fillingPoint(newX, newY, colour)
      }

      fillingArea(newX, newY, colour, newIter, newAcc)
    }
  }

  private def threshold(acc: Double, iteration: Double): Double = acc / iteration

  private def inc(iteration: Double): Double = iteration + 1

  /** Creates a list with the coordinates from the surrounding nodes */
  private def nodesCoordinates(x: Int, y: Int): List[Coordinates] = {
    List((x - 1, y + 1),
      (x, y + 1),
      (x + 1, y + 1),
      (x - 1, y),
      (x + 1, y),
      (x - 1, y - 1),
      (x, y - 1),
      (x + 1, y - 1))
  }

  /** Creates a map of node coordinates and respective colour. Example:
   *
   * (2,2) -> "EMPTY"
   * (2,1) -> "COLORED"
   * (2,3) -> "BLOCKED"
   * (1,3) -> "COLORED"
   * ....
   * */
  private def mapNodes(list: List[Coordinates], colour: String): Map[Coordinates, String] = {
    var map = Map[Coordinates, String]()

    list.foreach { coord =>
      val x = coord._1
      val y = coord._2
      val filling: String = program.canvas(y)(x).toString

      if (filling == WHITESPACE) {
        map = map + (coord -> EMPTY_NODE)
      } else if (filling == colour) {
        map = map + (coord -> COLORED_NODE)
      }
    }
    map
  }

  /**
   * Filters out the map for empty and already colored nodes. If empty nodes exists it takes one at random.
   * Otherwise, if there are no empty nodes, then picks a colored node at random.
   */
  private def getNextPoint(mappedNodes: Map[Coordinates, String]): (Coordinates, String) = {
    val nodesNoColour: Seq[Coordinates] = mappedNodes.toList.filter(_._2.contains(EMPTY_NODE)).map(_._1)
    val nodesColour: Seq[Coordinates] = mappedNodes.toList.filter(_._2.contains(COLORED_NODE)).map(_._1)

    if (nodesNoColour.nonEmpty) {
      val r = new Random()
      val size = nodesNoColour.length
      val index = r.nextInt(size)
      (nodesNoColour(index), EMPTY_NODE)
    } else {
      val r = new Random()
      val size = nodesColour.length
      val index = r.nextInt(size)
      (nodesColour(index), COLORED_NODE)
    }
  }


  /**
   * Splits the horizontal line on the x coordinate into two segments: left and right.
   * The empty nodes on the trailing of left segment are filled with the colour.
   * Conversely, the empty nodes on heading of the right segment are filled with the
   * colour.
   *
   *  -----------------
   *  |           xxxx|        left seg.           right seg.
   *  |xxxxx   Ö  x  x| ---> List(|xxxxx   )  +  List(  x  x|)  ---> List(|xxxxxooo)  +  List(oox  x|)
   *  |    x      xxxx|
   *  |    x          |
   *  -----------------
   *
   *
   * */
  private def fillingPoint(x: Int, y: Int, colour: String): Unit = {
    val left = program.canvas(y).substring(0, x)
    val right = program.canvas(y).substring(x, program.canvasWidth)

    program.canvas(y) = fillTrailing(left, colour) + fillHeading(right, colour)
  }

  /**
   * Regular expression that enables to fill the whitespaces in the trailing of the
   * left segment with the colour.
   * */
  private def fillTrailing(line: String, colour: String): String = {
    val pattern = "\\s+".r
    Try{
      pattern.findAllIn(line).next().length
    } match {
      case Success(value) => line.replaceAll("^\\s+", colour * value)
      case Failure(_) => line
    }
  }

  /**
   * Regular expression that enables to fill the whitespaces in the heading of the
   * right segment with the colour.
   * */
  private def fillHeading(line: String, colour: String): String = {
    val pattern = "^\\s+".r
    Try {
      pattern.findAllIn(line).next().length
    } match {
      case Success(value) => line.replaceAll("^\\s+", colour * value)
      case Failure(_) => line
    }
  }
}


object FillAreaProgram {
  val EMPTY_NODE = "EMPTY"
  val COLORED_NODE = "ALREADY_COLORED"
  val BLOCKED_NODE = "BLOCKED"
}