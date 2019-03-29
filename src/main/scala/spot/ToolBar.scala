package spot

import com.thoughtworks.binding.Binding.Vars
import com.thoughtworks.binding.{Binding, dom}
import org.scalajs.dom.Node

object Toolbar {
  @dom def apply(componentsAndSizes: Seq[(Binding[Node], Int)], gap: Int = 10): Binding[Node] = {
    val components = componentsAndSizes.map(_._1)
    val sizes = componentsAndSizes.map(_._2)
    <div style={toolbarStyle(sizes, gap)}>
      {
      for (comp <- Vars.apply(components:_*)) yield {
        comp.bind
      }
      }
    </div>
  }

  private def toolbarStyle(sizes: Seq[Int], gap: Int) = {
    val sizesString = sizes.map(s ⇒ s"${s}px").mkString(" ")
    Seq(
      "background: white",
      "width:100%",
      "display: grid",
      "align-items: left",
      s"grid-template-columns: $sizesString",
      "grid-template-rows: auto",
      s"grid-gap: ${gap}px",
      "align-items: center",
      s"padding-left: ${gap}px"
    ).mkString(";")
  }
}
