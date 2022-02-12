import core.DrawingService.DrawingProgram
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import org.scalatest.{BeforeAndAfter, PrivateMethodTester}
import org.slf4j.{Logger, LoggerFactory}

import scala.collection.mutable

class ProgramSpec extends AnyFlatSpec with Matchers with BeforeAndAfter with PrivateMethodTester{

  import DrawingProgram._

  protected var logger: Logger = LoggerFactory.getLogger(this.getClass)
  var program: DrawingProgram = _
  val color = "o"

  before{
    program = new DrawingProgram().createCanvas(40, 30)
  }

  "The aggregation of the top bar of the canvas" should "be" in {
    val upperFrame = program.canvas(0)
    val groupedChar = upperFrame.groupBy(identity).map(x => (x._1, x._2.length))

    groupedChar should be (Map('-' -> 40))
  }

  "drawLine" should "create a new horizontal line" in {
    program.drawHorizontalLine(10, 15, 15)
    val line = program.canvas(15)
    val groupedChar = line.groupBy(identity).map(x => (x._1, x._2.length))

    groupedChar should be (Map('x' -> 6, '|' -> 2,  ' ' -> 32))
  }

  "the private method" should "get the adjacent point coordinates" in {

    val mock = PrivateMethod[List[Coordinates]](Symbol("getAdjacentPointsCoordinates"))
    val output = program.invokePrivate(mock(2,2))

    output should be (List((1,3), (2,3), (3,3), (1,2), (3,2), (1,1), (2,1), (3,1)))
  }

  "the private method" should "get possible directions to move into" in {

    val mockPossibleDirections = PrivateMethod[Map[Coordinates, String]](Symbol("getPossibleDirections"))

    val inputList= List((1,3), (2,3), (3,3), (1,2), (3,2), (1,1), (2,1), (3,1))
    val output = program.invokePrivate(mockPossibleDirections(inputList, color))

    val expectedOutput = mutable.HashMap(
      (2,1) -> EMPTY_COORDINATE,
      (1,2) -> EMPTY_COORDINATE,
      (1,1) -> EMPTY_COORDINATE,
      (3,2) -> EMPTY_COORDINATE,
      (3,1) -> EMPTY_COORDINATE,
      (3,3) -> EMPTY_COORDINATE,
      (2,3) -> EMPTY_COORDINATE,
      (1,3) -> EMPTY_COORDINATE
    )

    output should equal(expectedOutput)
  }

}