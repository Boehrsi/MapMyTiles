package de.boehrsi.mapmytiles.core;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;
import de.boehrsi.mapmytiles.entities.BodyProvider;
import de.boehrsi.mapmytiles.entities.LocatedEntity;
import sun.security.provider.SHA;

import java.util.*;

class Entities {

    private List<LocatedEntity> list = new ArrayList<>();

    private HashMap<String, List<LocatedEntity>> mappedLists = new HashMap<>();

    void build(TiledMap tiledMap, String layerName, BodyProvider entity, int tileCountWidth, int tileCountHeight,
                   int tileSize, boolean centered) {
        TiledMapTileLayer layer = (TiledMapTileLayer) tiledMap.getLayers().get(layerName);
        for (int x = 0; x < tileCountWidth; x++) {
            for (int y = 0; y < tileCountHeight; y++) {
                if (layer.getCell(x, y) != null) {
                    int calculatedX = calcSize(x, tileSize, centered);
                    int calculatedY = calcSize(y, tileSize, centered);
                    buildEntity(layerName, entity, calculatedX, calculatedY);
                }
            }
        }
    }

    void add(World world) {
        for (LocatedEntity locatedEntity : list) {
            BodyProvider bodyProvider = locatedEntity.getEntity();
            bodyProvider.create(world, locatedEntity.getX(), locatedEntity.getY());
        }
    }

    private void buildEntity(String layerName, BodyProvider entity, int calculatedX, int calculatedY) {
        LocatedEntity locatedEntity = new LocatedEntity(calculatedX, calculatedY, entity, layerName);
        list.add(locatedEntity);
        List<LocatedEntity> tempList = mappedLists.get(layerName);
        if (tempList != null) {
            tempList.add(locatedEntity);
        } else {
            tempList = new ArrayList<>(Collections.singletonList(locatedEntity));
        }
        mappedLists.put(layerName, tempList);
    }

    void removeEntity(LocatedEntity locatedEntity) {
        String layerName = locatedEntity.getLayerName();
        list.remove(locatedEntity);
        List<LocatedEntity> tempList = mappedLists.get(layerName);
        tempList.remove(locatedEntity);
        mappedLists.put(layerName, tempList);
    }

    void removeLayer(String layerName) {
        List<LocatedEntity> tempList = mappedLists.get(layerName);
        list.removeAll(tempList);
        mappedLists.remove(layerName);
    }

    List<LocatedEntity> getList() {
        return list;
    }

    List<LocatedEntity> getList(String layerName) {
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
