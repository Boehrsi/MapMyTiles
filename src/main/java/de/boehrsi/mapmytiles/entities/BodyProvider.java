package de.boehrsi.mapmytiles.entities;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;

public abstract class BodyProvider implements Cloneable {

    private Body body;

    public Body getBody() {
        return body;
    }

    public void create(World world, int x, int y) {
        BodyDef bodyDef = getBodyDef();
        bodyDef.position.set(x, y);
        body = world.createBody(bodyDef);
        body.createFixture(getFixtureDef()).setUserData(getUserData());
    }

    public BodyProvider clone() {
        try {
            return (BodyProvider) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Defines the {@link BodyDef} of the object.
     *
     * @return BodyDef of the object
     */
    public abstract BodyDef getBodyDef();

    /**
     * Defines the {@link FixtureDef} of the object.
     *
     * @return FixtureDef of the object
     */
    public abstract FixtureDef getFixtureDef();

    /**
     * Defines the user data string of the object.
     *
     * @return User data string of the object
     */
    public abstract String getUserData();

}
