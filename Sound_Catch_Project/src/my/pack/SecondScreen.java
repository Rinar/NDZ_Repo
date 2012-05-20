package my.pack;

import java.io.File;
import java.io.IOException;

import android.app.Activity;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class SecondScreen extends Activity {
	/** Called when the activity is first created. */
	MediaRecorder recorder = new MediaRecorder();
	File sound;

	
	 /*public void comparatorOfSound(File file) {
	 
	 if (String.valueOf("sound")== whistle) { Vibrator v = (Vibrator)
	 getSystemService(Context.VIBRATOR_SERVICE); v.vibrate(300, 3);
	 
	 } return;
	 
	 }*/
	

	public void getTextOnScreen(String text) {
		TextView textOnScreen = (TextView) findViewById(R.id.textView1);
		textOnScreen.setText(text);
	}

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.secondscreen);
		final View recButton = findViewById(R.id.rec);
		final View stopButton = findViewById(R.id.stop);
		stopButton.setEnabled(false);
		recButton.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				stopButton.setEnabled(true);
				recButton.setEnabled(false);
				try {
					recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
					recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
					recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
					if (sound == null) {
						File sampleDir = Environment
								.getExternalStorageDirectory();

						try {
							sound = File.createTempFile("temp", ".3gp",
									sampleDir);
						} catch (IOException e) {
							return;
						}
					}
					recorder.setOutputFile(sound.getAbsolutePath());

					recorder.prepare();
					recorder.start(); // Recording is now started
				} catch (Exception ex) {
					stopButton.setEnabled(false);
					recButton.setEnabled(true);
					getTextOnScreen("Something going wrong");
				}
			}
		});
		stopButton.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				stopButton.setEnabled(false);
				recButton.setEnabled(true);
				recorder.stop();
				
				recorder.release(); // Now the object cannot be reused
				//comparatorOfSound();
			}
		});
	}

	
}
