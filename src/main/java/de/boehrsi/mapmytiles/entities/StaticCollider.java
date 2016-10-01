package de.boehrsi.mapmytiles.entities;

import com.badlogic.gdx.physics.box2d.*;

public class StaticCollider {
    private int x;
    private int y;
    private int width;
    private int height;
    private Body body;
    private World world;

    /**
     * Initializes a {@link StaticCollider} object
     *
     * @param x      Tile coordinates for the horizontal position
     * @param y      Tile coordinates for the vertical position
     * @param width  Width of the collider as tile size
     * @param height Height of the collider as tile size
     * @param world  The world which the collider belongs to
     */
    public StaticCollider(int x, int y, int width, int height, World world) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.world = world;
    }

    private void createBody(boolean reset) {
        if (reset) {
            destroyBody();
        }
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(x, y);
        body = world.createBody(bodyDef);
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width / 2, height / 2);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        body.createFixture(fixtureDef).setUserData("staticCollider");
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

    /*
    * Creates the body and adds it to the world, if it wasn't created before.
    * The user data of the fixture is set to staticCollider
    */
    public void createBody() {
        createBody(false);
    }

    /*
    * Recreates the body and adds it to the world, if it was already created.
    * The user data of the fixture is set to staticCollider
    */
    public void recreateBody() {
        createBody(true);
    }

    /**
     * Destroys the body and removes it from the world
     */
    public void destroyBody() {
        world.destroyBody(body);
        body = null;
    }

}