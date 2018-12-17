package am.aua.com.dbproject.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import am.aua.com.dbproject.R;
import am.aua.com.dbproject.Utils.BlackListItem;
import am.aua.com.dbproject.Utils.BlackListRecyclerAdapter;

public class BlackListFragment extends Fragment {
    public static final int TAG = 565;
    RecyclerView recyclerView;
    List<BlackListItem> blackListItems;
    Context context;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        blackListItems = new ArrayList<>();

        super.onCreate(savedInstanceState);
    }
    String url ;//= "http://10.5.0.135:8080/admin/black-list";
    RequestQueue requestQueue;
    LinearLayout layout;

    @Override
    public void onAttach(Context context) {
        url = getString(R.string.url_root) + "admin/black-list";
        this.context = context;
        super.onAttach(context);
        requestQueue =  Volley.newRequestQueue(context);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.)
        View view = inflater.inflate(R.layout.black_list_fragment,container,false);
        recyclerView = view.findViewById(R.id.blackListFragmentRecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        request();
        return view;
    }


    void request() {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Do something with response
                        //mTextView.setText(response.toString());

                        // Process the JSON
                        try {
                            // Get the JSON array
                            JSONArray array = response.getJSONArray("data");

                            // Loop through the array elements
                            for (int i = 0; i < array.length(); i++) {
                                // Get current json object
                                JSONObject borrows = array.getJSONObject(i);
                                Gson gson = new Gson();
                                BlackListItem blackListItem = gson.fromJson(borrows.toString(), BlackListItem.class);
                                blackListItems.add(blackListItem);
                            }
                            recyclerView.setAdapter(new BlackListRecyclerAdapter(context,blackListItems,getFragmentManager()));

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Do something when error occurred
                        Snackbar.make(
                                layout,
                                "Error.",
                                Snackbar.LENGTH_LONG
                        ).show();
                    }
                }
        );
        requestQueue.add(jsonObjectRequest);
    }
}
