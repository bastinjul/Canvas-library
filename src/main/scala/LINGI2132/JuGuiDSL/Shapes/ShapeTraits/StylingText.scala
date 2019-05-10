package LINGI2132.JuGuiDSL.Shapes.ShapeTraits

trait StylingText{
  object textStyle {
    var font : String = "10px sans-serif"
    var textAlign : String = "start"
    var textBaseline : String = "alphabetic"
  }

  def font(font: String): Unit ={
    this.textStyle.font = font
  }

  def textAlign(textAlignment: String): Unit ={
    this.textStyle.textAlign = textAlignment
  }

  def textBaseline(textBaseline: String): Unit ={
    this.textStyle.textBaseline = textBaseline
  }
}
