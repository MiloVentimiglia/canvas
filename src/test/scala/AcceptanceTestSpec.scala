//import core.DrawingService.DrawingProgram
//import core.FillAreaService.FillAreaProgram
//import org.scalatest.flatspec.AnyFlatSpec
//import org.scalatest.featurespec.AnyFeatureSpec
//import org.scalatest.matchers.should.Matchers
//import org.scalatest.{BeforeAndAfter, GivenWhenThen}
//import org.slf4j.{Logger, LoggerFactory}
//
//import scala.collection.mutable
//import scala.reflect.{ClassTag, classTag}
//
//class AcceptanceTestSpec extends AnyFeatureSpec with BeforeAndAfter with GivenWhenThen{
//
//  import DrawingProgram._
//  import FillAreaProgram.EMPTY_NODE
//
//  protected var logger: Logger = LoggerFactory.getLogger(this.getClass)
//  val color = "o"
//
//  before{
//    val program = new DrawingProgram().createCanvas(40, 30)
//  }
//
//
//  Feature("Multiplication") {
//
//    info(" In order to avoid making mistakes")
//    info("As an accountant")
//    info(" I want to multiply numbers")
//
//    scenario("Multiply two variables") {
//
//      given("a variable x with value 3")
//      val x = 3
//      and("a variable y with value 4")
//      val y = 4
//
//      when("i multiply x * y")
//      val result = x * y
//
//      then("i get 12")
//      assert(result === 12)
//    }
//  }