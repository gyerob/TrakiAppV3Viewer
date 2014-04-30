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
import data.Slalom;

public class SlalomAdapter extends BaseAdapter {
	ArrayList<Slalom> slaloms;

	public SlalomAdapter(final ArrayList<Slalom> Slaloms) {
		super();
		slaloms = Slaloms;
	}

	@Override
	public Slalom getItem(int position) {
		return slaloms.get(position);
	}

	@Override
	public int getCount() {
		return slaloms.size();
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Slalom slalom = getItem(position);
		
		LayoutInflater inflater = (LayoutInflater) parent.getContext()
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = inflater.inflate(R.layout.row_slalom, null);
		
		TextView name = (TextView) view.findViewById(R.id.row_slalomName);
		TextView number = (TextView) view.findViewById(R.id.row_slalomNumber);
		TextView ido = (TextView) view.findViewById(R.id.row_slalomIdo);
		TextView hiba = (TextView) view.findViewById(R.id.row_slalomHiba);
		TextView vido = (TextView) view.findViewById(R.id.row_slalomVido);
		
		name.setTextColor(Color.BLACK);
		number.setTextColor(Color.BLACK);
		ido.setTextColor(Color.BLACK);
		hiba.setTextColor(Color.BLACK);
		vido.setTextColor(Color.BLACK);

		name.setText(slalom.getName());
		number.setText(Integer.toString(slalom.getNumber()));
		ido.setText(slalom.getIdo());
		hiba.setText(Integer.toString(slalom.getHiba()));
		vido.setText(slalom.getVido());
		
		return view;
	}
}
