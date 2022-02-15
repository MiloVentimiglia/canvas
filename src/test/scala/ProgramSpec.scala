import core.Implicits._
import core.drawingservice.DrawingProgram
import core.fillareaservice.FillAreaProgram
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import org.scalatest.{BeforeAndAfter, PrivateMethodTester}
import scala.collection.mutable


class ProgramSpec extends AnyFlatSpec with Matchers with BeforeAndAfter with PrivateMethodTester{

  import DrawingProgram._
  import FillAreaProgram._

  implicit val program: DrawingProgram = new DrawingProgram().createCanvas(40, 30)
  implicit val fillArea: FillAreaProgram = new FillAreaProgram(program)
  val colour = "o"

  "the private method" should "get the adjacent nodes" in {

    val mock = PrivateMethod[List[Coordinates]](Symbol("nodesCoordinates"))
    val output = fillArea.invokePrivate(mock(2,2))

    output should be (List((1,3), (2,3), (3,3), (1,2), (3,2), (1,1), (2,1), (3,1)))
  }

  "the private method" should "get possible directions to move into" in {

    val mockPossibleDirections = PrivateMethod[Map[Coordinates, String]](Symbol("mapNodes"))

    val inputList= List((1,3), (2,3), (3,3), (1,2), (3,2), (1,1), (2,1), (3,1))
    val output = fillArea.invokePrivate(mockPossibleDirections(inputList, colour))

    val expectedOutput = mutable.HashMap(
      (2,1) -> EMPTY_NODE,
      (1,2) -> EMPTY_NODE,
      (1,1) -> EMPTY_NODE,
      (3,2) -> EMPTY_NODE,
      (3,1) -> EMPTY_NODE,
      (3,3) -> EMPTY_NODE,
      (2,3) -> EMPTY_NODE,
      (1,3) -> EMPTY_NODE
    )

    output should equal(expectedOutput)
  }

  "This method" should "attest whether canvas is empty" in {
    isCanvasEmpty should be(false)
  }

}