package Project

sealed abstract class SVGAnimation

case class SVGFadeInAnimation(from: Double, end: Double, duration: Double, delay: Double) extends SVGAnimation
