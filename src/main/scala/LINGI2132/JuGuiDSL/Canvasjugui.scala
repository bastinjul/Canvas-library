package LINGI2132.JuGuiDSL

import LINGI2132.JuGuiDSL.Animations.AnimationTraits.Animation
import LINGI2132.JuGuiDSL.Animations.{AnimationZone, LineDashAnimation, RotationAnimation, ScalingAnimation}
import LINGI2132.JuGuiDSL.Interactions.InteractionTraits.Interaction
import LINGI2132.JuGuiDSL.Interactions.{ChangeColorInteraction, DisappearInteraction}
import LINGI2132.JuGuiDSL.JGShape._
import org.scalajs.dom
import org.scalajs.dom.html

import scala.collection.mutable.ArrayBuffer


class Canvasjugui(canvas: html.Canvas) {
  val ctx : dom.CanvasRenderingContext2D = canvas.getContext("2d").asInstanceOf[dom.CanvasRenderingContext2D]

  var shapes = new ArrayBuffer[JGShape]

  /**
    * Add shapes to arraybuffer
    * @param shape: A shape that extends JGShape
    */
  def += (shape: Any): Unit = {
    shape match{
      case a: Array[JGShape] => a.foreach(s => shapes.append(s))
      case sh : JGShape => shapes.append(sh)
    }
  }

  /**
    * draw each shape contained in the buffer
    */
  def draw() : Unit = { // draw each shape
    shapes.foreach(s => s.draw(ctx))
  }

  /**
    * Make an animation happen
    * @param shape  A shape that extends JGShape
    * @param animation An animation that extends Animation[JGShape]
    * @param animationZone The area in which the animation will take place
    */
  def animate(shape: JGShape, animation: Animation[JGShape], animationZone: AnimationZone): Unit = {
    animation match{
      case lineDashAnimation : LineDashAnimation => lineDashAnimation.animate(shape, ctx, animationZone)
      case rotationAnimation: RotationAnimation => rotationAnimation.animate(shape, ctx, animationZone)
      case scalingAnimation: ScalingAnimation => scalingAnimation.animate(shape, ctx, animationZone)
    }
  }

  var disapearInteractions = new ArrayBuffer[(JGShape, Interaction[JGShape])]
  var changeColorInteractions = new ArrayBuffer[(JGShape, Interaction[JGShape])]

  /**
    * Make an interaction happen
    * @param shape A shape that extends JGShape
    * @param interaction An interaction that extends Interaction[JGShape]
    */
  def interact(shape: JGShape, interaction: Interaction[JGShape]): Unit = {
    shape.draw(ctx)
    interaction match {
      case changeColorInteraction: ChangeColorInteraction => changeColorInteractions.append((shape, changeColorInteraction))
      case disapearInteraction: DisappearInteraction => disapearInteractions.append((shape, disapearInteraction))
    }
  }

  canvas.onclick = { // interact when canvas clicked
    e: dom.MouseEvent => {
      disapearInteractions.foreach(i => {
        i._2.interact(i._1, ctx, e.clientX, e.clientY)
      })
    }
  }

  canvas.ondblclick = { // interact when canvas clicked
    e: dom.MouseEvent => {
      changeColorInteractions.foreach(i => {
        i._2.interact(i._1, ctx, e.clientX, e.clientY)
      })
    }
  }
}

