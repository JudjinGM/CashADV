package customcircle

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import app.cashadvisor.uikit.R

class CircularDiagramFive(
    context: Context,
    attributeSet: AttributeSet? = null
) : View(context, attributeSet) {

    private val paintFirstCircle = Paint()
    private val paintSecondCircle = Paint()

    private val paintMoths = Paint()
    private val paintWord = Paint()

    private var firstCircleColor = context.getColor(R.color.subcolour1)
    private var secondCircleColor = context.getColor(R.color.m5)

    private var firstTextColor = context.getColor(R.color.colour2)
    private var secondTextColor = context.getColor(R.color.subcolour2)

    private var firstTextSize = 120f
    private var secondTextSize = 40f

    private var strokeWidthFirst = 10f
    private var strokeWidthSecond = 30f

    private var startAngle = 270f
    private var sweepAngle = 0f

    private var monthsCount = "0/0"

    init {
        if (attributeSet != null) {
            val typedArray = context.obtainStyledAttributes(attributeSet, R.styleable.CircularDiagramFive)
            firstCircleColor = typedArray.getColor(R.styleable.CircularDiagramFive_cdf_firstCircleColor, firstCircleColor)
            secondCircleColor = typedArray.getColor(R.styleable.CircularDiagramFive_cdf_secondCircleColor, secondCircleColor)
            firstTextColor = typedArray.getColor(R.styleable.CircularDiagramFive_cdf_firstTextColor, firstTextColor)
            secondTextColor = typedArray.getColor(R.styleable.CircularDiagramFive_cdf_secondTextColor, secondTextColor)
            firstTextSize = typedArray.getDimension(R.styleable.CircularDiagramFive_cdf_firstTextSize, firstTextSize)
            secondTextSize = typedArray.getDimension(R.styleable.CircularDiagramFive_cdf_secondTextSize, secondTextSize)
            strokeWidthFirst = typedArray.getDimension(R.styleable.CircularDiagramFive_cdf_strokeWidthFirst, strokeWidthFirst)
            strokeWidthSecond = typedArray.getDimension(R.styleable.CircularDiagramFive_cdf_strokeWidthSecond, strokeWidthSecond)

            typedArray.recycle()
        }

        paintFirstCircle.apply {
            style = Paint.Style.STROKE
            strokeWidth = strokeWidthFirst
            color = firstCircleColor
            isAntiAlias = true
        }

        paintSecondCircle.apply {
            style = Paint.Style.STROKE
            strokeCap = Paint.Cap.ROUND
            strokeWidth = strokeWidthSecond
            color = secondCircleColor
            isAntiAlias = true
        }

        paintMoths.apply {
            color = firstTextColor
            textAlign = Paint.Align.CENTER
            textSize = firstTextSize
            isAntiAlias = true
        }

        paintWord.apply {
            color = secondTextColor
            textAlign = Paint.Align.CENTER
            textSize = secondTextSize
            isAntiAlias = true
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val circleRadius = (height/2.5).toFloat()

        val xPos = width / 2f
        val yPos = height / 2f
        val yPosText =  (yPos - ((paintMoths.descent() + paintMoths.ascent()) / 2))

        canvas.drawCircle((width/2).toFloat(), (height/2).toFloat(), circleRadius, paintFirstCircle)
        canvas.drawText(monthsCount, xPos, yPosText, paintMoths)
        canvas.drawText(context.getString(R.string.months), xPos, yPosText+(yPosText/6), paintWord)

        canvas.drawArc(
            xPos - circleRadius,
            yPos - circleRadius,
            xPos + circleRadius,
            yPos + circleRadius,
            startAngle,
            sweepAngle,
            false,
            paintSecondCircle
        )
    }

    fun setValue(currentMonth: Int, totalMonths: Int) {
        monthsCount = "$currentMonth/$totalMonths"
        sweepAngle = (360 / totalMonths) * currentMonth.toFloat()
        invalidate()
    }
}