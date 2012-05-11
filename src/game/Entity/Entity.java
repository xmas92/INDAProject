package game.Entity;

import java.awt.geom.Dimension2D;
import java.awt.geom.Point2D;

import game.Model;
import game.View;
import game.Controller.Controller;
import game.Event.EventCallback;
import game.Geometry.Rectangle;

public interface Entity extends Controller, Model, View, EventCallback {
	/**
	 * Get the position
	 * @return
	 */
	public Point2D position();
	/**
	 * Get the dimensions
	 * @return
	 */
	public Dimension2D dimension();
	/**
	 * Get the bounding collision box.
	 * @return
	 */
	public Rectangle collisionBox();
}
