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
import data.Trailer;

public class TrailerAdapter extends BaseAdapter {
	ArrayList<Trailer> trailers;

	public TrailerAdapter(final ArrayList<Trailer> Trailers) {
		super();
		this.trailers = Trailers;
	}

	@Override
	public Trailer getItem(int position) {
		return trailers.get(position);
	}

	@Override
	public int getCount() {
		return trailers.size();
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Trailer trailer = getItem(position);
		
		LayoutInflater inflater = (LayoutInflater) parent.getContext()
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = inflater.inflate(R.layout.row_trailer, null);
		
		TextView name = (TextView) view.findViewById(R.id.row_trailerName);
		TextView number = (TextView) view.findViewById(R.id.row_trailerNumber);
		TextView ido = (TextView) view.findViewById(R.id.row_trailerIdo);
		TextView hiba = (TextView) view.findViewById(R.id.row_trailerHiba);
		TextView vido = (TextView) view.findViewById(R.id.row_trailerVido);
		
		name.setTextColor(Color.BLACK);
		number.setTextColor(Color.BLACK);
		ido.setTextColor(Color.BLACK);
		hiba.setTextColor(Color.BLACK);
		vido.setTextColor(Color.BLACK);

		name.setText(trailer.getName());
		number.setText(Integer.toString(trailer.getNumber()));
		ido.setText(trailer.getIdo());
		hiba.setText(Integer.toString(trailer.getHiba()));
		vido.setText(trailer.getVido());
		
		return view;
	}	
}
