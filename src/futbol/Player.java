/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package futbol;

import com.jme3.animation.AnimChannel;
import com.jme3.animation.AnimControl;
import com.jme3.animation.AnimEventListener;
import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.material.RenderState.BlendMode;
import com.jme3.math.ColorRGBA;
import com.jme3.renderer.queue.RenderQueue.Bucket;
import com.jme3.renderer.queue.RenderQueue.ShadowMode;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.texture.Texture;

/**
 *
 * @author jpruiz84
 */
public class Player {

	public AnimChannel channel;
	AnimControl control;
	Spatial model1;
	Spatial model2;
	Spatial model3;
	Spatial model4;
	Spatial model5;
	Spatial model6;
	Spatial model7;
	Spatial model8;
	String directory = "Models/player4";
	String anim = "correr1";
	AnimEventListener listener;
	Node playerNode;

	AssetManager assetManager;

	Player(AssetManager _assetManager) {
		assetManager = _assetManager;
	}

	public void init(Node _rootNode, AnimEventListener _listener) {
		listener = _listener;

		playerNode = new Node("Player Node");

		model1 = (Spatial) assetManager.loadModel(directory + "/body.mesh.xml");
		model2 = (Spatial) assetManager.loadModel(directory + "/eyes.mesh.xml");
		model3 = (Spatial) assetManager.loadModel(directory + "/eyebrows.mesh.xml");
		model4 = (Spatial) assetManager.loadModel(directory + "/hair.mesh.xml");
		model5 = (Spatial) assetManager.loadModel(directory + "/shoes.mesh.xml");
		model6 = (Spatial) assetManager.loadModel(directory + "/shorts.mesh.xml");
		model7 = (Spatial) assetManager.loadModel(directory + "/tights.mesh.xml");
		model8 = (Spatial) assetManager.loadModel(directory + "/tshirt.mesh.xml");

		playerNode.attachChild(model1);
		playerNode.attachChild(model2);
		playerNode.attachChild(model3);
		playerNode.attachChild(model4);
		playerNode.attachChild(model5);
		playerNode.attachChild(model6);
		playerNode.attachChild(model7);
		playerNode.attachChild(model8);

		playerNode.scale(0.105f);

		playerNode.setLocalTranslation(0, 0, 0);
		_rootNode.attachChild(playerNode);

		anim = "arrancar";

		control = model1.getControl(AnimControl.class);
		control.addListener(listener);
		channel = control.createChannel();
		channel.setAnim(anim);

		control = model2.getControl(AnimControl.class);
		control.addListener(listener);
		channel = control.createChannel();
		channel.setAnim(anim);

		control = model3.getControl(AnimControl.class);
		control.addListener(listener);
		channel = control.createChannel();
		channel.setAnim(anim);

		control = model4.getControl(AnimControl.class);
		control.addListener(listener);
		channel = control.createChannel();
		channel.setAnim(anim);

		control = model5.getControl(AnimControl.class);
		control.addListener(listener);
		channel = control.createChannel();
		channel.setAnim(anim);

		control = model6.getControl(AnimControl.class);
		control.addListener(listener);
		channel = control.createChannel();
		channel.setAnim(anim);

		control = model7.getControl(AnimControl.class);
		control.addListener(listener);
		channel = control.createChannel();
		channel.setAnim(anim);

		control = model8.getControl(AnimControl.class);
		control.addListener(listener);
		channel = control.createChannel();
		channel.setAnim(anim);
		
	}

	void anim(String _anim, float speed) {
		anim = _anim;

		control = model1.getControl(AnimControl.class);
		channel = control.getChannel(0);
		channel.setAnim(anim);
		channel.setSpeed(speed);

		control = model2.getControl(AnimControl.class);
		channel = control.getChannel(0);
		channel.setAnim(anim);
		channel.setSpeed(speed);

		control = model3.getControl(AnimControl.class);
		channel = control.getChannel(0);
		channel.setAnim(anim);
		channel.setSpeed(speed);

		control = model4.getControl(AnimControl.class);
		channel = control.getChannel(0);
		channel.setAnim(anim);
		channel.setSpeed(speed);

		control = model5.getControl(AnimControl.class);
		channel = control.getChannel(0);
		channel.setAnim(anim);
		channel.setSpeed(speed);

		control = model6.getControl(AnimControl.class);
		channel = control.getChannel(0);
		channel.setAnim(anim);
		channel.setSpeed(speed);

		control = model7.getControl(AnimControl.class);
		channel = control.getChannel(0);
		channel.setAnim(anim);
		channel.setSpeed(speed);

		control = model8.getControl(AnimControl.class);
		channel = control.getChannel(0);
		channel.setAnim(anim);
		channel.setSpeed(speed);

	}

	void stop() {
		playerNode.setLocalTranslation(-10, 0, 0);
		anim("arrancar", 100);
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		playerNode.setLocalTranslation(0, 0, 0);
		
		


	}

	public void makeToonish(Spatial spatial) {
		if (spatial instanceof Node) {
			Node n = (Node) spatial;
			for (Spatial child : n.getChildren())
				makeToonish(child);
		} else if (spatial instanceof Geometry) {
			Geometry g = (Geometry) spatial;
			Material m = g.getMaterial();
			if (m.getMaterialDef().getName().equals("Phong Lighting")) {
				m.setBoolean("UseMaterialColors",true);
				m.setBoolean("UseAlpha",true);
				m.getAdditionalRenderState().setBlendMode(BlendMode.Alpha);
			}
		}
	}
}
