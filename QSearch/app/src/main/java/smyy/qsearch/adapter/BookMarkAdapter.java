package smyy.qsearch.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import smyy.qsearch.R;
import smyy.qsearch.fragment.FragmentMeal;
import smyy.qsearch.helper.Database;
import smyy.qsearch.model.Message;

/**
 * Created by Sümeyye on 30.4.2017.
 */

public class BookMarkAdapter extends RecyclerView.Adapter<BookMarkAdapter.MyViewHolder> {

    private List<Message> items;
    View vi;
    Context context;
    Message message;
    public static Database db;

    public BookMarkAdapter(Context c, List<Message> items) {
        this.items = items;
        this.context = c;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        vi = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_bookmark, null);
        MyViewHolder vh = new MyViewHolder(vi);
        return vh;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final Message message = items.get(position);
        holder.txtBookmark.setText(message.getMessageContent());

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        public TextView txtBookmark;

        public MyViewHolder(View itemView) {
            super(itemView);
            txtBookmark = (TextView) vi.findViewById(R.id.txtBookmark);
            txtBookmark.setOnClickListener(this);
            db = new Database(context);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            message = items.get(position);
            if (view.getId() == txtBookmark.getId() ) {
                showPopupMenu(view);
            }
        }

        @Override
        public boolean onLongClick(View view) {
            return false;
        }

        private void showPopupMenu(final View view) {
            PopupMenu popup = new PopupMenu(context, view);
            MenuInflater inflater = popup.getMenuInflater();
            inflater.inflate(R.menu.menu_bookmark, popup.getMenu());
            popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.action_remove_message:
                            RemoveBookmark(getAdapterPosition());
                            return true;
                        default:
                    }
                    return false;
                }
            });

            popup.show();
        }

        private void RemoveBookmark(int position){
            db.removeBookMark(message.getMessageID());
            items.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, items.size());
        }
    }
}
