package edu.csulb.com.arttherapy;



import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;


public class CanvasView extends View {

    private Paint paint = new Paint();
    Context ctx;
    private Path path = new Path();
    public CanvasView(Context context, AttributeSet attrs) {
        super(context, attrs);

        this.ctx = context;
        paint.setAntiAlias(true);
        paint.setStrokeWidth(5f);
        paint.setColor(Color.GREEN);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeJoin(Paint.Join.ROUND);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawPath(path, paint);
    }

    public boolean onTouchEvent(MotionEvent event) {
        // Get the coordinates of the touch event.
        float eventXLoc = event.getX();
        float eventYLoc = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                // Set a new starting point for drawing
                path.moveTo(eventXLoc, eventYLoc);
                return true;
            case MotionEvent.ACTION_MOVE:
                // Connect the points
                path.lineTo(eventXLoc, eventYLoc);
                break;
            default:
                return false;
        }

        // View repaint & calls onDraw
        invalidate();
        return true;
    }
}
