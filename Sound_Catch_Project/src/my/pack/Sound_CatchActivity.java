package my.pack;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

public class Sound_CatchActivity extends Activity implements OnClickListener {
	/** Called when the activity is first created. */
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		View firstButton = findViewById(R.id.first_button);
		firstButton.setOnClickListener(this);

	}

	// function for "on click" event
	public void onClick(View view) {
		Intent intent = new Intent(this, SecondScreen.class);
		startActivity(intent);
	}
}