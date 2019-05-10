package LINGI2132.JuGuiDSL.Shapes.ShapeTraits

import org.scalajs.dom

trait LineParameters {
  object lineParameters {
    var strokeLineCap : String = "butt"
    var lineJoin : String = "round"
    var miterLimit : Double = 10.0
  }

  def strokeLineCap(endType: String) : Unit = {
    this.lineParameters.strokeLineCap = endType
  }

  def lineJoin(ltype: String) : Unit = {
    this.lineParameters.lineJoin = ltype
  }

  def miterLimit(value: Double) : Unit = {
    this.lineParameters.miterLimit = value
  }

  def setParameters(ctx: dom.CanvasRenderingContext2D): Unit ={
    ctx.lineCap = this.lineParameters.strokeLineCap
    ctx.lineJoin = this.lineParameters.lineJoin
    ctx.miterLimit = this.lineParameters.miterLimit
  }

}