package sp.voice;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import com.iflytek.cloud.speech.RecognizerListener;
import com.iflytek.cloud.speech.RecognizerResult;
import com.iflytek.cloud.speech.SpeechConstant;
import com.iflytek.cloud.speech.SpeechError;
import com.iflytek.cloud.speech.SpeechRecognizer;
import com.iflytek.cloud.speech.SpeechSynthesizer;
import com.iflytek.cloud.speech.SynthesizeToUriListener;

/**
 * @author 作者: 如今我已·剑指天涯
 * @Description:读取字节数组的工具类。音频流听写
 *创建时间:2016年5月16日下午6:26:01
 */
 public class ReadBytesUtils {
	
	private static String accept  = null;
	
	private static MscTest mObject;

	private static StringBuffer mResult = new StringBuffer();
	
	/**
	 * 听写
	 * @param data 
	 */
	public static void Recognize(String data) {
		if (SpeechRecognizer.getRecognizer() == null)
			SpeechRecognizer.createRecognizer();
		RecognizePcmfileByte(data);
	}
	
	
	//在这死循环
	public  static void onLoop(String data) {
		Recognize(data);
	}
	
	/**
	 * 自动化测试注意要点 如果直接从音频文件识别，需要模拟真实的音速，防止音频队列的堵塞
	 * @param data 
	 */
	public static void RecognizePcmfileByte(String data) {
		// 1、读取音频文件
	//	FileInputStream in = null;
		byte[] voiceBuffer = data.getBytes();
		System.out.println(voiceBuffer.toString());
		System.out.println("进来了");
		try {
		//	in = new FileInputStream(file)
			//在这里读取音频文件或者字节数组，读取旭哥给我发过来的字节流			
			//voiceBuffer = new byte[in.available()];
		//	in.read(voiceBuffer);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
		}
		// 2、音频流听写
		if (0 == voiceBuffer.length) {
			mResult.append("no audio avaible!");
		} else {
			SpeechRecognizer recognizer = SpeechRecognizer.getRecognizer();
			recognizer.setParameter(SpeechConstant.DOMAIN, "iat");
			recognizer.setParameter(SpeechConstant.LANGUAGE, "zh_cn");
			recognizer.setParameter(SpeechConstant.AUDIO_SOURCE, "-1");
			//写音频流时，文件是应用层已有的，不必再保存
//			recognizer.setParameter(SpeechConstant.ASR_AUDIO_PATH,
//					"./iflytek.pcm");
			recognizer.setParameter( SpeechConstant.RESULT_TYPE, "plain" );
			recognizer.startListening(recListener);
			ArrayList<byte[]> buffers = splitBuffer(voiceBuffer,voiceBuffer.length, 4800);
			for (int i = 0; i < buffers.size(); i++) {
				// 每次写入msc数据4.8K,相当150ms录音数据
				recognizer.writeAudio(buffers.get(i), 0, buffers.get(i).length);
				try {
					Thread.sleep(150);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			recognizer.stopListening();
		}
	}

	/**
	 * 将字节缓冲区按照固定大小进行分割成数组
	 * 
	 * @param buffer
	 *            缓冲区
	 * @param length
	 *            缓冲区大小
	 * @param spsize
	 *            切割块大小
	 * @return
	 */
	public  static ArrayList<byte[]>  splitBuffer(byte[] buffer, int length, int spsize) {
		ArrayList<byte[]> array = new ArrayList<byte[]>();
		if (spsize <= 0 || length <= 0 || buffer == null
				|| buffer.length < length)
			return array;
		int size = 0;
		while (size < length) {
			int left = length - size;
			if (spsize < left) {
				byte[] sdata = new byte[spsize];
				System.arraycopy(buffer, size, sdata, 0, spsize);
				array.add(sdata);
				size += spsize;
			} else {
				byte[] sdata = new byte[left];
				System.arraycopy(buffer, size, sdata, 0, left);
				array.add(sdata);
				size += left;
			}
		}
		return array;
	}

	/**
	 * 听写监听器
	 */
	private static RecognizerListener recListener = new RecognizerListener() {

		public void onBeginOfSpeech() {
			DebugLog.Log( "onBeginOfSpeech enter" );
			DebugLog.Log("*************开始录音*************");
		}

		public void onEndOfSpeech() {
			DebugLog.Log( "onEndOfSpeech enter" );
		}

		public void onVolumeChanged(int volume) {
			DebugLog.Log( "onVolumeChanged enter" );
			if (volume > 0)
				DebugLog.Log("*************音量值:" + volume + "*************");

		}

		public void onResult(RecognizerResult result, boolean islast) {
			DebugLog.Log( "onResult enter" );
			mResult.append(result.getResultString());
			
			if( islast ){
				DebugLog.Log("识别结果为:" + mResult.toString());
				//在这里可以直接把文字mResult.toString() 传给小艾
				//直接调用远程方法，将数据发送过去，并接受小艾传过来的数据
			    accept  = SendData.sendData(mResult.toString());
				System.out.println("这是小艾传回来的数据是：：：："+ accept);
				mResult.delete(0, mResult.length());
				//onLoop();
			}
		}

		public void onError(SpeechError error) {
			
			DebugLog.Log("*************" + error.getErrorCode()
					+ "*************");
			//onLoop();
		}

		public void onEvent(int eventType, int arg1, int agr2, String msg) {
			DebugLog.Log( "onEvent enter" );
		}

	};

	// *************************************无声合成*************************************

	/**
	 * 合成
	 * @throws FileNotFoundException 
	 */
	private static void Synthesize() throws FileNotFoundException {
		SpeechSynthesizer speechSynthesizer = SpeechSynthesizer
				.createSynthesizer();
		// 设置发音人
		speechSynthesizer.setParameter(SpeechConstant.VOICE_NAME, "xiaoyan");
		// 设置语速，范围0~100
		speechSynthesizer.setParameter(SpeechConstant.SPEED, "50");
		// 设置语调，范围0~100
		speechSynthesizer.setParameter(SpeechConstant.PITCH, "50");
		// 设置音量，范围0~100
		speechSynthesizer.setParameter(SpeechConstant.VOLUME, "50");
		// 设置合成音频保存位置（可自定义保存位置），默认保存在“./iflytek.pcm”
		speechSynthesizer.synthesizeToUri(accept , "testVoice/1_test.pcm",	synthesizeToUriListener);
		//在这里将生成的文件转换成字节流给旭哥
		//FileInputStream in = new FileInputStream("test/tts_test.pcm");
		
	}

	/**
	 * 合成监听器
	 */
	static SynthesizeToUriListener synthesizeToUriListener = new SynthesizeToUriListener() {

		public void onBufferProgress(int progress) {
			DebugLog.Log("*************合成进度*************" + progress);

		}

		public void onSynthesizeCompleted(String uri, SpeechError error) {
			if (error == null) {
				DebugLog.Log("*************合成成功*************");
				DebugLog.Log("合成音频生成路径：" + uri);
			} else
				DebugLog.Log("*************" + error.getErrorCode()
						+ "*************");
			//onLoop();

		}

	};


	
	
	

}
