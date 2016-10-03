package de.boehrsi.mapmytiles.core;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.physics.box2d.World;
import de.boehrsi.mapmytiles.entities.StaticCollider;
import de.boehrsi.mapmytiles.entities.LocatedEntity;

import java.util.List;

/**
 * Main class for tile to java mapping. Gives you access to all created entities and colliders.
 */
@SuppressWarnings("unused")
public class TileMapper {
    private TiledMap tiledMap;
    private OrthogonalTiledMapRenderer tiledMapRenderer;
    private Colliders colliders = new Colliders();
    private Entities entities = new Entities();
    private int tileCountWidth;
    private int tileCountHeight;
    private int tileSize;
    private World world;

    /**
     * Initializes the {@link TileMapper}
     *
     * @param mapName         Name of the layer within the Tiled map
     * @param tileCountWidth  Number of tiles defining the width
     * @param tileCountHeight Number of tiles defining the height
     * @param tileSize        Width and height of one tile
     * @param scalePtm        Scale parameter for PTM (Pixel to Meter) conversion
     * @param world           Box2d world of the tilemap
     */
    public TileMapper(String mapName, int tileCountWidth, int tileCountHeight, int tileSize, float scalePtm, World world) {
        tiledMap = new TmxMapLoader().load(mapName);
        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap, scalePtm);
        initSizes(tileCountWidth, tileCountHeight, tileSize);
        this.world = world;
    }

    /**
     * Initializes the {@link TileMapper}
     *
     * @param tiledMapRenderer Already defined {@link OrthogonalTiledMapRenderer}
     * @param tileCountWidth   Number of tiles defining the width
     * @param tileCountHeight  Number of tiles defining the height
     * @param tileSize         Width and height of one tile
     * @param world            Box2d world of the tilemap
     */
    public TileMapper(OrthogonalTiledMapRenderer tiledMapRenderer, int tileCountWidth, int tileCountHeight,
                      int tileSize, World world) {
        tiledMap = tiledMapRenderer.getMap();
        this.tiledMapRenderer = tiledMapRenderer;
        initSizes(tileCountWidth, tileCountHeight, tileSize);
        this.world = world;
    }

    /**
     * Destroy the {@link TileMapper} content.
     */
    public void destroy() {
        tiledMap.dispose();
        tiledMapRenderer.dispose();
        colliders.destroy();
        entities.destroy();
    }

    /**
     * Calculates the colliders for the map. The colliders are represented as a {@link List} of {@link StaticCollider} objects.
     *
     * @param colliderLayerName Name of the layer within the Tiled map
     */
    public void buildColliders(String colliderLayerName) {
        colliders.create(tiledMap, colliderLayerName, tileCountWidth, tileCountHeight, world);
    }

    /**
     * Adds the colliders to the map. The colliders must have been build before by the  buildColliders(String colliderLayerName) method.
     */
    public void addColliders() {
        colliders.add();
    }

    /**
     * Add an entity layer to the map. This will create an object with position data, but without any information about the object which should get drawn within the game.
     * If this is needed use the addEntityLayer(T entity, String layerName, boolean centered) method. The entities are represented as a {@link List} of {@link LocatedEntity} objects.
     *
     * @param layerName Name of the layer within the Tiled map
     * @param centered  Should the object be located in the middle of the tile, otherwise it will be located in the bottom left
     */
    public void addEntityLayer(String layerName, boolean centered) {
        addEntityLayer(null, layerName, centered);
    }

    /**
     * Add an entity layer to the map. This method creates an object with position data and information about the to be drawn object.
     * This allows the management of in game objects directly by using the entities list from this class.
     * The entities are represented as a {@link List} of {@link LocatedEntity} objects.
     *
     * @param entity    Object that should get directly bound to the {@link LocatedEntity}
     * @param layerName Name of the layer within the Tiled map
     * @param centered  Should the object be located in the middle of the tile, otherwise it will be located in the bottom left
     * @param <T>       Type of entity
     */
    @SuppressWarnings("WeakerAccess")
    public <T> void addEntityLayer(T entity, String layerName, boolean centered) {
        entities.add(tiledMap, layerName, entity, tileCountWidth, tileCountHeight, tileSize, centered);
    }

    /**
     * Get the {@link OrthogonalTiledMapRenderer}
     *
     * @return The created or user given tiledMapRenderer
     */
    public OrthogonalTiledMapRenderer getTiledMapRenderer() {
        return tiledMapRenderer;
    }

    /**
     * Get the collider list
     *
     * @return The list of all created colliders.
     */
    public List<StaticCollider> getColliderList() {
        return colliders.getList();
    }

    /**
     * Get all entities
     *
     * @return The list of all added entities on all layers
     */
    public List<LocatedEntity<?>> getEntityList() {
        return entities.getList();
    }

    /**
     * Get all entities on a specific layer
     *
     * @param layerName The layer which should get checked
     * @return The list of all added entities on the specified layers
     */
    public List<LocatedEntity<?>> getEntityList(String layerName) {
        return entities.getList(layerName);
    }

    /**
     * Remove a layer of entities
     *
     * @param layerName The Layer which should get removed
     */
    public void removeLayer(String layerName) {
        entities.removeLayer(layerName);
    }

    /**
     * Remove an entity
     *
     * @param locatedEntity The entity which should get removed
     */
    public void removeEntity(LocatedEntity<?> locatedEntity) {
        entities.removeEntity(locatedEntity);
    }

    /**
     * Get a {@link TiledMapTileLayer}
     *
     * @param layerName The relevant layer
     * @return The specified layer
     */
    public TiledMapTileLayer getTileMapTileLayer(String layerName) {
        return (TiledMapTileLayer) tiledMap.getLayers().get(layerName);
    }

    private void initSizes(int tileCountWidth, int tileCountHeight, int tileSize) {
        this.tileCountWidth = tileCountWidth;
        this.tileCountHeight = tileCountHeight;
        this.tileSize = tileSize;
    }

}
