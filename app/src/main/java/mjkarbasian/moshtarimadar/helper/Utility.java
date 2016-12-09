package mjkarbasian.moshtarimadar.helper;

import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import io.github.meness.roozh.Roozh;
import io.github.meness.roozh.RoozhLocale;
import mjkarbasian.moshtarimadar.Data.KasebContract;
import mjkarbasian.moshtarimadar.R;

import static mjkarbasian.moshtarimadar.helper.Samples.cost_1;
import static mjkarbasian.moshtarimadar.helper.Samples.customer_1;
import static mjkarbasian.moshtarimadar.helper.Samples.detail_sale_1;
import static mjkarbasian.moshtarimadar.helper.Samples.detail_sale_2;
import static mjkarbasian.moshtarimadar.helper.Samples.ds1PaymentItems;
import static mjkarbasian.moshtarimadar.helper.Samples.ds1ProductItems;
import static mjkarbasian.moshtarimadar.helper.Samples.ds1TaxItems;
import static mjkarbasian.moshtarimadar.helper.Samples.ds2PaymentItems;
import static mjkarbasian.moshtarimadar.helper.Samples.ds2ProductItems;
import static mjkarbasian.moshtarimadar.helper.Samples.ds2TaxItems;
import static mjkarbasian.moshtarimadar.helper.Samples.product_1;
import static mjkarbasian.moshtarimadar.helper.Samples.product_history;
import static mjkarbasian.moshtarimadar.helper.Samples.sale_1;
import static mjkarbasian.moshtarimadar.helper.Samples.sale_2;
import static mjkarbasian.moshtarimadar.helper.Samples.salesCode;
import static mjkarbasian.moshtarimadar.helper.Samples.setCost_1;
import static mjkarbasian.moshtarimadar.helper.Samples.setCustomer_1;
import static mjkarbasian.moshtarimadar.helper.Samples.setDS1PaymentItems;
import static mjkarbasian.moshtarimadar.helper.Samples.setDS1ProductItems;
import static mjkarbasian.moshtarimadar.helper.Samples.setDS1TaxItems;
import static mjkarbasian.moshtarimadar.helper.Samples.setDS2PaymentItems;
import static mjkarbasian.moshtarimadar.helper.Samples.setDS2ProductItems;
import static mjkarbasian.moshtarimadar.helper.Samples.setDS2TaxItems;
import static mjkarbasian.moshtarimadar.helper.Samples.setDetailSale_1;
import static mjkarbasian.moshtarimadar.helper.Samples.setDetailSale_2;
import static mjkarbasian.moshtarimadar.helper.Samples.setProduct_1;
import static mjkarbasian.moshtarimadar.helper.Samples.setProduct_history;
import static mjkarbasian.moshtarimadar.helper.Samples.setSale;
import static mjkarbasian.moshtarimadar.helper.Samples.setSaleDueDate;
import static mjkarbasian.moshtarimadar.helper.Samples.setSaleFinalAmount;
import static mjkarbasian.moshtarimadar.helper.Samples.setSaleOffTaxList;
import static mjkarbasian.moshtarimadar.helper.Samples.setSalePaymentList;
import static mjkarbasian.moshtarimadar.helper.Samples.setSaleProductList;
import static mjkarbasian.moshtarimadar.helper.Samples.setSaleSummary;
import static mjkarbasian.moshtarimadar.helper.Samples.setSale_1;
import static mjkarbasian.moshtarimadar.helper.Samples.setSale_2;
import static mjkarbasian.moshtarimadar.helper.Samples.setSalesAmount;
import static mjkarbasian.moshtarimadar.helper.Samples.setSalesCode;
import static mjkarbasian.moshtarimadar.helper.Samples.setSalesCustomer;


/**
 * Created by family on 6/24/2016.
 */
public class Utility {

    public static String makePlaceholders(int len) {
        StringBuilder sb = new StringBuilder(len * 2 - 1);
        sb.append("?");
        for (int i = 1; i < len; i++)
            sb.append(",?");
        return sb.toString();
    }

    public static Dialog dialogBuilder(Context context, int layout, int title) {
        Dialog dialog = new Dialog(context);
        dialog.setContentView(layout);
        dialog.setTitle(title);

        return dialog;
    }

    private static final String LOG_TAG = Utility.class.getSimpleName();

