package Views;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.Nullable;
import android.support.v4.os.TraceCompat;
import android.support.v4.view.NestedScrollingParent;
import android.support.v4.view.NestedScrollingParentHelper;
import android.support.v4.view.VelocityTrackerCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ScrollerCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.LinearLayout;
import android.widget.ViewFlipper;

/**
 * Created by admin on 2017/4/8.
 */

public class SRecyclerView extends LinearLayout implements NestedScrollingParent {
    public static final String TAG = "DEBUG";
    private NestedScrollingParentHelper scrollingParentHelper;
    private LinearLayout headLayout;
    private LinearLayout footLayout;
    private MyRecyclerView myRecyclerView;
    static final Interpolator sQuinticInterpolator = new Interpolator() {
        @Override
        public float getInterpolation(float t) {
            t -= 1.0f;
            return t * t * t * t * t + 1.0f;
        }
    };
    ScrollerCompat scrollerCompat = ScrollerCompat.create(getContext(),sQuinticInterpolator);
    private OnRefreshListener listener;

    private int headerHeight, footHeight;

    private int pullRate = 3;

    private int scrolls;

    private boolean isLoading = false;

    private int maxTime = 200;

    private int maxFastOverScroll= (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,200,getResources().getDisplayMetrics());

    private boolean canheader = true, canfooter = true;
    private ValueAnimator animator;
    private ValueAnimator animator1;
    private boolean canOverLoading = false;
    private boolean canOverscroll = true;

    public SRecyclerView(Context context) {
        this(context, null);
    }

    public SRecyclerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setOrientation(VERTICAL);
        scrollingParentHelper = new NestedScrollingParentHelper(this);

        headLayout = new LinearLayout(getContext());
        headLayout.setOrientation(VERTICAL);
        myRecyclerView = new MyRecyclerView(getContext());
        myRecyclerView.setNestedScrollingEnabled(true);
        footLayout = new LinearLayout(getContext());
        footLayout.setOrientation(VERTICAL);

        addView(headLayout, new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        addView(myRecyclerView, new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        addView(footLayout, new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
    }

    public SRecyclerView setRefreshMode(boolean head, boolean foot) {
        this.canheader = head;
        this.canfooter = foot;
        return this;
    }

    public SRecyclerView addHeader(View headView, int headerHeight) {
        this.headerHeight = headerHeight;
        headLayout.removeAllViews();
        headLayout.addView(headView);
        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, headerHeight);
        params.topMargin = -headerHeight;
        headLayout.setLayoutParams(params);
        return this;
    }

    public SRecyclerView addFooter(View footView, int footHeight) {
        this.footHeight = footHeight;
        footLayout.removeAllViews();
        footLayout.addView(footView);
        LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, footHeight);
        footLayout.setLayoutParams(layoutParams);
        return this;
    }

    public SRecyclerView setAdapter(RecyclerView.LayoutManager manager, RecyclerView.Adapter adapter) {
        myRecyclerView.setLayoutManager(manager);
        myRecyclerView.setAdapter(adapter);
        return this;
    }

    public SRecyclerView addItemDecorate(RecyclerView.ItemDecoration decoration) {
        myRecyclerView.addItemDecoration(decoration);
        return this;
    }

    @Override
    public boolean onStartNestedScroll(View child, View target, int nestedScrollAxes) {
        return true;
    }

    public SRecyclerView setRefreshingListener(OnRefreshListener listener) {
        this.listener = listener;
        return this;
    }

    public SRecyclerView setPullRate(int rate) {
        if(rate>=1){
            pullRate=rate;
        }
        return this;
    }

    public interface OnRefreshListener {
        void pullDown(int height);

        void pullUp(int height);

        void Refreshing();

        void Loading();
    }

    /**
     * @param loading    快速滑动加载？
     * @param overscroll 超出范围？
     * @return
     */
    public SRecyclerView setOverScrollEnable(boolean overscroll, boolean loading) {
        canOverLoading = loading;
        canOverscroll = overscroll;
        return this;
    }

