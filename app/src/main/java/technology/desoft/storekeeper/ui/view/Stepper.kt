package technology.desoft.storekeeper.ui.view

import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.support.v4.content.ContextCompat
import android.util.AttributeSet
import android.view.Gravity
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.FrameLayout
import android.widget.LinearLayout
import kotlinx.android.synthetic.main.view_stepper.view.*
import technology.desoft.storekeeper.R

class Stepper @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    interface Callback {
        fun onStep(oldValue: Int, newValue: Int)
    }

    private var callback: Callback? = null
    private var valueSize = 0f

    var minValue: Int = 0
    var maxValue: Int = 0
    private var dX = 0f

    var value: Int
        get() = stepperValue.text.toString().toInt()
        set(newValue) {
            if (newValue !in minValue..maxValue) return

            when (newValue) {
                minValue -> hideMinusButton()
                maxValue -> hidePlusButton()
                else -> showButtons()
            }
            callback?.onStep(oldValue = value, newValue = newValue)
            stepperValue.text = newValue.toString()
        }

    init {
        setPadding(8, 0, 8, 0)
        setBackgroundResource(R.drawable.background_stepper)
        LayoutInflater.from(context).inflate(R.layout.view_stepper, this, true)
        attrs?.let { initAttrs(it) }
        initStepper()
    }

    private fun initAttrs(attrs: AttributeSet) {
        val styleArray = context.obtainStyledAttributes(attrs, R.styleable.Stepper, 0, 0)
        val backgroundColor = styleArray.getColor(
            R.styleable.Stepper_stepperColor, ContextCompat.getColor(context, R.color.colorPrimary)
        )
        (background as GradientDrawable).setColor(backgroundColor)
        val textColor = styleArray.getColor(
            R.styleable.Stepper_textColor, ContextCompat.getColor(context, android.R.color.black)
        )
        stepperValue.setTextColor(textColor)
        val buttonsColor = styleArray.getColor(
            R.styleable.Stepper_buttonsColor, ContextCompat.getColor(context, R.color.colorAccent)
        )
        val cornerRadius = styleArray.getDimension(R.styleable.Stepper_stepperCornerRadius, 8f)
        (background as GradientDrawable).cornerRadius = cornerRadius
        minusButton.setTextColor(buttonsColor)
        plusButton.setTextColor(buttonsColor)
        val valueColor = styleArray.getColor(
            R.styleable.Stepper_valueColor, ContextCompat.getColor(context, R.color.colorAccent)
        )
        (stepperValue.background as GradientDrawable).setColor(valueColor)
        val textSize = styleArray.getDimension(R.styleable.Stepper_textSize, 12f)
        stepperValue.textSize = textSize
        plusButton.textSize = textSize
        minusButton.textSize = textSize
        val startValue = styleArray.getInteger(R.styleable.Stepper_startValue, 0)
        stepperValue.text = startValue.toString()
        minValue = styleArray.getInteger(R.styleable.Stepper_minValue, Int.MIN_VALUE)
        maxValue = styleArray.getInteger(R.styleable.Stepper_maxValue, Int.MAX_VALUE)
        valueSize = styleArray.getDimension(R.styleable.Stepper_valueSize, 0f)
        styleArray.recycle()
    }

    private fun initStepper() {
        plusButton.setOnClickListener { value++ }
        minusButton.setOnClickListener { value-- }
        initMoving(stepperValue)
    }

    private fun initMoving(stepperValue: View) {
        stepperValue.setOnTouchListener { v, event ->

            when (event?.action) {
                MotionEvent.ACTION_DOWN -> {
                    dX = v.x - event.rawX
                }
                MotionEvent.ACTION_MOVE -> v.animate()
                    .x(event.rawX + dX - v.width / 2)
                    .setDuration(0)
                    .start()
                MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> goToStartPosition()
                else -> super.onTouchEvent(event)
            }

            true
        }
    }

    private fun goToStartPosition() {
        val v = stepperValue
        stepperValue.animate()
            .x((this.width - v.width) / 2f - 12f)
            .setDuration(300L)
            .setInterpolator(AccelerateDecelerateInterpolator())
            .start()
        if (v.x + v.width / 2 < this.width / 3)
            value--
        if (v.x + v.width / 2 > 2 * this.width / 3)
            value++
    }

    private fun hidePlusButton() {
        plusButton.visibility = View.GONE
    }

    private fun hideMinusButton() {
        minusButton.visibility = View.GONE
    }

    override fun onViewAdded(child: View?) {
        super.onViewAdded(child)
        child?.viewTreeObserver?.addOnGlobalLayoutListener {
            if (valueSize != 0f) {
                stepperValue.layoutParams = FrameLayout.LayoutParams(
                    valueSize.toInt(), valueSize.toInt(), Gravity.CENTER
                )
            } else {
                stepperValue.width = stepperValue.height
            }
        }
    }

    private fun showButtons() {
        if (plusButton.visibility != View.VISIBLE)
            plusButton.visibility = View.VISIBLE
        if (minusButton.visibility != View.VISIBLE)
            minusButton.visibility = View.VISIBLE
    }


    fun setCallback(callback: Callback) {
        this.callback = callback
    }

    fun removeCallback() {
        this.callback = null
    }

}