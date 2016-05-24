package sp.voice.utils;

import java.io.File;
import java.io.IOException;



public class ChangeToUtils  {
	
	public static void changeToWav()  {
		Change myWavWriter = null;
		File myfile ;
		myfile = new File("D:/sunpeng.pcm");
		try {
			myWavWriter = new Change(myfile, 16000);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			myWavWriter.writeHeader();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			myWavWriter.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("转换完成");
	}
	
}
