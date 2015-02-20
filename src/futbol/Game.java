package futbol;

import com.jme3.animation.AnimChannel;
import com.jme3.animation.AnimControl;
import com.jme3.animation.AnimEventListener;
import com.jme3.animation.LoopMode;
import com.jme3.app.SimpleApplication;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.light.AmbientLight;
import com.jme3.light.DirectionalLight;
import com.jme3.light.SpotLight;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Vector3f;
import com.jme3.post.FilterPostProcessor;
import com.jme3.post.ssao.SSAOFilter;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.shadow.DirectionalLightShadowFilter;
import com.jme3.shadow.DirectionalLightShadowRenderer;
import com.jme3.shadow.EdgeFilteringMode;
import com.jme3.shadow.PssmShadowRenderer;
import com.jme3.shadow.SpotLightShadowFilter;
import com.jme3.shadow.SpotLightShadowRenderer;

public class Game extends SimpleApplication implements AnimEventListener, ActionListener {
	Spatial tubo1;
	Spatial tubo2;
	Player player1;

	public Ball ball1;
	public Target target1;
	
	public boolean player1Init = false;
	public boolean target1Init = false;

	public boolean updateTubo = false;
	public boolean playerStop = false;
	public boolean updateTarget = false;
	public boolean greenTarget = false;
	public boolean blueTarget = false;
	

	public float playerPosX;
	int count = 0;

	@Override
	public void simpleInitApp() {
		player1 = new Player(assetManager);
		ball1 = new Ball(assetManager);
		target1 = new Target(assetManager);

		setDisplayFps(false);
		setDisplayStatView(false);

		// Background Color
		// viewPort.setBackgroundColor(new ColorRGBA(0.2f, 0.2f, 0.2f, 0));
		viewPort.setBackgroundColor(new ColorRGBA(0.15f, 0.15f, 0.15f, 0));

		// Init light
		initLight();

		// Init Scene
		initScene();

		// Set the camera
		flyCam.setMoveSpeed(2f);
		flyCam.setEnabled(false);

		if (FutbolMPanel.rdbtnJugadorVirtual.isSelected()) {
			// Init the player
			player1.init(rootNode, this);
			player1Init = true;
			target1Init = false;
		}

		if (FutbolMPanel.rdbtnTarget.isSelected()) {
			// Init Target
			target1.init(rootNode);
			player1Init = false;
			target1Init = true;

		}

		// Init Ball
		ball1.init(rootNode);

		// Init Tubo
		initTubo();

		inputManager.addListener(this, "correr1");
		inputManager.addMapping("correr1", new KeyTrigger(KeyInput.KEY_SPACE));

		cam.setLocation(new Vector3f(-2.5397944f, 1.0558032f, 4.5f));
		cam.lookAtDirection(new Vector3f(1, 0, 0), new Vector3f(0, 1, 0));

		// tubo2.setLocalTranslation(10,0,0);
		// make the cube appear in the scene
	}

	private void initLight() {
		/**
		 * A white ambient light source.
		 */
		AmbientLight ambient = new AmbientLight();
		ambient.setColor(ColorRGBA.White.mult(0.9f));
		rootNode.addLight(ambient);

		DirectionalLight dl = new DirectionalLight();
		dl.setDirection(new Vector3f(-0.1f, -0.7f, -1).normalizeLocal());
		dl.setColor(new ColorRGBA(1f, 1f, 1f, 1.0f));
		rootNode.addLight(dl);

		SpotLight spot;

		spot = new SpotLight();
		Vector3f lightTarget = new Vector3f(0f, 0f, 4.5f);

		spot.setSpotRange(300);
		spot.setSpotInnerAngle(5f * FastMath.DEG_TO_RAD);
		spot.setSpotOuterAngle(10 * FastMath.DEG_TO_RAD);
		spot.setPosition(new Vector3f(50f, 100f, 0f));
		spot.setDirection(lightTarget.subtract(spot.getPosition()));
		spot.setColor(ColorRGBA.White.mult(2));
		rootNode.addLight(spot);

		final SpotLightShadowRenderer slsr = new SpotLightShadowRenderer(assetManager, 512);
		slsr.setLight(spot);
		slsr.setShadowIntensity(0.5f);
		slsr.setEdgeFilteringMode(EdgeFilteringMode.PCFPOISSON);
		viewPort.addProcessor(slsr);

		SpotLightShadowFilter slsf = new SpotLightShadowFilter(assetManager, 512);
		slsf.setLight(spot);
		slsf.setShadowIntensity(0.5f);
		slsf.setEdgeFilteringMode(EdgeFilteringMode.PCFPOISSON);
		slsf.setEnabled(false);

		FilterPostProcessor fpp = new FilterPostProcessor(assetManager);
		fpp.addFilter(slsf);
		viewPort.addProcessor(fpp);

	}

