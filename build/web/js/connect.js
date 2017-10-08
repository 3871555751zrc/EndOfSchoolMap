var connect = function(request, success, failure) {
		$.ajax({
			url: "http://176547or77.51mypc.cn/SchoolMapBackEnd/Service" + "?type=" + request.type,
			method: "post",
			data: {
				request: JSON.stringify(request.data)
			},
			success: function(data) {
				var json = JSON.parse(data);
				if(json["success"]) {
					if(success && typeof success === "function") {
						success(json);
					}
				} else {
					if(failure && typeof failure === "function") {
						failure(json);
					}
				}
			},
			error: function() {
                   console.log("dfd");
			}
		});
	};