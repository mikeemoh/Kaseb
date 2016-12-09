package mjkarbasian.moshtarimadar;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import mjkarbasian.moshtarimadar.Data.KasebContract;
import mjkarbasian.moshtarimadar.adapters.CostSaleProductAdapter;
import mjkarbasian.moshtarimadar.adapters.CustomerAdapter;
import mjkarbasian.moshtarimadar.adapters.PaymentAdapter;
import mjkarbasian.moshtarimadar.adapters.TypesSettingAdapter;
import mjkarbasian.moshtarimadar.helper.PaymentListModel;
import mjkarbasian.moshtarimadar.helper.Utility;

public class DetailSaleInsert extends AppCompatActivity {
    private static Context mContext;
    long customerId;
    long[] productIds = new long[2];
    long itemNumber;
    long subTotal;
    long totalDiscount;
    long totalDue;
    long totalTax;
    long totalPaid;
    long quantity;
    String amount;
    long paymentMethodId[];
    long amountPayment[];
    String dueDate[];
    long taxTypeId[];
    long amountTax[];
    ContentValues saleValues = new ContentValues();
    ContentValues detailSaleValues = new ContentValues();
    ContentValues[] itemsValuesArray;
    ContentValues itemsValues;
    ContentValues[] paymentValuesArray;
    ContentValues paymentValues;
    ContentValues[] taxValuesArray;
    ContentValues taxValues;
    FloatingActionButton fab;
    AlertDialog.Builder builder;
    ListView modeList;
    CostSaleProductAdapter mAdapter = null;
    CustomerAdapter mCAdapter = null;
    Dialog dialog;
    Button dialogButton;
    Spinner paymentMethod;
    Spinner taxTypes;
    Uri insertUri;
    String[] mProjection;
    TextView nameCustomer;
    TextView familyCustomer;
    CostSaleProductAdapter mProductAdapter;
    ListView mListView;
    String[] mSelection;
    String mWhereStatement;
    String mWhereStatementDialog;
    int productNumber = 0;
    int productNumberDialog = 0;
    List<String> mNewList = new ArrayList<String>();
    List<String> mNewListDialog = new ArrayList<String>();
    EditText paymentAmount;
    EditText paymentDueDate;
    String paymentMethodsId;
    String paymentMethodType;
    List<Long> paymenttAmountList = new ArrayList<Long>();
    List<String> paymenttDueDateList = new ArrayList<String>();
    List<String> paymenttMethodTypeList = new ArrayList<String>();

    Cursor cursor;
    TypesSettingAdapter cursorAdapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_sale_insert);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Fill in Sale summary
        customerId = 1;
        mContext = this;
        TextView totalAmount = (TextView) findViewById(R.id.card_detail_sale_summary_total_amount);
        TextView tax = (TextView) findViewById(R.id.card_detail_sale_summary_tax);
        TextView discount = (TextView) findViewById(R.id.card_detail_sale_summary_discount);
        TextView finalAmount = (TextView) findViewById(R.id.card_detail_sale_summary_final_amount);
        TextView payed = (TextView) findViewById(R.id.card_detail_sale_summary_payed);
        TextView balance = (TextView) findViewById(R.id.card_detail_sale_summary_balance);
        nameCustomer = (TextView) findViewById(R.id.detail_sales_info_customer_name);
        familyCustomer = (TextView) findViewById(R.id.detail_sales_info_customer_family);

        fab = (FloatingActionButton) findViewById(R.id.fab_detail_sale_insert);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mWhereStatementDialog = KasebContract.Products._ID + " = ? ";

        mProjection = new String[]{
                KasebContract.Products._ID,
                KasebContract.Products.COLUMN_PRODUCT_NAME,
                KasebContract.Products.COLUMN_PRODUCT_CODE};

//        mSelection = new String[]{"-1"};
//        mWhereStatement = KasebContract.Products._ID + " = ? ";

//        modeList = (ListView) findViewById(R.id.listview_sale_items);
        mProductAdapter = new CostSaleProductAdapter(
                DetailSaleInsert.this,
                getContentResolver().query(
                        KasebContract.Products.CONTENT_URI,
                        mProjection,
                        mWhereStatement,
                        mSelection,
                        null),
                0,
                "product");

        Cursor mCursor = mProductAdapter.getCursor();
        if (mCursor.moveToFirst())
            for (int i = 0; i < mCursor.getCount(); i++) {
                mNewListDialog.add(mCursor.getString(mCursor.getColumnIndex(KasebContract.Products._ID)));
                mCursor.moveToNext();
            }

        mCursor.close();
