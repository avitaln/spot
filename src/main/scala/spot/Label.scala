package spot

import com.thoughtworks.binding.{Binding, dom}
import org.scalajs.dom.{Event, Node}

object Label {
  private val fontSize =

  Styles.add(
    """
      |.my_label {
      |  font-family: Rubik;
      |  padding: 6px;
      |}
    """.stripMargin
  )

  @dom def apply(text: String, onClick: Event ⇒ Unit = e ⇒ {}, withCheck: Boolean = false): Binding[Node] = {
    <div align="center" class="my_label" style={style(withCheck)} onclick={event: Event => onClick(event) }>
      <bdo dir="rtl">{text}</bdo>
      {
      if (withCheck)
        check.bind
      else
        dot.bind
      }
    </div>
  }

  @dom def check: Binding[Node] =
    <a style="font-family:wingdings2;font-weight:700">P</a>

  @dom def dot: Binding[Node] =
    <a style="font-family:wingdings2;font-weight:700">B</a>

  def style(withCheck: Boolean): String =
    if (withCheck) "color:darkgray;font-size:30px" else "color: black;font-size:36px"
}
