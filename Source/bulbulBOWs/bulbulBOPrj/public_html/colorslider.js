/* Conversion Variables and Methods */
var HexCharacters="0123456789ABCDEF";

function HexValue(decValue){
  return HexCharacters.charAt((decValue>>4)&0xf)+HexCharacters.charAt(decValue&0xf)
}
function DecValue(hexValue){
  return parseInt(hexValue.toUpperCase(),16)
}
function DecFixed(decValue){
  return Math.min(parseFloat(Math.abs(Math.floor(decValue))), 255)
}
function HexFixed(hexValue){
  return HexValue(Math.min(parseFloat(Math.abs(Math.floor(DecValue(hexValue)))), 255))
}



var H_TYPE='H'
var V_TYPE='V'
var DEFAULT_TYPE=V_TYPE;

function ColorSlider(id){
	this.id=id;
	this.type=DEFAULT_TYPE;
	this.factor=1;
	this.scrollType='scrollTop';
	this.color='#FFFFFF';
	
	// Display Color Id & Object 
	this.colorId=this.id+'lyrColor';
	this.colorObj=null;
	
	// Slider Id & Object 
	this.redId=this.id+'lyrRed';
	this.greenId=this.id+'lyrGreen';
	this.blueId=this.id+'lyrBlue';
	this.redObj=null;
	this.greenObj=null;
	this.blueObj=null;
	
	// Hex Value Object & Id
	this.redHexId=this.id+'lyrRedHex';
	this.greenHexId=this.id+'lyrBlueHex';
	this.blueHexId=this.id+'lyrGreenHex';
	this.redHexObj=null;
	this.greenHexObj=null;
	this.blueHexObj=null;
	
	// Decimal Value Object & Id
	this.redDecId=this.id+'lyrRedDec';
	this.greenDecId=this.id+'lyrBlueDec';
	this.blueDecId=this.id+'lyrGreenDec';
	this.redDecObj=null;
	this.greenDecObj=null;
	this.blueDecObj=null;

	//Hex Values of The RGB
	this.redHexValue='FF';
	this.greenHexValue='FF';
	this.blueHexValue='FF';
	
	//Scroll Methods
	this.redOnScroll=red_onscroll;
	this.greenOnScroll=green_onscroll;
	this.blueOnScroll=blue_onscroll;	
	
	//Initialise Methods
	this.init=init;
	
	//Style
	this.borderStyle;
	this.backGroundStyle;
	this.buttonStyle;
	
	//Class
	this.borderClass;
	this.backGroundClass;
	this.buttonClass;
	
	this.okOnClick=function(){};
	this.cancelOnClick=function(){};
	
	this.setHexColor=set_Hex_Color;
	this.setRGBColor=set_RGB_Color;
	
	this.getHexColor=get_Hex_Color;
	this.getRGBColor=get_RGB_Color;
	
	//ColorSlider Rendering Methods
	this.render=drawColorSlider;
	
	//Tooltips
	this.tooltipHexValue='HexaDecimal Color value';
	this.tooltipRGBValue='R,G,B Color value';
	
	//Labels
	this.redLabel='Red';
	this.greenLabel='Green';
	this.blueLabel='Blue';
	
	this.okLabel='Ok';
	this.cancelLabel='Cancel';
				
}

function init(){
	eval('this.redObj.'+this.scrollType+'='+DecValue(this.color.substring(1,3))*this.factor);// Don't Remove this line it will not work for IE
	eval('this.redObj.'+this.scrollType+'='+DecValue(this.color.substring(1,3))*this.factor);
	eval('this.greenObj.'+this.scrollType+'='+DecValue(this.color.substring(3,5))*this.factor);
	eval('this.blueObj.'+this.scrollType+'='+DecValue(this.color.substring(5,7))*this.factor);
	
	this.redOnScroll();
	this.blueOnScroll();						
	this.greenOnScroll();	
	
}

