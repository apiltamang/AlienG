
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">
    <link rel="shortcut icon" href="../../docs-assets/ico/favicon.png">

    <title>AlienG2</title>

    <!-- Bootstrap core CSS -->
    <link href="css/bootstrap.css" rel="stylesheet">

    <!-- Custom styles for this template -->
    <link href="my-template.css" rel="stylesheet">

    <!-- Just for debugging purposes. Don't actually copy this line! -->
    <!--[if lt IE 9]><script src="../../docs-assets/js/ie8-responsive-file-warning.js"></script><![endif]-->

    <!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
      <script src="https://oss.maxcdn.com/libs/respond.js/1.3.0/respond.min.js"></script>
    <![endif]-->
  </head>

  <body>

    <div class="navbar navbar-inverse navbar-fixed-top" role="navigation">
      <div class="container">
        <div class="navbar-header">
          <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
            <span class="sr-only">Toggle navigation</span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
          </button>
          <a class="navbar-brand" href="#">AlienG2</a>
        </div>
        <div class="collapse navbar-collapse">
          <ul class="nav navbar-nav">
		  <%@ include file="NavigationPane.jsp" %>
          </ul>
        </div><!--/.nav-collapse -->
      </div>
    </div>

	<div class="container">
		<h3>
		 <%
	            String a=session.getAttribute("username").toString();
	            out.println("Hello  "+a);
		%>
		</h3></br>
		
		<h3>Please select the genome file which includes the genome sequences</h3>
		<form action="UploadFileProcess.jsp" method="post"
		                        enctype="multipart/form-data">
		<input type="file" name="file" size="50" />
		<br />
		<input type="submit" value="Upload File" />
		</form>
		</br></br>
		<form class="form-signin" action="results.jsp" method="post" >
	        <h4 class="form-results-heading">Are you looking for past orders?</h4>
	        <button class="btn btn-sm btn-danger btn-block"  type="submit">View Orders</button>
      	</form>
		<!--  A HREF="results.jsp">Click Here for results</A>-->
	</div>
<!-- /.container -->


    <!-- Bootstrap core JavaScript
    ================================================== -->
    <!-- Placed at the end of the document so the pages load faster -->
    <script src="https://code.jquery.com/jquery-1.10.2.min.js"></script>
    <script src="js/bootstrap.min.js"></script>
  </body>
</html>
