package de.boehrsi.mapmytiles.core;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import de.boehrsi.mapmytiles.entities.LocatedEntity;

import java.util.*;

class Entities {

    private List<LocatedEntity<?>> list = new ArrayList<>();

    private HashMap<String, List<LocatedEntity<?>>> mappedLists = new HashMap<>();

    <T> void add(TiledMap tiledMap, String layerName, T entity, int tileCountWidth, int tileCountHeight,
                 int tileSize, boolean centered) {
        TiledMapTileLayer layer = (TiledMapTileLayer) tiledMap.getLayers().get(layerName);
        for (int x = 0; x < tileCountWidth; x++) {
            for (int y = 0; y < tileCountHeight; y++) {
                if (layer.getCell(x, y) != null) {
                    int calculatedX = calcSize(x, tileSize, centered);
                    int calculatedY = calcSize(y, tileSize, centered);
                    addEntity(layerName, entity, calculatedX, calculatedY);
                }
            }
        }
    }

    private <T> void addEntity(String layerName, T entity, int calculatedX, int calculatedY) {
        LocatedEntity<T> locatedEntity = new LocatedEntity<>(calculatedX, calculatedY, entity, layerName);
        list.add(locatedEntity);
        List<LocatedEntity<?>> tempList = mappedLists.get(layerName);
        if (tempList != null) {
            tempList.add(locatedEntity);
        } else {
            tempList = new ArrayList<>(Collections.singletonList(locatedEntity));
        }
        mappedLists.put(layerName, tempList);
    }

    void removeEntity(LocatedEntity<?> locatedEntity) {
        String layerName = locatedEntity.getLayerName();
        list.remove(locatedEntity);
        List<LocatedEntity<?>> tempList = mappedLists.get(layerName);
        tempList.remove(locatedEntity);
        mappedLists.put(layerName, tempList);
    }

    void removeLayer(String layerName) {
        List<LocatedEntity<?>> tempList = mappedLists.get(layerName);
        list.removeAll(tempList);
        mappedLists.remove(layerName);
    }

    List<LocatedEntity<?>> getList() {
        return list;
    }

    List<LocatedEntity<?>> getList(String layerName) {
        return mappedLists.get(layerName);
    }

    private int calcSize(int value, int tileSize, boolean centered) {
        if (centered) {
            return value * tileSize + (tileSize / 2);
        }
        return value * tileSize;
    }

    void destroy() {
        list.clear();
    }

}
