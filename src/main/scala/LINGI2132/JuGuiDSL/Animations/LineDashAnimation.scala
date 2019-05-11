package LINGI2132.JuGuiDSL.Animations

import LINGI2132.JuGuiDSL.Animations.AnimationTraits.Animation
import LINGI2132.JuGuiDSL.JGShape.JGShape
import org.scalajs.dom

import scala.scalajs.js.timers.setTimeout
import scala.scalajs.js.{Array => ArrayJs}


case class LineDashAnimation(lineDash: ArrayJs[Double]) extends Animation[JGShape] {
  var offset = 0

  override def animate(shape: JGShape, ctx: dom.CanvasRenderingContext2D, animationZone: AnimationZone): Unit = {
    shape.setLineDash(lineDash)
    march(shape, ctx, animationZone)
  }

  override def draw(shape:JGShape, ctx: dom.CanvasRenderingContext2D, animationZone: AnimationZone): Unit ={
    ctx.clearRect(animationZone.zone.x, animationZone.zone.y, animationZone.zone.width, animationZone.zone.height)
    shape.fill(false)
    shape.lineDashOffset(-this.offset)
    shape.setLineDash(lineDash)
    shape.draw(ctx)
  }

  override def march(shape:JGShape,ctx: dom.CanvasRenderingContext2D, animationZone: AnimationZone): Unit = {
    offset += 1
    if(offset > 16){
      offset = 0
    }
    draw(shape, ctx, animationZone)
    setTimeout(20) {
      march(shape, ctx, animationZone)
    }
  }
}