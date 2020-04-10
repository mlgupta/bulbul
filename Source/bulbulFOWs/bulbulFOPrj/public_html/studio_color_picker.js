var NAMED_COLOR_RGB = new Array(
'#000000','#000080','#00008B','#0000CD','#0000FF','#006400','#008000','#008080','#008B8B','#00BFFF',
'#00CED1','#00FA9A','#00FF00','#00FF7F','#00FFFF','#00FFFF','#191970','#1E90FF','#20B2AA','#228B22',
'#2E8B57','#2F4F4F','#32CD32','#3CB371','#40E0D0','#4169E1','#4682B4','#483D8B','#48D1CC','#4B0082',
'#556B2F','#5F9EA0','#6495ED','#66CDAA','#696969','#6A5ACD','#6B8E23','#708090','#778899','#7B68EE',
'#7CFC00','#7FFF00','#7FFFD4','#800000','#800080','#808000','#808080','#8470FF','#87CEEB','#87CEFA',
'#8A2BE2','#8B0000','#8B008B','#8B4513','#8FBC8F','#90EE90','#9370D8','#9400D3','#98FB98','#9932CC',
'#9ACD32','#A0522D','#A52A2A','#A9A9A9','#ADD8E6','#ADFF2F','#AFEEEE','#B0C4DE','#B0E0E6','#B22222',
'#B8860B','#BA55D3','#BC8F8F','#BDB76B','#C0C0C0','#C71585','#CD5C5C','#CD853F','#D02090','#D19275',
'#D2691E','#D2B48C','#D3D3D3','#D87093','#D8BFD8','#DA70D6','#DAA520','#DC143C','#DCDCDC','#DDA0DD',
'#DEB887','#E0FFFF','#E6E6FA','#E9967A','#EE82EE','#EEE8AA','#F08080','#F0E68C','#F0F8FF','#F0FFF0',
'#F0FFFF','#F4A460','#F5DEB3','#F5F5DC','#F5F5F5','#F5FFFA','#F8F8FF','#FA8072','#FAEBD7','#FAF0E6',
'#FAFAD2','#FDF5E6','#FF0000','#FF00FF','#FF00FF','#FF1493','#FF4500','#FF6347','#FF69B4','#FF7F50',
'#FF8C00','#FFA07A','#FFA500','#FFB6C1','#FFC0CB','#FFD700','#FFDAB9','#FFDEAD','#FFE4B5','#FFE4C4',
'#FFE4E1','#FFEBCD','#FFEFD5','#FFF0F5','#FFF5EE','#FFF8DC','#FFFACD','#FFFAF0','#FFFAFA','#FFFF00',
'#FFFFE0','#FFFFF0','#FFFFFF');

var NAMED_COLOR_NAMES = new Array(
'Black','Navy','DarkBlue','MediumBlue','Blue','DarkGreen','Green','Teal','DarkCyan','DeepSkyBlue',
'DarkTurquoise','MediumSpringGreen','Lime','SpringGreen','Aqua','Aqua','MidnightBlue','DodgerBlue','LightSeaGreen','ForestGreen',
'SeaGreen','DarkSlateGray','LimeGreen','MediumSeaGreen','Turquoise','RoyalBlue','SteelBlue','DarkSlateBlue','MediumTurquoise','Indigo',
'DarkOliveGreen','CadetBlue','CornflowerBlue','MediumAquaMarine','DimGray','SlateBlue','OliveDrab','SlateGray','LightSlateGray','MediumSlateBlue',
'LawnGreen','Chartreuse','Aquamarine','Maroon','Purple','Olive','Gray','LightSlateBlue','SkyBlue','LightSkyBlue',
'BlueViolet','DarkRed','DarkMagenta','SaddleBrown','DarkSeaGreen','LightGreen','MediumPurple','DarkViolet','PaleGreen','DarkOrchid',
'YellowGreen','Sienna','Brown','DarkGray','LightBlue','GreenYellow','PaleTurquoise','LightSteelBlue','PowderBlue','FireBrick',
'DarkGoldenRod','MediumOrchid','RosyBrown','DarkKhaki','Silver','MediumVioletRed','IndianRed','Peru','VioletRed','Feldspar',
'Chocolate','Tan','LightGrey','PaleVioletRed','Thistle','Orchid','GoldenRod','Crimson','Gainsboro','Plum',
'BurlyWood','LightCyan','Lavender','DarkSalmon','Violet','PaleGoldenRod','LightCoral','Khaki','AliceBlue','HoneyDew',
'Azure','SandyBrown','Wheat','Beige','WhiteSmoke','MintCream','GhostWhite','Salmon','AntiqueWhite','Linen',
'LightGoldenRodYellow','OldLace','Red','Fuchsia','Fuchsia','DeepPink','OrangeRed','Tomato','HotPink','Coral',
'Darkorange','LightSalmon','Orange','LightPink','Pink','Gold','PeachPuff','NavajoWhite','Moccasin','Bisque',
'MistyRose','BlanchedAlmond','PapayaWhip','LavenderBlush','SeaShell','Cornsilk','LemonChiffon','FloralWhite','Snow','Yellow',
'LightYellow','Ivory','White');

  
  var BOX_WIDTH=20;
  var BOX_HEIGHT=20;
  
  var DEFAULT_COLOR='default';
  
  function ColorPicker(paletteId,width,height){
	this.paletteId=paletteId;
  this.selectedColor=DEFAULT_COLOR;
	this.previousColor=this.selectedColor;
	this.render=drawPicker;
  
  this.width=width;
  this.height=height;
  
  this.noOfColumns=parseInt((width-BOX_WIDTH)/BOX_WIDTH);
  
  this.pickerClassName='';
  this.paletteClassName='';

  this.show=showPalette;
  this.hide=hidePalette;
  this.click=clickPalette;
	this.onclick=null;	
  
  this.mouseoutdelay=1000;
  this.mouseoutTimer=null;
  
  this.mouseover=mouseover;
  this.mouseout=mouseout;
  this.select=selectPalette;
  
  this.onclick=null;
}

