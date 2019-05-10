package LINGI2132.JuGuiDSL.Interactions

import LINGI2132.JuGuiDSL.JGShape.JGShape
import org.scalajs.dom




trait Interactive {

}

trait Interaction [ApplyOn <: Interactive] {
  def interact(shape: JGShape, ctx: dom.CanvasRenderingContext2D, x: Double, y: Double)
}
