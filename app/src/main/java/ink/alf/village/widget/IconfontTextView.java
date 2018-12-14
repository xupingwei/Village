package ink.alf.village.widget;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

public class IconfontTextView extends AppCompatTextView {


    public IconfontTextView(Context context) {
        super(context);
    }

    public IconfontTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public IconfontTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void setTypeface(Typeface tf) {
        super.setTypeface(Typeface.createFromAsset(getResources().getAssets(), "iconfont.ttf"));
    }
}
