package com.qfc.yft.ui.gallery;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.ContentResolver;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.qfc.yft.R;
import com.qfc.yft.data.Const;
import com.qfc.yft.data.MyData;
import com.qfc.yft.data.NetConst;
import com.qfc.yft.net.ExpandableRequestTask;
import com.qfc.yft.net.action.ActionBuilder;
import com.qfc.yft.net.action.ActionReceiverImpl;
import com.qfc.yft.net.action.ActionRequestImpl;
import com.qfc.yft.net.action.album.DelAlbumPicReq;
import com.qfc.yft.net.action.album.SearchPIcsByAlbumIdRcv;
import com.qfc.yft.net.action.album.SearchPicsByAlbumIdReq;
import com.qfc.yft.ui.ImageLoaderHelper;
import com.qfc.yft.ui.ImageLoaderHelper.DisplayOptionType;
import com.qfc.yft.util.JackButtonColorFilter;
import com.qfc.yft.util.JackUtils;
import com.qfc.yft.vo.AlbumPic;
import com.qfc.yft.vo.User;

public class GFGrids extends CompoundRadiosFragment implements OnScrollListener ,View.OnClickListener{

	private GridView mGridView;
	GfgAdapter gAdapter;
	
	Map<Integer,String> selectedUrl;
//	Set<String> selectedUrl;
	private int albumId;
	private TextView tvCounter;
	private final int MAX_UPLOAD_COUNT=12;
	
	@Override
	public int getLayoutRid() {
		return R.layout.fragment_gf_grids;
	}

	@Override
	public void initView() {
		super.initView();
		Bundle arguments = getArguments();
		if (null == arguments)
			return;//
		albumId = arguments.getInt(NetConst.EXTRAS_GRIDALBUMID);
		String b = arguments.getString(NetConst.EXTRAS_GRIDALBUMNAME);
		mCompoundTitleManager.setTitleName(b);

		mGridView = (GridView) mView.findViewById(R.id.gridview_gf);
		mGridView.setOnScrollListener(this);
		View bottomView = mView.findViewById(R.id.layout_gf_grid_bottom);
		Button btnConfirm = (Button) mView.findViewById(R.id.btn_gf_grid);
		JackButtonColorFilter.setButtonFocusChanged(btnConfirm);
		btnConfirm.setOnClickListener(this);
		tvCounter = (TextView)mView.findViewById(R.id.tv_gf_grid_count);

		selectedUrl = new HashMap<Integer,String>();
		if (albumId == 0) {//本地，仅选择
			mRadioGroup.setVisibility(View.GONE);
			mCompoundTitleManager.initTitleBack();// FIXME

			ArrayList<AlbumPic> photos = getPhotos(b);
			initData(photos);
		} else {
			bottomView.setVisibility(View.GONE);
			mCompoundTitleManager.setRightText("删除", this);

			ActionRequestImpl actReq = new SearchPicsByAlbumIdReq(MyData.data().getMe().getShopId(), albumId, 1);
			ActionReceiverImpl actRcv = new SearchPIcsByAlbumIdRcv(getActivity()){
				public boolean response(String result) throws org.json.JSONException {
					boolean response = super.response(result);
					if(response){
						initData(dataList);
					}
					return response;
				};
			};
			ActionBuilder.getInstance().request(actReq , actRcv);
		}

	}

	protected void addSelected() {
		Fragment fragment = mCompoundFragmentManager.findFragmentByTag(GFUpload.class.getSimpleName());
		if(null==fragment)return;
		Bundle args = fragment.getArguments();
		args.putStringArray(NetConst.EXTRAS_LOCAL2UPLOAD, selectedUrl.values().toArray(new String[]{}));
//		mCompoundFragmentManager.popBackStack();
//		mCompoundFragmentManager.popBackStack();
		mCompoundFragmentManager.popBackStack(GFUpload.class.getSimpleName(), FragmentManager.POP_BACK_STACK_INCLUSIVE);
	}

