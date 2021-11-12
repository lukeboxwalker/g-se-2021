package de.techfak.gse.lwalkenhorst.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.widget.FrameLayout;
import android.widget.ImageView;

@SuppressLint("ViewConstructor")
public class PointDisplay extends FrameLayout {

    private final ImageView markedImage;
    private boolean marked = false;

    public PointDisplay(final Context context, final ImageView background, final ImageView markedImage) {
        super(context);
        this.addView(background);
        this.markedImage = markedImage;
    }

    public void mark() {
        if (!marked) {
            marked = true;
            this.addView(markedImage);
        }
    }
}
