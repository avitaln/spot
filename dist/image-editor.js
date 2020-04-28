let CANVAS_MAX_WIDTH = 980
let CANVAS_MAX_HEIGHT = 500

const WOOD = 10
const IWOOD = 20
const GLASS = 30
const PERSPEX = 40
const CANVAS = 50
const OSB = 60
const ALL_MATERIALS = [10,20,30,40,50,60]
const materialImages = [
  '59709c_a93269622e5c456eb0b73642ed6520a5~mv2.jpg',
  '59709c_8545da8ff44142ae8b8a29a8c449f84a~mv2.jpg',
  '59709c_aaedfd441c7c46628fee49bdccea26b2~mv2.jpg',
  '59709c_43206a2c3c8344c0b5dee078e10a3d8f~mv2.jpg',
  '59709c_5026a0ba6b2146aaa1e219be1b5f8ea3~mv2.jpg',
  '59709c_3ad21e0ead294fc6a24431e06065d54a~mv2.jpg'
]
const materials = { wood: WOOD, iwood: IWOOD, glass: GLASS, perspex: PERSPEX, canvas: CANVAS, osb: OSB }

const getImageForMaterial = (materialNum) => {
  return materialImages[materialNum/10-1]
}

/////////////////////////////

const SQUARE = 1
const HRECT = 2
const VRECT = 3
const CIRCLE = 4
const VELLIPSE = 5
const HELLIPSE = 6
const HAMSA = 7
const HEART = 8
const HEX = 9
const ALL_SHAPES = [1,2,3,4,5,6,7,8,9]

const shapes = { square: SQUARE, hrect: HRECT, vrect: VRECT, hamsa: HAMSA, heart: HEART, hex: HEX, 
                circle: CIRCLE, vellipse: VELLIPSE, hellipse: HELLIPSE }

/////////////////////////////

const shapeToHtml = []
const materialIdPrefix = "imge-material-"
const shapeIdPrefix = "imge-shape-"
const productIdPrefix = "imge-product-"

/////////////////////////////


