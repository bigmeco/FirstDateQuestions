package com.bigmeco.firstdatequestions

import android.animation.AnimatorInflater
import android.animation.AnimatorSet
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import android.animation.Animator
import android.animation.Animator.AnimatorListener
import android.os.Build
import android.util.Log
import android.widget.CompoundButton
import kotlinx.android.synthetic.main.card_front.*


class MainActivity : AppCompatActivity() {

    private lateinit var questions: MutableList<String>
    private lateinit var mSetRightOut: AnimatorSet
    private lateinit var mSetLeftIn: AnimatorSet
    private lateinit var mSetRightOut2: AnimatorSet
    private lateinit var mSetLeftIn2: AnimatorSet
    private var mIsBackVisible = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        loadAnimations()
        changeCameraDistance()
        questions = resources.getStringArray(R.array.questions).toMutableList()
        questions.shuffle()

        switchActions.setOnCheckedChangeListener { compoundButton: CompoundButton, b: Boolean ->
            if (b) {
                actionsText.text = resources.getString(R.string.action_on)
                questions.addAll(resources.getStringArray(R.array.actions).toMutableList())
                questions.shuffle()
            } else {
                actionsText.text = resources.getString(R.string.action_off)
                questions.removeAll(resources.getStringArray(R.array.actions).toMutableList())
            }

        }
        card.setOnClickListener {
            card.isEnabled = false
            flipCard()
            questions.remove(questions[0])
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
        mSetRightOut2 = AnimatorInflater.loadAnimator(this, R.animator.out_animation) as AnimatorSet
        mSetLeftIn2 = AnimatorInflater.loadAnimator(this, R.animator.in_animation) as AnimatorSet
        mSetRightOut.addListener(lisener)
        mSetLeftIn.addListener(lisenerQ)

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

    val lisener = object : AnimatorListener {

        override fun onAnimationStart(animation: Animator) {
            Log.e("fgfdgfdgf", "lisener Start")

        }

        override fun onAnimationRepeat(animation: Animator) {
            Log.e("fgfdgfdgf", "lisener Repeat")

        }

        override fun onAnimationEnd(animation: Animator) {
            Log.e("fgfdgfdgf", "lisener End")

        }

        override fun onAnimationCancel(animation: Animator) {


        }
    }
    val lisenerQ = object : AnimatorListener {

        override fun onAnimationStart(animation: Animator) {
            Log.e("fgfdgfdgf", "lisenerQ Start")
            android.os.Handler().postDelayed({
                if (questions.size <= 2) {
                    textQuestions.text = resources.getString(R.string.end)
                } else {
                    textQuestions.text = questions[1]
                }
            }, 800)

            if (questions.size <= 2) {
                textQuestions.text = resources.getString(R.string.end)
            } else {
                textQuestions.text = questions[1]
            }
        }

        override fun onAnimationRepeat(animation: Animator) {
            Log.e("fgfdgfdgf", "lisenerQ Repeat")

        }

        override fun onAnimationEnd(animation: Animator) {
            Log.e("fgfdgfdgf", questions.size.toString())
//            Log.e("fgfdgfdgf",questions[1])
//            Log.e("fgfdgfdgf",questions[0])
            Log.e("fgfdgfdgf", "lisenerQ end")
            if (mIsBackVisible) {

                mSetRightOut2.setTarget(card_back)
                mSetLeftIn2.setTarget(card_front)
                mSetRightOut2.start()
                mSetLeftIn2.start()
                mIsBackVisible = false
            }

            card.isEnabled = true

        }

        override fun onAnimationCancel(animation: Animator) {


        }
    }
}
