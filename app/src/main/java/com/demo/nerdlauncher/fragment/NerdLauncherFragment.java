package com.demo.nerdlauncher.fragment;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.demo.nerdlauncher.R;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by yufei0213 on 2017/2/5.
 */
public class NerdLauncherFragment extends Fragment {

    private static final String Tag = "NerdLauncherFragment";

    private RecyclerView recyclerView;

    public static NerdLauncherFragment newInstance() {

        return new NerdLauncherFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_nerd_launcher, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.fragment_nerd_launcher_recycle_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        setupAdapter();

        return view;
    }

    private void setupAdapter() {

        Intent startupIntent = new Intent(Intent.ACTION_MAIN);
        startupIntent.addCategory(Intent.CATEGORY_LAUNCHER);

        final PackageManager pm = getActivity().getPackageManager();
        List<ResolveInfo> activities = pm.queryIntentActivities(startupIntent, 0);

        Collections.sort(activities, new Comparator<ResolveInfo>() {
            @Override
            public int compare(ResolveInfo a, ResolveInfo b) {

                return String.CASE_INSENSITIVE_ORDER
                        .compare(a.loadLabel(pm).toString(),
                                b.loadLabel(pm).toString());
            }
        });

        recyclerView.setAdapter(new ActivityAdapter(activities));
    }

    private class ActivityAdapter extends RecyclerView.Adapter<ActivityHolder> {

        private List<ResolveInfo> activities;

        public ActivityAdapter(List<ResolveInfo> activities) {

            this.activities = activities;
        }

        @Override
        public ActivityHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            LayoutInflater inflater = LayoutInflater.from(getActivity());
            View view = inflater.inflate(android.R.layout.simple_list_item_1, parent, false);

            return new ActivityHolder(view);
        }

        @Override
        public void onBindViewHolder(ActivityHolder holder, int position) {

            holder.bindActivity(activities.get(position));
        }

        @Override
        public int getItemCount() {

            return activities.size();
        }
    }

    private class ActivityHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ResolveInfo resolveInfo;
        private TextView textView;

        public ActivityHolder(View itemView) {

            super(itemView);

            textView = (TextView) itemView;
            itemView.setOnClickListener(this);
        }

        public void bindActivity(ResolveInfo resolveInfo) {

            this.resolveInfo = resolveInfo;
            PackageManager pm = getActivity().getPackageManager();
            textView.setText(resolveInfo.loadLabel(pm).toString());
        }

        @Override
        public void onClick(View view) {

            ActivityInfo activityInfo = resolveInfo.activityInfo;

            Intent intent = new Intent(Intent.ACTION_MAIN)
                    .setClassName(activityInfo.applicationInfo.packageName, activityInfo.name)
                    .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            startActivity(intent);
        }
    }
}
