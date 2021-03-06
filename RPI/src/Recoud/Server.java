package Recoud;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.SourceDataLine;

import Recoud.Recoud_m.Play;

public class Server {
	ByteArrayInputStream bais = null;
	AudioFormat af = null;
	AudioInputStream ais = null;
	SourceDataLine sd = null;
	ByteArrayOutputStream baos = null;
	public void play(byte [] audioData)
	{
		//灏哹aos涓殑鏁版嵁杞崲涓哄瓧鑺傛暟鎹�
		//杞崲涓鸿緭鍏ユ祦
		bais = new ByteArrayInputStream(audioData);
		af = new Recoud_m().getAudioFormat();
		ais = new AudioInputStream(bais, af, audioData.length/af.getFrameSize());
		
		try {
			DataLine.Info dataLineInfo = new DataLine.Info(SourceDataLine.class, af);
            sd = (SourceDataLine) AudioSystem.getLine(dataLineInfo);
            sd.open(af);
            sd.start();
            //鍒涘缓鎾斁杩涚▼
            Play py = new Play();
            Thread t2 = new Thread(py);
            t2.start();           
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				//鍏抽棴娴�
				if(ais != null)
				{
					ais.close();
				}
				if(bais != null)
				{
					bais.close();
				}
				if(baos != null)
				{
					baos.close();
				}
				
			} catch (Exception e) {		
				e.printStackTrace();
			}
		}
		
	}
	public static void main(String [] args) throws Exception
	{
		Server s = new Server();
		  DatagramSocket ds= new DatagramSocket(8999);//设置了监听端口
		  byte [] buf = new byte[1024*100];
		  
		  while(true)
		  {
			  DatagramPacket dp = new DatagramPacket(buf,buf.length);
			  System.out.println("正在监听");
			  ds.receive(dp);
			 
			  String ip =dp.getAddress().getHostAddress();
			  //String data = new String(dp.getData(),0,dp.getLength());
			  //int port = dp.getPort();
			  //s.play(dp.getData());
			  //System.out.println(ip+"--"+data+"--"+port);
		  }
	}
	class Play implements Runnable
	{
		//鎾斁baos涓殑鏁版嵁鍗冲彲
		public void run() {
			byte bts[] = new byte[10000];
			try {
				int cnt;
	            //璇诲彇鏁版嵁鍒扮紦瀛樻暟鎹�
	            while ((cnt = ais.read(bts, 0, bts.length)) != -1) 
	            {
	                if (cnt > 0) 
	                {
	                    //鍐欏叆缂撳瓨鏁版嵁
	                    //灏嗛煶棰戞暟鎹啓鍏ュ埌娣烽鍣�
	                    sd.write(bts, 0, cnt);
	                }
	            }
	           
			} catch (Exception e) {
				e.printStackTrace();
			}finally{
				 sd.drain();
		         sd.close();
			}
			
			
		}		
	}
}
