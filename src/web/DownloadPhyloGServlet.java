package web;
 
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
 


import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
 
public class DownloadPhyloGServlet extends javax.servlet.http.HttpServlet implements
        javax.servlet.Servlet {
    static final long serialVersionUID = 1L;
    private static final int BUFSIZE = 4096;
    private String rootDirProjectPath;
    
    public void init() {        
        rootDirProjectPath = "C:\\Users\\tamanga13\\workspace\\Thesis\\";
    }
    
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        /*
         * Simple file download at project root (Successful)
         * String filePath=rootDirAbsPath+"Test_DownloadServlet.txt";
         */
    	//Get the name of the user using session data
    	HttpSession session=request.getSession();
    	String userFolderPath=String.format("%s\\%s\\Order-%s",rootDirProjectPath,
				session.getAttribute("username").toString() ,
				session.getAttribute("ordNum").toString()   );
    	
    	String userResultFilePath=userFolderPath+"\\blastresults.zip";
    	File file = new File(userResultFilePath);
        int length   = 0;
        ServletOutputStream outStream = response.getOutputStream();
        ServletContext context  = getServletConfig().getServletContext();
        String mimetype = context.getMimeType(userResultFilePath);
        
        // sets response content type
        if (mimetype == null) {
            mimetype = "application/octet-stream";
        }
        response.setContentType(mimetype);
        response.setContentLength((int)file.length());
        String fileName = (new File(userResultFilePath)).getName();
        
        // sets HTTP header
        response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
        
        byte[] byteBuffer = new byte[BUFSIZE];
        DataInputStream in = new DataInputStream(new FileInputStream(file));
        
        // reads the file's bytes and writes them to the response stream
        while ((in != null) && ((length = in.read(byteBuffer)) != -1))
        {
            outStream.write(byteBuffer,0,length);
        }
        
        in.close();
        outStream.close();
    }
}