package coreBenchmarks

import core.DrawingService.DrawingProgram
import org.openjdk.jmh.annotations._

import java.util.concurrent.TimeUnit
import scala.util.Random

object FillingBenchmark {

  @State(Scope.Thread)
  class FillingArea {

    private val canvasWidth: Int = 50
    private val canvasHeight: Int = 50
    private val color: String = "o"
    private def setup(): DrawingProgram = new DrawingProgram().createCanvas(canvasWidth, canvasHeight)

    @CompilerControl(CompilerControl.Mode.DONT_INLINE)
    def fillCanvas(): Unit = {
      val r = new Random
      setup().fillingArea(r.between(1, canvasWidth-1), r.between(1, canvasHeight-1), color)
    }
  }

}

@Warmup(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 20, time = 1, timeUnit = TimeUnit.SECONDS)
class FillingBenchmark {

  import coreBenchmarks.FillingBenchmark._

  @Benchmark
  @BenchmarkMode(Array(Mode.AverageTime))
  @OutputTimeUnit(TimeUnit.MILLISECONDS)
  def run(state: FillingArea): Unit =
    state.fillCanvas()

}
