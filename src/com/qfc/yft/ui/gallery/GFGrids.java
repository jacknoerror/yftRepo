package com.qfc.yft.ui.gallery;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.ContentResolver;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
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

import com.qfc.yft.R;
import com.qfc.yft.data.Const;
import com.qfc.yft.ui.ImageLoaderHelper;
import com.qfc.yft.ui.ImageLoaderHelper.DisplayOptionType;
import com.qfc.yft.util.JackUtils;

public class GFGrids extends CompoundRadiosFragment implements OnScrollListener {

	public static final String EXTRAS_GRIDALBUMNAME = "gridalbumname";
	public static final String EXTRAS_GRIDALBUMID = "gridalbumid";
	private GridView mGridView;
	GfgAdapter gAdapter;
	
	SparseArray<String> selectedUrl;
//	Set<String> selectedUrl;
	private int albumId;
	
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
		albumId = arguments.getInt(EXTRAS_GRIDALBUMID);
		String b = arguments.getString(EXTRAS_GRIDALBUMNAME);
		mCompoundTitleManager.setTitleName(b);

		mGridView = (GridView) mView.findViewById(R.id.gridview_gf);
		mGridView.setOnScrollListener(this);
		View bottomView = mView.findViewById(R.id.layout_gf_grid_bottom);
		Button btnConfirm = (Button) mView.findViewById(R.id.btn_gf_grid);

		if (albumId == 0) {//本地，仅选择
			mRadioGroup.setVisibility(View.GONE);
			mCompoundTitleManager.setRightText("删除", null);
			mCompoundTitleManager.initTitleBack();// FIXME

			ArrayList<String> photos = getPhotos(b);
			gAdapter = new GfgAdapter(photos);
			mGridView.setAdapter(gAdapter);
			
			mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
//					view.setSelected(!view.isSelected);
					boolean selected = selectedUrl.get(position)==null;
					view.findViewById(R.id.layout_item_grids).setVisibility(selected?View.VISIBLE:View.INVISIBLE);
//					mGridView.setItemChecked(position, !mGridView.isItemChecked(position));//min 11
					String url = gAdapter.contentList.get(position);
					if(selected){
						selectedUrl.put(position,url);
					}else{
						selectedUrl.remove(position);
					}
				}
				
			});
			selectedUrl = new SparseArray<String>();
		} else {
			bottomView.setVisibility(View.GONE);

		}

	}

	class GfgAdapter extends BaseAdapter {
		List<String> contentList;
		SparseArray<View> viewMap;

		public GfgAdapter(List<String> contentList) {
			super();
			if (null == contentList)
				contentList = new ArrayList<String>();
			this.contentList = contentList;
			viewMap = new SparseArray<View>();
		}

		@Override
		public int getCount() {
			return contentList.size();
		}

		@Override
		public Object getItem(int position) {
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
					.displayImage(contentList.get(position), viewHolder.img,
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
	public ArrayList<String> getPhotos(String album_dir) {
		ArrayList<String> photos = new ArrayList<String>();
		ContentResolver contentResolver = getActivity().getContentResolver();
		String[] projection = new String[] { MediaStore.Images.Media.DATA };
		Cursor cursor = contentResolver.query(
				MediaStore.Images.Media.EXTERNAL_CONTENT_URI, projection, null,
				null, MediaStore.Images.Media.DEFAULT_SORT_ORDER);
		cursor.moveToFirst();
		int fileNum = cursor.getCount();

		for (int counter = 0; counter < fileNum; counter++) {
			Log.w("tag",
					"---file is:"
							+ cursor.getString(cursor
									.getColumnIndex(MediaStore.Video.Media.DATA)));
			String path = cursor.getString(cursor
					.getColumnIndex(MediaStore.Video.Media.DATA));
			// 获取路径中文件的目录
			String file_dir = JackUtils.getDir(path);
			if (!path.startsWith("file://"))
				path = "file://" + path;// XXX 0610
			if (file_dir.equals(album_dir))
				photos.add(path);
			cursor.moveToNext();
		}
		cursor.close();

		return photos;
	}

}
