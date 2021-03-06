package mjkarbasian.moshtarimadar;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import mjkarbasian.moshtarimadar.Data.KasebContract;
import mjkarbasian.moshtarimadar.adapters.CustomerBillAdapter;

/**
 * Created by family on 7/27/2016.
 */
public class DetailCustomerBill extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    private final String LOG_TAG = CustomersLists.class.getSimpleName();
    final static int FRAGMENT_CUSTOMER_BILL_LOADER = 4;

    CustomerBillAdapter mCustomerAdapter = null;
    ListView mListView;
    String[] mProjection;
    String[] mSelection;
    String mWhereStatement;
    Long customerId;

    public DetailCustomerBill() {
        super();
    }

    public void onCreate(Bundle savedInstanceState) {

        customerId = Long.parseLong(this.getArguments().getString("customerId"));

        mProjection = new String[]{
                KasebContract.Sales._ID,
                KasebContract.Sales.COLUMN_CUSTOMER_ID,
                KasebContract.Sales.COLUMN_SALE_CODE};

        mSelection = new String[]{
                KasebContract.Sales.COLUMN_CUSTOMER_ID};

        mWhereStatement = KasebContract.Sales.COLUMN_CUSTOMER_ID + " = ? ";

        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mCustomerAdapter = new CustomerBillAdapter(
                getActivity(),
                null,
                0);

        View rootView = inflater.inflate(R.layout.customer_bill, container, false);
        mListView = (ListView) rootView.findViewById(R.id.list_view_customer_bills);
        mListView.setAdapter(mCustomerAdapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Cursor cursor = (Cursor) parent.getItemAtPosition(position);
                if (cursor != null) {
                    Toast.makeText(getActivity(), "go to this factor", Toast.LENGTH_SHORT).show();
//                    Intent intent = new Intent(getActivity(), DetailCustomer.class)
//                            .setData(KasebContract.Customers.buildCustomerUri(
//                                    Long.parseLong(cursor.getString(cursor.getColumnIndex(KasebContract.Customers._ID)))
//                            ));
//                    startActivity(intent);
                }
            }
        });
        return rootView;
    }

    @Override
    public void onStart() {
        Log.d(LOG_TAG, "onStart");
        super.onStart();
        updateList();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        Log.d(LOG_TAG, "onActivityCreated");
        super.onActivityCreated(savedInstanceState);
        getLoaderManager().initLoader(FRAGMENT_CUSTOMER_BILL_LOADER, null, this);
    }

    private void updateList() {
        getLoaderManager().restartLoader(FRAGMENT_CUSTOMER_BILL_LOADER, null, this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Log.d(LOG_TAG, "onCreateLoader");
        return new CursorLoader(
                getActivity(),
                KasebContract.Sales.customerSales(customerId),
                mProjection,
                mWhereStatement,
                mSelection,
                null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        Log.d(LOG_TAG, "onLoadFinished");
        mCustomerAdapter.swapCursor(cursor);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        Log.d(LOG_TAG, "onLoadReset");
        mCustomerAdapter.swapCursor(null);
    }
}
