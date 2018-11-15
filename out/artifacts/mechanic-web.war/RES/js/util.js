/*网页转向*/
function $U(url){
	window.location.href=url;
}

/*取字符长度，一个中文字符为两个字节*/
function $Len(str){
	return (''+str).replace(/[^\x0000-\xFF00]/gi,'xx').length;
}

//用正则表达式将前后空格用空字符串替代。
function trim(strSrc)
{
	return strSrc.replace(/(^\s*)|(\s*$)/g, "");
}

//onkeypress时根据输入类型控制输入字符,"F":浮点型 "I":整型 "D":日期型
function filterKey(sType){
	var iKey = window.event.keyCode;
	
	if(sType == "F"){ //浮点型
	
		if(iKey != 45 && iKey != 46 && iKey != 13 && iKey != 11 && !(iKey>=48 && iKey<=57))
			window.event.keyCode = 0
		else{
			if(iKey == 46){
				var obj = window.event.srcElement;
				if(obj.value.indexOf(".")>=0)
					window.event.keyCode = 0;
			}
			if(iKey == 45){
				var obj = window.event.srcElement;
				if(obj.value.indexOf("-")>=0)
					window.event.keyCode = 0;
				else{
					window.event.keyCode = 0;
					obj.value = "-" + obj.value;
					if(obj.onchange != null){
						obj.onchange();
					}
				}
			}
		}
	} //end "F"
	else if(sType == "I"){ //整型
		if(iKey != 13 && iKey != 45 && iKey != 11 && !(iKey>=48 && iKey<=57))
			window.event.keyCode = 0;
		else if(iKey == 45){
			var obj = window.event.srcElement;
			if(obj.value.indexOf("-")>=0)
				window.event.keyCode = 0;
			else{
				window.event.keyCode = 0;
				obj.value = "-" + obj.value;
				if(obj.onchange != null){
					obj.onchange();
				}
			}
		}
	}	// end "I"
	else if(sType == "D"){ //日期型
		var obj = window.event.srcElement;
		var strDate = obj.value;
		
		if(strDate.length>=10){
			window.event.keyCode = 0;
			return;
		}
		else if(strDate.length<4){ //年
			if(iKey != 13 && iKey != 11 && !(iKey>=48 && iKey<=57))
				window.event.keyCode = 0;
		}
		else if(strDate.length == 4){ //分隔符
			if(iKey != 45 && iKey != 47)
				window.event.keyCode = 0;
		}
		else if(strDate.length>=5){
			if( strDate.indexOf("-") > 0 && strDate.indexOf("-") == strDate.lastIndexOf("-")){ //正输入月份
				if(strDate.length>=7 && iKey != 45){ //如果长度过长，则退出
					window.event.keyCode = 0;
					return;
				}
				
				if(iKey>=48 && iKey<=57){
					var iPos = strDate.indexOf("-");
					
					var iMonth = parseInt("" + strDate.substr(iPos+1,strDate.length-iPos-1) + (parseInt(iKey) - 48));
					
					if(strDate.length>=6 && (iMonth <1 || iMonth > 12)){
						window.event.keyCode = 0;
						return;
					}
				}
			} // end if("-")
			else if( strDate.indexOf("/") > 0 && strDate.indexOf("/") == strDate.lastIndexOf("/")){ //正输入月份
				if(strDate.length>=7 && iKey != 47){ //如果长度过长，则退出
					window.event.keyCode = 0;
					return;
				}
				
				if(iKey>=48 && iKey<=57){
					var iPos = strDate.indexOf("/");
					
					var iMonth = parseInt("" + strDate.substr(iPos+1,strDate.length-iPos-1) + (parseInt(iKey) - 48));
					
					if(strDate.length >= 6 && (iMonth <1 || iMonth > 12)){
						window.event.keyCode = 0;
						return;
					}
				}
			} // end if("/")
			else if( strDate.indexOf("-") > 0 && strDate.indexOf("-") != strDate.lastIndexOf("-")){ //正输入日期
				if(iKey>=48 && iKey<=57){
					var iPos = strDate.lastIndexOf("-");
					if(strDate.length - iPos > 2){
						window.event.keyCode = 0;
						return;
					}
					
					var iDay = parseInt("" + strDate.substr(iPos+1,strDate.length-iPos-1) + (parseInt(iKey) - 48));
					
					if(iDay > 31){
						window.event.keyCode = 0;
						return;
					}
				}
			}
			else if( strDate.indexOf("/") > 0 && strDate.indexOf("/") != strDate.lastIndexOf("/")){ //正输入日期
				if(iKey>=48 && iKey<=57){
					var iPos = strDate.lastIndexOf("/");
					if(strDate.length - iPos > 2){
						window.event.keyCode = 0;
						return;
					}
					
					var iDay = parseInt("" + strDate.substr(iPos+1,strDate.length-iPos-1) + (parseInt(iKey) - 48));
					
					if(iDay > 31){
						window.event.keyCode = 0;
						return;
					}
				}
			}
			
		}
		
		if(strDate.charAt(strDate.length-1) == "-" || strDate.charAt(strDate.length-1) == "/"){
			if(iKey != 13 && iKey != 11 && !(iKey>=48 && iKey<=57))
				window.event.keyCode = 0;
		}
		
		if(iKey == 47 || iKey == 45){
			if(strDate.indexOf("-") > 0 && iKey == 47)
				window.event.keyCode = 0;
			if(strDate.indexOf("/") > 0 && iKey == 45)
				window.event.keyCode = 0;
		}
	} // end "D"
	
	
}

