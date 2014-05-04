package com.qfc.yft.ui.custom.list;

import com.qfc.yft.R;
import com.qfc.yft.YftData;
import com.qfc.yft.entity.User;
import com.qfc.yft.entity.listitem.LIICompany;
import com.qfc.yft.entity.listitem.LIIPeople;
import com.qfc.yft.entity.listitem.LIIProduct;
import com.qfc.yft.ui.YftActivityGate;
import com.qfc.yft.ui.custom.list.ListAbsAdapter.ListItemImpl;
import com.qfc.yft.utils.JackUtils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.AdapterView.OnItemClickListener;

/**
 * provide the three most common listener according to type (product, company, people)
 * @author taotao
 *
 */
public class JLVDefaultOnItemListener implements OnItemClickListener {
	int type;

	public JLVDefaultOnItemListener(int type){
		this.type = type;
	}
	
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		switch (type) {
		case ListItemImpl.ITEMTYPE_PRODUCT_SEARCH:
			LIIProduct prod = (LIIProduct)parent.getAdapter().getItem(position);
			if(prod.isPrivate()){
				PrivateChecker.prod = prod;
				PrivateChecker.context=parent.getContext();
				showPwdDialog(parent.getContext(),prod.getProductPassword());
			}else{
				
				YftActivityGate.goProduct(parent.getContext(),prod);
			}
			break;
		case ListItemImpl.ITEMTYPE_COMPANY_SEARCH:
			LIICompany comp = (LIICompany)parent.getAdapter().getItem(position);
			
			YftActivityGate.goShop(parent.getContext(), comp.getShopId(), comp.getShopName(), comp.getHasMotion(), -1);
			break;
		case ListItemImpl.ITEMTYPE_PEOPLE_SEARCH:
		case ListItemImpl.ITEMTYPE_PEOPLE_MY:
			LIIPeople peop = (LIIPeople)parent.getAdapter().getItem(position);
			
			YftActivityGate.goPeople(parent.getContext(), peop);
			break;
		/*case ListItemImpl.ITEMTYPE_IMAGINE:
			ImagineItemInfo iii = (ImagineItemInfo)parent.getAdapter().getItem(position);
			edit.setText(iii.iii_name.trim());
			gosearch();
			break;
		case ListItemImpl.ITEMTYPE_LOCALHISTORY:
			SearchHistoryItemInfo shi = (SearchHistoryItemInfo)currentJlv.getAdapter().getItem(position);
			edit.setText(shi.history_str.trim());
			gosearch();*/
		default:
			break;
		}

	}

	private void showPwdDialog(Context context, String productPassword) {
		PrivateChecker.et = new EditText(context);//
		new AlertDialog.Builder(context).setTitle("请输入私人展厅密码").setIcon(
			     R.drawable.ic_launcher).setView(
			    		 PrivateChecker.et).setPositiveButton("确定", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						
						checkPrivateCode();
						dialog.dismiss();
						PrivateChecker.dispose();
					}
				})
			     .setNegativeButton("取消", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
						
					}
				}).show();
	}
	static class PrivateChecker{
		static Integer pos;
		static EditText et;
		
		static LIIProduct prod; 
		static Context context;
		public static void dispose() {
			PrivateChecker.et=null;
			PrivateChecker.pos=null;
			PrivateChecker.prod = null;
			PrivateChecker.context = null;//
		}
	}
	
	private void checkPrivateCode(){
		String inputCode = PrivateChecker.et.getText().toString();
		String input2MD5 = JackUtils.getMD5(inputCode);
		Log.i("checkPrivate", PrivateChecker.prod.getProductPassword()+"_c_m_"+input2MD5);
		if(input2MD5.equals(PrivateChecker.prod.getProductPassword())){//成功
//			goActivity(mAdapter.contentList.get(PrivateChecker.pos));
			YftActivityGate.goProduct(PrivateChecker.context,PrivateChecker.prod);
		}else{
			JackUtils.showToast(PrivateChecker.context, "密码不正确");
		}
	}

	
}
