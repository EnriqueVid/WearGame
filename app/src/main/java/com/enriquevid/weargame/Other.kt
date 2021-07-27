package com.enriquevid.weargame

public fun Point2BoxCollision(p1x:Int, p1y:Int, p2x:Int, p2y:Int, p2w:Int, p2h:Int, xMargin:Int, yMargin:Int):Boolean
{
    if(p1x > (p2x - xMargin) && p1x < (p2x + p2w + xMargin) && p1y > (p2y - yMargin) && p2y < (p2y + p2h + yMargin)) return true
    return false
}