package futbol;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;

public class Target {

    Spatial model1;
    Node targetNode;
    
    public Material targetMaterial;
    
    AssetManager assetManager;

    Target(AssetManager _assetManager) {
        assetManager = _assetManager;
    }

    public void init(Node _rootNode) {
    	
    	targetNode = new Node("Target Node");
		model1 = (Spatial) assetManager.loadModel("Models/target/target.mesh.xml");

		targetMaterial = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
		targetMaterial.setColor("Color", new ColorRGBA(0.5f, 0.5f, 1f, 1f));
		
	    model1.setMaterial(targetMaterial);
		
		targetNode.attachChild(model1);

		targetNode.setLocalTranslation(0f, 0f, 4.5f);

		
		_rootNode.attachChild(targetNode);
    }
    
    public void move(Vector3f _position){
    	targetNode.setLocalTranslation(_position);
    }
    
}
