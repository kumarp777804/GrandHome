package com.buvanesh.grandhome.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.buvanesh.grandhome.R;
import com.buvanesh.grandhome.adapters.CustomTaskAdapter;
import com.buvanesh.grandhome.base.BaseActivity;
import com.buvanesh.grandhome.customview.CustomFloatingButton;
import com.buvanesh.grandhome.model.TaskModel;
import com.buvanesh.grandhome.utils.CustomDialog;
import com.buvanesh.grandhome.viewmodel.HomeView;
import com.buvanesh.grandhome.viewmodel.HomeViewModel;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends BaseActivity
        implements  HomeView ,HomeViewModel.HomePresenter, AdapterView.OnItemSelectedListener{

    private HomeViewModel homeViewModel;
    private ProgressBar homeProgress;
    private CustomFloatingButton fab;
    private RecyclerView mRecyclerTaskPlanner;
    private CustomTaskAdapter mCustomTaskAdapter;
    private TextView mTxtNoTask;
    private LinearLayout mLayDetails;
    private Spinner mSpinnerFilter;
    private static int CREDIT_VALUE = 0;
    private static int DEBIT_VALUE = 0;
    private TextView mTxtCredit;
    private TextView mTxtDebit;
    private static List<TaskModel> mTaskModel = null;

    @Override
    protected int getLayout() {
        return R.layout.activity_home;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        intiView();
        homeViewModel = new HomeViewModel(HomeActivity.this,this,this);
    }

    private void intiView() {
        homeProgress = (ProgressBar) findViewById(R.id.home_progress);
        mTxtNoTask = (TextView) findViewById(R.id.txt_notask);
        fab = (CustomFloatingButton) findViewById(R.id.fab);
        mRecyclerTaskPlanner = (RecyclerView) findViewById(R.id.recyler_task_list);
        mLayDetails = (LinearLayout) findViewById(R.id.lay_home_details);
        mSpinnerFilter = (Spinner) findViewById(R.id.spin_filter);
        mTxtCredit = (TextView) findViewById(R.id.txt_filter_credit);
        mTxtDebit = (TextView) findViewById(R.id.txt_filter_debit);

        mSpinnerFilter.setOnItemSelectedListener(this);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new CustomDialog().customDialog(HomeActivity.this,homeViewModel);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        homeViewModel.refreshList();
    }

    @Override
    public void showProgress() {
        homeProgress.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        homeProgress.setVisibility(View.GONE);
    }

    @Override
    public void updatedSuccessfully(List<TaskModel> taskModelList) {
        mTaskModel = taskModelList;
        mTxtNoTask.setVisibility(View.GONE);
        mLayDetails.setVisibility(View.VISIBLE);
        setCreditDebitValue(taskModelList);
        setAdapter(taskModelList);
    }

    private void setCreditDebitValue(List<TaskModel> taskModelList) {
        CREDIT_VALUE =0;
        DEBIT_VALUE =0;
        for(TaskModel taskModel: taskModelList){
            if(taskModel.getAmount()!=null) {
                if (taskModel.isBanking()) {
                    if (taskModel.isCredit()) {
                        CREDIT_VALUE = CREDIT_VALUE + Integer.parseInt(taskModel.getAmount());
                    }else {
                        DEBIT_VALUE = DEBIT_VALUE + Integer.parseInt(taskModel.getAmount());
                    }
                }
            }
        }
        mTxtCredit.setText("Credit Rs."+CREDIT_VALUE);
        mTxtDebit.setText("Debit Rs."+DEBIT_VALUE);
    }

    @Override
    public void updationFailed() {
        mTxtNoTask.setVisibility(View.VISIBLE);
        mLayDetails.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
        if(mCustomTaskAdapter!=null){
            switch (position){
                case 0:
                    setAdapter(mTaskModel);
                    break;
                case 1:
                    if(getPendingData().size()>0) {
                        setAdapter(getPendingData());
                    }else{
                        showToast();
                    }
                    break;
                case 2:
                    if(getCreditData().size()>0) {
                        setAdapter(getCreditData());
                    }else{
                        showToast();
                    }
                    break;
                case 3:
                    if(getDebitData().size()>0) {
                        setAdapter(getDebitData());
                    }else{
                        showToast();
                    }
                    break;
                case 4:
                    if(getTrainData().size()>0) {
                        setAdapter(getTrainData());
                    }else{
                        showToast();
                    }
                    break;
                case 5:
                    if(getBusData().size()>0) {
                        setAdapter(getBusData());
                    }else{
                        showToast();
                    }
                    break;
                case 6:
                    if(getCompletedData().size()>0) {
                        setAdapter(getCompletedData());
                    }else{
                        showToast();
                    }
                    break;
                case 7:
                    startActivity(new Intent(HomeActivity.this,PaymentDetailActivity.class));
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public List<TaskModel> getPendingData(){
        List<TaskModel> taskModelList = new ArrayList<>();
        for(TaskModel taskModel : mTaskModel){
            if(!taskModel.isStatus()){
                taskModelList.add(taskModel);
            }
        }
        return taskModelList;
    }

    public List<TaskModel> getCreditData(){
        List<TaskModel> taskModelList = new ArrayList<>();
        for(TaskModel taskModel : mTaskModel){
            if(taskModel.isBanking()) {
                if (taskModel.isCredit()) {
                    taskModelList.add(taskModel);
                }
            }
        }
        return taskModelList;
    }

    public List<TaskModel> getDebitData(){
        List<TaskModel> taskModelList = new ArrayList<>();
        for(TaskModel taskModel : mTaskModel){
            if(taskModel.isBanking()) {
                if (!taskModel.isCredit()) {
                    taskModelList.add(taskModel);
                }
            }
        }
        return taskModelList;
    }

    public List<TaskModel> getTrainData(){
        List<TaskModel> taskModelList = new ArrayList<>();
        for(TaskModel taskModel : mTaskModel){
            if(!taskModel.isBanking()) {
                if (taskModel.isTrain()) {
                    taskModelList.add(taskModel);
                }
            }
        }
        return taskModelList;
    }

    public List<TaskModel> getBusData(){
        List<TaskModel> taskModelList = new ArrayList<>();
        for(TaskModel taskModel : mTaskModel){
            if(!taskModel.isBanking()) {
                if (!taskModel.isTrain()) {
                    taskModelList.add(taskModel);
                }
            }
        }
        return taskModelList;
    }

    public List<TaskModel> getCompletedData(){
        List<TaskModel> taskModelList = new ArrayList<>();
        for(TaskModel taskModel : mTaskModel){
            if(taskModel.isStatus()){
                taskModelList.add(taskModel);
            }
        }
        return taskModelList;
    }

    public void setAdapter(List<TaskModel> taskModelList) {
        mCustomTaskAdapter = new CustomTaskAdapter(HomeActivity.this,taskModelList,homeViewModel);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        mRecyclerTaskPlanner.setLayoutManager(mLayoutManager);
        mRecyclerTaskPlanner.setItemAnimator(new DefaultItemAnimator());
        mRecyclerTaskPlanner.setAdapter(mCustomTaskAdapter);
    }

    public void showToast(){
        Toast.makeText(HomeActivity.this,"No Matches Found",Toast.LENGTH_SHORT).show();
    }


}
