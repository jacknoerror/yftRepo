package com.qfc.yft.ui.gallery;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import com.qfc.yft.R;

public class GFUpload extends JackAbsCompoundFragment {
	List<UploadPic> contentList;
	private UploadPicAdapter mAdapter;
	
	@Override
	public int getLayoutRid() {
		return R.layout.fragment_gf_upload;
	}

	@Override
	public void initView() {
		super.initView();
		mView.findViewById(R.id.layout_upload_choosealbum).setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
		});
		CheckBox checkbox_hd = (CheckBox)mView.findViewById(R.id.cb_upload);
		checkbox_hd.setOnCheckedChangeListener(null);//
		
		contentList = new ArrayList<UploadPic>();
		ListView listview_up = (ListView)mView.findViewById(R.id.lv_upload);
		mAdapter = new UploadPicAdapter();
		listview_up.setAdapter(mAdapter);

	}
	
	class UploadPic{
//		String bitm="";
		Bitmap bitmap;
		String desc="";
		public UploadPic(Bitmap bitmap) {
			super();
			this.bitmap = bitmap;
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
            holder.edit.setText(ssi.desc);
            
			return view;
		}
		
	}
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
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

}
