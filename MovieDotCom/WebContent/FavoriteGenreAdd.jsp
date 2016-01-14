<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Add Favorite Movie Genre</title>

<!-- Latest compiled and minified CSS -->
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
<!-- Optional theme -->
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap-theme.min.css">
<!-- jQuery -->
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/jquery/2.1.4/jquery.js"></script>
<!-- Latest compiled and minified JavaScript -->
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>

<!-- Custom CSS -->
<link href="css/half-slider.css" rel="stylesheet">
<link href="css/heroic-features.css" rel="stylesheet">
<link href="css/my-style.css" rel="stylesheet">

<!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--[if lt IE 9]>
            <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
            <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
        <![endif]-->

</head>

<body>
	<!-- Navigation -->
	<nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
		<div class="container">
			<!-- Brand and toggle get grouped for better mobile display -->
			<div class="navbar-header">
				<button type="button" class="navbar-toggle" data-toggle="collapse"
					data-target="#bs-example-navbar-collapse-1">
					<span class="sr-only">Toggle navigation</span> <span
						class="icon-bar"></span> <span class="icon-bar"></span> <span
						class="icon-bar"></span>
				</button>
				<a class="navbar-brand" href="#">MovieDotCom</a>
			</div>
			<!-- Collect the nav links, forms, and other content for toggling -->
			<div class="collapse navbar-collapse"
				id="bs-example-navbar-collapse-1">
				<ul class="nav navbar-nav">
					<li><a href="home">Home</a></li>
					<li><a href="popular">Popular Movies</a></li>
					<li><a href="moviesforrating">Customized recommendation</a></li>
					<li><a href="about.view.html">About</a></li>
				</ul>

				<div class="col-sm-3 col-md-3 pull-right">
					<form action="search" class="navbar-form" role="search">
						<div class="input-group">
							<input type="text" class="form-control" placeholder="Search"
								name="term" id="term">
							<div class="input-group-btn">
								<button class="btn btn-default" type="submit">
									<i class="glyphicon glyphicon-search"></i>
								</button>
							</div>
						</div>
					</form>
				</div>
			
				<ul class="nav navbar-nav pull-right">
					<% if(session.getAttribute("userid")!=null) { %>
					<li><a href="profileupdate">Profile</a></li>
					<li><a href="logout">Logout</a></li>
					<% } else  { %>
					<li><a href="login">Login</a></li>
					<li><a href="register">Register</a></li>
					<% } %>
				</ul>
			</div>
			<!-- /.navbar-collapse -->
		</div>
		<!-- /.container -->
	</nav>
	
	<div class="container hero-spacer">
		<div class="row">
			<div class="col-lg-12">
				<h1>${messages.title}</h1>
			</div>
		</div>
		<div class="row">
			<form class="form-horizontal" action="favoritegenreadd" method="post">
				<div class="form-group" <c:if test="${messages.disableSubmit}">style="display:none"</c:if>>
					<label for="userid" class="col-sm-2 control-label">UserId</label>
					<div class="col-sm-10" >
						<input id="userid" name="userid" value="${fn:escapeXml(param.userid)}">
					</div>
				</div>
				<div class="form-group">
					<label for="type" class="col-sm-2 control-label">Movie Genre</label>
					<div class="col-sm-10" >
					<select class="form-control" id="genreid" name="genreid">
						<option value=0>Please Choose</option>
						<option value=1>Action</option>
						<option value=2>Adult</option>
						<option value=3>Adventure</option>
						<option value=4>Animation</option>
						<option value=5>Comedy</option>
						<option value=6>Crime</option>
						<option value=7>Documentary</option>
						<option value=8>Drama</option>
						<option value=9>Family</option>
						<option value=10>Fantasy</option>
						<option value=11>Film-Noir</option>
						<option value=12>Horror</option>
						<option value=13>Music</option>
						<option value=14>Musical</option>
						<option value=15>Mystery</option>
						<option value=16>Romance</option>
						<option value=17>Sci-Fi</option>
						<option value=18>Short</option>
						<option value=19>Thriller</option>
						<option value=20>War</option>
						<option value=21>Western/option>
						<option value=22>Biography</option>
						<option value=23>History</option>
					</select>
					</div>
				</div>
				<div class="form-group">
					<div class="col-sm-offset-2 col-sm-10">
						<button ng-click="update()" class="btn btn-success">add</button>
					</div>
				</div>
			</form>
		</div>
	</div>

</body>
</html>