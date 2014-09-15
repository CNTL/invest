<%@ include file="../include/Include.jsp"%>
<HTML>
<HEAD>
	<TITLE>AfterProcess</TITLE>
	<META HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=UTF-8">
	<script type="text/javascript">
		function getOuterWindow(){
			var x = window;
			while (x.parent != null){
				if (x == x.parent) break;
				x = x.parent;
			}
			return x;
		}
		function closeWindow(retValue, callMode){
			var opWnd = getOuterWindow();
			if (opWnd == null) opWnd = window;
			
		    var parentWnd = (callMode == 0) ? opWnd.opener : opWnd.parent;
		    try {
				//重新加载父页面
				if (parentWnd.query) {
					parentWnd.query();
				}
				window.close();
			}catch (e){
			}
			
			if (callMode == 1) opWnd.close();
		}
		function doClose() {
			var callMode = parseInt("<c:out value="${callMode}"/>");
			var needRefresh = "<c:out value="${needRefresh}"/>";
			/**
			 * 若操作需要刷新，则调用刷新方法:
			 * 在DocList.js和Statusbar.js中同样定义refreshPage方法
			 * 则操作从工具栏启动和从文档列表右键菜单启动都可以正常返回刷新
			 */
			if (needRefresh)
				closeWindow("OK", callMode);
			else
				closeWindow(null, callMode);
		}
	</script>
</HEAD>

<BODY onload="doClose()">
</BODY>
</HTML>