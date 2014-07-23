package com.qfc.yft.ui.tabs.person;

import java.io.File;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.ToggleButton;

import com.qfc.yft.R;
import com.qfc.yft.data.MyData;
import com.qfc.yft.net.NetStrategies;
import com.qfc.yft.ui.offline.OfflineDataKeeper;
import com.qfc.yft.ui.offline.OfflineDownloadBuilder;
import com.qfc.yft.ui.offline.OfflineHelper;
import com.qfc.yft.ui.tabs.BarData;
import com.qfc.yft.ui.tabs.BarGroup;
import com.qfc.yft.ui.tabs.ContentAbstractFragment;
import com.qfc.yft.util.JackButtonColorFilter;
import com.qfc.yft.util.JackUtils;
import com.qfc.yft.vo.User;

public class TabPersonFragment extends ContentAbstractFragment {
	final String TAG = TabPersonFragment.class.getSimpleName();
	
	LinearLayout tpLayout;
	ToggleButton tgBtn;
	Button logoutBtn;
	
	User mpUser;
	@Override
	public int getLayoutRid() {
		return 0;
	}
	@Override
	public void initView() {
		if((mpUser = MyData.data().getMe()).getMemberType()==0){//游客
			logout();
			return;
		}
		mView = new ScrollView(getActivity());
		((ScrollView)mView).addView( mInflator.inflate(R.layout.fragment_person, null));
		initTable();
		
		logoutBtn = (Button)mView.findViewById(R.id.btn_person_logout);
		if(null!=logoutBtn)JackButtonColorFilter.setButtonFocusChanged(logoutBtn);
		logoutBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				logout();
			}
		});
		initTitleManager();
		titleManager.setTitleName(getString(R.string.titlename_person));
	}
	private void logout() {
		NetStrategies.logout();
		getActivity().finish();
	}
	final String[] DESC = new String[]{"我的商铺","我的收藏","我的人脉","同步我的商铺","离线模式","清空本地数据","移动商铺介绍","移动商铺企业推荐"};
	final int[] ICS = new int[]{R.drawable.person_myshop,R.drawable.person_mycollection,
			R.drawable.person_myfriends,R.drawable.person_refreshmyshop,R.drawable.person_offlinemodel,
			R.drawable.person_trash,R.drawable.person_moveshopintro,R.drawable.person_moveshoprecommanded
	};
	private void initTable() {
		if(mpUser==null) return;//critical error occurs
		int memberType = mpUser.getMemberType();
		String myName = mpUser.getRealName().isEmpty()?mpUser.getUserName():mpUser.getRealName();
		
		tpLayout = (LinearLayout)mView.findViewById(R.id.table_person);
		
		BarGroup bar1 = new BarGroup(getActivity());
		bar1.addBar(new BarData(R.drawable.person_icon,myName, null, null));//
		tpLayout.addView(bar1);
		
		BarGroup bar2 = new BarGroup(getActivity());
		if(memberType>1)bar2.addBar(new BarData(ICS[0], DESC[0], R.drawable.arrow, null));//我的商铺
		bar2.addBar(new BarData(ICS[1], DESC[1], R.drawable.arrow, null));//
		bar2.addBar(new BarData(ICS[2], DESC[2], R.drawable.arrow, null));
		tpLayout.addView(bar2);
		
		if(mpUser.getMemberType()==3){
			BarGroup bar3 = new BarGroup(getActivity());
			bar3.addBar(new BarData(ICS[3], DESC[3], R.drawable.person_refreshmyshop_right, null));//
			initUpdateSets(bar3.findViewById(ICS[3]));
			bar3.addBar(new BarData(ICS[4], DESC[4],android.R.drawable.btn_star_big_off, null));
			initToggleBtn(bar3.findViewById(ICS[4]));
			bar3.addBar(new BarData(ICS[5], DESC[5], null, null));
			initDeleteDialog(bar3);
			tpLayout.addView(bar3);
		}
		
		BarGroup bar4 = new BarGroup(getActivity());
		bar4.addBar(new BarData(ICS[6], DESC[6], R.drawable.arrow, null));//
		bar4.addBar(new BarData(ICS[7], DESC[7], R.drawable.arrow, null));
		tpLayout.addView(bar4);
		
	}
	
	private void initDeleteDialog(View bgroup) {
		AlertDialog.Builder builder = new Builder(getActivity());
		  builder.setMessage("确认删除离线数据？");

		  builder.setTitle("提示");

		  builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {

		   @Override
		   public void onClick(DialogInterface dialog, int which) {
		    dialog.dismiss();
		    deleteEverything();
		   }
		  });
		  
		  builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {

		   @Override
		   public void onClick(DialogInterface dialog, int which) {
		    dialog.dismiss();
		   }
		  });
		  
		  bgroup.setTag(builder); 
	}
	ProgressDialog oDialog;
	Handler oHandler;
	private void deleteEverything(){//封装起来？
		initHandler();
		oDialog = JackUtils.showProgressDialog(getActivity(), "");
		new Thread(){
			@Override
			public void run() {
				String path = MyData.data().getMyLocalPath();
				OfflineDataKeeper.clearOffPref();//
					if(!new File(path).exists()){
						oHandler.sendEmptyMessage(2);
					}else{
						if(JackUtils.deleteFile(path)){
							oHandler.sendEmptyMessage(0);
						}else{
							oHandler.sendEmptyMessage(1);
						}
					} 
			};
		}.start();
		
	}


	private void initHandler() {
		if(oHandler!=null) return;
		oHandler  = new Handler(){
			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				if(oDialog!=null&&oDialog.isShowing()) oDialog.dismiss();
				switch (msg.what) {
				case 0:
					/*OfflineDownloadBuilder.clear();
					MyData.data().setOffline(null);
					JackUtils.showToast(getActivity(), "离线数据删除成功");
					if(tgBtn.isChecked()) {
						MyData.data().setOfflineEnable(false);tgBtn.setChecked(false);
					}*/
					break;
				case 1:
					JackUtils.showToast(getActivity(), "删除失败");//
					break;
				case 2:
					JackUtils.showToast(getActivity(), "您的账号无需删除任何离线文件"); 
					break;
				default:
					break;
				}
			}
		};
	}
	

	private void initUpdateSets(View BarView){
		OfflineDownloadBuilder.getInstance().setViews(
							(ProgressBar)BarView.findViewById(R.id.progress_offline_person), 
							(ImageView)BarView.findViewById(R.id.img_item_person_right));
	}
	private void initToggleBtn(View barView) {
		tgBtn = (ToggleButton)barView.findViewById(R.id.toggle_offline_person);
		barView.findViewById(R.id.img_item_person_right).setVisibility(View.GONE);
		tgBtn.setVisibility(View.VISIBLE);
		tgBtn.setChecked(OfflineDataKeeper.isOfflineEnabled());
		tgBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if(isChecked){
					String path = MyData.data().getMyLocalPath();
					OfflineDataKeeper.setOfflineEnable(true);
					if(
//							OfflineDataKeeper.getOfflineData()==null||
							!new File(path).exists()){//
						JackUtils.showToast(getActivity(), "没有本地商铺数据，不能离线浏览");
						((ToggleButton)buttonView).setChecked(false);
						OfflineDataKeeper.setOfflineEnable(false);
					}
				}else{
					OfflineDataKeeper.setOfflineEnable(false);
					if(!JackUtils.isOpenNetwork()){
						JackUtils.showToast(getActivity(), "网络不可用，请检查您的网络");
						((ToggleButton)buttonView).setChecked(true);
						OfflineDataKeeper.setOfflineEnable(true);
					}
//					Log.i("person_toggleBtn", "check false");
				}
			}
		});
	}
	
}
