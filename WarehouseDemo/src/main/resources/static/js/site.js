$(document).ready(function() {
	console.log("ready");

	let messages = {};
	$.getJSON("/messages", function(data) {
		messages = data;
	});

	$("#entry-form").hide(0);

	$("#search-button").click(function() {
		$("#entry-form").hide(500);
		var searchTerm = $("#searchTerm").val();
		console.log("You searched for " + searchTerm);

		$.ajax({
			url: "http://localhost:8080/api/v1/products/search/" + searchTerm,
			success: function(result) {
				//console.log(result);
				var outputHTML = "";
				for (var x = 0; x < result.length; x++) {
					var product = result[x];
					outputHTML += "<div class='single-product mt-3'><ul>";
					outputHTML += "<li>" + messages["id"] + ": " + product.id + "</li>";
					outputHTML += "<li>" + messages["productNumber"] + ": " + product.productNumber + "</li>";
					outputHTML += "<li>" + messages["productName"] + ": " + product.productName + "</li>";
					outputHTML += "<li>" + messages["price"] + ": " + product.price + "</li>";
					outputHTML += "<li>" + messages["quantity"] + ": " + product.quantity + "</li>"; ``
					outputHTML += "</ul>";
					outputHTML += "<button class = 'edit-button btn btn-secondary mr-1' value = '" + product.id + "'>Edit</button>";
					outputHTML += "<button class = 'delete-button btn btn-secondary' value = '" + product.id + "'>Delete</button>";
					outputHTML += "</div>"
				}
				//console.log(outputHTML);
				$("#results-box").html(outputHTML);
			}
		});
	});

	$("#add-button").click(function() {
		$("#entry-form").show(500);
		$("#form-ok").hide();
		$("#form-add").show();
		$("#form-title").text(messages["addProduct"]);
		$("#form-close").text(messages["btn.cancel"]);
		$("#id").val('');
		$("#productNumber").val('p-');
		$("#productName").val('');
		$("#price").val('');
		$("#quantity").val('');
	});

	$("#form-add").click(function() {
		var obj = new Object();
		obj.productNumber = $("#productNumber").val();
		obj.productName = $("#productName").val();
		obj.price = $("#price").val();
		obj.quantity = $("#quantity").val();

		ajaxSubmitProduct("http://localhost:8080/api/v1/products/", "POST", obj);
	});

	$(document).on("click", ".edit-button", function() {
		const editIdNumber = $(this).val();
		//console.log(editIdNumber);

		$.ajax({
			url: "http://localhost:8080/api/v1/products/" + editIdNumber,
			type: 'GET',
			success: function(result) {
				//console.log(result);
				$("#id").val(result.id);
				$("#productNumber").val(result.productNumber);
				$("#productName").val(result.productName);
				$("#price").val(result.price);
				$("#quantity").val(result.quantity);

				$("#entry-form").show(500);
				$("#form-add").hide();
				$("#form-ok").show();
				$("#form-title").text(messages["editProduct"]);
				$("#form-close").text(messages["btn.cancel"]);
			}
		})
	});

	$("#form-ok").click(function() {
		var obj = new Object();
		obj.id = $("#id").val();
		obj.productNumber = $("#productNumber").val();
		obj.productName = $("#productName").val();
		obj.price = $("#price").val();
		obj.quantity = $("#quantity").val();

		ajaxSubmitProduct("http://localhost:8080/api/v1/products/" + product.id, "PUT", product);
	});

	$("#form-close").click(function() {
		$("#entry-form").hide(500);
		$(".is-invalid").removeClass("is-invalid");
		$(".invalid-feedback").text("");
		//$("#results-box").html("");
	});

	$(document).on("click", ".delete-button", function() {
		const deleteIdNumber = $(this).val();
		console.log(deleteIdNumber);

		$.ajax({
			url: "http://localhost:8080/api/v1/products/" + deleteIdNumber,
			type: 'DELETE',
			success: function(data) {
				console.log('delete response', data);
			},
			error: function(e) {
				console.log(e);
			}
		});
		$("#results-box").html("");
	});

	function ajaxSubmitProduct(url, type, dataObj) {
		$.ajax({
			url: url,
			type: type,
			contentType: 'application/json',
			data: JSON.stringify(dataObj),
			dataType: 'json',
			success: function(data) {
				console.log('response', data);
				$("#entry-form").hide(500);
				$("#results-box").html("");
			},
			error: function(e) {
				$(".is-invalid").removeClass("is-invalid");
				$(".invalid-feedback").text("");
				console.log(e);
				let response = e.responseJSON;
				if (!response && e.responseText) {
					try {
						response = JSON.parse(e.responseText);
					} catch (err) { }
				}

				if (e.status === 400) {
					if (response && response.errors) {
						for (const field in response.errors) {
							$("#error-" + field).text(response.errors[field]);
							$("#" + field).addClass("is-invalid");
						}
					} else if (response && response.message) {
						alert(messages["err.unhandledValidation"] + ": " + response.message);
					}
					else {
						alert(messages["err.unknownValidation"] + ", " + messages["err.checkConsole"]);
					}
				} else {
					alert(messages["err.unexpected"] + ", " + messages["err.checkConsole"]);
				}
			}
		});
	}
});