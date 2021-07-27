package com.enriquevid.weargame

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Rect

class Sprite
{
    var sprX: Int
    var sprY: Int
    var sprW: Int
    var sprH: Int
    var sprRect: Rect
    var sprBmp: Bitmap

    var isAnimated: Boolean
    var bmpRows: Int
    var bmpCols: Int
    var animTime: Int

    constructor(x:Int, y:Int, w:Int, h:Int, bmp:Bitmap)
    {
        sprX = x
        sprY = y
        sprW = w
        sprH = h
        sprRect = Rect(0, 0, w, h)
        sprBmp = bmp
        isAnimated = false
        bmpRows = -1;
        bmpCols = -1;
        animTime = -1;
    }

    constructor(x:Int, y:Int, w:Int, h:Int, bmp:Bitmap, anim:Boolean, r:Int, c:Int, t:Int)
    {
        sprX = x
        sprY = y
        sprW = w
        sprH = h
        sprRect = Rect(0, 0, w, h)
        sprBmp = bmp
        isAnimated = anim
        bmpRows = r;
        bmpCols = c;
        animTime = t;
    }

    fun drawSprite(c: Canvas)
    {
        if(!isAnimated)
        {
            c.drawBitmap(sprBmp, sprRect, Rect(sprX,sprY,sprX+sprW,sprY+sprH),null)

        }
    }

}