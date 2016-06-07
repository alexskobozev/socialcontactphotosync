package com.wishnewjam.socialcontactphotosync;

import rx.Observable;
import rx.Subscriber;

public class CollectContactsMaster
{

	Observable<String> collectPhoneContactsObservable = Observable.create(
			new Observable.OnSubscribe<String>()
			{
				@Override
				public void call(Subscriber<? super String> subscriber)
				{
					// collect contact
					subscriber.onNext("Hi");
					subscriber.onCompleted();
				}
			}
	);
}
