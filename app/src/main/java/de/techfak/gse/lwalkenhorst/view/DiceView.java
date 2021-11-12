package de.techfak.gse.lwalkenhorst.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.widget.ImageView;
import android.widget.LinearLayout;

import de.techfak.gse.lwalkenhorst.R;
import de.techfak.gse.lwalkenhorst.model.DiceColorFace;
import de.techfak.gse.lwalkenhorst.model.DiceNumberFace;
import de.techfak.gse.lwalkenhorst.model.DiceResult;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DiceView extends LinearLayout {

    private final List<ImageView> colorDice = new ArrayList<>();
    private final List<ImageView> numberDice = new ArrayList<>();

    private final Map<DiceColorFace, Bitmap> diceColorTextures = new HashMap<>();
    private final Map<DiceNumberFace, Bitmap> diceNumberTextures = new HashMap<>();

    public DiceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.setOrientation(HORIZONTAL);

        final LinearLayout colorCol = new LinearLayout(context);
        colorCol.setOrientation(VERTICAL);
        addView(colorCol);
        final LinearLayout numberCol = new LinearLayout(context);
        numberCol.setOrientation(VERTICAL);
        addView(numberCol);

        colorDice.add(new ImageView(context));
        colorDice.add(new ImageView(context));
        colorDice.add(new ImageView(context));

        colorDice.forEach(child -> {
            colorCol.addView(child);
            child.setPadding(2, 2, 2, 2);
        });

        numberDice.add(new ImageView(context));
        numberDice.add(new ImageView(context));
        numberDice.add(new ImageView(context));

        numberDice.forEach(child -> {
            numberCol.addView(child);
            child.setPadding(2, 2, 2, 2);
        });
    }

    public void updateDice(final DiceResult diceResult) {
        assert diceResult.getColors().size() == colorDice.size();
        List<DiceColorFace> colors = diceResult.getColors();
        for (int i = 0; i < colors.size(); i++) {
            DiceColorFace colorFace = colors.get(i);
            if (diceColorTextures.containsKey(colorFace)) {
                colorDice.get(i).setImageBitmap(diceColorTextures.get(colorFace));
            }
        }

        assert diceResult.getNumbers().size() == numberDice.size();
        List<DiceNumberFace> numbers = diceResult.getNumbers();
        for (int i = 0; i < numbers.size(); i++) {
            DiceNumberFace numberFace = numbers.get(i);
            if (diceNumberTextures.containsKey(numberFace)) {
                numberDice.get(i).setImageBitmap(diceNumberTextures.get(numberFace));
            }
        }
    }

    public void init(final DisplayMetrics metrics) {
        int size = metrics.widthPixels / 12;
        TextureLoader textureLoader = new TextureLoader(this);

        diceColorTextures.put(DiceColorFace.YELLOW, textureLoader.loadTexture(R.drawable.face_yellow, size));
        diceColorTextures.put(DiceColorFace.GREEN, textureLoader.loadTexture(R.drawable.face_green, size));
        diceColorTextures.put(DiceColorFace.RED, textureLoader.loadTexture(R.drawable.face_red, size));
        diceColorTextures.put(DiceColorFace.BLUE, textureLoader.loadTexture(R.drawable.face_blue, size));
        diceColorTextures.put(DiceColorFace.ORANGE, textureLoader.loadTexture(R.drawable.face_orange, size));
        diceColorTextures.put(DiceColorFace.JOKER, textureLoader.loadTexture(R.drawable.face_color_joker, size));

        diceNumberTextures.put(DiceNumberFace.ONE, textureLoader.loadTexture(R.drawable.face_one, size));
        diceNumberTextures.put(DiceNumberFace.TWO, textureLoader.loadTexture(R.drawable.face_two, size));
        diceNumberTextures.put(DiceNumberFace.THREE, textureLoader.loadTexture(R.drawable.face_three, size));
        diceNumberTextures.put(DiceNumberFace.FOUR, textureLoader.loadTexture(R.drawable.face_four, size));
        diceNumberTextures.put(DiceNumberFace.FIVE, textureLoader.loadTexture(R.drawable.face_five, size));
        diceNumberTextures.put(DiceNumberFace.JOKER, textureLoader.loadTexture(R.drawable.face_number_joker, size));
    }
}
