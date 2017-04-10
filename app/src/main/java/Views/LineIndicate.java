package Views;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.graphics.ColorUtils;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.saibaizi.alliance.R;

import Constance.GlobalDatas;
import util.Utils;

/**
 * Created by S0005 on 2017/4/7.
 */

public class LineIndicate extends View implements ViewPager.OnPageChangeListener {

    private float lineWidth, indicateLength;
    private int lineColor;
    private int lineIndicateColor;
    private float width, height;
    private int pagerNum;

    private int alpha = 80;

    private int currentPager = 0;
    private float percentage = 0;

    ViewPager mViewPager;

    //当自适应时置顶最小长度
    private int minPerWidth = Utils.dp2px(getContext(), 10);
    private Paint linePaint;
    private Paint indicatePaint;
    private ValueAnimator animator;
    private ViewPager.OnPageChangeListener pageChangeListener;

    public LineIndicate(Context context) {
        this(context, null);
    }

    public LineIndicate(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LineIndicate(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.LineIndicate);
        //线宽
        lineWidth = typedArray.getDimension(R.styleable.LineIndicate_LineWidth, Utils.dp2px(context, 2));
        lineColor = typedArray.getColor(R.styleable.LineIndicate_LineColor, 0xfff0f0f0);
        lineIndicateColor = typedArray.getColor(R.styleable.LineIndicate_LineIndicateColor, 0xff888888);
        typedArray.recycle();
        init();
    }

    private void init() {
        linePaint = new Paint();
        linePaint.setColor(lineColor);
        linePaint.setAntiAlias(true);
        linePaint.setStrokeWidth(lineWidth);
        linePaint.setStyle(Paint.Style.FILL);

        indicatePaint = new Paint();
        indicatePaint.setColor(lineIndicateColor);
        indicatePaint.setAntiAlias(true);
        indicatePaint.setStrokeWidth(lineWidth);
        indicatePaint.setStyle(Paint.Style.FILL);

    }

    public void setUpViewPager(ViewPager pager) {
        mViewPager = pager;
        pagerNum = pager.getAdapter().getCount();
        mViewPager.addOnPageChangeListener(this);
    }

    public void setonPagerChagerListener(ViewPager.OnPageChangeListener listener) {
        pageChangeListener = listener;
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mViewPager=null;
        pageChangeListener=null;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        if (widthMode == MeasureSpec.EXACTLY) {
            width = widthSize;
        } else {
            if (GlobalDatas.DEBUG)
                Log.i(GlobalDatas.TAG, "onMeasure: " + pagerNum);
            //最小10dp长
            width = minPerWidth * pagerNum;
        }

        if (heightMode == MeasureSpec.EXACTLY) {
            height = heightSize;
            lineWidth = Math.min(height, lineWidth);
        } else {
            height = lineWidth;
        }
        indicateLength = width / pagerNum;
        setMeasuredDimension((int)width, (int)height);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        canvas.drawRoundRect(0, 0, width, lineWidth, lineWidth, lineWidth, linePaint);

        indicatePaint.setColor(ColorUtils.setAlphaComponent(lineIndicateColor, alpha));

        canvas.drawRoundRect(currentPager * indicateLength + percentage * indicateLength, 0, (currentPager+1) * indicateLength + percentage * indicateLength, lineWidth, lineWidth, lineWidth, indicatePaint);

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        currentPager = position;
        percentage = positionOffset;
        if (pageChangeListener != null)
            pageChangeListener.onPageScrolled(position, positionOffset, positionOffsetPixels);
        invalidate();
    }

    @Override
    public void onPageSelected(int position) {
        currentPager = position;
        percentage = 0;
        if (pageChangeListener != null)
            pageChangeListener.onPageSelected(position);
        invalidate();

    }

    @Override
    public void onPageScrollStateChanged(int state) {
        if (state == ViewPager.SCROLL_STATE_IDLE) {
            startFadeAnimation();

        } else {
            if (animator != null)
                animator.cancel();
            alpha = 255;
            invalidate();
        }

        if (pageChangeListener != null)
            pageChangeListener.onPageScrollStateChanged(state);

    }

    private void startFadeAnimation() {
        if (animator != null) {
            animator.cancel();
            animator.setIntValues(255, 128);
        } else {
            animator = ValueAnimator.ofInt(255, 26);
            animator.setDuration(600);
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    int value = (int) valueAnimator.getAnimatedValue();
                    alpha = value;
                    invalidate();

                }
            });
        }
        animator.setStartDelay(500);
        animator.start();
    }


    @Override
    protected Parcelable onSaveInstanceState() {
        Parcelable superState = super.onSaveInstanceState();
        SavedState savedState = new SavedState(superState);
        savedState.currentPage = currentPager;
        return savedState;
    }

    @Override
    public void onRestoreInstanceState(Parcelable state) {
        SavedState savedState = (SavedState) state;
        super.onRestoreInstanceState(savedState.getSuperState());
        currentPager = savedState.currentPage;
        invalidate();
    }

    static class SavedState extends BaseSavedState {
        int currentPage;

        public SavedState(Parcelable superState) {
            super(superState);
        }

        private SavedState(Parcel in) {
            super(in);
            currentPage = in.readInt();
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            super.writeToParcel(dest, flags);
            dest.writeInt(currentPage);
        }

        @SuppressWarnings("UnusedDeclaration")
        public static final Creator<SavedState> CREATOR = new Creator<SavedState>() {
            @Override
            public SavedState createFromParcel(Parcel in) {
                return new SavedState(in);
            }

            @Override
            public SavedState[] newArray(int size) {
                return new SavedState[size];
            }
        };
    }
}
