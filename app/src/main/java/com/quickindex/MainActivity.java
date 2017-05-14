package com.quickindex;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends Activity {
    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerview;
    @BindView(R.id.quickindex)
    IndexHelper mQuickIndex;
    @BindView(R.id.guide_index_layout)
    GuideIndexLayout mGuideIndexLayout;
    private List<Friend> mTotalFriendList;
    private List<Friend> mSelectedGroup;
    private MyAdapter mMyAdapter;
    private LinearLayoutManager mManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initData();
        initRecycler();
        initQuickIndex();
    }

    //初始化RecyclerView
    private void initRecycler() {
        mManager = new LinearLayoutManager(this);
        mRecyclerview.setLayoutManager(mManager);
        mMyAdapter = new MyAdapter(mTotalFriendList, this);
        mRecyclerview.setAdapter(mMyAdapter);
        mRecyclerview.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int position = mManager.findFirstVisibleItemPosition();
                int charAt = mTotalFriendList.get(position).getPinYin().charAt(0);
                mQuickIndex.setCurrentPostion(charAt);
            }
        });
    }

    //初始化快速索引
    private void initQuickIndex() {
        mQuickIndex.setOnLetterSelectedListener(new IndexHelper.OnLetterSelectedListener() {
            @Override
            public void onLetterSelect(String letter) {
                scrollToLetterPostion(letter); //滚动到指定的位置,并将数据设置给GuideIndexLayout;
            }
        });
    }

    //recyclerview滚动到指定的位置,并且将数据设置给guideindexLayout.
    private void scrollToLetterPostion(String letter) {
        int aimPostion = -1;
        mSelectedGroup.clear();
        String firstLetter = null;
        for (int index = 0; index < mTotalFriendList.size(); index++) {
            firstLetter = mTotalFriendList.get(index).getPinYin().charAt(0) + "";
            if (letter.equals(firstLetter)) {
                mSelectedGroup.add(mTotalFriendList.get(index));
                if (aimPostion == -1) aimPostion = index;
            }
        }
        //遍历完集合还是没找到的话,并且当前的被点击的letter是#,那么将aimPostion设置为0即可;
        if (aimPostion == -1 && letter.equals("#")) aimPostion = 0;
        mManager.scrollToPositionWithOffset(aimPostion, 0);
        mGuideIndexLayout.refreshGuideLayout(mSelectedGroup, letter);
    }

    private void initData() {
        mSelectedGroup = new ArrayList<>();
        mTotalFriendList = new ArrayList<>();
        // 虚拟数据
        mTotalFriendList.add(new Friend("李宗伟"));
        mTotalFriendList.add(new Friend("李宗伟"));
        mTotalFriendList.add(new Friend("#张飞"));
        mTotalFriendList.add(new Friend("*张飞"));
        mTotalFriendList.add(new Friend(")张飞飞"));
        mTotalFriendList.add(new Friend("阿三"));
        mTotalFriendList.add(new Friend("阿四"));
        mTotalFriendList.add(new Friend("芭比"));
        mTotalFriendList.add(new Friend("芭比"));
        mTotalFriendList.add(new Friend("芭比"));
        mTotalFriendList.add(new Friend("芭比"));
        mTotalFriendList.add(new Friend("芭比"));
        mTotalFriendList.add(new Friend("芭比"));
        mTotalFriendList.add(new Friend("段誉"));
        mTotalFriendList.add(new Friend("段正淳"));
        mTotalFriendList.add(new Friend("张三丰"));
        mTotalFriendList.add(new Friend("陈坤"));
        mTotalFriendList.add(new Friend("林俊杰"));
        mTotalFriendList.add(new Friend("林俊"));
        mTotalFriendList.add(new Friend("陈坤坤"));
        mTotalFriendList.add(new Friend("王二a"));
        mTotalFriendList.add(new Friend("林俊杰a"));
        mTotalFriendList.add(new Friend("张四"));
        mTotalFriendList.add(new Friend("林俊杰"));
        mTotalFriendList.add(new Friend("易中天"));
        mTotalFriendList.add(new Friend("易中天"));
        mTotalFriendList.add(new Friend("易中天"));
        mTotalFriendList.add(new Friend("易中天"));
        mTotalFriendList.add(new Friend("易中天"));
        mTotalFriendList.add(new Friend("易中天"));
        mTotalFriendList.add(new Friend("凡客"));
        mTotalFriendList.add(new Friend("凡客"));
        mTotalFriendList.add(new Friend("凡客"));
        mTotalFriendList.add(new Friend("凡客"));
        mTotalFriendList.add(new Friend("嫚路"));
        mTotalFriendList.add(new Friend("嫚路"));
        mTotalFriendList.add(new Friend("嫚路"));
        mTotalFriendList.add(new Friend("嫚路"));
        mTotalFriendList.add(new Friend("嫚路"));
        mTotalFriendList.add(new Friend("田一"));
        mTotalFriendList.add(new Friend("田一"));
        mTotalFriendList.add(new Friend("田一"));
        mTotalFriendList.add(new Friend("田一"));
        mTotalFriendList.add(new Friend("阿信"));
        mTotalFriendList.add(new Friend("阿信"));
        mTotalFriendList.add(new Friend("阿信"));
        mTotalFriendList.add(new Friend("阿信"));
        mTotalFriendList.add(new Friend("阿信"));
        mTotalFriendList.add(new Friend("情书"));
        mTotalFriendList.add(new Friend("情书"));
        mTotalFriendList.add(new Friend("情书"));
        mTotalFriendList.add(new Friend("情书"));
        mTotalFriendList.add(new Friend("情书"));
        mTotalFriendList.add(new Friend("任正非"));
        mTotalFriendList.add(new Friend("任正非"));
        mTotalFriendList.add(new Friend("任正非"));
        mTotalFriendList.add(new Friend("任正非"));
        mTotalFriendList.add(new Friend("任正非"));
        mTotalFriendList.add(new Friend("任正非"));
        mTotalFriendList.add(new Friend("任正非"));
        mTotalFriendList.add(new Friend("情书"));
        mTotalFriendList.add(new Friend("情书"));
        mTotalFriendList.add(new Friend("阿信"));
        mTotalFriendList.add(new Friend("阿信"));
        mTotalFriendList.add(new Friend("田一"));
        mTotalFriendList.add(new Friend("田一"));
        mTotalFriendList.add(new Friend("嫚路"));
        mTotalFriendList.add(new Friend("嫚路路"));
        mTotalFriendList.add(new Friend("凡客客"));
        mTotalFriendList.add(new Friend("王二二"));
        mTotalFriendList.add(new Friend("&赵四"));
        mTotalFriendList.add(new Friend("杨坤"));
        mTotalFriendList.add(new Friend("赵子龙"));
        mTotalFriendList.add(new Friend("杨坤坤1"));
        mTotalFriendList.add(new Friend("李伟伟1"));
        mTotalFriendList.add(new Friend("宋江江"));
        mTotalFriendList.add(new Friend("宋江江1"));
        mTotalFriendList.add(new Friend("张天池"));
        mTotalFriendList.add(new Friend("张天池"));
        mTotalFriendList.add(new Friend("张天池"));
        mTotalFriendList.add(new Friend("张无忌"));
        mTotalFriendList.add(new Friend("张无忌"));
        mTotalFriendList.add(new Friend("张无忌"));
        mTotalFriendList.add(new Friend("张无忌"));
        mTotalFriendList.add(new Friend("张无忌"));
        mTotalFriendList.add(new Friend("张无忌"));
        mTotalFriendList.add(new Friend("张无忌"));
        mTotalFriendList.add(new Friend("张无忌"));
        mTotalFriendList.add(new Friend("张无忌"));
        mTotalFriendList.add(new Friend("张无忌"));
        mTotalFriendList.add(new Friend("张无忌"));
        mTotalFriendList.add(new Friend("张天池"));
        mTotalFriendList.add(new Friend("张天池"));
        mTotalFriendList.add(new Friend("张天池"));
        mTotalFriendList.add(new Friend("张天池"));
        mTotalFriendList.add(new Friend("宋宇星1"));
        mTotalFriendList.add(new Friend("宋宇星1"));
        mTotalFriendList.add(new Friend("宋宇星1"));
        mTotalFriendList.add(new Friend("宋宇星1"));
        mTotalFriendList.add(new Friend("宋宇星1"));
        mTotalFriendList.add(new Friend("宋宇星1"));
        mTotalFriendList.add(new Friend("李伟3"));
        mTotalFriendList.add(new Friend("李伟3"));
        mTotalFriendList.add(new Friend("李伟3"));
        mTotalFriendList.add(new Friend("李伟3"));

        Collections.sort(mTotalFriendList);
    }
}