/*自适应大小*/ 
function DrawImage(ImgD,_width,_height){
	if(!_width) _width=120;
	if(!_height) _height=120;
	var flag=false;
	var image=new Image();
	image.src=ImgD.src;
	if(image.width>0&&image.height>0){
		flag=true;
		if(image.width/image.height>=_width/_height){//120/120
			if(image.width>_width){   
				ImgD.width=_width;
				ImgD.height=(image.height*_width)/image.width;
			}else{
				ImgD.width=image.width;   
				ImgD.height=image.height;
			}
			ImgD.alt=image.width+"X"+image.height;
		}
		else{
			if(image.height>_height){   
				ImgD.height=_height;
				ImgD.width=(image.width*_height)/image.height;   
			}else{
				ImgD.width=image.width;   
				ImgD.height=image.height;
			}
			ImgD.alt=image.width+"X"+image.height;
		}
	}
}  

/*Cookie*/
function setCookie(name,value)
{
    var Days = 365;
    var exp  = new Date();    //new Date("December 31, 9998");
        exp.setTime(exp.getTime() + Days*24*60*60);
        document.cookie = name + "="+ escape (value) + ";expires=" + exp.toGMTString();
}
function getCookie(name)
{
    var arr,reg=new RegExp("(^| )"+name+"=([^;]*)(;|$)");
	if(arr=document.cookie.match(reg)) 
		return unescape(arr[2]);
	else 
		return null;
}
function delCookie(name)
{
    var exp = new Date();
        exp.setTime(exp.getTime() - 1);
    var cval=getCookie(name);
        if(cval!=null) 
        	document.cookie= name + "="+cval+";expires="+exp.toGMTString();
}

/*验证数字*/
function isNumber(e){
	var number = "1234567890";
	for(var i=0; i<e.length; i++){
		if (number.indexOf(e.charAt(i))<0) {
			return false;
		}
	}
	return true;
}

/*验证数字*/
function isAllDigits(argvalue) {
    argvalue = argvalue.toString();
    var validChars = "0123456789";
    var startFrom = 0;
    if (argvalue.substring(0, 2) == "0x") {
       validChars = "0123456789abcdefABCDEF";
       startFrom = 2;
    } else if (argvalue.charAt(0) == "0") {
       validChars = "01234567";
       startFrom = 1;
    } else if (argvalue.charAt(0) == "-") {
        startFrom = 1;
    }
    
    for (var n = startFrom; n < argvalue.length; n++) {
        if (validChars.indexOf(argvalue.substring(n, n+1)) == -1) return false;
    }
    return true;
}

/*检查固定电话号码*/
function isTellPhone(s){
    if (s.length<11||s.length > 20){
        return false;
    }
	var tellRex = /^0\d{2,3}-\d{7,8}(-\d{1,6})?$/;
	if(tellRex.test(s))
		return true;
	else
		return false;
} 

/*检查手机号码*/
function isMobile(s){
    if (s.length<11||s.length > 11){
        return false;
    }
	var mobileRex = "^(0|86|17951)?(13[0-9]|15[012356789]|17[678]|18[0-9]|14[57])[0-9]{8}$";
    var re = new RegExp(mobileRex);
    if (s.search(re) != -1) {
          return true;
    } else {
          return false;
    }
} 

