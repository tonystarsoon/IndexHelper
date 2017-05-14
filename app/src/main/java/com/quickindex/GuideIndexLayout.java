package com.quickindex;

import android.animation.FloatEvaluator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Guideline of QuickindexView
 * <p>
 * Created by tony on 2017/5/2.
 */
public class GuideIndexLayout extends LinearLayout {

    private TextView mGuideLetterView;
    private RecyclerView mGuideRecyclerView;
    private List<Friend> mDataList;
    private String mLetter;
    private GuideIndexLayoutAdapter mAdapter;
    private LinearLayoutManager mLinearLayoutManager;
    private boolean visablity = false;
    private FloatEvaluator mFloatEvaluator;
    private long startTime = -1;

    public GuideIndexLayout(Context context) {
        this(context, null);
    }

    public GuideIndexLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GuideIndexLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        mDataList = new ArrayList<>();
        mFloatEvaluator = new FloatEvaluator();
    }

    /**
     * 在onMeasure方法执行完成后执行,初始化父控件和子控件的宽高.
     *
     * @param w
     * @param h
     * @param oldw
     * @param oldh
     */
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        int measuredHeight = getMeasuredHeight();
        int measuredWidth = getMeasuredWidth();
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        setOrientation(LinearLayout.VERTICAL);
        super.onLayout(changed, l, t, r, b);
    }

    /**
     * Finalize inflating a view from XML.  This is called as the last phase
     * of inflation, after all child views have been added.
     * <p>Even if the subclass overrides onFinishInflate, they should always be
     * sure to call the super method, so that we get called.
     */
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        int childCount = getChildCount();
        if (childCount != 2) {
            throw new IllegalArgumentException("GuideIndexLayout must has a textView and a recyclerView");
        }
        mGuideLetterView = (TextView) getChildAt(0);
        mGuideRecyclerView = (RecyclerView) getChildAt(1);
        mGuideRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                startTime = System.currentTimeMillis();
                Log.i("time", "-------------------startTime" + startTime);
            }
        });
    }

    /**
     * 将最新的数据刷进导航栏目
     *
     * @param dataList
     * @param letter
     */
    public void refreshGuideLayout(List<Friend> dataList, String letter) {
        mDataList.clear();
        mDataList.addAll(dataList);
        if (mDataList.size() == 0) {
            mGuideLetterView.setBackground(getResources().getDrawable(R.drawable.shape_guide_letter_bg));
            mGuideRecyclerView.setVisibility(View.GONE);
        } else {
            setBackground(getResources().getDrawable(R.drawable.shape_guide_bg));
            mGuideLetterView.setBackground(getResources().getDrawable(R.drawable.shape_guide_letter_bg_have));
            mGuideRecyclerView.setVisibility(View.VISIBLE);
        }
        if (mAdapter == null) {
            mLinearLayoutManager = new LinearLayoutManager(getContext());
            mGuideRecyclerView.setLayoutManager(mLinearLayoutManager);
            mAdapter = new GuideIndexLayoutAdapter(mDataList);
            mGuideRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.notifyDataSetChanged();
        }

        mLetter = letter;
        mGuideLetterView.setText(mLetter);

        startTime = System.currentTimeMillis();

        if (!visablity) {
            visablity = true;
            setVisibility(View.VISIBLE);
            ObjectAnimator alpha = ObjectAnimator.ofObject(this, "alpha", mFloatEvaluator, 0f, 1.0f);
            alpha.setDuration(400);
            alpha.start();
            mHandler.sendEmptyMessageDelayed(0, 2000);
        }
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            long currentTimeMillis = System.currentTimeMillis();
            if (currentTimeMillis - startTime < 2000) {
                startTime = System.currentTimeMillis();
                mHandler.sendEmptyMessageDelayed(0, 2000);
            } else {
                visablity = false;
                ObjectAnimator alpha = ObjectAnimator.ofObject(GuideIndexLayout.this, "alpha", mFloatEvaluator, 1.0f, 0f);
                alpha.setDuration(400);
                alpha.start();
            }
        }
    };

    private class GuideIndexLayoutAdapter extends RecyclerView.Adapter {
        private List<Friend> mFriendList;

        public GuideIndexLayoutAdapter(List<Friend> friendList) {
            mFriendList = friendList;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            View view = inflater.inflate(R.layout.item_guide, parent, false);
            GuideIndexLayoutHolder holder = new GuideIndexLayoutHolder(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            TextView textView = ((GuideIndexLayoutHolder) holder).mTextView;
            String text = mFriendList.get(position).getName().charAt(0) + "";
            textView.setText(text);
        }

        @Override
        public int getItemCount() {
            return mFriendList.size();
        }
    }

    private class GuideIndexLayoutHolder extends RecyclerView.ViewHolder {
        TextView mTextView;
        View mView;

        public GuideIndexLayoutHolder(View view) {
            super(view);
            mView = view;
            mTextView = (TextView) mView.findViewById(R.id.text1);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    DensityUtils.dip2px(getContext(), 60)
                    , DensityUtils.dip2px(getContext(), 60));
            mTextView.setLayoutParams(params);
            mTextView.setTextColor(Color.DKGRAY);
            mTextView.setGravity(Gravity.CENTER);
        }
    }
}
