package com.br.bnsantos.login.example.components;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.os.Build;
import android.view.View;
import android.widget.LinearLayout;

/**
 * Created with IntelliJ IDEA.
 * User: bruno
 * Date: 10/2/13
 * Time: 2:14 PM
 * To change this template use File | Settings | File Templates.
 */
public class ProgressSpinner {

    private LinearLayout wrapedLayout;
    private LinearLayout toBeHiddenLayout;
    private LinearLayout toBeHiddenLayout2;
    private int shortAnimTime;

    public ProgressSpinner(LinearLayout wrapedLayout, LinearLayout toBeHiddenLayout, int shortAnimTime) {
        this.wrapedLayout = wrapedLayout;
        this.toBeHiddenLayout = toBeHiddenLayout;
        this.toBeHiddenLayout2 = toBeHiddenLayout;
        this.shortAnimTime = shortAnimTime;
    }

    @Deprecated
    public ProgressSpinner(LinearLayout wrapedLayout, LinearLayout toBeHiddenLayout1, LinearLayout toBeHiddenLayout2, int shortAnimTime) {
        this.wrapedLayout = wrapedLayout;
        this.toBeHiddenLayout = toBeHiddenLayout1;
        this.toBeHiddenLayout2 = toBeHiddenLayout2;
        this.shortAnimTime = shortAnimTime;
    }

    public void show(final boolean show) {

        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {

            // TODO refatorar, já que o código é o mesmo e muda só o parâmetro! Em objetos diferentes
            wrapedLayout.setVisibility(View.VISIBLE);
            wrapedLayout.animate().setDuration(shortAnimTime).alpha(show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    wrapedLayout.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });

            toBeHiddenLayout.setVisibility(View.GONE);
            toBeHiddenLayout2.setVisibility(View.GONE);
            toBeHiddenLayout.animate().setDuration(shortAnimTime).alpha(show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    toBeHiddenLayout.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });
            toBeHiddenLayout2.animate().setDuration(shortAnimTime).alpha(show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    toBeHiddenLayout2.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            wrapedLayout.setVisibility(show ? View.VISIBLE : View.GONE);
            toBeHiddenLayout.setVisibility(show ? View.GONE : View.VISIBLE);
            toBeHiddenLayout2.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }
}
