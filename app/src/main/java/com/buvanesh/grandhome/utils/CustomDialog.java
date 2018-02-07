package com.buvanesh.grandhome.utils;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TimePicker;

import com.buvanesh.grandhome.R;
import com.buvanesh.grandhome.data.GrandHomeData;
import com.buvanesh.grandhome.viewmodel.CustomDialogViewModel;
import com.buvanesh.grandhome.viewmodel.HomeViewModel;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import static android.support.v4.util.Preconditions.checkArgument;

/**
 * Created by buvaneshkumar_p on 10/26/2017.
 */

public class CustomDialog implements CustomDialogView, DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {
    private static String deleteTask = "Delete Task";
    private static String updateStatus = "Update Status";
    private static String deleteTaskMessage = "Do you want to delete the Task?";
    private static String completeMessage = "Change it to Complete?";
    private static String inProgressMessage = "Change it to InProgress?";
    private static boolean isBanking = true;
    private static boolean isIndividaul = true;
    private DatePickerDialog datePickerDialog;
    private TimePickerDialog mTimePicker;
    public static final String INDIVIDUAL_NAME = "MySelf";

    static CustomTaskViewHolder mViewHolder;
    CustomDialogViewModel mListener;
    public static CustomDialog mCustomDialog = null;

    public static CustomDialog getInstance(){
        if(mCustomDialog == null){
            return new CustomDialog();
        }
        return mCustomDialog;
    }

    private void initCalendar(Context context) {
        Calendar calendar = Calendar.getInstance();
        int startYear = calendar.get(Calendar.YEAR);
        int starthMonth =calendar.get(Calendar.MONTH);
        int startDay = calendar.get(Calendar.DAY_OF_MONTH);
        datePickerDialog = new DatePickerDialog(
                context,this, startYear, starthMonth, startDay);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        mTimePicker = new TimePickerDialog(context,this,hour,minute,true);
    }

