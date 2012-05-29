package com.task.soundcatch;

import java.io.File;
import java.io.IOException;

import com.task.soundcatch.R;

import com.task.soundcatch.FastFourierTransform;

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
	File sampleDir = Environment.getExternalStorageDirectory();

	/*public void comparatorOfSound(float inputSound[]) {
		FastFourierTransform fft = new FastFourierTransform();
		float spectrum[] = fft.fftMag(inputSound);
		return;
	  
	  }*/

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
		// recorder.setOutputFile("/sdcard/temp.3gp");

		final View recButton = findViewById(R.id.rec);
		final View stopButton = findViewById(R.id.stop);
		stopButton.setEnabled(false);

		recButton.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				try {
					stopButton.setEnabled(true);
					recButton.setEnabled(false);
					if (sound == null) {

						try {
							sound = File.createTempFile("temp", ".3gp",
									sampleDir);
						} catch (IOException e) {
							getTextOnScreen("Can't create temp file, because "
									+ e.getMessage(), R.id.textView1);
							return;
						}
					}
					recorder.setOutputFile(sound.getAbsolutePath()); // TODO -
																		// find
																		// better
																		// solution

					recorder.prepare();
					recorder.start(); // Recording is now started
					getTextOnScreen("I`m listning...", R.id.textView1);
				} catch (Exception ex) {
					stopButton.setEnabled(false);
					recButton.setEnabled(true);
					getTextOnScreen(
							"Can't start rec, because " + ex.getMessage(),
							R.id.textView1);
				}
			}
		});

		stopButton.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				stopButton.setEnabled(false);
				recButton.setEnabled(true);
				try {
					recorder.stop();
					getTextOnScreen("Record has been stopped", R.id.textView1);
				} catch (Exception ex) {
					getTextOnScreen(
							"Can't stop rec, because " + ex.getMessage(),
							R.id.textView1);
				}
				recorder.release(); // Now the object cannot be reused
				//comparatorOfSound(SOMTHING); //TODO - develop, how create "float[]" from "File" 
				sound.delete();
			}
		});
	}

}
