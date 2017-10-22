package srbrettle.Calc.PresentFutureValue;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

public class MainActivity extends AppCompatActivity {

    private CheckBox cbVal1, cbVal2, cbVal3, cbVal4;
    private TextView message;
    private Button btnCalculate;
    private Button btnClear;
    private EditText etVal1, etVal2, etVal3, etVal4;
    boolean chkVal1 = false;
    boolean chkVal2 = false;
    boolean chkVal3 = false;
    boolean chkVal4 = false;
    int numChecked = 0;
    double val1 = 0;
    double val2 = 0;
    double val3 = 0;
    double val4 = 0;
    int x = 60000; //ad time count
    boolean isPaused = false;
    boolean error = false;
    InterstitialAd mInterstitialAd;
    Handler handler = new Handler();
    String status = "";

    //Settings-------------------MoreBelow
    int iNumToCheck = 3;
    String sVal1 = "";
    String sVal2 = "";
    String sVal3 = "";
    String sVal4 = "";
    //--------------------------

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

       /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
        cbVal1 = (CheckBox) findViewById(R.id.checkBoxVal1);
        cbVal2 = (CheckBox) findViewById(R.id.checkBoxVal2);
        cbVal3 = (CheckBox) findViewById(R.id.checkBoxVal3);
        cbVal4 = (CheckBox) findViewById(R.id.checkBoxVal4);
        etVal1 = (EditText) findViewById(R.id.editTextVal1);
        etVal2 = (EditText) findViewById(R.id.editTextVal2);
        etVal3 = (EditText) findViewById(R.id.editTextVal3);
        etVal4 = (EditText) findViewById(R.id.editTextVal4);
        message = (TextView) findViewById(R.id.textViewUpdated);