    @Override
    public void onNestedPreScroll(View target, int dx, int dy, int[] consumed) {
        //TODO回拉时的处理
        if (isLoading)
            return;
        if (canheader) {
            //下拉回拉时
            if (myRecyclerView.canPull(-1) && dy > 0) {
                scrolls += dy;
                if (scrolls <= 0) {
                    Log.i(TAG, "onNestedPreScroll:下拉回拉时 ");
                    scrollTo(0, scrolls / pullRate);
                    consumed[1] = dy;

                    if (listener != null)
                        listener.pullDown(scrolls / pullRate);

                } else {
                    scrollTo(0, 0);
                    scrolls = 0;
                    consumed[1] = 0;
                }
                return;
            }
        }

        if (canfooter) {
            if (myRecyclerView.canPull(1) && dy < 0) {
                scrolls += dy;
                if (scrolls >= 0) {
                    Log.i(TAG, "onNestedPreScroll:上拉回拉时 ");
                    scrollTo(0, scrolls / pullRate);
                    consumed[1] = dy;

                    if (listener != null)
                        listener.pullUp(scrolls / pullRate);

                } else {
                    scrollTo(0, 0);
                    scrolls = 0;
                    consumed[1] = 0;
                }
            }
        }
    }

    @Override
    public void onNestedScroll(View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
        if (isLoading)
            return;
        //TODO拉动的处理
        if ((myRecyclerView.canPull(-1) && dyUnconsumed < 0&&canheader) || (myRecyclerView.canPull(1) && dyUnconsumed > 0&&canfooter)) {
            scrolls += dyUnconsumed;
            Log.i(TAG, "onNestedScroll: " + dyUnconsumed);
            scrollTo(0, scrolls / pullRate);

            if (listener != null) {
                if (scrolls > 0)
                    listener.pullUp(scrolls / pullRate);
                else
                    listener.pullDown(scrolls / pullRate);
            }
        }
    }

    @Override
    public boolean onNestedFling(View target, float velocityX, float velocityY, boolean consumed) {
        Log.i(TAG, "onNestedFling: " + scrolls + "==" + velocityY);
        if (scrolls == 0 && canOverscroll) {
            scrollerCompat.fling(0, 0, 0, (int) velocityY, 0, 0, 0, 3 * headerHeight, 0, 4 * headerHeight);
//            postInvalidate();
        }
        return canOverscroll;
    }

    public boolean onNestedPreFling(View target, float velocityX, float velocityY) {
        return false;
    }

    @Override
    public int getNestedScrollAxes() {
        return scrollingParentHelper.getNestedScrollAxes();
    }

    @Override
    public void computeScroll() {
        if (scrollerCompat.computeScrollOffset()) {
//            postInvalidate();
            if (scrolls != 0)
                scrollerCompat.abortAnimation();
            Log.i(TAG, "computeScroll: " + scrollerCompat.getCurrY());
            if ((myRecyclerView.canPull(-1) && scrollerCompat.getCurrY() < 0)) {
                if (scrollerCompat.getCurrY() < -pullRate * headerHeight && canOverLoading) {
                    smoothScroll(0, -headerHeight);
                    if (listener != null)
                        listener.Refreshing();
                } else {
                    smoothScrollRepeat(scrollerCompat.getCurrY()<-maxFastOverScroll?-maxFastOverScroll:scrollerCompat.getCurrY());
                }
                scrollerCompat.abortAnimation();
            } else if ((myRecyclerView.canPull(1) && scrollerCompat.getCurrY() > 0)) {
                if (scrollerCompat.getCurrY() > pullRate * headerHeight && canOverLoading) {
                    smoothScroll(0, headerHeight);
                    if (listener != null)
                        listener.Loading();
                } else {
                    smoothScrollRepeat(scrollerCompat.getCurrY()>maxFastOverScroll?maxFastOverScroll:scrollerCompat.getCurrY());
                }
                scrollerCompat.abortAnimation();
            }
        } else {
            Log.i(TAG, "computeScroll: finish");
        }
    }

