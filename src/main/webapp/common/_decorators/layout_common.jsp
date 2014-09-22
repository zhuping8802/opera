<%@ page isELIgnored="false" language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>  
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<title><decorator:title default="" /></title>
		<c:set var="ctx" value="http://${pageContext.request.localAddr}:${pageContext.request.localPort}${pageContext.request.contextPath}"/>
		<script type="text/javascript">
			var ctx = '${ctx}';
		</script>
		<script src="${ctx}/common/js/jquery/jquery-1.8.0.js" charset="utf-8" type="text/javascript"></script>
		<script src="${ctx}/common/js/jquery/json2.js" charset="utf-8" type="text/javascript"></script>
		<script src="${ctx}/common/js/module/ajaxBatch.js" charset="utf-8" type="text/javascript"></script>
		<decorator:head />
	</head>
	
	<body>
		<decorator:body />
	</body>
</html>