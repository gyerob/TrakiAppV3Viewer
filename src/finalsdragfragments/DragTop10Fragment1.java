package finalsdragfragments;

import hu.gyerob.trakiapp.R;

import java.util.ArrayList;
import java.util.List;

import jsonParser.JSONParser;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import toplistview.ToplistView;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import data.DragTop;

public class DragTop10Fragment1 extends Fragment {
	public static final String TITLE = "Lépcsõk";

	private static String url_get_drag_top = "http://gyerob.no-ip.biz/trakiweb/get_all_drag_top.php";
	private static final String TAG_SUCCESS = "success";
	private static final String TAG_PRODUCTS = "racers";

	// Progress Dialog
	private ProgressDialog pDialog;

	private JSONParser jsonParser;

	private ArrayList<DragTop> dragListr1;
	private ArrayList<DragTop> dragListr2;
	private ArrayList<DragTop> dragListr3;
	private ArrayList<DragTop> dragListr4;
	private JSONArray racers = null;

	ToplistView tvRound11;
	ToplistView tvRound12;
	ToplistView tvRound13;
	ToplistView tvRound14;
	ToplistView tvRound15;
	ToplistView tvRound16;
	ToplistView tvRound17;
	ToplistView tvRound18;

	ToplistView tvRound21;
	ToplistView tvRound22;
	ToplistView tvRound23;
	ToplistView tvRound24;

	ToplistView tvRound31;
	ToplistView tvRound32;
	ToplistView tvRound33;
	ToplistView tvRound34;

	ToplistView tvRound41;
	ToplistView tvRound42;
	ToplistView tvRound43;
	ToplistView tvRound44;

