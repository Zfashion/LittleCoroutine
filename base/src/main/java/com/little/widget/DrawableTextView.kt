package com.little.widget

import android.content.Context
import android.content.res.Resources
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.util.Log
import androidx.appcompat.widget.AppCompatTextView
import com.little.base.R

class DrawableTextView: AppCompatTextView {

    constructor(context: Context): this(context, null)

    constructor(context: Context, attributeSet: AttributeSet?): this(context, attributeSet, 0)

    constructor(context: Context, attributeSet: AttributeSet?, defStyle: Int): super(
        context,
        attributeSet,
        defStyle
    ) {
        init(context, attributeSet, defStyle)
    }

    companion object val TAG = "DrawableTextView"

    private var finalBitmap: Bitmap? = null
    private var drawableWidth: Int = 0
    private var drawableHeight: Int = 0
    private var drawablePaddingTop: Int = 0
    private var drawablePaddingBottom: Int = 0
    private var drawablePaddingLeft: Int = 0
    private var drawablePaddingRight: Int = 0
    private var loc: DrawableLoc = DrawableLoc.LEFT

    private var viewWidth: Int = 0
    private var viewHeight: Int = 0

    private var bitmapLeft = 0f
    private var bitmapTop = 0f

    private lateinit var textPaint: Paint
    private lateinit var bitmapPaint: Paint

    private fun init(context: Context, attrs: AttributeSet?, defStyle: Int) {
        val typeArrays = context.obtainStyledAttributes(
            attrs,
            R.styleable.DrawableTextView,
            defStyle,
            0
        )
        typeArrays.apply {
            val drawableSrc = getDrawable(R.styleable.DrawableTextView_drawableSrc)
            drawableWidth = getDimensionPixelSize(R.styleable.DrawableTextView_drawable_width, 0)
            drawableHeight = getDimensionPixelSize(R.styleable.DrawableTextView_drawable_height, 0)
            drawablePaddingTop = getDimensionPixelSize(R.styleable.DrawableTextView_drawable_padding_top, 0)
            drawablePaddingBottom = getDimensionPixelSize(R.styleable.DrawableTextView_drawable_padding_bottom, 0)
            drawablePaddingLeft = getDimensionPixelSize(R.styleable.DrawableTextView_drawable_padding_left, 0)
            drawablePaddingRight = getDimensionPixelSize(R.styleable.DrawableTextView_drawable_padding_right, 0)
            loc = DrawableLoc.fromLoc(getInt(R.styleable.DrawableTextView_drawable_loc, 2))
            generateBitmap(drawableSrc)
            recycle()
        }
        textPaint = Paint()
        bitmapPaint = Paint()
        bitmapPaint.xfermode = PorterDuffXfermode(PorterDuff.Mode.DST_OUT)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        viewWidth = measureWidth(widthMeasureSpec)
        viewHeight = measureHeight(heightMeasureSpec)
        setMeasuredDimension(viewWidth, viewHeight)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
    }


    override fun onDraw(canvas: Canvas?) {
        finalBitmap?.let {
            when(loc) {
                DrawableLoc.LEFT -> {
                    bitmapLeft = drawablePaddingLeft.toFloat()
                    bitmapTop = ((viewHeight - it.height) / 2).toFloat()
                    canvas?.drawBitmap(it, bitmapLeft, bitmapTop, bitmapPaint)
                    canvas?.translate((it.width + drawablePaddingLeft + drawablePaddingRight).toFloat(), 0f)
//                    canvas?.translate(0f, 0f)
                    super.onDraw(canvas)
                }

                DrawableLoc.TOP -> {
                    bitmapLeft = ((viewWidth - it.width) / 2).toFloat()
                    bitmapTop = (paddingTop + drawablePaddingTop).toFloat()
                    canvas?.drawBitmap(it, bitmapLeft, bitmapTop, bitmapPaint)
                    canvas?.translate(0f, 0f)
                    super.onDraw(canvas)
                }

                DrawableLoc.RIGHT -> {
                    super.onDraw(canvas)
                    bitmapLeft = (viewWidth - drawablePaddingRight - it.width).toFloat()
                    bitmapTop = ((viewHeight - it.height) / 2).toFloat()
                    canvas?.translate(bitmapLeft, 0f)
                    canvas?.drawBitmap(it, paddingRight.toFloat(), bitmapTop, bitmapPaint)
                }

                DrawableLoc.BOTTOM -> {
                    super.onDraw(canvas)
                    bitmapLeft = ((viewWidth - it.width) / 2).toFloat()
                    bitmapTop = (viewHeight - paddingBottom - drawablePaddingBottom - it.height).toFloat()
                    canvas?.translate(bitmapLeft, bitmapTop)
                    canvas?.drawBitmap(it, 0f, 0f, bitmapPaint)
                }
            }
        }
    }


