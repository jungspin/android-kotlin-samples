package com.pinslog.samples.view

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import com.pinslog.samples.R

class MyDrawingView : View {

    constructor(context: Context) : super(context) {}
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {

    }


    private lateinit var canvas: Canvas

    private val path: Path = Path()
    private var paintColor = context.getColor(R.color.black)
    private val bitmapPaint: Paint = Paint(Paint.DITHER_FLAG)
    private lateinit var bitmap: Bitmap

    private val paint: Paint = Paint().apply {
        isAntiAlias = true
        isDither = true
        strokeWidth = 10f
        color = paintColor
        style = Paint.Style.STROKE
        strokeJoin = Paint.Join.ROUND
        strokeCap = Paint.Cap.ROUND
        strokeWidth = 12f
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)
        canvas = Canvas(bitmap)
    }


    override fun onTouchEvent(motionEvent: MotionEvent?): Boolean {
        motionEvent?.let { event ->
            val x = event.x
            val y = event.y
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    // 처음 눌렀을 때
                    path.moveTo(x, y)
                }

                MotionEvent.ACTION_MOVE -> {
                    path.lineTo(x, y)
                }
            }
            invalidate()

        }

        return true
    }

    // 화면을 그리는 작업이 필요할 때마다 프레임워크가 자동으로 호출함
    // 매개변수인 canvas는 drawRect(), drawText() 같은 모든 그리기 메서드를 가지고 있음
    // 애플리케이션을 다시 그려야한다면 invaildate()가 호출되고, 안드로이드는 적절한 시간에
    // onDraw()를 호출함
    // Canvas는 Bitmap 객체를 가지고 있음
    // 사용자가 캔버스에 그림을 그리면 캔버스가 가지고 있는 비트맵에 그림이 그려짐
    override fun onDraw(canvas: Canvas?) {
//        super.onDraw(canvas)
        // path에 저장된 명령어들을 실행
        canvas?.let {
            it.drawBitmap(bitmap, 0f, 0f, bitmapPaint)
            it.drawPath(path, paint)
        }
    }

}