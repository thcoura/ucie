// See LICENSE.SiFive for license details.
// Simplified: removed CDE dependency (Field/Parameters)

package ucie.digital.util

import chisel3._

abstract class ClockGate extends BlackBox {
  val io = IO(new Bundle{
    val in = Input(Clock())
    val en = Input(Bool())
    val out = Output(Clock())
  })
}

object ClockGate {
  def apply(in: Clock, en: Bool, name: Option[String] = None): Clock = {
    val cg = Module(new EICG_wrapper)
    name.foreach(cg.suggestName(_))
    cg.io.in := in
    cg.io.en := en
    cg.io.out
  }
  def apply(in: Clock, en: Bool, name: String): Clock = apply(in, en, Some(name))
}

// behavioral model of Integrated Clock Gating cell
class EICG_wrapper extends ClockGate
