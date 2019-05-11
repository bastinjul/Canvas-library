package LINGI2132.JuGuiDSL.ClassExtensions


import LINGI2132.JuGuiDSL.JGShape.JGShape

import scala.scalajs.js.{Array => ArrayJs}

/**
  * for declaring implicit classes
  */
object ClassExtensions {

  /**
    * called when applying transformations/operations on a group of shapes
    */
  implicit class jgShapeArrayExtend[ApplyOn <: JGShape](s: Array[ApplyOn]){
    def translateY(i: Double) : Array[ApplyOn] = {
      s.foreach(sh => sh.translateY(i))
      s
    }

    def translateX(i: Double) : Array[ApplyOn] = {
      s.foreach(sh => sh.translateX(i))
      s
    }

    def translate(x: Double, y: Double) : Array[ApplyOn] = {
      s.foreach(sh => sh.translate(x, y))
      s
    }

    def changeColor(c: String) : Array[ApplyOn] = {
      s.foreach(sh => sh.changeColor(c))
      s
    }

    def fill(b: Boolean) : Array[ApplyOn] = {
      s.foreach(sh => sh.fill(b))
      s
    }

    def strokeWidth(w: Double) : Array[ApplyOn] = {
      s.foreach(sh => sh.strokeWidth(w))
      s
    }

    def clearArea(b: Boolean) : Array[ApplyOn] = {
      s.foreach(sh => sh.clearArea())
      s
    }

    def transparency(t: Double) : Array[ApplyOn] = {
      s.foreach(sh => sh.transparency(t))
      s
    }

    def setLineDash(value: ArrayJs[Double]) : Array[ApplyOn] = {
      s.foreach(sh => sh.setLineDash(value))
      s
    }

    def lineDashOffset(value: Double) : Array[ApplyOn] = {
      s.foreach(sh => sh.lineDashOffset(value))
      s
    }

    def shadowOffsetX(offset : Double): Array[ApplyOn] = {
      s.foreach(sh => sh.shadowOffsetX(offset))
      s
    }

    def shadowOffsetY(offset: Double): Array[ApplyOn] = {
      s.foreach(sh => sh.shadowOffsetY(offset))
      s
    }

    def shadowBlur(blur: Double): Array[ApplyOn] = {
      s.foreach(sh => sh.shadowBlur(blur))
      s
    }

    def shadowColor(color: String): Array[ApplyOn] = {
      s.foreach(sh => sh.shadowColor(color))
      s
    }

    def rotate(rotation : Double, centerX : Double, centerY : Double): Array[ApplyOn] = {
      s.foreach(sh => sh.rotate(rotation, centerX, centerY))
      s
    }

    def scale(x: Double, y: Double): Array[ApplyOn] = {
      s.foreach(sh => sh.scale(x, y))
      s
    }

    def transform(horizontalScaling: Double, horizontalSkewing: Double, verticalSkewing: Double,
                  verticalScaling: Double, horizontalMoving: Double, verticalMoving: Double): Array[ApplyOn] = {
      s.foreach(sh => sh.transform(horizontalScaling, horizontalSkewing, verticalSkewing, verticalScaling, horizontalMoving, verticalMoving))
      s
    }

    def setGlobalCompositeOperation(typeCompositeOperation : String): Array[ApplyOn] ={
      s.foreach(sh => sh.setGlobalCompositeOperation(typeCompositeOperation))
      s
    }
  }
}