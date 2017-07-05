package com.orbis.materiallikeview;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;

/**
 * Created by Carlos Leonardo Camilo Vargas Huamán on 7/4/17.
 */

public class MaterialLikeView extends FrameLayout implements View.OnClickListener {


    /**
     * Default state value for the view
     */
    private static final boolean DEFAULT_FAVORITE_STATE_VALUE = false;

    private ImageView ivStar;
    private CircleView circleView;
    private DotsView dotsView;

    private int circleViewColor;
    private int favoriteIcon;
    private int notFavoriteIcon;

    private int dotsViewColor1;
    private int dotsViewColor2;
    private int dotsViewColor3;
    private int dotsViewColor4;

    private int itemPosition = -1;

    private boolean favoriteState;

    private AnimatorSet likeAnimatorSet;
    private AnimatorSet unlikeAnimatorSet;

    private static final DecelerateInterpolator DECCELERATE_INTERPOLATOR = new DecelerateInterpolator();
    private static final AccelerateDecelerateInterpolator ACCELERATE_DECELERATE_INTERPOLATOR = new AccelerateDecelerateInterpolator();
    private static final OvershootInterpolator OVERSHOOT_INTERPOLATOR = new OvershootInterpolator(4);

    private OnMaterialLikeAnimationListener onMaterialLikeAnimationListener;
    private OnMaterialLikeItemClickListener onMaterialLikeItemClickListener;

    public void setOnMaterialLikeClickListener(OnMaterialLikeAnimationListener onMaterialLikeAnimationListener) {
        this.onMaterialLikeAnimationListener = onMaterialLikeAnimationListener;
    }

    public void setOnMaterialLikeItemClickListener(OnMaterialLikeItemClickListener onMaterialLikeItemClickListener) {
        this.onMaterialLikeItemClickListener = onMaterialLikeItemClickListener;
    }

    public MaterialLikeView(@NonNull Context context) {
        super(context);
    }

