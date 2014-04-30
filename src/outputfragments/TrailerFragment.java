package outputfragments;

import hu.gyerob.trakiapp.R;

import java.util.ArrayList;
import java.util.List;

import jsonParser.JSONParser;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import adapter.TrailerAdapter;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import data.Trailer;

public class TrailerFragment extends ListFragment {
	public static final String TITLE = "Pótkocsis";

	private TrailerAdapter adapter;
	ArrayList<Trailer> trailerList;

	public ProgressDialog pDialog;

	JSONParser jParser = new JSONParser();

	private static String url_all_trailer = "http://gyerob.no-ip.biz/trakiweb/get_all_trailer.php";

	private static final String TAG_SUCCESS = "success";
	private static final String TAG_PRODUCTS = "trailer";

	JSONArray trailers = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		trailerList = new ArrayList<Trailer>();

		new LoadAllTrailer().execute();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.output_trailer, container, false);
		return v;
	}

	class LoadAllTrailer extends AsyncTask<String, String, String> {

		boolean failed = false;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(TrailerFragment.this.getActivity());
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
			JSONObject json = jParser.makeHttpRequest(url_all_trailer, "GET",
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

						Trailer trailer = new Trailer();

						// Storing each json item in variable
						trailer.setNumber(Integer.parseInt(c.getString("rajt")));
						trailer.setName(c.getString("nev"));
						trailer.setIdo(c.getString("ido"));
						trailer.setHiba((c.getInt("hiba")));
						trailer.setVido((c.getString("vido")));

						trailerList.add(trailer);
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
						TrailerFragment.this.getActivity(),
						"Sikertelen lekérés, ellenõrizd az internetkapcsolatot",
						Toast.LENGTH_LONG).show();
			} else {
				adapter = new TrailerAdapter(trailerList);
				setListAdapter(adapter);
			}
		}
	}
}
