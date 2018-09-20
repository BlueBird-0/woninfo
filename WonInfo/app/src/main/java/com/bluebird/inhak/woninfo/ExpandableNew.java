package com.bluebird.inhak.woninfo;

import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.github.chuross.library.ExpandableLayout;

/**
 * Created by InHak on 2018-02-06.
 */

public class ExpandableNew {
    ExpandableLayout expandableLayout;
    ConstraintLayout constraintLayout;
    boolean open;
    //Animation animation;

    public ExpandableNew(final View view, int expandableLayout_id, int constraintLayout_id)
    {
        this.expandableLayout = view.findViewById(expandableLayout_id);
        this.constraintLayout = view.findViewById(constraintLayout_id);
        open = false;

        constraintLayout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //if(open)
                //{
                    //animation = AnimationUtils.loadAnimation(view.getContext(), R.anim.rotate_c);
                //}else {
                    //animation = AnimationUtils.loadAnimation(view.getContext(), R.anim.rotate_o);
                //}
                open = !open;
                //imageView.startAnimation(animation);
                expandableLayout.toggle();
            }
        });
    }

}
