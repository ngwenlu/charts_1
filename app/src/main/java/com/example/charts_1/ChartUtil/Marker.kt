package com.example.charts_1.ChartUtil

import android.view.LayoutInflater
import android.widget.RelativeLayout
import com.github.mikephil.charting.charts.Chart
import com.github.mikephil.charting.components.IMarker
import com.github.mikephil.charting.utils.MPPointF


class MarkerView(context: Context?, layoutResource: Int) : RelativeLayout(context),
    IMarker {
    private var mOffset = MPPointF()
    private val mOffset2 = MPPointF()
    private var mWeakChart: WeakReference<Chart<*>>? = null

    /**
     * Constructor. Sets up the MarkerView with a custom layout resource.
     *
     * @param context
     * @param layoutResource the layout resource to use for the MarkerView
     */
    init {
        setupLayoutResource(layoutResource)
    }

    /**
     * Sets the layout resource for a custom MarkerView.
     *
     * @param layoutResource
     */
    private fun setupLayoutResource(layoutResource: Int) {
        val inflated = LayoutInflater.from(context).inflate(layoutResource, this)
        inflated.layoutParams =
            LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
        inflated.measure(
            MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED),
            MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED)
        )

        // measure(getWidth(), getHeight());
        inflated.layout(0, 0, inflated.measuredWidth, inflated.measuredHeight)
    }

    fun setOffset(offset: MPPointF) {
        mOffset = offset
        if (mOffset == null) {
            mOffset = MPPointF()
        }
    }

    fun setOffset(offsetX: Float, offsetY: Float) {
        mOffset.x = offsetX
        mOffset.y = offsetY
    }

    override fun getOffset(): MPPointF {
        return mOffset
    }

    var chartView: Chart<*>?
        get() = if (mWeakChart == null) null else mWeakChart.get()
        set(chart) {
            mWeakChart = WeakReference(chart)
        }

    override fun getOffsetForDrawingAtPoint(posX: Float, posY: Float): MPPointF {
        val offset = offset
        mOffset2.x = offset.x
        mOffset2.y = offset.y
        val chart = chartView
        val width = width.toFloat()
        val height = height.toFloat()
        if (posX + mOffset2.x < 0) {
            mOffset2.x = -posX
        } else if (chart != null && posX + width + mOffset2.x > chart.width) {
            mOffset2.x = chart.width - posX - width
        }
        if (posY + mOffset2.y < 0) {
            mOffset2.y = -posY
        } else if (chart != null && posY + height + mOffset2.y > chart.height) {
            mOffset2.y = chart.height - posY - height
        }
        return mOffset2
    }

    fun refreshContent(e: Map.Entry<*, *>?, highlight: Highlight?) {
        measure(
            MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED),
            MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED)
        )
        layout(0, 0, measuredWidth, measuredHeight)
    }

    override fun draw(canvas: Canvas, posX: Float, posY: Float) {
        val offset = getOffsetForDrawingAtPoint(posX, posY)
        val saveId: Int = canvas.save()
        // translate to the correct position and draw
        canvas.translate(posX + offset.x, posY + offset.y)
        draw(canvas)
        canvas.restoreToCount(saveId)
    }
}