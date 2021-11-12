package de.techfak.gse.lwalkenhorst.view;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;

import java.util.HashMap;
import java.util.Map;

public class TextureLoader {

    private static final Map<Integer, Bitmap> TEXTURES = new HashMap<>();
    private final View view;

    public TextureLoader(View view) {
        super();
        this.view = view;
    }

    public Bitmap loadTexture(final int id, final int width, final int height) {
        if (TEXTURES.containsKey(id)) {
            return TEXTURES.get(id);
        }
        Bitmap bitmap = BitmapFactory.decodeResource(view.getResources(), id);
        bitmap = Bitmap.createScaledBitmap(bitmap, width, height, true);
        TEXTURES.put(id, bitmap);
        return bitmap;
    }

    public Bitmap loadTexture(final int id, final int size) {
        return loadTexture(id, size, size);
    }
}
