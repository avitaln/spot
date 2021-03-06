package spot

import com.thoughtworks.binding.{Binding, dom}
import org.scalajs.dom.{Event, Node}
import org.scalajs.dom.html.Button

object MaterialButton {
  val size = 300

  Styles.add(
    s"""
      |.material_container {
      |  position: relative;
      |  color: black;
      |  border: none;
      |  width: ${size}px;
      |  height: ${size}px;
      |  font-size: 32px;
      |  background: transparent;
      |  box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2), 0 6px 20px 0 rgba(0, 0, 0, 0.19);
      |}
      |
      |.material_centered {
      |  position: absolute;
      |  top: 50%;
      |  left: 50%;
      |  transform: translate(-50%, -50%);
      |}
      |
      |.material_image {
      |  width: 100%;
      |  height: 100%;
      |}
      |
      |.material_container:active {
      |  opacity: 0.5;
      |}
      |
    """.stripMargin
  )

  @dom def apply(item: Item, onClick: Event ⇒ Unit): Binding[Node] = {
    <div class="material_container" onclick={ event: Event => onClick(event) }>
      <img src={item.fullImageUrl} class="material_image" />
      <div class="material_centered">{item.name.toUpperCase}</div>
    </div>
  }
}
