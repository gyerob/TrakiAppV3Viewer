package hu.gyerob.trakiapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class FinalsActivity extends Activity {

	private Button drag;
	private Button slalom;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_finals);

		drag = (Button) findViewById(R.id.finalsDragbtn);
		slalom = (Button) findViewById(R.id.finalsSlalombtn);

		slalom.setOnClickListener(click);
		drag.setOnClickListener(click);
	}

	OnClickListener click = new OnClickListener() {

		@Override
		public void onClick(View v) {
			Intent i = null;

			switch (v.getId()) {
			case R.id.finalsDragbtn: {
				i = new Intent(getApplicationContext(),
						FinalsDragActivity.class);
				startActivity(i);
				break;
			}
			case R.id.finalsSlalombtn: {
				i = new Intent(getApplicationContext(),
						FinalsSlalomActivity.class);
				startActivity(i);
				break;
			}
			}
			
		}
	};
}
