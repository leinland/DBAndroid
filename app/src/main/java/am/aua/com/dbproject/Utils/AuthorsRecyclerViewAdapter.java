package am.aua.com.dbproject.Utils;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import am.aua.com.dbproject.Fragments.BooksRecyclerFragment;
import am.aua.com.dbproject.R;

public class AuthorsRecyclerViewAdapter extends RecyclerView.Adapter<AuthorsRecyclerViewAdapter.CustomViewHolder> {

    FragmentManager manager;
    private Context context;
    private List<AuthorItem> authorItems;

    public AuthorsRecyclerViewAdapter(Context context, List<AuthorItem> authorItemList, FragmentManager manager) {
        authorItems = authorItemList;
        this.context = context;
        this.manager = manager;
    }

    //
    @Override
    public AuthorsRecyclerViewAdapter.CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.book_recycler_item, parent, false);
        return new AuthorsRecyclerViewAdapter.CustomViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        AuthorsRecyclerViewAdapter.CustomViewHolder viewHolder = holder;
        AuthorItem currentItem = authorItems.get(position);
        viewHolder.firstName.setText(currentItem.getFirst_name());
        viewHolder.lastName.setText(currentItem.getLast_name());
    }


    @Override
    public int getItemCount() {
        if (!authorItems.isEmpty()) {
            return authorItems.size();
        } else return 0;
    }

    class CustomViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView firstName;
        private TextView lastName;
//        private OnItemClickListener mListener;


        public CustomViewHolder(View itemView) {
            super(itemView);
            this.firstName = itemView.findViewById(R.id.bookTitle);
            this.lastName = itemView.findViewById(R.id.author_text_view);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            AuthorItem authorItem = authorItems.get(getAdapterPosition());
            FragmentTransaction transition = manager.beginTransaction();
            transition.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            int authorID = authorItem.getId();

            Bundle bundle = new Bundle();
            bundle.putInt("int", authorID);
            BooksRecyclerFragment detailsFragment = new BooksRecyclerFragment();
            detailsFragment.setArguments(bundle);
            transition.replace(R.id.fragmentPlaceHolder, detailsFragment);
            transition.commit();

        }


    }
}