const createStyle = () => {
  return `<style>
    .material-default, .shape-default, .size-default, .design-default { display: none }
    .show-material .material-default, .show-shape .shape-default, .show-size .size-default, .show-design .design-default { display: block }

    .material-status-default, 
    .shape-status-default, 
    .size-status-default, 
    .design-status-default { 
      border-radius: 50%;
      width: 28px;
      height: 28px;
      padding: 6px;
      border: 2px solid;
      color: #666;
      text-align: center;
      font: 24px Arial, sans-serif;
    }

    .material-status-set-current .material-status-default, 
    .shape-status-set-current .shape-status-default, 
    .size-status-set-current .size-status-default, 
    .design-status-set-current .design-status-default { 
      border: 4px solid;
      color: #FF0000;
    }

    .product-size-price {
      width: 160px;
      height: 160px;
      border: 2px solid #dddddd;
      background-color: #eeeeee;
      border-radius: 4px;
      font-family: almoni-dl-aaa-400;
      font-weight: 400;
      justify-content: center;
      text-align: center;
      direction: rtl;
    }

    .material-image {
      border-radius: 10px;
    }
    .material-image:hover {
      opacity: 0.8;
    }

    .svgcontainer {
      width: 200px;
      height: 200px;
      display: grid;
      grid-template-columns: 200px;
      grid-template-rows: 200px;
      justify-items: center;
      align-items: center;
      justify-content: center;
    }

    .svgcontainer:hover {
      border: 2px solid #dddddd;
      background-color: #eeeeee;
      border-radius: 4px;
    }

    .numbercircle {
      border-radius: 50%;
      width: 28px;
      height: 28px;
      padding: 6px;
      border: 2px solid;
      color: #666;
      text-align: center;
      font: 24px Arial, sans-serif;
    }

    #imge-container-1 {
        width: 100%;
        height: 100vh;
    }
    #imge-text-1 {
        font-family: almoni-dl-aaa-400;
        font-size: 20px;
        font-weight: 400;
        color: #999997
    }

    #imge-status {
      width: 980px;
      height: 100px;
      display: grid;
      grid-template-columns: auto 44px auto 44px auto 44px auto 44px auto auto auto;
      grid-template-rows: 50px 60px;
      justify-items: center;
      align-items: center;
      justify-content: center;
      direction: rtl;
    }

    #imge-material {
      padding-top: 20px;
      width: 980px;
      display: grid;
      grid-template-columns: auto auto auto;
      grid-column-gap: 20px;
      grid-row-gap: 20px;
      justify-items: center;
      align-items: center;
      justify-content: center;
      direction: rtl;
    }

    #imge-shape {
      padding-top: 20px;
      width: 980px;
      height: 640px;
      display: grid;
      grid-template-columns: 200px 200px 200px;
      grid-template-rows: 200px 200px 200px;
      grid-column-gap: 20px;
      grid-row-gap: 20px;
      justify-items: center;
      align-items: center;
      justify-content: center;
      direction: rtl;
    }
    
    #imge-size {
      width: 980px;
      height: 500px;
      display: grid;
      grid-template-columns: 200px 200px 200px;
      grid-template-rows: 200px 200px 200px;
      grid-column-gap: 20px;
      grid-row-gap: 20px;
      justify-items: center;
      align-items: center;
      justify-content: center;
      direction: rtl;
    }

    #imge-design {
      width: 980px;
      height: 600px;
    }

    .custom-file-upload {
      border-radius: 36px;
      background-color: royalblue;
      width: 240px;
      height: 48px;
      text-align: center;
      vertical-align: middle;
      color: aliceblue;
      display: inline-block;
      padding-top: 22px;
      cursor: pointer;
      font-family: sans-serif;
      font-size: 22px;
    }

    #imge-upload-button-container {
      display: flex;
      align-items: center;
      justify-content: center;
      height: 80px;
      background-color: #f5e5ce;
    }

    #imge-canvas-container {
      width: 980px;
      height: 500px;
      background-color: #ebd8e6;
      display: flex;
      align-items: top;
      justify-content: center;
    }
}

  </style>`
};



const createStatusHtml = () => {
  const line = '<hr style="height:2px;width:120px;border-width:0;background-color:#666"/>'
  return `
  <div id="imge-status">
  <div></div>
  <div>בחירת חומר</div>
  <div></div>
  <div>בחירת צורה</div>
  <div></div>
  <div>בחירת מידה</div>
  <div></div>
  <div>עיצוב תמונה</div>
  <div></div>
  <div></div>
  <div></div>

  <div></div>
  <div id="imge-circle-material" class="material-status-default">1</div>
  ${line}
  <div id="imge-circle-shape" class="shape-status-default">2</div>
  ${line}
  <div id="imge-circle-size" class="size-status-default">3</div>
  ${line}
  <div id="imge-circle-design" class="design-status-default">4</div>
  <div></div>
  <div></div>
  <div></div>

  </div>
  `
}
//const createStatusHtml = () => {
//  const line = '<hr style="height:2px;width:120px;border-width:0;background-color:#666"/>'
//  return `
//  <div id="imge-status">
//
//  <div></div>
//  <img src="https://static.wixstatic.com/media/59709c_c5880752e8fa41be9f697a0b4b378b16~mv2.png/v1/fill/w_51,h_44,al_c,lg_1,q_85,usm_0.66_1.00_0.01/01-icon.webp"></img>
//  <div></div>
//  <img src="https://static.wixstatic.com/media/59709c_855e7ef3e9e54e7ba095b4e412cf9a2e~mv2.png/v1/fill/w_44,h_44,al_c,lg_1,q_85,usm_0.66_1.00_0.01/02-icon.webp"></img>
//  <div></div>
//  <img src="https://static.wixstatic.com/media/59709c_14d1b0e6c48142acabd0076352a7c021~mv2.png/v1/fill/w_44,h_44,al_c,lg_1,q_85,usm_0.66_1.00_0.01/03-icon.webp"></img>
//  <div></div>
//  <img src="https://static.wixstatic.com/media/59709c_569d83be2ef84bcd8f1dad9e69359a5c~mv2.png/v1/fill/w_44,h_44,al_c,lg_1,q_85,usm_0.66_1.00_0.01/04-icon.webp"></img>
//  <div></div>
//  <div></div>
//  <div></div>
//
//  <div></div>
//  <div id="imge-circle-material" class="numbercircle">1</div>
//  ${line}
//  <div id="imge-circle-shape" class="numbercircle">2</div>
//  ${line}
//  <div id="imge-circle-size" class="numbercircle">3</div>
//  ${line}
//  <div id="imge-circle-design" class="numbercircle">4</div>
//  <div></div>
//  <div></div>
//  <div></div>
//
//  </div>
//  `
//}