/*检查Email是否合法*/
function isEmail(s){
    if (s.length<7||s.length > 50){
            return false;
    }
     var regu = "^(([0-9a-zA-Z]+)|([0-9a-zA-Z]+[_.0-9a-zA-Z-]*[0-9a-zA-Z]+))@([a-zA-Z0-9-]+[.])+([a-zA-Z]{2}|net|NET|com|COM|gov|GOV|mil|MIL|org|ORG|edu|EDU|int|INT)$"
     var re = new RegExp(regu);
     if (s.search(re) != -1) {
           return true;
     } else {
           return false;
     }
}

/*检查字符串是否为Null*/
function isNull(s){
    if (s == null || s.length <= 0 || s.trim() == ""){
            return true;
    }
    return false;
}

/*检查字符串是否为空*/
function isEmpty(s){
    if (s == null || s.length <= 0 || s.trim() == ""){
            return true;
    }
    return false;
}

/*检查日期是否合法*/
function isValidDate(day, month, year) {
    if (month < 1 || month > 12) {
            return false;
        }
        if (day < 1 || day > 31) {
            return false;
        }
        if ((month == 4 || month == 6 || month == 9 || month == 11) &&
            (day == 31)) {
            return false;
        }
        if (month == 2) {
            var leap = (year % 4 == 0 &&
                       (year % 100 != 0 || year % 400 == 0));
            if (day>29 || (day == 29 && !leap)) {
                return false;
            }
        }
        return true;
    }

/*获得Radio的值*/
function getRadioValue(name){	
	var radios = document.getElementsByName(name);
	var i;   
	if (null == radios.length){
	  	if(radios.checked) {
	  		return radios.value;
	  	}
	}
    for(i = 0; i < radios.length; i++){
       if(radios[i].checked){
     		return radios[i].value;
       }
    }
    return 0;
}

/*设置Radio的值*/
function setRadioValue(name,value){	
	var radios = document.getElementsByName(name);
	var i;
	if (null == radios.length){
	  	if(radios.checked) {
	  		radios.checked = "checked";
	  	}
	}
    for(i=0;i<radios.length;i++){
       if(value == radios[i].value){
     		radios[i].checked = "checked";
       }
    }
    return 0;
}

/*获得CheckBox的值,多个为数组*/
function getCheckBoxValues(name){	
	var values = new Array();
	var cbs = document.getElementsByName(name);
	var i;   
	if (null == cbs) return values;	  
	if (null == cbs.length){
	  	if(cbs.checked) {
	  		values[values.length] = cbs.value;
	  	}
	  	return values;
	}	    
	var count = 0 ;  	
	for(i = 0; i<cbs.length; i++){
		if(cbs[i].checked){
			values[values.length] = cbs[i].value;
		}
	}
	return values;
}

/*设置CheckBox的值*/
function setCheckBoxValue(name,value){
	var cbs = document.getElementsByName(name);
	var i;
    if (null == cbs) return 0 ;
  	if (null == cbs.length){
  		cbs.checked = value;
  		return 0;
  	}
	for(i=0;i<cbs.length;i++){
  		cbs[i].checked = value;
  	}
  	return 0;
}

/*设置CheckBox选中状态*/
function setCheckBoxs(name,value){
	var cbs = document.getElementsByName(name);
	var i;
    if (null == cbs) return 0 ;
  	if (null == cbs.length){
  		cbs.checked = true;
  		return 0;
  	}
	for(i=0;i<cbs.length;i++){
		if(cbs[i].value == value){
			cbs[i].checked = true;
		}
  	}
  	return 0;
}

function htmlEncode(text) {
	return text.replace(/&/g, '&amp;').replace(/"/g, '&quot;').replace(/</g, '&lt;').replace(/>/g, '&gt;');
}

/**
 * 结束时间比较开始时间，开始时间早于结束时间返回true，否则返回false
 * @param stime
 * @param etime
 * @returns {Boolean}
 */
function checkEndTime(stime, etime){
	var start=new Date(stime.replace("-", "/").replace("-", "/"));
	var end=new Date(etime.replace("-", "/").replace("-", "/"));
	if(end<start){
	 	return false;
	}
	return true;
}