package com.demo.nerdlauncher.activity;

import android.support.v4.app.Fragment;

import com.demo.nerdlauncher.fragment.NerdLauncherFragment;

/**
 * Created by yufei0213 on 2017/2/5.
 */
public class NerdLauncherActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {

        return NerdLauncherFragment.newInstance();
    }
}
