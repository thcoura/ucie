package edu.berkeley.cs.ucie.digital
package e2e

import chisel3.stage.ChiselStage
import interfaces._
import sideband._
import logphy._
import ucie.digital.util.AsyncQueueParams

object GenerateVerilog extends App {
  (new ChiselStage).emitVerilog(
    new UCITop(
      fdiParams = FdiParams(width=64, dllpWidth=128, sbWidth=128),
      rdiParams = RdiParams(width=64, sbWidth=128),
      sbParams  = SidebandParams(),
      linkTrainingParams = LinkTrainingParams(),
      afeParams = AfeParams(mbLanes=16),
      laneAsyncQueueParams = AsyncQueueParams()
    ),
    Array("--target-dir", "generated")
  )
  println("UCITop.v → generated/")
}
