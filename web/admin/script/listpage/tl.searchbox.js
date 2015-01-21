var tlsearchBox = {
	searchFields:{fields:[],moreFields:[]},
	init : function(searchFields){
		tlsearchBox.searchFields = searchFields;
		if($(".datagrid-toolbar").length!=1) return;
		var searchBoxHTML = "<div id=\"main_search\" style=\"padding:5px;\">";
			searchBoxHTML +="	<div id=\"searchBtnArea\">";
			searchBoxHTML +="		<div class=\"btn-group searchListGroup\">";
			searchBoxHTML +="			<button type=\"button\" class=\"btn btn-search\" onclick=\"tldatagrid.reload();\">搜索</button>";
			searchBoxHTML +="			<!--<div class=\"btn\" id=\"searchList\" onclick=\"tldatagrid.reload();\">";
			searchBoxHTML +="				<i class=\"icon-search\"></i>";
			searchBoxHTML +="				<span class=\"more\" id=\"toggleSearchAdvList\"><i class=\"caret\"></i></span>";
			searchBoxHTML +="			</div>-->";
			searchBoxHTML +="		</div>";
			searchBoxHTML +="	</div>";
			searchBoxHTML +="	<div id=\"divQueryCust\" style=\"margin-right:40px;\">";
			searchBoxHTML +="	</div>";
			searchBoxHTML +="</div>";
		$(".datagrid-toolbar").prepend(searchBoxHTML);
		
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
		span.id="SPAN_"+field.code;
		li.appendChild(span);
		var label = document.createElement("label");
		label.id="LABEL_"+field.code;
		if(field.edittype=="CHECK"){
			if(field.datatype="YN"){
				label.className = "custform-label-checkbox checkbox";
				tlsearchBox.setElementAttrible(label,"for",field.code);
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
			if(field.edittype=="INPUT"){
				var input = tlsearchBox.createInput(field,"text","custform-input");
				div.appendChild(input);
			}else if(field.edittype=="INPUT_AUTOCOMPLETE_KV"){
				var input_ID = tlsearchBox.createInputExt(field,"hidden","","_ID");	
				var input_Name = tlsearchBox.createInput(field,"text","custform-input ac_input");
				tlsearchBox.setElementAttrible(input_Name,"auto-complete","true");
				tlsearchBox.setElementAttrible(input_Name,"pair","true");
				tlsearchBox.setElementAttrible(input_Name,"url",field.dataurl);
				input_Name.autocomplete = "off";
				div.appendChild(input_ID);
				div.appendChild(input_Name);
			}else if(field.edittype == "INPUT_DATE_RANGE"){
				var input0 = tlsearchBox.createInputExt(field,"text","custform-input-date validate[custom[dateFormat]]","_0");			
				var div_text = document.createTextNode(" - ");
				var input1 = tlsearchBox.createInputExt(field,"text","custform-input-date validate[custom[dateFormat]]","_1");
				if(field.code=="AI_PublishTime"||field.code=="B_PublishTime"||field.code=="P_Date"){
					input0.value=dateFormatter(new Date());
					input1.value=dateFormatter(new Date());
				}
				div.appendChild(input0);
				div.appendChild(div_text);
				div.appendChild(input1);
			}else if(field.edittype == "INPUT_DATE"){
				var input0 = tlsearchBox.createInput(field,"text","custform-input-date validate[custom[dateFormat]]");
				if(field.code=="AI_PublishTime"||field.code=="B_PublishTime"||field.code=="P_Date"){
					input0.value=dateFormatter(new Date());
				}
				div.appendChild(input0);
			}else if(field.edittype == "INPUT_DLG"){
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
			}else if(field.edittype == "SELECT"){
				var sel = document.createElement("select");
				sel.id = field.code;
				sel.name = field.code;				
				sel.className = "custform-select";
				tlsearchBox.setElementAttrible(sel,"show-all",field.showall);
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
		input.name = field.doctype+"."+field.code+idExt;
		input.doctype = field.doctype;
		input.id = field.code+idExt;
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
		attr.value = value;
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
	},
	getQueryConditions : function(){
		var conditions = [];
		if(tlsearchBox.searchFields.fields && tlsearchBox.searchFields.fields.length>0){
			var fields = tlsearchBox.searchFields.fields;
			for(var i in fields){
				tlsearchBox._oneQueryCondition(conditions,fields[i]);
			}
		}
		
		if(tlsearchBox.searchFields.moreFields && tlsearchBox.searchFields.moreFields.length>0){
			var fields = tlsearchBox.searchFields.moreFields;
			for(var i in fields){
				tlsearchBox._oneQueryCondition(conditions,fields[i]);
			}
		}
		return conditions;
	},
	_oneQueryCondition : function(conditions,field){
		var condition = {};
		condition.ext = 0;
		condition.values = [];
		if(field.edittype == "INPUT"
			|| field.edittype == "INPUT_DATE"
			|| field.edittype == "SELECT"
			|| field.edittype == "INPUT_AUTOCOMPLETE") {
			if($("#"+field.code).val()!=""){
				condition.values.push({"value":$("#"+field.code).val()});
			}
		}else if(field.edittype == "INPUT_DATE_RANGE"){
			var value1 = $("#"+field.code+"_0").val();
			var value2 = $("#"+field.code+"_1").val();
			if(value1 != "" || value2 != ""){
				condition.values.push({"value":value1});
				condition.values.push({"value":value2});
			}
		}else if(field.edittype == "INPUT_AUTOCOMPLETE_KV"
			|| field.edittype == "INPUT_DLG_KV"){
			var idValue = $("#"+field.extcode).val();
			var value = $("#"+field.code).val();
			if(!idValue && idValue!=""){
				value = idValue;
				condition.ext = 1;
			}
			if(!value && value!="") condition.values.push({"value":value});
		}
		if(condition.values.length>0){
			condition.name = field.name;
			condition.code = field.code;
			condition.extcode = field.extcode;
			condition.edittype = field.edittype;
			condition.operator = field.operator;
			condition.quote = field.quote;
			condition.sql = field.sql;
			
			conditions.push(condition);
		}
	},
	getQuery : function(){
		var values = [];
		tlsearchBox._oneTypeParams(values, "#queryForm select");
		tlsearchBox._oneTypeParams(values, "#queryForm input[type='checkbox']");
		tlsearchBox._oneTypeParams(values, "#queryForm input[type='radio']");
		tlsearchBox._oneTypeParams(values, "#queryForm input[type='hidden']");
		tlsearchBox._textParams(values, "#queryForm input[type='text']");
		
		if (values.length < 1) return "";
		
		var result = "@QUERYCODE@=QUERYCODE";
		for (var i = 0; i < values.length; i++) {
			result += "&" + tlsearchBox._encodeSpecialCode(values[i].name) + "=" + tlsearchBox._encodeSpecialCode(values[i].value);
		}
		
		return result;
	},
	_oneTypeParams : function(values, oneType) {
		var fields = $(oneType).serializeArray();
		jQuery.each( fields, function(i, sel){
			if (sel.value) {
				values.push(sel);
			}
		});
	},
	//对input text的处理：若hidden的ID域没有值，才加text值
	_textParams : function(values, oneType) {
		var fields = $(oneType).serializeArray();
		jQuery.each( fields, function(i, sel){
			if (sel.value) {
				if (!tlsearchBox._hasHiddenValue(values, sel.name))
					values.push(sel);
			}
		});
	},
	_hasHiddenValue : function(values, name) {
		for (var i = 0; i < values.length; i++) {
			if (values[i].name == name + "_ID" || values[i].name == name + "ID" )
				return true;
		}
		return false;
	},
	_encodeSpecialCode : function(param1){
		if (!param1) return "";

		var res = "";
		for(var i = 0;i < param1.length;i ++){
			switch (param1.charCodeAt(i)){
				case 0x20://space
				case 0x3f://?
				case 0x23://#
				case 0x26://&
				case 0x22://"
				case 0x27://'
				case 0x2a://*
				case 0x3d://=
				case 0x5c:// \
				case 0x2f:// /
				case 0x2e:// .
				case 0x25:// .
					res += escape(param1.charAt(i));
					break;
				case 0x2b:
					res += "%2b";
					break;
				default:
					res += encodeURI(param1.charAt(i));
			}
		}
		return res;
	}
}