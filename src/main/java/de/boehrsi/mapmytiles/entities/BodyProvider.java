package de.boehrsi.mapmytiles.entities;

import com.badlogic.gdx.physics.box2d.*;

public abstract class BodyProvider {

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
