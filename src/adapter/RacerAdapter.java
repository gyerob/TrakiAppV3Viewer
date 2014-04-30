package adapter;

import hu.gyerob.trakiapp.R;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;
import data.Racer;

public class RacerAdapter extends BaseAdapter {
	ArrayList<Racer> racers;

	public RacerAdapter(final ArrayList<Racer> Racers) {
		super();
		this.racers = Racers;
	}

	@Override
	public int getCount() {
		return racers.size();
	}

	@Override
	public Racer getItem(int position) {
		return racers.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Racer racer = getItem(position);

		LayoutInflater inflater = (LayoutInflater) parent.getContext()
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = inflater.inflate(R.layout.row_racer, null);

		TextView name = (TextView) view.findViewById(R.id.row_racerName);
		TextView number = (TextView) view.findViewById(R.id.row_racerNumber);
		CheckBox sex = (CheckBox) view.findViewById(R.id.row_racerSex);
		TextView town = (TextView) view.findViewById(R.id.row_racerTown);
		CheckBox trailer = (CheckBox) view.findViewById(R.id.row_racerTrailer);
		CheckBox slalom = (CheckBox) view.findViewById(R.id.row_racerSlalom);
		CheckBox drag = (CheckBox) view.findViewById(R.id.row_racerDrag);

		name.setTextColor(Color.BLACK);
		number.setTextColor(Color.BLACK);
		town.setTextColor(Color.BLACK);

		name.setText(racer.getName());
		number.setText(Integer.toString(racer.getNumber()));
		sex.setChecked(racer.getSex());
		town.setText(racer.getTown());
		trailer.setChecked(racer.getTrailer());
		slalom.setChecked(racer.getSlalom());
		drag.setChecked(racer.getDrag());

		return view;
	}
}
