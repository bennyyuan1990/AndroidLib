package com.benny.baselib.view.recyclerview;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
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

@DefaultBehavior(SlideCardListView.CardSlideListViewBehavior.class)
public class SlideCardListView extends FrameLayout {

    private static final String TAG = "CardSlideListView";
    private String mHeaderTitle;
    private int mHeaderBackground = Color.parseColor("#ff0000");
    private int mHeaderHeight;
    private TextView mHeaderTv;
    private RecyclerView mListRv;
    private int mInitialOffset;
    private boolean isAnimation = false;

    public int getHeaderHeight() {
        return mHeaderHeight;
    }

    public RecyclerView getListView() {
        return mListRv;
    }


    public SlideCardListView(@NonNull Context context) {
        this(context, null);
    }

    public SlideCardListView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SlideCardListView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        View view = LayoutInflater.from(context).inflate(R.layout.view_card_slide, this);
        mHeaderTv = (TextView) view.findViewById(R.id.view_card_slide_header_tv);
        mListRv = (RecyclerView) view.findViewById(R.id.view_card_slide_list_rv);
        mListRv.setLayoutManager(new LinearLayoutManager(context));

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.SlideCardListView);
        mHeaderTitle = typedArray.getString(R.styleable.SlideCardListView_headerTitle);
        mHeaderBackground = typedArray.getColor(R.styleable.SlideCardListView_headerBackground, mHeaderBackground);
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


    /**
     * 重置初始位置
     */
    public void resetInitialPosition() {
        if (getTop() > mInitialOffset && !isAnimation) {
            isAnimation = true;

            ObjectAnimator.ofPropertyValuesHolder();

            PropertyValuesHolder topHolder = PropertyValuesHolder.ofInt("top", getTop(), mInitialOffset);
            PropertyValuesHolder bottomHolder = PropertyValuesHolder.ofInt("bottom", getBottom() + getHeight(), mInitialOffset + getHeight());
            ObjectAnimator animator = ObjectAnimator.ofPropertyValuesHolder(this,topHolder,bottomHolder).setDuration(500);
            animator.addListener(new AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {

                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    isAnimation = false;
                }

                @Override
                public void onAnimationCancel(Animator animation) {
                    isAnimation = false;
                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });
            animator.start();
        }
    }

    public void setInitialOffset(int offset) {
        mInitialOffset = offset;
    }


    public static class CardSlideListViewBehavior extends CoordinatorLayout.Behavior<SlideCardListView> {

        private int mInitialOffset;

        @Override
        public boolean onMeasureChild(CoordinatorLayout parent, SlideCardListView child, int parentWidthMeasureSpec, int widthUsed, int parentHeightMeasureSpec, int heightUsed) {

            int height = MeasureSpec.getSize(parentHeightMeasureSpec);
            height = height - heightUsed - getMeasuredOffset(parent, child);
            child.measure(parentWidthMeasureSpec, MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY));
            return true;
        }


        private int getMeasuredOffset(CoordinatorLayout parent, SlideCardListView child) {
            int offset = 0;
            for (int i = 0, count = parent.getChildCount(); i < count; i++) {

                View view = parent.getChildAt(i);
                if (view != child && view instanceof SlideCardListView) {
                    offset += ((SlideCardListView) view).getHeaderHeight();
                }
            }

            return offset;
        }


        @Override
        public boolean onLayoutChild(CoordinatorLayout parent, SlideCardListView child, int layoutDirection) {
            parent.onLayoutChild(child, layoutDirection);

            mInitialOffset = getVerticalOffset(parent, child);
            child.offsetTopAndBottom(mInitialOffset);
            child.setInitialOffset(mInitialOffset);

            return true;
        }


        private int getVerticalOffset(CoordinatorLayout parent, SlideCardListView child) {
            int offset = 0;
            int childIndex = parent.indexOfChild(child);
            for (int i = 0; i < childIndex; i++) {
                View childAt = parent.getChildAt(i);

                if (childAt instanceof SlideCardListView) {
                    offset += ((SlideCardListView) childAt).getHeaderHeight();
                }
            }
            return offset;
        }


        private SlideCardListView getPreChild(CoordinatorLayout parent, SlideCardListView child) {
            SlideCardListView preChild = null;
            int childIndex = parent.indexOfChild(child);
            for (int i = childIndex - 1; i >= 0; i--) {
                View childAt = parent.getChildAt(i);
                if (childAt instanceof SlideCardListView) {
                    preChild = (SlideCardListView) childAt;
                    break;
                }
            }
            return preChild;
        }

        private SlideCardListView getNextChild(CoordinatorLayout parent, SlideCardListView child) {
            SlideCardListView nextChild = null;
            int childIndex = parent.indexOfChild(child);
            for (int i = childIndex + 1; i < parent.getChildCount(); i++) {
                View childAt = parent.getChildAt(i);
                if (childAt instanceof SlideCardListView) {
                    nextChild = (SlideCardListView) childAt;
                    break;
                }
            }
            return nextChild;
        }


        @Override
        public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, SlideCardListView child, View directTargetChild, View target, int nestedScrollAxes) {

            boolean isVertical = (nestedScrollAxes & ViewCompat.SCROLL_AXIS_VERTICAL) != 0;
            return isVertical && child == directTargetChild;
        }

        @Override
        public void onNestedPreScroll(CoordinatorLayout coordinatorLayout, SlideCardListView child, View target, int dx, int dy, int[] consumed) {

            if (child.getTop() > mInitialOffset) {
                scroll(child, dy, mInitialOffset, child.getHeight() + mInitialOffset - child.getHeaderHeight());
            }
            super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed);
        }


        @Override
        public void onNestedScroll(CoordinatorLayout coordinatorLayout, SlideCardListView child, View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {

            //ListView滑到最底部，下一个CardSlide向上滑动
            if (dyConsumed == 0 && dyUnconsumed > 0) {
                SlideCardListView nextChild = getNextChild(coordinatorLayout, child);
                if (nextChild != null) {
                    nextChild.resetInitialPosition();
                }
            }

            if (mInitialOffset == 0) {
                return;
            }
            int scrollOffset = scroll(child, dyUnconsumed, mInitialOffset, child.getHeight() + mInitialOffset - child.getHeaderHeight());

            if (scrollOffset > 0) {
                //当前滑动的ListView 列表Item已经是最顶端，整体正在往下滑动
                moveNext(coordinatorLayout, child);
            }
        }

        private void moveNext(CoordinatorLayout coordinatorLayout, SlideCardListView child) {
            SlideCardListView nextChild = getNextChild(coordinatorLayout, child);
            if (nextChild != null) {
                int deltaY = nextChild.getTop() - child.getTop() - nextChild.getHeaderHeight();

                if (deltaY < 0) {
                    nextChild.offsetTopAndBottom(-deltaY);
                    moveNext(coordinatorLayout, nextChild);
                }
            }
        }


        private int scroll(SlideCardListView child, int dy, int minOffset, int maxOffset) {
            int delta = clamp(child.getTop() - dy, minOffset, maxOffset) - child.getTop();
            child.offsetTopAndBottom(delta);
            return delta;
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
