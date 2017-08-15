package cn.sunet.net.bottomnavigatorfragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.TextView;

import butterknife.BindView;

/**
 * Author:  Leland
 * Emial:   AC_Dreamer@163.com
 * Website: www.sunet.net.cn
 * Date:    2017/8/15
 * Function: the fragment page for view page
 */

public class NewFragment extends SimpleFragment {

    @BindView(R.id.tv_content)
    TextView tvContent;

    private String title;

    public static NewFragment newInstance(String title) {

        Bundle args = new Bundle();

        NewFragment fragment = new NewFragment();
        fragment.title = title;
        fragment.setArguments(args);
        return fragment;
    }

    public static NewFragment newInstance() {
        return newInstance(null);
    }


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_new;
    }

    @Override
    protected void initEventAndData() {
        if (TextUtils.isEmpty(title)) {
            title = getString(R.string.app_name);
        }
        tvContent.setText(title);
    }
}
