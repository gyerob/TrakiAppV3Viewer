package hu.gyerob.trakiapp;

import gallery.GridFragment;
import gallery.WebFragment;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import jsonParser.JSONParser;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

public class GalleryActivity extends FragmentActivity {

	// private static final int SELECT_PHOTO = 100;
	private static String url_upload_img = "http://gyerob.no-ip.biz/trakiweb/img_up.php";
	private JSONParser jsonParser = new JSONParser();
	
	// Progress Dialog
	private ProgressDialog pDialog;
	
	private SharedPreferences prefs;
	private String nezet;
	
	private Fragment fragment;

	//private ImageView iv;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		

		prefs = PreferenceManager
				.getDefaultSharedPreferences(getBaseContext());
		nezet = prefs.getString("galleryswitch", "Grid");
		Toast.makeText(this, nezet, Toast.LENGTH_LONG).show();
		
		setContentView(R.layout.activity_gallery);
		
		FragmentManager fm = getSupportFragmentManager();
		FragmentTransaction ft = fm.beginTransaction();
		if (nezet.equals("Grid")) fragment = new GridFragment();
		else fragment = new WebFragment();
		ft.add(R.id.FragmentContainer, fragment);
		ft.commit();
	}

	protected void onActivityResult(int requestCode, int resultCode,
			Intent imageReturnedIntent) {
		super.onActivityResult(requestCode, resultCode, imageReturnedIntent);

		if (resultCode == RESULT_OK) {
			if (requestCode == 1) {
				Uri imgUri = imageReturnedIntent.getData();

				new ImgUpload().execute(imgUri);
			}
		}
	}

	private class ImgUpload extends AsyncTask<Uri, Bitmap, Bitmap> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(GalleryActivity.this);
			pDialog.setMessage("Kép feltöltése folyamatban...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}

		@Override
		protected Bitmap doInBackground(Uri... params) {
			Bitmap bm = null;
			for (Uri param : params) {
				try {
					bm = MediaStore.Images.Media.getBitmap(
							GalleryActivity.this.getContentResolver(), param);
					ByteArrayOutputStream baos = new ByteArrayOutputStream();

					// int width = bm.getWidth();
					// int height = bm.getHeight();
					// double ratio = 400/width;
					// int newheight = (int)(ratio*height);

					// bm = Bitmap.createScaledBitmap(bm, 800, newheight, true);

					bm.compress(Bitmap.CompressFormat.JPEG, 95, baos);
					byte[] bytearray = baos.toByteArray();
					String img = Base64.encodeToString(bytearray, 0);

					List<NameValuePair> data = new ArrayList<NameValuePair>();
					data.add(new BasicNameValuePair("image", img));

					JSONObject json = jsonParser.makeHttpRequest(
							url_upload_img, "POSTIMG", data);

					// check log cat fro response
					Log.d("Create Response", json.toString());

				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			return bm;
		}

		@Override
		protected void onPostExecute(Bitmap result) {
			super.onPostExecute(result);
			//iv.setImageBitmap(result);
			// dismiss the dialog once done
			pDialog.dismiss();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.gallery, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == R.id.itemImageUpload) {
			Intent photoPickerIntent = new Intent(Intent.ACTION_GET_CONTENT);
			photoPickerIntent.setType("image/*");
			startActivityForResult(
					Intent.createChooser(photoPickerIntent, "SELECT PICTURE"),
					1);
		}
		if (item.getItemId() == R.id.switchgalleryview) {
			if (nezet.equals("Grid")) {
				prefs.edit().putString("galleryswitch", "Web").commit();
				nezet = "Web";
			}
			else {
				prefs.edit().putString("galleryswitch", "Grid").commit();
				nezet = "Grid";
			}
			
			FragmentManager fm = getSupportFragmentManager();
			FragmentTransaction ft = fm.beginTransaction();
			if (nezet.equals("Grid")) fragment = new GridFragment();
			else fragment = new WebFragment();
			ft.replace(R.id.FragmentContainer, fragment);
			ft.commit();
		}
		return super.onOptionsItemSelected(item);
	}
}
