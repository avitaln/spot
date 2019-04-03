package spot

import com.thoughtworks.binding.Binding.{BindingInstances, Var, Vars}
import com.thoughtworks.binding.{Binding, dom}
import org.scalajs.dom.{Node, document}

import scala.scalajs.js

object Main {

  val SELECT_MATERIAL = "בחירת חומר"
  val SELECT_SIZE = "בחירת מידות"
  val UPLOAD_IMAGE = "העלאת תמונה"
  val EDIT_IMAGE = "התאמת תמונה למסגרת"
  val CROP = "חיתוך תמונה"
  val FIT = "כל התמונה בתוך המסגרת"
  val ADD_TO_CART = "הוסף לעגלת הקניות"

  // application state
  val dev = false
  val step: Var[Step] = Var(Step.materials)
  val selectedProduct: Var[Option[Item]] = Var(None)
  val selectedSize: Var[Option[Dim]] = Var(None)
  val items: Vars[Item] = Vars.empty
  val selectedUrl: Var[Option[String]] = Var(None)
  val imageFit: Var[Boolean] = Var(true) // otherwise crop
  val cropSelected: Binding[Boolean] = BindingInstances.map(imageFit)(x ⇒ !x)
  val fitSelected: Binding[Boolean]= BindingInstances.map(imageFit)(x ⇒ x)
  BindingInstances.map(step) {
    case Step.uploadingImage ⇒ org.scalajs.dom.window.parent.postMessage("show", "*")
    case _ ⇒ org.scalajs.dom.window.parent.postMessage("hide", "*")
  }.watch()

  //https://css-tricks.com/almanac/properties/o/object-position/
//  val objectPosX: Var[Int] = Var(50)
//  val objectPosY: Var[Int] = Var(50)

  //image://v1/59709c_b23ab4d91c204bc0acfa2e205a4e746e~mv2.png/888_550/59709c_b23ab4d91c204bc0acfa2e205a4e746e~mv2.png
  //https://static.wixstatic.com/media/b1ae3f_d5e107ccc9fb43e8a5d77dbb66048f54~mv2_d_4032_3024_s_4_2.jpg
  org.scalajs.dom.window.onmessage = e ⇒ {
    import upickle.default._
    val strMessage = e.data.toString
    if (strMessage.startsWith("U")) {
      val url = strMessage.substring(1)
      println("received url = " + url)
      val newUrl = "https://static.wixstatic.com/media/"+url.split("/")(3)
      println("global url = " + newUrl)
      setSelectedUrl(newUrl)
    } else if (strMessage.startsWith("I")) {
      val items = read[Items](strMessage.substring(1))
    }
  }

  private def setSelectedUrl(url: String): Unit = {
    selectedUrl.value = Some(url)
    step.value = Step.editingImage
  }

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
      case Step.materials ⇒ renderChooseMaterial1()
      case Step.sizes ⇒ renderChooseSizes2()
      case Step.uploadingImage ⇒ renderImageUploading3()
      case Step.editingImage ⇒ renderEditImage4()
      case _ => empty
      }
      x.bind
      }
    </div>
  }

  @dom def empty: Binding[Node] = <div>EMPTY</div>

  @dom
  def renderChooseMaterial1(): Binding[Node] = {
    <div>
      {selectMaterialLabel().bind}
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
  def renderChooseSizes2(): Binding[Node] = {
    <div>
      {selectMaterialLabel(withCheck = true).bind}
      {selectSizeLabel().bind}
      {
      val x = selectedProduct.bind.map { item =>
        val buttons = item.dimChoices.zip(item.dimChoicesScaled).map { case(orig, scaled) =>
          createSizeButton(orig, scaled)
        }
        val sizes = List.fill(3)(SizeButton.width) // 3 in a row
        Toolbar.apply(buttons, sizes, 20)
      }.getOrElse {
        empty
      }
      x.bind
      }
    </div>
  }

  @dom
  def renderImageUploading3(): Binding[Node] = {
    <div>
      {selectMaterialLabel(withCheck = true).bind}
      {selectSizeLabel(withCheck = true).bind}
      {uploadImageLabel().bind}
    </div>
  }

  @dom
  def renderEditImage4(): Binding[Node] = {
    val imageStyle = if (imageFit.bind)
      s"height:100%;width:100%;object-fit:contain"
    else
      s"height:100%;width:100%;object-fit:cover"

    <div>
      {selectMaterialLabel(withCheck = true).bind}
      {selectSizeLabel(withCheck = true).bind}
      {uploadImageLabel(withCheck = true).bind}
      {editImageLabel().bind}
      <br/>
      <div class={selectedSize.bind.map(d => s"rect${d.w}x${d.h}").getOrElse("")}
           style="overflow:hidden;border:none;display:table;margin:0 auto;box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2), 0 6px 20px 0 rgba(0, 0, 0, 0.19);">
        <img style={imageStyle} src={selectedUrl.bind.getOrElse("")}/>
      </div>
      <br/><br/>
      {
        Toolbar(
          Seq(
            SelectionButton(CROP, e => { imageFit.value = false }, cropSelected),
            SelectionButton(FIT, e => { imageFit.value = true }, fitSelected),
          ),
          Seq(200,200)
        ).bind
      }
      <br/><br/>
      {
        Toolbar(
          Seq(
            ActionButton(ADD_TO_CART, e => { addToCart }),
          ),
          Seq(300)
        ).bind
      }
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
    Styles.addRectStyle(dim)
    selectedSize.value = Some(dim)
    if (dev)
      setSelectedUrl("https://static.wixstatic.com/media/b1ae3f_d5e107ccc9fb43e8a5d77dbb66048f54~mv2_d_4032_3024_s_4_2.jpg")
    else {
      step.value = Step.uploadingImage
    }
  }

  def selectMaterialLabel(withCheck: Boolean = false): Binding[Node] =
    Label(SELECT_MATERIAL, e ⇒ { step.value = Step.materials}, withCheck = withCheck)

  def selectSizeLabel(withCheck: Boolean = false): Binding[Node] =
    Label(SELECT_SIZE, e ⇒ { step.value = Step.sizes}, withCheck = withCheck)

  def uploadImageLabel(withCheck: Boolean = false): Binding[Node] =
    Label(UPLOAD_IMAGE, e ⇒ { step.value = Step.uploadingImage}, withCheck = withCheck)

  def editImageLabel(withCheck: Boolean = false): Binding[Node] =
    Label(EDIT_IMAGE, e ⇒ { step.value = Step.editingImage}, withCheck = withCheck)

  def addToCart = {
    val productId = selectedProduct.value.map(_.id).getOrElse("")
    val size = selectedSize.value.map(_.toChoice).getOrElse("")
    val url = selectedUrl.value.getOrElse("")
    val data = js.Array(productId, size, url)
    org.scalajs.dom.window.parent.postMessage(data, "*")
  }

}
