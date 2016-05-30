package sp.voice;
/**
 * @author sunpeng123
 * @Description : 语音合成的工具类
 */

import com.iflytek.cloud.speech.SpeechConstant;
import com.iflytek.cloud.speech.SpeechError;
import com.iflytek.cloud.speech.SpeechSynthesizer;
import com.iflytek.cloud.speech.SynthesizerListener;

class SpeakUtils{
	
	/*public static void main(String[] args) {
		speak("哈哈");
	}*/
	
	private static SpeechSynthesizer mTts;
	

	
    public static void speak(String words) {

		if (SpeechSynthesizer.getSynthesizer() == null)
		 	   SpeechSynthesizer.createSynthesizer();
		    mTts = SpeechSynthesizer.createSynthesizer();
		 // 初始化合成对象，合成语音
			
			mTts.setParameter(SpeechConstant.VOICE_NAME, "vinn");//设置发音人 
			mTts.setParameter(SpeechConstant.SPEED, "50");//设置语速 
			mTts.setParameter(SpeechConstant.VOLUME, "80");//设置音量，范围 0~100 
			mTts.setParameter(SpeechConstant.TTS_BUFFER_TIME, "1");//这是我心设置的参数，为了提高速度
			mTts.startSpeaking(words, new SynthesizerListener(){

				@Override
				public void onBufferProgress(int arg0, int arg1, int arg2,
						String arg3) {
				}

				@Override
				public void onCompleted(SpeechError arg0) {
				}

				@Override
				public void onSpeakBegin() {
					System.out.println("即将开始说话");
				}

				@Override
				public void onSpeakPaused() {
				}

				@Override
				public void onSpeakProgress(int arg0, int arg1, int arg2) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void onSpeakResumed() {
					// TODO Auto-generated method stub
					
				}
				
			}); 
	}
	
	
	
}