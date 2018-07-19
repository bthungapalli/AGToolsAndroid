package com.agtools.agtools;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RadioButton;
import android.widget.RadioGroup;

/**
 * Created by soulsticesoftware on 09-02-2018.
 */

public class SegmentedRadioGroup extends RadioGroup{

    public SegmentedRadioGroup(Context context) {
        super(context);
    }

    public SegmentedRadioGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        changeButtonsImages();
    }

    private void changeButtonsImages(){
        int count = super.getChildCount();

        if(count > 1)
        {
            super.getChildAt(0).setBackgroundResource
                    (com.agtools.agtools.R.drawable.segment_radio_left);

            super.getChildAt(1).setBackgroundResource
                    (com.agtools.agtools.R.drawable.segment_radio_right);
        }
    }
}