        addListenerOnButton();
        handler.postDelayed(runnable, 30000);

        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId(getResources().getString(R.string.interstitial_ad_unit_id));

        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                requestNewInterstitial();
                handler.postDelayed(runnable, x);
            }
        });

        requestNewInterstitial();
        cbVal1.setChecked(true);
        cbVal2.setChecked(true);
        cbVal3.setChecked(true);
        cbVal4.setEnabled(false);
        chkVal1 = true;
        chkVal2 = true;
        chkVal3 = true;
        etVal1.setEnabled(true);
        etVal2.setEnabled(true);
        etVal3.setEnabled(true);
        numChecked = 3;

        //SETTINGS-------------------------------------------------FORMULAS AT END
        sVal1 = getResources().getString(R.string.input_1);
        sVal2 = getResources().getString(R.string.input_2);
        sVal3 = getResources().getString(R.string.input_3);
        sVal4 = getResources().getString(R.string.input_4);
    }

    private void requestNewInterstitial() {
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice("ABCDEF012345")
                .build();

        mInterstitialAd.loadAd(adRequest);
    }

    public void onCheckboxClicked(View view) {
        boolean checked = ((CheckBox) view).isChecked();

        // Check which checkbox was clicked
        switch(view.getId()) {
            case R.id.checkBoxVal1:
                if (checked)
                {
                    chkVal1 = true;
                    numChecked++;
                    etVal1.setEnabled(true);
                }
                else
                {
                    chkVal1 = false;
                    numChecked--;
                    etVal1.setEnabled(false);
                }
                break;
            case R.id.checkBoxVal2:
                if (checked)
                {
                    chkVal2 = true;
                    etVal2.setEnabled(true);
                    numChecked++;
                }
                else
                {
                    chkVal2 = false;
                    numChecked--;
                    etVal2.setEnabled(false);
                }
                break;
            case R.id.checkBoxVal3:
                if (checked)
                {
                    chkVal3 = true;
                    numChecked++;
                    etVal3.setEnabled(true);

                }
                else
                {
                    chkVal3 = false;
                    numChecked--;
                    etVal3.setEnabled(false);
                }
                break;
            case R.id.checkBoxVal4:
                if (checked)
                {
                    chkVal4 = true;
                    etVal4.setEnabled(true);
                    numChecked++;
                }
                else
                {
                    chkVal4 = false;
                    numChecked--;
                    etVal4.setEnabled(false);
                }
                break;
        }
        if (numChecked == iNumToCheck)
        {
            if (chkVal1 == false)
            {
                cbVal1.setEnabled(false);
                etVal1.setEnabled(false);
            }
            if (chkVal2 == false)
            {
                cbVal2.setEnabled(false);
                etVal2.setEnabled(false);
            }
            if (chkVal3 == false)
            {
                cbVal3.setEnabled(false);
                etVal3.setEnabled(false);
            }
            if (chkVal4 == false)
            {
                cbVal4.setEnabled(false);
                etVal4.setEnabled(false);
            }
        }
        else
        {
            cbVal1.setEnabled(true);
            cbVal2.setEnabled(true);
            cbVal3.setEnabled(true);
            cbVal4.setEnabled(true);
        }
    }

    public void addListenerOnButton() {

        message.setText("");

        btnCalculate = (Button) findViewById(R.id.btnCalculate);
        btnCalculate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                //Close keyboard
                InputMethodManager imm = (InputMethodManager) getSystemService(
                        INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);

                error = false;
                if (numChecked != iNumToCheck)
                    {
                        error = true;
                        message.setText("Check " + iNumToCheck + " boxes and input values.");
                    }
                else {

                    if (chkVal1 == true) {
                        if (etVal1.getText().toString().equals("")) {
                            message.setText("Please input value for " + sVal1 + ".");
                            error = true;
                        } else {
                            val1 = Double.parseDouble(etVal1.getText().toString());
                        }
                    }
                    if (chkVal2 == true) {
                        if (etVal2.getText().toString().equals("")) {
                            message.setText("Please input value for " + sVal2 + ".");
                            error = true;
                        } else {
                            val2 = Double.parseDouble(etVal2.getText().toString());
                        }
                    }
                    if (chkVal3 == true) {
                        if (etVal3.getText().toString().equals("")) {
                            message.setText("Please input value for " + sVal3 + ".");
                            error = true;
                        } else {
                            val3 = Double.parseDouble(etVal3.getText().toString());
                        }
                    }
                    if (chkVal4 == true) {
                        if (etVal4.getText().toString().equals("")) {
                            message.setText("Please input value for " + sVal4 + ".");
                            error = true;
                        } else {
                            val4 = Double.parseDouble(etVal4.getText().toString());
                        }
                    }
                }

                if (error == false) {
                    if ((chkVal1 == true)&&(chkVal2 == true)&&(chkVal3 == true))
                    {
                        etVal4.setText(Double.toString(FormulaVal4(val1,val2,val3))); //Set to result of FormulaVal4
                    }
                    if ((chkVal1 == true)&&(chkVal2 == true)&&(chkVal4 == true))
                    {
                        val3 = (val4/(val2/val1));
                        etVal3.setText(Double.toString(FormulaVal3(val1,val2,val4))); //Set to result of FormulaVal3
                    }
                    if ((chkVal1 == true)&&(chkVal4 == true)&&(chkVal3 == true))
                    {
                        val2 = ((val4/val3)*val1);
                        etVal2.setText(Double.toString(FormulaVal2(val1,val3,val4))); //Set to result of FormulaVal2
                    }
                    if ((chkVal2 == true)&&(chkVal4 == true)&&(chkVal3 == true))
                    {
                        val1 = ((val3/val4)*val2);
                        etVal1.setText(Double.toString(FormulaVal1(val2,val3,val4))); //Set to result of FormulaVal1
                    }

                    message.setText("Values Updated.");
                }
                btnCalculate.requestFocus();
            }
        });
        btnCalculate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    message.setText("");
                    if (chkVal1 == false)
                    {
                        etVal1.setText("");
                    }
                    if (chkVal2 == false)
                    {
                        etVal2.setText("");
                    }
                    if (chkVal3 == false)
                    {
                        etVal3.setText("");
                    }
                    if (chkVal4 == false)
                    {
                        etVal4.setText("");
                    }

                } else {
                    btnCalculate.performClick();
                }
            }
        });

        btnClear = (Button) findViewById(R.id.btnClear);
        btnClear.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                cbVal1.setChecked(false);
                cbVal2.setChecked(false);
                cbVal3.setChecked(false);
                cbVal4.setChecked(false);
                etVal1.setText("");
                etVal2.setText("");
                etVal3.setText("");
                etVal4.setText("");
                cbVal1.setEnabled(true);
                cbVal2.setEnabled(true);
                cbVal3.setEnabled(true);
                cbVal4.setEnabled(true);
                etVal1.setEnabled(false);
                etVal2.setEnabled(false);
                etVal3.setEnabled(false);
                etVal4.setEnabled(false);
                message.setText("");
                chkVal1 = false;
                chkVal2 = false;
                chkVal3 = false;
                chkVal4 = false;
                numChecked = 0;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Uri uri = Uri.parse("market://details?id=" + this.getPackageName());
            Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
            try {
                startActivity(goToMarket);
            } catch (ActivityNotFoundException e) {
                this.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=" + this.getPackageName())));
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
      /* do what you need to do */
            if ((mInterstitialAd.isLoaded()) && (isPaused == false))  {
                mInterstitialAd.show();
                x=(x+30000);
            } else {
                handler.postDelayed(runnable, x);
            }
        }
    };

    @Override
    public void onPause() {
        super.onPause();  // Always call the superclass method first
        isPaused = true;
    }

    @Override
    public void onResume() {
        super.onResume();  // Always call the superclass method first

        isPaused = false;
    }

    //FORMULAS --------------------------------------------------------------------
    public double FormulaVal1(double value2, double value3, double value4) {
        double dResult = 0;
        dResult = (root((value3/value4),value2) - 1)*100; //NthRoot(F/P) - 1 [*100 to make into percentage]
        dResult = round(dResult,4);
        return dResult;
    }
    public double FormulaVal2(double value1, double value3, double value4) {
        double dResult = 0;
        dResult = (java.lang.Math.log(value3/value4)) / (java.lang.Math.log(1+(value1/100))); //N=ln(F/P)/ln(1+I)
        dResult = round(dResult,4);
        return dResult;
    }
    public double FormulaVal3(double value1, double value2, double value4) {
        double dResult = 0;
        dResult = value4 * (java.lang.Math.pow((1+(value1/100)),value2)); //F=P*(1+I)^N
        dResult = round(dResult,2);
        return dResult;
    }
    public double FormulaVal4(double value1, double value2, double value3) {
        double dResult = 0;
        dResult = value3 / (java.lang.Math.pow((1+(value1/100)),value2)); //P=F/(1+I)^N
        dResult = round(dResult,2);
        return dResult;
    }
    public static double root(double num, double root)
    {
        return Math.pow(Math.E, Math.log(num)/root);
    }
    private static double round (double value, int precision) {
        int scale = (int) Math.pow(10, precision);
        return (double) Math.round(value * scale) / scale;
    }

}