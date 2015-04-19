package net.learn2develop.BasicViews1;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

/**
 * Created by joshua on 2015-03-19.
 */
public class test extends ScrollView {
    public test(Context context,AttributeSet as)
    {
        super(context,as);
    }
    public test(Context context)
    {
        super(context);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(200, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

}