    public  void customDialogTravel(final Context context, final boolean isTrain, final HomeViewModel homeViewModel){
        final Dialog myDialog = new Dialog(context);
        myDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        myDialog.setCancelable(false);
        myDialog.setContentView(R.layout.item_custom_dialog_travel);
        mViewHolder = new CustomTaskViewHolder();
        initCalendar(context);
        mListener = new CustomDialogViewModel(context,this);
        mViewHolder.mEdtName = (EditText)myDialog.findViewById(R.id.edt_travel_name);
        mViewHolder.mEdtTrainNo = (EditText)myDialog.findViewById(R.id.edt_travel_train_name);
        mViewHolder.mEdtAmount = (EditText)myDialog.findViewById(R.id.edt_travel_amount);
        mViewHolder.mEdtDate = (EditText)myDialog.findViewById(R.id.edt_travel_date);
        mViewHolder.mEdtTime = (EditText)myDialog.findViewById(R.id.edt_travel_time);
        mViewHolder.mEdtSeatNo = (EditText)myDialog.findViewById(R.id.edt_travel_seat);
        mViewHolder.mEdtCoachNo = (EditText)myDialog.findViewById(R.id.edt_travel_coach);
        mViewHolder.mEdtBoarding = (EditText)myDialog.findViewById(R.id.edt_travel_boarding);
        mViewHolder.mEdtPNR = (EditText)myDialog.findViewById(R.id.edt_travel_pnr);
        mViewHolder.mEdtComment =(EditText)myDialog.findViewById(R.id.edt_travel_message);
        mViewHolder.mImgSave = (ImageView)myDialog.findViewById(R.id.img_travel_save);
        if(isTrain){
            mViewHolder.mEdtTrainNo.setHint(R.string.train_name);
            mViewHolder.mImgSave.setImageResource(R.drawable.ic_train);
        }else {
            mViewHolder.mEdtTrainNo.setHint(R.string.bus_name);
            mViewHolder.mImgSave.setImageResource(R.drawable.ic_bus);
        }
        ImageView imgClose = (ImageView)myDialog.findViewById(R.id.img_travel_close);
        imgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myDialog.dismiss();
            }
        });
        if(isIndividaul){
            mViewHolder.mEdtName.setVisibility(View.GONE);
        }else {
            mViewHolder.mEdtName.setVisibility(View.VISIBLE);
        }
        mViewHolder.mEdtDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePickerDialog.show();
            }
        });
        mViewHolder.mEdtTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mTimePicker.show();
            }
        });
        mViewHolder.mImgSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mListener.pushNewTravel(getValue(mViewHolder.mEdtName),getValue(mViewHolder.mEdtTrainNo),getValue(mViewHolder.mEdtAmount),getValue(mViewHolder.mEdtDate),
                        getValue(mViewHolder.mEdtTime),getValue(mViewHolder.mEdtSeatNo),getValue(mViewHolder.mEdtCoachNo),getValue(mViewHolder.mEdtBoarding),
                        getValue(mViewHolder.mEdtPNR),getValue(mViewHolder.mEdtComment),isTrain,isIndividaul)){
                    myDialog.dismiss();
                    homeViewModel.refreshList();
                }
            }
        });
        myDialog.show();
        Window window = myDialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
    }

    private String getValue(EditText editText){
        return editText.getText().toString();
    }

    public void customDialogBanking(final Context context, final boolean isCredit, final HomeViewModel homeViewModel){
        final Dialog myDialog = new Dialog(context);
        myDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        myDialog.setCancelable(false);
        myDialog.setContentView(R.layout.item_custom_dialog_banking);
        mViewHolder = new CustomTaskViewHolder();
        mListener = new CustomDialogViewModel(context,this);
        mViewHolder.mImgSave = (ImageView)myDialog.findViewById(R.id.img_banking_save);
        mViewHolder.mEdtName = (EditText) myDialog.findViewById(R.id.edt_task_name);
        mViewHolder.mEdtAmount = (EditText) myDialog.findViewById(R.id.edt_task_amount);
        mViewHolder.mEdtComment = (EditText) myDialog.findViewById(R.id.edt_task_message);
        if(isCredit){
            mViewHolder.mImgSave.setImageResource(R.drawable.ic_credit);
        }else {
            mViewHolder.mImgSave.setImageResource(R.drawable.ic_debit);
        }
        if(isIndividaul){
            mViewHolder.mEdtName.setVisibility(View.GONE);
        }else {
            mViewHolder.mEdtName.setVisibility(View.VISIBLE);
        }
        ImageView imgClose = (ImageView) myDialog.findViewById(R.id.img_banking_close);
        imgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myDialog.dismiss();
            }
        });
        mViewHolder.mImgSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(mListener.pushNewBanking(mViewHolder.mEdtName.getText().toString(),mViewHolder.mEdtAmount.getText().toString(),
                        mViewHolder.mEdtComment.getText().toString(),isCredit,isIndividaul)){
                    myDialog.dismiss();
                    homeViewModel.refreshList();
                }
            }
        });
        myDialog.show();
        Window window = myDialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
    }

    public void customDialog(final Context context, final HomeViewModel homeViewModel) {
        final Dialog myDialog = new Dialog(context);
        myDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        myDialog.setCancelable(false);
        myDialog.setContentView(R.layout.item_custom_dialog);
        mListener = new CustomDialogViewModel(context,this);
        final CustomViewHolder viewHolder = new CustomViewHolder();
        viewHolder.mSpinnerTask = (Spinner)myDialog.findViewById(R.id.spin_task_type);
        viewHolder.mImgCreditTrain = (ImageView)myDialog.findViewById(R.id.img_credit_train);
        viewHolder.mImgDebitBus = (ImageView)myDialog.findViewById(R.id.img_debit_bus);
        viewHolder.mImgClose = (ImageView)myDialog.findViewById(R.id.img_main_close);
        viewHolder.mRadioGroupIndividual = (RadioGroup)myDialog.findViewById(R.id.radio_grp_individual);
        viewHolder.mSpinnerTask.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(i==0){
                    viewHolder.mImgCreditTrain.setImageResource(R.drawable.ic_credit);
                    viewHolder.mImgDebitBus.setImageResource(R.drawable.ic_debit);
                    isBanking = true;
                }else{
                    viewHolder.mImgCreditTrain.setImageResource(R.drawable.ic_train);
                    viewHolder.mImgDebitBus.setImageResource(R.drawable.ic_bus);
                    isBanking = false;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        viewHolder.mImgCreditTrain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int id =  viewHolder.mRadioGroupIndividual.getCheckedRadioButtonId();
                if(id == R.id.radio_individual){
                    isIndividaul = true;
                }else {
                    isIndividaul = false;
                }
                if(isBanking) {
                    customDialogBanking(context, true, homeViewModel);
                }else{
                    CustomDialog.getInstance().customDialogTravel(context, true, homeViewModel);
                }
                myDialog.dismiss();
            }
        });
        viewHolder.mImgDebitBus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int id =  viewHolder.mRadioGroupIndividual.getCheckedRadioButtonId();
                if(id == R.id.radio_individual){
                    isIndividaul = true;
                }else {
                    isIndividaul = false;
                }
                if(isBanking) {
                    customDialogBanking(context, false, homeViewModel);
                }else{
                    CustomDialog.getInstance().customDialogTravel(context, false, homeViewModel);
                }
                myDialog.dismiss();
            }
        });
        viewHolder.mImgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myDialog.dismiss();
            }
        });
        myDialog.show();
        Window window = myDialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
    }

    @Override
    public void NameError(String error) {
        if(mViewHolder!=null) {
            mViewHolder.mEdtName.setError(error);
        }
    }

    @Override
    public void TrainNameError(String error) {
        if(mViewHolder!=null) {
            mViewHolder.mEdtTrainNo.setError(error);
        }
    }

    @Override
    public void AmountError(String error) {
        if(mViewHolder!=null) {
            mViewHolder.mEdtAmount.setError(error);
        }
    }

    @Override
    public void DateError(String error) {
        if(mViewHolder!=null) {
            mViewHolder.mEdtDate.setError(error);
        }
    }

    @Override
    public void TimeError(String error) {
        if(mViewHolder!=null) {
            mViewHolder.mEdtTime.setError(error);
        }
    }

    @Override
    public void SeatNoError(String error) {
        if(mViewHolder!=null) {
            mViewHolder.mEdtSeatNo.setError(error);
        }
    }

    @Override
    public void CoachNoError(String error) {
        if(mViewHolder!=null) {
            mViewHolder.mEdtCoachNo.setError(error);
        }
    }

    @Override
    public void BoardingError(String error) {
        if(mViewHolder!=null) {
            mViewHolder.mEdtBoarding.setError(error);
        }
    }

    @Override
    public void CommentError(String error) {
        if(mViewHolder!=null) {
            mViewHolder.mEdtComment.setError(error);
        }
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
        mViewHolder.mEdtDate.setText(new StringBuilder().append(dayOfMonth+getDayOfMonthSuffix(dayOfMonth))
                .append("  ").append(getMonthShortName(month + 1)));
    }

    @Override
    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
        mViewHolder.mEdtTime.setText(hourOfDay+":"+minute);
    }

    static class CustomViewHolder {
        Spinner mSpinnerTask;
        ImageView mImgCreditTrain;
        ImageView mImgDebitBus;
        ImageView mImgClose;
        RadioGroup mRadioGroupIndividual;
    }

    static class CustomTaskViewHolder{
        EditText mEdtName;
        EditText mEdtAmount;
        EditText mEdtDate;
        EditText mEdtTime;
        EditText mEdtTrainNo;
        EditText mEdtSeatNo;
        EditText mEdtCoachNo;
        EditText mEdtBoarding;
        EditText mEdtPNR;
        EditText mEdtComment;
        ImageView mImgSave;

        /*TextInputLayout mErrorName;
        TextInputLayout mErrorAmount;
        TextInputLayout mErrorDate;
        TextInputLayout mErrorTime;
        TextInputLayout mErrorTrainNo;
        TextInputLayout mErrorSeatNo;
        TextInputLayout mErrorCoachNo;
        TextInputLayout mErrorBoarding;
        TextInputLayout mErrorComment;*/
    }

    public static String getMonthShortName(int monthNumber)
    {
        String monthName="";

        if(monthNumber>=0 && monthNumber<12)
            try
            {
                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.MONTH, monthNumber);

                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMM");
                simpleDateFormat.setCalendar(calendar);
                monthName = simpleDateFormat.format(calendar.getTime());
            }
            catch (Exception e)
            {
                if(e!=null)
                    e.printStackTrace();
            }
        return monthName;
    }

    @SuppressLint("RestrictedApi")
    String getDayOfMonthSuffix(final int n) {
        checkArgument(n >= 1 && n <= 31, "illegal day of month: " + n);
        if (n >= 11 && n <= 13) {
            return "th";
        }
        switch (n % 10) {
            case 1:  return "st";
            case 2:  return "nd";
            case 3:  return "rd";
            default: return "th";
        }
    }

}
