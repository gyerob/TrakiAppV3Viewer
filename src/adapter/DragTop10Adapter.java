package adapter;

import hu.gyerob.trakiapp.R;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import data.DragTop;

public class DragTop10Adapter extends BaseAdapter{

	ArrayList<DragTop> racers;

	public DragTop10Adapter(final ArrayList<DragTop> Racers) {
		super();
		racers = Racers;
	}

	@Override
	public int getCount() {
		return racers.size();
	}

	@Override
	public DragTop getItem(int position) {
		return racers.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		DragTop racer = racers.get(position);
		
		LayoutInflater inflater = (LayoutInflater) parent.getContext()
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = inflater.inflate(R.layout.row_top10_drag, null);
		
		TextView pos = (TextView) view.findViewById(R.id.row_dragtopPos);
		TextView name = (TextView) view.findViewById(R.id.row_dragtopName);
		TextView number = (TextView) view.findViewById(R.id.row_dragtopNumber);
		
		pos.setTextColor(Color.BLACK);
		name.setTextColor(Color.BLACK);
		number.setTextColor(Color.BLACK);
		
		pos.setText(Integer.toString(racer.getPid()));
		name.setText(racer.getName());
		number.setText(Integer.toString(racer.getNumber()));
		
		return view;
	}

}
