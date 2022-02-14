import core.Boot.controller
import core.DrawingService.DrawingProgram
import core.FillAreaService.FillAreaProgram
import org.scalatest.featurespec.AnyFeatureSpec
import org.scalatest.GivenWhenThen
import java.io.ByteArrayOutputStream


class AcceptanceTestSpec extends AnyFeatureSpec with GivenWhenThen {

  implicit val drawingProgram: DrawingProgram = new DrawingProgram()
  implicit val fillingProgram: FillAreaProgram = new FillAreaProgram(drawingProgram)

  Feature("Create a canvas") {

    Scenario("Trying to draw something without having a canvas") {
      Given(" the input L 1 2 6 2")
      val in: String = "L 1 2 6 2"

      When("the input is passed to the app without first having created a canvas")
      val out: ByteArrayOutputStream = new ByteArrayOutputStream()
      Console.withOut(out)(controller(in))

      Then("the following canvas is displayed")
      assert(out.toString().contains("Arguments are not correctly introduced or canvas does not exist."))
    }



    Scenario("Creating canvas and one of the inputs is not numerical") {
      Given("the input C x 4")
      val in: String = "C x 4"

      When("the input is passed to the app")
      val out: ByteArrayOutputStream = new ByteArrayOutputStream()
      Console.withOut(out)(controller(in))

      Then("the canvas is not created and the output message is")
      assert(out.toString().contains("Coordinates are not positive numerical values."))
    }


    Scenario("Creating canvas and one of the inputs is negative") {
      Given("the input C -1 4")
      val in: String = "C -1 4"

      When("the input is passed to the app")
      val out: ByteArrayOutputStream = new ByteArrayOutputStream()
      Console.withOut(out)(controller(in))

      Then("the canvas is not created and the output message is")
      assert(out.toString().contains("Coordinates are not positive numerical values."))
    }


    Scenario("Creating canvas and one of the inputs is zero") {
      Given("the input C 0 4")
      val in: String = "C 0 4"

      When("the input is passed to the app")
      val out: ByteArrayOutputStream = new ByteArrayOutputStream()
      Console.withOut(out)(controller(in))

      Then("the canvas is not created and the output message is")
      assert(out.toString().contains("Coordinates are not positive numerical values."))
    }


    Scenario("Creating a canvas with proper inputs") {
      Given(" the input C 20 4")
      val in: String = "C 20 4"

      When("the input is passed to the app")
      Console.withOut(new ByteArrayOutputStream())(controller(in))

      Then("the following canvas is displayed")
      drawingProgram.displayCanvas
    }
  }


  Feature("Draw horizontal and vertical lines") {


    Scenario("Drawing a horizontal or vertical line and one of the inputs is not numerical") {
      Given("the input L 1 2 6 x")
      val in: String = "L 1 x 6 x"

      When("the input is passed to the app")
      val out = new ByteArrayOutputStream()
      Console.withOut(out)(controller(in))

      Then("the output message is.")
      assert(out.toString().contains("Arguments are not correctly introduced or canvas does not exist."))
    }


    Scenario("Drawing a horizontal or vertical line when inputs are swapped") {
      Given("the input L 6 2 1 2 (instead of L 1 2 6 2)")
      val in: String = "L 6 2 1 2"

      When("the input is passed to the app")
      val out: ByteArrayOutputStream = new ByteArrayOutputStream()
      Console.withOut(out)(controller(in))

      Then("the output message is.")
      assert(out.toString().contains("Arguments are swapped."))
    }


    Scenario("Trying to draw a diagonal line") {
      Given("the input L 1 2 3 4")
      val in: String = "L 1 2 3 4"

      When("the input is passed to the app")
      val out: ByteArrayOutputStream = new ByteArrayOutputStream()
      Console.withOut(out)(controller(in))

      Then("the output message is.")
      assert(out.toString().contains("Arguments are not correctly introduced or canvas does not exist."))
    }


    Scenario("Drawing a horizontal line") {
      Given("the input L 1 2 6 2")
      val in: String = "L 1 2 6 2"

      When("the input is passed to the app")
      Console.withOut(new ByteArrayOutputStream())(controller(in))

      Then("the horizontal line is visible in the canvas")
      drawingProgram.displayCanvas
    }


    Scenario("Drawing a vertical line") {
      Given("the input L 6 3 6 4")
      val in: String = "L 6 3 6 4"

      When("the input is passed to the app")
      Console.withOut(new ByteArrayOutputStream())(controller(in))

      Then("the vertical line is visible in the canvas")
      drawingProgram.displayCanvas
    }
  }