const createMaterialsHtml = () => {
  return `
    <div class="material-default">
      <div id="imge-material">
        ${ALL_MATERIALS.map(num => createMaterialImageHtml(num,getImageForMaterial(num))).join("")}
      </div>
    </div>
  `
}

const transformProducts = (productsArray) => {
  let temp = []
  for (let i = 0; i < productsArray.length; i = i + 4) {
    let parts = productsArray[i].split("-");
    const magicIndex = materials[parts[0]] + shapes[parts[1]]
    const o = {
      productId: productsArray[i+1],
      width: parseInt(parts[2]),
      height: parseInt(parts[3]),
      sellPrice: productsArray[i+2],
      originalPrice: productsArray[i+3],
    }
    if (temp[magicIndex] === undefined)
      temp[magicIndex] = [o]
    else  
      temp[magicIndex].push(o)
  }
  return temp
}

const getProductHtml = (p) => {
  return `<div class="product-size-price" id="${productIdPrefix}${p.productId}" print-width=${p.width} print-height=${p.height} product-id=${p.productId}">
    <h3>${p.width}x${p.height} ס״מ</h3>
    <hr style="height:2px;width:100px;border-width:0;background-color:#dddddd">
    <h4>${p.sellPrice} ₪</h4>
    <h4 style="color:gray;"><del>${p.originalPrice} ₪</del></h4>
  </div>`
}

const createH1 = (id, text) => {
  const h2Element = document.createElement('h1');
  h2Element.textContent = text;
  h2Element.id = id;
  return h2Element;
};

const createImageContainer = () => {
  const imageContainer = document.createElement('div')
  imageContainer.id = 'imge-container-1'
  const canvas = document.createElement('canvas')
  canvas.id = 'canvas'
  canvas.width = 300
  canvas.height = 379
  canvas.style = 'border: 1px solid black'
  //canvas.style.transform = "scale(0.5,0.5)";
  imageContainer.appendChild(canvas)

  return imageContainer;
};

const createMaterialImageHtml = (num, src) => {
  return `<img class="material-image" id="${materialIdPrefix}${num}" src="https://static.wixstatic.com/media/${src}"></img>`
}

const createRectSvg = (num, w, h) => {
  const stroke = 4
  return `
    <svg id="${shapeIdPrefix}${num}" width="${w}" height="${h}">
    <rect x=${stroke/2} y=${stroke/2} width="${w - stroke}" height="${h - stroke}"
    style="fill:none;stroke-width:${stroke};stroke:rgb(61, 155, 233)" />
    </svg>`
}

const createEllipseSvg = (num, w, h) => {
  const stroke = 4
  return `
    <svg id="${shapeIdPrefix}${num}" width="${w}" height="${h}">
    <ellipse cx=${w/2} cy=${h/2} rx="${(w - stroke) / 2}" ry="${(h - stroke) / 2}"
    style="fill:none;stroke-width:${stroke};stroke:rgb(61, 155, 233)" />
    </svg>`
}

