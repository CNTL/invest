<%@ include file="../../include/Include.jsp"%>
<%@page pageEncoding="UTF-8"%>
<!doctype html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
 <%@include file="../inc/script.inc"%>
<%@include file="../inc/csslink.inc"%>
<script type="text/javascript" src="../js/plugin/query/jquery.query.js"></script>
<script type="text/javascript" src="../js/layer/layer.min.js"></script>
<script type="text/javascript" src="../userout/script/userCommon.js"></script>
<script type="text/javascript" src="../proj/script/datas.js"></script>
<script type="text/javascript" src="../user/user/script/orgDetailInfo.js"></script>
</head>
<body>
<%@include file="../../inc/header.inc"%>
<div class="shadow"></div>
<div class="container container-ex">
	<%@include file="../inc/userHeader.inc"%>
	<div class="row container-wapper">
	<%@include file="../inc/userHeaderMenu.inc"%>
	<div class="col-md-8">
        <div class="container-right">
        	<form class="form-horizontal" role="form"  id="form" name="form" action="">
        	
        		  <div class="form-group">
                      <label for="orgFullname" id="lbcompany"  class="col-sm-3 control-label">公司全称：</label>
                      <div class="col-sm-9">
                          <input type="text" class="form-control validate[maxSize[255],required]" type="text" id="orgFullname" name="orgFullname" placeholder="机构全称">
                      </div>
                  </div>

                   <div class="form-group">
                      <label for="location" class="col-sm-3 control-label">招聘地址：</label>
                      <div class="col-sm-6">
                         <input type="text" class="form-control" id="location" name="location" value="" placeholder="招聘地址"/>
				         <input type="hidden" class="form-control input-lg" id="coordinate" name="coordinate" value="" placeholder="招聘地址坐标"/>
                      </div>
                      <div  class="col-sm-2">
                      	<input type="button" class="btn btn-info" id="mapSearch" value="从地图搜索">
                      </div>
                  </div>
                  
                   <div class="form-group" id="orgNatruecontainer">
                      <label for="orgNature" class="col-sm-3 control-label">公司性质：</label>
                      
                      <div class="col-sm-4">
                          
					         <select id="orgNatureSel" name="orgTradeSel" class="form-control validate[maxSize[255],required]" style="width:170px">
						        <option value="国营">国营</option>
						        <option value="外商独资">外商独资</option>
						        <option value="中外合资">中外合资</option>
						        <option value="私营企业">私营企业</option>
						        <option value="其他">其他</option>
					        </select>
                      </div>
                      <div class="col-sm-3">
                      <input class="form-control validate[maxSize[255]]" type="text" id="orgNature" name="orgNature" style="width:200px" placeholder="请输入公司性质"/>
                      </div>
                  </div>
                  
                    <div class="form-group">
                      <label id="lborgScale" for="orgScale" class="col-sm-3 control-label">公司规模：</label>
                      <div class="col-sm-6">
                          <select id="orgScale" name="orgScale" class="form-control validate[maxSize[255],required]" style="width:400px">
						        <option value="1">10人以下</option>
						        <option value="2">10-50人</option>
						        <option value="3">50人以上</option>
						       
					        </select>
                      </div>
                  </div>
                  
                  <div class="form-group">
                      <label id="lborgHomePage" for="orgHomePage" class="col-sm-3 control-label">公司主页：</label>
                      <div class="col-sm-9">
                          <input type="text" class="form-control" type="text" id="orgHomePage" name="orgHomePage" placeholder="公司主页">
                      </div>
                  </div>
                  
                 
                  <div class="form-group">
                     <div class="col-sm-12 text-center">
                         <button type="submit" class="btn btn-primary" id="btnSave">保存信息</button>
                     </div>
                 </div>
                 
        	</form>
        </div>
     </div>
	</div>
</div>

<div style="display:none" style="width:680px;height:490px;">
						<iframe id="msgMap" src="../common/MsgMap.jsp" width="670" align="center" height="480"></iframe>
					</div>
<input class="form-control" type="hidden" id="orgTrade" name="orgTrade" style="width:400px"/>
<!-- script -->

<!-- script -->

<!-- footer -->
<%@include file="../../inc/footer.inc"%>
<!-- footer -->

</body>
</html>