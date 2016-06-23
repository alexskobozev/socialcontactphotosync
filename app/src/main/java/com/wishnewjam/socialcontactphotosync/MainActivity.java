package com.wishnewjam.socialcontactphotosync;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.jakewharton.rxbinding.view.RxView;
import com.trello.rxlifecycle.components.RxActivity;

public class MainActivity extends RxActivity
{
	// TODO:LIST
	// 1. MVP
	// 2. VK
	// 3. NOTIFICATIONS
	// 4. LIST

	private static final String TAG = "RXTesting";
	private static final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 24234;

	Button synchroButton;
	private rx.functions.Action1<java.lang.Throwable> myErrorHandler =
			throwable -> Log.d(TAG, "Error " + throwable.getMessage());

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		synchroButton = (Button) findViewById(R.id.btn_vk_sync);

		RxView.clicks(synchroButton).subscribe(aVoid -> {

			if (ContextCompat.checkSelfPermission(this,
					Manifest.permission.READ_CONTACTS)
					!= PackageManager.PERMISSION_GRANTED)
			{
				if (ActivityCompat.shouldShowRequestPermissionRationale(this,
						Manifest.permission.READ_CONTACTS))
				{
					AlertDialog.Builder builder = new AlertDialog.Builder(this);
					builder.setMessage(R.string.rationale).setCancelable(true).create().show();
				} else
				{
					ActivityCompat.requestPermissions(this,
							new String[]{Manifest.permission.READ_CONTACTS},
							MY_PERMISSIONS_REQUEST_READ_CONTACTS);
				}
			} else
			{
				Toast.makeText(this, "SARY(:", Toast.LENGTH_SHORT).show();
			}
		});

//		CollectContactsMaster.getCollectPhoneContactsObservable(getContentResolver()).subscribeOn(
//				Schedulers.io())
//				.flatMap()
	}

	@Override
	public void onRequestPermissionsResult(int requestCode,
	                                       String permissions[], int[] grantResults)
	{
		switch (requestCode)
		{
			case MY_PERMISSIONS_REQUEST_READ_CONTACTS:
			{
				// If request is cancelled, the result arrays are empty.
				if (grantResults.length > 0
						&& grantResults[0] == PackageManager.PERMISSION_GRANTED)
				{

					collectContacts();
				} else
				{
					Toast.makeText(this, "SARY(:", Toast.LENGTH_SHORT).show();
				}
			}
		}
	}

	private void collectContacts()
	{
		CollectContactsMaster.getCollectPhoneContactsObservable(getContentResolver())
				.subscribe(strings -> {
					strings.forEach(s -> Log.d(TAG, "Contact: " + s));
				}, myErrorHandler);
	}
}
