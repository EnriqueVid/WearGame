package com.enriquevid.weargame

import android.content.Context
import android.graphics.*
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView
import androidx.core.content.ContextCompat.getSystemService
import kotlin.math.sign


class GameView : SurfaceView
{

    private var sHolder: SurfaceHolder;
    private var gameLoopThread: GameLoopThread;

    //Background
    private var background: Bitmap;
    private var bRect: Rect;
    //private var bRect2: Rect;



    private var paint: Paint = Paint()
    private var bmp: Bitmap;

    public var text: String = "Texto";
    public var text2: String = "Texto2";
    public var text3: String = "Texto3";
    public var text4: String = "Texto4";

    var vibrator: Vibrator? = null;

    public var rotaryEvent: Float = 0.0f
    public var rotaryInput: Float = 0.0f

    var xPos: Float = 24f;

    //private var spr: Sprite;

    constructor(context: Context): super(context)
    {

        isFocusable = true
        isFocusableInTouchMode = true
        requestFocus()

        gameLoopThread = GameLoopThread(this);
        sHolder = holder
        sHolder.addCallback(object : SurfaceHolder.Callback
            {
                override fun surfaceDestroyed(holder: SurfaceHolder) {}
                override fun surfaceCreated(holder: SurfaceHolder)
                {
                    gameLoopThread.setRunning(true);
                    gameLoopThread.start();
                }
                override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {}
            })


        paint.setAntiAlias(false)
        paint.setDither(false)
        paint.setFilterBitmap(false);

        bmp = BitmapFactory.decodeResource(resources, R.drawable.default_img);
        background = BitmapFactory.decodeResource(resources, R.drawable.background_img);
        bRect = Rect(0, 0, background.width, background.height);

    }

    //Main Update Function--------------------------------------------------------------------------
    public fun update()
    {

        getInput()

        rotaryInput *= 5
        xPos += rotaryInput

        text = xPos.toString()

    }

    //Main Draw Function----------------------------------------------------------------------------
    public override fun draw(canvas: Canvas)
    {
        super.draw(canvas)
        canvas.drawColor(Color.BLUE)
        //canvas.drawBitmap(background, bRect, Rect(0,0,this.width,this.height), null)
        canvas.drawBitmap(background, 0f, 0f, null)

        canvas.drawBitmap(bmp, xPos, 105f, paint)

        var paint2: Paint = Paint()
        paint2.setColor(Color.BLACK)
        paint2.setStyle(Paint.Style.FILL)
        paint2.setTextSize(50f)

        canvas.drawText(text, 10f, 50f, paint2)
        canvas.drawText(rotaryInput.toString(), 10f, 100f, paint2)
        canvas.drawText(text3, 10f, 150f, paint2)

    }

    public fun getInput()
    {
        rotaryInput = 0f

        if(rotaryEvent >= 3f || rotaryEvent <= -3f)
        {
            rotaryInput = sign(rotaryEvent)
            rotaryEvent = 0f

            vibrate(1)
        }
    }

    public override fun onTouchEvent(event: MotionEvent?): Boolean
    {


        return super.onTouchEvent(event)
    }

    fun vibrate(millisec: Int)
    {
        if(vibrator != null)
        {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                vibrator?.vibrate(VibrationEffect.createOneShot(millisec.toLong(), VibrationEffect.EFFECT_TICK))
            } else {
                vibrator?.vibrate(millisec.toLong())
            }
        }
    }













}