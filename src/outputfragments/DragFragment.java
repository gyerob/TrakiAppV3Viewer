package outputfragments;

import hu.gyerob.trakiapp.R;

import java.util.ArrayList;
import java.util.List;

import jsonParser.JSONParser;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import adapter.DragAdapter;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import data.Drag;

public class DragFragment extends ListFragment {
	public static final String TITLE = "Gyorsulás";

	private DragAdapter adapter;
	ArrayList<Drag> dragList;

	public ProgressDialog pDialog;

	JSONParser jsonParser = new JSONParser();

	private static String url_all_drag = "http://gyerob.no-ip.biz/trakiweb/get_all_drag.php";

	private static final String TAG_SUCCESS = "success";
	private static final String TAG_PRODUCTS = "drag";

	JSONArray drags = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		dragList = new ArrayList<Drag>();
		
		new LoadAllDrag().execute();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.output_drag, container, false);
		return v;
	}

	class LoadAllDrag extends AsyncTask<String, String, String> {

		boolean failed = false;
		
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(DragFragment.this.getActivity());
			pDialog.setMessage("Versenyzõk betöltése, kérlek várj...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(false);
			pDialog.show();
		}

		/**
		 * getting All products from url
		 * */
		protected String doInBackground(String... args) {
			// Building Parameters
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			// getting JSON string from URL
			JSONObject json = jsonParser.makeHttpRequest(url_all_drag, "GET",
					params);

			try {
				// Checking for SUCCESS TAG
				int success = json.getInt(TAG_SUCCESS);

				if (success == 1) {
					// products found
					// Getting Array of Products
					drags = json.getJSONArray(TAG_PRODUCTS);

					// looping through All Products
					for (int i = 0; i < drags.length(); i++) {
						JSONObject c = drags.getJSONObject(i);

						Drag drag = new Drag();

						// Storing each json item in variable
						drag.setNumber(Integer.parseInt(c.getString("rajt")));
						drag.setName(c.getString("nev"));
						drag.setIdo1(c.getString("ido1"));
						drag.setIdo2(c.getString("ido2"));
						drag.setLegjobbIdo(c.getString("lido"));

						dragList.add(drag);
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
						DragFragment.this.getActivity(),
						"Sikertelen lekérés, ellenõrizd az internetkapcsolatot",
						Toast.LENGTH_LONG).show();
			} else {
			adapter = new DragAdapter(dragList);
			setListAdapter(adapter);}
		}
	}
}
