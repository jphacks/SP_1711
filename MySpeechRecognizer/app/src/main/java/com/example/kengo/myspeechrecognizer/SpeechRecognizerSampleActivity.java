package com.example.kengo.myspeechrecognizer;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.util.Log;
import android.widget.TextView;

import java.util.ArrayList;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class SpeechRecognizerSampleActivity extends Activity {
    private static final String TAG = "SR";
    private SpeechRecognizer recog;
    private Runnable readyRecognizeSpeech;
    private Handler handler = new Handler();
    static PackageManager pm;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recog = SpeechRecognizer.createSpeechRecognizer(this);
        recog.setRecognitionListener(new RecogListener(this));

        readyRecognizeSpeech = new Runnable() {
            @Override public void run() {
                startRecognizeSpeech();
            }
        };

        // listener登録
        //Button b = (Button)findViewById(R.id.start_recognize);
        /*b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startRecognizeSpeech();
            }
        });*/
        startRecognizeSpeech();
        pm = getPackageManager();

    }


    private void startRecognizeSpeech() {
        handler.removeCallbacks(readyRecognizeSpeech);

        Intent intent = RecognizerIntent.getVoiceDetailsIntent(getApplicationContext());
        //Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        recog.startListening(intent);

        ((TextView)findViewById(R.id.status)).setText("");
        ((TextView)findViewById(R.id.sub_status)).setText("");
        findViewById(R.id.start_recognize).setEnabled(false);
    }

    private static class RecogListener implements RecognitionListener {
        private SpeechRecognizerSampleActivity caller;
        private TextView status;
        private TextView subStatus;

        RecogListener(SpeechRecognizerSampleActivity a) {
            caller = a;
            status = (TextView)a.findViewById(R.id.status);
            subStatus = (TextView)a.findViewById(R.id.sub_status);
        }

        // 音声認識準備完了
        @Override
        public void onReadyForSpeech(Bundle params) {
            status.setText("ready for speech");
            Log.v(TAG,"ready for speech");
        }

        // 音声入力開始
        @Override
        public void onBeginningOfSpeech() {
            status.setText("beginning of speech");
            Log.v(TAG,"beginning of speech");
        }

        // 録音データのフィードバック用
        @Override
        public void onBufferReceived(byte[] buffer) {
            //status.setText("onBufferReceived");
            //Log.v(TAG,"onBufferReceived");
        }

        // 入力音声のdBが変化した
        @Override
        public void onRmsChanged(float rmsdB) {
            String s = String.format("recieve : % 2.2f[dB]", rmsdB);
            subStatus.setText(s);
            //Log.v(TAG,"recieve : " + rmsdB + "dB");
        }

        // 音声入力終了
        @Override
        public void onEndOfSpeech() {
            status.setText("end of speech");
            Log.v(TAG,"end of speech");
            caller.handler.postDelayed(caller.readyRecognizeSpeech, 500);
        }

        // ネットワークエラー又は、音声認識エラー
        @Override
        public void onError(int error) {
            status.setText("on error");
            Log.v(TAG,"on error");
            switch (error) {
                case SpeechRecognizer.ERROR_AUDIO:
                    // 音声データ保存失敗
                    subStatus.setText("ERROR_AUDIO");
                    break;
                case SpeechRecognizer.ERROR_CLIENT:
                    // Android端末内のエラー(その他)
                    subStatus.setText("ERROR_CLIENT");
                    break;
                case SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS:
                    // 権限無し
                    subStatus.setText("ERROR_INSUFFICIENT_PERMISSIONS");
                    break;
                case SpeechRecognizer.ERROR_NETWORK:
                    // ネットワークエラー(その他)
                    subStatus.setText("ERROR_NETWORK");
                    break;
                case SpeechRecognizer.ERROR_NETWORK_TIMEOUT:
                    // ネットワークタイムアウトエラー
                    subStatus.setText("ERROR_NETWORK_TIMEOUT");
                    break;
                case SpeechRecognizer.ERROR_NO_MATCH:
                    // 音声認識結果無し
                    subStatus.setText("ERROR_NO_MATCH");
                    caller.handler.postDelayed(caller.readyRecognizeSpeech,1000);
                    break;
                case SpeechRecognizer.ERROR_RECOGNIZER_BUSY:
                    // RecognitionServiceへ要求出せず
                    subStatus.setText("ERROR_RECOGNIZER_BUSY");
                    caller.handler.postDelayed(caller.readyRecognizeSpeech,1000);
                    break;
                case SpeechRecognizer.ERROR_SERVER:
                    // Server側からエラー通知
                    subStatus.setText("ERROR_SERVER");
                    break;
                case SpeechRecognizer.ERROR_SPEECH_TIMEOUT:
                    // 音声入力無し
                    subStatus.setText("ERROR_SPEECH_TIMEOUT");
                    caller.handler.postDelayed(caller.readyRecognizeSpeech,1000);
                    break;
                default:
            }
        }

        // イベント発生時に呼び出される
        @Override
        public void onEvent(int eventType, Bundle params) {
            status.setText("on event");
            Log.v(TAG,"on event");
        }

        // 部分的な認識結果が得られる場合に呼び出される
        @Override
        public void onPartialResults(Bundle partialResults) {
            status.setText("on partial results");
            Log.v(TAG,"on results");
        }

        // 認識結果
        //@Override
        public void onResults(Bundle data) {
            status.setText("on results");
            Log.v(TAG,"on results");

            ArrayList<String> results = data.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);

            TextView t = (TextView)caller.findViewById(R.id.result);
            t.setText("");
            for (String s : results) {
                t.append(s + "\n");
            }

            boolean end=false;
            for (String s : results) {
                if (s.contains("電車"))
                    end=true;
                if (s.contains("のった"))
                    end=true;
                if (s.contains("乗った"))
                    end=true;
            }
            if (end) {
                caller.findViewById(R.id.start_recognize).setEnabled(true);
                // ボタンがクリックされた時に呼び出されます
                Log.d("test","検知");
            }
            else
                caller.startRecognizeSpeech();
        }
    }
}
