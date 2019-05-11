package LINGI2132.JuGuiDSL.Shapes.ShapeTraits

/**
  * a trait specific for text
  */
trait StylingText{
  object textStyle {
    var font : String = "10px sans-serif"
    var textAlign : String = "start"
    var textBaseline : String = "alphabetic"
  }

  /**
    * sets the font according to
    * @param font
    */
  def font(font: String): Unit ={
    this.textStyle.font = font
  }

  /**
    * sets the alignment according to
    * @param textAlignment
    */
  def textAlign(textAlignment: String): Unit ={
    this.textStyle.textAlign = textAlignment
  }

  /**
    * sets the text baseline according to
    * @param textBaseline
    */
  def textBaseline(textBaseline: String): Unit ={
    this.textStyle.textBaseline = textBaseline
  }
}
