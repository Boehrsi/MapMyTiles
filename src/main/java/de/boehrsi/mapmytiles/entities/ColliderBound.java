package de.boehrsi.mapmytiles.entities;

public class ColliderBound {
    private int x;
    private int y;
    private int width;
    private int height;

    /**
     * Initializes a {@link ColliderBound} object
     *
     * @param x      Tile coordinates for the horizontal position
     * @param y      Tile coordinates for the vertical position
     * @param width  Width of the collider as tile size
     * @param height Height of the collider as tile size
     */
    public ColliderBound(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    /**
     * Get the tile coordinates for the horizontal position
     *
     * @return The x value
     */
    public int getX() {
        return x;
    }

    /**
     * Set the horizontal position as tile coordinates
     *
     * @param x Tile coordinates for the horizontal position
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Get the tile coordinates for the vertical position
     *
     * @return The y value
     */
    public int getY() {
        return y;
    }

    /**
     * Set the vertical position as tile coordinates
     *
     * @param y Tile coordinates for the vertical position
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * Get the tile size for the width
     *
     * @return The width value
     */
    public int getWidth() {
        return width;
    }

    /**
     * Set the width as tile size
     *
     * @param width Width of the collider
     */
    public void setWidth(int width) {
        this.width = width;
    }

    /**
     * Get the tile size for the height
     *
     * @return The height value
     */
    public int getHeight() {
        return height;
    }

    /**
     * Set the height as tile size
     *
     * @param height Height of the collider
     */
    public void setHeight(int height) {
        this.height = height;
    }

    /**
     * Increment the width of a collider
     */
    public void incrementWidth() {
        width++;
    }

    /**
     * Increment the height of a collider
     */
    public void incrementHeight() {
        height++;
    }

}