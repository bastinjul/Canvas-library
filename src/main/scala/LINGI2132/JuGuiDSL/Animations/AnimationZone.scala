package LINGI2132.JuGuiDSL.Animations

/**
  * used for specifying the place and area of where the animation will take place
  * @param xA
  * @param yA
  * @param widthA
  * @param heightA
  */
class AnimationZone(xA: Double, yA: Double, widthA: Double, heightA: Double) {
  object zone{
    val x : Double = xA
    val y : Double = yA
    val width : Double = widthA
    val height : Double = heightA
  }
}