const createHeartSvg = (num, w, h) => {
  const realW = 337.772
  const realH = 303.508
  const ratio = realW / w
  const h1 = realH / ratio
  const stroke = 4 * ratio
  const vb = `-6 -6 350 316`
  return `
    <svg id="${shapeIdPrefix}${num}" preserveAspectRatio="none" width="${w}" height="${h}" data-bbox="0 0 ${realW} ${realH}"  viewBox="${vb}" style="stroke-width: ${stroke}; fill-opacity: 0; stroke: rgb(61, 155, 233); stroke-opacity: 1; fill: rgb(61, 155, 233);">
        <g>
            <path d="M310.485 21.159C290.731 4.583 271.484-.029 245.926 0c-27.13 2.348-50.615 15.589-77.041 49.05C142.46 15.589 118.975 2.348 91.845 0 66.287-.029 47.038 4.583 27.284 21.159 14.511 31.878 0 60.341 0 87.465c.339 54.573 38.391 136.609 163.485 212.905l5.165 3.138.236-.141.236.141 5.165-3.138c125.094-76.296 163.146-158.332 163.485-212.905-.001-27.124-14.514-55.587-27.287-66.306z"></path>
        </g>
    </svg>
  `
}

const createHamsaSvg = (num, w, h) => {
  const realW = 300
  const realH = 379
  const ratio = realW / w
  const stroke = 3 * ratio
  const vb = `-4 -4  310 387`
  return `
    <svg id="${shapeIdPrefix}${num}" preserveAspectRatio="none" data-bbox="0 0 300 379" width="${w}" height="${h}" viewBox="${vb}" data-type="shape" style="stroke-width: ${stroke}; fill-opacity: 0; stroke: rgb(61, 155, 233); stroke-opacity: 1; fill: rgb(61, 155, 233);">
    <g>
        <path d="M135.9 1.3c-9.3 3.5-18.2 12.2-22.1 21.5-1.7 4-2.6 5.1-3.7 4.6-7.8-3.4-13-4.6-18.9-4.1-11.9.9-21.6 7.9-27.1 19.6l-2.6 5.6.1 54.7.1 54.8-3.1-2.1c-1.7-1.1-6-3.5-9.6-5.2-6-2.9-7.3-3.1-16.5-3.1-9 0-10.6.3-15.8 2.9-3.3 1.5-8.3 5-11.3 7.8-5 4.5-5.4 5.2-5.4 9.3s.4 4.9 4.7 8.7c12 10.8 14.9 14.8 20.2 28.5C31.1 220.7 33.3 234 35 266c1.2 23.3 3.6 33.4 12.1 50.2 9.9 19.7 28.6 38.4 49.7 49.6 9.9 5.3 26.3 10.7 37.1 12.2 11.7 1.8 32.3.8 43.1-2 4.7-1.2 13.1-4.4 18.7-7 23.9-11.3 41.5-28.2 53.2-51 9-17.6 12.4-31.5 13.6-55.5 1.3-28 3.6-41.4 9.6-55.6 4.5-10.6 9.6-17.2 19.1-24.8 8.3-6.6 8.3-6.7 8.6-11.7s.2-5.1-5.7-10.7c-3.6-3.4-8.8-7.1-12.8-9-6.2-2.9-7.6-3.2-16.3-3.1-10.3 0-15.4 1.5-24.7 7.3l-4.8 3-.5-55.2c-.5-58.6-.5-57.6-5.5-65.7-3-4.8-9.8-10.8-14.9-13.1-2.9-1.3-6.4-1.9-12.3-1.9-7.5 0-11.6 1.1-18.4 4.7-.3.2-1.4-1.7-2.4-4.2-2.2-6-11.5-16-17.9-19.3-4.2-2.1-6.7-2.6-14.6-2.9-6.3-.2-10.7.1-13.1 1zm25.8 3.3c7.4 3.6 13.9 10.2 17.5 18.2 1.7 3.7 3.2 6.8 3.3 6.9.1.2 2.6-1 5.6-2.7 7.4-4 14.9-4.8 22.5-2.4 8 2.4 17.1 11.3 20.2 19.7 2.2 5.7 2.2 6.8 2.2 61.2 0 30.5.3 55.5.7 55.5.3 0 2.7-1.3 5.2-2.9 10.7-6.9 15.9-8.6 26.1-8.6 8.7 0 10.1.3 16 3.2 3.6 1.8 8.8 5.5 11.8 8.4 4.4 4.4 5.2 5.8 5.2 8.9 0 4-1.6 5.9-10.6 12.3-5.7 4.1-8.6 7.5-13.3 15.8-7.7 13.8-11.7 31.6-13.1 59.5-1.5 28.6-3.9 39.4-13 57.9-13.9 28.1-38.6 48.8-69.5 58.2-8.5 2.6-10.1 2.7-28.5 2.7s-20-.1-28.5-2.7c-32.7-10-59.7-32.2-73.8-60.8-7.9-16-9.6-23.9-11.3-51.3-.8-13.1-1.9-26.9-2.4-30.5-2.8-17.6-9.8-36.6-17-45.6-1.7-2.2-5.5-5.9-8.3-8.2C3 172.5.6 168.8 1.5 166c.9-2.7 9.2-10 15.6-13.5 5-2.8 6.2-3 15.4-3 9.4 0 10.4.2 16.4 3.4 3.6 1.8 8.2 4.5 10.3 5.9 2.1 1.3 4 2.3 4.3 2.1.2-.3-.1-22.7-.6-49.9-.6-27.2-.7-52-.4-55 1.5-12.4 8.4-23 18.1-28 4-2.1 6.3-2.5 12.9-2.5 6.9 0 8.9.4 13.4 2.7 2.9 1.6 5.4 2.8 5.6 2.8s.9-1.7 1.5-3.8c3.9-11.9 15.6-22.9 27-25.3 5.7-1.1 15.1.1 20.7 2.7z"></path>
    </g>
    </svg>
  `
}

