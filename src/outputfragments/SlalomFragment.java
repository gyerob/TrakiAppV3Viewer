package outputfragments;

import hu.gyerob.trakiapp.R;

import java.util.ArrayList;
import java.util.List;

import jsonParser.JSONParser;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import adapter.SlalomAdapter;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import data.Slalom;

public class SlalomFragment extends ListFragment {
	public static final String TITLE = "Szlalom";

	private SlalomAdapter adapter;
	ArrayList<Slalom> slalomList;

	public ProgressDialog pDialog;

	JSONParser jsonParser = new JSONParser();

	private static String url_all_slalom = "http://gyerob.no-ip.biz/trakiweb/get_all_slalom.php";

	private static final String TAG_SUCCESS = "success";
	private static final String TAG_PRODUCTS = "slalom";

	JSONArray trailers = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		slalomList = new ArrayList<Slalom>();

		new LoadAllSlalom().execute();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.output_slalom, container, false);
		return v;
	}

	class LoadAllSlalom extends AsyncTask<String, String, String> {

		boolean failed = false;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(SlalomFragment.this.getActivity());
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
			JSONObject json = jsonParser.makeHttpRequest(url_all_slalom, "GET",
					params);

			try {
				// Checking for SUCCESS TAG
				int success = json.getInt(TAG_SUCCESS);

				if (success == 1) {
					// products found
					// Getting Array of Products
					trailers = json.getJSONArray(TAG_PRODUCTS);

					// looping through All Products
					for (int i = 0; i < trailers.length(); i++) {
						JSONObject c = trailers.getJSONObject(i);

						Slalom slalom = new Slalom();

						// Storing each json item in variable
						slalom.setNumber(Integer.parseInt(c.getString("rajt")));
						slalom.setName(c.getString("nev"));
						slalom.setIdo(c.getString("ido"));
						slalom.setHiba((c.getInt("hiba")));
						slalom.setVido((c.getString("vido")));

						slalomList.add(slalom);
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
						SlalomFragment.this.getActivity(),
						"Sikertelen lekérés, ellenõrizd az internetkapcsolatot",
						Toast.LENGTH_LONG).show();
			} else {
				adapter = new SlalomAdapter(slalomList);
				setListAdapter(adapter);
			}
		}
	}
}
