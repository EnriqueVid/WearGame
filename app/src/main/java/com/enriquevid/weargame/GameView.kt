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

    //BACKGROUND/FOREGROUND
    private var background: Bitmap;
    private var foreground: Bitmap;

    //INPUT SPR
    private var btnPressed: Bitmap;
    private var btnReleased: Bitmap;

    private var btnA: Sprite;
    private var btnB: Sprite;
    private var btnC: Sprite;

    //INPUT VARS
    public var rotaryEvent: Float = 0.0f;
    public var xDownEvent: Float = 0.0f;
    public var yDownEvent: Float = 0.0f;
    public var xMovEvent: Float = 0.0f;
    public var yMovEvent: Float = 0.0f;
    public var xUpEvent: Float = 0.0f;
    public var yUpEvent: Float = 0.0f;

    public var rotaryInput: Float = 0.0f;
    var xDown: Int = -1
    var yDown: Int = -1
    var xMov: Int = 0
    var yMov: Int = 0
    var xUp: Int = 0
    var yUp: Int = 0

    var btnAPress: Boolean = false;
    var btnBPress: Boolean = false;
    var btnCPress: Boolean = false;

    var vibrator: Vibrator? = null;


    //DEBUG
    public var text: String = "Texto";
    public var text2: String = "Texto2";
    public var text3: String = "Texto3";
    public var text4: String = "Texto4";


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

        background = BitmapFactory.decodeResource(resources, R.drawable.case_bottom);
        foreground = BitmapFactory.decodeResource(resources, R.drawable.case_top);

        btnPressed = BitmapFactory.decodeResource(resources, R.drawable.button_pressed);
        btnReleased = BitmapFactory.decodeResource(resources, R.drawable.button_released);
        btnA = Sprite(28,367,68,68, btnReleased)
        btnB = Sprite(150,369,68,68, btnReleased)
        btnC = Sprite(272,367,68,68, btnReleased)
    }

    //Main Update Function--------------------------------------------------------------------------
    public fun update()
    {
        getInput()

        if(Point2BoxCollision(xDown, yDown, btnA.sprX, btnA.sprY, btnA.sprW, btnA.sprH, 20, 20))
        {
            btnA.sprBmp = btnPressed
        }
        if(Point2BoxCollision(xDown, yDown, btnB.sprX, btnB.sprY, btnB.sprW, btnB.sprH, 0, 0))
        {
            btnB.sprBmp = btnPressed
        }
        if(Point2BoxCollision(xDown, yDown, btnC.sprX, btnC.sprY, btnC.sprW, btnC.sprH, 0, 0))
        {
            btnC.sprBmp = btnPressed
        }

        if(xUp != -1 && yUp != -1)
        {
            btnA.sprBmp = btnReleased
            btnB.sprBmp = btnReleased
            btnC.sprBmp = btnReleased
        }




    }

    //Main Draw Function----------------------------------------------------------------------------
    public override fun draw(canvas: Canvas)
    {
        super.draw(canvas)
        //Erase Screen
        canvas.drawColor(Color.BLUE)

        //Draw Background Image
        canvas.drawBitmap(background, 0f, 0f, null)

        //Draw Game Elements


        //Draw Foreground
        canvas.drawBitmap(foreground, 0f, 0f, null)

        //Draw Input
        btnA.drawSprite(canvas);
        btnB.drawSprite(canvas);
        btnC.drawSprite(canvas);

        //Debug

        var paint2: Paint = Paint()
        paint2.setColor(Color.WHITE)
        paint2.setStyle(Paint.Style.FILL)
        paint2.setTextSize(50f)

        canvas.drawText(text, 10f, 50f, paint2)
        canvas.drawText(rotaryInput.toString(), 10f, 100f, paint2)
        canvas.drawText(text3, 10f, 150f, paint2)
        canvas.drawText(text4, 10f, 200f, paint2)

    }

    public fun getInput()
    {
        rotaryInput = 0f
        xDown = 0
        yDown = 0
        xUp = -1
        yUp = -1

        if(rotaryEvent >= 3f || rotaryEvent <= -3f)
        {
            rotaryInput = sign(rotaryEvent)
            rotaryEvent = 0f

            vibrate(1)
        }


        if(xDownEvent != -1f && yDownEvent != -1f)
        {
            xDown = xDownEvent.toInt()
            yDown = yDownEvent.toInt()

            xDownEvent = -1f
            yDownEvent = -1f
        }

        if(xUpEvent != -1f && yUpEvent != -1f)
        {
            xUp = xUpEvent.toInt()
            yUp = yUpEvent.toInt()

            xUpEvent = -1f
            yUpEvent = -1f
        }






    }

    public override fun onTouchEvent(event: MotionEvent?): Boolean
    {
        if (event != null)
        {
            when(event.action)
            {
                MotionEvent.ACTION_DOWN ->
                    {
                        xDownEvent = event.x;
                        yDownEvent = event.y;
                    }
                MotionEvent.ACTION_UP ->
                    {
                        xUpEvent = event.x;
                        yUpEvent = event.y;
                    }
                MotionEvent.ACTION_MOVE ->
                    {
                        xMovEvent = event.x;
                        yMovEvent = event.y;
                    }
            }
        }

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