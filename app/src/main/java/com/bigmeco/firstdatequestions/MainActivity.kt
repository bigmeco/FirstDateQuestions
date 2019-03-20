package com.bigmeco.firstdatequestions

import android.animation.AnimatorInflater
import android.animation.AnimatorSet
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {


    private lateinit var mSetRightOut: AnimatorSet
    private lateinit var mSetLeftIn: AnimatorSet
    private var mIsBackVisible = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        loadAnimations()
        changeCameraDistance()
        card.setOnClickListener {
            flipCard()
        }
    }

    private fun changeCameraDistance() {
        val distance = 6000
        val scale = resources.displayMetrics.density * distance
        card_front.cameraDistance = scale
        card_back.cameraDistance = scale
    }

    private fun loadAnimations() {
        mSetRightOut = AnimatorInflater.loadAnimator(this, R.animator.out_animation) as AnimatorSet
        mSetLeftIn = AnimatorInflater.loadAnimator(this, R.animator.in_animation) as AnimatorSet
    }


    private fun flipCard() {
        if (!mIsBackVisible) {
            mSetRightOut.setTarget(card_front)
            mSetLeftIn.setTarget(card_back)
            mSetRightOut.start()
            mSetLeftIn.start()
            mIsBackVisible = true
        } else {
            mSetRightOut.setTarget(card_back)
            mSetLeftIn.setTarget(card_front)
            mSetRightOut.start()
            mSetLeftIn.start()
            mIsBackVisible = false
        }
    }
}
