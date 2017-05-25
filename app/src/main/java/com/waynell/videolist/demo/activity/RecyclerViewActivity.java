package com.waynell.videolist.demo.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.waynell.videolist.demo.R;
import com.waynell.videolist.demo.holder.BaseViewHolder;
import com.waynell.videolist.demo.holder.ViewHolderFactory;
import com.waynell.videolist.demo.model.BaseItem;
import com.waynell.videolist.demo.util.ItemUtils;
import com.waynell.videolist.visibility.calculator.SingleListViewItemActiveCalculator;
import com.waynell.videolist.visibility.items.ListItem;
import com.waynell.videolist.visibility.scroll.ItemsProvider;
import com.waynell.videolist.visibility.scroll.RecyclerViewItemPositionGetter;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author Wayne
 */
public class RecyclerViewActivity extends AppCompatActivity implements View.OnClickListener {

    private final String TAG = "RecyclerViewActivity";
    @Bind(R.id.recycler_view)
    RecyclerView mRecyclerView;

    private int mScrollState;

    private SingleListViewItemActiveCalculator mCalculator;
    private ControllRateLinearLayoutManager mLayoutManager;
    private int startX;
    private int startY;
    //当前可见条目
    private int mCurrentVisibleItemPosition;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_recycler_view);
        ButterKnife.bind(this);

        final MyAdapter adapter = new MyAdapter();
        mLayoutManager = new ControllRateLinearLayoutManager(this);
        mLayoutManager.setSpeedSlow();
        mCalculator = new SingleListViewItemActiveCalculator(adapter,
                new RecyclerViewItemPositionGetter(mLayoutManager, mRecyclerView));
        TextView mPlay = (TextView) findViewById(R.id.play);
        mPlay.setOnClickListener(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                mScrollState = newState;
                if(newState == RecyclerView.SCROLL_STATE_IDLE && adapter.getItemCount() > 0){
                    mCalculator.onScrollStateIdle();
                    mCurrentVisibleItemPosition = mLayoutManager.findFirstVisibleItemPosition();
                    System.out.println(TAG+" 当前itemposition为："+ mCurrentVisibleItemPosition);
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                mCalculator.onScrolled(mScrollState);
            }
        });

        mRecyclerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        startX = (int) event.getX();
                        startY = (int) event.getY();

                        break;
                    case MotionEvent.ACTION_MOVE:
                        int moveX = (int) event.getX();
                        int moveY = (int) event.getY();

                        int disX = moveX - startX;
                        int disY = moveY - startY;
                        //System.out.println(TAG+"startX :" + startX +",moveX :" + moveX+",disX:"+disX);
                        //System.out.println(TAG+"startY :" + startY +",moveY :" + moveY+",disY:"+disY);
                        LogUtils.d(TAG,"startX :" + startX +",moveX :" + moveX+",disX:"+disX);
                        LogUtils.d(TAG,"startY :" + startY +",moveY :" + moveY+",disY:"+disY);
                        startX = moveX;
                        startY = moveY;
                        //System.out.println(TAG+"startX :" + startX +",startY :" + startY);
                        break;
                    case MotionEvent.ACTION_UP:

                        float endX = event.getX();
                        float endY = event.getY();
                        //System.out.println(TAG+"endX :" + endX +",endY :" + endY);

                        LogUtils.d(TAG,"endX :" + endX +",endY :" + endY);
                        scrollView();
                        break;
                }

                return false;
            }
        });


    }

    public void scrollView(){
        if (mLayoutManager == null){
            return;
        }
        int firstVisibleItemPosition = mLayoutManager.findFirstVisibleItemPosition();
        View view = mLayoutManager.findViewByPosition(firstVisibleItemPosition);
        if (view != null){
            int[] position = new int[2];
            view.getLocationInWindow(position);
            int top = view.getTop();
            int bottom = view.getBottom();
            if (top > -(bottom - top)/2){
                LogUtils.d(TAG,"TAG 显示这个item");
                mRecyclerView.smoothScrollToPosition(firstVisibleItemPosition);
            }else if (top < -(bottom - top)/2){
                LogUtils.d(TAG,"TAG 显示下一个item");
                mRecyclerView.smoothScrollToPosition(firstVisibleItemPosition+1);
            }else{
                System.out.println("不满足滑动条件");
            }
            System.out.println(TAG+"top:"+top+",(bottom - top)/2:"+(bottom - top)/2);
            LogUtils.d(TAG,"top:"+top+",(bottom - top)/2:"+(bottom - top)/2);

        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.play:
                ToastUtils.showToast(this,mCurrentVisibleItemPosition+"");
                break;
        }
    }

    private class MyAdapter extends RecyclerView.Adapter<BaseViewHolder<? extends BaseItem>>
            implements ItemsProvider {

        private List<? extends BaseItem> mListItems;

        public MyAdapter() {
            mListItems = ItemUtils.generateMockData();
        }

        @Override
        public BaseViewHolder<? extends BaseItem> onCreateViewHolder(ViewGroup parent, int viewType) {
            return ViewHolderFactory.buildViewHolder(parent, viewType);
        }

        @SuppressWarnings("unchecked")
        @Override
        public void onBindViewHolder(BaseViewHolder holder, int position) {
            BaseItem baseItem = mListItems.get(position);
            holder.onBind(position, baseItem);
        }

        @Override
        public int getItemCount() {
            return mListItems.size();
        }

        @Override
        public int getItemViewType(int position) {
            return mListItems.get(position).getViewType();
        }

        @Override
        public ListItem getListItem(int position) {
            RecyclerView.ViewHolder holder = mRecyclerView.findViewHolderForAdapterPosition(position);
            if (holder instanceof ListItem) {
                return (ListItem) holder;
            }
            return null;
        }

        @Override
        public int listItemSize() {
            return getItemCount();
        }
    }
}
