package de.boehrsi.mapmytiles.core;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import de.boehrsi.mapmytiles.entities.LocatedEntity;

import java.util.ArrayList;
import java.util.List;

class Entities {

    List<LocatedEntity<?>> list = new ArrayList<>();

    <T> void add(TiledMap tiledMap, String layerName, T entity, int tileCountWidth, int tileCountHeight,
                 int tileSize, boolean centered) {
        TiledMapTileLayer layer = (TiledMapTileLayer) tiledMap.getLayers().get(layerName);
        for (int x = 0; x < tileCountWidth; x++) {
            for (int y = 0; y < tileCountHeight; y++) {
                if (layer.getCell(x, y) != null) {
                    int calculatedX = calcSize(x, tileSize, centered);
                    int calculatedY = calcSize(y, tileSize, centered);
                    list.add(new LocatedEntity<>(calculatedX, calculatedY, entity, layerName));
                }
            }
        }
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
