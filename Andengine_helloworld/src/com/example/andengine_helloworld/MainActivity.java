package com.example.andengine_helloworld;

import org.andengine.opengl.vbo.VertexBufferObjectManager;

import org.andengine.engine.Engine;
import org.andengine.engine.LimitedFPSEngine;
import org.andengine.engine.camera.Camera;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.primitive.Line;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.text.Text;
import org.andengine.entity.util.FPSCounter;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;
import org.andengine.opengl.texture.ITexture;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.ui.activity.SimpleBaseGameActivity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.graphics.Color;
import android.util.Log;
import android.util.SparseArray;
import android.widget.Toast;

public class MainActivity extends SimpleBaseGameActivity implements Constantes, IOnSceneTouchListener {
	public static int i=0;
	private Camera camera;
	private static final int MON_DIALOG = 0;

	/////////////////////////////
	//Balle
	private Rectangle balle;
	//Joueurs
	private Rectangle raquette1;
	private Rectangle raquette2;
	/////////////////////////////
	
	/////////////////////////////
	//SparseArray - non utilisé pour le moment
	private final SparseArray<Rectangle> cartejeu = new SparseArray<Rectangle>();
	/////////////////////////////
	
	
	/////////////////////////////
	//Position centrée Y
	private float centerY;
	/////////////////////////////
	
	/////////////////////////////
	// Font
	private Font mFont;
	/////////////////////////////
	

	////////////////////////////
	
	
	@Override
	public EngineOptions onCreateEngineOptions() {
		camera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
		this.camera.setCenter(0,0);
	    EngineOptions engineOptions = new EngineOptions(true, ScreenOrientation.LANDSCAPE_FIXED, 
	    new RatioResolutionPolicy(CAMERA_WIDTH, CAMERA_HEIGHT), camera);
	    return engineOptions;		
	}

	@Override
	public Engine onCreateEngine(final EngineOptions pEngineOptions) {

		
		//Dialogue début
		this.showDialog(MON_DIALOG);
		//Permet de limiter les FPS à 30
		return new LimitedFPSEngine(pEngineOptions, FPS);
	}

	

	@Override
	protected void onCreateResources() {
		////////////////////////////////////////////
		//Initialisation de la police
		final ITexture scoreFontTexture = new BitmapTextureAtlas(this.getTextureManager(), 256, 256, TextureOptions.BILINEAR);
		FontFactory.setAssetBasePath("font/");
		this.mFont = FontFactory.createFromAsset(this.getFontManager(), scoreFontTexture, this.getAssets(), "Droid.ttf", 15, true, Color.WHITE);
		this.mFont.load();
		////////////////////////////////////////////
	}

	@Override
	protected Scene onCreateScene() {
		final VertexBufferObjectManager vertexBufferObjectManager = this.getVertexBufferObjectManager();

		Scene scene = new Scene();
		final FPSCounter fpsCounter = new FPSCounter();
		this.mEngine.registerUpdateHandler(fpsCounter);
		final Text fpsText = new Text(-CAMERA_WIDTH/2,-CAMERA_HEIGHT/2	, mFont, "FPS:", "FPS: XXXXX".length(),vertexBufferObjectManager);
		scene.attachChild(fpsText);
		scene.registerUpdateHandler(new TimerHandler(1 / 20.0f, true, new ITimerCallback()
		{
		    @Override
		    public void onTimePassed(final TimerHandler pTimerHandler)
		    {
		        fpsText.setText("FPS: " +  String.format("%.2f", fpsCounter.getFPS()));
			//	Log.d("Etiquette", "passage MainAct");

		   //     updateBall(10+i, 10);
		     //   i=i+1;
		        
		    }
		}));
		/////////////////////////////////////////////////////////////
		//INITIALISATIONS
		/////////////////////////////////////////////////////////////
		//Balle
		this.balle = new Rectangle(0-BALL_WIDTH/2, 0, BALL_WIDTH, BALL_HEIGHT, vertexBufferObjectManager);
		scene.attachChild(this.balle);
		
		//Raquette joueur 1
		this.raquette1 = new Rectangle(-CAMERA_WIDTH/2+CAMERA_WIDTH/50-RAQU_WIDTH, 0,RAQU_WIDTH,RAQU_HEIGH, vertexBufferObjectManager);
		scene.attachChild(raquette1);
		
		//Raquette joueur 2
		this.raquette2 = new Rectangle(CAMERA_WIDTH/2-CAMERA_WIDTH/50, 0,RAQU_WIDTH,RAQU_HEIGH, vertexBufferObjectManager);
		scene.attachChild(raquette2);
		
		//Dessin d'une ligne au milieu
		scene.attachChild(new Line(0,CAMERA_HEIGHT/2,0,-CAMERA_HEIGHT/2,2,vertexBufferObjectManager));
		///////////////////////////////////////////////////////////////////
		///////////////////////////////////////////////////////////////////
		
		
		scene.setOnSceneTouchListener(this);
	    scene.setBackground(new Background(0.09804f, 0.6274f, 0.8784f));
		return scene;
	}
	
	
	

	@Override
	public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent) {
		//Mouvement de la raquette 1
		raquette1.setPosition(-CAMERA_WIDTH/2+CAMERA_WIDTH/50-RAQU_WIDTH, pSceneTouchEvent.getY());
		return false;
	}

	
	//Fonction d'update de la position de la balle
	public void updateBall(final float pX, final float pY) {
		this.balle.setPosition(pX, pY);

	}
	
	
	public void initGame(){
		
		monjeu njeu = new monjeu(this.balle);
	}
	
	///***************************************************************
	@Override
	protected Dialog onCreateDialog(final int pID) {
		switch(pID) {
			case MON_DIALOG:
			return new AlertDialog.Builder(this)
			.setIcon(android.R.drawable.ic_dialog_info)
			.setTitle("Your Server-IP ...")
			.setCancelable(false)
			.setMessage("Message de test")
			.setPositiveButton(android.R.string.ok, null)
			.setNeutralButton("Server", new OnClickListener() {
					@Override
					public void onClick(final DialogInterface pDialog, final int pWhich) {
					//	MainActivity.this.initGame();
					}
				})
			.create();
			default:
				return super.onCreateDialog(pID);
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}

