<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	Test ajaxBatch Page!
	<script type="text/javascript">
		$(function(){
			$.ajaxBatch([{
				reqUrl: "http://192.168.50.121/rdm/modelAction/queryModel.rdm",
				callback: "queryModelCallback"
			}])
		});
		function queryModelCallback(data){
			alert(data);
		}
	</script>
</body>
</html>