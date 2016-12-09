package mjkarbasian.moshtarimadar;

import android.content.ContentValues;
import android.database.Cursor;
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
import android.widget.Spinner;
import android.widget.Toast;

import mjkarbasian.moshtarimadar.Data.KasebContract;
import mjkarbasian.moshtarimadar.adapters.TypesSettingAdapter;
import mjkarbasian.moshtarimadar.helper.Utility;


/**
 * Created by Unique on 10/21/2016.
 */
public class CustomerInsert extends Fragment {

//    static TypesSettingAdapter stateTypesAdapter = null;

    EditText firstName;
    EditText lastName;
    EditText birthDay;
    Spinner stateType;
    EditText phoneMobile;
    EditText customerDescription;
    EditText email;
    EditText phoneWork;
    EditText phoneHome;
    EditText phoneOther;
    EditText phoneFax;
    EditText addressCountry;
    EditText addressCity;
    EditText addressStreet;
    EditText addressPostalCode;
    private Uri insertUri;
    //    ImageView imageCustomer;
    View rootView;

    ContentValues customerValues = new ContentValues();

    public CustomerInsert() {
        setHasOptionsMenu(true);
    }

//    public void insertImg(int id , Bitmap img ) {
//
//
//        byte[] data = getBitmapAsByteArray(img); // this is a function
//
//        imageCustomer.bi.bindLong(1, id);
//        insertStatement_logo.bindBlob(2, data);
//
//        insertStatement_logo.executeInsert();
//        insertStatement_logo.clearBindings() ;
//
//    }
//
//    public static byte[] getBitmapAsByteArray(Bitmap bitmap) {
//        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//        bitmap.compress(Bitmap.CompressFormat.PNG, 0, outputStream);
//        return outputStream.toByteArray();
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_customer_insert, container, false);

//        imageCustomer = (ImageView) rootView.findViewById(R.id.input_image_customer);

        stateType = (Spinner) rootView.findViewById(R.id.input_state_type_spinner);
        firstName = (EditText) rootView.findViewById(R.id.input_first_name);
        lastName = (EditText) rootView.findViewById(R.id.input_last_name);
        birthDay = (EditText) rootView.findViewById(R.id.input_birth_day);
        phoneMobile = (EditText) rootView.findViewById(R.id.input_phone_mobile);
        customerDescription = (EditText) rootView.findViewById(R.id.input_description);
        email = (EditText) rootView.findViewById(R.id.input_email);
        phoneWork = (EditText) rootView.findViewById(R.id.input_phone_work);
        phoneHome = (EditText) rootView.findViewById(R.id.input_phone_home);
        phoneOther = (EditText) rootView.findViewById(R.id.input_phone_other);
        phoneFax = (EditText) rootView.findViewById(R.id.input_phone_fax);
        addressCountry = (EditText) rootView.findViewById(R.id.input_address_country);
        addressCity = (EditText) rootView.findViewById(R.id.input_address_city);
        addressStreet = (EditText) rootView.findViewById(R.id.input_address_street);
        addressPostalCode = (EditText) rootView.findViewById(R.id.input_address_postal_code);
        birthDay.setText(Utility.preInsertDate(getActivity()));
        Cursor cursor = getContext().getContentResolver().query(KasebContract.State.CONTENT_URI
                , null, null, null, null);

        int[] toViews = {
                android.R.id.text1
        };
        String[] fromColumns = {
                KasebContract.State.COLUMN_STATE_POINTER
        };

        TypesSettingAdapter cursorAdapter = new TypesSettingAdapter(getActivity(), cursor, 0, KasebContract.State.COLUMN_STATE_POINTER);
        stateType.setAdapter(cursorAdapter);
        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(
            Menu menu, MenuInflater inflater) {
        menu.removeItem(R.id.sort_button);
        menu.removeItem(R.id.search_button);
        inflater.inflate(R.menu.fragments_for_insert, menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_inputs: {

//                ContentValues testValues = new ContentValues();
//                testValues.put(KasebContract.State.COLUMN_STATE_POINTER,"Gold");
//                Uri StateRowId = getActivity().getContentResolver().insert(
//                        KasebContract.State.CONTENT_URI, testValues);

                customerValues.put(KasebContract.Customers.COLUMN_FIRST_NAME, firstName.getText().toString());
                customerValues.put(KasebContract.Customers.COLUMN_LAST_NAME, lastName.getText().toString());
                customerValues.put(KasebContract.Customers.COLUMN_BIRTHDAY, birthDay.getText().toString());
                customerValues.put(KasebContract.Customers.COLUMN_PHONE_MOBILE, phoneMobile.getText().toString());
                customerValues.put(KasebContract.Customers.COLUMN_PHONE_WORK, phoneWork.getText().toString());
                customerValues.put(KasebContract.Customers.COLUMN_PHONE_HOME, phoneHome.getText().toString());
                customerValues.put(KasebContract.Customers.COLUMN_PHONE_FAX, phoneFax.getText().toString());
                customerValues.put(KasebContract.Customers.COLUMN_PHONE_OTHER, phoneOther.getText().toString());
                customerValues.put(KasebContract.Customers.COLUMN_DESCRIPTION, customerDescription.getText().toString());
                customerValues.put(KasebContract.Customers.COLUMN_EMAIL, email.getText().toString());
                customerValues.put(KasebContract.Customers.COLUMN_ADDRESS_COUNTRY, addressCountry.getText().toString());
                customerValues.put(KasebContract.Customers.COLUMN_ADDRESS_CITY, addressCity.getText().toString());
                customerValues.put(KasebContract.Customers.COLUMN_ADDRESS_STREET, addressStreet.getText().toString());
                customerValues.put(KasebContract.Customers.COLUMN_ADDRESS_POSTAL_CODE, addressPostalCode.getText().toString());

//                Long.parseLong(StateRowId.getLastPathSegment())

                customerValues.put(KasebContract.Customers.COLUMN_STATE_ID, stateType.getSelectedItemPosition() + 1);
                insertUri = getActivity().getContentResolver().insert(
                        KasebContract.Customers.CONTENT_URI,
                        customerValues
                );

                //region disabling edit
                firstName.setEnabled(false);
                lastName.setEnabled(false);
                birthDay.setEnabled(false);
                phoneMobile.setEnabled(false);
                customerDescription.setEnabled(false);
                email.setEnabled(false);
                phoneWork.setEnabled(false);
                phoneHome.setEnabled(false);
                phoneOther.setEnabled(false);
                phoneFax.setEnabled(false);
                addressCountry.setEnabled(false);
                addressCity.setEnabled(false);
                addressStreet.setEnabled(false);
                addressPostalCode.setEnabled(false);
                stateType.setEnabled(false);

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

    private void checkForValidity() {
    }

    private void backToLastPage() {
        Utility.clearForm((ViewGroup) rootView);
        getFragmentManager().popBackStackImmediate();
    }
}