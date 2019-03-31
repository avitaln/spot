package spot

import com.thoughtworks.binding.Binding.{Var, Vars}
import com.thoughtworks.binding.{Binding, dom}
import org.scalajs.dom.{Node, document}

object Main {

  org.scalajs.dom.window.onmessage = (e) ⇒ {
    import upickle.default._
    val items = read[Items](e.data.toString)
  }

  val step: Var[Step] = Var(Step.materials)
  val selectedProduct: Var[Option[Item]] = Var(None)
  val chosenSize: Var[Option[Dim]] = Var(None)
  val items: Vars[Item] = Vars.empty

  def main(args: Array[String]): Unit = {
    items.value.appendAll(Items.dummy.items)

    val element = document.getElementById("main")
    dom.render(element, mainComponent)
  }

  @dom
  def mainComponent: Binding[Node] = {
    <div>
      {
      val x = step.bind match {
      case Step.materials ⇒ renderChooseMaterial()
      case Step.sizes ⇒ renderChooseSizes()
      case Step.image ⇒ renderChooseImage()
      }
      x.bind
      }
    </div>
  }

  @dom
  def renderChooseSizes(): Binding[Node] = {
    <div>
      <h1>Chosen material: {selectedProduct.bind.map(_.name).getOrElse("")}</h1>
      <h1>Choose Size</h1>
      {
        val x = selectedProduct.bind.map { item =>
          val buttons = item.dimChoices.zip(item.dimChoicesScaled).map { case(orig, scaled) =>
            createSizeButton(orig, scaled)
          }
          val sizes = List.fill(3)(item.largestWidth * item.scale) // 5 in a row
          Toolbar.apply(buttons, sizes, 20)
        }.getOrElse {
          empty
        }
        x.bind
      }
    </div>
  }

  @dom def empty: Binding[Node] = {
    <div>aaa</div>
  }

  @dom
  def renderChooseMaterial(): Binding[Node] = {
    <div>
      <h1>בחר מוצר</h1>
      {
      val boundItems = items.bind
      val sizes = List.fill(3)(MaterialButton.size) // 3 in a row

      Toolbar.apply(
        items.bind.map(createMaterialButton), sizes
      ).bind
      }
    </div>
  }

  @dom
  def renderChooseImage(): Binding[Node] = {
    <div>
      <h1>Chosen material: {selectedProduct.bind.map(_.name).getOrElse("")}</h1>
      <h1>Chosen size: {chosenSize.bind.map(d => s"${d.w} X ${d.h}").getOrElse("")}</h1>
      <h1>Choose Image</h1>
      {UploadImageButton(e => {}).bind}
    </div>
  }

  def createMaterialButton(item: Item): Binding[Node] =
    MaterialButton(item, e => {
      onSelectMaterial(item)
    })

  def createSizeButton(orig: Dim, scaled: Dim): Binding[Node] = {
    SizeButton(orig, scaled, e => onSelectSize(orig))
  }

  def onSelectMaterial(item: Item): Unit = {
    selectedProduct.value = Some(item)
    step.value = Step.sizes
  }

  def onSelectSize(dim: Dim): Unit = {
    step.value = Step.image
    chosenSize.value = Some(dim)
  }
}
/*

<html>
  <head>
    <style>
      .button {
        background-color: #155DE9;
        color: white;
        border: none;
        padding: 10px 20px;
        text-align: center;
        font-family: 'Arial';
      }
      .label {
        font-family: 'Arial';
      }
      .input {
        font-size: 14px;
      }
    </style>

    <script type="text/javascript">
      // when a message is received from the page code
      window.onmessage = (event) => {
        if (event.data) {
          document.getElementById("theLabel").innerHTML = event.data;
        }
      };

      // send message to the page code
      function button_click() {
        window.parent.postMessage(document.getElementById("theMessage").value, "*");
      }
    </script>
  </head>

  <body>
    <span id="theLabel" class="label">HTML Label</span>
    <br/><br/>
    <button class="button" onclick="button_click()">< Send Message</button>
    <br/><br/>
    <input type="text" class="input" id="theMessage">
  </body>
</html>
 */