//To render the color palette
function drawPicker(){
  var pickerString='<table align="center" border="0" cellpadding="0" cellspacing="1" width="100%" >'
  pickerString+='  <tr>'
  pickerString+='    <td align="center" style="font-size:0px;">'
  pickerString+='      <div id="'+this.paletteId+'lyrColor" style="font-size:0px;width:51px;height:17px" class="bdrColor_CC6A00" onmouseout="return '+ this.paletteId +'.mouseout();" onmouseover="return '+ this.paletteId +'.mouseover();" onclick="return '+ this.paletteId +'.show();" ></div>'
  pickerString+='    </td>'
  pickerString+='    <td style="font-size:0px;" >'
  pickerString+='      <img src="images/clr_swatch.gif" alt="clrpkr" onmouseout="return '+ this.paletteId +'.mouseout();" onmouseover="return '+ this.paletteId +'.mouseover();" onclick="return '+ this.paletteId +'.show();">'
  pickerString+='    </td>'
  pickerString+='  </tr>'
  pickerString+='  <tr>'
  pickerString+='    <td colspan="2" style="font-size:0px;">'
  pickerString+='      <div id="'+this.paletteId+'lyrColorPalette" style="font-size:0px;" onmouseout="return '+ this.paletteId +'.mouseout();" onmouseover="return '+ this.paletteId +'.mouseover();"></div>'
  pickerString+='    </td>'
  pickerString+='  </tr>'
  pickerString+='</table>' 
  document.write(pickerString);
}

function clickPalette(objColorPalette){
  if(objColorPalette.defaultPalette!=0){
    this.select(DEFAULT_COLOR);  
  }else{
    this.select(objColorPalette.style.backgroundColor);
  }  
  this.hide();  
  if(this.onclick!=null){
    if(typeof this.onclick != "undefined"){
			eval(this.onclick);
		}
  }
}

