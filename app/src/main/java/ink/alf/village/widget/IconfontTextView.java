package ink.alf.village.widget;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

public class IconfontTextView extends AppCompatTextView {


    public IconfontTextView(Context context) {
        this(context, null);
    }

    public IconfontTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public IconfontTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        Typeface iconfont = Typeface.createFromAsset(getResources().getAssets(), "iconfont.ttf");
        this.setTypeface(iconfont);
    }
}
