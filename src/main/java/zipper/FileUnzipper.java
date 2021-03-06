package zipper;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

public class FileUnzipper {


	public static void unzip2(File source, String out) {
		ZipInputStream 			zipInputStream 	= null;
		ZipEntry 				zipEntry		= null;
		File					file			= null;
		BufferedOutputStream 	outputStream	= null;

		try {
			zipInputStream = new ZipInputStream(new FileInputStream(source));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		while (true) {
			try {
				zipEntry = zipInputStream.getNextEntry();
			} catch (IOException e) {
				e.printStackTrace();
			}
			if (zipEntry == null) {
				break;
			}

			file = new File(zipEntry.getName());

			if (zipEntry.isDirectory()) {
				file.mkdir();
				continue;
			}

			try {
				outputStream = new BufferedOutputStream(new FileOutputStream(file));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}

			int location;
			byte[] buffer = new byte[1];

			try {
				while ((location = zipInputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, location);
                }
			} catch (IOException e) {
				e.printStackTrace();
			}


		}

	}

	public static void unzip(File source, String out) throws IOException {
	    
		try (ZipInputStream zipInputStream = 
				new ZipInputStream(new FileInputStream(source))) {

			ZipEntry entry = zipInputStream.getNextEntry();

			while (entry != null) {

				File file = new File(out, entry.getName());

	            if (entry.isDirectory()) {
	            		file.mkdirs();
	            		} 
	            else {
	            	File parent = file.getParentFile();

	            	if (!parent.exists()) {
	            				parent.mkdirs();
	            				}

	            	try (BufferedOutputStream bufferedOutputStream = 
	            			new BufferedOutputStream(new FileOutputStream(file))) {
	            		int test=Math.toIntExact(entry.getSize());
	            		byte[] buffer = new byte[1];
	            		if(test>-1){
	            			buffer = new byte[test];
	            		} 

	            		int location;

	            		while ((location = zipInputStream.read(buffer)) != -1) {
	            			bufferedOutputStream.write(buffer, 0, location);
	                    	}
	            	}
	            	
	            	}
	            entry = zipInputStream.getNextEntry();
	        	}
	    }
	}
	
	public static void main(String[] args) throws IOException {
		
		File file= new File("C:/Documents and Settings/janusz.JAN/workspace/FileHandler/21.zip");
		unzip(file,"C:/Documents and Settings/janusz.JAN/workspace/FileHandler/");
	}
}
