package cn.lushantingyue.materialdesign_demo.main;

import android.support.annotation.NonNull;

import java.util.List;

import cn.lushantingyue.materialdesign_demo.base.BasePresenter;
import cn.lushantingyue.materialdesign_demo.base.BaseView;
import cn.lushantingyue.materialdesign_demo.bean.Articles;

/**
 * Created by Administrator on 2018/2/2 15.
 * Responsibilities: 契约类
 * Description:
 * ProjectName:
 */
public interface Contract {

    interface View extends BaseView<Presenter> {

        void showLoadingIndicator(boolean active);

        void addContent(List<Articles> tasks);

        void showAddTask();

        void showFooterLoading(Boolean loading);

        void showFooterEmpty(Boolean enable);

        void toastTips(int status);

        void clearContent();

    }

    interface Presenter extends BasePresenter {

        // ActivityResult
        void result(int requestCode, int resultCode);

        void refreshData(boolean refresh);

//        void openTaskDetails(@NonNull Task requestedTask);
//
//        void completeTask(@NonNull Task completedTask);
//
//        void activateTask(@NonNull Task activeTask);
//
//        void clearCompletedTasks();
//
//        void setFiltering(TasksFilterType requestType);
//
//        TasksFilterType getFiltering();
    }

}
