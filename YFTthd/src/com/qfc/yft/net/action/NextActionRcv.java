package com.qfc.yft.net.action;


import org.json.JSONException;

import android.content.Context;

/**
 * @author tao
 *do next request if the last goes well
 *consider extends {@link BareReceiver}
 */
public class NextActionRcv implements ActionReceiverImpl {

	ActionReceiverImpl lastRcv;
	ActionRequestImpl nextReq;
	ActionReceiverImpl nextRcv;
	

	public NextActionRcv(ActionReceiverImpl lastRcv,
			ActionRequestImpl nReq, ActionReceiverImpl nRcv) {
		super();
		this.lastRcv = lastRcv;
		this.nextReq = nReq;
		this.nextRcv = nRcv;
	}

	@Override
	public boolean response(String result) throws JSONException {
		if(null!=lastRcv&&lastRcv.response(result)){
			ActionBuilder.getInstance().request(nextReq, nextRcv);
			return true;
		}
		return false;
	}

	@Override
	public Context getReceiverContext() {
		return null==nextRcv?null:nextRcv.getReceiverContext();
	}


}
