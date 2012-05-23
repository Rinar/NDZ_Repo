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

	/*
	 * public void comparatorOfSound(File file) {
	 * 
	 * if (String.valueOf("sound")== whistle) { Vibrator v = (Vibrator)
	 * getSystemService(Context.VIBRATOR_SERVICE); v.vibrate(300, 3);
	 * 
	 * } return;
	 * 
	 * }
	 */

	public void getTextOnScreen(String text, int id) {
		TextView textOnScreen = (TextView) findViewById(id);
		textOnScreen.setText(text);
	}

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.secondscreen);
		recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
		recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
		recorder.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT);
		recorder.setOutputFile("/sdcard/temp.3gp");
		final View recButton = findViewById(R.id.rec);
		final View stopButton = findViewById(R.id.stop);
		stopButton.setEnabled(false);
		recButton.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				try {
				stopButton.setEnabled(true);
				recButton.setEnabled(false);
							

				recorder.prepare(); // CAN NOT TO PREPARE!!!
				
				
				
				
					recorder.start(); // Recording is now started
					getTextOnScreen("I`m listning...", R.id.textView1);}
				 catch (Exception ex) {
					stopButton.setEnabled(false);
					recButton.setEnabled(true);
					getTextOnScreen("Something going wrong just now  " + ex.getMessage(), R.id.textView1);
				}
			}
		});
		stopButton.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				stopButton.setEnabled(false);
				recButton.setEnabled(true);
				try {
					recorder.stop();
				} catch (Exception ex) {
					getTextOnScreen("Something going wrong" + ex.getMessage(), R.id.textView1);
				}

				recorder.release(); // Now the object cannot be reused
				// comparatorOfSound();
			}
		});
	}

}
