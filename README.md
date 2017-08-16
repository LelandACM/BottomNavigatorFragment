# BottomNavigatorFragment
###如何快速搭建一个优雅的主UI框架？这篇文章主要是关于这个来写的。
>我采用的是1个Activity+ 多个Fragment的这种模式，像支付宝底部有4个Tab并且页面是禁止滑动翻页的，而微信底部也是4个Tab但是可以滑动翻页。这篇文章就是用ViewPager+BottomNavigationView+多个Fragment快速搭建一个UI框架,当然啦你也可以使用[BottomBar][] + FrameLayout搭建类似的UI框架。其中主要的思路是来至于[Stackoverflow]

**一、 创建四个`Fragment`页面设置适配器到`ViewPager`**
```//创建四个Fragment页面设置适配器到ViewPager
private void setupViewPager(ViewPager viewPager) {
    List<Fragment> fragmentList = new ArrayList<>();
    // add four fragment page to list as the viewpager content
    fragmentList.add(NewFragment.newInstance(getString(R.string.title_home)));
    fragmentList.add(NewFragment.newInstance(getString(R.string.title_dashboard)));
    fragmentList.add(NewFragment.newInstance(getString(R.string.title_notifications)));
    fragmentList.add(NewFragment.newInstance(getString(R.string.title_me)));
    FragmentPageAdapter adapter = new FragmentPageAdapter(getSupportFragmentManager(), fragmentList);
    viewPager.setAdapter(adapter);
}
```
**二、 点击不同的Item监听页面变化事件设置按钮的焦点变化(颜色会自动变化为`colorPrimary`颜色)**
```
private ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.OnPageChangeListener() {
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }
    @Override
    public void onPageSelected(int position) {
        //if the item is not null , now set it check false
        if (mMenuItem != null)
            mMenuItem.setChecked(false);
        else //set the first bottom view check false
            navigation.getMenu().getItem(0).setChecked(false);
        // get the current select menu item and then focus it
        mMenuItem = navigation.getMenu().getItem(position);
        mMenuItem.setChecked(true);
    }
    @Override
    public void onPageScrollStateChanged(int state) {
    }
};
```
<<<<<<< HEAD
**三、  点击不同的按钮相应对应的item，随之切换显示相应的`Fragment` 页面**
=======
**三、  点击不同的按钮相应对应的item，随之切换显示相应的`Fragment` 页面
>>>>>>> origin/master
```
private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
        = new BottomNavigationView.OnNavigationItemSelectedListener() {
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.navigation_home:
                viewPager.setCurrentItem(0);
                return true;
            case R.id.navigation_dashboard:
                viewPager.setCurrentItem(1);
                return true;
            case R.id.navigation_notifications:
                viewPager.setCurrentItem(2);
                return true;
            case R.id.navigation_me:
                viewPager.setCurrentItem(3);
                return true;
        }
        return false;
    }
};
```
**四、 创建自定义`ViewPager`重写`onTouchEvent`和`onInterceptTouchEvent`事件并且向外暴露一个是否可滑动页面的`isSlidingEnable`开关默认开启滑动翻页**
```
public class CustomViewPager extends ViewPager {

    // the sliding page switch
    private boolean isSlidingEnable = true ;

    public CustomViewPager(Context context) {
        super(context);
    }

    public CustomViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    //重写此函数
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return  this.isSlidingEnable && super.onTouchEvent(ev);
    }
    //重写此函数
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return this.isSlidingEnable && super.onInterceptTouchEvent(ev);
    }

    public void setSlidingEnable(boolean slidingEnable) {
        isSlidingEnable = slidingEnable;
    }
}
```

当Item大于3时，Item动画变化非常夸张,感受一下

![animation.gif](http://upload-images.jianshu.io/upload_images/4073499-cddcc005e56970cd.gif?imageMogr2/auto-orient/strip)

话说Google这样做谁遵循`Material design` 

**五、创建`BottomNavigationViewHelper`类禁用掉shiftingMode 模式**
```
public class BottomNavigationViewHelper {
    static void removeShifMode(BottomNavigationView view) {
        BottomNavigationMenuView menuView = (BottomNavigationMenuView) view.getChildAt(0);
        try {
            Field shiftingMode = menuView.getClass().getDeclaredField("mShiftingMode");
            shiftingMode.setAccessible(true);
            shiftingMode.setBoolean(menuView, false);
            shiftingMode.setAccessible(false);
            for (int i = 0; i < menuView.getChildCount(); ++i) {
                BottomNavigationItemView itemView = (BottomNavigationItemView) menuView.getChildAt(i);
                itemView.setShiftingMode(false);
                //set once again checked value , so view will be updated
                itemView.setChecked(itemView.getItemData().isChecked());
            }

        } catch (NoSuchFieldException e) {
            Log.e("ERROR NO SUCH FIELD", "Unable to get shift mode field");
        } catch (IllegalAccessException e) {
            Log.e("ERROR ILLEGAL ALG", "Unable to change value of shift mode");
        }
    }
}
```
**六、使用这个使用两个功能：禁止滑动翻页、禁用`ShiftingMode`动画**
```
//禁用滑动翻页时设置为false,否则为true
viewPager.setSlidingEnable(false);
//移除shiftingMode动画
BottomNavigationViewHelper.removeShifMode(navigation);
//监听按钮选择事件
navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
//为Viewpager设置页面
setupViewPager(viewPager);
//监听viewPager变化事件
viewPager.addOnPageChangeListener(onPageChangeListener);
```
最终效果如下一个是可以滑动翻页、一个是禁用

![不禁用.gif](http://upload-images.jianshu.io/upload_images/4073499-a00676fb5b84adef.gif?imageMogr2/auto-orient/strip)![禁止滑动翻页.gif](http://upload-images.jianshu.io/upload_images/4073499-d1349f55a65c5fc7.gif?imageMogr2/auto-orient/strip)
<<<<<<< HEAD

**七、参考链接**

=======
**七、参考链接**
>>>>>>> origin/master
1.禁止滑动翻页:<https://stackoverflow.com/questions/9650265/how-do-disable-paging-by-swiping-with-finger-in-viewpager-but-still-be-able-to-s> `[Rajul](https://stackoverflow.com/users/1097358/rajul)`的回答更好

2.禁止ShiftingMode动画<https://stackoverflow.com/questions/40972293/remove-animation-shifting-mode-from-bottomnavigationview-android>

**八、简书博客传送门**
<https://github.com/GitACDreamer/BottomNavigatorFragment>

希望大家多多关注star and fork，各位觉得如果有帮助给个赞，谢谢！

[BottomBar]: https://github.com/roughike/BottomBar/
[Stackoverflow]: https://stackoverflow.com/
