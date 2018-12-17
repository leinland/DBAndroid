package am.aua.com.dbproject.Utils;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import am.aua.com.dbproject.Fragments.DetailsFragment;
import am.aua.com.dbproject.R;

public class BooksRecyclerViewAdapter extends RecyclerView.Adapter<BooksRecyclerViewAdapter.CustomViewHolder>  {//6
    private Context context;
    private List<BookItem> eventItems;
    FragmentManager manager;

    public BooksRecyclerViewAdapter(Context context, List<BookItem> eventItemList, FragmentManager manager) {
        this.context = context;
        eventItems = eventItemList;
        this.manager = manager;
    }

    //
    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType ) {
        View v = LayoutInflater.from(context).inflate(R.layout.book_recycler_item, parent, false);
        return new CustomViewHolder(v);
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {
        //11. and now the ViewHolder data
        CustomViewHolder viewHolder = holder;
        BookItem currentItem = eventItems.get(position);
        viewHolder.title.setText(currentItem.getName());
        viewHolder.author.setText(currentItem.getAuthor());
        viewHolder.year.setText(currentItem.getYear() + "");
    }

    @Override
    public int getItemCount() {
        if (!eventItems.isEmpty()) {
            return eventItems.size();
        } else return 0;
    }
//    private OnItemClickListener onItemClickListener;
//    // ...
//    public interface OnItemClickListener {
//        // note: here you would need some params, for instance the view
//        void onItemClick(View v);
//    }
//
//    public void setOnItemClickListener(OnItemClickListener l) {
//        this.onItemClickListener = l;
//    }

    class CustomViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView title;
        private TextView author;
        private TextView year;
//        private OnItemClickListener mListener;


        public CustomViewHolder(View itemView) {
            super(itemView);
            this.title = itemView.findViewById(R.id.bookTitle);
            this.author = itemView.findViewById(R.id.author_text_view);
            this.year = itemView.findViewById(R.id.date_text);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            BookItem listItem = eventItems.get(getAdapterPosition());
            FragmentTransaction transition = manager.beginTransaction();
            transition.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            Bundle bundle = new Bundle();
            bundle.putSerializable("listItem", listItem);
            DetailsFragment detailsFragment = new DetailsFragment();
            detailsFragment.setArguments(bundle);
            transition.replace(R.id.fragmentPlaceHolder, detailsFragment);
            transition.commit();

        }
    }
}