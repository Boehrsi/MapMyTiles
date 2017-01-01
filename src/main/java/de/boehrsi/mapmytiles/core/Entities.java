package de.boehrsi.mapmytiles.core;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.physics.box2d.World;
import de.boehrsi.mapmytiles.entities.BodyProvider;
import de.boehrsi.mapmytiles.entities.LocatedEntity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

class Entities {

    private List<LocatedEntity> list = new ArrayList<>();

    private HashMap<String, List<LocatedEntity>> mappedLists = new HashMap<>();

    void init(TiledMap tiledMap, BodyProvider entity, String layerName, int tileCountWidth, int tileCountHeight,
              int tileSize, boolean centered) {
        TiledMapTileLayer layer = (TiledMapTileLayer) tiledMap.getLayers().get(layerName);
        for (int x = 0; x < tileCountWidth; x++) {
            for (int y = 0; y < tileCountHeight; y++) {
                if (layer.getCell(x, y) != null) {
                    int calculatedX = calcSize(x, tileSize, centered);
                    int calculatedY = calcSize(y, tileSize, centered);
                    initEntity(layerName, entity, calculatedX, calculatedY);
                }
            }
        }
    }

    void create(World world) {
        for (LocatedEntity locatedEntity : list) {
            BodyProvider bodyProvider = locatedEntity.getBodyProvider();
            bodyProvider.create(world, locatedEntity.getX(), locatedEntity.getY());
        }
    }

    private void initEntity(String layerName, BodyProvider entity, int calculatedX, int calculatedY) {
        LocatedEntity locatedEntity = new LocatedEntity(calculatedX, calculatedY, entity.clone(), layerName);
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

    void destroyEntity(World world, LocatedEntity locatedEntity) {
        world.destroyBody(locatedEntity.getBodyProvider().getBody());
        removeEntity(locatedEntity);
    }

    void destroyLayer(World world, String layerName) {
        List<LocatedEntity> tempList = mappedLists.get(layerName);
        for(LocatedEntity tempEntity : tempList) {
            world.destroyBody(tempEntity.getBodyProvider().getBody());
        }
        removeLayer(layerName);
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

    void clear() {
        list.clear();
    }

}
