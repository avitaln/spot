package spot

import com.thoughtworks.binding.{Binding, dom}
import org.scalajs.dom.{Event, Node}

object SizeButton {
  Styles.add(
    """
      |.size_button {
      |  color: black;
      |  border: 1px solid black;
      |  width: 400px;
      |  height: 400px;
      |  font-size: 32px;
      |  outline: none;
      |}
      |.size_button:active {
      |  opacity: 0.5;
      |}
      |
    """.stripMargin
  )

  @dom def apply(w: Int, h: Int, onClick: Event ⇒ Unit): Binding[Node] = {
    <button class="size_button" style={sizeStyle(w,h)} onclick={ event: Event => onClick(event) }>{s"$w x $h".toUpperCase}</button>
  }

  def sizeStyle(w: Int, h: Int): String = {
    s"width:${w*100}px;height:${h*100}px"
  }
}
