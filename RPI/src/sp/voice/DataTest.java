package sp.voice;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class DataTest {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		 FileInputStream fis = new  FileInputStream(new File("testVoice/test.pcm"));
		 byte [] voiceBuffer = new byte[fis.available()];
		 fis.read(voiceBuffer);
		 ok ok1 = new ok();
		 ok1.ok(voiceBuffer);
	
	}

}
