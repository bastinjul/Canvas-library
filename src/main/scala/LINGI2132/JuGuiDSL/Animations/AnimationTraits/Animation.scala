package LINGI2132.JuGuiDSL.Animations.AnimationTraits

import LINGI2132.JuGuiDSL.Animations.AnimationZone
import LINGI2132.JuGuiDSL.JGShape.JGShape
import org.scalajs.dom

trait Animation [ApplyOn <: Animable] {
  def anime(shape: JGShape, ctx: dom.CanvasRenderingContext2D, animationZone: AnimationZone) : Unit
  def draw(shape: JGShape, ctx: dom.CanvasRenderingContext2D, animationZone: AnimationZone) : Unit
  def march(shape: JGShape, ctx: dom.CanvasRenderingContext2D, animationZone: AnimationZone): Unit
}
