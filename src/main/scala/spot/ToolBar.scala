package spot

import com.thoughtworks.binding.Binding.Vars
import com.thoughtworks.binding.{Binding, dom}
import org.scalajs.dom.Node

object Toolbar {
  @dom def apply(components: Seq[Binding[Node]], sizes: Seq[Int], gap: Int = 10): Binding[Node] = {
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
//      "background: cyan",
      "justify-content: center",
          "background: transparent",
//      "width:100%",
      "display: grid",
      s"grid-template-columns: $sizesString",
      "grid-template-rows: auto",
      s"grid-gap: ${gap}px",
      "align-items: center",
      s"padding-left: ${gap}px"
    ).mkString(";")
  }
}
