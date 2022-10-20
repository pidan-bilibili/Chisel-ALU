// See README.md for license details.

package counter

import chisel3._
import chiseltest._
import org.scalatest.freespec.AnyFreeSpec
import chisel3.experimental.BundleLiterals._

/**
  * This is a trivial example of how to run this Specification
  * From within sbt use:
  * {{{
  * testOnly gcd.GcdDecoupledTester
  * }}}
  * From a terminal shell use:
  * {{{
  * sbt 'testOnly gcd.GcdDecoupledTester'
  * }}}
  */
class counter_Spec extends AnyFreeSpec with ChiselScalatestTester {


  "test when clk === 10" in {
      test(new counter(clk_cycle = 10, n = 10)) {c => 
      c.io.ce.poke(true)
      c.clock.step(10)
      c.io.count.expect(1.U)
    }
  }

  "test when clk === 10, step 160" in {
      test(new counter(clk_cycle = 10, n = 10)) {c => 
      c.io.ce.poke(true)
      c.clock.step(160)
      c.io.count.expect(0.U)
    }
  }

  "test when ce == 0" in {
      test(new counter(clk_cycle = 10, n = 10)) {c => 
      c.io.ce.poke(false)
      c.clock.step(160)
      c.io.count.expect(0.U)
    }
  }

  "test when ce == 0 complex" in {
      test(new counter(clk_cycle = 10, n = 10)) {c => 
      c.io.ce.poke(false)
      c.clock.step(160)
      c.io.count.expect(0.U)
      c.clock.step(20)
      c.io.count.expect(0.U)
    }
  }

  "test complex" in {
      test(new counter(clk_cycle = 10, n = 10)) {c => 
      c.io.ce.poke(true)
      c.clock.step(160)
      c.io.count.expect(0.U)
      c.clock.step(20)
      c.io.count.expect(2.U)
      c.clock.step(40)
      c.io.count.expect(6.U)
      c.clock.step(40)
      c.io.count.expect(10.U)
      c.clock.step(40)
      c.io.count.expect(14.U)
      c.clock.step(40)
      c.io.count.expect(2.U)
      c.io.ce.poke(false)
      c.clock.step(160)
      c.io.count.expect(2.U)
      c.clock.step(20)
      c.io.count.expect(2.U)
    }
  }

}
