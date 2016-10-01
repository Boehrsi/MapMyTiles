package de.boehrsi.mapmytiles.core;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.physics.box2d.World;
import de.boehrsi.mapmytiles.entities.StaticCollider;

import java.util.ArrayList;
import java.util.List;

class Colliders {
    private List<StaticCollider> list = new ArrayList<>();

    private boolean foundCollider = false;

    void create(TiledMap tiledMap, String colliderLayerName, int width, int height, World world) {
        TiledMapTileLayer colliderLayer = (TiledMapTileLayer) tiledMap.getLayers().get(colliderLayerName);

        StaticCollider staticCollider = null;

        // Create collider list and connect x axis objects
        for (int countY = 0; countY <= height; countY++) {
            saveColliderPart(foundCollider, staticCollider);
            for (int countX = 0; countX <= width; countX++) {
                if (colliderLayer.getCell(countX, countY) != null) {
                    // Found new cell
                    if (!foundCollider) {
                        staticCollider = new StaticCollider(countX, countY, 1, 1, world);
                        foundCollider = true;
                    }
                    // Found next cell
                    else {
                        if (staticCollider != null) {
                            staticCollider.incrementWidth();
                        }
                    }
                } else {
                    saveColliderPart(foundCollider, staticCollider);
                }
            }
        }

        // Iterate through collider list and connect y axis objects
        if (list.size() > 1) {
            for (int index = 0; index < list.size(); index++) {
                for (int indexCompare = 0; indexCompare < list.size(); indexCompare++) {
                    if (!list.get(index).equals(list.get(indexCompare))
                            && list.get(index).getX() == list.get(indexCompare).getX()
                            && list.get(index).getWidth() == list.get(indexCompare).getWidth()
                            && (list.get(index).getY() + list.get(index).getHeight()) == list.get(indexCompare)
                            .getY()) {
                        // Increase Y size and remove merged index
                        list.get(index).incrementHeight();
                        list.remove(indexCompare);
                        // Compare edited entry
                        if (index > 0) {
                            index--;
                        }
                    }
                }
            }
        }
    }

    void add() {
        for (StaticCollider collider : list) {
            collider.createBody();
        }
    }

    void destroy() {
        for (StaticCollider collider : list) {
            collider.destroyBody();
        }
        list.clear();
    }

    List<StaticCollider> getList() {
        return list;
    }

    private void saveColliderPart(boolean foundCollider, StaticCollider locatedCollider) {
        if (foundCollider) {
            list.add(locatedCollider);
            this.foundCollider = false;
        }
    }
}
