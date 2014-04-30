package hu.gyerob.trakiapp;

import adapter.SlalomTop10FragmentPagerAdapter;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;

public class FinalsSlalomActivity extends FragmentActivity {

	private ViewPager pager;
	private SlalomTop10FragmentPagerAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.finals_slalom);

		pager = (ViewPager) findViewById(R.id.slalomViewPager);
		adapter = new SlalomTop10FragmentPagerAdapter(
				getSupportFragmentManager());
		pager.setAdapter(adapter);
	}
}
