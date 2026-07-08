package ucie.digital

package object util {
  implicit class BooleanToAugmentedBoolean(val x: Boolean) extends AnyVal {
    def toInt: Int = if (x) 1 else 0
    def option[T](z: => T): Option[T] = if (x) Some(z) else None
  }
}