    public static String getTheLastPathUri(Uri uri) {
        String[] segments = uri.getPath().split("/");
        String pathStr = segments[segments.length - 1];
        return pathStr;
    }

    public static String formatPurchase(Context context, String purcahseAmount) {

        return context.getString(R.string.format_purchase_amount, purcahseAmount);
    }

    public static String DecimalSeperation(Context context, double number) {
        DecimalFormat format = new DecimalFormat();
        format.setDecimalSeparatorAlwaysShown(true);
        String decNumber = (String) format.format(number);
        return decNumber;
    }

    public static int dipConverter(int dip, Context context) {

        int dpValue = dip; // margin in dips
        float d = context.getResources().getDisplayMetrics().density;
        int marginDip = (int) (dpValue * d); // margin in pixels

        return marginDip;
    }

    public static String currentDatePicker() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MMM-dd");
        String formattedDate = df.format(calendar.getTime());
        return formattedDate;
    }

    public static String JalaliDatePicker(String date) {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
        int year = 0;
        int month = 0;
        int day = 0;
        try {
            Date dateDate = df.parse(date);
            year = dateDate.getYear() + 1900;
            month = dateDate.getMonth();
            day = dateDate.getDay();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String jalaliDate = JalaliCalendar.gregorianToJalali(new JalaliCalendar.YearMonthDate(year, month, day)).toString();

        return jalaliDate;
    }

    public static String getLocale(Context context) {

        Locale current = context.getResources().getConfiguration().locale;
        return current.getCountry();
    }


    public static String doubleFormatter(double myNumber) {
        DecimalFormat f = (DecimalFormat) DecimalFormat.getInstance();
        f.setDecimalSeparatorAlwaysShown(false);
        f.setGroupingUsed(false);
        String myString = f.format(myNumber);
        return myString;
    }

    public static String contextDateFormatter(String date, Context context) {
        java.text.DateFormat dateFormat = android.text.format.DateFormat.getDateFormat(context);
        String formatedDate = dateFormat.format(date);
        return formatedDate;
    }

    public static String formatDate(Calendar calendar) {

        SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
        String formattedDate = df.format(calendar.getTime());
        return formattedDate;
    }

    public static boolean isToday(String date) throws ParseException {
        SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
        Date day = df.parse(date);
        Calendar checkDay = Calendar.getInstance();
        Calendar thisDay = Calendar.getInstance();
        checkDay.setTime(day);

        int checkDayYear = checkDay.get(Calendar.YEAR);
        int checkDayMonth = checkDay.get(Calendar.MONTH);
        int checkDayDayNum = checkDay.get(Calendar.DAY_OF_MONTH);

        int thisDayNum = thisDay.get(Calendar.DAY_OF_MONTH);
        int thisDayMonth = thisDay.get(Calendar.MONTH);
        int thisDayYear = thisDay.get(Calendar.YEAR);

        return (checkDayYear == thisDayYear && checkDayMonth == thisDayMonth && checkDayDayNum == thisDayNum);
    }

    public static boolean isThisWeek(String date) throws ParseException {
        SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
        Date day = df.parse(date);
        Calendar checkDay = Calendar.getInstance();
        Calendar thisDay = Calendar.getInstance();
        checkDay.setTime(day);

        int checkDayWeek = checkDay.get(Calendar.WEEK_OF_YEAR);
        int thisDayWeek = thisDay.get(Calendar.WEEK_OF_YEAR);

        return (checkDayWeek == thisDayWeek);
    }

    public static boolean isThisMonth(String date) throws ParseException {
        SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
        Date day = df.parse(date);
        Calendar checkDay = Calendar.getInstance();
        Calendar thisDay = Calendar.getInstance();
        checkDay.setTime(day);

        int checkDayMonth = checkDay.get(Calendar.MONTH);
        int thisDayMonth = thisDay.get(Calendar.MONTH);

        return (checkDayMonth == thisDayMonth);
    }

    public static void setCustomerState(ImageView customerState, int stateVar) {
        switch (stateVar) {
            case 1:
                customerState.setColorFilter(Color.argb(255, 255, 223, 0));
                break;
            case 2:
                customerState.setColorFilter(Color.argb(255, 169, 169, 169));
                break;
            case 3:
                customerState.setColorFilter(Color.argb(255, 207, 125, 50));
                break;
            default:
                customerState.setColorFilter(Color.argb(255, 0, 0, 0));

        }
    }

    public static String changeDate(String date, int option, int amount) throws ParseException {

        SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
        Date day = df.parse(date);
        Calendar currentDate = Calendar.getInstance();
        currentDate.setTime(day);
        String changedDate;
        switch (option) {
            case Calendar.DATE:
                currentDate.roll(Calendar.DAY_OF_MONTH, amount);
                break;
            case Calendar.MONTH:
                currentDate.roll(Calendar.MONTH, amount);
                break;
            case Calendar.YEAR:
                currentDate.roll(Calendar.YEAR, amount);
                break;
            default:

        }

        changedDate = Utility.formatDate(currentDate);
        return changedDate;

    }

    public static void initializer(Context context) {
        Cursor cursor = context.getContentResolver().query(KasebContract.CostTypes.CONTENT_URI, null, null, null, null);
        if (cursor.getCount() == 0) costTypesInit(context);
        cursor = context.getContentResolver().query(KasebContract.TaxTypes.CONTENT_URI, null, null, null, null);
        if (cursor.getCount() == 0) taxTypesInit(context);
        cursor = context.getContentResolver().query(KasebContract.PaymentMethods.CONTENT_URI, null, null, null, null);
        if (cursor.getCount() == 0) paymentsMethodinit(context);
        cursor = context.getContentResolver().query(KasebContract.State.CONTENT_URI, null, null, null, null);
        if (cursor.getCount() == 0) stateInits(context);
        cursor = context.getContentResolver().query(KasebContract.Customers.CONTENT_URI, null, null, null, null);
        if (cursor.getCount() == 0) customerInits(context);
        cursor = context.getContentResolver().query(KasebContract.Products.CONTENT_URI, null, null, null, null);
        if (cursor.getCount() == 0) productInits(context);
        cursor = context.getContentResolver().query(KasebContract.Costs.CONTENT_URI, null, null, null, null);
        if (cursor.getCount() == 0) costsInits(context);
        cursor = context.getContentResolver().query(KasebContract.Sales.CONTENT_URI, null, null, null, null);
        if (cursor.getCount() == 0) salesInits(context);
        cursor = context.getContentResolver().query(KasebContract.DetailSale.CONTENT_URI, null, null, null, null);
        if (cursor.getCount() == 0) detailSaleInits(context);
        cursor = context.getContentResolver().query(KasebContract.DetailSaleProducts.CONTENT_URI, null, null, null, null);
        if (cursor.getCount() == 0) dSaleProductInits(context);
        cursor = context.getContentResolver().query(KasebContract.DetailSalePayments.CONTENT_URI, null, null, null, null);
        if (cursor.getCount() == 0) dSalePaymentInits(context);
        cursor = context.getContentResolver().query(KasebContract.DetailSaleTaxes.CONTENT_URI, null, null, null, null);
        if (cursor.getCount() == 0) dSaleTaxInits(context);
        cursor = context.getContentResolver().query(KasebContract.ProductHistory.CONTENT_URI, null, null, null, null);
        if (cursor.getCount() == 0) productHistoryInits(context);
        cursor.close();

        if (Samples.productCode.size() == 0) {
            Samples.setProductCode();
            Samples.setProductName(context);
            Samples.setProductDate();
            Samples.setProductPrice();
            Samples.setProductPicture(context);
            Samples.setProducts();
            Samples.setProductsPriceList();
        }

        if (salesCode.size() == 0) {
            setSalesCode();
            setSaleDueDate();
            setSalesCustomer(context);
            setSalesAmount();
            setSaleProductList();
            try {
                setSalePaymentList(context);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            setSaleOffTaxList(context);
            setSaleFinalAmount(context);
            setSale();
            setSaleSummary();
        }


    }

    private static void productHistoryInits(Context context) {
        setProduct_history(context);
        ContentValues productHistory = new ContentValues();
        productHistory.put(KasebContract.ProductHistory.COLUMN_PRODUCT_ID, product_history.getInt(KasebContract.ProductHistory.COLUMN_PRODUCT_ID));
        productHistory.put(KasebContract.ProductHistory.COLUMN_QUANTITY, product_history.getInt(KasebContract.ProductHistory.COLUMN_QUANTITY));
        productHistory.put(KasebContract.ProductHistory.COLUMN_COST, product_history.getFloat(KasebContract.ProductHistory.COLUMN_COST));
        productHistory.put(KasebContract.ProductHistory.COLUMN_SALE_PRICE, product_history.getFloat(KasebContract.ProductHistory.COLUMN_SALE_PRICE));
        productHistory.put(KasebContract.ProductHistory.COLUMN_DATE, product_history.getString(KasebContract.ProductHistory.COLUMN_DATE));

        Uri productHUri = context.getContentResolver().insert(KasebContract.ProductHistory.CONTENT_URI, productHistory);
    }

    private static void detailSaleInits(Context context) {
        setDetailSale_1(context);
        setDetailSale_2(context);
        ContentValues detailSale1 = new ContentValues();
        ContentValues detailSale2 = new ContentValues();

        detailSale1.put(KasebContract.DetailSale.COLUMN_SALE_ID, detail_sale_1.getInt(KasebContract.DetailSale.COLUMN_SALE_ID));
        detailSale1.put(KasebContract.DetailSale.COLUMN_DATE, detail_sale_1.getString(KasebContract.DetailSale.COLUMN_DATE));
        detailSale1.put(KasebContract.DetailSale.COLUMN_ITEMS_NUMBER, detail_sale_1.getInt(KasebContract.DetailSale.COLUMN_ITEMS_NUMBER));
        detailSale1.put(KasebContract.DetailSale.COLUMN_SUB_TOTAL, detail_sale_1.getFloat(KasebContract.DetailSale.COLUMN_SUB_TOTAL));
        detailSale1.put(KasebContract.DetailSale.COLUMN_TOTAL_TAX, detail_sale_1.getFloat(KasebContract.DetailSale.COLUMN_TOTAL_TAX));
        detailSale1.put(KasebContract.DetailSale.COLUMN_TOTAL_DISCOUNT, detail_sale_1.getFloat(KasebContract.DetailSale.COLUMN_TOTAL_DISCOUNT));
        detailSale1.put(KasebContract.DetailSale.COLUMN_TOTAL_DUE, detail_sale_1.getFloat(KasebContract.DetailSale.COLUMN_TOTAL_DUE));
        detailSale1.put(KasebContract.DetailSale.COLUMN_TOTAL_PAID, detail_sale_1.getFloat(KasebContract.DetailSale.COLUMN_TOTAL_PAID));
        detailSale1.put(KasebContract.DetailSale.COLUMN_IS_BALANCED, detail_sale_1.getInt(KasebContract.DetailSale.COLUMN_IS_BALANCED));

        detailSale2.put(KasebContract.DetailSale.COLUMN_SALE_ID, detail_sale_2.getInt(KasebContract.DetailSale.COLUMN_SALE_ID));
        detailSale2.put(KasebContract.DetailSale.COLUMN_DATE, detail_sale_2.getString(KasebContract.DetailSale.COLUMN_DATE));
        detailSale2.put(KasebContract.DetailSale.COLUMN_ITEMS_NUMBER, detail_sale_2.getInt(KasebContract.DetailSale.COLUMN_ITEMS_NUMBER));
        detailSale2.put(KasebContract.DetailSale.COLUMN_SUB_TOTAL, detail_sale_2.getFloat(KasebContract.DetailSale.COLUMN_SUB_TOTAL));
        detailSale2.put(KasebContract.DetailSale.COLUMN_TOTAL_TAX, detail_sale_2.getFloat(KasebContract.DetailSale.COLUMN_TOTAL_TAX));
        detailSale2.put(KasebContract.DetailSale.COLUMN_TOTAL_DISCOUNT, detail_sale_2.getFloat(KasebContract.DetailSale.COLUMN_TOTAL_DISCOUNT));
        detailSale2.put(KasebContract.DetailSale.COLUMN_TOTAL_DUE, detail_sale_2.getFloat(KasebContract.DetailSale.COLUMN_TOTAL_DUE));
        detailSale2.put(KasebContract.DetailSale.COLUMN_TOTAL_PAID, detail_sale_2.getFloat(KasebContract.DetailSale.COLUMN_TOTAL_PAID));

        Uri ds1Uri = context.getContentResolver().insert(KasebContract.DetailSale.CONTENT_URI, detailSale1);
        Log.d(LOG_TAG, ds1Uri.toString());
        Uri ds2Uri = context.getContentResolver().insert(KasebContract.DetailSale.CONTENT_URI, detailSale2);
        Log.d(LOG_TAG, ds2Uri.toString());
    }

    private static void dSaleTaxInits(Context context) {
        setDS1TaxItems(context);
        setDS2TaxItems(context);
        ContentValues dsTax1 = new ContentValues();
        ContentValues dsTax2 = new ContentValues();

        dsTax1.put(KasebContract.DetailSaleTaxes.COLUMN_DETAIL_SALE_ID, ds1TaxItems.getInt(KasebContract.DetailSaleTaxes.COLUMN_DETAIL_SALE_ID));
        dsTax1.put(KasebContract.DetailSaleTaxes.COLUMN_TAX_TYPE_ID, ds1TaxItems.getInt(KasebContract.DetailSaleTaxes.COLUMN_TAX_TYPE_ID));
        dsTax1.put(KasebContract.DetailSaleTaxes.COLUMN_AMOUNT, ds1TaxItems.getFloat(KasebContract.DetailSaleTaxes.COLUMN_AMOUNT));

        dsTax2.put(KasebContract.DetailSaleTaxes.COLUMN_DETAIL_SALE_ID, ds2TaxItems.getInt(KasebContract.DetailSaleTaxes.COLUMN_DETAIL_SALE_ID));
        dsTax2.put(KasebContract.DetailSaleTaxes.COLUMN_TAX_TYPE_ID, ds2TaxItems.getInt(KasebContract.DetailSaleTaxes.COLUMN_TAX_TYPE_ID));
        dsTax2.put(KasebContract.DetailSaleTaxes.COLUMN_AMOUNT, ds2TaxItems.getFloat(KasebContract.DetailSaleTaxes.COLUMN_AMOUNT));

        Uri ds1TaxUri = context.getContentResolver().insert(KasebContract.DetailSaleTaxes.CONTENT_URI, dsTax1);
        Uri ds2TaxUri = context.getContentResolver().insert(KasebContract.DetailSaleTaxes.CONTENT_URI, dsTax2);
    }

    private static void dSalePaymentInits(Context context) {
        setDS1PaymentItems(context);
        setDS2PaymentItems(context);
        ContentValues dsPayment1 = new ContentValues();
        ContentValues dsPayment2 = new ContentValues();

        dsPayment1.put(KasebContract.DetailSalePayments.COLUMN_DETAIL_SALE_ID, ds1PaymentItems.getInt(KasebContract.DetailSalePayments.COLUMN_DETAIL_SALE_ID));
        dsPayment1.put(KasebContract.DetailSalePayments.COLUMN_MODIFIED_DATE, ds1PaymentItems.getString(KasebContract.DetailSalePayments.COLUMN_MODIFIED_DATE));
        dsPayment1.put(KasebContract.DetailSalePayments.COLUMN_DUE_DATE, ds1PaymentItems.getString(KasebContract.DetailSalePayments.COLUMN_DUE_DATE));
        dsPayment1.put(KasebContract.DetailSalePayments.COLUMN_PAYMENT_METHOD_ID, ds1PaymentItems.getInt(KasebContract.DetailSalePayments.COLUMN_PAYMENT_METHOD_ID));
        dsPayment1.put(KasebContract.DetailSalePayments.COLUMN_AMOUNT, ds1PaymentItems.getFloat(KasebContract.DetailSalePayments.COLUMN_AMOUNT));

        dsPayment2.put(KasebContract.DetailSalePayments.COLUMN_DETAIL_SALE_ID, ds2PaymentItems.getInt(KasebContract.DetailSalePayments.COLUMN_DETAIL_SALE_ID));
        dsPayment2.put(KasebContract.DetailSalePayments.COLUMN_MODIFIED_DATE, ds2PaymentItems.getString(KasebContract.DetailSalePayments.COLUMN_MODIFIED_DATE));
        dsPayment2.put(KasebContract.DetailSalePayments.COLUMN_DUE_DATE, ds2PaymentItems.getString(KasebContract.DetailSalePayments.COLUMN_DUE_DATE));
        dsPayment2.put(KasebContract.DetailSalePayments.COLUMN_PAYMENT_METHOD_ID, ds2PaymentItems.getInt(KasebContract.DetailSalePayments.COLUMN_PAYMENT_METHOD_ID));
        dsPayment2.put(KasebContract.DetailSalePayments.COLUMN_AMOUNT, ds2PaymentItems.getFloat(KasebContract.DetailSalePayments.COLUMN_AMOUNT));

        Uri ds1PaymentUri = context.getContentResolver().insert(KasebContract.DetailSalePayments.CONTENT_URI, dsPayment1);
        Uri ds2PaymentUri = context.getContentResolver().insert(KasebContract.DetailSalePayments.CONTENT_URI, dsPayment2);
    }

    private static void dSaleProductInits(Context context) {
        setDS1ProductItems(context);
        setDS2ProductItems(context);
        ContentValues dsProduct1 = new ContentValues();
        ContentValues dsProduct2 = new ContentValues();

        dsProduct1.put(KasebContract.DetailSaleProducts.COLUMN_PRODUCT_ID, ds1ProductItems.getInt(KasebContract.DetailSaleProducts.COLUMN_PRODUCT_ID));
        dsProduct1.put(KasebContract.DetailSaleProducts.COLUMN_DETAIL_SALE_ID, ds1ProductItems.getInt(KasebContract.DetailSaleProducts.COLUMN_DETAIL_SALE_ID));
        dsProduct1.put(KasebContract.DetailSaleProducts.COLUMN_QUANTITY, ds1ProductItems.getInt(KasebContract.DetailSaleProducts.COLUMN_QUANTITY));
        dsProduct1.put(KasebContract.DetailSaleProducts.COLUMN_AMOUNT, ds1ProductItems.getFloat(KasebContract.DetailSaleProducts.COLUMN_AMOUNT));

        dsProduct2.put(KasebContract.DetailSaleProducts.COLUMN_PRODUCT_ID, ds2ProductItems.getInt(KasebContract.DetailSaleProducts.COLUMN_PRODUCT_ID));
        dsProduct2.put(KasebContract.DetailSaleProducts.COLUMN_DETAIL_SALE_ID, ds2ProductItems.getInt(KasebContract.DetailSaleProducts.COLUMN_DETAIL_SALE_ID));
        dsProduct2.put(KasebContract.DetailSaleProducts.COLUMN_QUANTITY, ds2ProductItems.getInt(KasebContract.DetailSaleProducts.COLUMN_QUANTITY));
        dsProduct2.put(KasebContract.DetailSaleProducts.COLUMN_AMOUNT, ds2ProductItems.getFloat(KasebContract.DetailSaleProducts.COLUMN_AMOUNT));

        Uri dsProductUri = context.getContentResolver().insert(KasebContract.DetailSaleProducts.CONTENT_URI, dsProduct1);
        Uri dsProductUri2 = context.getContentResolver().insert(KasebContract.DetailSaleProducts.CONTENT_URI, dsProduct2);
    }

    private static void salesInits(Context context) {
        setSale_1(context);
        setSale_2(context);
        ContentValues sale1 = new ContentValues();
        ContentValues sale2 = new ContentValues();
        sale1.put(KasebContract.Sales.COLUMN_SALE_CODE, sale_1.getString(KasebContract.Sales.COLUMN_SALE_CODE));
        sale2.put(KasebContract.Sales.COLUMN_SALE_CODE, sale_2.getString(KasebContract.Sales.COLUMN_SALE_CODE));
        sale1.put(KasebContract.Sales.COLUMN_CUSTOMER_ID, sale_1.getInt(KasebContract.Sales.COLUMN_CUSTOMER_ID));
        sale2.put(KasebContract.Sales.COLUMN_CUSTOMER_ID, sale_2.getInt(KasebContract.Sales.COLUMN_CUSTOMER_ID));

        Uri saleUri = context.getContentResolver().insert(KasebContract.Sales.CONTENT_URI, sale1);
        Uri saleUri2 = context.getContentResolver().insert(KasebContract.Sales.CONTENT_URI, sale2);
    }

    private static void costsInits(Context context) {
        setCost_1(context);
        ContentValues cost = new ContentValues();
        cost.put(KasebContract.Costs.COLUMN_COST_NAME, cost_1.getString(KasebContract.Costs.COLUMN_COST_NAME));
        cost.put(KasebContract.Costs.COLUMN_COST_CODE, cost_1.getString(KasebContract.Costs.COLUMN_COST_CODE));
        cost.put(KasebContract.Costs.COLUMN_COST_TYPE_ID, cost_1.getInt(KasebContract.Costs.COLUMN_COST_TYPE_ID));
        cost.put(KasebContract.Costs.COLUMN_AMOUNT, cost_1.getFloat(KasebContract.Costs.COLUMN_AMOUNT));
        cost.put(KasebContract.Costs.COLUMN_DATE, cost_1.getString(KasebContract.Costs.COLUMN_DATE));
        cost.put(KasebContract.Costs.COLUMN_DESCRIPTION, cost_1.getString(KasebContract.Costs.COLUMN_DESCRIPTION));
        Uri costUri = context.getContentResolver().insert(KasebContract.Costs.CONTENT_URI, cost);
    }

    private static void productInits(Context context) {
        setProduct_1(context);
        ContentValues product = new ContentValues();
        product.put(KasebContract.Products.COLUMN_PRODUCT_NAME, product_1.getString(KasebContract.Products.COLUMN_PRODUCT_NAME));
        product.put(KasebContract.Products.COLUMN_PRODUCT_CODE, product_1.getString(KasebContract.Products.COLUMN_PRODUCT_CODE));
        product.put(KasebContract.Products.COLUMN_UNIT, product_1.getString(KasebContract.Products.COLUMN_UNIT));
        product.put(KasebContract.Products.COLUMN_DESCRIPTION, product_1.getString(KasebContract.Products.COLUMN_DESCRIPTION));
        Uri productUri = context.getContentResolver().insert(KasebContract.Products.CONTENT_URI, product);

    }

    private static void customerInits(Context context) {
        setCustomer_1(context);
        ContentValues customer = new ContentValues();
        customer.put(KasebContract.Customers.COLUMN_FIRST_NAME, customer_1.getString(KasebContract.Customers.COLUMN_FIRST_NAME));
        customer.put(KasebContract.Customers.COLUMN_LAST_NAME, customer_1.getString(KasebContract.Customers.COLUMN_LAST_NAME));
        customer.put(KasebContract.Customers.COLUMN_PHONE_MOBILE, customer_1.getString(KasebContract.Customers.COLUMN_PHONE_MOBILE));
        customer.put(KasebContract.Customers.COLUMN_EMAIL, customer_1.getString(KasebContract.Customers.COLUMN_EMAIL));
        customer.put(KasebContract.Customers.COLUMN_PHONE_WORK, customer_1.getString(KasebContract.Customers.COLUMN_PHONE_WORK));
        customer.put(KasebContract.Customers.COLUMN_PHONE_HOME, customer_1.getString(KasebContract.Customers.COLUMN_PHONE_HOME));
        customer.put(KasebContract.Customers.COLUMN_PHONE_FAX, customer_1.getString(KasebContract.Customers.COLUMN_PHONE_FAX));
        customer.put(KasebContract.Customers.COLUMN_PHONE_OTHER, customer_1.getString(KasebContract.Customers.COLUMN_PHONE_OTHER));
        customer.put(KasebContract.Customers.COLUMN_ADDRESS_COUNTRY, customer_1.getString(KasebContract.Customers.COLUMN_ADDRESS_COUNTRY));
        customer.put(KasebContract.Customers.COLUMN_ADDRESS_CITY, customer_1.getString(KasebContract.Customers.COLUMN_ADDRESS_CITY));
        customer.put(KasebContract.Customers.COLUMN_ADDRESS_STREET, customer_1.getString(KasebContract.Customers.COLUMN_ADDRESS_STREET));
        customer.put(KasebContract.Customers.COLUMN_ADDRESS_POSTAL_CODE, customer_1.getString(KasebContract.Customers.COLUMN_ADDRESS_POSTAL_CODE));
        customer.put(KasebContract.Customers.COLUMN_DESCRIPTION, customer_1.getString(KasebContract.Customers.COLUMN_DESCRIPTION));
        customer.put(KasebContract.Customers.COLUMN_STATE_ID, customer_1.getInt(KasebContract.Customers.COLUMN_STATE_ID));
        Uri customerUri = context.getContentResolver().insert(KasebContract.Customers.CONTENT_URI, customer);
    }

    private static void stateInits(Context context) {
        ContentValues[] contentValues;
        contentValues = new ContentValues[4];
        String[] ids = new String[]{
                context.getResources().getString(R.string.states_gold),
                context.getResources().getString(R.string.states_silver),
                context.getResources().getString(R.string.states_bronze, R.string.states_instart),
                context.getResources().getString(R.string.states_instart)};
        for (int i = 0; i < ids.length; i++) {
            ContentValues states = new ContentValues();
            states.put(KasebContract.State.COLUMN_STATE_POINTER, ids[i]);
            contentValues[i] = states;
        }

        int insertedUri = context.getContentResolver().bulkInsert(
                KasebContract.State.CONTENT_URI,
                contentValues
        );
        Log.d(LOG_TAG, "Data successfully initialized to: " + Integer.toString(insertedUri));


    }

    private static void paymentsMethodinit(Context context) {
        ContentValues[] contentValues;
        contentValues = new ContentValues[4];
        String[] ids = new String[]{context.getResources().getString(R.string.payment_method_cash)
                , context.getResources().getString(R.string.payment_method_cheque)
                , context.getResources().getString(R.string.payment_method_credit)
                , context.getResources().getString(R.string.payment_method_pos)};
        for (int i = 0; i < ids.length; i++) {
            ContentValues paymentMethods = new ContentValues();
            paymentMethods.put(KasebContract.PaymentMethods.COLUMN_PAYMENT_METHOD_POINTER, ids[i]);
            contentValues[i] = paymentMethods;
        }

        int insertedUri = context.getContentResolver().bulkInsert(
                KasebContract.PaymentMethods.CONTENT_URI,
                contentValues
        );
        Log.d(LOG_TAG, "Data successfully initialized to: " + Integer.toString(insertedUri));

    }

    private static void taxTypesInit(Context context) {
        ContentValues[] contentValues;
        contentValues = new ContentValues[2];
        String[] ids = new String[]{context.getResources().getString(R.string.tax_types_vas)
                , context.getResources().getString(R.string.tax_types_discount)};
        for (int i = 0; i < ids.length; i++) {
            ContentValues taxTypes = new ContentValues();
            taxTypes.put(KasebContract.TaxTypes.COLUMN_TAX_TYPE_POINTER, ids[i]);
            contentValues[i] = taxTypes;
        }

        int insertedUri = context.getContentResolver().bulkInsert(
                KasebContract.TaxTypes.CONTENT_URI,
                contentValues
        );
        Log.d(LOG_TAG, "Data successfully initialized to: " + Integer.toString(insertedUri));

    }

    private static void costTypesInit(Context context) {
        ContentValues[] contentValues;
        contentValues = new ContentValues[4];
        String[] ids = new String[]{context.getResources().getString(R.string.cost_types_bill),
                context.getResources().getString(R.string.cost_types_salary),
                context.getResources().getString(R.string.cost_types_tax),
                context.getResources().getString(R.string.cost_types_others)};
        for (int i = 0; i < ids.length; i++) {
            ContentValues costTypes = new ContentValues();
            costTypes.put(KasebContract.CostTypes.COLUMN_COST_TYPE_POINTER, ids[i]);
            contentValues[i] = costTypes;
        }

        int insertedUri = context.getContentResolver().bulkInsert(
                KasebContract.CostTypes.CONTENT_URI,
                contentValues
        );
        Log.d(LOG_TAG, "Data successfully initialized to: " + Integer.toString(insertedUri));
    }

    public static void clearForm(ViewGroup group) {
        for (int i = 0, count = group.getChildCount(); i < count; ++i) {
            View view = group.getChildAt(i);
            if (view instanceof EditText) {
                ((EditText) view).setText("");
            }
            if (view instanceof ViewGroup && (((ViewGroup) view).getChildCount() > 0))
                clearForm((ViewGroup) view);
        }
    }

    public static String preInsertDate(Context context) {

        Roozh persianCalendar = Roozh.getInstance(RoozhLocale.PERSIAN);
        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();
        Roozh pCalendar = persianCalendar.gregorianToPersian(date.getTime());
        int pYear = pCalendar.getYear();
        int pMonth = pCalendar.getMonth();
        int pDay = pCalendar.getDayOfMonth();
        String pDate = Integer.toString(pYear) + "/" + Integer.toString(pMonth) + "/" + Integer.toString(pDay);
        if (!getLocale(context).equals("IR")) {
           return formatDate(calendar);
        } else {
            return pDate;
        }
    }
}
