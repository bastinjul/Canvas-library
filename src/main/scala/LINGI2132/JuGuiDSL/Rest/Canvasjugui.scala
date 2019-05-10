package LINGI2132.JuGuiDSL.Rest

import LINGI2132.JuGuiDSL.Animations.AnimationTraits.Animation
import LINGI2132.JuGuiDSL.Animations.{AnimationZone, LineDashAnimation, RotationAnimation, ScalingAnimation}
import LINGI2132.JuGuiDSL.Interactions.{ChangeColorInteraction, DisapearInteraction, Interaction}
import org.scalajs.dom
import org.scalajs.dom.{html}

import scala.collection.mutable.{ArrayBuffer}
import LINGI2132.JuGuiDSL.JGShape._


class Canvasjugui(canvas: html.Canvas) {
  val ctx : dom.CanvasRenderingContext2D = canvas.getContext("2d").asInstanceOf[dom.CanvasRenderingContext2D]

  var shapes = new ArrayBuffer[JGShape]

  def += (shape: Any): Unit = {
    shape match{
      case a: Array[JGShape] => a.foreach(s => shapes.append(s))
      case sh : JGShape => shapes.append(sh)
    }
  }

  def draw() : Unit = {
    shapes.foreach(s => s.draw(ctx))
  }

  def anime(shape: JGShape, animation: Animation[JGShape], animationZone: AnimationZone): Unit = {
    animation match{
      case lineDashAnimation : LineDashAnimation => lineDashAnimation.anime(shape, ctx, animationZone)
      case rotationAnimation: RotationAnimation => rotationAnimation.anime(shape, ctx, animationZone)
      case scalingAnimation: ScalingAnimation => scalingAnimation.anime(shape, ctx, animationZone)
    }
  }

  def interact(shape: JGShape, interaction: Interaction[JGShape]): Unit = {
    shape.draw(ctx)
    interaction match {
      case changeColorInteraction: ChangeColorInteraction => changeColorInteractions.append((shape, changeColorInteraction))
      case disapearInteraction: DisapearInteraction => disapearInteractions.append((shape, disapearInteraction))
    }
  }

  var changeColorInteractions = new ArrayBuffer[(JGShape, Interaction[JGShape])]
  var disapearInteractions = new ArrayBuffer[(JGShape, Interaction[JGShape])]

  canvas.onclick = {
    e: dom.MouseEvent => {
      disapearInteractions.foreach(i => {
        i._2.interact(i._1, ctx, e.clientX, e.clientY)
      })
    }
  }

  canvas.ondblclick = {
    e: dom.MouseEvent => {
      changeColorInteractions.foreach(i => {
        i._2.interact(i._1, ctx, e.clientX, e.clientY)
      })
    }
  }

}

