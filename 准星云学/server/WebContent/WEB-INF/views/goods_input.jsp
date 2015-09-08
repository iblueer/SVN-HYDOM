<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%
	String path = request.getContextPath() + "/";
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path;
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link href="<%=basePath %>bootstrap/css/bootstrap.css" rel="stylesheet"/>
<title>
    	新增物流信息
</title>
</head>
<body>
	<div class="panel panel-primary widget-newsletter">
		<div class="panel-heading">
			<h4 class="panel-title">新增物流信息</h4>
		</div>
		<div class="panel-body">
			<form name="inputForm" id="inputForm"
				action="<c:if test="${empty goods.id }"><%=basePath %>goods/save.do?companyid=${companyid }</c:if><c:if test="${!empty goods.id }"><%=basePath %>goods/update.do</c:if>" method="post">
				<div class="form-group">
						<label class="col-sm-4 control-label">
							物流地址：
						</label>
						<div class="col-sm-6">
							<input class="form-control" type="text" id="goodsadd" name="goodsadd" title="请填写邮寄地址" placeholder="邮寄地址(物流地址)" required >
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-4 control-label">
							邮编：
						</label>
						<div class="col-sm-6">
							<input class="form-control" type="text" id="goodspost" name="goodspost" title="请填写邮政编码" placeholder="邮政编码" required>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-4 control-label">
							收货人：
						</label>
						<div class="col-sm-6">
							<input class="form-control" type="text" id="goodsname" name="goodsname" title="请填写收货人姓名" placeholder="收货人姓名" required >
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-4 control-label">
							收货人电话：
						</label>
						<div class="col-sm-6">
							<input class="form-control" type="text" id="goodstel" name="goodstel" title="请填写收货人联系电话" placeholder="收货人联系电话" required >
						</div>
					</div>
					
					<div class="form-group">
						<label class="col-sm-4 control-label">
							默认物流：
						</label>
						<div class="col-sm-6">
							<div class="checkbox block">
								<label>
									<input type="checkbox" id="isdefault" name="isdefault">
									勾选代表默认账户
								</label>
							</div>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-4 control-label">
							附件：
						</label>
						<div class="col-sm-4">
							<input type="file" name="file" id="file">						
						</div>
						<div class="col-sm-4"><input type="button" value="上传" id="upload"></div>
					</div>
					<div class="form-group">
						<label class="col-sm-4 control-label"></label>
						<div class="col-sm-4" id="files"></div>
					</div> 
							<input class="form-control" type="hidden" id="goodatt" name="goodatt"/>


							<input class="form-control" type="hidden" id="publicid" name="company.id" value="${companyid }"/>


				<div class="form-group">
						<label class="col-sm-4 control-label">
							备注：
						</label>
						<div class="col-sm-8">
							<textarea class="form-control" rows="5" id="bz" name="bz"></textarea>
						</div>
					</div>
					<div class="panel-footer">
						<div class="row">
							<div class="col-sm-9 col-sm-offset-5">
								<button class="btn btn-primary">保 存</button>
								<!-- <button class="btn btn-success">关 闭</button> -->
							</div>
						</div>
					</div>
			</form>
		</div>
		<!-- panel-body -->
	</div>

	<script src="<%=basePath%>bootstrap/js/jquery-1.11.1.min.js"></script>
<script src="<%=basePath%>bootstrap/js/jquery-migrate-1.2.1.min.js"></script>
<script src="<%=basePath%>bootstrap/js/bootstrap.min.js"></script>
<script src="<%=basePath%>bootstrap/js/modernizr.min.js"></script>
<script src="<%=basePath%>bootstrap/js/pace.min.js"></script>
<script src="<%=basePath%>bootstrap/js/retina.min.js"></script>
<script src="<%=basePath %>bootstrap/js/jquery.gritter.min.js"></script>
<script src="<%=basePath %>bootstrap/js/jquery.cookies.js"></script>
<script src="<%=basePath %>bootstrap/js/jquery.validate.min.js"></script>
<script src="<%=basePath %>bootstrap/js/jquery.form.js"></script>
<script src="<%=basePath %>bootstrap/js/custom.js"></script>
<script src="<%=basePath %>bootstrap/js/ajaxfileupload.js"></script>

<script type="text/javascript">

jQuery(document).ready(function() {
       // Error Message In One Container
       $("#inputForm").validate({
           errorLabelContainer: jQuery("#inputForm div.errorForm")
       });
       
       /*  $("#upload").click(function(){
      	 form.ajaxSubmit(options);
       }); */
       
       $("#upload").click(function(){
    	   		upload();
				});
       $("#isdefault").attr("checked",true);
       $("#isdefault").click(function(){
     	  if($(this).attr("checked")==undefined){
     		  $(this).val("false");
     	  }
     	 // alert($(this).val());
       });
       
});
       var attid ="";
       function upload(){
    	   var myfile = $("#file").val();
    	   //alert(myfile);
			if(myfile!=""){
				//异步上传
				$.ajaxFileUpload({
						url:"<%=basePath%>upload.do",
						type : "post",
						fileElementId : "file",
						dataType : "JSON",
						secureuri : false,
						success : function(data) {
							//alert(data);
							//alert(data.data);
							//var obj =JSON.parse(data);
							var obj = eval("(" + data + ")");

							attid += obj.data.id +",";
							$("#goodatt").val(attid);
							//alert(obj.data.smaillpath);
							//var jsondata = eval('(' + data + ')');
							//alert(jsondata);
							//alert(jsondata.attname);
							var filename = obj.data.attname;

							var filepath = obj.data.smaillpath;
							$("#files").append("<img name='"+filename+"' src='../"+filepath+"' class='img-thumbnail' style='width:100px;height:50px;'/>");
						}
					});
		}else{
			alert("请选择要上传的文件或图片!");
		}
	}
</script>

</body>
</html>
