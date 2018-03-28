package me.oogh.similar.murmur;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;

import java.util.Calendar;
import java.util.Date;

/**
 * @author oogh <oogh216@163.com>
 * @date 2018-03-25
 * @description 日期选择对话框
 */

public class DialogDatePicker extends DialogFragment implements DatePickerDialog.OnDateSetListener {
    private static final String TAG = DialogDatePicker.class.getSimpleName();

    private OnDateSetListener mListener;

    public void setOnDateSetListener(OnDateSetListener listener) {
        mListener = listener;
    }

    public DialogDatePicker() {
    }

    public static DialogDatePicker newInstance() {
        DialogDatePicker dialog = new DialogDatePicker();
        return dialog;
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog dialog = new DatePickerDialog(getContext(), this, year, month, day);
        calendar.set(year, month, day + 1);

        DatePicker datePicker = dialog.getDatePicker();
        datePicker.setMinDate(calendar.getTimeInMillis());
        return dialog;
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, dayOfMonth);
        mListener.onDateSet(calendar.getTime(), !calendar.getTime().before(new Date(view.getMinDate())));
    }

    public interface OnDateSetListener {
        /*
            Android 5.0 BUG
            1. DatePicker设置 minDate只能在显示上有所改变
            2. 但是最小可选范围之外依然可以被选中
            3. 因此，添加了一个valid变量，用于确定选中的时间点是否有效
         */
        void onDateSet(Date date, boolean valid);
    }

}
