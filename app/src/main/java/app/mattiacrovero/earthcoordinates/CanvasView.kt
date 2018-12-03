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
        color = Color.parseColor("#ffffff")
        strokeWidth = 10f
    }

    private val paintLine2 = Paint().apply {
        color = Color.parseColor("#ff0072")
        strokeWidth = 10f
    }

    var centerX = 0f
    var centerY = 0f
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        centerX = width/2f
        centerY = height/2f

        // zero direction line
        canvas.drawLine(centerX,centerY,centerX+directionZero.x*400,centerY+directionZero.y*400,paintLine2)

        // acceleration line
        canvas.drawLine(centerX,centerY,centerX+direction.x*60,centerY+direction.y*60,paintLine)

    }

    fun updateDirection(dir: Vector3) {
        dir.x *= -1
        direction = dir
        invalidate()
    }
}