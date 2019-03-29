package spot

import com.thoughtworks.binding.{Binding, dom}
import org.scalajs.dom.{Event, Node}

object UploadImageButton {
  Styles.add(
    """
      |.upload_button {
      |  color: black;
      |  border: 1px solid black;
      |  width: 400px;
      |  height: 300px;
      |  font-size: 32px;
      |  outline: none;
      |}
      |.size_button:active {
      |  opacity: 0.5;
      |}
      |
    """.stripMargin
  )

  @dom def apply(onClick: Event ⇒ Unit): Binding[Node] = {
    <button class="upload_button" onclick={ event: Event => onClick(event) }>UPLOAD IMAGE...</button>
  }
}
