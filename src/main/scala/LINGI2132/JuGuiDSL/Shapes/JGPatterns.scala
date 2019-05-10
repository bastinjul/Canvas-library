package LINGI2132.JuGuiDSL.Shapes

import LINGI2132.JuGuiDSL.JGShape.JGShape
import org.scalajs.dom
import org.scalajs.dom.CanvasRenderingContext2D
import org.scalajs.dom.raw.{Event, HTMLImageElement}

case class JGPatterns(var src: String, patternType: String, rect: JGRectangle) extends JGShape(x = rect.x, y = rect.y) {


  override def inLimits(xInput: Double, yInput: Double): Boolean = {
    rect.inLimits(xInput, yInput)
  }

  override def draw(ctx: CanvasRenderingContext2D): Unit = {
    contextSetGlobalParameters(ctx)

    contextSetSpecificTypeParameters(ctx)

    contextSetTransformation(ctx)

    drawShape(ctx)

    contextResetTransformation(ctx)
  }

  override def drawShape(ctx: CanvasRenderingContext2D): Unit = {
    this.parameters.fill = true
    val image = dom.document.createElement("img").asInstanceOf[HTMLImageElement]
    image.src = src
    image.onload = { evt: Event =>
      val pattern = ctx.createPattern(image, patternType)
      ctx.fillStyle = pattern
      ctx.fillRect(rect.x, rect.y, rect.width, rect.height)
    }
  }
}