    public MaterialLikeView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initStyle(attrs, 0, 0);
        initView();
    }

    public MaterialLikeView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initStyle(attrs, defStyleAttr, 0);
        initView();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public MaterialLikeView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initStyle(attrs, defStyleAttr, defStyleRes);
    }

    private void initStyle(AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        final TypedArray a = getContext().obtainStyledAttributes(
                attrs, R.styleable.MaterialLikeView, defStyleAttr, defStyleRes);

        circleViewColor = a.getColor(
                R.styleable.MaterialLikeView_mlvCircleViewColor, ContextCompat.getColor(this.getContext(), R.color.md_red_500));

        favoriteIcon = a.getResourceId(
                R.styleable.MaterialLikeView_mlvFavoriteIcon, R.drawable.ic_favorite_red_500_48dp);

        notFavoriteIcon = a.getResourceId(
                R.styleable.MaterialLikeView_mlvNotFavoriteIcon, R.drawable.ic_favorite_border_grey_700_48dp);

        dotsViewColor1 = a.getColor(
                R.styleable.MaterialLikeView_mlvDotsViewColor1, ContextCompat.getColor(this.getContext(), R.color.md_red_500));

        dotsViewColor2 = a.getColor(
                R.styleable.MaterialLikeView_mlvDotsViewColor1, ContextCompat.getColor(this.getContext(), R.color.md_red_600));
        dotsViewColor3 = a.getColor(
                R.styleable.MaterialLikeView_mlvDotsViewColor1, ContextCompat.getColor(this.getContext(), R.color.md_red_700));
        dotsViewColor4 = a.getColor(
                R.styleable.MaterialLikeView_mlvDotsViewColor1, ContextCompat.getColor(this.getContext(), R.color.md_red_900));
        favoriteState = a.getBoolean(
                R.styleable.MaterialLikeView_mlvFavoriteState, DEFAULT_FAVORITE_STATE_VALUE);

        a.recycle();
    }

    private void initView() {
        FrameLayout frameLayout = (FrameLayout) LayoutInflater.from(getContext()).inflate(R.layout.view_materiallikeview, this, true);
        circleView = frameLayout.findViewById(R.id.vCircle);
        ivStar = frameLayout.findViewById(R.id.ivStar);
        dotsView = frameLayout.findViewById(R.id.vDotsView);

        if(favoriteState){
            ivStar.setImageResource(favoriteIcon);
        }else{
            ivStar.setImageResource(notFavoriteIcon);
        }

        circleView.setColorCirclePaint(circleViewColor);
        dotsView.setCurrentColors(dotsViewColor1, dotsViewColor2, dotsViewColor3, dotsViewColor4);

        likeAnimatorSet = new AnimatorSet();
        unlikeAnimatorSet = new AnimatorSet();

        frameLayout.setOnClickListener(this);
    }

    @SuppressWarnings("SameParameterValue")
    public void startLikeAnimation() {

        if (!isLikeAnimationRunning()) {
            ivStar.setImageResource(favoriteIcon);

            likeAnimatorSet.cancel();
            ivStar.animate().cancel();
            ivStar.setScaleX(0);
            ivStar.setScaleY(0);
            circleView.setInnerCircleRadiusProgress(0);
            circleView.setOuterCircleRadiusProgress(0);
            dotsView.setCurrentProgress(0);

            ObjectAnimator outerCircleAnimator = ObjectAnimator.ofFloat(circleView, CircleView.OUTER_CIRCLE_RADIUS_PROGRESS, 0.1f, 1f);
            outerCircleAnimator.setDuration(200);
            outerCircleAnimator.setInterpolator(DECCELERATE_INTERPOLATOR);

            ObjectAnimator innerCircleAnimator = ObjectAnimator.ofFloat(circleView, CircleView.INNER_CIRCLE_RADIUS_PROGRESS, 0.1f, 1f);
            innerCircleAnimator.setDuration(150);
            innerCircleAnimator.setStartDelay(150);
            innerCircleAnimator.setInterpolator(DECCELERATE_INTERPOLATOR);

            ObjectAnimator starScaleYAnimator = ObjectAnimator.ofFloat(ivStar, ImageView.SCALE_Y, 0.2f, 1f);
            starScaleYAnimator.setDuration(200);
            starScaleYAnimator.setStartDelay(200);
            starScaleYAnimator.setInterpolator(OVERSHOOT_INTERPOLATOR);

            ObjectAnimator starScaleXAnimator = ObjectAnimator.ofFloat(ivStar, ImageView.SCALE_X, 0.2f, 1f);
            starScaleXAnimator.setDuration(200);
            starScaleXAnimator.setStartDelay(200);
            starScaleXAnimator.setInterpolator(OVERSHOOT_INTERPOLATOR);

            ObjectAnimator dotsAnimator = ObjectAnimator.ofFloat(dotsView, DotsView.DOTS_PROGRESS, 0, 1f);
            dotsAnimator.setDuration(450);
            dotsAnimator.setStartDelay(50);
            dotsAnimator.setInterpolator(ACCELERATE_DECELERATE_INTERPOLATOR);

            likeAnimatorSet.playTogether(
                    outerCircleAnimator,
                    innerCircleAnimator,
                    starScaleYAnimator,
                    starScaleXAnimator,
                    dotsAnimator
            );

            likeAnimatorSet.start();

            likeAnimatorSet.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    setFavoriteState(true);
                    if(onMaterialLikeAnimationListener !=null) onMaterialLikeAnimationListener.onMaterialLikeAnimationFinished();
                    if(onMaterialLikeItemClickListener !=null) onMaterialLikeItemClickListener.onMaterialLikeItemFinished(itemPosition);

                }
            });

        }
    }

    public void startNotLikeAnimation() {
        if (!isUnLikeAnimationRunning()) {
            ivStar.setImageResource(notFavoriteIcon);

            unlikeAnimatorSet.cancel();

            ObjectAnimator starScaleYAnimator = ObjectAnimator.ofFloat(ivStar, ImageView.SCALE_Y, 1f, 0.7f, 1f);
            starScaleYAnimator.setDuration(200);
            starScaleYAnimator.setInterpolator(DECCELERATE_INTERPOLATOR);

            ObjectAnimator starScaleXAnimator = ObjectAnimator.ofFloat(ivStar, ImageView.SCALE_X, 1f, 0.7f, 1f);
            starScaleXAnimator.setDuration(200);
            starScaleXAnimator.setInterpolator(DECCELERATE_INTERPOLATOR);

            unlikeAnimatorSet.playTogether(
                    starScaleXAnimator,
                    starScaleYAnimator
            );

            unlikeAnimatorSet.addListener(new AnimatorListenerAdapter() {

                @Override
                public void onAnimationEnd(Animator animation) {
                    setFavoriteState(false);
                    if (onMaterialLikeAnimationListener != null)
                        onMaterialLikeAnimationListener.onMaterialNotLikeAnimationFinished();
                    if (onMaterialLikeItemClickListener != null)
                        onMaterialLikeItemClickListener.onMaterialUnLikeItemFinished(itemPosition);
                }
            });

            unlikeAnimatorSet.start();
        }
    }

    public boolean isLikeAnimationRunning() {
        return likeAnimatorSet.isRunning();
    }

    public boolean isUnLikeAnimationRunning() {
        return unlikeAnimatorSet.isRunning();
    }

    @Override
    public void onClick(View view) {
        if(isFavoriteState()){
            startNotLikeAnimation();
        }else{
            startLikeAnimation();
        }
    }

    public boolean isFavoriteState() {
        return favoriteState;
    }

    public void setFavoriteState(boolean favoriteState) {
        this.favoriteState = favoriteState;
    }
}
