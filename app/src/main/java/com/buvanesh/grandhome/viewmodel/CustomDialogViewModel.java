package com.buvanesh.grandhome.viewmodel;

import android.content.Context;
import android.text.TextUtils;

import com.buvanesh.grandhome.R;
import com.buvanesh.grandhome.data.GrandHomeData;
import com.buvanesh.grandhome.model.TaskModel;
import com.buvanesh.grandhome.utils.CustomDialogView;

import static com.buvanesh.grandhome.utils.CustomDialog.INDIVIDUAL_NAME;

/**
 * Created by buvaneshkumar_p on 2/5/2018.
 */

public class CustomDialogViewModel {
    private Context mContext;
    private CustomDialogView mListener;

    public CustomDialogViewModel(Context mContext, CustomDialogView mListener) {
        this.mContext = mContext;
        this.mListener = mListener;
    }


    public boolean pushNewBanking(String name,String amount, String message,boolean isCredit,boolean isIndividual){
        boolean isResult = true;
        if(isIndividual){
            name = INDIVIDUAL_NAME;
        }else {
            if(TextUtils.isEmpty(name)){
                mListener.NameError(mContext.getString(R.string.name_error));
                isResult=false;
            }
        }
        if(TextUtils.isEmpty(amount)){
            mListener.AmountError(mContext.getString(R.string.amount_error));
            isResult = false;
        }
        if(TextUtils.isEmpty(message)){
            mListener.CommentError(mContext.getString(R.string.comment_error));
            isResult = false;
        }
        if(isResult){
            TaskModel taskModel = new TaskModel();
            taskModel.setName(name);
            taskModel.setAmount(amount);
            taskModel.setMessage(message);
            taskModel.setCredit(isCredit);
            taskModel.setBanking(true);
            GrandHomeData grandHomeData = new GrandHomeData(mContext);
            grandHomeData.insertNewTask(taskModel);
        }
        return isResult;
    }

    public boolean pushNewTravel(String name,String trainNo, String amount, String date, String time,String seat,String coach,
                                 String boarding, String pnr,String message,boolean isTrain, boolean isIndividual){
        boolean isResult = true;
        if(isIndividual){
            name = INDIVIDUAL_NAME;
        }else {
            if(TextUtils.isEmpty(name)){
                mListener.NameError(mContext.getString(R.string.name_error));
                isResult=false;
            }
        }
        if(TextUtils.isEmpty(amount)){
            mListener.AmountError(mContext.getString(R.string.amount_error));
            isResult = false;
        }
        if(TextUtils.isEmpty(trainNo)){
            mListener.TrainNameError(mContext.getString(R.string.trainno_error));
            isResult = false;
        }
        if(TextUtils.isEmpty(date)){
            mListener.DateError(mContext.getString(R.string.date_error));
            isResult = false;
        }
        if(TextUtils.isEmpty(time)){
            mListener.TimeError(mContext.getString(R.string.time_error));
            isResult = false;
        }
        if(TextUtils.isEmpty(seat)){
            mListener.SeatNoError(mContext.getString(R.string.seat_no_error));
            isResult = false;
        }
        if(TextUtils.isEmpty(coach)){
            mListener.CoachNoError(mContext.getString(R.string.coach_no_error));
            isResult = false;
        }
        if(TextUtils.isEmpty(boarding)){
            mListener.BoardingError(mContext.getString(R.string.boarding_error));
            isResult = false;
        }
        if(TextUtils.isEmpty(message)){
            mListener.CommentError(mContext.getString(R.string.comment_error));
        }
        if(isResult){
            TaskModel taskModel = new TaskModel();
            taskModel.setName(name);
            taskModel.setTrainNo(trainNo);
            taskModel.setAmount(amount);
            taskModel.setBoardingTime(time);
            taskModel.setBoardingDate(date);
            taskModel.setSeatNo(seat);
            taskModel.setCoachNo(coach);
            taskModel.setBoarding(boarding);
            taskModel.setPnrNo(pnr);
            taskModel.setMessage(message);
            taskModel.setTrain(isTrain);
            taskModel.setBanking(false);
            GrandHomeData grandHomeData = new GrandHomeData(mContext);
            grandHomeData.insertNewTask(taskModel);
        }

        return isResult;
    }
}
