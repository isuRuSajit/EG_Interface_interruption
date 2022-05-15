<%@page import="com.Interruption"%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>



<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Interruption Management</title>
<link rel="stylesheet" href="Views/bootstrap.min.css">
<script src="Components/jquery-3.2.1.min.js"></script>
<script src="Components/interruptions.js"></script>
</head>
<body>
	<div class="container">
		<div class="row">
			<div class="col-6">
				<h1>Interruption Management</h1>
				<form id="formInterruption" name="formInterruption">
					Aria Code: <input id="intAriaCode" name="intAriaCode" type="text"
						class="form-control form-control-sm"> <br> Aria Name:
					<input id="intAriaName" name="intAriaName" type="text"
						class="form-control form-control-sm"> <br>  Date
						: <input id="intDate" name="intDate" type="text"
						class="form-control form-control-sm"> <br> 
					Time : <input id="intTime" name="intTime" type="text"
						class="form-control form-control-sm"> <br> <input
						id="btnSave" name="btnSave" type="button" value="Save"
						class="btn btn-primary"> <input type="hidden"
						id="hidInterruptionIDSave" name="hidInterruptionIDSave" value="">
				</form>
				<div id="alertSuccess" class="alert alert-success"></div>
				<div id="alertError" class="alert alert-danger"></div>
				<br>
				<div id="divInterruptionsGrid">
					<%
					Interruption interruptionObj = new Interruption();
					out.print(interruptionObj.readInterruptions());
					%>
				</div>
			</div>
		</div>
	</div>
</body>
</html>