const createHexSvg = (num, w, h) => {
  const realW = 311.994 - 42.971
  const realH = 264.234 - 47.695
  const ratio = realW / w
  const stroke = 4 * ratio
  const vb = `38.24381818181816 43.09962608695651 321.4483636363637 273.42474782608696`
  return `
    <svg id="${shapeIdPrefix}${num}" preserveAspectRatio="none" data-bbox="42.971 47.695 311.994 264.234" width="${w}" height="${h}" viewBox="${vb}" style="stroke-width: ${stroke}; fill-opacity: 0; stroke: rgb(61, 155, 233); stroke-opacity: 1; fill: rgb(61, 155, 233);">
    <g>
    <path d="M120.97 311.929L42.971 179.812 120.97 47.695h155.996l77.999 132.117-77.999 132.117H120.97z"></path>
    </g>
    </svg>
  `
}

const initShapesHtml = () => {
  if (shapeToHtml.length == 0) {
    shapeToHtml[SQUARE] = `<div class="svgcontainer">${createRectSvg(SQUARE, 120, 120)}</div>`;
    shapeToHtml[HRECT] = `<div class="svgcontainer">${createRectSvg(HRECT, 160, 100)}</div>`;
    shapeToHtml[VRECT] = `<div class="svgcontainer">${createRectSvg(VRECT, 100, 160)}</div>`;
    shapeToHtml[CIRCLE] = `<div class="svgcontainer">${createEllipseSvg(CIRCLE, 120, 120)}</div>`;
    shapeToHtml[HELLIPSE] = `<div class="svgcontainer">${createEllipseSvg(HELLIPSE, 100, 160)}</div>`;
    shapeToHtml[VELLIPSE] = `<div class="svgcontainer">${createEllipseSvg(VELLIPSE, 160, 100)}</div>`;
    shapeToHtml[HEART] = `<div class="svgcontainer">${createHeartSvg(HEART, 25*5, 23*5)}</div>`;
    shapeToHtml[HAMSA] = `<div class="svgcontainer">${createHamsaSvg(HAMSA, 23.6*5, 29.8*5)}</div>`;
    shapeToHtml[HEX] = `<div class="svgcontainer">${createHexSvg(HEX, 28.5*5, 25*5)}</div>`;
  }
}

