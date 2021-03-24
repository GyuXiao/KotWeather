package com.example.kotweather.common.custom

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.*
import android.view.View.OnClickListener
import android.widget.HorizontalScrollView
import android.widget.LinearLayout
import android.widget.Scroller
import com.example.kotweather.R
import com.example.kotweather.common.util.getSky
import com.example.kotweather.model.HourlyWeather
import java.util.*


class WeatherView : HorizontalScrollView {
    private lateinit var mPaint: Paint
    private lateinit var mPath: Path

    private var viewWidth = 0
    private var viewHeight = 0
    private var screenWidth = 0
    private var screenHeight = 0
    private var lineInteval = 0

    private var mLineWidth = 6f
    private var mLineColor = R.color.always_white_text
    private var lastX = 0.0F

    private var mColumnNumber = 6

    private lateinit var mVelocityTracker: VelocityTracker
    private lateinit var viewConfiguration: ViewConfiguration
    private lateinit var scroller: Scroller
    private lateinit var mHourlyWeatherList: ArrayList<HourlyWeather>
    private lateinit var onWeatherItemClickListener: OnWeatherItemClickListener

    constructor(mContext: Context) : super(mContext,null)

    constructor(mContext: Context, attrs: AttributeSet) :
            super(mContext, attrs) {
        init(mContext, attrs)
    }

    constructor(mContext: Context, attrs: AttributeSet, defStyleAttr:Int) :
            super(mContext, attrs, defStyleAttr) {
        init(mContext, attrs)
    }

    private fun init(context: Context, attributeSet: AttributeSet) {
        mPaint = Paint()
        mPaint.isAntiAlias = true
        mPaint.strokeWidth = mLineWidth
        mPaint.style = Paint.Style.STROKE
        mPaint.setColor(context.getColor(mLineColor))
        mPath = Path()
        scroller = Scroller(context)
        viewConfiguration = ViewConfiguration.get(context)
        screenWidth = resources.displayMetrics.widthPixels
    }


    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        if (childCount > 0) {
            var root = getChildAt(0) as ViewGroup
            if (root.childCount > 0) {
                var hourlyWeatherItem = root.getChildAt(0) as HourlyWeatherItem
                val dX: Int = hourlyWeatherItem.getTempX().toInt()
                val dY: Int = hourlyWeatherItem.getTempY().toInt()
                val temperatureView =
                    hourlyWeatherItem.findViewById<View>(R.id.hourly_temp) as TemperatureView
                temperatureView.setRadius(10F)
                val x = (dX + temperatureView.getXPoint()).toInt()
                val y = (dY + temperatureView.getYPoint()).toInt()
                mPath.reset()
                mPath.moveTo(x.toFloat(), y.toFloat())

                //折线
                for (i in 0 until root.childCount - 1) {
                    val child: HourlyWeatherItem = root.getChildAt(i) as HourlyWeatherItem
                    val child1: HourlyWeatherItem = root.getChildAt(i + 1) as HourlyWeatherItem
                    val dayX = child.getTempX().toInt() + child.width * i
                    val dayY = child.getTempY().toInt()
                    val dayX1 = (child1.getTempX()).toInt() + child1.width * (i + 1)
                    val dayY1 = child1.getTempY().toInt()
                    val tempV = child.findViewById<View>(R.id.hourly_temp) as TemperatureView
                    val tempV1 = child1.findViewById<View>(R.id.hourly_temp) as TemperatureView
                    tempV.setRadius(10F)
                    tempV1.setRadius(10F)
                    val x1 = dayX + tempV.getXPoint()
                    val y1 = dayY + tempV.getYPoint()
                    val x11 = dayX1 + tempV1.getXPoint()
                    val y11 = dayY1 + tempV1.getYPoint()
                    canvas!!.drawLine(
                        x1, y1, x11, y11, mPaint
                    )
                    invalidate()
                }
            }
        }
    }


