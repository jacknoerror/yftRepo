package com.qfc.yft.ui.tab.work;

import android.view.View;
import android.view.View.OnClickListener;

import com.qfc.yft.R;
import com.qfc.yft.data.Const;
import com.qfc.yft.ui.MyPortal;
import com.qfc.yft.ui.tab.ContentAbstractFragment;

public class TabWorkFragment extends ContentAbstractFragment implements OnClickListener {

	@Override
	public void initView() {
		initTitleManager();
		titleManager.setTitleName(getString(R.string.titlename_work));
		mView.findViewById(R.id.tvbtn_work_x1).setOnClickListener(this);
		mView.findViewById(R.id.tvbtn_work_x2).setOnClickListener(this);
		mView.findViewById(R.id.tvbtn_work_x3).setOnClickListener(this);
		mView.findViewById(R.id.tvbtn_work_y1).setOnClickListener(this);
		mView.findViewById(R.id.tvbtn_work_y2).setOnClickListener(this);
		mView.findViewById(R.id.tvbtn_work_y3).setOnClickListener(this);
	}

	@Override
	public int getLayoutRid() {
		return R.layout.fragment_work;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tvbtn_work_x1://��˾���
			MyPortal.goAlbumShList(getActivity(), 0);
			break;
		case R.id.tvbtn_work_x2://����
			MyPortal.goAlbumShList(getActivity(), Const.BS_GO_PHOTO);
			break;
		case R.id.tvbtn_work_x3://�ϴ�
			MyPortal.goAlbumShList(getActivity(), Const.BS_GO_LOCAL);
			break;
		case R.id.tvbtn_work_y1://��Ʒ����
			MyPortal.goProductManage(getActivity());
			break;
		case R.id.tvbtn_work_y2://���۶���
			
			break;
		case R.id.tvbtn_work_y3://�ɹ�����
			
			break;

		default:
			break;
		}
		
	}

}
