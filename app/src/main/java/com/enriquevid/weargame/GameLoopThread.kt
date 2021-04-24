package com.enriquevid.weargame

import android.graphics.Canvas

class GameLoopThread : Thread
{
    private var gView: GameView
    private var running: Boolean = false;

    private var FPS: Long = 15

    constructor(gv: GameView)
    {
        gView = gv;
    }

    override fun run()
    {
        var prevTime: Long = System.currentTimeMillis()
        var dTime: Long = 0
        var c: Canvas? = null

        while(running)
        {
            dTime += System.currentTimeMillis() - prevTime
            prevTime = System.currentTimeMillis();

            gView.text3 = dTime.toString()

            if(dTime >= 1000/FPS)
            {
                gView.update()
                dTime = 0
            }



            c = gView.getHolder().lockCanvas();
            if(c != null)
            {
                //synchronized(gView.getHolder())
                //{
                    gView.draw(c);
                //}
                gView.getHolder().unlockCanvasAndPost(c);
            }

            /*
            try
            {
                c = gView.getHolder().lockCanvas();
                if(c != null)
                {
                    synchronized(gView.getHolder())
                    {

                        gView.draw(c);
                    }
                }
            }
            catch(e: Exception)
            {

            }
            finally
            {
                if(c != null)
                {
                    gView.getHolder().unlockCanvasAndPost(c);
                }
            }

            sleepTime = ticksPS - (System.currentTimeMillis() - startTime)
            try
            {
                if(sleepTime > 0)
                {
                    sleep(sleepTime)
                }
                else
                {
                    sleep(10)
                }
            }
            catch (e: java.lang.Exception)
            {

            }
            */
        }
    }

    fun setRunning(run: Boolean)
    {
        running = run;
    }
}