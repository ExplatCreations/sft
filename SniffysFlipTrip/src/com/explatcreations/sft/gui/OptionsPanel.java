/*****************************************************************************
 * Copyright 2013, see AUTHORS file.
 *
 * This file is part of Sniffy's Flip Trip.
 *
 * Sniffy's Flip Trip is free software: you can redistribute it and/or
 * modify it under the terms of the GNU General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Sniffy's Flip Trip is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Sniffy's Flip Trip.
 * If not, see <http://www.gnu.org/licenses/>.
 ****************************************************************************/

package com.explatcreations.sft.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.explatcreations.sft.Game;
import com.explatcreations.sft.data.Point2i;
import com.explatcreations.sft.enums.MDisplayMode;
import com.explatcreations.sft.functions.IAction;
import com.explatcreations.sft.functions.IAction1;
import com.explatcreations.sft.functions.IFunction2;
import com.explatcreations.sft.graphics.MDisplay;
import com.explatcreations.sft.input.Controls;
import com.explatcreations.sft.modes.Slider;

import java.util.ArrayList;
import java.util.List;

/**
 * @author moopslc
 */
public class OptionsPanel extends Panel {
    private Panel topPanel;
    private ArrayList<Button> endingButtons = new ArrayList<Button>();
    private List<ISelectable> widgets = new ArrayList<ISelectable>();
    private ClickyCursor cursor;
    private IAction queuedAction;
    private static final int center = Game.RenderWidth/2;
    private static final int sliderX = center - getFrameWidth()/4 + 40;
    private static final int sliderY = 30;
    private static final int soundWidth = 30;
    private static final int ySpace = 22;

    public OptionsPanel(IAction unset) {
        super(unset);

        widgets.add(makeSfxSlider());
        widgets.add(makeMusicSider());
        widgets.add(makeDisplaySlider());
        if(EndingSlider.getUnlocked().size() >= 1) {
            widgets.add(makeEndingSlider());
        }
        widgets.add(new SaveDeleteButton(sliderX, sliderY + ySpace*4));
        widgets.add(makeControlsButton());
        widgets.add(makeQuitButton());
        widgets.add(makeBackButton());
        /*topPanel = new ControlsPanel(new IAction() {
            @Override
            public void eval() {
                topPanel = null;
            }
        });*/
        this.cursor = new ClickyCursor(widgets.size() - 1);
    }

    private ISelectable makeQuitButton() {
        final IAction quitPanel = new IAction() {
            @Override
            public void eval() {
                final IAction noFunction = new IAction() {
                    @Override
                    public void eval() {
                        topPanel = null;
                    }
                };
                final IAction yesFunction = new IAction() {
                    @Override
                    public void eval() {
                        Game.exit();
                    }
                };
                queuedAction = new IAction() {
                    @Override
                    public void eval() {
                        topPanel = new YesNoPanel("Quit to desktop?", yesFunction, noFunction);
                    }
                };
            }
        };
        return new PanelButton("Quit to desktop",  sliderX, sliderY + ySpace*6, quitPanel);
    }
    private ISelectable makeControlsButton() {
        final IAction unset = new IAction() {
            @Override
            public void eval() {
                topPanel = null;
            }
        };
        final IAction onPress = new IAction() {
            @Override
            public void eval() {
                topPanel = new ControlsPanel(unset);
            }
        };
        return new ControlsButton(sliderX, sliderY + ySpace*5, onPress);
    }

    private ISelectable makeBackButton() {
        final IAction onPress = new IAction() {
            @Override
            public void eval() {
                cancel();
            }
        };
        return new PanelButton("Back", sliderX, sliderY + ySpace*7, onPress);
    }

    private static ISelectable makeEndingSlider() {
        return new EndingSlider(sliderX, sliderY + ySpace*3);
    }

    private static final IFunction2<Integer, String> getVolumeString = new IFunction2<Integer, String>() {
        @Override
        public String apply(Integer input) {
            return String.format("%3d%%", input);
        }
    };

