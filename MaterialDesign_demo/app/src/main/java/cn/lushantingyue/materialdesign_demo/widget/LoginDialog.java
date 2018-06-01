package cn.lushantingyue.materialdesign_demo.widget;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import cn.lushantingyue.materialdesign_demo.R;

/**
 * Created by Administrator on 2018/6/1 13.
 * Responsibilities:
 * Description: 登陆弹窗
 * ProjectName:
 * Why: 谷歌建议使用DialogFragment来管理对话框，当旋转屏幕和按下后退键时可以更好的管理其声明周期，它和Fragment有着基本一致的声明周期。
 * 且DialogFragment也允许开发者把AlertDialog作为内嵌的组件进行重用，类似Fragment
 * （DialogFragment可以在大屏幕和小屏幕显示出不同的效果，屏幕旋转相关数据不会被销毁）
 */
public class LoginDialog extends DialogFragment implements View.OnClickListener {

    public static LoginDialog newInstance() {
        LoginDialog f = new LoginDialog();
        Bundle args = new Bundle();
        f.setArguments(args);

        return f;
    }

    public LoginDialog() {
        super();
    }

    private EditText usrname, password;
    private Button btn_login, btn_register;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int style = DialogFragment.STYLE_NO_TITLE;
        int theme = android.R.style.Theme_Holo_Light_Dialog;

        setStyle(style, theme);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.login_fragment_dialog, container, false);
        usrname = (EditText) v.findViewById(R.id.edit_username);
        password = (EditText) v.findViewById(R.id.edit_password);
        btn_login = (Button)v.findViewById(R.id.btn_login);
        btn_register = (Button) v.findViewById(R.id.btn_register);

        btn_login.setOnClickListener(this);
        btn_register.setOnClickListener(this);

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onClick(View view) {
        FragmentActivity activity = getActivity();
        if (activity instanceof LoginInterface) {
            String usr = usrname.getText().toString();
            String psw = password.getText().toString();

            LoginInterface call = (LoginInterface) activity;
            call.btn_click(view.getId(), usr, psw);
        }
    }
}
