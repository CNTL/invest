var tlsearchBox = {
	init : function(searchFields){
		var fields=[];var moreFields = [];
		if(searchFields && searchFields.fields && searchFields.fields.length>0){
			fields = searchFields.fields;
			var queryDIV = $("#divQueryCust");
			queryDIV.html("");
			var queryForm = document.createElement("form");
			queryForm.id = "queryForm";
			tlsearchBox.setElementAttrible(queryForm,"code","customQuery");
			var fieldsDiv = document.createElement("div");
			fieldsDiv.className = "querycontent";
			queryForm.appendChild(fieldsDiv);
			var ul = document.createElement("ul");
			ul.id="list_common";
			ul.className = "list_search ui-droppable ui-sortable";
			var li = document.createElement("li");
			li.className = "drop-class";
			ul.appendChild(li);
			for(var i=0;i<fields.length;i++){
				tlsearchBox.initSeachField(fields[i],ul);
			}
			fieldsDiv.appendChild(ul);
			if(searchFields && searchFields.moreFields && searchFields.moreFields.length>0){
				moreFields = searchFields.moreFields;
				var morlUL = document.createElement("ul");
				morlUL.id="list_more";
				morlUL.className = "list_search ui-droppable ui-sortable";
				var morLI = document.createElement("li");
				morLI.className = "drop-class";
				morlUL.appendChild(morLI);
				for(var i=0;i<moreFields.length;i++){
					tlsearchBox.initSeachField(moreFields[i],morlUL);
				}
				fieldsDiv.appendChild(morlUL);
			}
			
			queryDIV.append(queryForm);
		}
	},
	initSeachField : function(field,ul){
		var li = document.createElement("li");
		li.className = "drop-class";
		var span = document.createElement("span");
		span.className = "custform-span";
		span.id="SPAN_"+field.fieldcode;
		li.appendChild(span);
		var label = document.createElement("label");
		label.id="LABEL_"+field.fieldcode;
		if(field.ctrl=="CHECK"){
			if(field.datatype="YN"){
				label.className = "custform-label-checkbox checkbox";
				tlsearchBox.setElementAttrible(label,"for",field.fieldcode);
				span.appendChild(label);
				var ck = tlsearchBox.createInput(field,"checkbox","custform-checkbox");
				label.appendChild(ck);
				var label_text = document.createTextNode(field.name);
				label.appendChild(label_text); 
			}
		}else{
			label.className="custform-label";
			var label_text = document.createTextNode(field.name+"：");
			label.appendChild(label_text);
			span.appendChild(label);		
			var div = document.createElement("div");
			div.className="custform-controls";
			span.appendChild(div);
			if(field.ctrl=="INPUT"){
				var input = tlsearchBox.createInput(field,"text","custform-input");
				div.appendChild(input);
			}else if(field.ctrl=="INPUT_AUTOCOMPLETE"){
				var input_ID = tlsearchBox.createInputExt(field,"hidden","","_ID");	
				var input_Name = tlsearchBox.createInput(field,"text","custform-input ac_input");
				tlsearchBox.setElementAttrible(input_Name,"auto-complete","true");
				tlsearchBox.setElementAttrible(input_Name,"pair","true");
				tlsearchBox.setElementAttrible(input_Name,"url",field.dataurl);
				input_Name.autocomplete = "off";
				div.appendChild(input_ID);
				div.appendChild(input_Name);
			}else if(field.ctrl == "INPUT_DATA_RANGE"){
				var input0 = tlsearchBox.createInputExt(field,"text","custform-input-date validate[custom[dateFormat]]","_0");			
				var div_text = document.createTextNode(" - ");
				var input1 = tlsearchBox.createInputExt(field,"text","custform-input-date validate[custom[dateFormat]]","_1");
				if(field.fieldcode=="AI_PublishTime"||field.fieldcode=="B_PublishTime"||field.fieldcode=="P_Date"){
					input0.value=dateFormatter(new Date());
					input1.value=dateFormatter(new Date());
				}
				div.appendChild(input0);
				div.appendChild(div_text);
				div.appendChild(input1);
			}else if(field.ctrl == "INPUT_DATA"){
				var input0 = tlsearchBox.createInput(field,"text","custform-input-date validate[custom[dateFormat]]");
				if(field.fieldcode=="AI_PublishTime"||field.fieldcode=="B_PublishTime"||field.fieldcode=="P_Date"){
					input0.value=dateFormatter(new Date());
				}
				div.appendChild(input0);
			}else if(field.ctrl == "INPUT_DLG"){
				var inputName = tlsearchBox.createInput(field,"text","custform-input34");
				inputName.readonly = "true";
				var selectBtn = tlsearchBox.createButton("button","btn custform-button");
				tlsearchBox.setElementAttrible(selectBtn,"onclick",field.openDLG);
				var btn_text = document.createTextNode("...");
				selectBtn.appendChild(btn_text);
				var inputID = tlsearchBox.createInputExt(field,"hidden","","_ID");
				tlsearchBox.addClass(div,"input-append");
				div.appendChild(inputName);
				div.appendChild(selectBtn);
				div.appendChild(inputID);
			}else if(field.ctrl == "SELECT"){
				var sel = document.createElement("select");
				sel.id = field.fieldcode;
				sel.name = field.doctype+"."+field.fieldcode;				
				sel.className = "custform-select";
				tlsearchBox.setElementAttrible(sel,"show-all",field.showall);
				tlsearchBox.setElementAttrible(sel,"doctype",field.doctype);
				if(field.datatype=="url"){
					tlsearchBox.setElementAttrible(sel,"url",field.dataurl);
				}else if(field.datatype=="fixed"){
					if(field.showall == "true"){
						var op = document.createElement("OPTION");
						op.value = "";
						op.text = "";
						sel.options.add(op);
					}
					for(var i=0;i<field.datas.length;i++){
						var op = document.createElement("OPTION");
						op.value = field.datas[i].value;
						op.text = field.datas[i].text;
						sel.options.add(op);
					}
				}else if(field.datatype=="cat"){
					tlsearchBox.setElementAttrible(sel,"cattype",field.cattype);
				}
				
				div.appendChild(sel);
			}
		}
		ul.appendChild(li);
	},
	createInput : function(field,type,className){		
		return tlsearchBox.createInputExt(field,type,className,"");
	},
	createInputExt : function(field,type,className,idExt){
		var input = document.createElement("input");
		input.name = field.doctype+"."+field.fieldcode+idExt;
		input.doctype = field.doctype;
		input.id = field.fieldcode+idExt;
		input.className=className;
		input.type = type;
		input.value = "";
		return input;
	},
	createButton : function(type,className){
		var input = document.createElement("button");		
		input.type = "button";
		input.className=className;
		return input;
	},
	setElementAttrible : function(el,name,value){
		var attr = document.createAttribute(name);
		attr.nodeValue = value;
		el.setAttributeNode(attr);
	},
	addClass : function(el,className){
		var pattern = new RegExp("(^| )" + className + "( |$)");
		if (!pattern.test(el.className)){
			if(el.className ==" "){
				el.className = className;
			}else{
				el.className += " " + className;
			}
		}
		return true;
	}
}