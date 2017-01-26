package yss.com.myrongappication.view;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;


import yss.com.myrongappication.R;
import yss.com.myrongappication.util.KeyBoardUtil;

/**
 * Created by Administrator on 2016/4/8.
 */
public class WithEditTextDialog extends Dialog {
    private Context context;
    private String title;
    private String leftoption;
    private String rightoption;
    private ClickListenerInterface clickListenerInterface;
    private String editContent;
    private View view;

    public EditText getDialogContentET() {
        return dialogContentET;
    }

    public void setDialogContentET(EditText dialogContentET) {
        this.dialogContentET = dialogContentET;
    }

    private EditText dialogContentET;

    public interface ClickListenerInterface {

        public void doConfirm();

        public void doCancel();
    }

    public WithEditTextDialog(Context context, String title, String leftoption, String rightoption) {
        super(context, R.style.Theme_dialog);
        this.context = context;
        this.title = title;
        this.leftoption = leftoption;
        this.rightoption = rightoption;
        init();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);

    }

    public View getView() {
        return view;
    }

    public String getEditContent() {
        return editContent;
    }

    public void init() {
        LayoutInflater inflater = LayoutInflater.from(context);
        view = inflater.inflate(R.layout.edittextdialog, null);
        getWindow().setContentView(view);
        view.findFocus();
        TextView dialogTitleTV = (TextView) view.findViewById(R.id.dialogTitleTV);
        LinearLayout leftoptionDialogLL = (LinearLayout) view.findViewById(R.id.leftoptionDialogLL);
        LinearLayout rightoptionDialogLL = (LinearLayout) view.findViewById(R.id.rightoptionDialogLL);
        TextView leftoptionDialogTV = (TextView) view.findViewById(R.id.leftoptionDialogTV);
        TextView rightoptionDialogTV = (TextView) view.findViewById(R.id.rightoptionDialogTV);
        dialogContentET = (EditText) view.findViewById(R.id.dialogContentET);

        dialogTitleTV.setText(title);
        leftoptionDialogTV.setText(leftoption);
        rightoptionDialogTV.setText(rightoption);

        leftoptionDialogLL.setOnClickListener(new clickListener());
        rightoptionDialogLL.setOnClickListener(new clickListener());

        Window dialogWindow = getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        DisplayMetrics d = context.getResources().getDisplayMetrics(); // 获取屏幕宽、高用
        lp.width = (int) (d.widthPixels * 0.8); // 高度设置为屏幕的0.6
        dialogWindow.setAttributes(lp);
    }

    public void setClicklistener(ClickListenerInterface clickListenerInterface) {
        this.clickListenerInterface = clickListenerInterface;
    }

    private class clickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            int id = v.getId();
            switch (id) {
                case R.id.leftoptionDialogLL:
                    clickListenerInterface.doConfirm();
                    break;
                case R.id.rightoptionDialogLL:
                    clickListenerInterface.doCancel();
                    break;
            }
        }

    }

    @Override
    public void show() {
        super.show();
        KeyBoardUtil.dismissKeyBoard((Activity)context);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        InputMethodManager imm = (InputMethodManager)
                context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(view, 0); //显示软键盘
        imm.toggleSoftInput(0, InputMethodManager.SHOW_FORCED); //显示软键盘
    }
}