    private fun measureHeight(heightMeasureSpec: Int): Int {
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        val heightSize = MeasureSpec.getSize(heightMeasureSpec)
        val finalPaddingTop = if (paddingTop > drawablePaddingTop) paddingTop else drawablePaddingTop
        val finalPaddingBottom = if (paddingBottom> drawablePaddingBottom) paddingBottom else drawablePaddingBottom
        val height = when(heightMode) {
            MeasureSpec.EXACTLY -> {
                val h = if (loc == DrawableLoc.LEFT || loc == DrawableLoc.RIGHT) finalPaddingTop + finalPaddingBottom + heightSize
                else paddingTop + paddingBottom + heightSize + (finalBitmap?.height ?: 0) + drawablePaddingTop + drawablePaddingBottom
                Log.i(TAG, "h: $h")
                h
            }
            else -> {
                val textHeight = dp2px(-textPaint.ascent() + textPaint.descent() + 26)
                Log.i(TAG, "文本高度：$textHeight")
                val o3 = if (finalBitmap != null) if (textHeight > finalBitmap!!.height) textHeight else finalBitmap!!.height
                else textHeight
                val h = if (loc == DrawableLoc.LEFT || loc == DrawableLoc.RIGHT) finalPaddingTop + finalPaddingBottom + o3
                else paddingTop + paddingBottom + o3 + (finalBitmap?.height ?: 0) + drawablePaddingTop + drawablePaddingBottom
                Log.i(TAG, "h: $h")
                h
            }
        }
        Log.i(TAG, "控件的高度：$height")
        return height
    }

    private fun measureWidth(widthMeasureSpec: Int): Int {
        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val widthSize = MeasureSpec.getSize(widthMeasureSpec)
        Log.i(TAG, "宽度1：$widthSize")
        val bitmapWidth = if (finalBitmap != null) finalBitmap!!.width else 0
        Log.i(TAG, "图片宽度：$bitmapWidth")
        val width = when(widthMode) {
            MeasureSpec.EXACTLY -> {
                Log.i(TAG, "EXACTLY")
                val i = if (loc == DrawableLoc.LEFT || loc == DrawableLoc.RIGHT) { widthSize + paddingLeft + paddingRight + bitmapWidth + drawablePaddingLeft + drawablePaddingRight }
                else { widthSize + paddingLeft + paddingRight }
                Log.i(TAG, "i: $i")
                i
            }
            else -> {
                Log.i(TAG, "Other")
                val textWidth = dp2px(textPaint.measureText(text.toString()) + 16)
                Log.i(TAG, "textWidth: $textWidth")
                val i = if (loc == DrawableLoc.LEFT || loc == DrawableLoc.RIGHT) { textWidth + paddingLeft + paddingRight + bitmapWidth + drawablePaddingLeft + drawablePaddingRight }
                else { textWidth + paddingLeft + paddingRight }
                Log.i(TAG, "i: $i")
                i
            }
        }
        Log.i(TAG, "控件的宽度：$width")
        return width
    }


    private fun generateBitmap(drawableSrc: Drawable?) {
        val bitmap = (drawableSrc as BitmapDrawable).bitmap
        finalBitmap =
            if (drawableWidth != 0 && drawableHeight != 0) getBitmap(bitmap)
            else Bitmap.createScaledBitmap(bitmap, drawableWidth, drawableHeight, true)
    }

    private fun getBitmap(bitmap: Bitmap): Bitmap? {
        val widthScale = (drawableWidth.toFloat() / bitmap.width.toFloat())
        val heightScale = (drawableHeight.toFloat() / bitmap.height.toFloat())
        val matrix = Matrix()
        matrix.postScale(widthScale, heightScale)
        return Bitmap.createBitmap(bitmap, 0, 0, drawableWidth, drawableHeight, matrix, true)
    }





    enum class DrawableLoc(var loc: Int) {
        TOP(0), BOTTOM(1), LEFT(2), RIGHT(3);

        companion object {
            fun fromLoc(loc: Int): DrawableLoc {
                for (f in values()) {
                    if (f.loc == loc) return f
                }
                throw IllegalArgumentException()
            }
        }
    }

    fun dp2px(dpValue: Float): Int {
        val scale = Resources.getSystem().displayMetrics.density
        return (dpValue * scale + 0.5f).toInt()
    }
}