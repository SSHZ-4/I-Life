package Recoud;

import java.net.DatagramPacket;
import java.net.DatagramSocket;

import sp.voice.MscTest;
import sp.voice.ReadBytesUtils;
import sp.voice.ok;

public class Server {
	public static void main(String [] args) throws Exception
	{
		  DatagramSocket ds= new DatagramSocket(9898);//设置了监听端口
		  byte [] buf = new byte[1024];
		  
		 // while(true)
		  {
			  DatagramPacket dp = new DatagramPacket(buf,buf.length);
			  System.out.println("正在监听");
			  ds.receive(dp);
			 
			  String ip =dp.getAddress().getHostAddress();
			  //接受传递的字节数组
			   byte[] data = dp.getData();
			  //调用我的程序，将字节数组读进去，转成文字发给小艾
			  if(data!=null){
				  //ReadBytesUtils.onLoop(data);
				  ok ok1 = new ok();
				  ok1.ok(data);
				  System.out.println(data);
			  }
			  else {
				System.out.println("data是空哒");
			  }
			  
			  System.out.println(ip+"--"+data+"--");
		  }
	}
}
