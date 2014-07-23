package com.qfc.yft.ui.adapter.mj;

import org.json.JSONException;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.qfc.yft.R;
import com.qfc.yft.data.Const;
import com.qfc.yft.net.action.ActionBuilder;
import com.qfc.yft.net.action.ActionReceiverImpl;
import com.qfc.yft.net.action.ActionRequestImpl;
import com.qfc.yft.net.action.product.PMOffline;
import com.qfc.yft.net.action.product.PMOnline;
import com.qfc.yft.net.action.product.PMRepublish;
import com.qfc.yft.ui.ImageLoaderHelper;
import com.qfc.yft.ui.custom.list.MspAdapter;
import com.qfc.yft.util.JackUtils;
import com.qfc.yft.vo.ProductManage;

public class ListAdapterProductManage extends MspAdapter {

	class ProductManageViewHolder extends ViewHolderImpl implements OnClickListener, ActionReceiverImpl{

		ImageView icon;
		TextView tvName,tvPrice,tvStatus;
		TextView funcOn,funcOff,funcRe;
		CheckBox cb;
		
		@Override
		public void init() {
			icon = (ImageView)getHolderView().findViewById(R.id.img_item_pm);
			tvName = (TextView)getHolderView().findViewById(R.id.tv_pm_name);
			tvPrice = (TextView)getHolderView().findViewById(R.id.tv_pm_price);
			tvStatus = (TextView)getHolderView().findViewById(R.id.tv_pm_status);
			funcOn = (TextView)getHolderView().findViewById(R.id.btn_pm_on);
			funcOff = (TextView)getHolderView().findViewById(R.id.btn_pm_off);
			funcRe = (TextView)getHolderView().findViewById(R.id.btn_pm_re);
			
			cb = (CheckBox)getHolderView().findViewById(R.id.cb_item_pm);
//			getHolderView().setSelected(false);
			
			funcOn.setOnClickListener(this);
			funcOff.setOnClickListener(this);
			funcRe.setOnClickListener(this);
		}

		@Override
		public void setup(final int position) {
			ProductManage itm = (ProductManage) getItem(position);
//			ImageLoaderHelper.imageLoader.displayImage(itm.getImageUrl300X300(), icon);
			tvName.setText(itm.getProductName());
			tvPrice.setText("￥"+itm.getProductPrice());
			tvStatus.setText(itm.getStatusStr());
			
			funcOn.setEnabled(itm.isOnline());
			funcOff.setEnabled(itm.isOffline());
			funcRe.setEnabled(itm.isRepublish());
			funcOn.setTag(itm);
			funcOff.setTag(itm);
			funcRe.setTag(itm);
			
			cb.setVisibility(myScrollPageListView.isSelected()?View.VISIBLE:View.GONE);
			cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
				
				@Override
				public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//					getHolderView().setSelected(isChecked);
					myScrollPageListView.setItemChecked(position, isChecked);
				}
			});
			cb.setChecked(myScrollPageListView.isItemChecked(position));//0619
			
//			Log.i("NOW", String.format("(%s)(%s)(%s)(%s)", funcOff.isClickable()+"", "","",position+""));
		}

		@Override
		public int getLayoutId() {
			return R.layout.item_productmanage;
		}

		@Override
		public void onClick(View v) {
			ProductManage pm = (ProductManage) v.getTag();
			if(null==pm) return;
			ActionRequestImpl actReq = null;
			ActionReceiverImpl actRcv = this;
			switch (v.getId()) {
			case R.id.btn_pm_on:
				actReq = new PMOnline(pm.getCompanyId(), pm.getProductId());
				break;
			case R.id.btn_pm_off:
				actReq = new PMOffline(pm.getCompanyId(), pm.getProductId());
				
				break;
			case R.id.btn_pm_re:
				
				actReq = new PMRepublish(pm.getCompanyId(), pm.getProductId());
				break;

			default:
				break;
			}
			ActionBuilder.getInstance().request(actReq, actRcv);
//			Log.i("NOW", "onClick");
		}

		@Override
		public boolean response(String result) throws JSONException {
			if(result.contains("true")){ //FIXME
				JackUtils.showToast(getContextInAdapter(), "操作成功");
				myScrollPageListView.setup();
			}else{
				JackUtils.showToast(getContextInAdapter(), "操作失败");
				
			}
			return false;
		}

		@Override
		public Context getReceiverContext() {
			return getContextInAdapter();
		}
		
	}
	
	@Override
	public ViewHolderImpl getHolderInstance() {
		return new ProductManageViewHolder();
	}

	@Override
	public boolean areAllItemsEnabled() {
		// TODO Auto-generated method stub
		return super.areAllItemsEnabled();
//		return false;
		
	}
	
	@Override
	public boolean isEnabled(int position) {
		// TODO Auto-generated method stub
		return super.isEnabled(position);
//		return false;
	}
	
	@Override
	public long getItemId(int position) {
		return ((ProductManage)getItem(position)).getProductId();
	}
	
}
