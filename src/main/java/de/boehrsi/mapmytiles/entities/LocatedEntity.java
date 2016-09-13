package de.boehrsi.mapmytiles.entities;

public class LocatedEntity<T> {
	private int x;
	private int y;
	private T entity;
	private String layerName;

	public LocatedEntity(int x, int y, String layerName) {
		this(x, y, null, layerName);
	}

	public LocatedEntity(int x, int y, T entity, String layerName) {
		this.x = x;
		this.y = y;
		this.entity = entity;
		this.layerName = layerName;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public void setEntity(T entity) {
		this.entity = entity;
	}

	public T getEntity() {
		return entity;
	}

	public String getLayerName() {
		return layerName;
	}
}