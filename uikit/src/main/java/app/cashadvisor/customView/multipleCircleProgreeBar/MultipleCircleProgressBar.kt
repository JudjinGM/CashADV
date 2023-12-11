package app.cashadvisor.customView.multipleCircleProgreeBar


import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import app.cashadvisor.customView.multipleCircleProgreeBar.model.Progress
import app.cashadvisor.customView.multipleCircleProgreeBar.model.ProgressCircle


class MultipleCircleProgressBar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0,
) : View(context, attrs, defStyleAttr, defStyleRes) {

    private var progressList: List<Progress> = listOf()

    private var centerX: Float = 0f
    private var centerY: Float = 0f

    private var listOfMainPaints: List<Paint> = mutableListOf()
    private var listOfTransparentPaints: List<Paint> = mutableListOf()
    private var progressCircleList: List<ProgressCircle> = mutableListOf()

    private val arcPath = Path()

    fun initialiseProgressBar(progressList: List<Progress>) {
        this.progressList = progressList
        initPaints()
        invalidate()
    }

    fun setProgressById(id: Int, amount: Int) {
        val result = progressList.map { progress ->
            if (progress.id == id) {
                progress.copy(value = amount)
            } else progress.copy()
        }
        progressList = result
        invalidate()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        val defaultSide = convertDimension(DEFAULT_SIZE).toInt()

        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        val widthSize = MeasureSpec.getSize(widthMeasureSpec)
        val heightSize = MeasureSpec.getSize(heightMeasureSpec)

        val sideSize = widthSize.coerceAtMost(heightSize)


        val width = when (widthMode) {
            MeasureSpec.AT_MOST -> sideSize.coerceAtMost(defaultSide)
            MeasureSpec.EXACTLY -> sideSize
            else -> defaultSide
        }

        val height = when (heightMode) {
            MeasureSpec.AT_MOST -> sideSize.coerceAtMost(defaultSide)
            MeasureSpec.EXACTLY -> sideSize
            else -> defaultSide
        }
        setMeasuredDimension(width, height)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        prepareForDraw()
        drawProgressBar(
            canvas = canvas, progressCircleList = progressCircleList
        )
    }


    private fun prepareForDraw() {
        centerX = width / 2f
        centerY = height / 2f

        if (progressList.isNotEmpty()) {
            val size = width.coerceAtMost(height)

            val strokeWidth = calculateStrokeWidth(size, progressList.size)
            val radiusList = calculateRadiusList(size, strokeWidth, progressList.size)
            applyStrokeWidthToPaints(listOfMainPaints, strokeWidth)
            applyStrokeWidthToPaints(listOfTransparentPaints, strokeWidth)

            progressCircleList = initProgressCircles(
                progressList, listOfMainPaints, listOfTransparentPaints, radiusList
            )

            val angleList: MutableList<Float> = mutableListOf()
            for (progress in progressList) {
                angleList.add(getAngle(progress.value))
            }
        }
    }

    private fun drawProgressBar(canvas: Canvas, progressCircleList: List<ProgressCircle>) {
        for (progressCircle in progressCircleList) {
            val rectF = RectF(
                centerX - progressCircle.radius.toFloat(),
                centerY - progressCircle.radius.toFloat(),
                centerX + progressCircle.radius.toFloat(),
                centerY + progressCircle.radius.toFloat()
            )
            canvas.drawCircle(
                centerX, centerY, progressCircle.radius.toFloat(), progressCircle.paintBackground
            )
            arcPath.addRoundRect(rectF, 100f, 100f, Path.Direction.CW)

            arcPath.reset()
            arcPath.addArc(rectF, START_ANGLE, progressCircle.endAngle)
            canvas.drawPath(arcPath, progressCircle.paintMain)
        }
    }

    private fun initPaints() {
        listOfMainPaints = initMainPaints(progressList)
        listOfTransparentPaints = initBackgroundPaints(progressList)
    }

    private fun initMainPaints(progressList: List<Progress>): List<Paint> {
        val result: MutableList<Paint> = mutableListOf()
        for (progress in progressList) {
            val paint = Paint()
            initPaint(paint, progress.mainColor)
            result.add(paint)
        }
        return result
    }

    private fun initBackgroundPaints(progressList: List<Progress>): List<Paint> {
        val result: MutableList<Paint> = mutableListOf()
        for (progress in progressList) {
            val paint = Paint()
            initPaint(paint, progress.backgroundColor)
            result.add(paint)
        }
        return result
    }

    private fun initPaint(paint: Paint, color: Int) {
        paint.color = color
        paint.style = Paint.Style.STROKE
        paint.isAntiAlias = true
        paint.strokeCap = Paint.Cap.ROUND
    }

    private fun initProgressCircles(
        progressList: List<Progress>,
        listOfMainPaints: List<Paint>,
        listOfBackgroundPaints: List<Paint>,
        radiusList: List<Int>
    ): List<ProgressCircle> {

        val result: MutableList<ProgressCircle> = mutableListOf()

        for ((index, progress) in progressList.withIndex()) {
            result.add(
                ProgressCircle(
                    paintMain = listOfMainPaints[index],
                    paintBackground = listOfBackgroundPaints[index],
                    endAngle = getAngle(progress.value),
                    radius = radiusList[index]
                )
            )
        }
        return result
    }

    private fun calculateRadiusList(width: Int, strokeWidth: Int, count: Int): List<Int> {
        val result = mutableListOf<Int>()
        val maxRadius = width / 2 - (strokeWidth / 2)
        if (count >= 1) {
            result.add(maxRadius)
        }
        var radius = maxRadius
        for (i in 1 until count) {
            radius -= (strokeWidth * 2)
            result.add(radius)
        }
        return result
    }


    private fun calculateStrokeWidth(width: Int, progressCount: Int): Int {
        return (width / 2) / (progressCount * 2)
    }

    private fun getAngle(progress: Int): Float {
        return progress * FULL_CIRCLE_ANGLE / PROGRESS_MAX_VALUE
    }

    private fun applyStrokeWidthToPaints(paints: List<Paint>, strokeWidth: Int) {
        for (paint in paints) {
            paint.strokeWidth = strokeWidth.toFloat()
        }
    }

    private fun convertDimension(dimensionDp: Int): Float {
        return dimensionDp * resources.displayMetrics.density
    }

    companion object {
        const val DEFAULT_SIZE = 160
        const val PROGRESS_MAX_VALUE = 100f
        const val FULL_CIRCLE_ANGLE = 360
        const val START_ANGLE = 270f
    }
}