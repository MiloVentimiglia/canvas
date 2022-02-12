//import core.{DrawingProgram, Helpers}
//import org.scalatest.flatspec.AnyFlatSpec
//import org.scalatest.matchers.should.Matchers
//import org.scalatest.BeforeAndAfter
//import org.slf4j.{Logger, LoggerFactory}
//
//class HelpersSpec extends AnyFlatSpec with Matchers with BeforeAndAfter with Helpers {
//
//  protected val logger: Logger = LoggerFactory.getLogger(this.getClass)
//  implicit var program: DrawingProgram = _
//
//  before{
//    program = new DrawingProgram().createCanvas(40, 30)
//  }
//
//  "The function" should "attest whether the x points belong" in {
//    val xPoints = List(10, 20, 40)
//    val yPoints = List(15, 25)
//    val result = isInsideCanvas(xPoints, yPoints, program.canvasWidth, program.canvasHeight)
//
//    result should be (false)
//  }
//
//  "This method" should "attest whether canvas is empty" in {
//    isCanvasEmpty should be (false)
//  }
//
//  "This method" should "attest whether all coordinates are integers" in {
//    val input = List("1","2","4","a")
//    checkCoordinatesTypes(input) should be (false)
//  }
//
//
//}
//
