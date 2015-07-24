package pl.snowdog.dzialajlokalnie.util;

import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;

/**
 * Created by bartek on 17.07.15.
 */
public class FadeInAnimation extends AlphaAnimation {
    private final View view;

    public FadeInAnimation(View view) {
        super(0.0f, 1.0f);

        this.view = view;

        setDuration(300);
        setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                FadeInAnimation.this.view.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
    }

    public void startAnimation() {
        view.startAnimation(this);
    }
}
