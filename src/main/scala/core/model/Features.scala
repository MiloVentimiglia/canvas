package core.model

sealed trait Feature

case class CreateCanvas(width: String, height: String) extends Feature
case class DrawLine(x1: String, y1: String, x2: String, y2: String) extends Feature
case class DrawSquare(x1: String, y1: String, x2: String, y2: String) extends Feature
case class FillArea(x: String, y: String, colour: String) extends Feature



