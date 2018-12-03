package app.mattiacrovero.earthcoordinates


import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View


class CanvasView(internal var context: Context, attrs: AttributeSet) : View(context, attrs) {

    private var mBitmap: Bitmap? = null
    private var mCanvas: Canvas? = null

    private var directionZero = Vector3(0f,-1f,0f)
    private var direction = Vector3(0f,0f,0f)

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        mBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)
        mCanvas = Canvas(mBitmap!!)
    }

    private val paintBackground = Paint().apply {
        color = Color.parseColor("#000000")
    }

    private val paintLine = Paint().apply {
        color = Color.parseColor("#0000FF")
        strokeWidth = 10f
    }

    private val paintLine2 = Paint().apply {
        color = Color.parseColor("#00FF00")
        strokeWidth = 10f
    }
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.translate(500f,600f)
        canvas.drawLine(0f,0f,direction.x*200,direction.y*200,paintLine)
        canvas.drawLine(0f,0f,directionZero.x*200,directionZero.y*200,paintLine2)

    }

    fun updateDirection(dir: Vector3) {
        dir.x *= -1
        direction = dir
        invalidate()
    }
}