package LINGI2132.JuGuiDSL.Animations

import LINGI2132.JuGuiDSL.Animations.AnimationTraits.Animation
import LINGI2132.JuGuiDSL.JGShape.JGShape
import org.scalajs.dom.CanvasRenderingContext2D

import scala.scalajs.js.timers.setTimeout

case class RotationAnimation(centerX: Double, centerY: Double) extends Animation[JGShape] {
  var angle : Double = 0
  override def animate(shape: JGShape, ctx: CanvasRenderingContext2D, animationZone: AnimationZone): Unit = {
    march(shape, ctx, animationZone)
  }

  override def draw(shape: JGShape, ctx: CanvasRenderingContext2D, animationZone: AnimationZone): Unit ={
    ctx.clearRect(animationZone.zone.x, animationZone.zone.y, animationZone.zone.width, animationZone.zone.height)
    shape.rotate(angle, centerX, centerY)
    shape.draw(ctx)
  }

  override def march(shape: JGShape, ctx: CanvasRenderingContext2D, animationZone: AnimationZone): Unit = {
    angle += Math.PI / 180
    if (angle == Math.PI) {
      angle = 0
    }
    draw(shape, ctx, animationZone)
    setTimeout(60) {
      march(shape, ctx, animationZone)
    }
  }
}
