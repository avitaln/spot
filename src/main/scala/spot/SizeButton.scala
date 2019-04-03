package spot

import com.thoughtworks.binding.{Binding, dom}
import org.scalajs.dom.{Event, Node}

object SizeButton {
  val width = 200

  Styles.add(
    """
      |.size_button {
      |  color: black;
      |  border: none;
      |  font-size: 22px;
      |  outline: none;
      |  background: lightgoldenrodyellow;
      |  box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2), 0 6px 20px 0 rgba(0, 0, 0, 0.19);
      |}
      |.size_button:active {
      |  opacity: 0.5;
      |}
      |
    """.stripMargin
  )

  @dom def apply(orig: Dim, scaled: Dim, onClick: Event ⇒ Unit): Binding[Node] = {
    <button class="size_button" style={sizeStyle(scaled)} onclick={ event: Event => onClick(event) }>{s"${orig.w} X ${orig.h}".toUpperCase}</button>
  }

  def sizeStyle(scaled: Dim): String = {
//    s"width:100px;height:100px"
    s"width:${scaled.w}px;height:${scaled.h}px"
  }
}