	/**
	 * @param photos
	 */
	protected void initData(List photos) {
		gAdapter = new GfgAdapter(photos);
		mGridView.setAdapter(gAdapter);
		
		mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
//					view.setSelected(!view.isSelected);
				
				boolean selecting = selectedUrl.get(position)==null;
				if(albumId==0&&selectedUrl.size()>=MAX_UPLOAD_COUNT&&selecting) {//判断是否超了
					JackUtils.showToast(getActivity(), "一次最多上传"+MAX_UPLOAD_COUNT+"张图片");
					return;
				}
				view.findViewById(R.id.layout_item_grids).setVisibility(selecting?View.VISIBLE:View.INVISIBLE);
//					mGridView.setItemChecked(position, !mGridView.isItemChecked(position));//min 11
					//对选中的图片进行记录
				AlbumPic albumPic = gAdapter.contentList.get(position);
				if(selecting){
					selectedUrl.put(albumPic.getPicId(),albumPic.getPicOriginNameCode());
				}else{
					selectedUrl.remove(position);
				}
				//对数量进行记录
				int size = selectedUrl.size();
				tvCounter.setText(String.format("%d/%d", size,MAX_UPLOAD_COUNT));
				tvCounter.setVisibility(size==0||albumId>0?View.INVISIBLE:View.VISIBLE);//aid>0时 是在删图片,不受个数限制
				
			}
			
		});
	}

	/**
	 * item = String; use specific rcv to convert to AlbumPic
	 * @author taotao
	 * @Date 2014-6-12
	 */
	class GfgAdapter extends BaseAdapter {
		List<AlbumPic> contentList;
		SparseArray<View> viewMap;

		public GfgAdapter(List contentList) {
			super();
			if (null == contentList)
				contentList = new ArrayList<AlbumPic>();
			this.contentList = contentList;
			viewMap = new SparseArray<View>();
		}

		@Override
		public int getCount() {
			return contentList.size();
		}

		@Override
		public AlbumPic getItem(int position) {
			return contentList.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View view = viewMap.get(position);
			GfgViewHolder viewHolder = null;
			if (null == view) {
				view = LayoutInflater.from(getActivity()).inflate(
						R.layout.item_gf_grids, null);
				viewHolder = new GfgViewHolder();
				viewHolder.img = (ImageView) view
						.findViewById(R.id.img_item_grids);
				view.setTag(viewHolder);
				viewMap.put(position, view);
			} else {
				viewHolder = (GfgViewHolder) view.getTag();
			}
			ImageLoaderHelper.imageLoader
					.displayImage(contentList.get(position).getPicOriginNameCode(), viewHolder.img,
							ImageLoaderHelper
									.getDisplayOpts(DisplayOptionType.DEFAULT));
			int side = Const.SCREEN_WIDTH/3-JackUtils.dip2px(getActivity(), 5);
			view.setLayoutParams(new AbsListView.LayoutParams(
					side, side));
//			viewHolder.img.setLayoutParams(new RelativeLayout.LayoutParams(
//					side, side));
			return view;
		}

	}

	class GfgViewHolder {
		ImageView img;
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
	}

	private void addPaddingTop(View v, int top) {
		if (null == v)
			return;
		final int margin = JackUtils.dip2px(getActivity(), 5);
		v.setPadding(margin, margin + top, margin, margin);
	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		if (null != mRadioGroup&&albumId>0) {

			if (firstVisibleItem > 1) {
				mRadioGroup.setVisibility(View.GONE);
				addPaddingTop(mGridView, 0);
			} else {
				mRadioGroup.setVisibility(View.VISIBLE);
				int measuredHeight = mRadioGroup.getMeasuredHeight();
//				if (0 != measuredHeight)
					addPaddingTop(mGridView, measuredHeight);

			}
		}

	}

	/**
	 * @param album_dir
	 * @return
	 */
	public ArrayList<AlbumPic> getPhotos(String album_dir) {
		ArrayList<AlbumPic> photos = new ArrayList<AlbumPic>();
		ContentResolver contentResolver = getActivity().getContentResolver();
		String[] projection = new String[] { MediaStore.Images.Media.DATA };
		Cursor cursor = contentResolver.query(
				MediaStore.Images.Media.EXTERNAL_CONTENT_URI, projection, null,
				null, MediaStore.Images.Media.DEFAULT_SORT_ORDER);
		cursor.moveToFirst();
		int fileNum = cursor.getCount();

		for (int counter = 0; counter < fileNum; counter++) {
			Log.w("tag","---file is:"	+ cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DATA)));
			String path = cursor.getString(cursor
					.getColumnIndex(MediaStore.Video.Media.DATA));
			// 获取路径中文件的目录
			String file_dir = JackUtils.getDir(path);
			if (!path.startsWith("file://"))
				path = "file://" + path;// XXX 0610
			if (file_dir.equals(album_dir)){
				AlbumPic ap = new AlbumPic();
				ap.setPicOriginNameCode(path);
				photos.add(ap);
			}
			cursor.moveToNext();
		}
		cursor.close();

		return photos;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_gf_grid:
			addSelected();
			break;
		case R.id.tv_title_right:
			User me = MyData.data().getMe();
			if(null==me) return;
			Object[] array =   selectedUrl.keySet().toArray();
//			Integer[] array = selectedUrl.keySet().toArray(new Integer[]{});
			
			new ExpandableRequestTask(new DelAlbumPicReq(me.getShopId(), albumId, ""), getActivity(), "删除%d张照片").execute(array);
			break;
		default:
			break;
		}
	}

}
