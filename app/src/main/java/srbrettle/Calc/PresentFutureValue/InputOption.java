package srbrettle.Calc.PresentFutureValue;

import android.widget.CheckBox;
import android.widget.EditText;

public class InputOption {
    public int orderId;
    public CheckBox checkBox;
    public EditText editText;

    public InputOption(int order, CheckBox chk, EditText et) {
        orderId = order;
        checkBox = chk;
        editText = et;
    }
}
