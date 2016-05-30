package sp.voice;

public class SendMessages {
		
	public  static void SendMessage(String text) throws InterruptedException{
		String words = text;
		String prewords = text.replace("发消息","");
		String finalWords = prewords.replace("给", "").trim();
		String word = finalWords.substring(0, finalWords.length()-1);
		System.out.println(word);
		SpeakUtils.speak(word + "连接成功，请说话");
		
		//jdbc连接数据库，读取放回的结果
		
		System.out.println(word+"连接成功，请说话");
		
	}
	
	
	
	
}
