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
				reqUrl: "http://localhost:8080/opera/springjson/show.do?phone.phone=2222&phone.telPhone=3333&name=zh&eamil=d&mobiles[0].name=dddd&mobiles[1].name=apple&numbers[n1]=21&numbers[0]=44&array[4]=aaaa&mobiles[1].info.desc=appledesc",
				callback: "showFn"
			},{
				reqUrl: "http://localhost:8080/opera",
				callback: "operaFn"
			}])
		});
		
		function showFn(data){
			alert(data);
		}
		
		function operaFn(data){
			alert(data);
		}
	</script>
</body>
</html>