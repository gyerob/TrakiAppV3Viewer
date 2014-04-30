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
import data.Drag;

public class DragAdapter extends BaseAdapter {
	ArrayList<Drag> drags;
	
	public DragAdapter(final ArrayList<Drag> Drags) {
		super();
		drags = Drags;
	}

	@Override
	public Drag getItem(int position) {
		return drags.get(position);
	}

	@Override
	public int getCount() {
		return drags.size();
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Drag drag = getItem(position);
		
		LayoutInflater inflater = (LayoutInflater) parent.getContext()
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = inflater.inflate(R.layout.row_drag, null);
		
		TextView name = (TextView) view.findViewById(R.id.row_dragName);
		TextView number = (TextView) view.findViewById(R.id.row_dragNumber);
		TextView ido1 = (TextView) view.findViewById(R.id.row_dragIdo1);
		TextView ido2 = (TextView) view.findViewById(R.id.row_dragIdo2);
		TextView lido = (TextView) view.findViewById(R.id.row_dragLido);
		
		name.setTextColor(Color.BLACK);
		number.setTextColor(Color.BLACK);
		ido1.setTextColor(Color.BLACK);
		ido2.setTextColor(Color.BLACK);
		lido.setTextColor(Color.BLACK);

		name.setText(drag.getName());
		number.setText(Integer.toString(drag.getNumber()));
		ido1.setText(drag.getIdo1());
		ido2.setText(drag.getIdo2());
		lido.setText(drag.getLegjobbIdo());
		
		return view;
	}
}
