<html>
<head>
<title>Welcome!</title>
</head>
<body>
	Hello ${name}!!
	<p>Wanna post!!?</p>
	<form action="/tweet" method="POST">
		<dl>
			<dt>New Tweet:
			<dd>
				<input type="text" id="tweet" name="tweet" size="140"
					value="" />
		</dl>
		<input type="submit" value="Tweet!!">
	</form>
</body>

</html>
