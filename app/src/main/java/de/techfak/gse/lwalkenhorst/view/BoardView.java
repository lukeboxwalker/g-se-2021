package de.techfak.gse.lwalkenhorst.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.techfak.gse.lwalkenhorst.R;
import de.techfak.gse.lwalkenhorst.model.Board;
import de.techfak.gse.lwalkenhorst.model.Bounds;
import de.techfak.gse.lwalkenhorst.model.RuleManager;
import de.techfak.gse.lwalkenhorst.model.TileColor;
import de.techfak.gse.lwalkenhorst.model.TilePosition;

public class BoardView extends LinearLayout {

    private final Map<TileColor, Bitmap> colors = new HashMap<>();
    private final TextureLoader textureLoader;

    private final Map<Integer, PointDisplay> columnMap = new HashMap<>();

    private final List<TileClickHandler> clickHandlers = new ArrayList<>();

    public BoardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.setOrientation(VERTICAL);
        textureLoader = new TextureLoader(this);
    }

    public void init(final Board board, final RuleManager manager, final DisplayMetrics metrics) {
        int size = metrics.widthPixels / 20;
        this.colors.put(TileColor.BLUE, textureLoader.loadTexture(R.drawable.blue, size));
        this.colors.put(TileColor.RED, textureLoader.loadTexture(R.drawable.red, size));
        this.colors.put(TileColor.GREEN, textureLoader.loadTexture(R.drawable.green, size));
        this.colors.put(TileColor.YELLOW, textureLoader.loadTexture(R.drawable.yellow, size));
        this.colors.put(TileColor.ORANGE, textureLoader.loadTexture(R.drawable.orange, size));


        Bitmap crossed = textureLoader.loadTexture(R.drawable.cross, size);
        Bitmap crossed_marked = textureLoader.loadTexture(R.drawable.cross_mark, size);
        Bitmap startCol = textureLoader.loadTexture(R.drawable.highlight, size);
        Bitmap header = textureLoader.loadTexture(R.drawable.board_header, size * 15, size);

        final Bounds bounds = board.getBounds();
        LinearLayout layoutRow = new LinearLayout(getContext());
        ImageView headerImage = new ImageView(getContext());
        headerImage.setImageBitmap(header);
        layoutRow.addView(headerImage);
        addView(layoutRow);


        for (int row = 0; row < bounds.getRows(); row++) {
            layoutRow = new LinearLayout(getContext());
            layoutRow.setOrientation(HORIZONTAL);
            addView(layoutRow);
            for (int col = 0; col < bounds.getColumns(); col++) {
                ImageView tileImage = new ImageView(getContext());
                tileImage.setImageBitmap(colors.get(board.getTileAt(row, col).getColor()));
                TilePosition position = new TilePosition(row, col);
                ImageView crossImage = new ImageView(getContext());
                crossImage.setImageBitmap(crossed);
                ImageView markImage = new ImageView(getContext());
                markImage.setImageBitmap(crossed_marked);
                TileDisplay tileDisplay = new TileDisplay(getContext(), position, tileImage, crossImage, markImage);
                if (board.getTileAt(row, col).isCrossed()) {
                    tileDisplay.cross();
                }
                tileDisplay.registerClickHandler(this::handle);
                if (board.getStartColumn() == col) {
                    ImageView startColImage = new ImageView(getContext());
                    startColImage.setImageBitmap(startCol);
                    tileDisplay.addView(startColImage);
                }
                layoutRow.addView(tileDisplay);
            }
        }

        layoutRow = new LinearLayout(getContext());
        layoutRow.setOrientation(HORIZONTAL);
        addView(layoutRow);

        for (int column = 0; column < board.getBounds().getColumns(); column++) {
            final ImageView markedImage = new ImageView(getContext());
            markedImage.setImageBitmap(textureLoader.loadTexture(R.drawable.mark_circle, size));
            final int pointsForColumn = manager.getPointsForCol(column);
            final ImageView pointImage = new ImageView(getContext());
            pointImage.setImageBitmap(getImageForColumn(pointsForColumn, size));
            final PointDisplay display = new PointDisplay(getContext(), pointImage, markedImage);
            columnMap.put(column, display);
            layoutRow.addView(display);
        }
    }

    public void markColumnAsFull(final int column) {
        if (columnMap.containsKey(column)) {
            PointDisplay pointDisplay = columnMap.get(column);
            assert pointDisplay != null;
            pointDisplay.mark();
        } else {
            throw new IllegalArgumentException();
        }
    }

    private Bitmap getImageForColumn(final int pointsForColumn, final int size) {
        switch (pointsForColumn) {
            case 1:
                return textureLoader.loadTexture(R.drawable.point_one, size);
            case 2:
                return textureLoader.loadTexture(R.drawable.point_two, size);
            case 3:
                return textureLoader.loadTexture(R.drawable.point_three, size);
            case 5:
                return textureLoader.loadTexture(R.drawable.point_five, size);
            default:
                throw new IllegalArgumentException();
        }
    }

    public void handle(final TileDisplay tileDisplay) {
        clickHandlers.forEach(clickHandler -> clickHandler.handle(tileDisplay));
    }

    public void registerClickHandler(final TileClickHandler clickHandler) {
        this.clickHandlers.add(clickHandler);
    }
}
