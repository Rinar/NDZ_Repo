package com.task.soundcatch;

import java.io.File;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class SecondScreen extends Activity {
	/** Called when the activity is first created. */
	//MediaRecorder recorder = new MediaRecorder();
	File sound;
	File sampleDir = Environment.getExternalStorageDirectory();
   StreamAudioRecord recorder = new StreamAudioRecord(null);
 	public void comparatorOfSound_1(File sound) {
		float[] trans = new float[(int) sound.length()];
		FastFourierTransform fft = new FastFourierTransform();
		float spectrum[] = fft.fftMag(trans);
		getTextOnScreen(String.valueOf(spectrum), R.id.textView1);
		
		  
	  }/*
	public void comparatorOfSound_2(File sound) {
		double[] trans = new double[(int) sound.length()];
		RealDoubleFFT fft = new RealDoubleFFT((int) sound.length());
		fft.ft(trans);
		getTextOnScreen((fft.toString()), R.id.textView1);
		
		  
	  }*/

	private void getTextOnScreen(String text, int id) {
		TextView textOnScreen = (TextView) findViewById(id);
		textOnScreen.setText(text);
	}

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.secondscreen);
		//recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
		//recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
		//recorder.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT);
		// recorder.setOutputFile("/sdcard/temp.3gp");

		final View recButton = findViewById(R.id.rec);
		final View stopButton = findViewById(R.id.stop);
		stopButton.setEnabled(false);

		recButton.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				try {
					stopButton.setEnabled(true);
					recButton.setEnabled(false);
					recorder.run(); // Recording is now started
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
				
				try{
				//comparatorOfSound_1(sound);
					}
				catch(Exception ex) {
					getTextOnScreen(
							"Can't comp, because " + ex.getMessage(),
							R.id.textView1);
				}
				//recorder.fin(); // Now the object cannot be reused
				//sound.delete();
			}
		});
	}

}
