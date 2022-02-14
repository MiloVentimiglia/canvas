import core.Boot.controller
import core.DrawingService.DrawingProgram
import core.FillAreaService.FillAreaProgram
import org.scalatest.featurespec.AnyFeatureSpec
import org.scalatest.{BeforeAndAfter, GivenWhenThen}
import org.slf4j.{Logger, LoggerFactory}
import java.io.ByteArrayOutputStream


class AcceptanceTestSpec extends AnyFeatureSpec with BeforeAndAfter with GivenWhenThen {

  protected var logger: Logger = LoggerFactory.getLogger(this.getClass)

  implicit val drawingProgram: DrawingProgram = new DrawingProgram()
  implicit val fillingProgram: FillAreaProgram = new FillAreaProgram(drawingProgram)


  Feature("Create a canvas") {
    Scenario("Creating a canvas") {
      Given("a width and height of 20 4")
      val in = "C 20 4"

      When("the inputs are passed to the app")
      Console.withOut(new ByteArrayOutputStream())(controller(in))

      Then("the following canvas is displayed")
      drawingProgram.displayCanvas
    }


    Scenario("Creating canvas and one of the inputs is not numerical") {
      Given("a width and height of x 4")
      val in = "C x 4"

      When("the inputs are passed to the app")
      val out = new ByteArrayOutputStream()
      Console.withOut(out)(controller(in))

      Then("the canvas is not created and the output message is")
      assert(out.toString().contains("Coordinates are not positive numerical values."))
    }


    Scenario("Creating canvas and one of the inputs is non positive") {
      Given("a width and height of -1 4")
      val in = "C -1 4"

      When("the inputs are passed to the app")
      val out = new ByteArrayOutputStream()
      Console.withOut(out)(controller(in))

      Then("the canvas is not created and the output message is")
      assert(out.toString().contains("Coordinates are not positive numerical values."))
    }


    Scenario("Creating canvas and one of the inputs is zero") {
      Given("a width and height of 0 4")
      val in = "C 0 4"

      When("the inputs are passed to the app")
      val out = new ByteArrayOutputStream()
      Console.withOut(out)(controller(in))

      Then("the canvas is not created and the output message is")
      assert(out.toString().contains("Coordinates are not positive numerical values."))
    }
  }


  Feature("Draw horizontal line") {

    Scenario("Drawing a horizontal line") {
      Given("the inputs L 1 2 6 2")
      val in = "L 1 2 6 2"

      When("the inputs are passed to the controller")
      Console.withOut(new ByteArrayOutputStream())(controller(in))

      Then("the horizontal line is visible in the canvas")
      drawingProgram.displayCanvas
    }

    Scenario("Drawing a horizontal line and one of the inputs is not numerical") {
      Given("the inputs L 1 2 6 x")
      val in = "L 1 x 6 x"

      When("the inputs are passed to the controller")
      val out = new ByteArrayOutputStream()
      Console.withOut(out)(controller(in))

      Then("the horizontal line is visible in the canvas")
      assert(out.toString().contains("Arguments are not correctly introduced or canvas does not exist."))
    }

    Scenario("Drawing a horizontal line when x1 and x2 inputs are swapped") {
      Given("the inputs L 6 2 1 2")
      val in = "L 6 2 1 2"

      When("the inputs are passed to the controller")
      val out = new ByteArrayOutputStream()
      Console.withOut(out)(controller(in))

      Then("the horizontal line is visible in the canvas")
      assert(out.toString().contains("Arguments are swapped."))
    }
  }











}