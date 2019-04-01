package spot

import upickle.default._

case class Dim(w: Int, h: Int)
case class Item(id: String, name: String, media: String, choices: List[String]) {

  lazy val fullImageUrl: String = {
    "https://static.wixstatic.com/media/" + media.split("/")(3)
  }

  lazy val dimChoices: List[Dim] = {
    choices.map { s ⇒
      val w = s.split("X")(0).toInt
      val h = s.split("X")(1).toInt
      Dim(w, h)
    }
  }

  lazy val largestWidth = dimChoices.map(_.w).max

  lazy val scale = 450 / largestWidth

  lazy val dimChoicesScaled: List[Dim] = {
    dimChoices.map(d ⇒ Dim(d.w*scale, d.h*scale))
  }

}
object Item{
  implicit val rw: ReadWriter[Item] = macroRW
}

case class Items(items: List[Item])
object Items{
  implicit val rw: ReadWriter[Items] = macroRW

  val dummy = Items(List(
    Item("20f0394f-2d21-4600-98bc-663ace3600bb","OSB בלוק","wix:image://v1/b1ae3f_f78b5db902124c45876ee712722bb79b~mv2.jpg/file.jpg#originWidth=450&originHeight=450",
      List("9X9", "14X14", "19X19", "14X9", "9X14", "14X21", "21X14", "29X19", "19X29")),
    Item("24dfe63d-4ad6-43c1-bb13-0c3145e09303","עץ בלוק","wix:image://v1/b1ae3f_e8c66ab5eedc4284a059a328b9a67ff4~mv2.jpg/file.jpg#originWidth=450&originHeight=450",
      List("19X19", "9X9", "14X9", "9X14", "14X14", "14X21", "21X14", "19X29", "29X19")),
    Item("73e464fb-0bd3-4890-8f8e-daa75f3cf7e0","תמונה על גבי זכוכית","wix:image://v1/b1ae3f_b0ac180933ca445180a4711c537e53b7~mv2.jpg/file.jpg#originWidth=450&originHeight=450",
      List("20X20", "30X30", "20X30", "30X20", "30X40", "40X30", "40X60", "60X40", "50X70", "70X50")),
    Item("ef4dd83d-5f73-48ac-b2d9-6f6ba8cedd86","תמונת אלומיניום","wix:image://v1/b1ae3f_be696c3bb0fc4e28b89520106986114b~mv2.jpg/file.jpg#originWidth=450&originHeight=450",
      List("20X20", "20X30", "30X30", "30X40", "40X30", "40X60", "60X40", "50X70", "70X50", "60X80", "80X60")),
    Item("c8879697-c9ce-4bda-a791-b7dbca3a1d33","תמונת פרספקס","wix:image://v1/b1ae3f_954e17f4620d40f2ad9047d049751a26~mv2.jpg/file.jpg#originWidth=450&originHeight=450",
      List("20X20", "30X30", "30X20", "20X30", "30X40", "40X30", "50X70", "70X50", "60X40", "40X60")),
    Item("82a18480-a47a-483e-8069-4cb720031ffb","תמונת קנבס","wix:image://v1/b1ae3f_075eac31eddd461da7856aac70e90dbd~mv2.jpg/file.jpg#originWidth=450&originHeight=450",
      List("20X20", "30X30", "20X30", "30X20", "30X40", "40X30", "40X60", "60X40", "50X70", "70X50", "60X80", "80X60", "70X100", "100X70", "100X100", "80X120", "120X80"))))
}

