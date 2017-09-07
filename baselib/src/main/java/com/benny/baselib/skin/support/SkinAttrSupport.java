package com.benny.baselib.skin.support;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import com.benny.baselib.skin.attr.SkinAttr;
import com.benny.baselib.skin.attr.SkinType;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yuanbb on 2017/9/7.
 */

public class SkinAttrSupport {


    public static List<SkinAttr> getSkinAttrs(Context context, AttributeSet attrs) {
        if (context == null || attrs == null) {
            return null;
        }
        List<SkinAttr> result = new ArrayList<>();

        for (int i = 0, count = attrs.getAttributeCount(); i < count; i++) {
            String attributeName = attrs.getAttributeName(i);
            String attributeValue = attrs.getAttributeValue(i);
            SkinType skinType = getSkinType(attributeName);

            if (skinType != null) {
                attributeValue = getResName(context, attributeValue);
                if (!TextUtils.isEmpty(attributeName)) {
                    SkinAttr attr = new SkinAttr(attributeValue, skinType);
                    result.add(attr);
                }
            }
        }
        return result;

    }

    private static String getResName(Context context, String attributeValue) {
        if (!TextUtils.isEmpty(attributeValue) && attributeValue.startsWith("@")) {
            return context.getResources().getResourceEntryName(Integer.parseInt(attributeValue.substring(1)));
        }
        return null;
    }

    private static SkinType getSkinType(String attributeName) {
        SkinType[] types = SkinType.values();
        for (SkinType type : types) {
            if (type.getResName().equals(attributeName)) {
                return type;
            }
        }
        return null;
    }


}