	ArrayList<ToplistView> topracers;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		jsonParser = new JSONParser();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.finalsdragscrollv2, container,
				false);

		tvRound11 = (ToplistView) v.findViewById(R.id.dtoplistr11);
		tvRound12 = (ToplistView) v.findViewById(R.id.dtoplistr12);
		tvRound13 = (ToplistView) v.findViewById(R.id.dtoplistr13);
		tvRound14 = (ToplistView) v.findViewById(R.id.dtoplistr14);
		tvRound15 = (ToplistView) v.findViewById(R.id.dtoplistr15);
		tvRound16 = (ToplistView) v.findViewById(R.id.dtoplistr16);
		tvRound17 = (ToplistView) v.findViewById(R.id.dtoplistr17);
		tvRound18 = (ToplistView) v.findViewById(R.id.dtoplistr18);

		tvRound21 = (ToplistView) v.findViewById(R.id.dtoplistr21);
		tvRound22 = (ToplistView) v.findViewById(R.id.dtoplistr22);
		tvRound23 = (ToplistView) v.findViewById(R.id.dtoplistr23);
		tvRound24 = (ToplistView) v.findViewById(R.id.dtoplistr24);

		tvRound31 = (ToplistView) v.findViewById(R.id.dtoplistr31);
		tvRound32 = (ToplistView) v.findViewById(R.id.dtoplistr32);
		tvRound33 = (ToplistView) v.findViewById(R.id.dtoplistr33);
		tvRound34 = (ToplistView) v.findViewById(R.id.dtoplistr34);

		tvRound41 = (ToplistView) v.findViewById(R.id.dtoplistr41);
		tvRound42 = (ToplistView) v.findViewById(R.id.dtoplistr42);
		tvRound43 = (ToplistView) v.findViewById(R.id.dtoplistr43);
		tvRound44 = (ToplistView) v.findViewById(R.id.dtoplistr44);

		tvRound11.setclickable(false);
		tvRound12.setclickable(false);
		tvRound13.setclickable(false);
		tvRound14.setclickable(false);
		tvRound15.setclickable(false);
		tvRound16.setclickable(false);
		tvRound17.setclickable(false);
		tvRound18.setclickable(false);
		tvRound21.setclickable(false);
		tvRound22.setclickable(false);
		tvRound23.setclickable(false);
		tvRound24.setclickable(false);
		tvRound31.setclickable(false);
		tvRound32.setclickable(false);
		tvRound33.setclickable(false);
		tvRound34.setclickable(false);
		tvRound41.setclickable(false);
		tvRound42.setclickable(false);
		tvRound43.setclickable(false);
		tvRound44.setclickable(false);

		dragListr1 = new ArrayList<DragTop>();
		dragListr2 = new ArrayList<DragTop>();
		dragListr3 = new ArrayList<DragTop>();
		dragListr4 = new ArrayList<DragTop>();
		new GetList().execute();

		return v;
	}

	class GetList extends AsyncTask<String, String, String> {

		boolean failed = false;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(
					DragTop10Fragment1.this.getActivity());
			pDialog.setMessage("Versenyzõ frissítése..");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}

		@Override
		protected String doInBackground(String... param) {
			// Building Parameters
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			// getting JSON string from URL
			JSONObject json = jsonParser.makeHttpRequest(url_get_drag_top,
					"GET", params);

			try {
				// Checking for SUCCESS TAG
				int success = json.getInt(TAG_SUCCESS);

				if (success == 1) {
					// products found
					// Getting Array of Products
					racers = json.getJSONArray(TAG_PRODUCTS);

					Log.d("racers hossza:", Integer.toString(racers.length()));
					// looping through All Products
					for (int i = 0; i < racers.length(); i++) {
						JSONObject c = racers.getJSONObject(i);

						DragTop racer = new DragTop();

						// Storing each json item in variable
						racer.setNumber(Integer.parseInt(c.getString("rajt")));
						racer.setName(c.getString("nev"));
						racer.setWon(Integer.parseInt(c.getString("nyert")));
						racer.setPid(Integer.parseInt(c.getString("pid")));

						if (i < 8)
							dragListr1.add(racer);
						else if (i < 12)
							dragListr2.add(racer);
						else if (i < 16)
							dragListr3.add(racer);
						else if (i < 20)
							dragListr4.add(racer);
					}
				}
			} catch (JSONException e) {
				e.printStackTrace();
			} catch (Exception e) {
				failed = true;
				e.printStackTrace();
			}

			return null;
		}

		protected void onPostExecute(String file_url) {

			pDialog.dismiss();

			if (failed) {
				Toast.makeText(
						DragTop10Fragment1.this.getActivity(),
						"Sikertelen lekérés\n, ellenõrizd az internetkapcsolatot.",
						Toast.LENGTH_LONG).show();
			} else {
				tvRound11.setData(dragListr1.get(4).getName(), dragListr1
						.get(4).getNumber(), dragListr1.get(4).getWon(),
						dragListr1.get(4).getPid());
				Log.d("1round", tvRound11.getName() + " " + tvRound11.getPid());
				tvRound12.setData(dragListr1.get(3).getName(), dragListr1
						.get(3).getNumber(), dragListr1.get(3).getWon(),
						dragListr1.get(3).getPid());
				Log.d("1round", tvRound12.getName() + " " + tvRound12.getPid());
				tvRound13.setData(dragListr1.get(1).getName(), dragListr1
						.get(1).getNumber(), dragListr1.get(1).getWon(),
						dragListr1.get(1).getPid());
				Log.d("1round", tvRound13.getName() + " " + tvRound13.getPid());
				tvRound14.setData(dragListr1.get(6).getName(), dragListr1
						.get(6).getNumber(), dragListr1.get(6).getWon(),
						dragListr1.get(6).getPid());
				Log.d("1round", tvRound14.getName() + " " + tvRound14.getPid());
				tvRound15.setData(dragListr1.get(7).getName(), dragListr1
						.get(7).getNumber(), dragListr1.get(7).getWon(),
						dragListr1.get(7).getPid());
				Log.d("1round", tvRound15.getName() + " " + tvRound15.getPid());
				tvRound16.setData(dragListr1.get(0).getName(), dragListr1
						.get(0).getNumber(), dragListr1.get(0).getWon(),
						dragListr1.get(0).getPid());
				Log.d("1round", tvRound16.getName() + " " + tvRound16.getPid());
				tvRound17.setData(dragListr1.get(2).getName(), dragListr1
						.get(2).getNumber(), dragListr1.get(2).getWon(),
						dragListr1.get(2).getPid());
				Log.d("1round", tvRound17.getName() + " " + tvRound17.getPid());
				tvRound18.setData(dragListr1.get(5).getName(), dragListr1
						.get(5).getNumber(), dragListr1.get(5).getWon(),
						dragListr1.get(5).getPid());
				Log.d("1round", tvRound18.getName() + " " + tvRound18.getPid());

				tvRound21.setData(dragListr2.get(0).getName(), dragListr2
						.get(0).getNumber(), dragListr2.get(0).getWon(),
						dragListr2.get(0).getPid());
				tvRound22.setData(dragListr2.get(1).getName(), dragListr2
						.get(1).getNumber(), dragListr2.get(1).getWon(),
						dragListr2.get(1).getPid());
				tvRound23.setData(dragListr2.get(2).getName(), dragListr2
						.get(2).getNumber(), dragListr2.get(2).getWon(),
						dragListr2.get(2).getPid());
				tvRound24.setData(dragListr2.get(3).getName(), dragListr2
						.get(3).getNumber(), dragListr2.get(3).getWon(),
						dragListr2.get(3).getPid());

				tvRound31.setData(dragListr3.get(0).getName(), dragListr3
						.get(0).getNumber(), dragListr3.get(0).getWon(),
						dragListr3.get(0).getPid());
				tvRound32.setData(dragListr3.get(1).getName(), dragListr3
						.get(1).getNumber(), dragListr3.get(1).getWon(),
						dragListr3.get(1).getPid());
				tvRound33.setData(dragListr3.get(2).getName(), dragListr3
						.get(2).getNumber(), dragListr3.get(2).getWon(),
						dragListr3.get(2).getPid());
				tvRound34.setData(dragListr3.get(3).getName(), dragListr3
						.get(3).getNumber(), dragListr3.get(3).getWon(),
						dragListr3.get(3).getPid());

				tvRound41.setData(dragListr4.get(0).getName(), dragListr4
						.get(0).getNumber(), dragListr4.get(0).getWon(),
						dragListr4.get(0).getPid());
				tvRound42.setData(dragListr4.get(1).getName(), dragListr4
						.get(1).getNumber(), dragListr4.get(1).getWon(),
						dragListr4.get(1).getPid());
				tvRound43.setData(dragListr4.get(2).getName(), dragListr4
						.get(2).getNumber(), dragListr4.get(2).getWon(),
						dragListr4.get(2).getPid());
				tvRound44.setData(dragListr4.get(3).getName(), dragListr4
						.get(3).getNumber(), dragListr4.get(3).getWon(),
						dragListr4.get(3).getPid());
			}
		}
	}
}
