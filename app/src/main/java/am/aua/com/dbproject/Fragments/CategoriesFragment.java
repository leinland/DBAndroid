package am.aua.com.dbproject.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import am.aua.com.dbproject.ExpandableListDataPump;
import am.aua.com.dbproject.R;
import am.aua.com.dbproject.Utils.CustomExpandableListAdapter;
import am.aua.com.dbproject.Utils.ServerCallback;
import am.aua.com.dbproject.Utils.SubCategory;

public class CategoriesFragment extends Fragment {

    public static final int TAG = 999892;
    public
    ExpandableListView expandableListView;
    ExpandableListAdapter expandableListAdapter;
    List<String> expandableListTitle;
    HashMap<String, List<SubCategory>> expandableListDetail;

    Context context;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable final Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.category_fragment, container, false);
        expandableListView = v.findViewById(R.id.expandable_list);


        ExpandableListDataPump.getInstance(context).request(new ServerCallback() {
            @Override
            public void onSuccess(HashMap<String, List<SubCategory>> result) {
                expandableListDetail = result;
                expandableListTitle = new ArrayList<>(expandableListDetail.keySet());


                expandableListAdapter = new CustomExpandableListAdapter(context, expandableListTitle, expandableListDetail);
                expandableListView.setAdapter(expandableListAdapter);


                expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
                    @Override
                    public void onGroupExpand(int groupPosition) {
                        Toast.makeText(context,
                                expandableListTitle.get(groupPosition) + " List Expanded.",
                                Toast.LENGTH_SHORT).show();

                    }
                });

                expandableListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
                    @Override
                    public void onGroupCollapse(int groupPosition) {
                        Toast.makeText(context,
                                expandableListTitle.get(groupPosition) + " List Collapsed.",
                                Toast.LENGTH_SHORT).show();

                    }
                });
                expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
                    @Override
                    public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                        int subCategoryId = expandableListDetail.get(expandableListTitle.get(groupPosition)).get(childPosition).getSubCategoryId();

                        FragmentManager manager = getFragmentManager();
                        FragmentTransaction transition = manager.beginTransaction();
                        transition.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);

                        Bundle bundle = new Bundle();
                        bundle.putInt("subInt", subCategoryId);
                        BooksRecyclerFragment detailsFragment = new BooksRecyclerFragment();
                        detailsFragment.setArguments(bundle);
                        transition.replace(R.id.fragmentPlaceHolder, detailsFragment);
                        transition.commit();

                        return true;
                    }
                });
            }
        });

        return v;
    }

    @Override
    public void onAttach(Context context) {
        this.context = context;
        super.onAttach(context);
    }
}