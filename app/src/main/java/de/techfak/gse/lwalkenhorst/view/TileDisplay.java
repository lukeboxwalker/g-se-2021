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
    private final List<TileClickHandler> clickHandlers = new ArrayList<>();

    private boolean isCrossed = false;

    public TileDisplay(final Context context, final TilePosition position, final ImageView tile, final ImageView crossImage) {
        super(context);
        addView(tile);
        this.position = position;
        this.crossImage = crossImage;
        setOnClickListener(event -> clickHandlers.forEach(clickHandler -> clickHandler.handle(this)));
    }

    public void setCrossed(final boolean crossed) {
        if (crossed && !isCrossed) {
            addView(crossImage);
            isCrossed = true;
        } else if (!crossed) {
            removeView(crossImage);
            isCrossed = false;
        }
    }

    public void registerClickHandler(final TileClickHandler clickHandler) {
        this.clickHandlers.add(clickHandler);
    }

    public TilePosition getPosition() {
        return position;
    }
}
