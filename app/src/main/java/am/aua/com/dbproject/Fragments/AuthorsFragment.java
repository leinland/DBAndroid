package am.aua.com.dbproject.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
import am.aua.com.dbproject.Utils.AuthorItem;
import am.aua.com.dbproject.Utils.AuthorsRecyclerViewAdapter;

public class AuthorsFragment extends Fragment {
    public final static int TAG = 78;
    RecyclerView recyclerView;
    Context context;
    List<AuthorItem> authorItems;
    String url; //"http://10.5.0.135:8080/admin/authors";
    RequestQueue requestQueue;
    ConstraintLayout layout;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        authorItems = new ArrayList<>();

        url = getString(R.string.url_root)+"admin/authors";
//        authorItems.add(new AuthorItem(1,"Ernesto","Parpeci"));
//        authorItems.add(new AuthorItem(1,"Ernesto","Parpeci"));
//        authorItems.add(new AuthorItem(1,"Ernesto","Parpeci"));
//        authorItems.add(new AuthorItem(1,"Ernesto","Parpeci"));
//        authorItems.add(new AuthorItem(1,"Ernesto","Parpeci"));
//        authorItems.add(new AuthorItem(1,"Ernesto","Parpeci"));
//        authorItems.add(new AuthorItem(1,"Ernesto","Parpeci"));
//        authorItems.add(new AuthorItem(1,"Ernesto","Parpeci"));
//        authorItems.add(new AuthorItem(1,"Ernesto","Parpeci"));
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onAttach(Context context) {
        this.context = context;
        requestQueue = Volley.newRequestQueue(context);

        super.onAttach(context);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.author_recycler, container, false);
        recyclerView = v.findViewById(R.id.authors_recycler_view);
        layout = v.findViewById(R.id.layout);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));

        request();
        return v;
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
                                AuthorItem blackListItem = gson.fromJson(borrows.toString(), AuthorItem.class);
                                authorItems.add(blackListItem);
                            }
                            recyclerView.setAdapter(new AuthorsRecyclerViewAdapter(context, authorItems, getFragmentManager()));

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
