package de.boehrsi.mapmytiles.core;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import de.boehrsi.mapmytiles.entities.ColliderBound;

import java.util.ArrayList;
import java.util.List;

class Colliders {
    private List<ColliderBound> list = new ArrayList<>();

    private boolean foundCollider = false;

    void create(TiledMap tiledMap, String colliderLayerName, int width, int height) {
        TiledMapTileLayer colliderLayer = (TiledMapTileLayer) tiledMap.getLayers().get(colliderLayerName);

        ColliderBound colliderBound = null;

        // Create collider list and connect x axis objects
        for (int countY = 0; countY <= height; countY++) {
            saveColliderPart(foundCollider, colliderBound);
            for (int countX = 0; countX <= width; countX++) {
                if (colliderLayer.getCell(countX, countY) != null) {
                    // Found new cell
                    if (!foundCollider) {
                        colliderBound = new ColliderBound(countX, countY, 1, 1);
                        foundCollider = true;
                    }
                    // Found next cell
                    else {
                        if (colliderBound != null) {
                            colliderBound.incrementWidth();
                        }
                    }
                } else {
                    saveColliderPart(foundCollider, colliderBound);
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

    void destroy() {
        list.clear();
    }

    List<ColliderBound> getList() {
        return list;
    }

    private void saveColliderPart(boolean foundCollider, ColliderBound locatedCollider) {
        if (foundCollider) {
            list.add(locatedCollider);
            this.foundCollider = false;
        }
    }
}
