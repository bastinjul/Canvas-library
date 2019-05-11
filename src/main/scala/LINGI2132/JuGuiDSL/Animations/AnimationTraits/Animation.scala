package LINGI2132.JuGuiDSL.Animations.AnimationTraits

import LINGI2132.JuGuiDSL.Animations.AnimationZone
import LINGI2132.JuGuiDSL.JGShape.JGShape
import org.scalajs.dom

trait Animation [ApplyOn <: Animable] {
  /**
    * make an animation happen
    * @param shape the shape that has to be animated
    * @param ctx an instance of CanvasRenderingContext2D used for drawing shapes, text, ...
    * @param animationZone the place and area of where the animation takes place
    */
  def animate(shape: JGShape, ctx: dom.CanvasRenderingContext2D, animationZone: AnimationZone) : Unit

  /**
    * draws a new frame of the shape
    * @param shape the shape that has to be animated
    * @param ctx an instance of CanvasRenderingContext2D used for drawing shapes, text, ...
    * @param animationZone the place and area of where the animation takes place
    */
  def draw(shape: JGShape, ctx: dom.CanvasRenderingContext2D, animationZone: AnimationZone) : Unit

  /**
    * recursive function that makes the animation take place infinitely
    * @param shape the shape that has to be animated
    * @param ctx an instance of CanvasRenderingContext2D used for drawing shapes, text, ...
    * @param animationZone the place and area of where the animation takes place
    */
  def march(shape: JGShape, ctx: dom.CanvasRenderingContext2D, animationZone: AnimationZone): Unit
}
