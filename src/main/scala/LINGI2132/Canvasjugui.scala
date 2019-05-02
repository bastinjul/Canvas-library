package LINGI2132

import org.scalajs.dom.html

import scala.collection.mutable.ArrayBuffer

class Canvasjugui(canvas: html.Canvas) {

  var shapes = new ArrayBuffer[JGShape]

  def += (shape: Any): Unit = {
    shape match{
      case a: Array[JGShape] => a.foreach(s => shapes.append(s))
      case sh : JGShape => shapes.append(sh)
    }
  }

  def draw() : Unit = {
    shapes.foreach(s => s.draw())
  }

}

trait JGShape {

  object parameters {
    var color : String = "#000000"
    var strokeWidth : Double = 1.0
  }

  def fill() : Unit

  def stroke() : Unit

  def draw() : Unit

}

case class JGRectangle(x: Int, y: Int, width: Double, height: Double) extends JGShape {

  override def fill(): Unit = {}

  override def stroke(): Unit = {}

  override def draw(): Unit = {}
}

case class JGCircle(x: Int, y: Int, radius: Double) extends JGShape {

  override def fill(): Unit = {}

  override def stroke(): Unit = {}

  override def draw(): Unit = {}
}

case class JGCurve(x: Int, y: Int) extends JGShape
{
  override def fill(): Unit = {}

  override def stroke(): Unit = {}

  override def draw(): Unit = {}
}