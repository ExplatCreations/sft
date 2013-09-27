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

package com.explatcreations.sft;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.explatcreations.sft.audio.SftMusic;
import com.explatcreations.sft.audio.OverlapSfx;
import com.explatcreations.sft.audio.Sfx;
import com.explatcreations.sft.data.Recti;
import com.explatcreations.sft.graphics.AnimatedSprite;
import com.explatcreations.sft.graphics.SpriteBase;
import com.explatcreations.sft.graphics.StaticSprite;
import com.explatcreations.sft.tiles.Tile;

/**
 * @author moopslc
 */
public class Assets {

    public static TextureRegion makeTextureRegion(Texture t, int x, int y, int width, int height) {
        TextureRegion region = new TextureRegion(t, x, y, width, height);
        region.flip(false, true);
        return region;
    }

    public final Texture SplashPageGraphic = loadTexture("eclogo");

    public final Texture DPadClearGraphic = loadTexture("dpadclear");
    public final Texture DPadGraphic = loadTexture("dpad");

    public final Texture FlagGraphic = loadTexture("flag");

    public final Texture BatWingGraphic = loadTexture("batwing");
    public final Texture MouseCursor = loadTexture("wandcursor2");
    public final Texture JoyBorderGraphic = loadTexture("buttonclear");
    public final Texture JoyBorderParchmentGraphic = loadTexture("buttonparchment");

    public final Texture ArrowsGraphic = loadTexture("arrows");

    public final Texture FlipTileGraphic = loadTexture("fliptiles");
    public final Texture StarGraphic = loadTexture("tiletostar");
    public final Texture TrapdoorGraphic = loadTexture("trapdoors");
    public final Texture PitTileGraphic = loadTexture("pittile");
    public final Texture WaterTileGraphic = loadTexture("water");
    public final Texture IceSplashGraphic = loadTexture("icesplash");
    public final Texture IceBlockGraphic = loadTexture("iceblock");

    public final Texture IceFloorGraphic = loadTexture("icetile");

    public final Texture FloorTileGraphic = loadTexture("regulartile");

    public final Texture BreakableWallGraphic = loadTexture("breakableblock");
    public final Texture DarkBreakableWallGraphic = loadTexture("darkbreakableblock");

    public final Texture WallTileGraphic = loadTexture("blocks");
    public final Texture DarkWallTileGraphic = loadTexture("darkblocks");

    public final Texture RedDoorGraphic = loadTexture("reddoor");
    public final Texture YellowDoorGraphic = loadTexture("yellowdoor");
    public final Texture GreenDoorGraphic = loadTexture("greendoor");
    public final Texture BlueDoorGraphic = loadTexture("bluedoor");
            
    public final Texture LeverTileGraphic = loadTexture("lever");

    public final Texture RedKeyGraphic = loadTexture("redkey");
    public final Texture YellowKeyGraphic = loadTexture("yellowkey");
    public final Texture GreenKeyGraphic = loadTexture("greenkey");
    public final Texture BlueKeyGraphic = loadTexture("bluekey");

    public final Texture EmptyPickupGraphic = loadTexture("emptypickup");
    public final Texture FirePickupGraphic = loadTexture("firepickup");
    public final Texture WindPickupGraphic = loadTexture("windpickup");
    public final Texture EarthPickupGraphic = loadTexture("earthpickup");
    public final Texture IcePickupGraphic = loadTexture("icepickup");
    
    public final Texture MagicIcons = loadTexture("pickups");
    public final Texture KeyIcons = loadTexture("keyicon");

    public final Texture ShutterTileGraphic = loadTexture("itemframe");

    public final Texture TeleportersGraphic = loadTexture("teleporters");

    public final Texture TileShadowGraphic = loadTexture("wallshadow");

    public final Texture TitleLettering = loadTexture("title");
    public final Texture TitleScreenScene = loadTexture("titlescreen");
    public final Texture TowerGraphic = loadTexture("tower");
    public final Texture TowerShadowGraphic = loadTexture("towershadow");
    public final Texture TitleScreenSniffyLettering = loadTexture("titlesniffy");
    public final Texture BlueBits = loadTexture("titlebluebits");
    public final Texture YellowBits = loadTexture("titleyellowbits");

    public final Texture SkyStarGraphic = loadTexture("skystar");


    public final Texture PushBlockGraphic = loadTexture("pushableblock");
    public final Texture BallGraphic = loadTexture("ballroll");
    public final Texture PaintBallGraphic = loadTexture("paintballroll");

    public final Texture FireballGraphic = loadTexture("fireball");
    public final Texture WindBallGraphic = loadTexture("windmagic");
    public final Texture IceBallGraphic = loadTexture("iceball");

    public final Texture KeyBorderParchmentGraphic = loadTexture("9patchkeyparchment");

    public final Texture KeyBorderGraphic = loadTexture("9patchkeyclear");
    
    public final Texture PlayerWalkGraphic = loadTexture("maincharwalk");

