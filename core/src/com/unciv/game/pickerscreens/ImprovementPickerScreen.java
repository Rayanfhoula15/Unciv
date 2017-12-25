package com.unciv.game.pickerscreens;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.unciv.civinfo.TileInfo;
import com.unciv.game.UnCivGame;
import com.unciv.models.gamebasics.GameBasics;
import com.unciv.models.gamebasics.TileImprovement;

public class ImprovementPickerScreen extends PickerScreen {
    private String SelectedImprovement;

    public ImprovementPickerScreen(final TileInfo tileInfo) {
        rightSideButton.setText("Pick improvement");

        rightSideButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                tileInfo.startWorkingOnImprovement(SelectedImprovement);
                game.setWorldScreen();
                dispose();
            }
        });

        rightSideButton.setTouchable(Touchable.disabled);
        rightSideButton.setColor(Color.GRAY);

        VerticalGroup regularImprovements = new VerticalGroup();
        regularImprovements.space(10);
        for(final TileImprovement improvement : GameBasics.TileImprovements.values()) {
            if(!tileInfo.canBuildImprovement(improvement) || improvement.name.equals(tileInfo.improvement)) continue;
            TextButton TB = new TextButton(improvement.name +"\r\n"+improvement.getTurnsToBuild()+" turns", skin);

            TB.addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    SelectedImprovement = improvement.name;
                    rightSideButton.setTouchable(Touchable.enabled);
                    rightSideButton.setText(improvement.name);
                    rightSideButton.setColor(Color.WHITE);
                    descriptionLabel.setText(improvement.getDescription());
                }
            });
            regularImprovements.addActor(TB);
        }
        topTable.add(regularImprovements);
    }
}