package core

import core.model._
import core.drawingservice.DrawingProgram
import core.fillareaservice.FillAreaProgram
import scala.util.control.Breaks.break

trait Controller extends Implicits {
  this: Implicits =>

  def controller(input: String)
                (implicit program: DrawingProgram, fillAreaProgram: FillAreaProgram): Unit = {

    val inputTokens: List[String] = input.split(" ").filter(_.nonEmpty).toList
    val command: Option[String] = inputTokens.headOption

    lazy val coordinates: List[String] = inputTokens.tail
    lazy val numberInputs: Int = coordinates.length

    lazy val in1: String = coordinates(0)
    lazy val in2: String = coordinates(1)
    lazy val in3: String = coordinates(2)
    lazy val in4: String = coordinates(3)

    command match {
      case Some("C") if numberInputs == 2 =>
        CreateCanvas(in1, in2).safeRun
      case Some("L") if numberInputs == 4 =>
        DrawLine(in1, in2, in3, in4).safeRun
      case Some("R") if numberInputs == 4 =>
        DrawSquare(in1, in2, in3, in4).safeRun
      case Some("B") if numberInputs == 3 =>
        FillArea(in1, in2, in3).safeRun
      case Some("Q") =>
        println("Gracefully closing program.")
        break()
      case _ =>
        println("Command is not implemented and/or inputs are not correctly introduced.")
    }
  }

}
