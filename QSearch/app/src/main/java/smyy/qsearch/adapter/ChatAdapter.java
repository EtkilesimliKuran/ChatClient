package smyy.qsearch.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import smyy.qsearch.R;
import smyy.qsearch.activity.ChatActivity;
import smyy.qsearch.fragment.FragmentMeal;
import smyy.qsearch.helper.Database;
import smyy.qsearch.model.Message;

/**
 * Created by Sümeyye on 9.4.2017.
 */

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.MyViewHolder> {

    private List<Message> items;
    private static final int MY_MESSAGE = 0, OTHER_MESSAGE = 1;
    View vi;
    Context context;
    String personid;
    Message messageDetail;
    public static Database db;

    public ChatAdapter(Context c, List<Message> items, String personid) {
        this.items = items;
        this.context = c;
        this.personid = personid;
    }

    @Override
    public int getItemViewType(int position) {
        if (items.get(position).getisMine()) {
            return MY_MESSAGE;
        } else {
            return OTHER_MESSAGE;
        }
    }

    @Override
    public ChatAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == MY_MESSAGE) {
            vi = LayoutInflater.from(context).inflate(R.layout.item_mine_message, null);
        } else if (viewType == OTHER_MESSAGE) {
            vi = LayoutInflater.from(context).inflate(R.layout.item_other_message, null);
        }
        ChatAdapter.MyViewHolder vh = new ChatAdapter.MyViewHolder(vi);
        return vh;
    }

    @Override
    public void onBindViewHolder(ChatAdapter.MyViewHolder holder, int position) {
        Message message = items.get(position);
        if(!message.getisMine())
            holder.txtMessageContent.setTextColor(Color.BLACK);
        holder.txtMessageContent.setText(message.getMessageContent());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        public TextView txtMessageContent;

        public MyViewHolder(View itemView) {
            super(itemView);
            txtMessageContent = (TextView) vi.findViewById(R.id.message_content);
            txtMessageContent.setOnLongClickListener(this);
            db = new Database(context);
        }

        @Override
        public void onClick(View view) {

        }

        @Override
        public boolean onLongClick(View view) {
            int position = getAdapterPosition();
            messageDetail = items.get(position);
            if (view.getId() == txtMessageContent.getId()) {
                showPopupMenu(view);
            }
            return false;
        }

        private void showPopupMenu(final View view) {
            PopupMenu popup = new PopupMenu(context, view);
            MenuInflater inflater = popup.getMenuInflater();
            inflater.inflate(R.menu.menu_rmvmsg, popup.getMenu());
            popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.action_add_bookmark:
                            db.insertBookmark(messageDetail);
                        return true;
                        case R.id.action_remove_message:
                            RemoveMessage(getAdapterPosition());
                            return true;
                        case R.id.action_iptal:
                            return true;
                        default:
                    }
                    return false;
                }
            });

            popup.show();
        }

        private void RemoveMessage(int position){
            db.removeMessage(messageDetail.getMessageID());
            items.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, items.size());
        }
    }
}