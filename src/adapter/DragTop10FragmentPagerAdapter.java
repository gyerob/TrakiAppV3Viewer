package adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import finalsdragfragments.DragTop10Fragment1;
import finalsdragfragments.DragTop10Fragment2;

public class DragTop10FragmentPagerAdapter extends FragmentStatePagerAdapter {

	private DragTop10Fragment1 f1;
	private DragTop10Fragment2 f2;

	public DragTop10FragmentPagerAdapter(FragmentManager fm) {
		super(fm);
		f1 = new DragTop10Fragment1();
		f2 = new DragTop10Fragment2();
	}

	@Override
	public Fragment getItem(int pos) {
		switch (pos) {
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
			return DragTop10Fragment1.TITLE;
		case 1:
			return DragTop10Fragment2.TITLE;
		default:
			return null;
		}
	}

	@Override
	public int getCount() {
		return 2;
	}

}
