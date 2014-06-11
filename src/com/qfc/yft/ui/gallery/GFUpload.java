package com.qfc.yft.ui.gallery;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.qfc.yft.R;
import com.qfc.yft.data.Const;
import com.qfc.yft.data.NetConst;
import com.qfc.yft.ui.ImageLoaderHelper;
import com.qfc.yft.ui.MyPortal;

public class GFUpload extends JackAbsCompoundFragment implements View.OnClickListener{
	private static final String EXTRAS_LOCAL2UPLOAD = "local2upload";
	List<UploadPic> contentList;
	private UploadPicAdapter mAdapter;
	private TextView tv_toAlbum;
	private int id_toAlbum;
	
	@Override
	protected void handleTitle() {
		super.handleTitle();
		mCompoundTitleManager.setTitleName(getString(R.string.titlename_upload));
		mCompoundTitleManager.setRightText("取消", this);
		mCompoundTitleManager.setLeftText("上传", this);
	}
	
	@Override
	public void onHiddenChanged(boolean hidden) {
		if(!hidden){
			Bundle arguments = getArguments();
			//新加图片
			String[] selectedLocPicPaths = arguments.getStringArray(EXTRAS_LOCAL2UPLOAD);
			if(null!=selectedLocPicPaths&&selectedLocPicPaths.length>0){
				for(String p:selectedLocPicPaths){
					contentList.add(new UploadPic(p));
				}
				mAdapter.notifyDataSetChanged();
			}
			
			//上传到
			int toAlbumId = arguments.getInt("toalbumid");
			String toAlbumName=arguments.getString("toalbumname");
			if(toAlbumId>0&&toAlbumName!=null){
				tv_toAlbum.setText(toAlbumName);
				id_toAlbum = toAlbumId;
			}
			else{
				id_toAlbum = 0;
			}
		}
		super.onHiddenChanged(hidden);
	}
	
	@Override
	public int getLayoutRid() {
		return R.layout.fragment_gf_upload;
	}

	@Override
	public void initView() {
		super.initView();
		
		Bundle arguments = getArguments();
		if(null!=arguments){
			handleInitParams(arguments);
		}
		
		mView.findViewById(R.id.layout_upload_choosealbum).setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
		});
		tv_toAlbum = (TextView)mView.findViewById(R.id.tv_upload_toalbum);
		
		CheckBox checkbox_hd = (CheckBox)mView.findViewById(R.id.cb_upload);
		checkbox_hd.setOnCheckedChangeListener(null);//
		
		contentList = new ArrayList<UploadPic>();
		ListView listview_up = (ListView)mView.findViewById(R.id.lv_upload);
		View footer = mInflator.inflate(R.layout.footer_upload, null);
		footer.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
		});
		listview_up.addFooterView(footer);
		mAdapter = new UploadPicAdapter();
		listview_up.setAdapter(mAdapter);
	}

	/**
	 * @param arguments
	 */
	protected void handleInitParams(Bundle arguments) {
		int a = arguments.getInt(NetConst.EXTRAS_UPLOADACTION);
		Fragment fragment = null;
		switch (a) {
		case Const.BS_GO_PHOTO:
			MyPortal.goCamera(getActivity());
			break;
		case Const.BS_GO_LOCAL:
			fragment = mCompoundFragmentManager.findFragmentByTag(GFSecondLocal.class.getSimpleName());
//			if(null==fragment) fragment = new GFSecondLocal();
//			mCompoundFragmentManager.beginTransaction().replace(R.id.frame_common, fragment).addToBackStack(getClass().getSimpleName()).commit();
			FragmentTransaction beginTransaction = mCompoundFragmentManager.beginTransaction();
			beginTransaction.hide(this);
			if(null==fragment) 
				beginTransaction.add(R.id.frame_common,fragment = new GFSecondLocal());
			else	
				beginTransaction.show(fragment);
			beginTransaction.addToBackStack(getClass().getSimpleName()).commit();
//			replaceFragment(fragment, false, getClass().getSimpleName());
			break;
		default:
			break;
		}
	}
	
	class UploadPic{
		Bitmap bitmap;
//		String desc="";
		public String uri;
		public UploadPic(Bitmap bitmap) {
			super();
			this.bitmap = bitmap;
		}
		public UploadPic(String uri) {
			super();
			this.uri = uri;
		}
		
		
		
	}
	class ViewHolder{
		ImageView img;
		EditText edit;
	}
	class UploadPicAdapter extends BaseAdapter{

		SparseArray<View> viewMap ;  
		public UploadPicAdapter(){
			viewMap = new SparseArray<View>();
		}
		
		@Override
		public int getCount() {
			return contentList.size();
		}

		@Override
		public UploadPic getItem(int position) {
			return contentList.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View view = viewMap.get(position);
			ViewHolder holder = new ViewHolder();
			
			if(view==null){
				view = LayoutInflater.from(getActivity()).inflate(R.layout.item_upload, null);
				   
                holder = new ViewHolder();
                holder.img = (ImageView)view.findViewById(R.id.img_item_upload);
                holder.edit = (EditText)view.findViewById(R.id.et_item_upload);
                //TODO record edit
   
                viewMap.put(position, view);
   
                view.setTag(holder);
			}else{
				view = viewMap.get(position);
                holder = (ViewHolder)view.getTag();
            }
            

            //哟啊不要换地方？
			UploadPic ssi = contentList.get(position);
//            JackImageLoader.justSetMeImage(ssi.shopLogoImage, holder.icimg);
//			Bitmap bm = JackUtils.getbmFromAssetsFile(getResources(), ssi.imgPath);
			if(null!=ssi.bitmap)holder.img.setImageBitmap(ssi.bitmap);
			else ImageLoaderHelper.imageLoader.displayImage(ssi.uri, holder.img);
//            holder.edit.setText(ssi.desc);
            
			return view;
		}
		
	}
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(resultCode==Activity.RESULT_OK){
			
			Bundle bundle = data.getExtras();
			// Uri camareUri = (Uri) bundle.get(MediaStore.EXTRA_OUTPUT);
			Bitmap camerabmp = (Bitmap) bundle.get("data");
	//		ivIcon.setImageBitmap(camerabmp);
			contentList.add(new UploadPic(camerabmp));
			mAdapter.notifyDataSetChanged();
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv_title_right:
			mCompoundFragmentManager.popBackStack();
			break;

		default:
			break;
		}
		
	}

}
