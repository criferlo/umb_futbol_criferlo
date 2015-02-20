package futbol;

import com.jme3.asset.AssetManager;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;

public class Ball {


    Spatial model1;
    Spatial model2;
    Node ballnode;
    
    AssetManager assetManager;

    Ball(AssetManager _assetManager) {
        assetManager = _assetManager;
    }

    public void init(Node _rootNode) {
    	
		ballnode = new Node("Ball Node");
		model1 = (Spatial) assetManager.loadModel("Models/ball/ballBalck.mesh.xml");
		model2 = (Spatial) assetManager.loadModel("Models/ball/ballWithe.mesh.xml");
		ballnode.attachChild(model1);
		ballnode.attachChild(model2);

		ballnode.setLocalTranslation(0, 0, 9);

		
		_rootNode.attachChild(ballnode);
    }
    
    public void move(float x, float y, float z){
    	ballnode.setLocalTranslation(x, y, z);
    }
    
}
