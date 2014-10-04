package com.tl.invest.workspace;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tl.kernel.web.BaseController;

public class AfterProcess extends BaseController {

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	protected void handle(HttpServletRequest request,
			HttpServletResponse response, Map model) throws Exception {
		String callMode = get(request, "callMode", "1");
		String refresh = get(request, "refresh", "true");
		String closedlg = get(request, "closedlg","true");
		String errors = get(request, "errors","");
		String itemIDs = get(request, "itemIDs", "");
		
		model.put("callMode", callMode);
		model.put("refresh", refresh);
		model.put("closedlg", closedlg);
		model.put("errors", errors);
		model.put("itemIDs", itemIDs);
		
		model.put("@VIEWNAME@", "common/After");
	}

}
