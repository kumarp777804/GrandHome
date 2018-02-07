package com.buvanesh.grandhome.viewmodel;

import android.content.Context;
import android.text.TextUtils;

import com.buvanesh.grandhome.data.GrandHomeData;
import com.buvanesh.grandhome.model.TaskModel;

import java.util.List;

import static com.buvanesh.grandhome.utils.CustomDialog.INDIVIDUAL_NAME;

/**
 * Created by buvaneshkumar_p on 10/26/2017.
 */

public class HomeViewModel {
    private Context mContext;
    private HomeView mHomeView;
    private HomePresenter mListener;

    public interface HomePresenter{
        void updatedSuccessfully(List<TaskModel> taskModelList);
        void updationFailed();
    }
    public HomeViewModel(Context context, HomeView homeView, HomePresenter listener){
        this.mContext =context;
        this.mHomeView = homeView;
        this.mListener = listener;
    }

    public void refreshList(){
        mHomeView.showProgress();
        getData();
    }

    private void getData() {
        GrandHomeData grandHomeData = new GrandHomeData(mContext);
        List<TaskModel> arrayList = grandHomeData.getTaskDetails();
        if(arrayList.size()>0) {
            mListener.updatedSuccessfully(arrayList);
            mHomeView.hideProgress();
        }else{
            mHomeView.hideProgress();
            mListener.updationFailed();
        }
    }



}
