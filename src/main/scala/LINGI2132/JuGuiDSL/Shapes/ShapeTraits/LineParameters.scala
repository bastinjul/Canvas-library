package LINGI2132.JuGuiDSL.Shapes.ShapeTraits

import org.scalajs.dom

/**
  * a trait specific for lines
  */
trait LineParameters {
  object lineParameters {
    var strokeLineCap : String = "butt"
    var lineJoin : String = "round"
    var miterLimit : Double = 10.0
  }

  /**
    * sets the stroke linecap according to
    * @param endType
    */
  def strokeLineCap(endType: String) : Unit = {
    this.lineParameters.strokeLineCap = endType
  }

  /**
    * sets the linejoin according to
    * @param ltype
    */
  def lineJoin(ltype: String) : Unit = {
    this.lineParameters.lineJoin = ltype
  }

  /**
    * sets the miterLimit according to
    * @param value
    */
  def miterLimit(value: Double) : Unit = {
    this.lineParameters.miterLimit = value
  }

  /**
    * sets all parameters of the CanvasRenderingContext2D instance to the
    * current values of the lineParameters object
    * @param ctx an instance of CanvasRenderingContext2D used for drawing shapes, text, ...
    */
  def setParameters(ctx: dom.CanvasRenderingContext2D): Unit ={
    ctx.lineCap = this.lineParameters.strokeLineCap
    ctx.lineJoin = this.lineParameters.lineJoin
    ctx.miterLimit = this.lineParameters.miterLimit
  }

}