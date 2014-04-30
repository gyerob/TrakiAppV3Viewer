package hu.gyerob.trakiapp;

import map.TrakiMapRenderer;
import map.TrakiSurfaceView;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ConfigurationInfo;
import android.os.Build;
import android.os.Bundle;

public class MapActivity extends Activity {

	private TrakiSurfaceView felulet;
	private boolean rendererSet = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		felulet = new TrakiSurfaceView(this);

		// OpenGL ES 2 kompatibilitás ellenõrzés
		final ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
		final ConfigurationInfo configurationInfo = activityManager
				.getDeviceConfigurationInfo();
		final boolean supportsEs2 = configurationInfo.reqGlEsVersion >= 0x20000
				|| (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1 && (Build.FINGERPRINT
						.startsWith("generic")
						|| Build.FINGERPRINT.startsWith("unknown")
						|| Build.MODEL.contains("google_sdk")
						|| Build.MODEL.contains("Emulator") || Build.MODEL
							.contains("Android SDK built for x86")));

		if (supportsEs2) {
			// OpenGL ES 2 context
			felulet.setEGLContextClientVersion(2);

			// Renderer beállítás
			felulet.setRenderer(new TrakiMapRenderer(this));
			rendererSet = true;
		} else
			return;

		setContentView(felulet);
	}

	@Override
	protected void onPause() {
		super.onPause();
		if (rendererSet)
			felulet.onPause();
	}

	@Override
	protected void onResume() {
		super.onResume();
		if (rendererSet)
			felulet.onResume();
	}
}
