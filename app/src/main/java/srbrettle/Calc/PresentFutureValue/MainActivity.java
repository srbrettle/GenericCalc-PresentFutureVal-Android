package srbrettle.Calc.PresentFutureValue;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
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

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TextView message;
    private Button btnCalculate;
    private Button btnClear;
    private List<InputOption> inputOptions = new ArrayList<>();
    int numChecked = 0;
    boolean isPaused = false;
    boolean error = false;

    //Settings------------------
    int iNumToCheck = 3;
    int iNumOfInputOptions = 4;
    //--------------------------

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        CheckBox cbVal1 = findViewById(R.id.checkBoxVal1);
        CheckBox cbVal2 = findViewById(R.id.checkBoxVal2);
        CheckBox cbVal3 = findViewById(R.id.checkBoxVal3);
        CheckBox cbVal4 = findViewById(R.id.checkBoxVal4);
        EditText etVal1 = findViewById(R.id.editTextVal1);
        EditText  etVal2 = findViewById(R.id.editTextVal2);
        EditText  etVal3 = findViewById(R.id.editTextVal3);
        EditText  etVal4 = findViewById(R.id.editTextVal4);
        inputOptions.add(new InputOption(1, cbVal1, etVal1));
        inputOptions.add(new InputOption(2, cbVal2, etVal2));
        inputOptions.add(new InputOption(3, cbVal3, etVal3));
        inputOptions.add(new InputOption(4, cbVal4, etVal4));

        message = findViewById(R.id.textViewUpdated);
        message.setText("");

        addListenerOnButtons();

        // Initialise Values
        cbVal1.setChecked(true);
        cbVal2.setChecked(true);
        cbVal3.setChecked(true);
        cbVal4.setEnabled(false);
        etVal1.setEnabled(true);
        etVal2.setEnabled(true);
        etVal3.setEnabled(true);
        numChecked = 3;
    }

    public void onCheckboxClicked(View view) {
        boolean checked = ((CheckBox) view).isChecked();

        // Handle the checking of the specific checkBox
        for (InputOption item:inputOptions) {
            if (view.getId() == item.checkBox.getId()) {
                handleCheckOrUncheck(checked, item.editText);
                break;
            }
        }

        // Handle enabling / disabling depending on if enough
        // have been checked to run calculation
        for (InputOption item:inputOptions) {
            CheckBox currentCheckBox = item.checkBox;
            EditText currentEditText = item.editText;
            if (!currentCheckBox.isChecked()) {
                if (numChecked == iNumToCheck) {
                    currentCheckBox.setEnabled(false);
                    currentEditText.setEnabled(false);
                }
                else {
                    currentCheckBox.setEnabled(true);
                }
            }
        }
    }

    private void handleCheckOrUncheck(boolean checked, EditText editText) {
        if (checked)
        {
            numChecked++;
            editText.setEnabled(true);
        }
        else
        {
            numChecked--;
            editText.setEnabled(false);
        }
    }

    public void addListenerOnButtons() {
        addListenerOnCalculateButton();
        addListenerOnClearButton();
    }

    private void addListenerOnCalculateButton() {
        // CALCULATE Button
        btnCalculate = findViewById(R.id.btnCalculate);
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
                    for (InputOption item:inputOptions) {
                        CheckBox currentCheckBox = item.checkBox;
                        EditText currentEditText = item.editText;
                        if (currentCheckBox.isChecked()) {
                            if (currentEditText.getText().toString().equals("")) {
                                message.setText("Please input value for " + currentCheckBox.getText() + ".");
                                error = true;
                            }
                        }
                    }
                }

                if (!error) {
                    runCalculation();
                }
                btnCalculate.requestFocus();
            }
        });
        btnCalculate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    message.setText("");

                    for (InputOption item:inputOptions) {
                        CheckBox currentCheckBox = item.checkBox;
                        EditText currentEditText = item.editText;
                        if (!currentCheckBox.isChecked()) {
                            currentEditText.setText("");
                        }
                    }
                } else {
                    btnCalculate.performClick();
                }
            }
        });
    }

    private void addListenerOnClearButton() {
        // RESET Button
        btnClear = findViewById(R.id.btnClear);
        btnClear.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                for (InputOption item:inputOptions) {
                    CheckBox currentCheckBox = item.checkBox;
                    EditText currentEditText = item.editText;
                    currentCheckBox.setChecked(false);
                    currentCheckBox.setEnabled(true);
                    currentEditText.setEnabled(false);
                    currentEditText.setText("");
                }
                message.setText("");
                numChecked = 0;
            }
        });
    }

    private void runCalculation() {
        double[] val = new double[iNumOfInputOptions];

        // Get Values from editTexts
        for (InputOption item:inputOptions) {
            int orderId = item.orderId;
            EditText currentEditText = item.editText;
            if (currentEditText.isEnabled()) {
                val[orderId-1] = Double.parseDouble(currentEditText.getText().toString());
            }
        }

        // Carry out calculations
        for (InputOption item:inputOptions) {
            int orderId = item.orderId;
            CheckBox currentCheckBox = item.checkBox;
            EditText currentEditText = item.editText;
            if (!currentCheckBox.isChecked())
            {
                if (orderId==1) {
                    currentEditText.setText(String.format("%,.3f", CalculationMaths.FormulaVal1(val[1],val[2],val[3]))); //Set to result of FormulaVal1
                }
                else if (orderId==2)
                {
                    currentEditText.setText(String.format("%,.3f", CalculationMaths.FormulaVal2(val[0],val[2],val[3]))); //Set to result of FormulaVal2
                }
                else if (orderId==3)
                {
                    currentEditText.setText(String.format("%,.3f", CalculationMaths.FormulaVal3(val[0],val[1],val[3]))); //Set to result of FormulaVal3
                }
                else if (orderId==4)
                {
                    currentEditText.setText(String.format("%,.3f", CalculationMaths.FormulaVal4(val[0],val[1],val[2]))); //Set to result of FormulaVal4
                }
            }
        }

        message.setText(getResources().getString(R.string.updated));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
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
}