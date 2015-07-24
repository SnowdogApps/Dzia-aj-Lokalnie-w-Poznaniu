package pl.snowdog.dzialajlokalnie.util;

import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;

/**
 * Created by bartek on 17.07.15.
 */
public class FadeOutAnimation extends AlphaAnimation {
    private final View view;

    public FadeOutAnimation(View view) {
        super(1.0f, 0.0f);

        this.view = view;

        setDuration(300);
        setAnimationListener(new AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                FadeOutAnimation.this.view.setVisibility(View.INVISIBLE);
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
