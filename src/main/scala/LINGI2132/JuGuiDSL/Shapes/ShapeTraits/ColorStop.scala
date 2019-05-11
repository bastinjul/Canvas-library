package LINGI2132.JuGuiDSL.Shapes.ShapeTraits

import scala.collection.mutable.ArrayBuffer

/**
  * a trait used for gradients
  */
trait ColorStop {
  object colors {
    var colorStop: ArrayBuffer[(Double, String)] = ArrayBuffer()
  }

  /**
    * appends a tuple of the following parameters to the colorStop arrayBuffer
    * @param offset
    * @param color
    */
  def addColorStop(offset: Double, color: String) : Unit = {
    this.colors.colorStop.append((offset, color))
  }
}