  Feature("Draw square") {

    Scenario("Drawing a square and at least once of the inputs is not numerical") {
      Given("the input R 14 1 x 3")
      val in: String = "R 14 1 x 3"

      When("the input is passed to the app")
      val out: ByteArrayOutputStream = new ByteArrayOutputStream()
      Console.withOut(out)(controller(in))

      Then("the output message is.")
      assert(out.toString().contains("Arguments are not correctly introduced or canvas does not exist."))
    }


    Scenario("Drawing a square and inputs are swapped") {
      Given("the input R 18 1 14 3")
      val in: String = "R 18 1 14 3"

      When("the input is passed to the app")
      val out: ByteArrayOutputStream = new ByteArrayOutputStream()
      Console.withOut(out)(controller(in))

      Then("the output message is.")
      assert(out.toString().contains("Arguments are not correctly introduced or canvas does not exist."))
    }


    Scenario("Drawing a square in the canvas") {
      Given("the input R 14 1 18 3")
      val in: String = "R 14 1 18 3"

      When("the input is passed to the app")
      Console.withOut(new ByteArrayOutputStream())(controller(in))

      Then("the square is visible in the canvas")
      drawingProgram.displayCanvas
    }
  }


  Feature("Fill area with colour") {

    Scenario("One of the inputs is not numerical") {
      Given("the input B 10 a o")
      val in: String = "B 10 a o"

      When("the input is passed to the app")
      val out: ByteArrayOutputStream = new ByteArrayOutputStream()
      Console.withOut(out)(controller(in))

      Then("the output message is.")
      assert(out.toString().contains("Arguments are not correctly introduced or canvas does not exist."))
    }


    Scenario("One of the inputs is negative") {
      Given("the input B 10 -3 o")
      val in: String = "B 10 -3 o"

      When("the input is passed to the app")
      val out: ByteArrayOutputStream = new ByteArrayOutputStream()
      Console.withOut(out)(controller(in))

      Then("the output message is.")
      assert(out.toString().contains("Arguments are not correctly introduced or canvas does not exist."))
    }


    Scenario("The colour symbol is x") {
      Given("the input B 10 3 x")
      val in: String = "B 10 3 x"

      When("the input is passed to the app")
      val out: ByteArrayOutputStream = new ByteArrayOutputStream()
      Console.withOut(out)(controller(in))

      Then("the output message is.")
      assert(out.toString().contains("Arguments are not correctly introduced or canvas does not exist."))
    }


    Scenario("Filling empty area in the canvas") {
      Given("the input B 10 3 o")
      val in: String = "B 10 3 o"

      When("the input is passed to the app")
      Console.withOut(new ByteArrayOutputStream())(controller(in))

      Then("the canvas is filled")
      drawingProgram.displayCanvas
    }



    Scenario("Filling empty area in the canvas with edge corners or narrow areas different from the example") {
      Given("a canvas")
      drawingProgram.createCanvas(30, 40)

      When("with narrow channels and complex inner shapes")
      drawingProgram.drawSquare(24, 30, 27, 38)
      drawingProgram.drawSquare(3, 5, 10, 10)

      Then("the canvas is filled")
      drawingProgram.displayCanvas
      fillingProgram.fillingArea(3, 3,"o")
      drawingProgram.displayCanvas
    }
  }
}