//        modeList.setAdapter(mProductAdapter);
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
        inflater.inflate(R.menu.menu_detail_sale_insert, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case R.id.print_button:
                Toast.makeText(this,
                        getApplicationContext().getResources().getString(R.string.print_your_factor), Toast.LENGTH_LONG).show();
                break;
            case R.id.save:
                EditText saleCode = (EditText) findViewById(R.id.detail_sales_info_sale_code);

                saleValues.put(KasebContract.Sales.COLUMN_CUSTOMER_ID, customerId);
                saleValues.put(KasebContract.Sales.COLUMN_IS_DELETED, 0);
                saleValues.put(KasebContract.Sales.COLUMN_SALE_CODE, saleCode.getText().toString());

                insertUri = this.getContentResolver().insert(
                        KasebContract.Sales.CONTENT_URI,
                        saleValues
                );

                //region disabling edit
                saleCode.setEnabled(false);

                EditText saleDate = (EditText) findViewById(R.id.detail_sales_info_sale_date);

                itemNumber = 2;
                subTotal = 10;
                totalDiscount = 5;
                totalDue = 10;
                totalTax = 2;
                totalPaid = 5;

                detailSaleValues.put(KasebContract.DetailSale.COLUMN_DATE, saleDate.getText().toString());
                detailSaleValues.put(KasebContract.DetailSale.COLUMN_IS_BALANCED, 0);
                detailSaleValues.put(KasebContract.DetailSale.COLUMN_ITEMS_NUMBER, itemNumber);
                detailSaleValues.put(KasebContract.DetailSale.COLUMN_SALE_ID, insertUri.getLastPathSegment());
                detailSaleValues.put(KasebContract.DetailSale.COLUMN_SUB_TOTAL, subTotal);
                detailSaleValues.put(KasebContract.DetailSale.COLUMN_TOTAL_DISCOUNT, totalDiscount);
                detailSaleValues.put(KasebContract.DetailSale.COLUMN_TOTAL_DUE, totalDue);
                detailSaleValues.put(KasebContract.DetailSale.COLUMN_TOTAL_PAID, totalPaid);
                detailSaleValues.put(KasebContract.DetailSale.COLUMN_TOTAL_TAX, totalTax);

                insertUri = this.getContentResolver().insert(
                        KasebContract.DetailSale.CONTENT_URI,
                        detailSaleValues
                );

                productIds[0] = 1;
                productIds[1] = 2;
                quantity = 5;
                amount = "5";

                itemsValuesArray = new ContentValues[2];

                itemsValues = new ContentValues();
                itemsValues.put(KasebContract.DetailSaleProducts.COLUMN_AMOUNT, amount);
                itemsValues.put(KasebContract.DetailSaleProducts.COLUMN_DETAIL_SALE_ID, insertUri.getLastPathSegment());
                itemsValues.put(KasebContract.DetailSaleProducts.COLUMN_PRODUCT_ID, productIds[0]);
                itemsValues.put(KasebContract.DetailSaleProducts.COLUMN_QUANTITY, quantity);

                itemsValuesArray[0] = itemsValues;

                itemsValues = new ContentValues();
                itemsValues.put(KasebContract.DetailSaleProducts.COLUMN_AMOUNT, amount);
                itemsValues.put(KasebContract.DetailSaleProducts.COLUMN_DETAIL_SALE_ID, insertUri.getLastPathSegment());
                itemsValues.put(KasebContract.DetailSaleProducts.COLUMN_PRODUCT_ID, productIds[1]);
                itemsValues.put(KasebContract.DetailSaleProducts.COLUMN_QUANTITY, quantity);

                itemsValuesArray[1] = itemsValues;

                this.getContentResolver().bulkInsert(
                        KasebContract.DetailSaleProducts.CONTENT_URI,
                        itemsValuesArray
                );

                paymentMethodId = new long[2];
                amountPayment = new long[2];
                dueDate = new String[2];

                paymentMethodId[0] = 1;
                paymentMethodId[1] = 2;
                amountPayment[0] = 120000;
                amountPayment[1] = 52000;
                dueDate[0] = "95-10-2";
                dueDate[1] = "95-11-3";

                paymentValuesArray = new ContentValues[2];

                paymentValues = new ContentValues();
                paymentValues.put(KasebContract.DetailSalePayments.COLUMN_DUE_DATE, dueDate[0]);
                paymentValues.put(KasebContract.DetailSalePayments.COLUMN_DETAIL_SALE_ID, insertUri.getLastPathSegment());
                paymentValues.put(KasebContract.DetailSalePayments.COLUMN_AMOUNT, amountPayment[0]);
                paymentValues.put(KasebContract.DetailSalePayments.COLUMN_PAYMENT_METHOD_ID, paymentMethodId[0]);

                paymentValuesArray[0] = paymentValues;

                paymentValues = new ContentValues();
                paymentValues.put(KasebContract.DetailSalePayments.COLUMN_DUE_DATE, dueDate[1]);
                paymentValues.put(KasebContract.DetailSalePayments.COLUMN_DETAIL_SALE_ID, insertUri.getLastPathSegment());
                paymentValues.put(KasebContract.DetailSalePayments.COLUMN_AMOUNT, amountPayment[1]);
                paymentValues.put(KasebContract.DetailSalePayments.COLUMN_PAYMENT_METHOD_ID, paymentMethodId[1]);

                paymentValuesArray[1] = paymentValues;

                this.getContentResolver().bulkInsert(
                        KasebContract.DetailSalePayments.CONTENT_URI,
                        paymentValuesArray
                );

                taxTypeId = new long[2];
                amountTax = new long[2];

                taxTypeId[0] = 1;
                taxTypeId[1] = 2;
                amountTax[0] = 120000;
                amountTax[1] = 52000;

                taxValuesArray = new ContentValues[2];

                taxValues = new ContentValues();
                taxValues.put(KasebContract.DetailSaleTaxes.COLUMN_DETAIL_SALE_ID, insertUri.getLastPathSegment());
                taxValues.put(KasebContract.DetailSaleTaxes.COLUMN_AMOUNT, amountTax[0]);
                taxValues.put(KasebContract.DetailSaleTaxes.COLUMN_TAX_TYPE_ID, taxTypeId[0]);

                taxValuesArray[0] = taxValues;

                taxValues = new ContentValues();
                taxValues.put(KasebContract.DetailSaleTaxes.COLUMN_DETAIL_SALE_ID, insertUri.getLastPathSegment());
                taxValues.put(KasebContract.DetailSaleTaxes.COLUMN_AMOUNT, amountTax[1]);
                taxValues.put(KasebContract.DetailSaleTaxes.COLUMN_TAX_TYPE_ID, taxTypeId[1]);

                taxValuesArray[1] = taxValues;

                this.getContentResolver().bulkInsert(
                        KasebContract.DetailSaleTaxes.CONTENT_URI,
                        taxValuesArray
                );

                //just a message to show everything are under control
                Toast.makeText(this,
                        getApplicationContext().getResources().getString(R.string.msg_insert_succeed), Toast.LENGTH_LONG).show();

                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void fab_detail_sale_insert(View v) {

        PopupMenu popup = new PopupMenu(this, v);
        popup.getMenu().add(0, 0, 0, R.string.fab_add_product);
        popup.getMenu().add(0, 1, 0, R.string.fab_add_payment);
        popup.getMenu().add(0, 2, 0, R.string.fab_add_tax);
        popup.getMenu().add(0, 3, 0, R.string.fab_add_customer);
        popup.setOnMenuItemClickListener(
                new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            //region case 0 ADD PRODUCT
                            case 0:
                                builder = new AlertDialog.Builder(DetailSaleInsert.this);
                                builder.setTitle(R.string.fab_add_product);

                                mProjection = new String[]{
                                        KasebContract.Products._ID,
                                        KasebContract.Products.COLUMN_PRODUCT_NAME,
                                        KasebContract.Products.COLUMN_PRODUCT_CODE};

                                productNumberDialog = mNewListDialog.size();
                                mSelection = new String[(productNumberDialog > 0 ? productNumberDialog : 1)];
                                for (int i = 0; i < mNewListDialog.size(); i++) {
                                    mSelection[i] = mNewListDialog.get(i);
                                }

                                if (mSelection[0] == null)
                                    mSelection[0] = "-1";

                                mWhereStatement = KasebContract.Products._ID + " IN (" +
                                        Utility.makePlaceholders((productNumberDialog > 0 ? productNumberDialog : 1)) + ")";

                                modeList = new ListView(DetailSaleInsert.this);
                                mAdapter = new CostSaleProductAdapter(
                                        DetailSaleInsert.this,
                                        getContentResolver().query(
                                                KasebContract.Products.CONTENT_URI,
                                                mProjection,
                                                mWhereStatement,
                                                mSelection,
                                                null),
                                        0,
                                        "product");
                                modeList.setAdapter(mAdapter);

                                modeList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                        Cursor cursor = (Cursor) parent.getItemAtPosition(position);
                                        if (cursor != null) {
                                            String _id = cursor.getString(cursor.getColumnIndex(KasebContract.Products._ID));

                                            if (!mNewList.contains(_id)) {
                                                productNumber = mNewList.size() + 1;

                                                mNewList.add(_id);
                                                mNewListDialog.remove(_id);

                                                mSelection = new String[productNumber];
                                                for (int i = 0; i < mNewList.size(); i++) {
                                                    mSelection[i] = mNewList.get(i);
                                                }

                                                mWhereStatement = KasebContract.Products._ID + " IN (" + Utility.makePlaceholders(productNumber) + ")";

                                                modeList = (ListView) findViewById(R.id.listview_sale_items);
                                                mProductAdapter = new CostSaleProductAdapter(
                                                        DetailSaleInsert.this,
                                                        getContentResolver().query(
                                                                KasebContract.Products.CONTENT_URI,
                                                                mProjection,
                                                                mWhereStatement,
                                                                mSelection,
                                                                null),
                                                        0,
                                                        "product");
                                                modeList.setAdapter(mProductAdapter);

                                                modeList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                                    @Override
                                                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                                        Cursor cursor = (Cursor) parent.getItemAtPosition(position);
                                                        if (cursor != null) {
                                                            String _id = cursor.getString(cursor.getColumnIndex(KasebContract.Products._ID));

                                                            mNewListDialog.add(_id);
                                                            mNewList.remove(_id);

                                                            int addListProductNumber = mNewList.size();

                                                            mSelection = new String[(addListProductNumber > 0 ? addListProductNumber : 1)];
                                                            for (int i = 0; i < addListProductNumber; i++) {
                                                                mSelection[i] = mNewList.get(i);
                                                            }

                                                            if (mSelection[0] == null)
                                                                mSelection[0] = "-1";

                                                            mWhereStatement = KasebContract.Products._ID + " IN (" +
                                                                    Utility.makePlaceholders((addListProductNumber > 0 ? addListProductNumber : 1)) + ")";

                                                            modeList = (ListView) findViewById(R.id.listview_sale_items);
                                                            mProductAdapter = new CostSaleProductAdapter(
                                                                    DetailSaleInsert.this,
                                                                    getContentResolver().query(
                                                                            KasebContract.Products.CONTENT_URI,
                                                                            mProjection,
                                                                            mWhereStatement,
                                                                            mSelection,
                                                                            null),
                                                                    0,
                                                                    "product");
                                                            modeList.setAdapter(mProductAdapter);
                                                        }
                                                    }
                                                });

                                                dialog.dismiss();
                                            }
                                        }
                                        cursor.close();
                                    }
                                });

                                builder.setView(modeList);
                                dialog = builder.create();

