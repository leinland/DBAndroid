package am.aua.com.dbproject.Fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import am.aua.com.dbproject.R;
import am.aua.com.dbproject.Utils.BookItem;

public class DetailsFragment extends Fragment {
    String url = "http://192.168.7.160:8080/admin/take-book";
    Context context;
    JSONObject jsonBody;
    TextView bookQuantity;

    @Override
    public void onAttach(Context context) {
        this.context = context;
        super.onAttach(context);
    }
    BookItem bookItem;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable final Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.details_fragment, container, false);

        bookItem = (BookItem) getArguments().getSerializable("listItem");

        TextView bookName = view.findViewById(R.id.book_name_details);
        TextView authorName = view.findViewById(R.id.author_id_details);
        TextView bookDescription = view.findViewById(R.id.book_description_details);
        bookQuantity = view.findViewById(R.id.book_quantity_details);
        Button takeButton = view.findViewById(R.id.take_button);



        takeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Integer.valueOf(bookQuantity.getText().toString()) > 1) {
                    showDialog();
                } else Toast.makeText(context, "Cannot borrow book", Toast.LENGTH_SHORT).show();
            }
        });
        bookName.setText(bookItem.getName());
        authorName.setText(bookItem.getAuthor());
        bookDescription.setText(bookItem.getDescription());
        bookQuantity.setText(String.valueOf(bookItem.getTotal_amount()));


        return view;
    }

    void showDialog() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
        alertDialog.setMessage("Enter User Id");

        final EditText input = new EditText(context);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        input.setLayoutParams(lp);
        alertDialog.setView(input);

        alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                jsonBody = new JSONObject();
                String userId = input.getText().toString();
                try {
                    jsonBody.put("userId", userId);
                    jsonBody.put("bookId",String.valueOf( bookItem.getId()));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                sendRequest();
            }
        });

        alertDialog.setNegativeButton("CANCEL",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

        alertDialog.show();
    }

    void sendRequest() {
        RequestQueue queue = Volley.newRequestQueue(context);


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url, jsonBody,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show();
                        int a =  Integer.valueOf(bookQuantity.getText().toString());
                        a--;
                        bookQuantity.setText(String.valueOf(a));
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
}
