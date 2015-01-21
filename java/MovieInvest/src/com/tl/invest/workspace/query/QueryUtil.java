package com.tl.invest.workspace.query;

import java.util.ArrayList;
import java.util.List;

import com.tl.common.StringUtils;
import com.tl.common.log.Log;
import com.tl.kernel.context.Context;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class QueryUtil {
	static Log log = Context.getLog("invest");
	
	public static QueryField[] getQueryFields(JSONArray jsonArray){
		if(log.isDebugEnabled())
			log.debug(jsonArray.toString());
			
		List<QueryField> queryFields = new ArrayList<QueryField>();
		for (int i=0;i<jsonArray.size();i++) {
			JSONObject qField = jsonArray.getJSONObject(i);
			QueryField f = new QueryField();
			f.setCode(qField.has("code")?qField.getString("code"):"");
			f.setExtCode(qField.has("extcode")?qField.getString("extcode"):f.getCode());
			f.setExt(qField.has("ext")?("1".equals(qField.getString("ext"))||"true".equals(qField.getString("ext"))):false);
			f.setName(qField.has("name")?qField.getString("name"):"");
			f.setEditType(Enum.valueOf(QueryEditType.class,qField.has("edittype")?qField.getString("edittype").toUpperCase():"INPUT"));
			f.setOperator(Enum.valueOf(QueryOperator.class, qField.has("operator")?qField.getString("operator").toUpperCase():"EQ"));
			f.setSql(qField.has("sql")?qField.getString("sql"):"");
			boolean needQuote = false;
			String quote = qField.has("quote")?qField.getString("quote"):"false";
			if("true".equals(quote) || "1".equals(quote)){
				needQuote = true;
			}
			f.setNeedQuote(needQuote);
			
			List<QueryFieldValue> valueList = new ArrayList<QueryFieldValue>();
			if(qField.has("values")){
				JSONArray valuesJA = qField.getJSONArray("values");
				for (int j=0;j<valuesJA.size();j++) {
					JSONObject valueObject = valuesJA.getJSONObject(j);
					QueryFieldValue v = new QueryFieldValue();
					v.setValue(valueObject.has("value")?valueObject.getString("value"):"");
					if(StringUtils.isNotEmpty(v.getValue()))
						valueList.add(v);
				}
			}
			f.setValues((QueryFieldValue[])valueList.toArray(new QueryFieldValue[0]));
			if(StringUtils.isNotEmpty(f.getCode()) && f.getValues().length>0)
				queryFields.add(f);
		}
		
		return (QueryField[])queryFields.toArray(new QueryField[0]);
	}
}
