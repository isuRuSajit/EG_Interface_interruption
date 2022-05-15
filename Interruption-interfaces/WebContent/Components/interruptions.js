$(document).ready(function() {
	$("#alertSuccess").hide();
	$("#alertError").hide();
});
// SAVE ============================================
$(document).on("click", "#btnSave", function(event) {
	// Clear alerts---------------------
	$("#alertSuccess").text("");
	$("#alertSuccess").hide();
	$("#alertError").text("");
	$("#alertError").hide();
	// Form validation-------------------
	var status = validateInterruptionForm();
	if (status != true) {
		$("#alertError").text(status);
		$("#alertError").show();
		return;
	}
	// If valid------------------------
	var type = ($("#hidInterruptionIDSave").val() == "") ? "POST" : "PUT";
	$.ajax(
		{
			url: "InterruptionsAPI",
			type: type,
			data: $("#formInterruption").serialize(),
			dataType: "text",
			complete: function(response, status) {
				onInterruptionSaveComplete(response.responseText, status);
			}
		});
});

function onInterruptionSaveComplete(response, status) {
	if (status == "success") {
		var resultSet = JSON.parse(response);
		if (resultSet.status.trim() == "success") {

			$("#alertSuccess").text("Successfully saved.");
			$("#alertSuccess").show();
			$("#divInterruptionsGrid").html(resultSet.data);

		} else if (resultSet.status.trim() == "error") {

			$("#alertError").text(resultSet.data);
			$("#alertError").show();
		}
	} else if (status == "error") {
		$("#alertError").text("Error while saving.");
		$("#alertError").show();
	} else {
		$("#alertError").text("Unknown error while saving..");
		$("#alertError").show();
	}
	$("#hidInterruptionIDSave").val("");
	$("#formInterruption")[0].reset();
}



// UPDATE==========================================
$(document).on("click", ".btnUpdate", function(event) {
	$("#hidInterruptionIDSave").val($(this).closest("tr").find('#hidInterruptionIDUpdate').val());
	$("#intAriaCode").val($(this).closest("tr").find('td:eq(0)').text());
	$("#intAriaName").val($(this).closest("tr").find('td:eq(1)').text());
	$("#intDate").val($(this).closest("tr").find('td:eq(2)').text());
	$("#intTime").val($(this).closest("tr").find('td:eq(3)').text());
});



$(document).on("click", ".btnRemove", function(event) {
	$.ajax(
		{
			url: "InterruptionsAPI",
			type: "DELETE",
			data: "intID=" + $(this).data("intid"),
			dataType: "text",
			complete: function(response, status) {
				onInterruptionDeleteComplete(response.responseText, status);
			}
		});
});

function onInterruptionDeleteComplete(response, status) {
	if (status == "success") {
		var resultSet = JSON.parse(response);
		if (resultSet.status.trim() == "success") {
			$("#alertSuccess").text("Successfully deleted.");
			$("#alertSuccess").show();
			$("#divInterruptionsGrid").html(resultSet.data);
		} else if (resultSet.status.trim() == "error") {
			$("#alertError").text(resultSet.data);
			$("#alertError").show();
		}
	} else if (status == "error") {
		$("#alertError").text("Error while deleting.");
		$("#alertError").show();
	} else {
		$("#alertError").text("Unknown error while deleting..");
		$("#alertError").show();
	}
}
// CLIENT-MODEL================================================================
function validateInterruptionForm() {
	// CODE
	if ($("#intAriaCode").val().trim() == "") {
		return "Insert Aria Code.";
	}
	// NAME
	if ($("#intAriaName").val().trim() == "") {
		return "Insert Aria Name.";
	}
	// Date-------------------------------
	if ($("#intDate").val().trim() == "") {
		return "Insert Date.";
	}
	// Time------------------------
	if ($("#intTime").val().trim() == "") {
		return "Insert Time.";
	}
	return true;
}
