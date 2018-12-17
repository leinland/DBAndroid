package am.aua.com.dbproject;

import android.content.Context;
import android.widget.Toast;

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

import am.aua.com.dbproject.Utils.Categories;
import am.aua.com.dbproject.Utils.ServerCallback;
import am.aua.com.dbproject.Utils.SubCategory;

public class ExpandableListDataPump {
    private static ExpandableListDataPump instance;
    private static Context mContext;
    RequestQueue requestQueue;
    HashMap<String, List<SubCategory>> expandableListDetail = new HashMap<>();
    private List<Categories> categoriesList = new ArrayList<>();
    private String url = "http://192.168.7.160:8080/admin/categories";

    public static ExpandableListDataPump getInstance(Context context) {
        if (instance == null) {
            instance = new ExpandableListDataPump();
        }
        mContext = context;
        return instance;
    }


//    public HashMap<String, List<String>> getData() {
//
//        HashMap<String, List<String>> expandableListDetail = new HashMap<>();
//
//        List<String> cricket = new ArrayList<String>();
//        cricket.add("India");
//        cricket.add("Pakistan");
//        cricket.add("Australia");
//        cricket.add("England");
//        cricket.add("South Africa");
//
//        List<String> football = new ArrayList<String>();
//        football.add("Brazil");
//        football.add("Spain");
//        football.add("Germany");
//        football.add("Netherlands");
//        football.add("Italy");
//
//        List<String> basketball = new ArrayList<String>();
//        basketball.add("United States");
//        basketball.add("Spain");
//        basketball.add("Argentina");
//        basketball.add("France");
//        basketball.add("Russia");
//
//        expandableListDetail.put("CRICKET TEAMS", cricket);
//        expandableListDetail.put("FOOTBALL TEAMS", football);
//        expandableListDetail.put("BASKETBALL TEAMS", basketball);
//        return expandableListDetail;
//    }

    public void request(final ServerCallback callback) {
        url = mContext.getString(R.string.url_root) + "admin/categories";


        requestQueue = Volley.newRequestQueue(mContext);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
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
                        Categories categories = gson.fromJson(borrows.toString(), Categories.class);
                        expandableListDetail.put(categories.getCategoryName(), categories.getSubCategories());
// categoriesList.add(categories);
                    }
                    callback.onSuccess(expandableListDetail);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(mContext, "Error Accured", Toast.LENGTH_SHORT).show();
                    }
                }
        );
        requestQueue.add(jsonObjectRequest);
    }

    void createData() {
        for (Categories categories : categoriesList) {

        }
    }


}