class ImageEditor extends HTMLElement {
  constructor() {
    super();
  }

  loadFile(selectedFile) {
    const self = this
    var reader = new FileReader()
    reader.onload = function(event) {
      let imgObj = new Image()
      imgObj.onload = function() {
        console.log(`Loaded image size = ${imgObj.width}x${imgObj.height}`)
        self.zoom = 1
        self.applyImage(imgObj)
        self.scaleImage(true)
        self.canvas.renderAll();
      }
      imgObj.src = event.target.result;
    }
    reader.readAsDataURL(selectedFile)
  }
  
  applyImage(imgObj) {
    this.originalHeight = imgObj.height
    this.originalWidth = imgObj.width
    const fabricImage = new fabric.Image(imgObj);
    fabricImage["hasControls"] = false
    fabricImage["hasBorders"] = false
    fabricImage["centeredScaling"] = true
    if (this.image) {
      this.canvas.remove(this.image)
    }
    this.image = fabricImage
    this.canvas.add(this.image);
  }
  
  initCanvas() {
    if (!this.canvas) {
      fabric.filterBackend = new fabric.Canvas2dFilterBackend()
      this.canvas = new fabric.Canvas("canvas", { backgroundColor : "green" })
      this.canvas.enableGLFiltering = true
      
      const onMove = function(e) {
        var obj = e.target;
        const rect = obj.getBoundingRect(false, true)
        
        if (rect.left > 0) {
          obj.set("left", 0)
        } else if (rect.left + rect.width < this.canvas.width) {
          obj.set("left",this.canvas.width - rect.width)
        }
        
        if (rect.top > 0) {
          obj.set("top", 0)
        } else if (rect.top + rect.height < this.canvas.height) {
          obj.set("top",this.canvas.height - rect.height)
        }

      }.bind(this)
      
      this.canvas.on("object:moved", onMove);
      this.canvas.on("object:scaled", onMove);
    }
  }

  scaleImage(resetToCenter) {
    if (this.image && this.canvas) {
      if (this.canvas.width/this.canvas.height > this.originalWidth/this.originalHeight) {
        console.log(`scaling to width from ${this.image.width} to ${this.canvas.width}`)
        this.image.scaleToWidth(this.canvas.width * this.zoom);
      } else {
        console.log(`scaling to height from ${this.image.height} to ${this.canvas.height}`)
        this.image.scaleToHeight(this.canvas.height * this.zoom);
      }
      if (resetToCenter) this.canvas.centerObject(this.image);
    }
  }

  reset() {
    this.setZoom(1)
  }
  
  setZoom(z) {
    this.zoom = z
    this.scaleImage()
    this.canvas.centerObject(image);
  }

  connectedCallback() {
  }
  
  createDom() {
    this.innerHTML = `
      ${createStyle()}
      ${createStatusHtml()}
      ${createMaterialsHtml()}
      <div class="shape-default">  
        <div id="imge-shape">
        </div>
      </div>
      <div class="size-default">  
        <div id="imge-size">
        </div>
      </div>
      <div class="design-default">
        <div id="imge-design" />
          <div id="imge-upload-button-container">
            <label class="custom-file-upload">לחץ להעלאת תמונה
              <input id="imge-upload-button" type="file" accept="image/png, image/jpeg" multiple style="display:none"/>
            </label>
          </div>
          <div id="imge-canvas-container">
            <div id="imge-canvas-scaled-container">
              <canvas id="canvas" style="border: none;"></canvas>
            </div>
          </div>
        </div>
      </div>
    `
//            <div id="imge-canvas-scaled-container" style="display:block; transform: scale(0.5,0.5); transform-origin: top">
    this.gotoMaterials()
    this.initListeners()
  }
  
