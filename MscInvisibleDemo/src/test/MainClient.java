package test;

import java.io.FileOutputStream;

import zx.rpc.support.RPC;

public class MainClient {
	public static void main(String[] args) throws Exception{
		Media m = RPC.getProxy(Media.class, "169.254.178.141", 20382);
		byte [] buffer = m.takePhotos();
		FileOutputStream file = new FileOutputStream("E://zx.jpg");
		
		file.write(buffer);
		file.flush();
		file.close();
		
	}
}
