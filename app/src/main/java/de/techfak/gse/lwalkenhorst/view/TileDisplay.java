package de.techfak.gse.lwalkenhorst.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.widget.FrameLayout;
import android.widget.ImageView;

import de.techfak.gse.lwalkenhorst.model.TilePosition;

import java.util.ArrayList;
import java.util.List;

@SuppressLint("ViewConstructor")
public class TileDisplay extends FrameLayout {

    private final TilePosition position;
    private final ImageView crossImage;
    private final ImageView markImage;
    private final List<TileClickHandler> clickHandlers = new ArrayList<>();

    private boolean isCrossed = false;
    private boolean marked = false;

    public TileDisplay(final Context context, final TilePosition position, final ImageView tile, final ImageView crossImage, final ImageView markImage) {
        super(context);
        this.markImage = markImage;
        this.position = position;
        this.crossImage = crossImage;
        addView(tile);
        setOnClickListener(event -> clickHandlers.forEach(clickHandler -> clickHandler.handle(this)));
    }

    public void setMarked(final boolean marked) {
        if (isCrossed) {
            return;
        }
        if (marked) {
            addView(markImage);
            this.marked = true;
        } else {
            removeView(markImage);
            this.marked = false;
        }
    }

    public void cross() {
        if (isCrossed) {
            return;
        }
        isCrossed = true;
        removeView(markImage);
        addView(crossImage);
    }

    public void registerClickHandler(final TileClickHandler clickHandler) {
        this.clickHandlers.add(clickHandler);
    }

    public TilePosition getPosition() {
        return position;
    }

    public boolean isMarked() {
        return marked;
    }
}
