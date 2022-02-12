//object off {
//
//  def isInsideCanvas(implicit program: DrawingProgram, inputs: List[String]): Boolean = {
//
//    val inSafe = inputs.map(_.safeToInt)
//    val XWithinLimits: Either[Throwable, Boolean] = Try{(inSafe(0).get > 0) && (inSafe(2).get < program.canvasWidth)}.toEither
//
//    val YWithinLimits: Either[Throwable, Boolean] = Try(
//      (inSafe(1).get > 0) && (inSafe(3).get < program.canvasHeight)
//    ).toEither
//
//    val limits = for {
//      a <- XWithinLimits
//      b <- YWithinLimits
//    } yield (a, b)
//
//    !limits.toString.contains("false")
//  }
//
//}
