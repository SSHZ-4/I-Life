package sp.voice;


import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;

import sp.voice.utils.JsonParser;
import sp.xiaoai.Tureing;
import net.sf.json.JSONObject;


public class SendData {
	
	private static String result;

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	public static String sendData(String text) throws InterruptedException {
		//如果要解析json结果，请考本项目示例的 com.iflytek.util.JsonParser类
		
		//调用解析方法生成串给旭哥
		String stringText = JsonParser.parseIatResult(text);
		//System.out.println("解析json之后的数据字符串格式111111"+stringText);
		
		System.out.println(text);
		Tureing t = new Tureing();
		String ask = text;
		try {
			ask=new String(ask.getBytes("UTF-8"),"UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		 if ((ask.contains("发消息") || ask.contains("发语音")) && ask.contains("给")  ) {
				//此时走自己的程序
			 SendMessages.SendMessage(ask);
			 			 
	      }else{
		String s=t.ask(ask);
		System.out.println("这是返回来的的数据"+s);
		
		String result = s;
			HashMap<String, String> data = new HashMap<String, String>();  
	       // 将json字符串转换成jsonObject  
	      JSONObject jsonObject = JSONObject.fromObject((Object)s);  
	       Iterator it = jsonObject.keys();  
	       // 遍历jsonObject数据，添加到Map对象  
	       while (it.hasNext())  
	       {  
	           String key = String.valueOf(it.next());  
	           String value =  jsonObject.get(key).toString();  
	           data.put(key, value);
	       }  
	       
	       //第一种：普遍使用，二次取值
	       System.out.println("通过Map.keySet遍历key和value：");
	       for (String key : data.keySet()) {
	        System.out.println("key= "+ key + " and value= " + data.get(key));
	        if (key.equals("text")) {
				  result = data.get(key);
			}
	       }
	       System.out.println("最终结果：：："+result);
		
		SpeakUtils.speak(result);
		return result;
		
	}
		return result;
}
}
