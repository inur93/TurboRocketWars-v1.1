package com.vormadal.turborocket;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.vormadal.turborocket.models.Map;
import com.vormadal.turborocket.models.Player;
import com.vormadal.turborocket.models.Ship;
import com.vormadal.turborocket.models.actors.ActorPlayer;
import com.vormadal.turborocket.models.ammo.Bullet;
import com.vormadal.turborocket.models.ammo.Cannon;
import com.vormadal.turborocket.models.ammo.Fragment;
import com.vormadal.turborocket.models.ammo.SeekerMissile;
import com.vormadal.turborocket.models.configs.MapConfig;
import com.vormadal.turborocket.screens.MenuScreen;
import com.vormadal.turborocket.utils.InputConfiguration;
import com.vormadal.turborocket.utils.InputConfiguration.InputType;
import com.vormadal.turborocket.utils.PropKeys;

import static com.vormadal.turborocket.utils.ConfigUtil.*;
import static com.vormadal.turborocket.utils.PropKeys.*;

public class TurboRocketWarsGame extends Game  {

	@Override
	public void create() {
		setScreen(new MenuScreen(this));
	}
		

	
	
	

	
}
