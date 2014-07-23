package com.qfc.yft.ui.tabs.main.cat;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;

import com.qfc.yft.R;

public class CatTopBarFrag extends CatAbsContentFragment implements OnClickListener {

	private View btnBack;
	private View btnSearch;
	private EditText mEdit;

	public final View getBtnBack() {
		return btnBack;
	}

	public final View getBtnSearch() {
		return btnSearch;
	}

	public String getEditContent() {
		return mEdit.getText().toString();
	}
	public EditText getEdit(){
		return mEdit;
	}

	@Override
	public int getLayoutRid() {
		return R.layout.layout_topblue;
	}
	
	@Override
	public void initView() {
		super.initView();
		btnBack = mView.findViewById(R.id.btn_top_back);
		btnSearch = mView.findViewById(R.id.tv_gosearch);
		mEdit = (EditText)mView.findViewById(R.id.edit_search);
		btnBack.setOnClickListener(this);
		btnSearch.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_top_back:
			getActivity().finish();//default
			break;
		default:
			break;
		}
		
	}

}
