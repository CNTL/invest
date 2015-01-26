package com.tl.invest.workspace;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.tl.common.Pair;
import com.tl.common.ResourceMgr;
import com.tl.common.StringUtils;
import com.tl.common.log.Log;
import com.tl.db.DBSession;
import com.tl.db.IResultSet;
import com.tl.invest.workspace.query.QueryField;
import com.tl.invest.workspace.query.QueryOperator;
import com.tl.invest.workspace.query.QueryParser;
import com.tl.invest.workspace.query.QueryUtil;
import com.tl.kernel.context.Context;
import com.tl.kernel.web.BaseController;
@SuppressWarnings({ "rawtypes", "unused" })
public class TbListController extends BaseController {	
	@Override
	protected void handle(HttpServletRequest request,
			HttpServletResponse response, Map model) throws Exception {
		String order = get(request,"order","desc");
		String sort = get(request, "sort", "");
		//�õ���ѯ����
		String primarykey = get(request, "primarykey","");
		String tbView = get(request, "tbview","");
		String rule = get(request, "rule","");
		boolean showFooter = "true".equals(get(request, "showfooter", "false"))? true : false;
		
		String action = get(request, "action", "getdata");
		if("getdata".equals(action)){
			int page = getInt(request, "page",1);
			int pageSize = getInt(request, "rows",50);
			//�õ������ֶ�
			JSONObject sumFieldJsonObject = getJsonObject(request, "sumfields");
			//���ܴ���
			JSONArray sumFieldArray = sumFieldJsonObject.getJSONArray("fields");
			List<Pair> sumFields = new ArrayList<Pair>();
			for (int i=0;i<sumFieldArray.size();i++) {	
				JSONObject sumField = sumFieldArray.getJSONObject(i);
				sumFields.add(new Pair(sumField.getString("name"), sumField.getString("code")));
			}
			
			String jsondata = "";
						
			if(StringUtils.isNotEmpty(tbView)){
				//�õ�ѡ��鿴�ֶ�
				JSONObject selFieldJsonObject = getJsonObject(request, "selectfields");
				JSONArray selFields = selFieldJsonObject.getJSONArray("fields");
				
				JSONObject searchFieldJsonObject = getJsonObject(request, "queryconditions");
				JSONArray searchFields = searchFieldJsonObject.getJSONArray("fields");
				//ѡ���ֶδ���
				List<Pair> selectFields = new ArrayList<Pair>();
				for (int i=0;i<selFields.size();i++) {	
					JSONObject selfield = selFields.getJSONObject(i);
					selectFields.add(new Pair(selfield.getString("name"), selfield.getString("code")));
				}
				
				QueryField[] queryFields = QueryUtil.getQueryFields(searchFields);
				
				QueryParser parser = (QueryParser)Context.getBean(QueryParser.class);
				//��ͼ��ѯ����
				String viewWhere = parser.getSQL(queryFields);
				if(StringUtils.isNotEmpty(viewWhere) && StringUtils.isNotEmpty(rule)){
					viewWhere += " and ";
				}
				viewWhere += rule;
				jsondata = queryFromView(tbView,primarykey,selectFields,sumFields,order,sort,viewWhere,page,pageSize,showFooter);
			}
			
			output(jsondata, response);
		}
	}
	
	
	private String queryFromView(String tbView, String primarykey,
			List<Pair> selectFields, List<Pair> sumFields, String order,
			String sort, String whereSQL, int page, int pageSize,boolean showFooter) {
		String selFieldsStr = "";
		for (Pair field : selectFields) {
			if(StringUtils.isNotEmpty(selFieldsStr)) selFieldsStr += ",";
			selFieldsStr += field.getStringValue();
		}
		
		String sql = "select "+selFieldsStr+" from "+tbView+" where "+whereSQL+" order by "+sort+" "+ order;
		DBSession dbSession = null;
		IResultSet rs = null;
		StringBuffer sb = new StringBuffer();
		sb.append("{\"total\":");
		try{
			dbSession = Context.getDBSession();
			sql = dbSession.getDialect().getLimitString(sql, pageSize*(page-1), pageSize);
			rs = dbSession.executeQuery(sql);
			String countSql = "select count(0) from "+tbView+" where  "+whereSQL;
			if(log.isDebugEnabled()){
				log.debug(sql);
				log.debug(countSql);
			}
			sb.append(getCount(countSql,dbSession)+",\"rows\":[");
			
			StringBuffer sb1 = new StringBuffer();
			while (rs.next()) {
				if(sb1.length()>0) sb1.append(",");
				sb1.append("{");
				StringBuffer sb2 = new StringBuffer();				
				for (Pair field : selectFields) {
					if(sb2.length()>0) sb2.append(",");
					String code = field.getStringValue();
					String value = rs.getString(code);
					if(value==null) value = "";
					if(StringUtils.isNotEmpty(value))
						value = value.replace("\n", "").replace("\r", "");
					sb2.append("\""+code+"\":\""+value+"\"");
				}
				sb1.append(sb2);
				sb1.append("}");
			}
			
			sb.append(sb1);
			sb.append("]");
			if(showFooter){
				sb.append(",\"footer\":[{");
				List<Pair> pairs = getSumView(sumFields,whereSQL ,dbSession,tbView);
				if(pairs!=null){
					StringBuffer sbFooter = new StringBuffer();
					for (Pair pair : pairs) {
						if(sbFooter.length()>0) sbFooter.append(",");
						sbFooter.append("\""+pair.getKey()+"\":\""+pair.getStringValue()+"\"");
					}
					sb.append(sbFooter.toString());
				}
				sb.append("}]");
			}else{
				sb.append(",\"footer\":[]");
			}
			sb.append("}");
		}catch(Exception ex){
			sb = new StringBuffer();
			sb.append("{\"total\":");
			sb.append("0,\"rows\":[],\"footer\":[]}");
		}finally{
			ResourceMgr.closeQuietly(rs);
			ResourceMgr.closeQuietly(dbSession);
		}
		return sb.toString();
	}
	
