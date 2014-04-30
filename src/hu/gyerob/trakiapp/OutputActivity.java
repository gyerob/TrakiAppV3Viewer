package hu.gyerob.trakiapp;

import adapter.OutputFragmentPagerAdapter;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;

public class OutputActivity extends FragmentActivity {

	private ViewPager pager;
	private OutputFragmentPagerAdapter outputadapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_output);
		
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		
		pager = (ViewPager) findViewById(R.id.outputViewPager);
		outputadapter = new OutputFragmentPagerAdapter(
				getSupportFragmentManager());
		pager.setAdapter(outputadapter);
	}
}