function drawColorSlider(){
	var strSkull='';
	var strHorSkull='';
	var strVerSkull='';
	var style4Border='border: 1px solid #808080;';
	var style4BackGround='background-color:#D7D7D7;';
	var style4Button='border: #669966 1px solid; font-family: Verdana, Arial, Helvetica, sans-serif;font-weight: normal; font-size: 11px; color: #333333;background-image: url(butt_tile.gif); background-repeat: repeat-x;';
	
	this.borderStyle=style4Border+this.borderStyle;
	this.backGroundStyle=style4BackGround+this.backGroundStyle;
	this.buttonStyle=style4Button+this.buttonStyle;
	
	if (typeof this.borderClass!='undefined'){
		this.borderStyle='';
	}
	if (typeof this.backGroundClass!='undefined'){
		this.backGroundStyle='';
	}
	if (typeof this.buttonClass!='undefined'){
		this.buttonStyle='';
	}
	//Generation of InnerHTML
	var strInnerHTML="";
	if(this.type==H_TYPE){
		for (var i=0;i<331;i++) {
		   strInnerHTML+='<span style="font-size:8px;visibility:hidden">W</span>';
		}
	}else{
		for (var i=0;i<520;i++) {
		   strInnerHTML+='<div style="font-size:8px;visibility:hidden">W</div>';
		}
	}
		
	//Skeleton Preperation String for Horizontal
	strHorSkull+='<table border="0" cellpadding="2" cellspacing="1" ><tr>';		
	strHorSkull+='<td height="40px" valign="bottom" align="right" style="font-weight:normal; font-size:11px; font-family:arial, verdana; color:#FF0000">'+ this.redLabel +'</td>';
	strHorSkull+='<td><div id="'+this.redId+'" style="width:100px;height:32px;overflow:auto;overflow-x:auto;overflow-y:hidden" onscroll="return eval(\''+this.id +'\').redOnScroll();">';
	strHorSkull+=strInnerHTML; // For Red Layer
	strHorSkull+='</div></td></tr><tr>';
	strHorSkull+='<td height="40px" valign="bottom" align="right" style="font-weight:normal; font-size:11px; font-family:arial, verdana; color:#009900">'+ this.greenLabel +'</td>';
	strHorSkull+='<td><div id="'+this.greenId+'" style="width:100px;height:32px;overflow:auto;overflow-x:auto;overflow-y:hidden" onscroll="return eval(\''+this.id +'\').greenOnScroll();">';
	strHorSkull+=strInnerHTML; // For Green Layer
	strHorSkull+='</div></td></tr><tr>';
	strHorSkull+='<td height="40px" valign="bottom" align="right" style="font-weight:normal; font-size:11px; font-family:arial, verdana; color:#0000FF">'+ this.blueLabel +'</td>';
	strHorSkull+='<td><div id="'+this.blueId+'" style="width:100px;height:32px;overflow:auto;overflow-x:auto;overflow-y:hidden" onscroll="return eval(\''+this.id +'\').blueOnScroll();">';
	strHorSkull+=strInnerHTML; // For Blue Layer
	strHorSkull+='</div></td></tr></table>';

	//Skeleton Preperation String for Vertical
	strVerSkull+='<table border="0" cellpadding="0" cellspacing="0" ><tr>';
	strVerSkull+='<td width="50px"  align="center" style="font-weight:normal; font-size:11px; font-family:arial, verdana; color:#FF0000" >'+ this.redLabel +'</td>';
	strVerSkull+='<td width="50px" align="center" style="font-weight:normal; font-size:11px; font-family:arial, verdana; color:#009900">'+ this.greenLabel +'</td>';
	strVerSkull+='<td width="50px" align="center" style="font-weight:normal; font-size:11px; font-family:arial, verdana; color:#0000FF">'+ this.blueLabel +'</td>';
	strVerSkull+='</tr><tr><td>';
	strVerSkull+='<div id="'+this.redId+'" style="width:32px;height:100px;overflow:auto;overflow-y:auto;overflow-x:hidden" onscroll="return eval(\''+this.id +'\').redOnScroll();">';
	strVerSkull+=strInnerHTML; // For Red Layer
	strVerSkull+='</div></td><td>';
	strVerSkull+='<div id="'+this.greenId+'" style="width:32px;height:100px;overflow:auto;overflow-y:auto;overflow-x:hidden" onscroll="return eval(\''+this.id +'\').greenOnScroll();">';
	strVerSkull+=strInnerHTML; // For Green Layer
	strVerSkull+='</div></td><td>';
	strVerSkull+='<div id="'+this.blueId+'" style="width:32px;height:100px;overflow:auto;overflow-y:auto;overflow-x:hidden" onscroll="return eval(\''+this.id +'\').blueOnScroll();" >';
	strVerSkull+=strInnerHTML; // For Blue Layer
	strVerSkull+='</div></td></tr></table>';

	//Skeleton Preperation String
	strSkull+='<table class="' + this.borderClass + ' ' + this.backGroundClass+'" border="0"  cellpadding="0" cellspacing="4" style="'+ this.borderStyle + ';'+ this.backGroundStyle+'">';
	strSkull+='<tr><td align="center"  height="125px" width="150px" valign="top">';
	strSkull+=((this.type==H_TYPE)?strHorSkull:strVerSkull); // Placement For strHorSkull or strVerSkull 
	strSkull+='</td></tr>';
	strSkull+='<tr><td  align="center" >';
	strSkull+='<div class="' + this.borderClass + '" id="'+this.colorId+'" style="font-size:12px;width:129px;height:35px;'+ this.borderStyle + '" ></div>';
	strSkull+='</td></tr>';
	strSkull+='<tr><td  align="center">';
	strSkull+='<table width="100%" border="0" cellpadding="0" cellspacing="1">';
	strSkull+='<tr><td align="right" width="41%">';
	strSkull+='<div  class="' + this.borderClass + '"  align="center" title="'+ this.tooltipHexValue +'" style="background-color: #ffffff;margin-top:2px; font-weight:normal; font-size:11px; font-family:arial, verdana; width:55px; height:18px; width:50px;'+ this.borderStyle + '" >';
	strSkull+='#<span id="'+this.redHexId+'"  ></span><span id="'+this.greenHexId+'" ></span><span id="'+this.blueHexId+'" ></span>';
	strSkull+='</div>';
	strSkull+='</td><td align="left" width="59%">';
	strSkull+='<div  class="' + this.borderClass + '" align="center" title="'+ this.tooltipRGBValue +'" style="background-color: #ffffff;  margin-top:2px; font-weight:normal; font-size:11px; font-family:arial, verdana; width:70px; height:18px; width:76px;'+ this.borderStyle + '" >';
	strSkull+='(<span id="'+this.redDecId+'"  ></span>,<span id="'+this.greenDecId+'" ></span>,<span id="'+this.blueDecId+'" ></span>)';
	strSkull+='</div>';
	strSkull+='</td></tr></table>';
	strSkull+='</td></tr><tr><td  align="center" height="30px">';
	strSkull+='<input class="' + this.buttonClass + '" type="button" value="'+ this.okLabel +'" id="btnOk" name="btnOk" onclick="return eval(\''+this.id +'\').okOnClick();" style="height:20px; width:62px;'+ this.buttonStyle + '">';
	strSkull+='&nbsp;<input class="' + this.buttonClass + '" type="button" value="'+ this.cancelLabel +'" id="btnCancel" name="btnCancel" onclick="return eval(\''+this.id +'\').cancelOnClick();" style="height:20px; width:62px;'+ this.buttonStyle + ' ">';
	strSkull+='</td></tr></table>';
	//alert(strSkull);
	document.write(strSkull);
	
	/*Finding RGB Slider Objects*/
	this.redObj=MM_findObj(this.redId);
	this.greenObj=MM_findObj(this.greenId);
	this.blueObj=MM_findObj(this.blueId);
	
	/*Finding Display Color Object*/
	this.colorObj=MM_findObj(this.colorId);
	
	/* Finding Hex Value Object */
	this.redHexObj=MM_findObj(this.redHexId);
	this.greenHexObj=MM_findObj(this.greenHexId);
	this.blueHexObj=MM_findObj(this.blueHexId);
	
	/* Finding Decimal Value Object */
	this.redDecObj=MM_findObj(this.redDecId);
	this.greenDecObj=MM_findObj(this.greenDecId);
	this.blueDecObj=MM_findObj(this.blueDecId);
	
	this.factor=(this.type==H_TYPE)?10:20;
	
	this.scrollType=(this.type==H_TYPE)?'scrollLeft':'scrollTop';	
}

