package com.qfc.yft.net.action;

import org.json.JSONException;

import android.content.Context;

public class ActUpdateRcv implements ActionReceiverImpl {

	ActionReceiverImpl rcv;
	ActionUpdateImpl updater;
	
	
	
	public ActUpdateRcv(ActionReceiverImpl rcv, ActionUpdateImpl updater) {
		super();
		this.rcv = rcv;
		this.updater = updater;
	}

	@Override
	public boolean response(String result) throws JSONException {
		return null!=rcv&&rcv.response(result)&&null!=updater&&updater.update(rcv);
	}

	@Override
	public Context getReceiverContext() {
		return null!=rcv?rcv.getReceiverContext():null;
	}

}
