package al.ali.blast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Zipper {
	
	public static void zip(String srcDir, String zipFile) {
		/*---------------------------------------------
		 * This method takes a directory given by
		 * srcDir 	(format: C:\\path\\to\\dir
		 * to 
		 * destFile (format: C:\\path\\to\\file.zip
		 * NOTE:
		 * I think the current implementation of Zipper
		 * doesn't recursively include sub-directories
		 * and/or their files in the archive. Make sure
		 * that is enabled so that the sub-directory,
		 * 'trees' will also be available in the zip.
		 *--------------------------------------------*/
		/* The example came up with these default
		 * values.
		 * String zipFile = "C:/archive.zip";
		 * String srcDir = "C:/foldertocompress";
		*/
		try {
			
			// create byte buffer
			byte[] buffer = new byte[1024];

			FileOutputStream fos = new FileOutputStream(zipFile);

			ZipOutputStream zos = new ZipOutputStream(fos);

			File dir = new File(srcDir);

			File[] files = dir.listFiles();

			for (int i = 0; i < files.length; i++) {
				
				System.out.println("Adding file: " + files[i].getName());

				FileInputStream fis = new FileInputStream(files[i]);

				// begin writing a new ZIP entry, positions the stream to the start of the entry data
				zos.putNextEntry(new ZipEntry(files[i].getName()));
				
				int length;

				while ((length = fis.read(buffer)) > 0) {
					zos.write(buffer, 0, length);
				}

				zos.closeEntry();

				// close the InputStream
				fis.close();
			}

			// close the ZipOutputStream
			zos.close();
			
		}
		catch (IOException ioe) {
			System.out.println("Error creating zip file" + ioe);
		}
		
	}

}