    public final Texture Parchment16x16Graphic = loadTexture("parchment9patch16x16-2nd");
    
    public final Texture Parchment8x8CharredGraphic = loadTexture("charredparchment9patch8x8");
    public final Texture Parchment8x8Graphic = loadTexture("parchment9patch8x8");
    
    public final Texture MagicMenuGraphic = loadTexture("magicselect");
    public final Texture MagicCursorGraphic = loadTexture("magiccursor");

    public final Texture RightArrowGraphic = loadTexture("arrow");

    public final Texture CheckmarkGraphic = loadTexture("stagecheckmark");
    public final Texture GoldStarGraphic = loadTexture("stagegoldstar");
    public final Texture ExclamationGraphic = loadTexture("stageexclamation");

    public final Texture ReticleGraphic = loadTexture("reticle");

    public final Texture MuteGraphic = loadTexture("muteicon");

    public final Texture StumpGraphic = loadTexture("stump");

    public final Pixmap SmallIcon = loadPixmap("Icon16x16");
    public final Pixmap MediumIcon = loadPixmap("Icon32x32");
    public final Pixmap LargeIcon = loadPixmap("Icon128x128");


    public final String HowToPlayText = loadTextFile("howtoplay");
    public final String TeleportersText = loadTextFile("teleporters");
    public final String LeversText = loadTextFile("levers");
    public final String FrozenText = loadTextFile("frozen");
    public final String CracksText = loadTextFile("cracks");
    public final String FillerText = loadTextFile("filler");
    public final String PushablesText = loadTextFile("pushables");
    public final String FireMagicText = loadTextFile("magicfire");
    public final String EarthMagicText = loadTextFile("magicearth");
    public final String WindMagicText = loadTextFile("magicwind");
    public final String IceMagicText = loadTextFile("magicice");
    public final String DoorsText = loadTextFile("doors");
    public final String CreditsText = loadTextFile("credits");

    public final AnimatedSprite BlankSprite = makeBlankSprite();

    public final Sfx CollectSound = loadSfx("collect", 3);
    public final Sfx FlipOnSound = loadSfx("flipon", 3);
    public final Sfx FlipOffSound = loadSfx("flipoff", 3);
    public final Sfx TeleportSound = loadSfx("teleport", 3);
    public final Sfx TelefailSound = loadSfx("telefail", 3);
    public final Sfx Disintegrate2Sound = loadOverlapSfx("disintegrate2", 3);
    public final Sfx DoorunlockSound = loadSfx("doorunlock", 3);
    public final Sfx ButtonpushSound = loadSfx("buttonpush", 3);
    public final Sfx FireballcastSound = loadSfx("fireballcast", 3);
    public final Sfx FireballhitSound = loadSfx("fireballhit", 3);
    public final Sfx GrowblockSound = loadSfx("growblock", 3);
    public final Sfx UndoSound = loadSfx("undo", 3);
    public final Sfx StepSound = loadSfx("step", 3);
    public final Sfx MagicpickupSound = loadSfx("magicpickup", 3);
    public final Sfx WindmagiccastSound = loadSfx("windmagiccast", 3);
    public final Sfx WindmagichitSound = loadSfx("windmagichit", 3);
    public final Sfx IcemagiccastSound = loadSfx("icemagiccast", 3);
    public final Sfx IcemagichitSound = loadSfx("icemagichit", 3);
    public final Sfx IcefreezeSound = loadSfx("icefreeze", 3);
    public final Sfx RollSound = loadSfx("roll", 3);
    public final Sfx BallhitSound = loadSfx("ballhit", 3);
    public final Sfx IcecrackSound = loadSfx("icecrack", 3);
    public final Sfx SplashSound = loadSfx("splash", 3);
    public final Sfx ClickSound = loadSfx("click", 1);
    public final Sfx ClackSound = loadSfx("clack", 2);
    public final Sfx DeleteblipSound = loadSfx("deleteblip", 3);
    public final Sfx DeleteexplosionSound = loadSfx("deleteexplosion", 3);
    public final Sfx BloopSound = loadOverlapSfx("bloop", 3);
    public final Sfx TeleinSound = loadSfx("telein", 3);
    public final Sfx TeleoutSound = loadSfx("teleout", 3);
    public final Sfx NewnoteSound = loadSfx("newnote", 3);
    public final Sfx ButtonclickSound = loadSfx("buttonclick", 3);
    public final Sfx PageflipSound =loadSfx("pageflip", 0);
    public final Sfx PageflipoutSound = loadSfx("pageflipout", 0);
    public final Sfx MagicmenuselectSound = loadSfx("magicmenuselect", 3);
    public final Sfx ExplodefinalSound = loadSfx("explodefinal", 3);
    public final Sfx WiggleSound = loadOverlapSfx("wiggle", 3);
    public final Sfx MagicemptySound = loadSfx("magicempty", 3);
    public final Sfx ResetclickSound = loadSfx("resetclick", 3);
    public final Sfx ResetclackSound = loadSfx("resetclack", 3);
    public final Sfx LevelunlockSound = loadSfx("levelunlock", 3);

