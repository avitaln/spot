package spot

import scala.scalajs.js

object Styles {
  def add(s: String): Unit = {
    val domStyle = js.Dynamic.global.document.createElement("style")
    domStyle.textContent = s
    js.Dynamic.global.document.head.appendChild(domStyle)
  }
}