function showPalette(){  
  var objParent=MM_findObj(this.paletteId+'lyrColorPalette');
  
  this.hide();
  
  var noOfColors=NAMED_COLOR_RGB.length;
  
  var objClrPickerLayer=document.createElement('DIV');
  
  objClrPickerLayer.style.width=(this.width+this.noOfColumns)+'px';
  objClrPickerLayer.style.height=this.height+'px';
  objClrPickerLayer.style.overflow='auto';
  objClrPickerLayer.style.position='absolute';
  objClrPickerLayer.style.backgroundColor='#ffffff';
  objClrPickerLayer.style.align='center';
  objClrPickerLayer.style.fontSize='0px';
  objClrPickerLayer.objThis=this;
  objClrPickerLayer.onmouseout=function(){
    this.objThis.mouseout();
  }
  
  objClrPickerLayer.onmouseover=function(){
    this.objThis.mouseover();
  }
  
  objClrPickerLayer.className=this.pickerClassName;
  
  var objClrPickerTable=document.createElement('TABLE');
  
  objClrPickerTable.border='0';
  objClrPickerTable.cellPadding='0';
  objClrPickerTable.cellSpacing='0';
  
  var objRow=null;
  var objCell=null;
  
  for(var colorIndex=0;colorIndex<noOfColors;colorIndex++){
    
    if((colorIndex%this.noOfColumns)==0){
      objRow=objClrPickerTable.insertRow(objClrPickerTable.rows.length);
    }
    
    objCell=objRow.insertCell(objRow.cells.length);
    var objColorPalette=document.createElement('DIV');
    objColorPalette.style.fontSize='0';
    objColorPalette.style.width=(BOX_WIDTH)+'px';
    objColorPalette.style.height=(BOX_HEIGHT)+'px';
    objColorPalette.style.cursor='hand';
    objColorPalette.style.cursor='pointer';
    objColorPalette.style.backgroundColor=NAMED_COLOR_RGB[colorIndex];
    objColorPalette.title=NAMED_COLOR_NAMES[colorIndex]; 
    objColorPalette.defaultPalette=0;
    objColorPalette.colorIndex=colorIndex;
    objColorPalette.colorLength=noOfColors;
    objColorPalette.style.border='1px solid '+objColorPalette.style.backgroundColor;
    objColorPalette.objThis=this;
    
    objColorPalette.onclick=function(){
      this.objThis.click(this);
    }
    
    objColorPalette.onmouseover=function(){
      if(this.colorIndex<=(parseInt(this.colorLength/2))){
        this.style.border='1px double #FFFFFF';
      }else{
        this.style.border='1px double #000000';
      }
      this.objThis.mouseover();
    };

    objColorPalette.onmouseout=function(){
      this.style.border='1px solid '+this.style.backgroundColor;
      this.objThis.mouseout();
    };
    
    objCell.appendChild(objColorPalette);
  }
  
  //Adding default Color Cell
  var lastRow=objClrPickerTable.rows[objClrPickerTable.rows.length-1];
  
  if(((lastRow.cells.length)%this.noOfColumns)==0){
    lastRow=objClrPickerTable.insertRow(objClrPickerTable.rows.length);
  }
  var lastCell=lastRow.insertCell(lastRow.cells.length);
  
  var objDefaultColorPalette=document.createElement('IMG');
  objDefaultColorPalette.src='images/default_color.gif';
  objDefaultColorPalette.style.fontSize='0';
  objDefaultColorPalette.style.width=(BOX_WIDTH)+'px';
  objDefaultColorPalette.style.height=(BOX_HEIGHT)+'px';
  objDefaultColorPalette.style.cursor='hand';
  objDefaultColorPalette.style.cursor='pointer';
  objDefaultColorPalette.style.border='1px solid #FFFFFF';
  objDefaultColorPalette.defaultPalette=1;
  objDefaultColorPalette.title='Default Color';
  objDefaultColorPalette.objThis=this;
  
  objDefaultColorPalette.onclick=function(){
    this.objThis.click(this);
  }
  
  objDefaultColorPalette.onmouseover=function(){
    this.style.border='1px double #000000';
    this.objThis.mouseover();
  };

  objDefaultColorPalette.onmouseout=function(){
    this.style.border='1px solid #FFFFFF';
    this.objThis.mouseout();
  };
  
  lastCell.appendChild(objDefaultColorPalette);
  
  objClrPickerLayer.appendChild(objClrPickerTable);
  objParent.appendChild(objClrPickerLayer);
}

function mouseout(){
  clearTimeout(this.mouseoutTimer);
  this.mouseoutTimer=setTimeout(this.paletteId+'.hide()',this.mouseoutdelay);	
}

function mouseover(){
  clearTimeout(this.mouseoutTimer);
}

function hidePalette(){
  var objParent=MM_findObj(this.paletteId+'lyrColorPalette');
  objParent.innerHTML='';
}  

function selectPalette(color){
  color=(trim(color).length==0)?DEFAULT_COLOR:color;
  this.selectedColor=color;
  var objLyrColor=MM_findObj(this.paletteId+'lyrColor');
  if(color==DEFAULT_COLOR){
    objLyrColor.style.backgroundColor='';
    objLyrColor.style.fontSize='9px';
    objLyrColor.innerHTML='<div>'+DEFAULT_COLOR+'</div>';
  }else{
    objLyrColor.style.fontSize='0px';
    objLyrColor.innerHTML='';
    objLyrColor.style.backgroundColor=color;
  }
}

