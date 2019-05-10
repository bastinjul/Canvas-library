package LINGI2132.JuGuiDSL.Shapes.ShapeTraits

import scala.collection.mutable.ArrayBuffer

trait ColorStop {
  object colors {
    var colorStop: ArrayBuffer[(Double, String)] = ArrayBuffer()
  }

  def addColorStop(offset: Double, color: String) : Unit = {
    this.colors.colorStop.append((offset, color))
  }
}