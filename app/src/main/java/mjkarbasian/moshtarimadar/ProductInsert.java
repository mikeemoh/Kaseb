package mjkarbasian.moshtarimadar;

import android.content.ContentValues;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import mjkarbasian.moshtarimadar.Data.KasebContract;
import mjkarbasian.moshtarimadar.helper.Utility;

/**
 * Created by Unique on 10/25/2016.
 */
public class ProductInsert extends Fragment {

    private static final String LOG_TAG = CostInsert.class.getSimpleName();
    EditText productName;
    EditText productCode;
    EditText unit;
    EditText productDescription;
    EditText buyPrice;
    EditText quantity;
    EditText salePrice;
    EditText buyDate;
    ContentValues productValues = new ContentValues();
    ContentValues productHistoryValues = new ContentValues();
    private Uri insertUri;
    View rootView;

    public ProductInsert() {
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_product_insert, container, false);


//        unit = (Spinner) rootView.findViewById(R.id.input_unit_spinner);
//        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
//                R.array.unit, android.R.layout.simple_spinner_item);
        productName = (EditText) rootView.findViewById(R.id.input_product_name);
        productCode = (EditText) rootView.findViewById(R.id.input_product_code);
        unit = (EditText) rootView.findViewById(R.id.input_product_unit);
        productDescription = (EditText) rootView.findViewById(R.id.input_product_description);

        buyPrice = (EditText) rootView.findViewById(R.id.input_buy_price);
        quantity = (EditText) rootView.findViewById(R.id.input_quantity);
        salePrice = (EditText) rootView.findViewById(R.id.input_sale_price);
        buyDate = (EditText) rootView.findViewById(R.id.input_buy_date);
        buyDate.setText(Utility.preInsertDate(getActivity()));
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        unit.setAdapter(adapter);

        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.removeItem(R.id.sort_button);
        menu.removeItem(R.id.search_button);
        inflater.inflate(R.menu.fragments_for_insert, menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_inputs: {
                productValues.put(KasebContract.Products.COLUMN_PRODUCT_NAME, productName.getText().toString());
                productValues.put(KasebContract.Products.COLUMN_PRODUCT_CODE, productCode.getText().toString());
                productValues.put(KasebContract.Products.COLUMN_UNIT, unit.getText().toString());
                productValues.put(KasebContract.Products.COLUMN_DESCRIPTION, productDescription.getText().toString());
                insertUri = getActivity().getContentResolver().insert(
                        KasebContract.Products.CONTENT_URI,
                        productValues
                );

                productHistoryValues.put(KasebContract.ProductHistory.COLUMN_COST, buyPrice.getText().toString());
                productHistoryValues.put(KasebContract.ProductHistory.COLUMN_QUANTITY, quantity.getText().toString());
                productHistoryValues.put(KasebContract.ProductHistory.COLUMN_SALE_PRICE, salePrice.getText().toString());
                productHistoryValues.put(KasebContract.ProductHistory.COLUMN_DATE, buyDate.getText().toString());
                productHistoryValues.put(KasebContract.ProductHistory.COLUMN_PRODUCT_ID, insertUri.getLastPathSegment());

                insertUri = getActivity().getContentResolver().insert(
                        KasebContract.ProductHistory.CONTENT_URI,
                        productHistoryValues
                );

                //region disabling edit
                productName.setEnabled(false);
                productCode.setEnabled(false);
                unit.setEnabled(false);
                productDescription.setEnabled(false);
                buyPrice.setEnabled(false);
                quantity.setEnabled(false);
                salePrice.setEnabled(false);
                buyDate.setEnabled(false);

                //just a message to show everything are under control
                Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.msg_insert_succeed),
                        Toast.LENGTH_LONG).show();

                checkForValidity();
                backToLastPage();

                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    // this method check the validation and correct entries. its check fill first and then check the validation rules.
    private void checkForValidity() {
    }

    // this method back to the activity view. this must be a utility method.
    private void backToLastPage() {
        Utility.clearForm((ViewGroup) rootView);
        getFragmentManager().popBackStackImmediate();
    }
}