function red_onscroll(){
	var scrollPoint=Math.ceil(eval('this.redObj.'+this.scrollType)/this.factor);
	scrollPoint=((scrollPoint<10)?"0":"").toString() + scrollPoint.toString(10);
	var hexScrollPoint=HexValue(scrollPoint);
	this.redDecObj.innerHTML=scrollPoint;
	this.redDecObj.style.color='#'+hexScrollPoint+'0000';
	this.redHexObj.innerHTML=hexScrollPoint;
	this.redHexObj.style.color='#'+hexScrollPoint+'0000';
	this.redHexValue=hexScrollPoint;
	this.color='#'+this.redHexValue+this.greenHexValue+this.blueHexValue;	
	this.colorObj.style.backgroundColor=this.color;	
}

function green_onscroll(){
	var scrollPoint=Math.ceil(eval('this.greenObj.'+this.scrollType)/this.factor);
	scrollPoint=((scrollPoint<10)?"0":"").toString() + scrollPoint.toString(10);
	var hexScrollPoint=HexValue(scrollPoint);
	this.greenDecObj.innerHTML=scrollPoint;
	this.greenDecObj.style.color='#00'+hexScrollPoint+'00';
	this.greenHexObj.innerHTML=hexScrollPoint;
	this.greenHexObj.style.color='#00'+hexScrollPoint+'00';
	this.greenHexValue=hexScrollPoint;
	this.color='#'+this.redHexValue+this.greenHexValue+this.blueHexValue;	
	this.colorObj.style.backgroundColor=this.color;
}
function blue_onscroll(){
	var scrollPoint=Math.ceil(eval('this.blueObj.'+this.scrollType)/this.factor);
	scrollPoint=((scrollPoint<10)?"0":"").toString() + scrollPoint.toString(10);	
	var hexScrollPoint=HexValue(scrollPoint);	
	this.blueDecObj.innerHTML=scrollPoint;
	this.blueDecObj.style.color='#0000'+hexScrollPoint;
	this.blueHexObj.innerHTML=hexScrollPoint;
	this.blueHexObj.style.color='#0000'+hexScrollPoint;
	this.blueHexValue=hexScrollPoint;
	this.color='#'+this.redHexValue+this.greenHexValue+this.blueHexValue;	
	this.colorObj.style.backgroundColor=this.color;
}

