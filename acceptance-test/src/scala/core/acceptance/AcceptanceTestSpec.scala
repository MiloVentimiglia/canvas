import core.DrawingService.DrawingProgram
import core.FillAreaService.FillAreaProgram
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import org.scalatest.{BeforeAndAfter, GivenWhenThen}
import org.slf4j.{Logger, LoggerFactory}

import scala.collection.mutable
import scala.reflect.{ClassTag, classTag}

class AcceptanceTestSpec extends AnyFlatSpec with Matchers with BeforeAndAfter with GivenWhenThen{

  import DrawingProgram._
  import FillAreaProgram.EMPTY_NODE

  protected var logger: Logger = LoggerFactory.getLogger(this.getClass)
  val color = "o"

  before{
    program = new DrawingProgram().createCanvas(40, 30)

  }