  async initListeners() {
    document.getElementById("imge-status").onclick = (event) => {
      const element = this.findElementWithIdPrefix("imge-circle-", event.target)
      if (element) {
        switch (element.id) {
          case "imge-circle-material": this.gotoMaterials(); break;
          case "imge-circle-shape": this.gotoShapes(); break;
          case "imge-circle-size": this.gotoSizes(); break;
          case "imge-circle-design": this.gotoDesign(); break;
        }
      }
    }

    document.getElementById("imge-material").onclick = (event) => {
      const element = this.findElementWithIdPrefix(materialIdPrefix, event.target)
      if (element) {
        const numStr = element.id.substring(materialIdPrefix.length)    
        this.selectedMaterial = parseInt(numStr)
        this.gotoShapes()
      }
    }
    
    document.getElementById("imge-shape").onclick = (event) => {
      const element = this.findElementWithIdPrefix(shapeIdPrefix, event.target)
      if (element) {
        const numStr = element.id.substring(shapeIdPrefix.length)    
        this.selectedShape = parseInt(numStr)
        this.gotoSizes()
      }
    }

    document.getElementById("imge-size").onclick = (event) => {
      const element = this.findElementWithIdPrefix(productIdPrefix, event.target)
      if (element) {
        this.printWidth = parseInt(element.getAttribute("print-width"))
        this.printHeight = parseInt(element.getAttribute("print-height"))
        this.gotoDesign()
      }
    }
    
    document.getElementById("imge-upload-button").onchange = (event) => {
       this.loadFile(event.target.files[0])
    }
  }
  
  findElementWithIdPrefix(prefix, element) {
    if (element.id.startsWith(prefix))
      return element
    else {
      if (element.parentElement) {
        return this.findElementWithIdPrefix(prefix, element.parentElement)
      } else
        return undefined
    }
  }

  attributeChangedCallback(name, oldValue, newValue) {   
    if (name === 'products') {
      console.log ("attributeChangedCallback")
      this.products = transformProducts(JSON.parse(newValue))
      this.createDom()
    }
  }

  gotoMaterials() {
    this.classList = "show-material material-status-set-current"
  }

  gotoDesign() {
    this.initCanvas()
    this.setCanvasSize(this.calculateCanvasSize())
    this.scaleImage(true)
    this.classList = "show-design design-status-set-current"
  }
  
  setCanvasSize(newCanvasSize) {
    if (newCanvasSize.width != this.canvas.getWidth() || newCanvasSize.height != this.canvas.getHeight()) {
      this.canvas.setHeight(newCanvasSize.height)
      this.canvas.setWidth(newCanvasSize.width)
    }
  }

  calculateCanvasSize() {
    const ratio = this.printWidth/this.printHeight
    if (ratio > CANVAS_MAX_WIDTH/CANVAS_MAX_HEIGHT) {
      // print is wider than screen
      return {
        width: CANVAS_MAX_WIDTH,  
        height: CANVAS_MAX_WIDTH / ratio
      }
    } else {
      return {
        width: CANVAS_MAX_HEIGHT * ratio,  
        height: CANVAS_MAX_HEIGHT
      }
    }
  }
  
  gotoShapes() {
    this.classList = "show-shape shape-status-set-current"
    initShapesHtml()
    const validShapes = ALL_SHAPES.filter(i => this.products[this.selectedMaterial+i] != undefined)
    const html = validShapes.map(i => shapeToHtml[i]).join("")
    document.getElementById("imge-shape").innerHTML = html
  }
  
  gotoSizes() {
    this.classList = "show-size size-status-set-current"
    const arrayIndex = this.selectedShape + this.selectedMaterial
    const html = this.products[arrayIndex].map(p => getProductHtml(p)).join("")
    document.getElementById("imge-size").innerHTML = html
  }

  static get observedAttributes() {
    return ['products'];
  }

}

customElements.define('image-editor', ImageEditor);
