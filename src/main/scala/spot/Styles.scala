package spot

import scala.scalajs.js

object Styles {
  def add(s: String): Unit = {
    val domStyle = js.Dynamic.global.document.createElement("style")
    domStyle.textContent = s
    js.Dynamic.global.document.head.appendChild(domStyle)
  }

  // generate class "square20x10
  def addRectStyle(d: Dim): Unit = {
    val height = 500
    val width = ((d.w.toDouble / d.h.toDouble) * 500).toInt
    val color = "#DDDDDD"
//    val color = "#808080"
    add(
      s"""
        |.rect${d.w}x${d.h} {
        |  width: ${width}px;
        |  height: ${height}px;
        |  background-image:
        |   linear-gradient(45deg, $color 25%, transparent 25%), linear-gradient(-45deg, $color 25%, transparent 25%), linear-gradient(45deg, transparent 75%, $color 75%), linear-gradient(-45deg, transparent 75%, $color 75%);
        |  background-size: 20px 20px;
        |  background-position: 0 0, 0 10px, 10px -10px, -10px 0px;
        |}
      """.stripMargin)
  }
}

