<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<html>
<head>
	<title>Home</title>
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
	<script type="text/javascript">
	
	function insertMember(){
		$.ajax({
			url:"insertMember",
			type:"POST",
			cache:false,
			data:$("#form1").serialize(),
			async:false,
			success:function(data){
				console.log("success");
			},
			error:function(e){
				console.log("err : " + e);
			}
		});
	}
	</script>
</head>
<body>
<button id="insertMember" onclick="insertMember()">insertMember</button>
</body>
</html>