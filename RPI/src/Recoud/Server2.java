package Recoud;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.SourceDataLine;

import sp.voice.MscTest;
import sp.voice.ok;

public class Server2 {
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
		
		  Server2 s = new Server2();
		  ServerSocket server = new ServerSocket(11114);
		  
		  byte []  b ;
		  while(true)
		  {
			  b = new byte[102400*2];
			  System.out.println("监听中");
			  Socket socket = server.accept();
			  System.out.println("收到请求");
			  InputStream in = socket.getInputStream();
			  in.read(b,0,b.length);
			  
			 ok ok1 = new ok();
			  ok1.ok(b);
			
//			  s.play(b);
		  }
		 
		  
		  
	}
	
	
	
	class Play implements Runnable
	{
		//鎾斁baos涓殑鏁版嵁鍗冲彲
		public void run() {
			byte bts[] = new byte[102400*2/3];
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
