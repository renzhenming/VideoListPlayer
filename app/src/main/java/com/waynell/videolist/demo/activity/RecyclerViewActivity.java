package com.waynell.videolist.demo.activity;

import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.VideoView;

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
    private MyAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_recycler_view);
        ButterKnife.bind(this);

        adapter = new MyAdapter();
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

    @Override
    protected void onResume() {
        super.onResume();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mCalculator.onScrolled(AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL);
                mCalculator.onScrollStateIdle();
            }
        },1000);

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
        private int heightPixels = -1;

        public MyAdapter() {
            mListItems = ItemUtils.generateMockData();
        }

        @Override
        public BaseViewHolder<? extends BaseItem> onCreateViewHolder(ViewGroup parent, int viewType) {
            if (heightPixels == -1)
                heightPixels = getResources().getDisplayMetrics().heightPixels;
            return ViewHolderFactory.buildViewHolder(parent, viewType,heightPixels);
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

        public void setScreenHeight(int heightPixels) {
            this.heightPixels = heightPixels;
        }
    }
    /* *
     * 通过API动态改变当前屏幕的显示方向
     */
    public void apiChangeOrientation() {
        // 取得当前屏幕方向
        int orient = getRequestedOrientation();
        // 若非明确的landscape或portrait时 再透过宽高比例的方法来确认实际显示方向
        // 这会保证orient最终值会是明确的横屏landscape或竖屏portrait
        if (orient != ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
                && orient != ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
            //宽>高为横屏,反正为竖屏
            //int[] size = MyUtils.getDisplaySize(this);
            int heightPixels = getResources().getDisplayMetrics().heightPixels;
            int widthPixels = getResources().getDisplayMetrics().widthPixels;
            orient = widthPixels > heightPixels ? ActivityInfo.SCREEN_ORIENTATION_PORTRAIT: ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;
        }
        if (orient == ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }
        else{
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
    }

//        @Override
//    protected void onResume() {
//        mOrientation = ActivityInfo.SCREEN_ORIENTATION_USER;
//        this.setRequestedOrientation(mOrientation);
//        Display display = getWindowManager().getDefaultDisplay();
//        int width = display.getWidth();
//        int height = display.getHeight();
//        if (width > height) {
//            mOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;
//        } else {
//            mOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
//        }
//        super.onResume();
//    }



    @Override
    public void onConfigurationChanged(Configuration newConfig) {

        super.onConfigurationChanged(newConfig);
        //this.setRequestedOrientation(mOrientation);
        //apiChangeOrientation();
        // 当新设置中，屏幕布局模式为横排时
//        System.out.println("111111："+newConfig.orientation);
//        if(newConfig.orientation == ActivityInfo.SCREEN_ORIENTATION_USER) {
//            System.out.println("111111横屏");
//            new Handler().postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    setScreenWidthAsHeight();      //
//                }
//            }, 1100);//尝试过直接使用post操作，在updatePopup函数中也能获取正确位置
//        }else if (newConfig.orientation == ActivityInfo.SCREEN_ORIENTATION_PORTRAIT){
//            System.out.println("111111竖屏");
//            new Handler().postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    setScreenHeightAsHeight();    //
//                }
//            }, 1100);
//        }
    }
//
//    private void setScreenHeightAsHeight() {
//        int heightPixels = getResources().getDisplayMetrics().widthPixels;
//        System.out.println(",heightPixels:"+heightPixels);
//        adapter.setScreenHeight(heightPixels);
//        adapter.notifyItemInserted(0);
//    }
//
//    private void setScreenWidthAsHeight() {
//        int widthPixels = getResources().getDisplayMetrics().widthPixels;
//        System.out.println("widthPixels:"+widthPixels);
//        adapter.setScreenHeight(widthPixels);
//        adapter.notifyItemInserted(0);
//    }

//    private void initScreen() {
//        Display display = getWindowManager().getDefaultDisplay();
//        int width = display.getWidth();
//        int height = display.getHeight();
//        if (width > height) {
//            mOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE; // 横屏
//        } else {
//            mOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT; // 竖屏
//        }
//    }
}
