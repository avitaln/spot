package spot

import com.thoughtworks.binding.Binding.{Var, Vars}
import com.thoughtworks.binding.{Binding, dom}
import org.scalajs.dom.{Node, document}



object Main {

  val step: Var[Step] = Var(Step.materials)
  val chosenMaterlal: Var[Material] = Var(Material(""))
  val chosenSize: Var[String] = Var("")
  val materials: Vars[Material] = Vars.empty

  def main(args: Array[String]): Unit = {
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
      <h1>Chosen material: {chosenMaterlal.bind.name}</h1>
      <h1>Choose Size</h1>
      {
      Toolbar.apply(
        Seq(
          createSizeButton(1,1),
          createSizeButton(2,2),
          createSizeButton(3,3)
        ), 40
      ).bind
      }
    </div>
  }

  @dom
  def renderChooseMaterial(): Binding[Node] = {
    <div>
      <h1>Choose Material</h1>
      {
      Toolbar.apply(
        Seq(
          createMaterialButton("wood"),
          createMaterialButton("aluminum"),
          createMaterialButton("glass")
        )
      ).bind
      }
    </div>
  }

  @dom
  def renderChooseImage(): Binding[Node] = {
    <div>
      <h1>Chosen material: {chosenMaterlal.bind.name}</h1>
      <h1>Chosen size: {chosenSize.bind}</h1>
      <h1>Choose Image</h1>
      {UploadImageButton(e => {}).bind}
    </div>
  }

  def createMaterialButton(name: String): (Binding[Node], Int) =
    MaterialButton(name, e => {
      onSelectMaterial(Material(name))
    }) → 400

  def createSizeButton(w: Int, h: Int): (Binding[Node], Int) =
    SizeButton(w, h, e => {
      onSelectSize(s"$w x $h".toUpperCase)
    }) → (w * 100)

  def onSelectMaterial(material: Material): Unit = {
    chosenMaterlal.value = material
    step.value = Step.sizes
  }

  def onSelectSize(size: String): Unit = {
    step.value = Step.image
    chosenSize.value = size
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