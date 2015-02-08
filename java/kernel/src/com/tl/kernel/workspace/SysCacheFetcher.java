package com.tl.kernel.workspace;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tl.common.StringUtils;
import com.tl.kernel.config.CacheConfig;
import com.tl.kernel.config.CacheInfo;
import com.tl.kernel.config.ConfigReader;
import com.tl.kernel.context.CacheManager;
import com.tl.kernel.web.BaseController;

public class SysCacheFetcher extends BaseController {

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	protected void handle(HttpServletRequest request,
			HttpServletResponse response, Map model) throws Exception {
		String action = get(request, "action","");
		if("list".equals(action)){
			ConfigReader reader = ConfigReader.getInstance();
			CacheConfig cacheConfig = reader.getCacheConfig();
			StringBuffer sb = new StringBuffer();
			if (cacheConfig != null){
				CacheInfo[] cacheArr  = cacheConfig.getCaches();
				for (CacheInfo cache : cacheArr) {
					if(sb.length()>0) sb.append(",");
					sb.append("{");
					sb.append("\"name\":\""+cache.getName()+"\"");
					sb.append(",\"class\":\""+cache.getInvokeClass()+"\"");
					sb.append("}");
				}
			}
			sb.insert(0, "[");
			sb.append("]");
			output(sb.toString(), response);
		}else if("refresh".equals(action)){
			String refreshCache = get(request, "caches", "");
			String[] caches = refreshCache.split(",");
			for (String cacheName : caches) {
				CacheManager.refresh(cacheName);
			}
			output("{\"succees\":true,\"msg\":\"Ë¢ÐÂ³É¹¦\"}", response);
		}
	}

}
