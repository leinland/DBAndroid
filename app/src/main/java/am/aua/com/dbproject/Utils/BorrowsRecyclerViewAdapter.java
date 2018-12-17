package am.aua.com.dbproject.Utils;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import am.aua.com.dbproject.R;

public class BorrowsRecyclerViewAdapter extends RecyclerView.Adapter<BorrowsRecyclerViewAdapter.CustomViewHolder> {//6
    FragmentManager manager;
    String url = "http://192.168.7.160:8080/admin/return";
    private Context context;
    private List<BorrowsItems> borrowsItems;

    public BorrowsRecyclerViewAdapter(Context context, List<BorrowsItems> eventItemList, FragmentManager manager) {
        this.context = context;
        borrowsItems = eventItemList;
        this.manager = manager;
    }

    //
    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.borrows_recycler_item, parent, false);
        return new CustomViewHolder(v);
    }


    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {
        //11. and now the ViewHolder data
        final CustomViewHolder viewHolder = holder;
        final BorrowsItems currentItem = borrowsItems.get(position);
        viewHolder.userId.setText(String.valueOf(currentItem.getUserId()));
        viewHolder.bookId.setText(String.valueOf(currentItem.getBookId()));
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        Date netDate = (new Date(currentItem.getTake_date()));
        Date anotherNetDate = (new Date(currentItem.getReturn_date()));
        viewHolder.borrowDate.setText(sdf.format(netDate));
        viewHolder.returnDate.setText(sdf.format(anotherNetDate));
        viewHolder.checkBox.setChecked(currentItem.isReturn_status());
        viewHolder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    JSONObject jsonBody = new JSONObject();
                    jsonBody.put("userId", String.valueOf(currentItem.getUserId()));
                    jsonBody.put("bookId", String.valueOf(currentItem.getBookId()));
                    sendRequest(jsonBody,viewHolder.checkBox,viewHolder.button);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        if (!borrowsItems.isEmpty()) {
            return borrowsItems.size();
        } else return 0;
    }

    void sendRequest(JSONObject jsonBody,final CheckBox checkBox, final Button button) {
        RequestQueue queue = Volley.newRequestQueue(context);
        url = context.getString(R.string.url_root) + "admin/return";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PUT, url, jsonBody,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show();
                        checkBox.setChecked(true);
                        button.setEnabled(false);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("TAG", error.getMessage(), error);
            }
        }) { //no semicolon or coma
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/json");
                return params;
            }
        };
        queue.add(jsonObjectRequest);
    }


class CustomViewHolder extends RecyclerView.ViewHolder {

    BorrowsItems listItem;
    private TextView userId;
    private TextView bookId;
    private TextView borrowDate;
    private TextView returnDate;
    private CheckBox checkBox;
    private Button button;

    public CustomViewHolder(View itemView) {
        super(itemView);
        this.userId = itemView.findViewById(R.id.borrowsUserId);
        this.bookId = itemView.findViewById(R.id.bookId);
        this.borrowDate = itemView.findViewById(R.id.take_date);
        this.returnDate = itemView.findViewById(R.id.return_date);
        this.checkBox = itemView.findViewById(R.id.borrow_status);
        this.button = itemView.findViewById(R.id.return_button);
    }


}
}