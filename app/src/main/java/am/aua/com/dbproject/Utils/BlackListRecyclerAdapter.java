package am.aua.com.dbproject.Utils;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import am.aua.com.dbproject.R;

public class BlackListRecyclerAdapter extends RecyclerView.Adapter<BlackListRecyclerAdapter.CustomViewHolder> {

    FragmentManager manager;
    private Context context;
    private List<BlackListItem> blackListItems;

    public BlackListRecyclerAdapter(Context context, List<BlackListItem> authorItemList, FragmentManager manager) {
        blackListItems = authorItemList;
        this.context = context;
        this.manager = manager;
    }

    //
    @Override
    public BlackListRecyclerAdapter.CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.black_list_item, parent, false);
        return new BlackListRecyclerAdapter.CustomViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull BlackListRecyclerAdapter.CustomViewHolder holder, int position) {
        BlackListRecyclerAdapter.CustomViewHolder viewHolder = holder;
        BlackListItem currentItem = blackListItems.get(position);
        viewHolder.name.setText(currentItem.getName());
        viewHolder.id.setText(String.valueOf(currentItem.getId()));

        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        Date netDate = (new Date(currentItem.getEndDate()));
        viewHolder.date.setText(sdf.format(netDate));
    }


    @Override
    public int getItemCount() {
        if (!blackListItems.isEmpty()) {
            return blackListItems.size();
        } else return 0;
    }

    class CustomViewHolder extends RecyclerView.ViewHolder {

        private TextView name;
        private TextView id;
        private TextView date;

        //        private OnItemClickListener mListener;
        public CustomViewHolder(View itemView) {
            super(itemView);
            this.name = itemView.findViewById(R.id.blackListName);
            this.id = itemView.findViewById(R.id.blackListId);
            this.date = itemView.findViewById(R.id.blackListDate);
        }
    }
}
