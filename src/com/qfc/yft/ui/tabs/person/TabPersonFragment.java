package com.qfc.yft.ui.tabs.person;

import java.io.File;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Environment;
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
import android.widget.TableLayout;
import android.widget.ToggleButton;

import com.qfc.yft.R;
import com.qfc.yft.YftApplication;
import com.qfc.yft.YftData;
import com.qfc.yft.YftValues;
import com.qfc.yft.entity.User;
import com.qfc.yft.net.OfflineDownloadBuilder;
import com.qfc.yft.net.chat.GIMSocketServer;
import com.qfc.yft.ui.tabs.ContentAbstractFragment;
import com.qfc.yft.ui.tabs.custom.BarData;
import com.qfc.yft.ui.tabs.custom.BarGroup;
import com.qfc.yft.utils.JackButtonColorFilter;
import com.qfc.yft.utils.JackUtils;

public class TabPersonFragment extends ContentAbstractFragment {
	final String TAG = TabPersonFragment.class.getSimpleName();
	
	LinearLayout tpLayout;
	ToggleButton tgBtn;
	Button logoutBtn;
	
	User mpUser;
	@Override
	public void initView() {
		mpUser = YftData.data().getMe();
		if(mpUser.getMemberType()==0){//�ο�
			logout();
			return;
		}
//		mView = mInflator.inflate(R.layout.fragment_person, null);
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
		
	}
	private void logout() {
		YftValues.logout();
		getActivity().finish();
	}
	final String[] DESC = new String[]{"�ҵ�����","�ҵ��ղ�","�ҵ�����","ͬ���ҵ�����","����ģʽ","��ձ�������","�ƶ����̽���","�ƶ�������ҵ�Ƽ�"};
	final int[] ICS = new int[]{R.drawable.person_myshop,R.drawable.person_mycollection,
			R.drawable.person_myfriends,R.drawable.person_refreshmyshop,R.drawable.person_offlinemodel,
			R.drawable.person_trash,R.drawable.person_moveshopintro,R.drawable.person_moveshoprecommanded
	};
	private void initTable() {
		if(mpUser==null) return;//critical error occurs
		int memberType = mpUser.getMemberType();
		String myName = mpUser.getRealName().isEmpty()?mpUser.getUserName():mpUser.getRealName();
//		int mySex = mpUser.get
		
		tpLayout = (LinearLayout)mView.findViewById(R.id.table_person);
		
		BarGroup bar1 = new BarGroup(getActivity());
		bar1.addBar(new BarData(R.drawable.person_icon,myName, null, null));//
		tpLayout.addView(bar1);
		
		BarGroup bar2 = new BarGroup(getActivity());
		if(memberType>1)bar2.addBar(new BarData(ICS[0], DESC[0], R.drawable.arrow, null));//�ҵ�����
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
		  builder.setMessage("ȷ��ɾ���������ݣ�");

		  builder.setTitle("��ʾ");

		  builder.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {

		   @Override
		   public void onClick(DialogInterface dialog, int which) {
		    dialog.dismiss();
		    deleteEverything();
		   }
		  });
		  
		  builder.setNegativeButton("ȡ��", new DialogInterface.OnClickListener() {

		   @Override
		   public void onClick(DialogInterface dialog, int which) {
		    dialog.dismiss();
		   }
		  });
		  
		  bgroup.setTag(builder); 
	}
	ProgressDialog oDialog;
	Handler oHandler;
	private void deleteEverything(){//��װ������
		initHandler();
		oDialog = JackUtils.showProgressDialog(getActivity(), "");
		new Thread(){
			@Override
			public void run() {
				String path = YftValues.getMyLocalPath();
				YftData.data().clearOffPref();//
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
					OfflineDownloadBuilder.clear();
					YftData.data().setOffline(null);
					JackUtils.showToast(getActivity(), "��������ɾ���ɹ�");
					if(tgBtn.isChecked()) {
						YftData.data().setOfflineEnable(false);tgBtn.setChecked(false);
					}
					break;
				case 1:
					JackUtils.showToast(getActivity(), "ɾ��ʧ��");//
					break;
				case 2:
					JackUtils.showToast(getActivity(), "�����˺�����ɾ���κ������ļ�"); 
					break;
				default:
					break;
				}
			}
		};
	}
	

	private void initUpdateSets(View BarView){
		OfflineDownloadBuilder.setBtns(
							(ProgressBar)BarView.findViewById(R.id.progress_offline_person), 
							(ImageView)BarView.findViewById(R.id.img_item_person_right));
		OfflineDownloadBuilder.setContext(getActivity());
	}
	private void initToggleBtn(View barView) {
		tgBtn = (ToggleButton)barView.findViewById(R.id.toggle_offline_person);
		barView.findViewById(R.id.img_item_person_right).setVisibility(View.GONE);
		tgBtn.setVisibility(View.VISIBLE);
		tgBtn.setChecked(YftData.data().isOfflineEnabled());
		tgBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if(isChecked){
					String path = YftValues.getMyLocalPath();
					YftData.data().setOfflineEnable(true);
					if(YftData.data().getOfflineData()==null||!new File(path).exists()){//
						JackUtils.showToast(getActivity(), "û�б����������ݣ������������");
						((ToggleButton)buttonView).setChecked(false);
						YftData.data().setOfflineEnable(false);
					}
				}else{
					YftData.data().setOfflineEnable(false);
					if(!JackUtils.isOpenNetwork()){
						JackUtils.showToast(getActivity(), "���粻���ã�������������");
						((ToggleButton)buttonView).setChecked(true);
						YftData.data().setOfflineEnable(true);
					}
					Log.i("person_toggleBtn", "check false");
				}
			}
		});
	}
	
}
