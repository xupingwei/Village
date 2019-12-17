package ink.alf.village.widget;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatTextView;

/**
 * @author 13793
 */
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
        Typeface iconfont = Typeface.createFromAsset(getResources().getAssets(), "iconfont/iconfont.ttf");
        this.setTypeface(iconfont);
    }
}
