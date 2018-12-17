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
import am.aua.com.dbproject.Utils.BookItem;
import am.aua.com.dbproject.Utils.BooksRecyclerViewAdapter;

public class BooksRecyclerFragment extends Fragment {

    public static final int TAG = 7832;
    Context context;
    List<BookItem> bookList = new ArrayList<>();
    ConstraintLayout rootView;
    String dummyJson;
    String url = "http://192.168.7.160:8080/admin/books";
    String subUrl = "http://192.168.7.160:8080/admin/books/category/";

    RequestQueue requestQueue;
    RecyclerView recyclerView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bookList = new ArrayList<>();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
        requestQueue = Volley.newRequestQueue(context);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.books_fragment, container, false);
        rootView = view.findViewById(R.id.rootView);
        recyclerView = view.findViewById(R.id.books_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
//        BooksRecyclerViewAdapter adapter = new BooksRecyclerViewAdapter(context, bookList,getFragmentManager());
//        recyclerView.setAdapter(adapter);


        int a = getArguments().getInt("int", -1);
        if (a != -1) {
            String id = String.valueOf(a);
            url = url + "/author/" + id;
            request(url
            );
        }
        int b = getArguments().getInt("subInt", -1);
        if (b != -1) {
            String id = String.valueOf(b);
            subUrl = subUrl + id;
            request(subUrl);
        }
        return view;
    }

    void request(String url) {
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
                                BookItem bookItem = gson.fromJson(borrows.toString(), BookItem.class);
                                bookList.add(bookItem);
                            }
                            recyclerView.setAdapter(new BooksRecyclerViewAdapter(context, bookList, getFragmentManager()));
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
                                rootView,
                                "Error.",
                                Snackbar.LENGTH_LONG
                        ).show();
                    }
                }
        );
        requestQueue.add(jsonObjectRequest);
    }

}
