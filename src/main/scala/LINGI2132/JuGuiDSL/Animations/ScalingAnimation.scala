package LINGI2132.JuGuiDSL.Animations

import LINGI2132.JuGuiDSL.Animations.AnimationTraits.Animation
import LINGI2132.JuGuiDSL.JGShape.JGShape
import org.scalajs.dom.CanvasRenderingContext2D

import scala.scalajs.js.timers.setTimeout

case class ScalingAnimation(minXScale : Double, minYScale: Double, maxXScale: Double, maxYScale: Double) extends Animation[JGShape]{
  var scaleX : Double = minXScale
  var scaleY : Double = minYScale
  var ascent : Boolean = true

  override def animate(shape: JGShape, ctx: CanvasRenderingContext2D, animationZone: AnimationZone): Unit = {
    march(shape, ctx, animationZone)
  }

  override def draw(shape: JGShape, ctx: CanvasRenderingContext2D, animationZone: AnimationZone): Unit = {
    ctx.clearRect(animationZone.zone.x, animationZone.zone.y, animationZone.zone.width, animationZone.zone.height)
    shape.scale(scaleX, scaleY)
    shape.draw(ctx)
  }

  override def march(shape: JGShape, ctx: CanvasRenderingContext2D, animationZone: AnimationZone): Unit = {
    if (ascent) {
      scaleX += (maxXScale - minYScale) / 60
      scaleY += (maxYScale - minYScale) / 60
    }
    else {
      scaleX -= (maxXScale - minYScale) / 60
      scaleY -= (maxYScale - minYScale) / 60
    }
    if (scaleX >= maxXScale) {
      ascent = false
    }
    if (scaleX <= minXScale) {
      ascent = true
    }

    draw(shape, ctx, animationZone)
    setTimeout(60) {
      march(shape, ctx, animationZone)
    }
  }
}