    private static ISelectable makeSfxSlider() {
        final IAction1<Integer> setSfx = new IAction1<Integer>() {
            @Override
            public void eval(Integer value) {
                final float newVolume = value / 100.0f;
                Game.audio.setSfxVolume(newVolume);
                Game.assets.ClackSound.play();
            }
        };

        final List<Integer> sfxOptions = Slider.getVolumeOptions();
        final int initialSfx = Slider.getIndex(sfxOptions, Game.settings.getIntSfxVolume());

        return new Slider<Integer>(sliderX, sliderY, "Sfx:", sfxOptions, setSfx, getVolumeString, initialSfx, false, soundWidth);

    }

    private static ISelectable makeMusicSider() {
        final IAction1<Integer> setMusic = new IAction1<Integer>() {
            @Override
            public void eval(Integer value) {
                final float newVolume = value / 100.0f;
                Game.audio.setMusicVolume(newVolume);
            }
        };
        final List<Integer> sfxOptions = Slider.getVolumeOptions();
        final int initialMusic = Slider.getIndex(sfxOptions, Game.settings.getIntMusicVolume());
        return new Slider<Integer>(sliderX, sliderY + ySpace, "Music:", sfxOptions, setMusic, getVolumeString, initialMusic, false, soundWidth);

    }

    private static ISelectable makeDisplaySlider() {
        final IAction1<MDisplayMode.DisplayType> setDisplay = new IAction1<MDisplayMode.DisplayType>() {
            @Override
            public void eval(MDisplayMode.DisplayType arg1) {
                final int width;
                final int height;
                if (arg1 == MDisplayMode.DisplayType.FullScreen ||  arg1 == MDisplayMode.DisplayType.WindowedFullScreen) {
                    final Graphics.DisplayMode desktopMode = Gdx.graphics.getDesktopDisplayMode();
                    width = desktopMode.width;
                    height = desktopMode.height;
                } else {
                    width = Game.WindowWidth;
                    height = Game.WindowHeight;
                }
                Game.settings.setDisplayMode(new MDisplayMode(width, height, arg1));
            }
        };
        final IFunction2<MDisplayMode.DisplayType, String> getDisplayString = new IFunction2<MDisplayMode.DisplayType, String>() {
            @Override
            public String apply(MDisplayMode.DisplayType input) {
                return input.toString();
            }
        };
        final List<MDisplayMode.DisplayType> displayTypes = Slider.getVideoOptions();
        final int initialDisplay = Slider.getIndex(displayTypes, Game.settings.getDisplayMode().type, 0);
        return new Slider<MDisplayMode.DisplayType>(sliderX, sliderY + ySpace*2, "Display Mode:", displayTypes, setDisplay, getDisplayString, initialDisplay, true, 130);
    }

    @Override
    protected void doUnset() {
        super.doUnset();
        Game.settings.setSfxVolume(Game.audio.getSfxVolume());
        Game.settings.setMusicVolume(Game.audio.getMusicVolume());
        Game.settings.flush();
        MDisplay.setDisplay(Game.settings.getDisplayMode());
    }

    @Override
    protected int getHeight() {
        return 210;
    }

    @Override
    protected int getWidth() {
        return super.getWidth() + 10;
    }

    @Override
    protected int getYOffset() {
        return -getHeight()/2 + 90;
    }

    @Override
    protected void updateNormal() {
        if (queuedAction != null) {
            queuedAction.eval();
            queuedAction = null;
            return;
        }
        if (topPanel != null) {
            topPanel.update();
            return;
        }


        if (Controls.Up.menuZip()) {
            cursor.decrement();
        } else if (Controls.Down.menuZip()) {
            cursor.increment();
        }

        widgets.get(cursor.getValue()).updateSelected();
        for (Button button:endingButtons) {
            button.updateSelected();
        }


    }

    @Override
    protected void drawSelf(Point2i offset) {
        for (int i = 0; i < widgets.size(); i += 1) {
            widgets.get(i).draw(offset, cursor.getValue() == i);
        }
        for (Button button:endingButtons) {
            button.draw(offset);
        }
        if (topPanel != null) {
            topPanel.draw();
        }
    }
}
