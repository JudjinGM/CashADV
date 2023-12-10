package app.cashadvisor.uikit.customview

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import app.cashadvisor.uikit.R

class CircularGraph(
    context: Context,
    attrs: AttributeSet? = null,
) : View(context, attrs) {

    private val paint1 = Paint(Paint.ANTI_ALIAS_FLAG)
    private val paint2 = Paint(Paint.ANTI_ALIAS_FLAG)
    private val textPaint1 = Paint()
    private val textPaint2 = Paint()

    private var totalValueText = ""
    private var headerText = "Всего!"
    private val rubleSymbol = "\u20BD"
    private var strokeWidth = 36f


    private var color1 = context.getColor(R.color.subcolour1)
    private var color2 = context.getColor(R.color.m5)

    private var textSize = 40f

    private var biasAngle = 7f

    private var startAngle1 = 0f
    private var sweepAngle1 = 360f
    private var startAngle2 = 0f
    private var sweepAngle2 = 0f

    init {
        paint1.style = Paint.Style.STROKE
        paint1.strokeCap = Paint.Cap.ROUND

        paint2.style = Paint.Style.STROKE
        paint2.strokeCap = Paint.Cap.ROUND

        textPaint1.color = context.getColor(R.color.subcolour2)
        textPaint1.textAlign = Paint.Align.CENTER

        textPaint2.color = context.getColor(R.color.colour1)
        textPaint2.textAlign = Paint.Align.CENTER

        if (attrs != null) {
            val typedArray = context.obtainStyledAttributes(attrs, R.styleable.CircularGraph)

            color1 = typedArray.getColor(R.styleable.CircularGraph_arcColor1, color1)
            color2 = typedArray.getColor(R.styleable.CircularGraph_arcColor2, color2)
            strokeWidth = typedArray.getDimension(R.styleable.CircularGraph_circleThickness, strokeWidth)
            biasAngle = typedArray.getDimension(R.styleable.CircularGraph_arcSpacing, biasAngle)
            headerText = typedArray.getString(R.styleable.CircularGraph_headerText) ?: headerText
            textSize = typedArray.getDimension(R.styleable.CircularGraph_textScale, textSize)

            typedArray.recycle()
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        paint1.strokeWidth = strokeWidth
        paint1.color = color1

        paint2.strokeWidth = strokeWidth
        paint2.color = color2

        textPaint1.textSize = textSize
        textPaint2.textSize = textSize

        val centerX = width / 2f
        val centerY = height / 2f

        val circleRadius = width.coerceAtMost(height) / 2 - 0.5f * strokeWidth

        canvas.drawArc(
            centerX - circleRadius,
            centerY - circleRadius,
            centerX + circleRadius,
            centerY + circleRadius,
            startAngle1,
            sweepAngle1,
            false,
            paint1
        )

        canvas.drawArc(
            centerX - circleRadius,
            centerY - circleRadius,
            centerX + circleRadius,
            centerY + circleRadius,
            startAngle2,
            sweepAngle2,
            false,
            paint2
        )

        val textCenterBias = centerY / 8

        val textY1 = centerY - (textPaint1.descent() + textPaint1.ascent()) / 2 - textCenterBias
        canvas.drawText(headerText, centerX, textY1, textPaint1)

        val textY2 = centerY - (textPaint2.descent() + textPaint2.ascent()) / 2 + textCenterBias
        canvas.drawText(totalValueText, centerX, textY2, textPaint2)
    }

    fun setNewValues(varValue: Float, constValue: Float) {

        val sum = varValue + constValue

        val sumFormat = String.format("%.2f", sum)

        val varValueRatio = varValue / sum
        val constValueRatio = constValue / sum

        totalValueText = "$rubleSymbol $sumFormat"

        val varValueAngle = varValueRatio * (360 - 2 * biasAngle)
        val constValueAngle = constValueRatio * (360 - 2 * biasAngle)

        if (constValueRatio <= 0.01) {
            startAngle1 = 0f + biasAngle
            sweepAngle1 = varValueAngle
            sweepAngle2 = 0f
        } else if (varValueRatio <= 0.01) {
            startAngle1 = 0f
            sweepAngle1 = 0f
            startAngle2 = 0f + biasAngle
            sweepAngle2 = constValueAngle
        } else {
            startAngle1 = 0f + biasAngle
            sweepAngle1 = varValueAngle - biasAngle
            startAngle2 = varValueAngle + 2 * biasAngle
            sweepAngle2 = constValueAngle - biasAngle
        }

        invalidate()
    }
}


