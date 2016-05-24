package sp.voice;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class DataTest {
	public static void read(String FileName) throws IOException {
		// TODO Auto-generated method stub
		 FileInputStream fis = new  FileInputStream(new File(FileName));
		 byte [] voiceBuffer = new byte[fis.available()];
		 fis.read(voiceBuffer);
		 ok ok1 = new ok();
		 ok1.ok(voiceBuffer);
	}

}
