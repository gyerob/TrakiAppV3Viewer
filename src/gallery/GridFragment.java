package gallery;

import hu.gyerob.trakiapp.R;

import java.util.ArrayList;
import java.util.List;

import jsonParser.JSONParser;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import adapter.GridViewAdapter;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;

public class GridFragment extends Fragment {

	private static String url_get_all_image = "http://gyerob.no-ip.biz/trakiweb/get_all_image.php";
	private static final String TAG_SUCCESS = "success";
	private static final String TAG_PRODUCTS = "images";

	private JSONParser jsonParser = new JSONParser();;
	private GridViewAdapter gridAdapter;
	private ArrayList<String> picturenames;
	private JSONArray images = null;
	private GridView gridView;
	private ImageView iv;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.activity_gallery_grid, container, false);

		picturenames = new ArrayList<String>();

		gridView = (GridView) v.findViewById(R.id.gridview);
		gridAdapter = new GridViewAdapter(getActivity(),
				R.layout.row_grid, picturenames);
		gridView.setAdapter(gridAdapter);

		iv = (ImageView) v.findViewById(R.id.galleryiv);
		iv.setImageDrawable(getResources().getDrawable(R.drawable.icon));

		gridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View v,
					int position, long id) {
				Bitmap bm = ((GridViewAdapter) gridView.getAdapter())
						.getBitmap(position);
				if (bm != null)
					iv.setImageBitmap(bm);
			}
		});

		new GetImages().execute();
		
		return v;
	}

	public class GetImages extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... param) {
			// Building Parameters
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			// getting JSON string from URL
			JSONObject json = jsonParser.makeHttpRequest(url_get_all_image,
					"GET", params);

			picturenames = new ArrayList<String>();

			try {
				// Checking for SUCCESS TAG
				int success = json.getInt(TAG_SUCCESS);

				if (success == 1) {
					// products found
					// Getting Array of Products
					images = json.getJSONArray(TAG_PRODUCTS);

					// looping through All Products
					for (int i = 0; i < images.length(); i++) {
						JSONObject c = images.getJSONObject(i);

						// Storing each json item in variable
						String nev = c.getString("nev");

						picturenames.add(nev);
					}
				}
			} catch (JSONException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);

			gridAdapter.clear();
			gridAdapter.addAll(picturenames);
			gridAdapter.notifyDataSetChanged();
		}
	}

}
