var pageUrl = "";
var self = {
	init : function(url) {
		pageUrl = url;
		$("#btnFirstpage").click(function(){self.changePage(4)});
		$("#btnLastpage").click(function(){self.changePage(0)});
		$("#btnNextpage").click(function(){self.changePage(1)});
		$("#btnFinalpage").click(function(){self.changePage(5)});
		//self.getInitMsg();
	},
	changePage : function(flag, page){
		if (!self._getText("Pages")) return;
		
		var currentpage = self._calculatePage(flag, page);
		
		if (currentpage < 0) return;
		
		self.refresh4Page(currentpage);
		self.setCurrentPage(currentpage);
	},
	setCurrentPage : function(value){
		var a = parseInt(value);
		self._setText("CurrentPage", a);
	},
	setPageCount : function(value){
		var a = parseInt(value);
		self._setText("Pages", a);
	},
	//翻页
	refresh4Page : function(currentpage, url) {
		if(url != null && url.length > 0) pageUrl = url + "&curPage=" + currentpage;
		else if(pageUrl != null && pageUrl.length > 0) pageUrl += "&curPage=" + currentpage; 
		window.location.href = pageUrl;
	},
	//修改一个元素的Text
	_setText : function(name, value){
		var span = document.getElementById(name);
		if (typeof span.innerText == "undefined")
			span.textContent = value;
		else
			span.innerText = value;
	},
	//获取一个元素的Text
	_getText : function(name){
		var span = document.getElementById(name);
		if (typeof span.innerText == "undefined")
			return span.textContent;
		else
			return span.innerText;
	},
	//计算应该到哪一页
	_calculatePage : function(flag, page) {
		var pages = parseInt(self._getText("Pages"));
		
		if (pages <= 1) return -1;/* 只有一页时，不存在页数变化的问题 */
		
		var currentpage = parseInt(self._getText("CurrentPage"));
		switch (flag) {
			case 0: /* 上一页 */
				if (currentpage != 1)
					currentpage--;
				break;
			case 1:/* 下一页 */
				if (currentpage != pages)
					currentpage++;
				break;
			case 2:/* 跳转页 */
				var jumpto =  parseInt(document.getElementById("Turn2Page").value);
				if (!jumpto || jumpto > pages || jumpto <= 0){
					alert(statuserror.invalidPage);
					return -1;
				}
				currentpage = --jumpto;
				break;
			case 3: /*第几页*/
				currentpage = page;
				break;
			case 4:/*第一页*/
				currentpage = 1;
				break;
			case 5:/* 最后一页 */
				currentpage = pages;
				break;
			default:
				currentpage = 0;
				break;
		}
		return currentpage;
	}
}