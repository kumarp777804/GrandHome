package com.buvanesh.grandhome.view;

import android.app.Dialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.buvanesh.grandhome.R;
import com.buvanesh.grandhome.adapters.PaymentSpinnerAdapter;
import com.buvanesh.grandhome.base.BaseActivity;
import com.buvanesh.grandhome.data.GrandHomeData;
import com.buvanesh.grandhome.model.ContactModel;
import com.buvanesh.grandhome.model.TaskModel;
import com.buvanesh.grandhome.utils.Utilities;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import static com.buvanesh.grandhome.utils.Utilities.getBitmapFromView;
import static com.buvanesh.grandhome.utils.Utilities.shareView;

public class PaymentDetailActivity extends BaseActivity {

    public String Name = null;
    LinearLayout mLayPayment;
    LinearLayout mLayCredit;
    TextView txtName;
    TextView txtPhone;
    TextView txtTotal;
    public LinearLayout mLayMain;
    View viewDebit;
    View viewCredit;

    @Override
    protected int getLayout() {
        return R.layout.activity_payment_detail;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        customDialog(PaymentDetailActivity.this);
    }

    private void initView() {
        mLayPayment = (LinearLayout) findViewById(R.id.lay_payment_details);
        mLayCredit = findViewById(R.id.lay_payment_credits);
        mLayMain = findViewById(R.id.lay_payment_main);
        txtName = findViewById(R.id.txt_payment_name);
        txtPhone = findViewById(R.id.txt_payment_phone);
        txtTotal = findViewById(R.id.txt_payment_total);
        viewDebit = findViewById(R.id.lay_payment_debit);
        viewCredit = findViewById(R.id.lay_payment_credit);
        ImageView  imgShare = findViewById(R.id.img_share);
        imgShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getScreenShot();
            }
        });
    }

    public void customDialog(final Context context) {
        final Dialog myDialog = new Dialog(context);
        myDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        myDialog.setCancelable(false);
        myDialog.setContentView(R.layout.item_dialog_details);
        final DetailsViewHolder viewHolder = new DetailsViewHolder();
        viewHolder.mSpinnerTask = (Spinner)myDialog.findViewById(R.id.spin_name_list);
        viewHolder.mImgSearch = (ImageView)myDialog.findViewById(R.id.img_search);
        viewHolder.mImgClose = (ImageView)myDialog.findViewById(R.id.img_detail_close);
        final List<ContactModel> contactModels = new Utilities().getContactDetails(PaymentDetailActivity.this);
        if(contactModels.size()>0){
            PaymentSpinnerAdapter adapter = new PaymentSpinnerAdapter(PaymentDetailActivity.this, R.layout.item_contacts,
                    contactModels);
            viewHolder.mSpinnerTask.setAdapter(adapter);
        }
        viewHolder.mImgSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(viewHolder.mSpinnerTask!=null&&viewHolder.mSpinnerTask.getChildCount()>0) {
                    myDialog.dismiss();
                    Name = contactModels.get(viewHolder.mSpinnerTask.getSelectedItemPosition()).getName();
                    getData(Name);
                }else{
                    myDialog.dismiss();
                    Name = "MySelf";
                    getData(Name);
                }
            }
        });

        viewHolder.mImgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myDialog.dismiss();
                finish();
            }
        });
        myDialog.show();
        Window window = myDialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
    }

    static class DetailsViewHolder {
        Spinner mSpinnerTask;
        ImageView mImgSearch;
        ImageView mImgClose;
    }
    public void getData(String name){
        TextView txtComment = null;
        TextView txtDate = null;
        TextView txtAmount = null;
        LayoutInflater layoutInflater = (LayoutInflater)this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = null;
        GrandHomeData grandHomeData = new GrandHomeData(PaymentDetailActivity.this);
        List<TaskModel> arrayList = grandHomeData.getTaskDetails();
        int TotalAmount = 0;
        for(TaskModel taskModel : getData(true,Name,arrayList)){
                viewCredit.setVisibility(View.VISIBLE);
                view = getView(view,layoutInflater);
                txtComment = view.findViewById(R.id.txt_item_comment);
                txtDate = view.findViewById(R.id.txt_item_date);
                txtAmount = view.findViewById(R.id.txt_item_amount);
                txtComment.setText(taskModel.getMessage());
                txtDate.setText("On "+taskModel.getDate().replace("-"," ").replace("Y",""));
                txtAmount.setText(taskModel.getAmount());
                mLayCredit.addView(view);
                TotalAmount = TotalAmount - Integer.parseInt(taskModel.getAmount());
        }
        for(TaskModel taskModel : getData(false,Name,arrayList)){
            viewDebit.setVisibility(View.VISIBLE);
            txtName.setText(taskModel.getName());
            txtPhone.setText(taskModel.getNumber());
            view = getView(view,layoutInflater);
            txtComment = view.findViewById(R.id.txt_item_comment);
            txtDate = view.findViewById(R.id.txt_item_date);
            txtAmount = view.findViewById(R.id.txt_item_amount);
            String message = null;
            if(!taskModel.isBanking()){
                message = taskModel.getTrainNo()+" "+taskModel.getMessage();
            }else{
                message = taskModel.getMessage();
            }
            txtComment.setText(message);
            txtDate.setText("On "+taskModel.getDate().replace("-"," ").replace("Y",""));
            txtAmount.setText(taskModel.getAmount());
            mLayPayment.addView(view);
            TotalAmount = TotalAmount + Integer.parseInt(taskModel.getAmount());
        }
        txtTotal.setText("Rs: "+TotalAmount);
    }

    private View getView(View view, LayoutInflater layoutInflater) {
        view = layoutInflater.inflate(R.layout.item_payment_details,null,false);
        return view;
    }

    private void getScreenShot(){
        shareView(PaymentDetailActivity.this,mLayMain,Name);
    }

    public List<TaskModel> getData(boolean isCredit,String name,List<TaskModel> taskModel){
        List<TaskModel> taskModelList = new ArrayList<>();
        if(isCredit){
        for(TaskModel model : taskModel){
            if(name.equals(model.getName())) {
                if (model.isCredit()) {
                    taskModelList.add(model);
                }
            }
        }
        }else{
            for(TaskModel model : taskModel){
                if(name.equals(model.getName())) {
                    if (!model.isCredit()) {
                        taskModelList.add(model);
                    }
                }
            }
        }
        return taskModelList;
    }
}
