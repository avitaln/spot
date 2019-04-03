package spot

import com.thoughtworks.binding.{Binding, dom}
import org.scalajs.dom.{Event, Node}

object Button {
  //  border: 1px solid black;
  Styles.add(
    """
      |.plain_button {
      |  color: black;
      |  font-size: 24px;
      |  outline: none;
      |  background: #EFEFEF;
      |  border: none;
      |
      |  width:200px;
      |  height:100px;
      |  box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2), 0 6px 20px 0 rgba(0, 0, 0, 0.19);
      |}
      |.plain_button:active {
      |  opacity: 0.5;
      |}
      |
    """.stripMargin
  )

  @dom def apply(text: String, onClick: Event ⇒ Unit, selected: Binding[Boolean]): Binding[Node] = {
    <button class="plain_button" style={calcStyle(selected.bind)} onclick={ event: Event => onClick(event) }><bdo dir="rtl">{text}</bdo></button>
  }

  private def calcStyle(value: Boolean): String = {
    if (value) "border-bottom: 4px solid blue;" else ""
  }
}
