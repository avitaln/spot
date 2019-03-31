package spot

import com.thoughtworks.binding.{Binding, dom}
import org.scalajs.dom.{Event, Node}

object SizeButton {
  Styles.add(
    """
      |.size_button {
      |  color: black;
      |  border: 1px solid black;
      |  font-size: 16px;
      |  outline: none;
      |}
      |.size_button:active {
      |  opacity: 0.5;
      |}
      |
    """.stripMargin
  )

  @dom def apply(orig: Dim, scaled: Dim, onClick: Event ⇒ Unit): Binding[Node] = {
    println(s"orig = $orig scaled = $scaled")

    <button class="size_button" style={sizeStyle(scaled)} onclick={ event: Event => onClick(event) }>{s"${orig.w} X ${orig.h}".toUpperCase}</button>
  }

  def sizeStyle(scaled: Dim): String = {
    s"width:${scaled.w}px;height:${scaled.h}px"
  }
}
