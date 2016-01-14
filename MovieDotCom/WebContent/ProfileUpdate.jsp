<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/notify/0.3.4/notify.min.js"></script>
	
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

	<!-- Page Content -->
	<div class="container hero-spacer">
		
		<div class="row">
			<div class="col-lg-12">
				<h1>User Profile</h1>
			</div>
		</div>
		<div class="row">
			<form class="form-horizontal">
				<div class="form-group">
					<label for="username" class="col-sm-2 control-label">Username:</label>
					<div class="col-sm-10">
						<input ng-model="username" type="text" class="form-control" 
							id="username" name="username" value="${user.getUserName()}">
					</div>
				</div>
				<div class="form-group">
					<label for="password" class="col-sm-2 control-label">Password:</label>
					<div class="col-sm-10">
						<input ng-model="password" type="password" class="form-control"
							id="password" name="password" value="" placeholder="${user.getPassword()}">
					</div>
				</div>
				<div class="form-group">
					<label for="firstname" class="col-sm-2 control-label">First
						Name:</label>
					<div class="col-sm-10">
						<input ng-model="firstname" type="text" class="form-control"
							id="firstname" name="firstname" placeholder="${user.getFirstName()}">
					</div>
				</div>
				<div class="form-group">
					<label for="lastname" class="col-sm-2 control-label">Last
						Name:</label>
					<div class="col-sm-10">
						<input ng-model="lastname" type="text" class="form-control"
							id="lastname" name="lastname" placeholder="${user.getLastName()}">
					</div>
				</div>
				<div class="form-group">
					<label for="email" class="col-sm-2 control-label">Email:</label>
					<div class="col-sm-10">
						<input ng-model="email" type="text" class="form-control"
							id="email" name="email" placeholder="${user.getEmail()}">
					</div>
				</div>
				<div class="form-group">
					<label for="profile" class="col-sm-2 control-label">Profile:</label>
					<div class="col-sm-10">
						<input ng-model="profile" type="text" class="form-control"
							id="profile" name="profile" placeholder="${user.getProfile()}">
					</div>
				</div>
				<div class="form-group">
					<div class="col-sm-offset-2 col-sm-10">
						<button 
							ng-click="update()" 
							class="btn btn-success"
							onclick="updateProfile(
								<c:out value="$('#userid').val()" />, 
								<c:out value="$('#password').val()" />,
								<c:out value="$('#firstname').val()" />,
								<c:out value="$('#lastname').val()" />,
								<c:out value="$('#email').val()" />,
								<c:out value="$('#profile').val()" />)">Update</button>
					</div>
			</form>
		</div>
		<!-- /.row -->
		
		<div>
			<h1>Favorite Movies</h1>
			<table class="table table-bordered">
				<thead>
					<tr>
      					<th>Title</th>
      					<th>Year</th>
      					<th>Description</th>
      					<th>Delete favorite movie</th>
    				</tr>
				</thead>
				<tbody>
					<c:forEach items="${favoriteMovies}" var="favoriteMovie">
						<tr>
							<td><c:out value="${favoriteMovie.getMovie().getTitle()}"/></td>
							<td><c:out value="${favoriteMovie.getMovie().getYear()}"/></td>
							<td><c:out value="${favoriteMovie.getMovie().getDescription()}"></c:out></td>
							<td><button 
								ng-click="update()" class="btn btn-default"  onclick="deleteFavoriteMovie(<c:out value="${favoriteMovie.getFavoriteMovieId()}" />)">Delete</button></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
		<div id="favoriteMovieAdd">
			<a href="moviesforrating?userid=<c:out value="${user.getUserId()}" />">Add Favorite Movie</a>
		</div>
		
		<div>
			<h1>Favorite Directors</h1>
			<table class="table table-bordered">
				<thead>
					<tr>
      					<th>First Name</th>
      					<th>Last Name</th>
      					<th>Delete favorite director</th>
    				</tr>
				</thead>
					<c:forEach items="${favoriteDirectors}" var="favoriteDirector">
						<tr>
							<td><c:out value="${favoriteDirector.getDirector().getFirstName()}"/></td>
							<td><c:out value="${favoriteDirector.getDirector().getLastName()}"/></td>
							<td><button 
								ng-click="update()" class="btn btn-default"  onclick="deleteFavoriteDirector(<c:out value="${favoriteDirector.getFavoriteDirectorId()}" />)">Delete</button></td>
						</tr>
					</c:forEach>
			</table>
		</div>
		<div id="favoriteDirectorAdd">
			<a href="favoritedirectoradd?userid=<c:out value="${user.getUserId()}" />">Add Favorite Director</a>
		</div>
		
		<div>
			<h1>Favorite Actors</h1>
			<table class="table table-bordered">
				<thead>
					<tr>
      					<th>First Name</th>
      					<th>Last Name</th>
      					<th>Delete favorite actor</th>
    				</tr>
				</thead>
				<tbody>
					<c:forEach items="${favoriteActors}" var="favoriteActor">
						<tr>
							<td><c:out value="${favoriteActor.getActor().getFirstName()}"/></td>
							<td><c:out value="${favoriteActor.getActor().getLastName()}"/></td>
							<td><button 
								ng-click="update()" class="btn btn-default"  onclick="deleteFavoriteActor(<c:out value="${favoriteActor.getFavoriteActorId()}" />)">Delete</button></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
		<div id="favoriteActorAdd">
			<a href="favoriteactoradd?userid=<c:out value="${user.getUserId()}" />">Add Favorite Actor</a>
		</div>
		
		<div>
			<h1>Favorite Movie Genres</h1>
			<table class="table table-bordered">
				<thead>
					<tr>
      					<th>Genre Type</th>
      					<th>Delete favorite movie genre</th>
    				</tr>
				</thead>
				<tbody>
					<c:forEach items="${favoriteGenres}" var="favoriteGenre">
						<tr>
							<td><c:out value="${favoriteGenre.getGenre().getGenreType().toString()}"/></td>
							<td><button 
								ng-click="update()" class="btn btn-default"  onclick="deleteFavoriteGenre(<c:out value="${favoriteGenre.getFavoriteGenreId()}" />)">Delete</button>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
		<div id="favoriteGenreAdd">
			<a href="favoritegenreadd?userid=<c:out value="${user.getUserId()}" />">Add Favorite Movie Genre</a>
		</div>
		
		<hr>
		<!-- Footer -->
		<footer>
			<div class="row">
				<div class="col-lg-12">
					<p>Copyright &copy; MovieDotCom 2015</p>
				</div>
			</div>
		</footer>

	</div>
	<!-- /.container -->

	<!-- Script to Activate the Carousel -->
	<script>
		$('.carousel').carousel({
			interval : 5000
		//changes the speed
		})
		
		function updateProfile(userid, password, firstname, lastname, email, profile) {
			$.ajax({
		        type: "post",
		        url: "profileupdate",
		        data: {"userid":userid, "password":password, "firstname":firstname,
		        	"lastname": lastname, "email":email, "profile": profile},
		        success: function(msg) {
		        	$.notify("User profile updated", "success");
		        	location.reload();
		        },
		        error:function (xhr, ajaxOptions, thrownError){
		            alert(xhr.status);
		            alert(thrownError);
		        }
		    });
		}
		
		function deleteFavoriteMovie(favoriteMovieId) {
			$.ajax({
		        type: "post",
		        url: "favoritemoviedelete",
		        data: {"favoriteMovieId":favoriteMovieId},
		        success: function(msg) {
		        	$.notify("Favorite Movie Deleted", "success");
		        	location.reload();
		        },
		        error:function (xhr, ajaxOptions, thrownError){
		            alert(xhr.status);
		            alert(thrownError);
		        }
		    });
		}
		
		function deleteFavoriteDirector(favoriteDirectorId) {
			$.ajax({
		        type: "post",
		        url: "favoritedirectordelete",
		        data: {"favoriteDirectorId":favoriteDirectorId},
		        success: function(msg) {
		        	$.notify("Favorite Director Deleted", "success");
		        	location.reload();
		        },
		        error:function (xhr, ajaxOptions, thrownError){
		            alert(xhr.status);
		            alert(thrownError);
		        }
		    });
		}
		
		function deleteFavoriteActor(favoriteActorId) {
			$.ajax({
		        type: "post",
		        url: "favoriteactordelete",
		        data: {"favoriteActorId":favoriteActorId},
		        success: function(msg) {
		        	$.notify("Favorite Actor Deleted", "success");
		        	location.reload();
		        },
		        error:function (xhr, ajaxOptions, thrownError){
		            alert(xhr.status);
		            alert(thrownError);
		        }
		    });
		}
		
		function deleteFavoriteGenre(favoriteGenreId) {
			$.ajax({
		        type: "post",
		        url: "favoritegenredelete",
		        data: {"favoriteGenreId":favoriteGenreId},
		        success: function(msg) {
		        	$.notify("Favorite Genre Deleted", "success");
		        	location.reload();
		        },
		        error:function (xhr, ajaxOptions, thrownError){
		            alert(xhr.status);
		            alert(thrownError);
		        }
		    });
		}
	</script>

</body>
</html>