function set_Hex_Color(hexColor){
	if(typeof hexColor =='undefined') return false;
	if(HexCharacters.indexOf(hexColor.toUpperCase().substring(1,2))==-1) return false
	if(HexCharacters.indexOf(hexColor.toUpperCase().substring(2,3))==-1) return false
	if(HexCharacters.indexOf(hexColor.toUpperCase().substring(3,4))==-1) return false
	if(HexCharacters.indexOf(hexColor.toUpperCase().substring(4,5))==-1) return false
	if(HexCharacters.indexOf(hexColor.toUpperCase().substring(5,6))==-1) return false
	if(HexCharacters.indexOf(hexColor.toUpperCase().substring(6,7))==-1) return false
	if (hexColor.length!=7) return false
	if (hexColor.indexOf('#')!=0) return false
	this.color=hexColor;
}

function set_RGB_Color(redPart,greenPart,bluePart){
	if(typeof redPart =='undefined') return false;
	if(typeof greenPart =='undefined') return false;
	if(typeof bluePart =='undefined') return false;	
	this.color='#'+HexValue(redPart)+HexValue(greenPart)+HexValue(bluePart);
}

function get_Hex_Color(){
	return this.color;
}
function get_RGB_Color(){
	var hexColor=this.color;
	var redPart=DecValue(hexColor.toUpperCase().substring(1,3));
	var greenPart=DecValue(hexColor.toUpperCase().substring(3,5));
	var bluePart=DecValue(hexColor.toUpperCase().substring(5,7));
	redPart=((redPart<10)?"0":"").toString() + redPart.toString(10);
	greenPart=((greenPart<10)?"0":"").toString() + greenPart.toString(10);
	bluePart=((bluePart<10)?"0":"").toString() + bluePart.toString(10);	
	return '('+redPart +','+ greenPart +','+ bluePart+')';
}