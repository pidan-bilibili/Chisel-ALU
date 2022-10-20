// See README.md for license details.

package counter

import chisel3._
import chisel3.util._

/**
  * edge-detector
  */
class counter(val clk_cycle: Int, val n: Int) extends Module {
  val io = IO(new Bundle {
    val ce                    = Input(Bool())
    val count                 = Output(UInt(log2Ceil(n + 1).W))
  })

  val cycle = RegInit(1.U(log2Ceil(clk_cycle + 1).W))
  val output = RegInit(0.U(log2Ceil(n + 1).W))

  io.count := output

  when (io.ce) {
    when (cycle === clk_cycle.U) {
      output := output + 1.U
      cycle := 1.U
    }.otherwise {
      cycle := cycle + 1.U
    }
  }

}






