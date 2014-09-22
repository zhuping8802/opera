(function(jQuery) {
	jQuery.extend({
		/**
		 * 批量请求数据
		 * param o: [{reqUrl:"", params: {}, callback: ""},{reqUrl:"", params: {}, callback: ""},...]
		 * {
		 * 	reqUrl:"", --------请求路径
		 * 	params: {}, ------------请求参数，满足ajax格式json串
		 * 	callback: ""---------------请求成功后，可执行回调函数方法名
		 * }
		 */
		ajaxBatch : function(o){
			if(o && jQuery.isArray(o)){
				var params = {ajaxBatches: JSON.stringify(o)};
				jQuery.ajax({
					url: ctx + "/ajaxBatch/doBatch.htm",
					type: "POST",
					data: params,
					async: true,
					dataType: "json",
					success: function(data){
						if(data && data.length > 0){
							for(var i = 0, len = data.length; i < len; i++){
								var obj = data[i];
								var callback = obj.callback,
									result = obj.result;
								try{
									eval(callback)(result);
								}catch(e){
									
								}
							}
						}
					},
					error: function(e){
						alert("ajaxBatch error=" + e);
					}
				});
			}else{
				alert("param is not null and param must be is Array!");
			}
		}
	});
})(jQuery);