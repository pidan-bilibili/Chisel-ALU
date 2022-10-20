// See README.md for license details.

package fifo

import chisel3._
import chisel3.util._

/**
  * edge-detector
  */
class fifo(val WIDTH: Int, val DEPTH: Int) extends Module {
  val io = IO(new Bundle {
    val clk                   = IO(Input(Clock()))
    val reset                 = IO(Input(Bool()))

    val wr_en                 = Input(Bool())
    val d_in                  = Input(UInt((WIDTH-1).W))
    val full                  = Output(Bool())

    val rd_en                 = Input(Bool())
    val dout                  = Output(UInt((WIDTH-1).W))
    val empty                 = Output(Bool())
  })

  // decalue the memory
  val fifo = Reg(Vec(WIDTH-1, UInt((DEPTH-1).W)))
  val rd_counter = RegInit(0.U(log2Ceil(DEPTH - 1).W))
  val wr_counter = RegInit(0.U(log2Ceil(DEPTH - 1).W))
  val fifo_counter = RegInit(0.U((DEPTH - 1).W))


  // assign
  io.empty := (fifo_counter === 0.U)
  io.full := (fifo_counter === DEPTH.U)



  when (io.reset) {
    rd_counter := 0.U
    wr_counter := 0.U
    fifo_counter := 0.U
    fifo := 0.U
  }

  when (wr_counter === DEPTH.U) {
    wr_counter := 0.U
  }

  when (rd_counter === DEPTH.U) {
    rd_counter := 0.U
  }

  when (io.wr_en && io.rd_en && !io.empty && !io.full) {
    fifo(wr_counter) := io.d_in
    wr_counter := wr_counter + 1.U

    io.dout := fifo(rd_counter)
    rd_counter := rd_counter + 1.U
  }.elsewhen(io.rd_en && !io.empty) {
    io.dout := fifo(rd_counter)
    rd_counter := rd_counter + 1.U
    fifo_counter := fifo_counter - 1.U
  }.elsewhen (io.wr_en && !io.full) {
    fifo(wr_counter) := io.d_in
    wr_counter := wr_counter + 1.U
    fifo_counter := fifo_counter + 1.U
  }
}






