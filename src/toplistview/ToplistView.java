package toplistview;

import hu.gyerob.trakiapp.R;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ToplistView extends LinearLayout {
	private String name;
	private int number;
	private int win;
	private int round;
	private int pid;

	private TextView tv;
	private CheckBox cb1;
	private CheckBox cb2;

	private IListViewUpdate listener;

	public interface IListViewUpdate {
		public void onRacerClick(ToplistView tv);
	}

	public ToplistView getToplistView() {
		return this;
	}

	public void setData(String name, int number, int win, int pid) {
		setName(name);
		setNumber(number);
		setWin(win);
		setPid(pid);
	}

	public ToplistView(Context context, AttributeSet attr) {
		super(context, attr);
		View.inflate(context, R.layout.toplistviewlayout, this);
		setWin(0);
		this.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (getWin() == 0) {
					cb1.setChecked(true);
					setWin(1);
				} else if (getWin() == 1) {
					cb2.setChecked(true);
					setWin(2);
				}
				listener.onRacerClick(getToplistView());
			}
		});
	}

	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();
		tv = (TextView) findViewById(R.id.toplisttext);
		cb1 = (CheckBox) findViewById(R.id.toplistcheck1);
		cb2 = (CheckBox) findViewById(R.id.toplistcheck2);
	}

	protected void clickable(boolean click) {
		cb1.setClickable(click);
		cb2.setClickable(click);
	}

	public void setclickable(boolean katt) {
		clickable(katt);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
		tv.setText(name);
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public int getWin() {
		return win;
	}

	public void setWin(int win) {
		this.win = win;
		if (win == 1)
			cb1.setChecked(true);
		else if (win == 2) {
			cb1.setChecked(true);
			cb2.setChecked(true);
		}
	}

	public int getRound() {
		return round;
	}

	public void setRound(int round) {
		this.round = round;
	}

	public IListViewUpdate getListener() {
		return listener;
	}

	public int getPid() {
		return pid;
	}

	public void setPid(int pid) {
		this.pid = pid;
	}
}
