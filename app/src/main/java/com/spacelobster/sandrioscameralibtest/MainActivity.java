package com.spacelobster.sandrioscameralibtest;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.sandrios.sandriosCamera.internal.SandriosCamera;
import com.sandrios.sandriosCamera.internal.configuration.CameraConfiguration;

public class MainActivity extends AppCompatActivity {
	private final int REQUEST_CAMERA = 99;
	private final int CAPTURE_MEDIA = 88;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		Button btn = (Button)findViewById(R.id.run_camera_btn);
		btn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				launchMediaCapture();
			}
		});
	}

	@Override
	public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);

		if (grantResults.length > 0) {
			if (requestCode == REQUEST_CAMERA) {
				if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
					Log.d("Perm", "Wooohoo camera permission granted!");
					launchMediaCapture();
				}
			}
		}
	}

	private void launchMediaCapture() {
		if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED ||
				ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
			askCameraPermission();
		} else {
			new SandriosCamera(this, CAPTURE_MEDIA)
					.setShowPicker(true)
					.setMediaAction(CameraConfiguration.MEDIA_ACTION_PHOTO)
					.launchCamera();
		}
	}

	@TargetApi(Build.VERSION_CODES.M)
	private void askCameraPermission() {
		requestPermissions(new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CAMERA);
	}
}
