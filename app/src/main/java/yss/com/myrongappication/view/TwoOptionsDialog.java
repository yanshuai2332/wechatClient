package yss.com.myrongappication.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import yss.com.myrongappication.R;


/**
 * Created by Administrator on 2016/4/8.
 */
public class TwoOptionsDialog extends Dialog {
    private Context context;
    private String title;
    private String leftoption;
    private String rightoption;
    private ClickListenerInterface clickListenerInterface;

    public interface ClickListenerInterface {

        public void doConfirm();

        public void doCancel();
    }

    public TwoOptionsDialog(Context context, String title) {
       this(context,title,"确定","取消");
    }

    public TwoOptionsDialog(Context context, String title, String leftoption, String rightoption) {
        super(context, R.style.Theme_dialog);
        this.context = context;
        this.title=title;
        this.leftoption=leftoption;
        this.rightoption=rightoption;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);

        init();
    }

    public void init() {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.myalertdialog, null);
        setContentView(view);

        TextView dialogTitleTV = (TextView) view.findViewById(R.id.dialogTitleTV);
        LinearLayout leftoptionDialogLL= (LinearLayout) view.findViewById(R.id.leftoptionDialogLL);
        LinearLayout rightoptionDialogLL= (LinearLayout) view.findViewById(R.id.rightoptionDialogLL);
        TextView leftoptionDialogTV= (TextView) view.findViewById(R.id.leftoptionDialogTV);
        TextView rightoptionDialogTV= (TextView) view.findViewById(R.id.rightoptionDialogTV);
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

    ;

}
