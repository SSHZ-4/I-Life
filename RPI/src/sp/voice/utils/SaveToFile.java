package sp.voice.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import com.iflytek.cloud.speech.DataUploader;
import com.iflytek.cloud.speech.LexiconListener;
import com.iflytek.cloud.speech.RecognizerListener;
import com.iflytek.cloud.speech.RecognizerResult;
import com.iflytek.cloud.speech.Setting;
import com.iflytek.cloud.speech.SpeechConstant;
import com.iflytek.cloud.speech.SpeechError;
import com.iflytek.cloud.speech.SpeechListener;
import com.iflytek.cloud.speech.SpeechRecognizer;
import com.iflytek.cloud.speech.SpeechSynthesizer;
import com.iflytek.cloud.speech.SpeechUtility;
import com.iflytek.cloud.speech.SynthesizeToUriListener;
import com.iflytek.cloud.speech.UserWords;

public class SaveToFile {

	private static final String APPID = "5731baf8";

	private static SaveToFile mObject;

	private static StringBuffer mResult = new StringBuffer();

	private static SaveToFile getMscObj() {
		if (mObject == null)
			mObject = new SaveToFile();
		return mObject;
	}

	public  void onLoop(String words) {
		//在应用发布版本中，请勿显示日志，详情见此函数说明。
				Setting.setShowLog( true );
				SpeechUtility.createUtility("appid=" + APPID);
				Synthesize(words);
	}


	// *************************************无声合成*************************************

	/**
	 * 合成
	 */
	private void Synthesize(String words) {
		SpeechSynthesizer speechSynthesizer = SpeechSynthesizer
				.createSynthesizer();
		// 设置发音人
		speechSynthesizer.setParameter(SpeechConstant.VOICE_NAME, "vinn");
		// 设置语速，范围0~100
		speechSynthesizer.setParameter(SpeechConstant.SPEED, "50");
		// 设置语调，范围0~100
		speechSynthesizer.setParameter(SpeechConstant.PITCH, "50");
		// 设置音量，范围0~100
		speechSynthesizer.setParameter(SpeechConstant.VOLUME, "50");
		// 设置合成音频保存位置（可自定义保存位置），默认保存在“./iflytek.pcm”
		speechSynthesizer.synthesizeToUri(words, "D:/sunpeng.pcm",synthesizeToUriListener);
		
	
	}

	/**
	 * 合成监听器
	 */
	SynthesizeToUriListener synthesizeToUriListener = new SynthesizeToUriListener() {

		public void onBufferProgress(int progress) {
			DebugLog.Log("*************合成进度*************" + progress);

		}

		public void onSynthesizeCompleted(String uri, SpeechError error) {
			if (error == null) {
				DebugLog.Log("*************合成成功*************");
				DebugLog.Log("合成音频生成路径：" + uri);
				ChangeToUtils.changeToWav();
			} else
				DebugLog.Log("*************" + error.getErrorCode()
						+ "*************");
		}

	};
}
