package de.boehrsi.mapmytiles.core;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import de.boehrsi.mapmytiles.entities.ColliderBound;
import de.boehrsi.mapmytiles.entities.LocatedEntity;

import java.util.List;

@SuppressWarnings("unused")
public class MapMyTiles {
    private TiledMap tiledMap;
    private OrthogonalTiledMapRenderer tiledMapRenderer;
    private Colliders colliders = new Colliders();
    private Entities entities = new Entities();
    private int tileCountWidth;
    private int tileCountHeight;
    private int tileSize;

    public MapMyTiles(String mapName, int tileCountWidth, int tileCountHeight, int tileSize, float scalePtm) {
        tiledMap = new TmxMapLoader().load(mapName);
        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap, scalePtm);
        initSizes(tileCountWidth, tileCountHeight, tileSize);
    }

    public MapMyTiles(OrthogonalTiledMapRenderer tiledMapRenderer, int tileCountWidth, int tileCountHeight,
                      int tileSize) {
        tiledMap = tiledMapRenderer.getMap();
        this.tiledMapRenderer = tiledMapRenderer;
        initSizes(tileCountWidth, tileCountHeight, tileSize);
    }

    public void destroy() {
        tiledMap.dispose();
        tiledMapRenderer.dispose();
        colliders.destroy();
        entities.destroy();
    }

    public void buildColliders(String colliderLayerName) {
        colliders.create(tiledMap, colliderLayerName, tileCountWidth, tileCountHeight);
    }

    public void addEntityLayer(String layerName, boolean centered) {
        addEntityLayer(null, layerName, centered);
    }

    @SuppressWarnings("WeakerAccess")
    public <T> void addEntityLayer(T entity, String layerName, boolean centered) {
        entities.add(tiledMap, layerName, entity, tileCountWidth, tileCountHeight, tileSize, centered);
    }

    public OrthogonalTiledMapRenderer getTiledMapRenderer() {
        return tiledMapRenderer;
    }

    public List<ColliderBound> getColliderList() {
        return colliders.list;
    }

    public List<LocatedEntity<?>> getEntityList() {
        return entities.list;
    }

    public TiledMapTileLayer getTileMapTileLayer(String layerName) {
        return (TiledMapTileLayer) tiledMap.getLayers().get(layerName);
    }

    private void initSizes(int tileCountWidth, int tileCountHeight, int tileSize) {
        this.tileCountWidth = tileCountWidth;
        this.tileCountHeight = tileCountHeight;
        this.tileSize = tileSize;
    }

}
