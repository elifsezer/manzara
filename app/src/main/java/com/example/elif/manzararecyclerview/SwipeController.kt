package com.example.elif.manzararecyclerview

import android.annotation.SuppressLint
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper.Callback
import  android.support.v7.widget.helper.ItemTouchHelper.*
import android.view.MotionEvent
import android.view.View
import android.widget.Button

class SwipeController : Callback() {
    enum class ButtonsState {
        GONE,
        LEFT_VISIBLE,
        RIGHT_VISIBLE
    }

    var swipeBack: Boolean = false
    var buttonShowedState: ButtonsState = ButtonsState.GONE
    var buttonWidth: Double =300.00


    override fun getMovementFlags(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder): Int {
        return makeMovementFlags(0, LEFT or RIGHT)
    }

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        p2: RecyclerView.ViewHolder
    ): Boolean {
        return false
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, directon: Int) {

    }

    override fun convertToAbsoluteDirection(flags: Int, layoutDirection: Int): Int {
        if (swipeBack) {
            swipeBack = false
            return 0
        }
        return super.convertToAbsoluteDirection(flags, layoutDirection)
    }

    override fun onChildDraw(
        c: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) {
        if (actionState == ACTION_STATE_SWIPE) {
            setTouchListener(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
        }
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
    }


    private fun setTouchListener(c: Canvas, recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, dX: Float, dY: Float, actionState: Int, isCurrentlyActive: Boolean
    ) {
        recyclerView.setOnTouchListener(object: View.OnTouchListener{
            override fun onTouch(v: View?, event: MotionEvent?): Boolean {
               swipeBack= event?.action == MotionEvent.ACTION_CANCEL || event?.action == MotionEvent.ACTION_UP
                if (swipeBack)
                {
                    if (dX < -buttonWidth) buttonShowedState=ButtonsState.RIGHT_VISIBLE
                    else if (dX > buttonWidth) buttonShowedState=ButtonsState.LEFT_VISIBLE
                    if (buttonShowedState != ButtonsState.GONE)
                    {
                        setTouchListener(c,recyclerView,viewHolder, dX, dY, actionState, isCurrentlyActive)
                        setItemClickable(recyclerView,false)
                    }
                }
                return false
            }
        })
    }

    fun setTouchDownListener(c:Canvas,recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder,dX:Float,dY: Float,actionState: Int,isCurrentlyActive: Boolean)
    {
        recyclerView.setOnTouchListener(object: View.OnTouchListener{
            override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                if (event?.action == MotionEvent.ACTION_DOWN)
                {
                    setTouchListener(c,recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                }
                return false
            }
        })
    }

    fun setTouchUpListener(c:Canvas,recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder,dX:Float,dY: Float,actionState: Int,isCurrentlyActive: Boolean)
    {
        recyclerView.setOnTouchListener(object: View.OnTouchListener{
            override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                if (event?.action == MotionEvent.ACTION_UP)
                {
                    onChildDraw(c, recyclerView, viewHolder, 0F, dY, actionState, isCurrentlyActive)
                    recyclerView.setOnTouchListener(object: View.OnTouchListener{
                        override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                        return false
                        }
                    })
                }
                return false
                setItemClickable(recyclerView,true)
                swipeBack=false
                buttonShowedState=ButtonsState.GONE
            }
        })
    }

    fun setItemClickable(recyclerView: RecyclerView, isClickable:Boolean)
    {
        for (i in 0..recyclerView.childCount)
        {
            recyclerView.getChildAt(i).isClickable
        }
    }

    fun onChilDraw(c:Canvas,recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder,dX:Float,dY: Float,actionState: Int,isCurrentlyActive: Boolean)
    {
        drawButtons(c,viewHolder)
    }

    fun drawButtons(c:Canvas,viewHolder: RecyclerView.ViewHolder)
    {
       var buttonWidthWithoutPadding= buttonWidth-20
        var corners=16
        var itemView:View=viewHolder.itemView
        var p:Paint= Paint()

        var leftButton:RectF= RectF(itemView.left.toFloat(),itemView.top.toFloat(),itemView.left.toFloat() + buttonWidthWithoutPadding.toFloat(),
            itemView.bottom.toFloat())
        p.color=Color.BLUE
        c.drawRoundRect(leftButton,corners.toFloat(),corners.toFloat(),p)
        drawText("EDITLE",c,leftButton,p)



    }

    fun drawText(text:String,c:Canvas,button:RectF,p:Paint)
    {
        var textSize=60
        p.color=Color.WHITE
        p.isAntiAlias=true
        p.textSize=textSize.toFloat()

        var textWidth=p.measureText(text)
        c.drawText(text,button.centerX()-(textWidth/2),button.centerY()+(textSize/2),p)
    }



}