    public final SftMusic StampSound = loadMusic("stamp", false);
    public final SftMusic WinJingleMusic = loadMusic("WinJingle", false);
    public final SftMusic GoldJingleSound = loadMusic("GoldJingle", false);
    public final SftMusic LevelSelectBGMMusic = loadMusic("LevelSelectBGM", true);
    public final SftMusic TitleBGMMusic = loadMusic("TitleBGM", true);
    public final SftMusic EasyTileFlippinMusic = loadMusic("EasyTileFlippin", true);
    public final SftMusic ElevatedMusic = loadMusic("Elevated", true);
    public final SftMusic MidEndingMusic = loadMusic("JobWellDone", true);

    public final SftMusic GoodTimesMusic = loadMusic("GoodTimes", false);
    public final SftMusic TimpoLivesMusic = loadMusic("TimpoLives", false);

    public final BitmapFont Font = makeFont("8bitoperator", 11);

    private BitmapFont makeFont(String name, int size) {
        final String path = "fonts/" + name + ".ttf";
        final FreeTypeFontGenerator gen = new FreeTypeFontGenerator(Gdx.files.internal(path));
        return gen.generateFont(size, FreeTypeFontGenerator.DEFAULT_CHARS, true);
    }


    private SftMusic loadMusic(String name, boolean looping) {
        final String path = "music/" + name;
        try {
            final Music music = Gdx.app.getAudio().newMusic(Gdx.files.internal(path + ".ogg"));
            return new SftMusic(music, looping);
        } catch(Throwable t) {
            final Music music = Gdx.app.getAudio().newMusic(Gdx.files.internal(path + ".wav"));
            return new SftMusic(music, looping);
        }
    }

    private static Sfx loadSfx(String name, int maxFrequency) {
        final String path = "sfx/" + name + ".ogg";
        final Music music = Gdx.app.getAudio().newMusic(Gdx.files.internal(path));
        return new Sfx(music, maxFrequency, name);
    }

    private static OverlapSfx loadOverlapSfx(String name, int maxFrequency) {
        final String path = "sfx/" + name + ".ogg";
        final Sound sound = Gdx.app.getAudio().newSound(Gdx.files.internal(path));
        return new OverlapSfx(sound, maxFrequency);
    }

    public static SpriteBase makeArrowSprite(String name) {
        final int index;
        if (name.equals("Up")) {
            index = 0;
        } else if (name.equals("Down")) {
            index = 1;
        } else if (name.equals("Right")) {
            index = 2;
        } else {
            index = 3;
        }
        return makeSpriteFrame(Game.assets.ArrowsGraphic, 16, 16, index);
    }

    public static AnimatedSprite makeTileSprite(Texture texture) {
        final AnimatedSprite result = new AnimatedSprite(texture, Tile.Size, Tile.Size);
        final String dummy = "dummy123";
        result.addAnimation(dummy, 1, new int[]{0}, false);
        result.play(dummy);
        return result;
    }

    public static StaticSprite makeStaticSprite(Texture t, Recti rect) {
        TextureRegion region = makeTextureRegion(t, rect.x, rect.y, rect.width, rect.height);
        return new StaticSprite(region);
    }

    public static StaticSprite makeStaticSprite(Texture t) {
        TextureRegion region = makeTextureRegion(t, 0, 0, t.getWidth(), t.getHeight());
        return new StaticSprite(region);
    }

    public static AnimatedSprite makeTileFrame(Texture t, int frame) {
        final AnimatedSprite result = new AnimatedSprite(t, Tile.Size, Tile.Size);
        final String dummy = "dummy123";
        result.addAnimation(dummy, 1, new int[]{frame}, false);
        result.play(dummy);
        return result;
    }

    public static AnimatedSprite makeSpriteFrame(Texture t, int width, int height, int frame) {
        final AnimatedSprite result = new AnimatedSprite(t, width, height);
        final String dummy = "dummy123";
        result.addAnimation(dummy, 1, new int[]{frame}, false);
        result.play(dummy);
        return result;
    }


    private static Pixmap loadPixmap(String name) {
        return new Pixmap(Gdx.files.internal("sprites/" + name + ".png"));
    }

    private static Texture loadTexture(String name) {
        return new Texture(Gdx.files.internal("sprites/" + name + ".png"));
    }

    private static String loadTextFile(String name) {
        final String path = "notes/" + name + ".txt";
        return Gdx.files.internal(path).readString();
    }

    private static AnimatedSprite makeBlankSprite() {
        final Texture t = new Texture(1, 1, Pixmap.Format.RGBA8888);
        final String name = "play";
        final AnimatedSprite result = new AnimatedSprite(t, 1, 1);
        result.addAnimation(name, 1, new int[]{0}, false);
        result.play(name);
        return result;
    }
}
