package test;


import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.imageio.stream.FileImageInputStream;

public class OCR {
	public static void main(String[] args) throws Exception {
		String hostnameString = "123.206.86.234";
		Socket socket = new Socket(hostnameString,80);
		PrintWriter out = new PrintWriter(socket.getOutputStream(),true);
		BufferedReader bfrd = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		String line = null;
		StringBuilder sbBuilder = new StringBuilder();
		int[] data = getImageByte("F:\\zx.jpg");
		//sbBuilder.append("GET /OCRWS/ocr.asmx/readByte?");
		for (int i = 0; i < data.length-1; i++) {
			sbBuilder.append("b="+data[i]+"&");
		}
		sbBuilder.append("b="+data[data.length-1]);
		//sbBuilder.append(" HTTP/1.1");
		System.out.println(sbBuilder);

			out.println("POST /OCRWS/ocr.asmx/readByte HTTP/1.1");
			out.println("Host: "+hostnameString);
			out.println("Content-Type: application/x-www-form-urlencoded");
			out.println("Cache-Control: no-cache");
			out.println("User-Agent:Mozilla/4.0 (compatible; MSIE 5.0; Windows XP; DigExt");
			out.println("Content-Length: "+sbBuilder.length());
			out.println("");
			out.println(sbBuilder.toString());
			
			

//			out.println(sbBuilder);
//			out.println("Host: localhost");
//			out.println("User-Agent:Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/43.0.2357.130 Safari/537.36");
//			
//			out.println();
//			out.println();

		while((line = bfrd.readLine())!=null)
		{
			System.out.println(line);
		}
		socket.close();
		out.close();
	}
	public static int[] getImageByte(String path) throws Exception {
		File f = new File(path);
		InputStream is = new FileInputStream(f);
		byte[] b = new byte[(int)f.length()];
		is.read(b);
		is.close();
		int data[] = new int[b.length];
		for(int i=0;i<b.length;i++){
				data[i] = b[i] & 0xff;
		}
		return data;
//
//		for (int i = 0; i < data.length; i++) {
//			if (i % 408 == 0) {
//				System.out.println();
//			}
//			System.out.print(data[i]+"  ");
//			
//		}
//		System.out.println(b.length);
	}
	public static byte[] image2byte(String path){
	    byte[] data = null;
	    FileImageInputStream input = null;
	    try {
	      input = new FileImageInputStream(new File(path));
	      ByteArrayOutputStream output = new ByteArrayOutputStream();
	      byte[] buf = new byte[1024];
	      int numBytesRead = 0;
	      while ((numBytesRead = input.read(buf)) != -1) {
	      output.write(buf, 0, numBytesRead);
	      }
	      data = output.toByteArray();
	      output.close();
	      input.close();
	    }
	    catch (FileNotFoundException ex1) {
	      ex1.printStackTrace();
	    }
	    catch (IOException ex1) {
	      ex1.printStackTrace();
	    }
	    return data;
	  }
}

