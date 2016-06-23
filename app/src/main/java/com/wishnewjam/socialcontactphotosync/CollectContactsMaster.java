package com.wishnewjam.socialcontactphotosync;

import android.content.ContentResolver;
import android.database.Cursor;
import android.os.Build;
import android.provider.ContactsContract;

import java.util.ArrayList;

import rx.Observable;
import rx.Subscriber;

public class CollectContactsMaster
{
	private final static String[] FROM_COLUMNS = {
			Build.VERSION.SDK_INT
					>= Build.VERSION_CODES.HONEYCOMB ?
					ContactsContract.Contacts.DISPLAY_NAME_PRIMARY :
					ContactsContract.Contacts.DISPLAY_NAME
	};

	private static final String[] PROJECTION =
			{
					ContactsContract.Contacts._ID,
					ContactsContract.Contacts.LOOKUP_KEY,
					ContactsContract.Contacts.DISPLAY_NAME_PRIMARY
			};

	private static final String SELECTION =

			ContactsContract.Contacts.DISPLAY_NAME_PRIMARY + " LIKE ?";

	private final static int[] TO_IDS = {
			android.R.id.text1
	};

	public void collectContacts()
	{

	}

	public static Observable<ArrayList<String>> getCollectPhoneContactsObservable(ContentResolver
			                                                                       contentResolver)
	{
		return Observable.create(
				new Observable.OnSubscribe<ArrayList<String>>()
				{
					@Override
					public void call(Subscriber<? super ArrayList<String>> subscriber)
					{
						Cursor cur = contentResolver.query(ContactsContract.Contacts.CONTENT_URI,
								null, null, null, null);

						ArrayList<String> contactNamesArrayList = new ArrayList<>();
						if (cur != null && cur.getCount() > 0)
						{
							while (cur.moveToNext())
							{
								String id = cur.getString(
										cur.getColumnIndex(ContactsContract.Contacts._ID));
								String name = cur.getString(cur.getColumnIndex(
										ContactsContract.Contacts.DISPLAY_NAME));

								contactNamesArrayList.add(name);

//								if (cur.getInt(cur.getColumnIndex(
//										ContactsContract.Contacts.HAS_PHONE_NUMBER)) > 0)
//								{
//									Cursor pCur = contentResolver.query(
//											ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
//											null,
//											ContactsContract.CommonDataKinds.Phone.CONTACT_ID +
//													" = ?",
//											new String[]{id}, null);
//									while (pCur.moveToNext())
//									{
//										String phoneNo = pCur.getString(pCur.getColumnIndex(
//												ContactsContract.CommonDataKinds.Phone.NUMBER));
//
//									}
//									pCur.close();
//								}
							}
							cur.close();
						}

						// collect contact
						subscriber.onNext(contactNamesArrayList);

						subscriber.onCompleted();
					}
				}
		);
	}
}
