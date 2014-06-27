package com.qfc.yft.ui.custom.list;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.qfc.yft.R;
import com.qfc.yft.net.action.ActionReceiverImpl;
import com.qfc.yft.net.action.ActionStrategies;
import com.qfc.yft.ui.adapter.mj.ListAdapterImagine.ImagineItemInfo;

public class MyJackListView extends ListView implements
		AbsListView.OnScrollListener, ActionReceiverImpl {
	private static final String HINT_DONE = "";

	private static final String HINT_PULLTOREFRESH = "����ˢ��";

	final String TAG = getClass().getSimpleName();

	List<ListItemImpl> everythingList;
	MspFactoryImpl factory;

	MspAdapter myAdapter;
	MspPage currentPage;
	OnGetPageListener gpListener;

	View moreView;
	// header
	LinearLayout mHeaderLinearLayout;
	private TextView mHeaderTextView;
	ProgressBar mHeaderProgressBar;
	private int mHeaderHeight;

	private int mCurrentScrollState;

	int requestingPage;
	boolean isSetup;
	ListItemImpl.Type mType;

	private LayoutInflater mInflater;

	public interface OnGetPageListener {
		public void page(MyJackListView qListView, int pageNo);
	}

	public MyJackListView(Context context, ListItemImpl.Type type) {
		super(context);
		mType = type;
		init();
	}

	private void init() {
		mInflater = LayoutInflater.from(getContext());

		factory = new MspFactory(mType);
		myAdapter = factory.getNewAdapter();
		if (null != myAdapter)
			myAdapter.setListView(this);

		setOnGetPageListener(factory.getDefaultOnPageChangeListener());

		// setOnItemClickListener(this);
		setOnScrollListener(this);
		setFastScrollEnabled(true);
		everythingList = new ArrayList<ListItemImpl>();

		// initHeader();//
		// setFooterDividersEnabled(false);
		setSelector(R.drawable.selector_field_white_grey); //
		setDivider(getResources().getDrawable(R.color.textcolor_light));
		setDividerHeight(2);

	}

	public final MspPage getCurrentPage() {
		return currentPage;
	}

	public final ListItemImpl.Type getType() {
		return mType;
	}

	/**
	 * 
	 */
	public void initHeader() {
		addHeaderView();
		measureView(mHeaderLinearLayout);
		mHeaderHeight = mHeaderLinearLayout.getMeasuredHeight();
		setHeaderTopPadding(-mHeaderHeight);// 0523 set minus header padding
		setHeaderDividersEnabled(false);
	}

	@Override
	public void setAdapter(ListAdapter adapter) {
		super.setAdapter(adapter);
		if (adapter instanceof MspAdapter)
			myAdapter = (MspAdapter) adapter;
	}

	public void updateList(List<ListItemImpl> list) {
		if (null == getAdapter() && null != myAdapter) {
			setAdapter(myAdapter);
			if (null != mHeaderLinearLayout)
				setSelection(1);// do this after// setAdapter// everytime//
								// 0617why everytime & move
		}
		// ���ڴ˴����ڸ������ݵ�ʱ��������adapter
		if (myAdapter != null) {
			List<ListItemImpl> lvList = ((MspAdapter) myAdapter).getList();
			if (lvList != list) {
				lvList.clear();
				lvList.addAll(list);
			}
		}
		myAdapter.notifyDataSetChanged();

	}

	public final boolean isSetup() {
		return isSetup;
	}

	/**
	 * ��������ʹ�ÿؼ�ǰ���ô˷���
	 */
	public void setup() {

		addMoreView();// ��ʼʱ���� ������Ҫʱ���� �� ע��λ�ã�С�ı���
		if (gpListener != null) {// ��ʼ������
			requestingPage = 1;// 0227
			gpListener.page(this, 1);
			if (everythingList != null)
				everythingList.clear();// reset
		}

		isSetup = true;
	}

	/**
	 * ��Ϊ���ڹ��캯��������߶ȣ�Ӧ����measureһ��
	 * 
	 * @param child
	 */
	private void measureView(View child) {
		ViewGroup.LayoutParams p = child.getLayoutParams();
		if (p == null) {
			p = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT,
					ViewGroup.LayoutParams.WRAP_CONTENT);
		}

		int childWidthSpec = ViewGroup.getChildMeasureSpec(0, 0 + 0, p.width);
		int lpHeight = p.height;
		int childHeightSpec;
		if (lpHeight > 0) {
			childHeightSpec = MeasureSpec.makeMeasureSpec(lpHeight,
					MeasureSpec.EXACTLY);
		} else {
			childHeightSpec = MeasureSpec.makeMeasureSpec(0,
					MeasureSpec.UNSPECIFIED);
		}
		child.measure(childWidthSpec, childHeightSpec);
	}

	/**
	 * 
	 */
	private void addHeaderView() {
		if (null == mHeaderLinearLayout) {
			mHeaderLinearLayout = (LinearLayout) mInflater.inflate(
					R.layout.header_mylist, null);
			mHeaderTextView = (TextView) mHeaderLinearLayout
					.findViewById(R.id.tv_header);
			mHeaderProgressBar = (ProgressBar) mHeaderLinearLayout
					.findViewById(R.id.progress_header);
			addHeaderView(mHeaderLinearLayout);
		}
	}

	private void addMoreView() {
		// footer //0224 ����if���ֹ������ʱmoreView��bug //0226 extract handle
		// visibility
		if (null == moreView) {
			if (isSetup && iS_TOTAL_TOO_FEW)
				return; // 0523 when few, no show. usually have to do this when
						// "nextpage" results in errors
			moreView = mInflater.inflate(R.layout.footer_more, null);
			addFooterView(moreView);
		} else {// never happen

			moreView.setVisibility(View.VISIBLE);
		}
	}

	private void removeMoreView() {
		if (null != moreView) {
			removeFooterView(moreView);
			moreView = null;
		}
	}

	private void nextPage() {
		if (null == currentPage)
			return;// ����Ҫ��ҳ
		int requestpage = currentPage.curPageNo + 1;
		if (null == gpListener || requestingPage == requestpage
				|| !currentPage.hasNext)
			return;
		requestingPage = requestpage;
		gpListener.page(this, requestpage);
	}

	/**
	 * ��������Ĳ�������˴�
	 * 
	 * @param gpListener
	 */
	public void setOnGetPageListener(OnGetPageListener gpListener) {
		this.gpListener = gpListener;
	}

	@Override
	public boolean response(String result) throws JSONException {
		// ���ͷ��ˢ����
		if (mPullRefreshState == EXIT_PULL_REFRESH) {
			// mPullRefreshState = NONE_PULL_REFRESH;
			Message msg = mHandler.obtainMessage();
			msg.what = REFRESH_DONE;
			// ֪ͨ���̼߳����������
			mHandler.sendMessage(msg);
		}
		switch (mType) {
		case IP_IMAGINE:// {"data":[{"��������":0}]}}
			JSONObject job = ActionStrategies.getResultObject(result);
			if (null != job) {
				JSONArray jarr = job.getJSONArray("data");
				// if(null==everythingList) everythingList = new
				// ArrayList<ListItemImpl>();
				everythingList.clear();
				for (int i = 0; i < jarr.length(); i++) {
					ImagineItemInfo iii = new ImagineItemInfo();
					String singleResult = jarr.getString(i);
					if (!singleResult.contains(":"))
						continue;
					String[] srPats = singleResult.split(":");
					iii.iii_name = srPats[0].replace('{', ' ').replace('\"',' ');
					iii.iii_count = srPats[1].replace('}', ' ');
					everythingList.add(iii);
				}
				updateList(everythingList);
			}
			break;
		default:
			currentPage = new MspPage(factory);
			if (!result.isEmpty()) {// ȷ����pages
				currentPage.initJackJson(ActionStrategies
						.getResultObject(result));
				// curP status ok?
				currentPage.curPageNo = requestingPage;
				everythingList.addAll(currentPage.getDataList());
				updateList(everythingList);
			}

			if (null == currentPage || !currentPage.hasNext) {
				removeMoreView();
				Log.i(TAG, "no Next");
			} else {
			}
			break;
		}
		return false;
	}

	@Override
	public Context getReceiverContext() {
		if(mType==ListItemImpl.Type.IP_IMAGINE){
			return null;
		}
		return (requestingPage > 1 || mPullRefreshState == EXIT_PULL_REFRESH) ? null
				: getContext();// 0512
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		mCurrentScrollState = scrollState;
		if (scrollState == SCROLL_STATE_IDLE) {
			// invalidateViews();
		}
		;
	}

	private final static int NONE_PULL_REFRESH = 0; // ����״̬
	private final static int ENTER_PULL_REFRESH = 1; // ��������ˢ��״̬
	private final static int OVER_PULL_REFRESH = 2; // ��������ˢ��״̬
	private final static int EXIT_PULL_REFRESH = 3; // ���ֺ󷴵������״̬
	private int mPullRefreshState = 0; // ��¼ˢ��״̬

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		// if(null!=mHeaderLinearLayout)Log.i(TAG,
		// "b:"+mHeaderLinearLayout.getBottom()+"-"+mHeaderHeight+"touch"+SCROLL_STATE_TOUCH_SCROLL+":"+mCurrentScrollState);
		iS_TOTAL_TOO_FEW = visibleItemCount >= totalItemCount;

		if (null != mHeaderLinearLayout) {

			// header
			if (mCurrentScrollState == SCROLL_STATE_TOUCH_SCROLL
					&& firstVisibleItem == 0
					&& (mHeaderLinearLayout.getBottom() >= 0 && mHeaderLinearLayout
							.getBottom() < mHeaderHeight)) {
				// �����ҽ���������ˢ��״̬
				if (mPullRefreshState == NONE_PULL_REFRESH) {
					mPullRefreshState = ENTER_PULL_REFRESH;
					Log.i(TAG, "in");
				}
			} else if (mCurrentScrollState == SCROLL_STATE_TOUCH_SCROLL
					&& firstVisibleItem == 0
					&& (mHeaderLinearLayout.getBottom() >= mHeaderHeight)) {
				// �����ﵽ���ޣ���������ˢ��״̬
				if (mPullRefreshState == ENTER_PULL_REFRESH
						|| mPullRefreshState == NONE_PULL_REFRESH) {
					mPullRefreshState = OVER_PULL_REFRESH;
					// �����ǽ�������ˢ��״̬��Ҫ����һ����ʾ�ı�
					mDownY = mMoveY;// ���ں������������Ч��
					mHeaderTextView.setText("�ɿ�ˢ��");
					/*
					 * mHeaderPullDownImageView.setVisibility(View.GONE);
					 * mHeaderReleaseDownImageView.setVisibility(View.VISIBLE);
					 */
				}
			} else if (mCurrentScrollState == SCROLL_STATE_TOUCH_SCROLL
					&& firstVisibleItem != 0) {
				// ��ˢ����
				if (mPullRefreshState == ENTER_PULL_REFRESH) {
					mPullRefreshState = NONE_PULL_REFRESH;
				}
			} else if (mCurrentScrollState == SCROLL_STATE_FLING) {
				// &&firstVisibleItem == 0 ) {
				// �ɻ�״̬��������ʾ��header��Ҳ����Ӱ�������ķɻ�
				// ֻ����������²ž���λ��
				// Log.i("NOW", "line 315");
				if (firstVisibleItem == 0) {
					if (mPullRefreshState == NONE_PULL_REFRESH) {
						/*
						 * MotionEvent mEvent = MotionEvent.obtain(
						 * SystemClock.uptimeMillis(),
						 * SystemClock.uptimeMillis(),
						 * MotionEvent.ACTION_CANCEL, 0, 0, 0);
						 * dispatchTouchEvent(mEvent);// mEvent.recycle();
						 * setSelection(1);
						 */
						setHeaderTopPadding(-mHeaderHeight);

					}
				}
			}
		}
		// if(null!=mHeaderLinearLayout)Log.i("NOW",
		// mHeaderLinearLayout.getTop()+":top");

		// footer
		if (myAdapter == null || myAdapter.getCount() == 0)
			return;
		int count = myAdapter.getCount();
		int lastItem = firstVisibleItem
				+ visibleItemCount
				- (mHeaderLinearLayout != null ? 1 : 0)
				- (moreView != null && moreView.getVisibility() == View.VISIBLE ? 1
						: 0);//  notice,test
		if (lastItem == count) {
			nextPage(); // ���ظ������ݣ��������ʹ���첽����
		}
	}

	private float mDownY;
	private float mMoveY;

	/*
	 * ���⴦����header��ȫ��ʾ������ֻ������1/3�ľ������������û�һ�ּ��������������ֵĵ��ɸо���
	 * �����onTouchEvent�ﴦ��ȽϷ���
	 * 
	 * @see android.widget.AbsListView#onTouchEvent(android.view.MotionEvent)
	 */
	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		switch (ev.getAction()) {
		case MotionEvent.ACTION_DOWN:
			// ���°���λ��
			// �ı�
			mDownY = ev.getY();
			// if(!iS_TOTAL_TOO_FEW) setHeaderTopPadding(0);//0523 abort minus
			// header padding // 0617 why minus?
			break;
		case MotionEvent.ACTION_MOVE:
			// Log.i(TAG, "ACTION_MOVE:mPullRefreshState:"+mPullRefreshState);
			// Log.i(TAG, "mCurrentScrollState:"+mCurrentScrollState);
			mMoveY = ev.getY();

			int padTop = (int) ((mMoveY - mDownY) / 3);// 1/3�����ۿ�
			// �ƶ�ʱ��ָ��λ��
			if (padTop > 0) {
			}
			if(null==mHeaderTextView)break;
			// Log.i(TAG, "mPullRefreshState"+mPullRefreshState);
			if (mPullRefreshState == OVER_PULL_REFRESH) {
				setHeaderTopPadding(padTop);
				mHeaderTextView.setText(HINT_PULLTOREFRESH);
			}
			//
			if (iS_TOTAL_TOO_FEW && mCurrentScrollState == 1 && mMoveY > mDownY) {// 0515
				setHeaderTopPadding(padTop);
				if (mPullRefreshState == NONE_PULL_REFRESH) {// ÿ�ػ�������һ��
					mPullRefreshState = OVER_PULL_REFRESH;
					mHeaderTextView.setText(HINT_PULLTOREFRESH);
				}
				mHeaderTextView.setText(HINT_PULLTOREFRESH);
			}
			break;
		case MotionEvent.ACTION_UP:
			// when you action up, it will do these:
			// 1. roll back util header topPadding is 0
			// 2. hide the header by setSelection(1)
			if (mPullRefreshState == OVER_PULL_REFRESH
					|| mPullRefreshState == ENTER_PULL_REFRESH) {
				new Thread() {
					@Override
					public void run() {
						Message msg;
						while (mHeaderLinearLayout.getPaddingTop() > 1) {
							msg = mHandler.obtainMessage();
							msg.what = REFRESH_BACKING;
							mHandler.sendMessage(msg);
							try {
								sleep(5);// ��һ�㷴������һ���Ӿ͵���ȥ��
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						}
						msg = mHandler.obtainMessage();
						if (mPullRefreshState == OVER_PULL_REFRESH) {
							msg.what = REFRESH_BACED;// ����������ɣ���������
						} else {
							msg.what = REFRESH_RETURN;// δ�ﵽˢ�½��ޣ�ֱ�ӷ���
						}
						mHandler.sendMessage(msg);
					};
				}.start();
			}
			break;
		}
		return super.onTouchEvent(ev);
	}

	/**
	 * @param padTop
	 */
	private void setHeaderTopPadding(int padTop) {
		if (null == mHeaderLinearLayout)
			return;
		// ע�������mDownY��onScroll�ĵڶ���else�б��ı���
		mHeaderLinearLayout.setPadding(mHeaderLinearLayout.getPaddingLeft(),
				padTop, mHeaderLinearLayout.getPaddingRight(),
				mHeaderLinearLayout.getPaddingBottom());
	}

	// ��Ϊ�漰��handler���ݴ���Ϊ�������Ƕ������³���
	private final static int REFRESH_BACKING = 0; // ������
	private final static int REFRESH_BACED = 1; // �ﵽˢ�½��ޣ�����������
	private final static int REFRESH_RETURN = 2; // û�дﵽˢ�½��ޣ�����
	private final static int REFRESH_DONE = 3; // �������ݽ���

	private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case REFRESH_BACKING:
				/*
				 * mHeaderLinearLayout.setPadding(
				 * mHeaderLinearLayout.getPaddingLeft(), (int)
				 * (mHeaderLinearLayout.getPaddingTop() * 0.75f),
				 * mHeaderLinearLayout.getPaddingRight(),
				 * mHeaderLinearLayout.getPaddingBottom());
				 */
				setHeaderTopPadding((int) (mHeaderLinearLayout.getPaddingTop() * 0.75f));
				break;
			case REFRESH_BACED:
				mHeaderTextView.setText("���ڼ���...");
				mHeaderProgressBar.setVisibility(View.VISIBLE);
				/*
				 * mHeaderPullDownImageView.setVisibility(View.GONE);
				 * mHeaderReleaseDownImageView.setVisibility(View.GONE);
				 */
				mPullRefreshState = EXIT_PULL_REFRESH;
				// do refreshing work here
				setup();
				break;
			case REFRESH_RETURN:
				// δ�ﵽˢ�½��ޣ�����
				mHeaderTextView.setText(HINT_PULLTOREFRESH);
				mHeaderProgressBar.setVisibility(View.INVISIBLE);
				/*
				 * mHeaderPullDownImageView.setVisibility(View.VISIBLE);
				 * mHeaderReleaseDownImageView.setVisibility(View.GONE);
				 */
				/*
				 * mHeaderLinearLayout.setPadding(
				 * mHeaderLinearLayout.getPaddingLeft(), 0,
				 * mHeaderLinearLayout.getPaddingRight(),
				 * mHeaderLinearLayout.getPaddingBottom());
				 */
				setHeaderTopPadding(iS_TOTAL_TOO_FEW ? -mHeaderHeight : 0);// ����ʱ��ȥͷ��
				mPullRefreshState = NONE_PULL_REFRESH;
				setSelection(1);
				break;
			case REFRESH_DONE:
				// ˢ�½����󣬻ָ�ԭʼĬ��״̬
				mHeaderTextView.setText(HINT_DONE);
				mHeaderProgressBar.setVisibility(View.INVISIBLE);
				/*
				 * mHeaderPullDownImageView.setVisibility(View.VISIBLE);
				 * mHeaderReleaseDownImageView.setVisibility(View.GONE);
				 * mHeaderUpdateText.setText(getContext().getString(R.string.
				 * app_list_header_refresh_last_update,
				 * mSimpleDateFormat.format(new Date())));
				 */
				/*
				 * mHeaderLinearLayout.setPadding(
				 * mHeaderLinearLayout.getPaddingLeft(), 0,
				 * mHeaderLinearLayout.getPaddingRight(),
				 * mHeaderLinearLayout.getPaddingBottom());
				 */
				setHeaderTopPadding(iS_TOTAL_TOO_FEW ? -mHeaderHeight : 0);
				mPullRefreshState = NONE_PULL_REFRESH;
				setSelection(1);
				break;
			default:
				break;
			}
		}
	};

	private boolean iS_TOTAL_TOO_FEW;
}
