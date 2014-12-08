<%@ page import="java.io.*,java.util.*, javax.servlet.*" %>
<%@ page import="javax.servlet.http.*" %>
<%@ page import="org.apache.commons.fileupload.*" %>
<%@ page import="org.apache.commons.fileupload.disk.*" %>
<%@ page import="org.apache.commons.fileupload.servlet.*" %>
<%@ page import="org.apache.commons.io.output.*" %>
<%@ page import="filehandler.FileHandler" %>
<%@ page import="ExtContentPath.UnivPathString" %>

<!DOCTYPE html>
<html>
<head>
	<title>Configuration</title>
	 <link href="css/bootstrap.css" rel="stylesheet">

    <!-- Custom styles for this template -->
    <link href="my-template.css" rel="stylesheet">
</head>
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

<body>
	<div class="container">

<%
   File file ;
   int maxFileSize = 10000000 * 1024;
   int maxMemSize = 10000000 * 1024;
   ServletContext context = pageContext.getServletContext();
   
   
   String username=session.getAttribute("username").toString();
   String userFolder=UnivPathString.rootPath+username;;
   
   //ready directory so user's uploaded file goes into this directory
   FileHandler.readyDirectory(userFolder) ;
   context.setAttribute("file-upload", userFolder);
   String filePath = context.getAttribute("file-upload").toString();
 
  
   System.out.println("filePath: " + filePath);
   // Verify the content type
   String contentType = request.getContentType();
   if ((contentType.indexOf("multipart/form-data") >= 0)) {

      DiskFileItemFactory factory = new DiskFileItemFactory();
      // maximum size that will be stored in memory
      factory.setSizeThreshold(maxMemSize);
      // Location to save data that is larger than maxMemSize.
      factory.setRepository(new File("c:\\temp"));

      // Create a new file upload handler
      ServletFileUpload upload = new ServletFileUpload(factory);
      // maximum file size to be uploaded.
      upload.setSizeMax( maxFileSize );
      try{ 
         // Parse the request to get file items.
         List fileItems = upload.parseRequest(request);

         // Process the uploaded file items
         Iterator i = fileItems.iterator();


         String fileName = null;
         String filep = null;
         while ( i.hasNext () ) 
         {
            FileItem fi = (FileItem)i.next();
            if ( !fi.isFormField () )	
            {
            // Get the uploaded file parameters
            String fieldName = fi.getFieldName();
            fileName = fi.getName();
            
            //Set the name of file user uploaded as a session variable
           session.setAttribute("userUploadedFile", fileName);

            boolean isInMemory = fi.isInMemory();
            long sizeInBytes = fi.getSize();
            // Write the file
            if( fileName.lastIndexOf("\\") >= 0 ){
            //	System.out.println(filePath);
            //	System.out.println(fileName.substring( fileName.lastIndexOf("\\")));
            	filep = filePath + "\\"+
                        fileName.substring( fileName.lastIndexOf("\\") + 1);
            	
            	file = new File( filep) ;
            	System.out.println("CONDITION True: file: "+file.toString());
            }else{
            	filep = filePath + "\\"+
                        fileName.substring(fileName.lastIndexOf("\\")+1);
            file = new File(filep ) ;
            System.out.println("CONDITION False: file: "+file.toString());
            }
            fi.write( file ) ;
            //out.println("Uploaded Filename: " + filePath + fileName + "<br>");
            }
         }
         //System.out.println(filep); 
%>
        <form METHOD=POST ACTION="ConfirmationPage.jsp">
		<table>
       	<tr><td> AlienG </td></tr>
       	
        <tr><td></td><td><input type="hidden"  name="alienG_infile" value="<%=filep %>" /></td></tr>
        <tr><td>EvalueMax</td><td><input type="text"  value ="1e-5" name="alienG_EvalueMax"/></td></tr>
        <tr><td>Coverage</td><td><input type="text" value="0.01" name="alienG_Coverage"/></td></tr>
        <tr><td>ScoreMin</td><td><input type="text" value="50" name="alienG_ScoreMin"/></td></tr>    
		<tr><td>AIEvalue</td><td><input type="text" value="0" name="alienG_AIEvalue"/></td></tr>
		<tr><td>Ratio Score</td><td><input type="texts" value="0.9" name="alienG_ratioScore"/></td></tr>
		<tr><td>Blast Parameters</td><td><input type="text" value="-p blastp -e 1e-5 -b 1000 -m 7" name="alienG_blastparam"/></td></tr>   
		<tr><td>Group 1</td><td><input type="text" value="Bacteria 'OR' Archaea 'OR'  Alveolata 'OR' Cryptophyta 'OR' Euglenida 'OR' Haptophyceae 'OR' Rhizaria 'OR' stramenopiles 'OR' Viridiplantae 'OR' Glaucocystophyceae 'OR' Rhodophyta 'OR' viruses" name="alienG_group1" SIZE = 40/></td></tr>
        <tr><td>Group 2</td><td><input type="text" value="*" name="alienG_group2" SIZE = 40/></td></tr>
		<tr><td>Exclusions</td><td><input type="text" value="Fungi;contaminant;other sequences;crystal structure;Ternary Complex;|pdb|;artificial sequences;synthetic construct;viruses;plasmids;environmental samples;uncultured"name="alienG_exclusion" SIZE = 40/></td></tr>
		<tr><td><INPUT TYPE=SUBMIT></td></tr>
		</table>
		</form>
         
<% 
         
      }catch(Exception ex) {
         System.out.println(ex);
      }
   }
   if ((contentType.indexOf("multipart/form-data") < 0)) {
%>
      <p>No file uploaded</p>
<% 

   }
%>
</div>
</body>
</html>

