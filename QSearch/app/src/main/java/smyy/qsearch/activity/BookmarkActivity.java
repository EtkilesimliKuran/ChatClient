package smyy.qsearch.activity;

import android.support.v4.view.GravityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import smyy.qsearch.R;
import smyy.qsearch.adapter.BookMarkAdapter;
import smyy.qsearch.helper.Database;
import smyy.qsearch.helper.ItemOffsetDecoration;
import smyy.qsearch.model.Message;

public class BookmarkActivity extends AppCompatActivity {

    public static Database db;
    List<Message> bookmarks;
    BookMarkAdapter bookMarkAdapter;
    RecyclerView list_bookmark;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookmark);
        db = new Database(BookmarkActivity.this);
        list_bookmark=(RecyclerView) findViewById(R.id.listBookMark);
        bookmarks=db.getAllBookMarks();

        getSupportActionBar().setTitle("Not Defteri");
        if (bookmarks != null) {
            bookMarkAdapter = new BookMarkAdapter(this, bookmarks);
            RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(BookmarkActivity.this, 1);
            list_bookmark.setLayoutManager(mLayoutManager);
            ItemOffsetDecoration itemDecoration = new ItemOffsetDecoration(BookmarkActivity.this, R.dimen.item_offset);
            list_bookmark.addItemDecoration(itemDecoration);
            list_bookmark.setItemAnimator(new DefaultItemAnimator());
            list_bookmark.setAdapter(bookMarkAdapter);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_bookmark, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_remove_message:
                ResetTable();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void ResetTable(){
        db.resetTableBookMark();
        bookmarks=new ArrayList<>();
        bookMarkAdapter = new BookMarkAdapter(this, bookmarks);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(BookmarkActivity.this, 1);
        list_bookmark.setLayoutManager(mLayoutManager);
        list_bookmark.setItemAnimator(new DefaultItemAnimator());
        list_bookmark.setAdapter(bookMarkAdapter);
    }

}
