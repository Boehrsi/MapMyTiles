package de.boehrsi.mapmytiles.entities;

/**
 * Entity with position information. If given by the user also the to be drawn object is referenced.
 */
public class LocatedEntity {
    private int x;
    private int y;
    private BodyProvider entity;
    private String layerName;

    /**
     * Initializes a {@link LocatedEntity} without information about the to be drawn object.
     *
     * @param x         World coordinates in pixel for the horizontal position
     * @param y         World coordinates in pixel for the vertical position
     * @param layerName Name of the layer within the Tiled map
     */
    public LocatedEntity(int x, int y, String layerName) {
        this(x, y, null, layerName);
    }

    /**
     * Initializes a {@link LocatedEntity} with information about the to be drawn object.
     *
     * @param x         World coordinates in pixel for the horizontal position
     * @param y         World coordinates in pixel for the vertical position
     * @param entity    The entity which should get drawn
     * @param layerName Name of the layer within the Tiled map
     */
    public LocatedEntity(int x, int y, BodyProvider entity, String layerName) {
        this.x = x;
        this.y = y;
        this.entity = entity;
        this.layerName = layerName;
    }

    /**
     * Get the x value / horizontal position
     *
     * @return The horizontal position
     */
    public int getX() {
        return x;
    }

    /**
     * Get the y value / vertical position
     *
     * @return The vertical position
     */
    public int getY() {
        return y;
    }

    /**
     * Get the entity which should get drawn.
     *
     * @return The entity
     */
    public BodyProvider getEntity() {
        return entity;
    }

    /**
     * Set the entity which should be drawn.
     *
     * @param entity The entity of a user specific type
     */
    public void setEntity(BodyProvider entity) {
        this.entity = entity;
    }

    /**
     * Get the layer name which the {@link LocatedEntity} belongs to.
     *
     * @return The layer name
     */
    public String getLayerName() {
        return layerName;
    }
}