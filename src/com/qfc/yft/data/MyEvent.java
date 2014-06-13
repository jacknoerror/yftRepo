package com.qfc.yft.data;

import android.os.Bundle;
import de.greenrobot.event.EventBus;

/**
 *used for {@link EventBus} 
 * @author taotao
 * @Date 2014-6-13
 */
public class MyEvent {
	int eventId;
	Bundle bundle;
	public MyEvent(int eventId) {
		super();
		this.eventId = eventId;
	}
	public final Bundle getBundle() {
		return bundle;
	}
	public final void setBundle(Bundle bundle) {
		this.bundle = bundle;
	}
	public final int getEventId() {
		return eventId;
	}
	
}
