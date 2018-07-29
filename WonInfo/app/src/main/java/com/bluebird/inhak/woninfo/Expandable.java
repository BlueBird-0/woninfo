package com.bluebird.inhak.woninfo;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.github.chuross.library.ExpandableLayout;

/**
 * Created by InHak on 2018-02-06.
 */

public class Expandable {
    ExpandableLayout expandableLayout;
    RelativeLayout relativeLayout;
    ImageView imageView;
    boolean open;
    Animation animation;

    public Expandable(final View view, int expandableLayout_id, int relativeLayout_id, int imageView_id)
    {
        this.expandableLayout = view.findViewById(expandableLayout_id);
        this.relativeLayout = view.findViewById(relativeLayout_id);
        this.imageView = view.findViewById(imageView_id);
        open = false;

        relativeLayout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(open)
                {
                    animation = AnimationUtils.loadAnimation(view.getContext(), R.anim.rotate_c);
                }else {
                    animation = AnimationUtils.loadAnimation(view.getContext(), R.anim.rotate_o);
                }
                open = !open;
                imageView.startAnimation(animation);
                expandableLayout.toggle();
            }
        });
    }

}
