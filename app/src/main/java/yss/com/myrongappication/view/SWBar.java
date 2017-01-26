package yss.com.myrongappication.view;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import yss.com.myrongappication.R;
import yss.com.myrongappication.util.StringUtil;


/**
 * 用于商维管理的toolbar
 * Created by hliman on 2016/3/25 0025.
 */
public class SWBar extends RelativeLayout {
    private String title;//标题
    private Context context;
    private ImageView backIv;//返回的imageView;
    private TextView titleTv;//标题
    private int titleColor;//标题颜色
    private int bacKImage;//返回图标


    public SWBar(Context context) {
        this(context, null);
    }

    public SWBar(final Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        View view = LayoutInflater.from(context).inflate(R.layout.sw_bar, this, true);
        backIv = (ImageView) view.findViewById(R.id.customBackIv);
        backIv.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (context instanceof Activity) {
                    ((Activity) context).finish();
                }
            }
        });
        titleTv = (TextView) view.findViewById(R.id.customTitle);
        initAttr(attrs);
    }

    /**
     * 初始化属性
     */
    private void initAttr(AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.SWBar);
        CharSequence title = typedArray.getText(R.styleable.SWBar_barTitle);
        if (StringUtil.isNotEmpty(title))
            titleTv.setText(title);
        int backView = typedArray.getInt(R.styleable.SWBar_backView, -1);
        if (backView != -1)
            backIv.setImageResource(backView);
        int defaultTitleColor = Color.parseColor("#FFFFFF");
        int titleColor = typedArray.getColor(R.styleable.SWBar_titleColor, defaultTitleColor);
        titleTv.setTextColor(titleColor);
        setBackgroundColor(getResources().getColor(R.color.colorPrimary));
    }


    public int getBacKImage() {
        return bacKImage;
    }

    public void setBacKImage(int bacKImage) {
        this.bacKImage = bacKImage;
        backIv.setImageResource(bacKImage);
    }

    public int getTitleColor() {
        return titleColor;
    }

    public void setTitleColor(int titleColor) {
        this.titleColor = titleColor;
        titleTv.setTextColor(titleColor);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
        titleTv.setText(StringUtil.isEmpty(title) ? "" : title);
    }

    public void dismissBackView() {
        backIv.setVisibility(View.GONE);
    }

    public void showBackView() {
        backIv.setVisibility(View.VISIBLE);
    }

}
