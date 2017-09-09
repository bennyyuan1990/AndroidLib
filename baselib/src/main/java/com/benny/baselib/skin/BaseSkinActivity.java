package com.benny.baselib.skin;

import android.content.Context;
import android.os.Build;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.LayoutInflaterCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.VectorEnabledTintResources;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewParent;
import com.benny.baselib.skin.support.SkinAppCompatViewInflater;
import com.benny.baselib.view.BaseActivity;
import org.xmlpull.v1.XmlPullParser;

/**
 * Created by yuanbb on 2017/9/7.
 */

public class BaseSkinActivity extends BaseActivity {

    private static final String TAG = "BaseSkinActivity";
    private SkinAppCompatViewInflater mSkinAppCompatViewInflater;
    private static boolean IS_PRE_LOLLIPOP = Build.VERSION.SDK_INT < VERSION_CODES.LOLLIPOP;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        LayoutInflaterCompat.setFactory2(getLayoutInflater(), this);
        mSkinAppCompatViewInflater = new SkinAppCompatViewInflater();
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
        View view = getDelegate().createView(parent, name, context, attrs);

        if (view == null) {
            boolean inheritContext = false;
            if (IS_PRE_LOLLIPOP) {
                inheritContext = (attrs instanceof XmlPullParser)
                    ? ((XmlPullParser) attrs).getDepth() > 1
                    : shouldInheritContext((ViewParent) parent);
            }

            view =  mSkinAppCompatViewInflater.createView(parent, name, context, attrs, inheritContext,
                IS_PRE_LOLLIPOP, /* Only read android:theme pre-L (L+ handles this anyway) */
                true, /* Read read app:theme as a fallback at all times for legacy reasons */
                VectorEnabledTintResources.shouldBeUsed() /* Only tint wrap the context if enabled */
            );
        } else if (view == null) {
            view = super.onCreateView(parent, name, context, attrs);
        }

        if (view != null) {
            int attributeCount = attrs.getAttributeCount();
            for (int i = 0; i < attributeCount; i++) {
                String attributeName = attrs.getAttributeName(i);
                String attributeValue = attrs.getAttributeValue(i);
            }


        }
        return view;
    }

    private boolean shouldInheritContext(ViewParent parent) {
        if (parent == null) {
            // The initial parent is null so just return false
            return false;
        }
        final View windowDecor = getWindow().getDecorView();
        while (true) {
            if (parent == null) {
                // Bingo. We've hit a view which has a null parent before being terminated from
                // the loop. This is (most probably) because it's the root view in an inflation
                // call, therefore we should inherit. This works as the inflated layout is only
                // added to the hierarchy at the end of the inflate() call.
                return true;
            } else if (parent == windowDecor || !(parent instanceof View)
                || ViewCompat.isAttachedToWindow((View) parent)) {
                // We have either hit the window's decor view, a parent which isn't a View
                // (i.e. ViewRootImpl), or an attached view, so we know that the original parent
                // is currently added to the view hierarchy. This means that it has not be
                // inflated in the current inflate() call and we should not inherit the context.
                return false;
            }
            parent = parent.getParent();
        }
    }


}
