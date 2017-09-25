package com.benny.baselib.view.recyclerview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.CoordinatorLayout.DefaultBehavior;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import com.benny.baselib.R;

/**
 * Created by yuanbb on 2017/9/25.
 */

@DefaultBehavior(CardSlideListView.CardSlideListViewBehavior.class)
public class CardSlideListView extends FrameLayout {

    private static final String TAG = "CardSlideListView";
    private String mHeaderTitle;
    private int mHeaderBackground = Color.parseColor("#ff0000");
    private int mHeaderHeight;
    private TextView mHeaderTv;
    private RecyclerView mListRv;

    public int getHeaderHeight() {
        return mHeaderHeight;
    }

    public RecyclerView getListView() {
        return mListRv;
    }


    public CardSlideListView(@NonNull Context context) {
        this(context, null);
    }

    public CardSlideListView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CardSlideListView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        View view = LayoutInflater.from(context).inflate(R.layout.view_card_slide, this);
        mHeaderTv = (TextView) view.findViewById(R.id.view_card_slide_header_tv);
        mListRv = (RecyclerView) view.findViewById(R.id.view_card_slide_list_rv);
        mListRv.setLayoutManager(new LinearLayoutManager(context));

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CardSlideListView);
        mHeaderTitle = typedArray.getString(R.styleable.CardSlideListView_headerTitle);
        mHeaderBackground = typedArray.getColor(R.styleable.CardSlideListView_headerBackground, mHeaderBackground);
        typedArray.recycle();

        mHeaderTv.setText(mHeaderTitle);
        mHeaderTv.setBackgroundColor(mHeaderBackground);
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (mHeaderHeight == 0 || h != oldh) {
            mHeaderHeight = mHeaderTv.getMeasuredHeight();
        }
    }


    public static class CardSlideListViewBehavior extends CoordinatorLayout.Behavior<CardSlideListView> {

        private int mInitialOffset;

        @Override
        public boolean onMeasureChild(CoordinatorLayout parent, CardSlideListView child, int parentWidthMeasureSpec, int widthUsed, int parentHeightMeasureSpec, int heightUsed) {

            int height = MeasureSpec.getSize(parentHeightMeasureSpec);
            height = height - heightUsed - getMeasuredOffset(parent, child);
            child.measure(parentWidthMeasureSpec, MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY));
            return true;
        }


        private int getMeasuredOffset(CoordinatorLayout parent, CardSlideListView child) {
            int offset = 0;
            for (int i = 0, count = parent.getChildCount(); i < count; i++) {

                View view = parent.getChildAt(i);
                if (view != child && view instanceof CardSlideListView) {
                    offset += ((CardSlideListView) view).getHeaderHeight();
                }
            }

            return offset;
        }


        @Override
        public boolean onLayoutChild(CoordinatorLayout parent, CardSlideListView child, int layoutDirection) {
            parent.onLayoutChild(child, layoutDirection);
            CardSlideListView preChild = getPreChild(parent, child);
            if (preChild != null) {
                int offset = preChild.getTop() + preChild.getHeaderHeight();
                mInitialOffset = offset;
                child.offsetTopAndBottom(offset);
            }

            return true;
        }


        private CardSlideListView getPreChild(CoordinatorLayout parent, CardSlideListView child) {
            CardSlideListView preChild = null;
            int childIndex = parent.indexOfChild(child);
            for (int i = childIndex - 1; i >= 0; i--) {
                View childAt = parent.getChildAt(i);
                if (childAt instanceof CardSlideListView) {
                    preChild = (CardSlideListView) childAt;
                    break;
                }
            }
            return preChild;
        }


        @Override
        public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, CardSlideListView child, View directTargetChild, View target, int nestedScrollAxes) {

            boolean isVertical = (nestedScrollAxes & ViewCompat.SCROLL_AXIS_VERTICAL) != 0;
            return isVertical && child == directTargetChild;
        }

        @Override
        public void onNestedPreScroll(CoordinatorLayout coordinatorLayout, CardSlideListView child, View target, int dx, int dy, int[] consumed) {

            if (child.getTop() > mInitialOffset) {
                scroll(child, dy, mInitialOffset, child.getHeight() + mInitialOffset - child.getHeaderHeight());
            }
            super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed);
        }


        @Override
        public void onNestedScroll(CoordinatorLayout coordinatorLayout, CardSlideListView child, View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
            scroll(child, dyUnconsumed, mInitialOffset, child.getHeight() + mInitialOffset - child.getHeaderHeight());
        }




        private int scroll(CardSlideListView child, int dy, int minOffset, int maxOffset) {
            int delta = clamp(child.getTop() - dy, minOffset, maxOffset) - child.getTop();
            child.offsetTopAndBottom(delta);
            return 0;
        }


        int clamp(int offset, int minOffset, int maxOffset) {
            if (minOffset > offset) {
                return minOffset;
            } else if (offset > maxOffset) {
                return maxOffset;
            } else {
                return offset;
            }
        }
    }
}
