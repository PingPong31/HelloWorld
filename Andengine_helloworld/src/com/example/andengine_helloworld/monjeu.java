package com.example.andengine_helloworld;

import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.sprite.Sprite;
import org.andengine.extension.physics.box2d.FixedStepPhysicsWorld;
import org.andengine.extension.physics.box2d.PhysicsConnector;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.extension.physics.box2d.util.Vector2Pool;
import org.andengine.extension.physics.box2d.util.constants.PhysicsConstants;
import org.andengine.util.math.MathUtils;

import android.util.Log;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;

public class monjeu extends MainActivity implements IUpdateHandler, Constantes {

	private static final FixtureDef PADDLE_FIXTUREDEF = PhysicsFactory.createFixtureDef(1, 1, 0);
	private static final FixtureDef BALL_FIXTUREDEF = PhysicsFactory.createFixtureDef(1, 1, 0);
	private static final FixtureDef WALL_FIXTUREDEF = PhysicsFactory.createFixtureDef(1, 1, 0);

	// ===========================================================
	// Fields
	// ===========================================================

	private final PhysicsWorld mPhysicsWorld;
	private Body mBallBody;
	
	 public monjeu(Rectangle face){
			this.mPhysicsWorld = new FixedStepPhysicsWorld(FPS, 2, new Vector2(0, 0), false, 8, 8);

			
	/* Ball */
	this.mBallBody = PhysicsFactory.createCircleBody(this.mPhysicsWorld, 0, 0, BALL_RADIUS, BodyType.DynamicBody, BALL_FIXTUREDEF);
	this.mBallBody.setBullet(true);
	this.mPhysicsWorld.registerPhysicsConnector(new PhysicsConnector(face, mBallBody, true, true));
	
	
	
	
	final Vector2 vector2 = Vector2Pool.obtain(0, 0);
	Log.d("Etiquette", "passage onUpdate PongServer");

	this.mBallBody.setTransform(vector2, 0);

	vector2.set(MathUtils.randomSign() * MathUtils.random(3, 4), MathUtils.randomSign() * MathUtils.random(3, 4));
	this.mBallBody.setLinearVelocity(vector2);
	Vector2Pool.recycle(vector2);


final Vector2 ballPosition = this.mBallBody.getPosition();
final float ballX = ballPosition.x * PhysicsConstants.PIXEL_TO_METER_RATIO_DEFAULT - BALL_RADIUS;
final float ballY = ballPosition.y * PhysicsConstants.PIXEL_TO_METER_RATIO_DEFAULT - BALL_RADIUS;
super.updateBall(ballX, ballY);
	 }
	 
	 
	 
	@Override
	public void onUpdate(float pSecondsElapsed) {
//		final Vector2 vector2 = Vector2Pool.obtain(0, 0);
//		Log.d("Etiquette", "passage onUpdate PongServer");
//
//		this.mBallBody.setTransform(vector2, 0);
//
//		vector2.set(MathUtils.randomSign() * MathUtils.random(3, 4), MathUtils.randomSign() * MathUtils.random(3, 4));
//		this.mBallBody.setLinearVelocity(vector2);
//		Vector2Pool.recycle(vector2);
//	
//	this.mPhysicsWorld.onUpdate(pSecondsElapsed);
//	
//	final Vector2 ballPosition = this.mBallBody.getPosition();
//	final float ballX = ballPosition.x * PhysicsConstants.PIXEL_TO_METER_RATIO_DEFAULT - BALL_RADIUS;
//	final float ballY = ballPosition.y * PhysicsConstants.PIXEL_TO_METER_RATIO_DEFAULT - BALL_RADIUS;
//	super.updateBall(ballX, ballY);
	
	}

	@Override
	public void reset() {
		// TODO Auto-generated method stub
		
	}

}
