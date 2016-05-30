package Recoud;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.net.Socket;

public class Recoud {

	public static void main(String [] args) throws Exception
	{
	
			File directory = new File("");
			Runtime.getRuntime().exec("arecord -d 5 -D plughw:1,0 zhangxu.wav");
			Thread.sleep(5100);
			String s= directory.getAbsolutePath();
			File file = new File(s+"//zhangxu.wav");  
			
			
			
	        byte [] buf =new byte[1000];
	        int len=0;
	        Socket socket = new Socket("169.254.249.232",11122);//169.254.249.232
			OutputStream out =socket.getOutputStream();
	        FileInputStream fi = new FileInputStream(file);  
	        while((len=fi.read(buf))!=-1)
	        {
	         out.write(buf, 0, len);
	        }
	        socket.shutdownOutput(); 
	        
	        socket.close();
			System.out.println("发送完毕");

	}
}
