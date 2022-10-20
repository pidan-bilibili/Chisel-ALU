// See README.md for license details.

package edge_detector

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
class edge_detector_Spec extends AnyFreeSpec with ChiselScalatestTester {


  "test when signal === 0-0" in {
      test(new edge_detector(width = 1)) {c => 
      c.io.signal_in.poke(0.U)
      c.clock.step(1)
      c.io.signal_in.poke(0.U)
      c.io.edge_detect_pulse.expect(0.U)
    }
  }


  "test when signal === 0-1" in {
      test(new edge_detector(width = 1)) {c => 
      c.io.signal_in.poke(0.U)
      c.clock.step(1)
      c.io.signal_in.poke(1.U)
      c.io.edge_detect_pulse.expect(1.U)
    }
  }

  "test when signal === 1-0" in {
      test(new edge_detector(width = 1)) {c => 
      c.io.signal_in.poke(1.U)
      c.clock.step(1)
      c.io.signal_in.poke(0.U)
      c.io.edge_detect_pulse.expect(0.U)
    }
  }

  "test when signal == 1-1" in {
      test(new edge_detector(width = 1)) {c => 
      c.io.signal_in.poke(1.U)
      c.clock.step(1)
      c.io.signal_in.poke(1.U)
      c.io.edge_detect_pulse.expect(0.U)
    }
  }

  "test with 4-bits width 0" in {
      test(new edge_detector(width = 4)) {c => 
      c.io.signal_in.poke("b1010".U)
      c.clock.step(1)
      c.io.signal_in.poke("b1001".U)
      c.io.edge_detect_pulse.expect("b0001".U)
    }
  }

  "test with 4-bits width 1" in {
      test(new edge_detector(width = 4)) {c => 
      c.io.signal_in.poke("b1111".U)
      c.clock.step(1)
      c.io.signal_in.poke("b0000".U)
      c.io.edge_detect_pulse.expect("b0000".U)
    }
  }
}