//    override fun onTouchEvent(ev: MotionEvent?): Boolean {
//        if(mVelocityTracker == null){
//            mVelocityTracker = VelocityTracker.obtain()
//        }
//        mVelocityTracker.addMovement(ev)
//        when(ev?.action){
//            MotionEvent.ACTION_DOWN->{
//                if(!scroller.isFinished){
//                    scroller.abortAnimation()
//                }
//                lastX = ev.x
//                return true
//            }
//            MotionEvent.ACTION_MOVE->{
//                var x = ev.x
//                var deltaX = (lastX-x).toInt()
//                if(scrollX + deltaX < 0){
//                    scrollTo(0,0)
//                    return true
//                } else if(scrollX + deltaX > viewWidth - screenWidth){
//                    scrollTo(viewWidth-screenWidth, 0)
//                    return true
//                }
//                scrollBy(deltaX, 0)
//                lastX = x
//            }
//            MotionEvent.ACTION_UP->{
//                var x = ev.x
//                mVelocityTracker.computeCurrentVelocity(1000)
//                var xVelocity = mVelocityTracker.getXVelocity().toInt()
//                if(abs(xVelocity) > viewConfiguration.scaledMinimumFlingVelocity){
//                    scroller.fling(scrollX,0,-xVelocity,0,0,viewWidth-screenWidth,0,0)
//                    invalidate()
//                }
//            }
//        }
//        return super.onTouchEvent(ev)
//    }



    fun setLineWidth(lineWidth: Float) {
        mLineWidth = lineWidth
        mPaint.strokeWidth = lineWidth
        invalidate()
    }


    fun setOnWeatherItemClickListener(weatherItemClickListener: OnWeatherItemClickListener) {
        onWeatherItemClickListener = weatherItemClickListener
    }

    private fun getScreenWidth(): Int {
        val wm = context
            .getSystemService(Context.WINDOW_SERVICE) as WindowManager
        return wm.defaultDisplay.width
    }

    private class TempComparator : Comparator<HourlyWeather> {
        override fun compare(o1: HourlyWeather, o2: HourlyWeather): Int {
            return if (o1.temp === o2.temp) {
                0
            } else if (o1.temp > o2.temp) {
                1
            } else {
                -1
            }
        }
    }

    private fun getMaxTemp(list: ArrayList<HourlyWeather>?): Int {
        return (
                if (list != null) {
                    Collections.max<HourlyWeather>(list, TempComparator()).temp
                } else 0)
    }

    private fun getMinTemp(list: ArrayList<HourlyWeather>?): Int {
        return (if (list != null) {
            Collections.min<HourlyWeather>(list, TempComparator()).temp
        } else 0)
    }

    @Throws(Exception::class)
    fun setColumnNumber(num: Int) {
        if (num > 2) {
            this.mColumnNumber = num
            setList(this.mHourlyWeatherList)
        } else {
            throw Exception("ColumnNumber should lager than 2")
        }
    }

    fun setList(list: ArrayList<HourlyWeather>) {
        this.mHourlyWeatherList = list
        var screenWidth = getScreenWidth()
        var max = getMaxTemp(list).toInt()
        var min = getMinTemp(list).toInt()
        removeAllViews()
        val llRoot = LinearLayout(context)
        llRoot.layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        llRoot.orientation = LinearLayout.HORIZONTAL
        for (i in 0 until list.size) {
            val model: HourlyWeather = list[i]
            val itemView = HourlyWeatherItem(context)
            itemView.setMaxTemp(max)
            itemView.setMinTemp(min)
            itemView.setTime(model.time)
            itemView.setTemp(model.temp)
            itemView.setWeather(model.weather)
            itemView.setImg(getSky(model.skycon.value).icon)
            itemView.setWindOri(model.windOri)
            itemView.setWindLevel(model.windLevel)
            itemView.setAirLevel(model.airLevel)
            itemView.layoutParams =
                LinearLayout.LayoutParams(
                    screenWidth / mColumnNumber,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )

            itemView.setClickable(true)
            itemView.setOnClickListener(OnClickListener {
                if (onWeatherItemClickListener != null) {
                    onWeatherItemClickListener.onItemClick(itemView, i, list[i])
                }
            })
            llRoot.addView(itemView)
        }
        addView(llRoot)
        invalidate()
    }

    open interface OnWeatherItemClickListener {
        fun onItemClick(
            itemView: HourlyWeatherItem?,
            position: Int,
            hourlyWeather: HourlyWeather?
        )
    }
}