//                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                                dialog.show();
                                //endregion case 0
                                break;
                            //region case 1 ADD PAYMENT
                            case 1:
                                dialog = Utility.dialogBuilder(DetailSaleInsert.this
                                        , R.layout.dialog_add_payment_for_sale
                                        , R.string.fab_add_payment);

                                paymentAmount = (EditText) dialog.findViewById(R.id.add_payment_for_sale_text1);
                                paymentDueDate = (EditText) dialog.findViewById(R.id.input_buy_date);
                                paymentDueDate.setText(Utility.preInsertDate(mContext));

                                paymentMethod = (Spinner) dialog.findViewById(R.id.input_payment_method_spinner);
                                cursor = getContentResolver().query(KasebContract.PaymentMethods.CONTENT_URI
                                        , null, null, null, null);

                                cursorAdapter = new TypesSettingAdapter(mContext,
                                        cursor,
                                        0,
                                        KasebContract.PaymentMethods.COLUMN_PAYMENT_METHOD_POINTER);
                                paymentMethod.setAdapter(cursorAdapter);

                                paymentMethod.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                                        Cursor cursor = (Cursor) paymentMethod.getSelectedItem();
                                        paymentMethodsId =
                                                cursor.getString(
                                                        cursor.getColumnIndex(KasebContract.PaymentMethods._ID));
                                        paymentMethodType =
                                                cursor.getString(
                                                        cursor.getColumnIndex(KasebContract.PaymentMethods.COLUMN_PAYMENT_METHOD_POINTER));
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> arg0) {
                                    }
                                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                        Cursor cursor = (Cursor) parent.getItemAtPosition(position);
                                        paymentMethodsId =
                                                cursor.getString(
                                                        cursor.getColumnIndex(KasebContract.PaymentMethods._ID));
                                        paymentMethodType =
                                                cursor.getString(
                                                        cursor.getColumnIndex(KasebContract.PaymentMethods.COLUMN_PAYMENT_METHOD_POINTER));
                                    }
                                });

                                dialogButton = (Button) dialog.findViewById(R.id.add_payment_for_sale_button1);
                                dialogButton.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        ListView paymentMethodRowListView = (ListView) findViewById(R.id.listview_sale_payments);
                                        ArrayList<PaymentListModel> paymentArrayValues = new ArrayList<PaymentListModel>();

                                        paymenttAmountList.add(Long.parseLong(paymentAmount.getText().toString()));
                                        paymenttDueDateList.add(paymentDueDate.getText().toString());
                                        paymenttMethodTypeList.add(paymentMethodType);

                                        for (int i = 0; i < paymenttAmountList.size(); i++) {
                                            PaymentListModel paymentMethodRow = new PaymentListModel();
                                            paymentMethodRow.setpaymentAmount(paymenttAmountList.get(i));
                                            paymentMethodRow.setpaymentDueDate(paymenttDueDateList.get(i));
                                            paymentMethodRow.setpaymentMethod(paymenttMethodTypeList.get(i));

                                            paymentArrayValues.add(paymentMethodRow);
                                        }

                                        PaymentAdapter adapter = new PaymentAdapter(getBaseContext(), paymentArrayValues);
                                        paymentMethodRowListView.setAdapter(adapter);
                                        dialog.dismiss();
                                    }
                                });

                                dialog.show();
                                //endregion case 1
                                break;
                            //region case 2 ADD TAX
                            case 2:
                                dialog = Utility.dialogBuilder(DetailSaleInsert.this
                                        , R.layout.dialog_add_tax_for_sale
                                        , R.string.fab_add_tax);

                                taxTypes = (Spinner) dialog.findViewById(R.id.input_tax_type_spinner);
                                cursor = getContentResolver().query(KasebContract.TaxTypes.CONTENT_URI
                                        , null, null, null, null);

                                cursorAdapter = new TypesSettingAdapter(mContext,
                                        cursor,
                                        0,
                                        KasebContract.TaxTypes.COLUMN_TAX_TYPE_POINTER);
                                taxTypes.setAdapter(cursorAdapter);

                                dialogButton = (Button) dialog.findViewById(R.id.add_tax_for_sale_button1);
                                dialogButton.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        dialog.dismiss();
                                    }
                                });

                                dialog.show();
                                //endregion case 2
                                break;
                            //region case 3 ADD CUSTOMER
                            case 3:
                                mProjection = new String[]{
                                        KasebContract.Customers._ID,
                                        KasebContract.Customers.COLUMN_FIRST_NAME,
                                        KasebContract.Customers.COLUMN_LAST_NAME,
                                        KasebContract.Customers.COLUMN_STATE_ID};

                                modeList = new ListView(DetailSaleInsert.this);
                                mCAdapter = new CustomerAdapter(
                                        DetailSaleInsert.this,
                                        getContentResolver().query(
                                                KasebContract.Customers.CONTENT_URI,
                                                mProjection,
                                                null,
                                                null,
                                                null),
                                        0);
                                modeList.setAdapter(mCAdapter);

                                builder = new AlertDialog.Builder(DetailSaleInsert.this);
                                builder.setTitle(R.string.fab_add_customer);

                                modeList.setOnItemClickListener(
                                        new AdapterView.OnItemClickListener() {
                                            @Override
                                            public void onItemClick(AdapterView<?> parent, View view, int position,
                                                                    long id) {
//                                Cursor c = mCAdapter.getCursor();
//                                c.moveToPosition(position);

                                                Cursor cursor = (Cursor) parent.getItemAtPosition(position);
                                                if (cursor != null) {
                                                    nameCustomer.setText(cursor.getString(cursor.getColumnIndex(KasebContract.Customers.COLUMN_FIRST_NAME)));
                                                    familyCustomer.setText(cursor.getString(cursor.getColumnIndex(KasebContract.Customers.COLUMN_LAST_NAME)));
                                                    customerId = Long.parseLong(cursor.getString(cursor.getColumnIndex(KasebContract.Customers._ID)));
                                                    dialog.dismiss();
                                                }
                                                cursor.close();
                                            }
                                        }

                                );

                                builder.setView(modeList);
                                dialog = builder.create();

                                dialog.show();
                                break;
                            //endregion case 3
                            default:
                                break;
                        }
                        return true;
                    }
                }

        );
        popup.show();//showing popup menu
    }
}
