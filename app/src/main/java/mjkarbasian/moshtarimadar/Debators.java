package mjkarbasian.moshtarimadar;

import android.app.SearchManager;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ListView;

import mjkarbasian.moshtarimadar.adapters.DebatorAdapter;

public class Debators extends DrawerActivity {
    DebatorAdapter mDebatorAdapter;
    ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_debators);
        getLayoutInflater().inflate(R.layout.activity_debators,(FrameLayout)findViewById(R.id.container));
        mListView =(ListView) findViewById(R.id.list_view_debators);
        mDebatorAdapter = new DebatorAdapter(this);
        mListView.setAdapter(mDebatorAdapter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        this.finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_customers, menu);

        // Associate searchable configuration with the SearchView
        SearchManager searchManager =
                (SearchManager) getSystemService(this.SEARCH_SERVICE);
        SearchView searchView =
                (SearchView) menu.findItem(R.id.search_button).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));
        return true;
    }

    public void call_debator(View view) {
        Snackbar.make(view, "call intent", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
        view.getId();
        view.getTag();
        
    }

    public void message_dabator(View view) {
        Snackbar.make(view, "message intent", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }

    public void share_dabator(View view) {
        Snackbar.make(view, "share intent", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }
}
