package LINGI2132.JuGuiDSL

import LINGI2132.JuGuiDSL.Animations.AnimationTraits.Animation
import LINGI2132.JuGuiDSL.Animations.{AnimationZone, LineDashAnimation, RotationAnimation, ScalingAnimation}
import LINGI2132.JuGuiDSL.Interactions.{ChangeColorInteraction, DisapearInteraction, Interaction}
import LINGI2132.JuGuiDSL.JGShape._
import org.scalajs.dom
import org.scalajs.dom.html
import scala.collection.mutable.ArrayBuffer


class Canvasjugui(canvas: html.Canvas) {
  val ctx : dom.CanvasRenderingContext2D = canvas.getContext("2d").asInstanceOf[dom.CanvasRenderingContext2D]

  var shapes = new ArrayBuffer[JGShape]

  def += (shape: Any): Unit = { // add shapes to buffer
    shape match{
      case a: Array[JGShape] => a.foreach(s => shapes.append(s))
      case sh : JGShape => shapes.append(sh)
    }
  }

  def draw() : Unit = { // draw each shape
    shapes.foreach(s => s.draw(ctx))
  }

  def anime(shape: JGShape, animation: Animation[JGShape], animationZone: AnimationZone): Unit = { // make animation happen
    animation match{
      case lineDashAnimation : LineDashAnimation => lineDashAnimation.anime(shape, ctx, animationZone)
      case rotationAnimation: RotationAnimation => rotationAnimation.anime(shape, ctx, animationZone)
      case scalingAnimation: ScalingAnimation => scalingAnimation.anime(shape, ctx, animationZone)
    }
  }

  var disapearInteractions = new ArrayBuffer[(JGShape, Interaction[JGShape])]
  var changeColorInteractions = new ArrayBuffer[(JGShape, Interaction[JGShape])]

  def interact(shape: JGShape, interaction: Interaction[JGShape]): Unit = {  // make interaction happen
    shape.draw(ctx)
    interaction match {
      case changeColorInteraction: ChangeColorInteraction => changeColorInteractions.append((shape, changeColorInteraction))
      case disapearInteraction: DisapearInteraction => disapearInteractions.append((shape, disapearInteraction))
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