	private int getCount(String sql,DBSession dbSession){
		int count =0;
		//DBSession dbSession = null;
		IResultSet rs = null;
		try{
			//dbSession = Context.getDBSession();
			rs = dbSession.executeQuery(sql);
			if(rs.next())
				count = rs.getInt(1);
		}catch(Exception ex){
		}finally{
			ResourceMgr.closeQuietly(rs);
		}
		
		return count;
	}
	
	private List<Pair> getSumView(List<Pair> sumFields,String whereSQL,DBSession dbSession,String view){
		if(sumFields==null || sumFields.size()<=0) return null;
		List<Pair> pairs = new ArrayList<Pair>();
		//DBSession dbSession = null;
		IResultSet rs = null;
		String selectSumField = "";
		for (Pair pair : sumFields) {
			if(!"".equals(selectSumField)) selectSumField += ",";
			selectSumField += "sum("+pair.getStringValue()+") as "+pair.getStringValue();
		}
		if("".equals(selectSumField)) return null;
		try {
			String sql = "select "+selectSumField+" from "+view+" where  "+whereSQL;
			//System.out.println(sql);
			rs = dbSession.executeQuery(sql);
			if(rs.next()){
				for (Pair pair : sumFields) {
					Pair p = new Pair();
					p.setKey(pair.getStringValue());
					p.setValue(rs.getBigDecimal(pair.getStringValue()));
					pairs.add(p);
				}
			}
		} catch (Exception e) {
		}finally{
			ResourceMgr.closeQuietly(rs);
		}
		
		return pairs;
	}


	protected JSONObject getJsonObject(HttpServletRequest request,String jsonName) {
		String jsonString = "";
		jsonString = get(request, jsonName);
		JSONObject jsonObject = null;
        try{    
            jsonObject = JSONObject.fromObject(jsonString);    
        }catch(Exception e){    
            e.printStackTrace();    
        }
        return jsonObject;
	}
}
