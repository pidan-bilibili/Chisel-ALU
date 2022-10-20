// See README.md for license details.

package edge_detector

import chisel3._
import chisel3.util._

/**
  * edge-detector
  */
class edge_detector(val width: Int) extends Module {
  val io = IO(new Bundle {
    val signal_in             = Input(UInt(width.W))
    val edge_detect_pulse     = Output(UInt(width.W))
  })

  val reg = Reg(UInt(width.W))
  reg := io.signal_in
  io.edge_detect_pulse := io.signal_in & ~reg

}
