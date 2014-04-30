package adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import finalsslalomfragments.SlalomTop10Fragment1;
import finalsslalomfragments.SlalomTop10Fragment2;

public class SlalomTop10FragmentPagerAdapter extends FragmentPagerAdapter {
	
	private SlalomTop10Fragment1 f1;
	private SlalomTop10Fragment2 f2;

	public SlalomTop10FragmentPagerAdapter(FragmentManager fm) {
		super(fm);
		f1 = new SlalomTop10Fragment1();
		f2 = new SlalomTop10Fragment2();
	}

	@Override
	public Fragment getItem(int pos) {
		switch(pos) {
			case 0:
				return f1;
			case 1:
				return f2;
			default:
				return null;
		}
	}
	
	@Override
	public CharSequence getPageTitle(int pos) {
		switch (pos) {
			case 0:
				return SlalomTop10Fragment1.TITLE;
			case 1:
				return SlalomTop10Fragment2.TITLE;
			default:
				return null;
		}
	}

	@Override
	public int getCount() {
		return 2;
	}

}
