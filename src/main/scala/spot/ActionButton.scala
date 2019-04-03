package spot

import com.thoughtworks.binding.{Binding, dom}
import org.scalajs.dom.{Event, Node}

object ActionButton {
  //  border: 1px solid black;
  Styles.add(
    """
      |.action_button {
      |  font-size: 24px;
      |  outline: none;
      |  background: #3899ec;
      |  border: none;
      |  color: white;
      |  font-weight: 700;
      |  width:300px;
      |  height:100px;
      |  border-radius: 50px;
      |  box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2), 0 6px 20px 0 rgba(0, 0, 0, 0.19);
      |}
      |.action_button:active {
      |  opacity: 0.5;
      |}
      |
    """.stripMargin
  )

  @dom def apply(text: String, onClick: Event ⇒ Unit): Binding[Node] = {
    <button class="action_button" onclick={ event: Event => onClick(event) }><bdo dir="rtl">{text}</bdo></button>
  }

}
