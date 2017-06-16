package com.app.football.main;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.app.football.R;


/**
 * Created by Administrator on 2017/4/6.
 *
 */

public class LoginChooseFragment extends DialogFragment {

    private View mView;
    private ChooseListener mChooseListener;

    interface ChooseListener {
        void useQQ(DialogFragment dialog);
        void useWeiBo(DialogFragment dialog);
        void useSoHu(DialogFragment dialog);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = getLayoutInflater(null);
        mView = inflater.inflate(R.layout.dialog_log_in_choose, null);
        ImageView qq = (ImageView) mView.findViewById(R.id.qq_method);
        ImageView weibo = (ImageView) mView.findViewById(R.id.weibo_method);
        ImageView sohu = (ImageView) mView.findViewById(R.id.sohu_method);
        MyListener listener = new MyListener();
        qq.setOnClickListener(listener);
        weibo.setOnClickListener(listener);
        sohu.setOnClickListener(listener);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ChooseListener) {
            mChooseListener = (ChooseListener) context;
        } else {
            // 必须实现ChooseListener接口
            throw new RuntimeException();
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("使用第三方账号登录")
                .setView(mView);
        return builder.create();
    }

    private class MyListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.qq_method:
                    mChooseListener.useQQ(LoginChooseFragment.this);
                    break;
                case R.id.weibo_method:
                    mChooseListener.useWeiBo(LoginChooseFragment.this);
                    break;
                case R.id.sohu_method:
                    mChooseListener.useSoHu(LoginChooseFragment.this);
                    break;
                default:
                    break;
            }
        }
    }

}