    private void smoothScrollRepeat(int max) {
        if (animator1 != null) {
            animator1.cancel();
            animator1.setIntValues(0, max, 0);
            animator1.setDuration(400);
        } else {
            animator1 = ValueAnimator.ofInt(0, max, 0);
            animator1.setDuration(400);
            animator1.setInterpolator(new DecelerateInterpolator());
            animator1.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    int value = (int) animation.getAnimatedValue();
                    scrollTo(0, value);
                    scrolls = pullRate * value;

                    if (listener != null) {
                        if (value >= 0)
                            listener.pullUp(value);
                        if (value <= 0)
                            listener.pullDown(value);
                    }

                }
            });
        }
        animator1.start();
    }

    public void notifyRefreshComplete() {
        Log.i(TAG, "notifyRefreshComplete: smoothScroll");
        smoothScroll(scrolls / pullRate, 0);
        postDelayed(new Runnable() {
            @Override
            public void run() {
                isLoading = false;
            }
        }, getAnimatorDuring(scrolls / pullRate, 0));
    }

    @Override
    public void onStopNestedScroll(View child) {
        scrollingParentHelper.onStopNestedScroll(child);
        Log.i(TAG, "onStopNestedScroll: ");
        if (scrolls / pullRate <= -headerHeight) {
            if (listener != null)
                listener.Refreshing();
            isLoading = true;
            smoothScroll(scrolls / pullRate, -headerHeight);
        } else if (scrolls / pullRate >= footHeight) {

            if (listener != null)
                listener.Loading();

            isLoading = true;
            smoothScroll(scrolls / pullRate, footHeight);
        } else {
            smoothScroll(scrolls / pullRate, 0);
        }
    }

    private void smoothScroll(int from, int to) {
        if (from == to)
            return;
        Log.i(TAG, "smoothScroll: " + from + "-" + to);
        if (animator != null) {
            animator.cancel();
            animator.setIntValues(from, to);
            animator.setDuration(getAnimatorDuring(from, to));
        } else {
            animator = ValueAnimator.ofInt(from, to);
            animator.setDuration(getAnimatorDuring(from, to));
            animator.setInterpolator(new DecelerateInterpolator());
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    int value = (int) animation.getAnimatedValue();
                    scrollTo(0, value);
                    scrolls = pullRate * value;

                    if (listener != null) {
                        if (value >= 0)
                            listener.pullUp(value);
                        if (value <= 0)
                            listener.pullDown(value);
                    }

                }
            });
        }
        animator.start();
    }

    private long getAnimatorDuring(int from, int to) {
        return (long) (maxTime * ((float) Math.abs(from - to) / (float) headerHeight));
    }

    @Override
    public void onNestedScrollAccepted(View child, View target, int axes) {
        scrollingParentHelper.onNestedScrollAccepted(child, target, axes);
    }

    private class MyRecyclerView extends RecyclerView {
        private StaggeredGridLayoutManager staggeredGridLayoutManager = null;
        private LinearLayoutManager linearLayoutManager = null;
        private GridLayoutManager gridLayoutManager = null;

        public MyRecyclerView(Context context) {
            super(context);
//            setDescendantFocusability(FOCUS_BLOCK_DESCENDANTS);
            setHorizontalFadingEdgeEnabled(false);
            setVerticalFadingEdgeEnabled(false);
            setOverScrollMode(OVER_SCROLL_NEVER);
            setItemAnimator(new DefaultItemAnimator());
            setVerticalScrollBarEnabled(false);
            setHorizontalScrollBarEnabled(false);
        }

        @Override
        public void setLayoutManager(LayoutManager layout) {
            super.setLayoutManager(layout);
            if (layout instanceof LinearLayoutManager) {
                linearLayoutManager = (LinearLayoutManager) layout;
                if (linearLayoutManager.getOrientation() != VERTICAL)
                    throw new UnsupportedOperationException("仅支持垂直方向的刷新");
            } else if (layout instanceof GridLayoutManager) {
                gridLayoutManager = (GridLayoutManager) layout;
                if (gridLayoutManager.getOrientation() != VERTICAL)
                    throw new UnsupportedOperationException("仅支持垂直方向的刷新");
            } else if (layout instanceof StaggeredGridLayoutManager) {
                staggeredGridLayoutManager = (StaggeredGridLayoutManager) layout;
                if (staggeredGridLayoutManager.getOrientation() != VERTICAL)
                    throw new UnsupportedOperationException("仅支持垂直方向的刷新");
            }
        }

        /**
         * -1 pulldown 1 pullup
         * recyclerview不能滑动了，外层就可滑动了
         */
        public boolean canPull(int orentation) {
            return (orentation == 1 || orentation == -1) && !canScrollVertically(orentation);
        }
    }

    private class MyItemTouchHelper extends ItemTouchHelper {
        /**
         * Creates an ItemTouchHelper that will work with the given Callback.
         * <p>
         * You can attach ItemTouchHelper to a RecyclerView via
         * {@link #attachToRecyclerView(RecyclerView)}. Upon attaching, it will add an item decoration,
         * an onItemTouchListener and a Child attach / detach listener to the RecyclerView.
         *
         * @param callback The Callback which controls the behavior of this touch helper.
         */
        public MyItemTouchHelper(Callback callback) {
            super(callback);
        }
    }

}
