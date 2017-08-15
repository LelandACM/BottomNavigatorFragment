package cn.sunet.net.bottomnavigatorfragment;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Author:  Leland
 * Emial:   AC_Dreamer@163.com
 * Website: www.sunet.net.cn
 * Date:    2017/8/15
 * Function: custom view pager has a switch that has the function what can disable sliding page
 */

public class CustomViewPager extends ViewPager {

    // the sliding page switch
    private boolean isSlidingEnable = true ;

    public CustomViewPager(Context context) {
        super(context);
    }

    public CustomViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return  this.isSlidingEnable && super.onTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return this.isSlidingEnable && super.onInterceptTouchEvent(ev);
    }

    public void setSlidingEnable(boolean slidingEnable) {
        isSlidingEnable = slidingEnable;
    }
}
