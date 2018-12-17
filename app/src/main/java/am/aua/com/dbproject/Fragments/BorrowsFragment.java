package am.aua.com.dbproject.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import am.aua.com.dbproject.R;
import am.aua.com.dbproject.Utils.BorrowsItems;
import am.aua.com.dbproject.Utils.BorrowsRecyclerViewAdapter;

public class BorrowsFragment extends Fragment {
    public static final int TAG = 565;
    RecyclerView recyclerView;
    List<BorrowsItems> borrowsItems;
    Context context;
    String url = "http://192.168.7.160:8080/admin/borrow";
    RequestQueue requestQueue;
    LinearLayout layout;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        url = getString(R.string.url_root) + "admin/borrow";

        borrowsItems = new ArrayList<>();
    }

    @Override
    public void onAttach(Context context) {
        this.context = context;
        requestQueue =  Volley.newRequestQueue(context);
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.)
        View view = inflater.inflate(R.layout.borrows_fragment, container, false);
        layout = view.findViewById(R.id.linear);
        recyclerView = view.findViewById(R.id.borrowRecycler);
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
                                BorrowsItems borrowsItem = gson.fromJson(borrows.toString(), BorrowsItems.class);
                                borrowsItems.add(borrowsItem);
                            }
                            recyclerView.setAdapter(new BorrowsRecyclerViewAdapter(context,borrowsItems, getFragmentManager()));

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
