package test;

import java.io.File;
import java.io.FileInputStream;
import java.util.Arrays;

public class MeidaImpl implements Media {
	public byte [] takePhotos() throws Exception
	{
		File directory = new File("");
		Runtime.getRuntime().exec("raspistill -o "+"zhangxu.jpg"+" -t 1");
		String s= directory.getAbsolutePath();
		File file = new File(s+"//zhangxu.jpg");  
        long fileSize = file.length();   
        FileInputStream fi = new FileInputStream(file);  
        byte[] buffer = new byte[(int) fileSize];  
        fi.read(buffer);
        fi.close();  
        return buffer;  
	}
}
