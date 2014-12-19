package filehandler;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.*;

 public class FileHandler {
	public static File returnFileHandle (String dirPath, String fileName)throws IOException, Exception{
		//This method returns a file Object so that
		//it can be used to write data in.
		//Checks in the [curr_dir]/dir/file exists.
		String filePath=dirPath+"\\"+fileName;
		
		//ready directory 'dir' in current working directory
		readyDirectory(dirPath);
		
		//create the file once the directory is made		
		File file=new File(filePath);
		try{
			if(!file.exists()){
				file.createNewFile();
			}else{
				; //file already exists. Content to be appended
			}
			return file;
		}catch(Exception e){
			//some error encountered while creating new file
			throw new IOException("Error creating file.");
		}finally{
			; //
		}
	}//end method returnFileHandler
	
	//this readies the directory/ies
	public static int readyDirectory(String dirName)throws Exception
	{
		try
		{
			File dirPath=new File(dirName);
			if(!dirPath.exists()){
				System.out.printf("Making new Dir: %s\n",dirPath.toString());
				dirPath.mkdirs();
				return 1;
			}else{
				System.out.printf("Already exists Dir: %s\n",dirPath.toString());
				return 0;
			}//end check/construct 'dir'
		}
		catch(Exception e)
		{
			throw new Exception("Error readying directory. \n Exception Message: \n"+e.getMessage()+"\n");
		}

	}

	//this copies a file from the source to the destination
	public static boolean copyFile(String srcDir, String dstDir, String fileName) throws Exception {

    	String dest=dstDir+"\\"+fileName;
    	File destFile=new File(dest);

    	String src =srcDir+"\\"+fileName;
    	File sourceFile=new File(src);
    	
	    FileChannel source = null;
	    FileChannel destination = null;
	   
	    boolean success=true;
	    
	    try {

	    	if(!destFile.exists()) {
	    		destFile.createNewFile();
	    	}

	    	source = new FileInputStream(sourceFile).getChannel();
	    	destination = new FileOutputStream(destFile).getChannel();
	    	destination.transferFrom(source, 0, source.size());
	    	
	    	
	    	
	    }
	    catch(Exception e)
	    {
	    	success=true;
	    	
	    	throw new Exception("Error copying file..."+fileName+
	    			" from \n "+srcDir+" ---> "+dstDir+".\n Exception Message: \n"+e.getMessage()+"\n");
	    }
	    
	    finally {
	    	if(source != null) {
	    		source.close();
	    	}
	    	if(destination != null) {
	    		destination.close();
	    	}
	    }
	    
	    return success;
	    
	}
	
	//this moves a file from the source to the destination
	public static boolean moveFile(String srcDir, String dstDir, String fileName) throws Exception {
		File sourceDir=new File(srcDir);
    	String src =srcDir+"\\"+fileName;
    	File sourceFile=new File(src);
    	
    	//call copy file to do actual copy
    	if(!copyFile(srcDir,dstDir,fileName))
    		return false;
    	
    	//now delete the file at src
	    if(sourceFile.delete())
	    	return true; 
	    else 
	    	return false;
	}
	
	//this method copies content of one directory into another, provided that they are all files
	public static boolean copyAllFilesInDir(String srcDir,String dstDir) throws Exception
	{
		boolean success=true;
		//get list of files:
		File[] files=new File(srcDir).listFiles();
		
		for (File file: files)
		{
			if(file.isFile())
			{
				copyFile(srcDir,dstDir,file.getName());
				System.out.println("copying file... "+file.getName()+" from: "+srcDir+" to: "+dstDir);
			}
		}
		
		return success;
	}

	public static boolean deleteAllFilesInDir(String dir) 
	{
		//delete all files in the dir 
		boolean success=true;
		//get list of files:
		File[] files=new File(dir).listFiles();
		
		for (File file: files)
		{
			if(file.isFile())
			{
				if(!file.delete())
					success=false;
				System.out.println("Deleting all files in dir: "+dir);
			}
		}
		
		return success;
		
	}
}