	private void initScene() {
		Spatial sceneModel;
		sceneModel = assetManager.loadModel("Scenes/floor1.j3o");
		rootNode.attachChild(sceneModel);
	}

	public void onAnimCycleDone(AnimControl control, AnimChannel channel, String animName) {
		if (animName.equals("correr1")) {
			channel.setAnim("arrancar");
			channel.setLoopMode(LoopMode.DontLoop);
			channel.setSpeed(1f);
			ballTracking.shoted = false;
		}
	}

	public void onAnimChange(AnimControl control, AnimChannel channel, String animName) {
	}

	public void onAction(String binding, boolean value, float tpf) {
		if (binding.equals("correr1") && value) {
			/*
			 * if (!channel.getAnimationName().equals("Dodge")){
			 * channel.setAnim("Dodge", 0.50f);
			 * channel.setLoopMode(LoopMode.Cycle); channel.setSpeed(0.10f); }
			 */
			player1.anim("correr1", 1);

			System.out.println("pulsado");
			System.out.println("Direction: " + cam.getDirection());
			System.out.println("UP: " + cam.getUp());
			System.out.println("Location: " + cam.getLocation());

		}
	}

	private void initTubo() {
		Node tuboNode = new Node("Tubo Node");
		tubo1 = (Spatial) assetManager.loadModel("Models/tubo1/tubo1.mesh.xml");
		tuboNode.attachChild(tubo1);
		rootNode.attachChild(tuboNode);

		Node tuboNode2 = new Node("Tubo Node");
		tubo2 = (Spatial) assetManager.loadModel("Models/tubo1/tubo1.mesh.xml");
		tuboNode.attachChild(tubo2);
		rootNode.attachChild(tuboNode2);
		// tubo2.scale(.2f);
		tubo2.setLocalTranslation(0, 0, 9f);

	}

	public void setTubo(float xPos) {
		tubo2.setLocalTranslation(0, 0, xPos / 6f);
	}

	@Override
	public void simpleUpdate(float tpf) {

		if (player1Init) {
			playerPosX = this.player1.control.getSkeleton().getBone(0).getLocalPosition().getZ() * 0.105f;
		}

		if (updateTubo) {
			ball1.move((float) ballTracking.ballYpos, (float) ballTracking.ballZpos, (float) ballTracking.ballXpos);
			updateTubo = false;
		}

		if (playerStop) {
			player1.stop();
			playerStop = false;
		}

		if (updateTarget) {
			target1.move(FutbolMPanel.targetPosition);
			updateTarget = false;
		}
		
		if (greenTarget){
			target1.targetMaterial.setColor("Color", ColorRGBA.Green);
			greenTarget = false;
		}
		
		if (blueTarget){
			target1.targetMaterial.setColor("Color", new ColorRGBA(0.5f, 0.5f, 1f, 1f));
			blueTarget = false;
		}

		// System.out.println(cam.getLocation() + " ," + cam.getDirection());

	}
}
