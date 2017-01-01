package de.boehrsi.mapmytiles.core;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.physics.box2d.World;
import de.boehrsi.mapmytiles.entities.BodyProvider;
import de.boehrsi.mapmytiles.entities.LocatedEntity;
import de.boehrsi.mapmytiles.entities.StaticCollider;

import java.util.List;

/**
 * Main class for tile to java mapping. Gives you access to all created entities and colliders.
 */
public class TileMapper {
    private TiledMap tiledMap;
    private OrthogonalTiledMapRenderer tiledMapRenderer;
    private Colliders colliders = new Colliders();
    private Entities entities = new Entities();
    private int tileCountWidth;
    private int tileCountHeight;
    private int tileSize;
    private float scalePtm;
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
        this.scalePtm = scalePtm;
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
        entities.clear();
    }

    /**
     * Calculates the colliders for the map. The colliders are represented as a {@link List} of {@link StaticCollider} objects.
     *
     * @param colliderLayerName Name of the layer within the Tiled map
     */
    public void initColliders(String colliderLayerName) {
        colliders.create(tiledMap, colliderLayerName, tileCountWidth, tileCountHeight, world);
    }

    /**
     * Adds the colliders to the map. The colliders must have been init before by the initColliders(String colliderLayerName) method.
     */
    public void createColliders() {
        colliders.add(tileSize, scalePtm);
    }

    /**
     * Builds an entity layer with positions relative to the map. This will create an object with position data, but without any information about the object which should get drawn within the game.
     * If this is needed use the initEntityLayer(T entity, String layerName, boolean centered) method. The entities are represented as a {@link List} of {@link LocatedEntity} objects.
     *
     * @param layerName Name of the layer within the Tiled map
     * @param centered  Should the object be located in the middle of the tile, otherwise it will be located in the bottom left
     */
    public void initEntityLayer(String layerName, boolean centered) {
        initEntityLayer(null, layerName, centered);
    }

    /**
     * Builds an entity layer with positions relative to the map. This method creates an object with position data and information about the to be drawn object.
     * This allows the management of in game objects directly by using the entities list from this class.
     * The entities are represented as a {@link List} of {@link LocatedEntity} objects.
     *
     * @param entity    Object that should get directly bound to the {@link LocatedEntity}
     * @param layerName Name of the layer within the Tiled map
     * @param centered  Should the object be located in the middle of the tile, otherwise it will be located in the bottom left
     */
    @SuppressWarnings("WeakerAccess")
    public void initEntityLayer(BodyProvider entity, String layerName, boolean centered) {
        entities.init(tiledMap, entity, layerName, tileCountWidth, tileCountHeight, tileSize, centered);
    }

    /**
     * Adds the the objects to the world.
     *
     * @param world World which should be the parent of the entity.
     */
    public void createEntityLayer(World world) {
        entities.create(world);
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
    public List<LocatedEntity> getEntityList() {
        return entities.getList();
    }

    /**
     * Get all entities on a specific layer
     *
     * @param layerName The layer which should get checked
     * @return The list of all added entities on the specified layers
     */
    public List<LocatedEntity> getEntityList(String layerName) {
        return entities.getList(layerName);
    }

    /**
     * Removes a layer of entities, will not clear the Box2D objects on the map. For an intrusive object
     * removal use destroyLayer().
     *
     * @param layerName The Layer which should get removed
     */
    public void removeLayer(String layerName) {
        entities.removeLayer(layerName);
    }

    /**
     * Removes an entity, will not clear the Box2D object on the map. For an intrusive object
     * removal use destroyEntity().
     *
     * @param locatedEntity The entity which should get removed
     */
    public void removeEntity(LocatedEntity locatedEntity) {
        entities.removeEntity(locatedEntity);
    }

    /**
     * Removes and destroys a layer of entities, will also clear the Box2D objects on the map. For a non intrusive object
     * removal use removeLayer()
     *
     * @param layerName The Layer which should get removed and destroyed
     */
    public void destroyLayer(String layerName) {
        entities.destroyLayer(world, layerName);
    }

    /**
     * Removes and destroys an entity, will also clear the Box2D object on the map. For a non intrusive object
     * removal use removeEntity()
     *
     * @param locatedEntity The entity which should get removed and destroyed
     */
    public void destroyEntity(LocatedEntity locatedEntity) {
        entities.destroyEntity(world, locatedEntity);
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
