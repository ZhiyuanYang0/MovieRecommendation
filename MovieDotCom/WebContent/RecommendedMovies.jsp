<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">

<head>

<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">

<title>MovieDotCom</title>

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
				<li class="active"><a href="moviesforrating">Customized
						recommendation</a></li>
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
	<!-- /.container --> </nav>

	<!-- Page Content -->
	<div class="container hero-spacer">

		<div class="row">
			<div class="col-lg-12">
				<h1>Recommended Movies for you</h1>
			</div>
		</div>

		<!-- Page Features -->
		<div class="row text-center">
			<div class="row text-center">
				<div class="col-md-9 hero-feature">
					<c:forEach items="${movies}" var="m">
						<a href="movieinfo?movieid=<c:out value="${m.getMovieId()}" />&from=recommedation">
							<div class="col-md-3 col-sm-6 hero-feature">
								<div class="thumbnail" style="height: 500px; overflow: hidden;">
									<img height="500" width="800"
										src='<c:out value="${m.getImageURL()}" />' alt="starting">
									<div class="caption">
										<h3>
											<c:out value="${m.getTitle()}" />
										</h3>
										<p>
											<font color="blue">Year</font>:
											<c:out value="${m.getYear()}" />
										</p>
										<p>
											<font color="blue">Rating</font>:
											<c:out value="${m.getRating()}" />
										</p>
										<p>
											<font color="blue">Description</font>:
											<c:out value="${m.getDescription()}" />
										</p>
									</div>
								</div>
							</div>
						</a>
					</c:forEach>
				</div>
				<div class="col-md-3 col-sm-6 hero-feature">
					
				</div>
			</div>
			<!-- /.row -->
			<hr>
			<!-- Footer -->
			<footer>
			<div class="row">
				<div class="col-lg-12">
					<p>Copyright &copy; MovieDotCom 2015</p>
				</div>
			</div>
			<!-- /.row --> </footer>

		</div>
		<!-- /.container -->
</body>

</html>