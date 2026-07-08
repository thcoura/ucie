// Vendored from rocket-chip. Simplified: replaced AsyncResetShiftReg
// with Chisel3 built-in ShiftRegister to avoid Chisel2 compat issues.
package ucie.digital.util
import chisel3._
import chisel3.util._

object AsyncResetSynchronizerShiftReg {
  def apply[T <: Data](in: T, depth: Int, init: Int = 0, name: Option[String] = None): T = {
    val reg = ShiftRegister(in, depth)
    name.foreach(reg.suggestName(_))
    reg
  }
  def apply[T <: Data](in: T, depth: Int, name: Option[String]): T = apply(in, depth, 0, name)
  def apply[T <: Data](in: T, depth: Int, init: T, name: Option[String]): T = apply(in, depth, 0, name)
  def apply[T <: Data](in: T, depth: Int, init: T): T = apply(in, depth, 0, None)
}
object SynchronizerShiftReg {
  def apply[T <: Data](in: T, sync: Int = 3, name: Option[String] = None): T =
    if (sync == 0) in else { val r = ShiftRegister(in, sync); name.foreach(r.suggestName(_)); r }
}
