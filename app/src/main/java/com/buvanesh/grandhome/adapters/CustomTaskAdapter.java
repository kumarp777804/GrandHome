package com.buvanesh.grandhome.adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.buvanesh.grandhome.R;
import com.buvanesh.grandhome.data.GrandHomeData;
import com.buvanesh.grandhome.model.TaskModel;
import com.buvanesh.grandhome.viewmodel.HomeViewModel;

import java.util.List;

/**
 * MyHealth
 * Created by buvaneshkumar_p on 11/14/2017.
 * Copyright © 2017 Photon Infotech. All rights reserved.
 */

public class CustomTaskAdapter  extends RecyclerView.Adapter<CustomTaskAdapter.MyViewHolder>{
    private Context mContext;
    private List<TaskModel> mTaskModelList;
    private HomeViewModel mHomeViewModel;

    public CustomTaskAdapter(Context context,List<TaskModel> taskModelList,HomeViewModel homeViewModel){
        mContext = context;
        mTaskModelList = taskModelList;
        mHomeViewModel = homeViewModel;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_task_planner,
                parent,false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        TaskModel taskModel = mTaskModelList.get(position);
        if(taskModel.isBanking()){
            if(taskModel.isCredit()){
                setVisibility(holder,holder.mLayCredit);
                creditFunction(holder,taskModel);
                holder.mImgCreditUpdate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        alertDialog(mContext,mHomeViewModel,mTaskModelList.get(position).getId(),true);
                    }
                });
            }else{
                setVisibility(holder,holder.mLayDebit);
                debitFunction(holder,taskModel);
                holder.mImgDebitUpdate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        alertDialog(mContext,mHomeViewModel,mTaskModelList.get(position).getId(),true);
                    }
                });
            }
        }else {
            if(taskModel.isTrain()){
                setVisibility(holder,holder.mLayTrain);
                trainFunction(holder,taskModel);
                holder.mImgTrainUpdate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        alertDialog(mContext,mHomeViewModel,mTaskModelList.get(position).getId(),true);
                    }
                });
            }else {
                setVisibility(holder,holder.mLayBus);
                busFunction(holder,taskModel);
                holder.mImgBusUpdate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        alertDialog(mContext,mHomeViewModel,mTaskModelList.get(position).getId(),true);
                    }
                });
            }
        }
        holder.mView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                final AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);
                alertDialog.setTitle("Remove Task");
                alertDialog.setMessage("Do you want to delete the task?");
                alertDialog.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        GrandHomeData grandHomeData = new GrandHomeData(mContext);
                        grandHomeData.deleteTask(mTaskModelList.get(position).getId());
                        mHomeViewModel.refreshList();
                        dialogInterface.dismiss();
                    }
                });
                alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                alertDialog.show();
                return false;
            }
        });
    }

    private void busFunction(MyViewHolder holder,TaskModel taskModel) {
        holder.mTxtBusName.setText(taskModel.getName());
        holder.mTxtBusNo.setText(taskModel.getTrainNo()+" - "+taskModel.getBoardingDate());
        holder.mTxtBusBoarding.setText(taskModel.getBoarding()+" - "+taskModel.getBoardingTime());
        holder.mTxtBusAmount.setText(taskModel.getAmount());
        holder.mTxtBusCoach.setText(taskModel.getCoachNo());
        holder.mTxtBusSeat.setText(taskModel.getSeatNo());
        if(taskModel.isStatus()){
            holder.mImgBusUpdate.setImageResource(R.drawable.ic_completed);
        }else {
            holder.mImgBusUpdate.setImageResource(R.drawable.ic_update);
        }
    }

    private void trainFunction(MyViewHolder holder,TaskModel taskModel) {
        holder.mTxtTrainName.setText(taskModel.getName());
        holder.mTxtTrainNo.setText(taskModel.getTrainNo()+" - "+taskModel.getBoardingDate());
        holder.mTxtTrainBoarding.setText(taskModel.getBoarding()+" - "+taskModel.getBoardingTime());
        holder.mTxtTrainAmount.setText(taskModel.getAmount());
        holder.mTxtTrainCoach.setText(taskModel.getCoachNo());
        holder.mTxtTrainSeat.setText(taskModel.getSeatNo());
        if(taskModel.isStatus()){
            holder.mImgTrainUpdate.setImageResource(R.drawable.ic_completed);
        }else {
            holder.mImgTrainUpdate.setImageResource(R.drawable.ic_update);
        }
    }

    private void debitFunction(MyViewHolder holder,TaskModel taskModel) {
        holder.mTxtDebitName.setText(taskModel.getName());
        holder.mTxtDebitAmount.setText("Rs."+taskModel.getAmount());
        holder.mTxtDebitMessage.setText(taskModel.getMessage());
        if(taskModel.isStatus()){
            holder.mImgDebitUpdate.setImageResource(R.drawable.ic_completed);
        }else {
            holder.mImgDebitUpdate.setImageResource(R.drawable.ic_update);
        }
    }

    private void creditFunction(MyViewHolder holder,TaskModel taskModel) {
        holder.mTxtCreditName.setText(taskModel.getName());
        holder.mTxtCreditAmount.setText("Rs."+taskModel.getAmount());
        holder.mTxtCreditMessage.setText(taskModel.getMessage());
        if(taskModel.isStatus()){
            holder.mImgCreditUpdate.setImageResource(R.drawable.ic_completed);
        }else {
            holder.mImgCreditUpdate.setImageResource(R.drawable.ic_update);
        }
    }

    @Override
    public int getItemCount() {
        return mTaskModelList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        public TextView mTxtCreditName;
        public TextView mTxtCreditAmount;
        public TextView mTxtCreditMessage;
        public ImageView mImgCreditUpdate;

        public TextView mTxtDebitName;
        public TextView mTxtDebitAmount;
        public TextView mTxtDebitMessage;
        public ImageView mImgDebitUpdate;

        public TextView mTxtTrainName;
        public TextView mTxtTrainNo;
        public TextView mTxtTrainBoarding;
        public TextView mTxtTrainAmount;
        public TextView mTxtTrainCoach;
        public TextView mTxtTrainSeat;
        public ImageView mImgTrainUpdate;

        public TextView mTxtBusName;
        public TextView mTxtBusNo;
        public TextView mTxtBusBoarding;
        public TextView mTxtBusAmount;
        public TextView mTxtBusCoach;
        public TextView mTxtBusSeat;
        public ImageView mImgBusUpdate;

        public RelativeLayout mLayCredit;
        public RelativeLayout mLayDebit;
        public RelativeLayout mLayTrain;
        public RelativeLayout mLayBus;
        public View mView;

        public MyViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            mTxtCreditName = (TextView)itemView.findViewById(R.id.txt_credit_name);
            mTxtCreditAmount = (TextView)itemView.findViewById(R.id.txt_credit_amount);
            mTxtCreditMessage = (TextView)itemView.findViewById(R.id.txt_credit_message);
            mImgCreditUpdate = (ImageView)itemView.findViewById(R.id.img_credit_status);

            mTxtDebitName = (TextView)itemView.findViewById(R.id.txt_debit_name);
            mTxtDebitAmount = (TextView)itemView.findViewById(R.id.txt_debit_amount);
            mTxtDebitMessage = (TextView)itemView.findViewById(R.id.txt_debit_message);
            mImgDebitUpdate = (ImageView)itemView.findViewById(R.id.img_debit_status);

            mTxtTrainNo = (TextView)itemView.findViewById(R.id.txt_train_details);
            mTxtTrainName = (TextView)itemView.findViewById(R.id.txt_train_name);
            mTxtTrainBoarding = (TextView)itemView.findViewById(R.id.txt_train_boarding);
            mTxtTrainAmount = (TextView)itemView.findViewById(R.id.txt_train_amount);
            mTxtTrainCoach = (TextView)itemView.findViewById(R.id.txt_train_coach);
            mTxtTrainSeat = (TextView)itemView.findViewById(R.id.txt_train_seatno);
            mImgTrainUpdate = (ImageView)itemView.findViewById(R.id.img_train_status);

            mTxtBusNo = (TextView)itemView.findViewById(R.id.txt_bus_details);
            mTxtBusName = (TextView)itemView.findViewById(R.id.txt_bus_name);
            mTxtBusBoarding = (TextView)itemView.findViewById(R.id.txt_bus_boarding);
            mTxtBusAmount = (TextView)itemView.findViewById(R.id.txt_bus_amount);
            mTxtBusCoach = (TextView)itemView.findViewById(R.id.txt_seat_type);
            mTxtBusSeat = (TextView)itemView.findViewById(R.id.txt_seatno);
            mImgBusUpdate = (ImageView)itemView.findViewById(R.id.img_bus_status);

            mLayCredit = (RelativeLayout)itemView.findViewById(R.id.lay_credit);
            mLayDebit = (RelativeLayout)itemView.findViewById(R.id.lay_debit);
            mLayTrain = (RelativeLayout)itemView.findViewById(R.id.lay_train);
            mLayBus = (RelativeLayout)itemView.findViewById(R.id.lay_bus);
        }
    }

    public void setVisibility(MyViewHolder holder,View view){
        holder.mLayCredit.setVisibility(View.GONE);
        holder.mLayDebit.setVisibility(View.GONE);
        holder.mLayTrain.setVisibility(View.GONE);
        holder.mLayBus.setVisibility(View.GONE);
        view.setVisibility(View.VISIBLE);
    }

    private void alertDialog(final Context context, final HomeViewModel homeViewModel,
                                   final int id, final boolean isCompleted){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
        alertDialog.setTitle("Update Task");
        if(!isCompleted){
            alertDialog.setMessage("Change it to InProgress?");
        }else{
            alertDialog.setMessage("Change it to Complete?");
        }
        alertDialog.setPositiveButton("Update", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                GrandHomeData grandHomeData = new GrandHomeData(context);
                grandHomeData.updateStatus(id,isCompleted);
                homeViewModel.refreshList();
                dialogInterface.dismiss();
            }
        });
        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        alertDialog.show();
    }
}
