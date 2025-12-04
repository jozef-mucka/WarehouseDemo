$(document).ready(function() {
	console.log("ready");

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
					outputHTML += "<li>Id: " + product.id + "</li>";
					outputHTML += "<li>Product Number: " + product.productNumber + "</li>";
					outputHTML += "<li>Product Name: " + product.productName + "</li>";
					outputHTML += "<li>Price: " + product.price + "</li>";
					outputHTML += "<li>Quantity: " + product.quantity + "</li>";
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
		$("#form-title").text('Add product');
		$("#form-close").text('Cancel');
		$("#productid").val('');
		$("#productnumber").val('p-');
		$("#productname").val('');
		$("#productprice").val('');
		$("#productquantity").val('');
	});

	$("#form-add").click(function() {
		var obj = new Object();
		obj.productNumber = $("#productnumber").val();
		obj.productName = $("#productname").val();
		obj.price = $("#productprice").val();
		obj.quantity = $("#productquantity").val();

		var jsonString = JSON.stringify(obj);
		//console.log('json for update', jsonString);

		$.ajax({
			url: "http://localhost:8080/api/v1/products/",
			type: 'POST',
			contentType: 'application/json',
			data: jsonString,
			dataType: 'json',
			success: function(data) {
				console.log('update response', data);
			},
			error: function(e) {
				console.log(e);
			}
		});
		$("#entry-form").hide(500);
		$("#results-box").html("");
	});

	$(document).on("click", ".edit-button", function() {
		const editIdNumber = $(this).val();
		//console.log(editIdNumber);

		$.ajax({
			url: "http://localhost:8080/api/v1/products/" + editIdNumber,
			type: 'GET',
			success: function(result) {
				//console.log(result);
				$("#productid").val(result.id);
				$("#productnumber").val(result.productNumber);
				$("#productname").val(result.productName);
				$("#productprice").val(result.price);
				$("#productquantity").val(result.quantity);

				$("#entry-form").show(500);
				$("#form-add").hide();
				$("#form-ok").show();
				$("#form-title").text('Edit product');
				$("#form-close").text('Cancel edits');
			}
		})
	});

	$("#form-ok").click(function() {
		var obj = new Object();
		obj.id = $("#productid").val();
		obj.productNumber = $("#productnumber").val();
		obj.productName = $("#productname").val();
		obj.price = $("#productprice").val();
		obj.quantity = $("#productquantity").val();

		var jsonString = JSON.stringify(obj);
		//console.log('json for update', jsonString);

		$.ajax({
			url: "http://localhost:8080/api/v1/products/" + obj.id,
			type: 'PUT',
			contentType: 'application/json',
			data: jsonString,
			dataType: 'json',
			success: function(data) {
				console.log('update response', data);
			},
			error: function(e) {
				console.log(e);
			}
		});
		$("#entry-form").hide(500);
		$("#results-box").html("");
	});

	$("#form-close").click(function() {
		$("#entry-form").hide(500);
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
});