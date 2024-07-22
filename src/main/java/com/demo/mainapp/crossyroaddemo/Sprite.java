package com.demo.mainapp.crossyroaddemo;//Class to draw graphics.

import java.awt.*;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.texture.Texture;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;

//Class to make rectangles for collision method.
//Class to set sprite image.
//Class for graphics.


/*
 * Class that creates a sprite with an image, location,
 * directional movement and a collision method.
 */
class Sprite {
	// Sprite location
	private Point2D location;

	// Sprite direction
	private Point2D direction;

	// Holds the image of the sprite
	private Texture image;

	// Draw sprite image or not
	private boolean show = true;

	// Holds the image filename
	private String filename = "";

	// The FXGL entity representing this sprite
	private Entity entity;

	/**
	 * The default constructor.
	 */
	Sprite() {
		image = null;
		location = new Point2D(0, 0);
		direction = new Point2D(0, 0);
		entity = new Entity();
	}

	/**
	 * Constructor that sets the sprite
	 * image and location.
	 */
	public Sprite(String filename, int xloc, int yloc) {
		setImage(filename);
		location = new Point2D(xloc, yloc);
		entity = new Entity();
		entity.setPosition(location);
		entity.getViewComponent().addChild(image);
	}

	/**
	 * Constructor that takes the location
	 * as the argument.
	 */
	public Sprite(int xloc, int yloc) {
		location = new Point2D(xloc, yloc);
		entity = new Entity();
		entity.setPosition(location);
	}

	/**
	 * Constructor that takes an image filename as the argument.
	 */
	Sprite(String filename) {
		setImage(filename);
		location = new Point2D(0, 0);
		entity = new Entity();
		entity.setPosition(location);
		entity.getViewComponent().addChild(image);
	}


	/*
	 * Method to set the image variable.
	 */
	void setImage(String filename) {
		this.filename = filename;

		try {
			Image img = new Image(getClass().getResourceAsStream(filename));
			this.image = new Texture(img);
		} catch (Exception e) {
			image = null;
		}
	}

	/*
	 * Getters.
	 */
	//Get xloc.
	public int getXLoc() {
		return (int) location.getX();
	}

	/*
	 * Setters
	 */
	//Sets xloc.
	void setXLoc(int xloc) {
		location = new Point2D(xloc, location.getY());
		entity.setPosition(location);
	}

	//Get yloc.
	public int getYLoc() {
		return (int) location.getY();
	}

	//Sets yloc.
	public void setYLoc(int yloc) {
		location = new Point2D(location.getX(), yloc);
		entity.setPosition(location);
	}

	// Get xdir.
	public double getXDir() {
		return direction.getX();
	}

	// Sets xdir.
	public void setXDir(double xdir) {
		direction = new Point2D(xdir, direction.getY());
	}

	// Get ydir.
	public double getYDir() {
		return direction.getY();
	}

	// Sets ydir.
	public void setYDir(double ydir) {
		direction = new Point2D(direction.getX(), ydir);
	}

	//Get image filename.
	String getFileName() {
		return filename;
	}


	/*
	 * Moves character by adding the 
	 * direction to the location.
	 */
	void move() {
		location = location.add(direction);
		entity.setPosition(location);
	}

	//Return the width of the sprite
	//or 20 if the image is null.
	int getWidth() {
		if (image == null)
			return 20;
		else
			return (int) image.getWidth();
	}

	//Return the height of the sprite
	//or 20 if the image in null.
	int getHeight() {
		if (image == null)
			return 20;
		else
			return (int) image.getHeight();
	}
	/*
	 * Method to draw sprite onto JPanel.
	 */
	void paint() {
		if (show && image != null) {
			FXGL.getGameScene().addUINode(image);
			image.setTranslateX(location.getX());
			image.setTranslateY(location.getY());
		}
	}
	public void remove() {
		FXGL.getGameScene().removeUINode(image);
	}
}
