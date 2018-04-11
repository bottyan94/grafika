package com.iit.uni.engine.math;

// This is our 2D point class. This will be used to store the UV coordinates.
/**
 * Vector 2D Class
 * 
 * @author Mileff Peter
 *
 */
public class Vector2D {

	public float x;
	public float y;

	public static final int SIZE = 8;
	public static final String[] FIELD_NAMES = new String[] { "x", "y" };

	public Vector2D() {
		this(0, 0);
	}

	public Vector2D(float x, float y) {
		this.x = x;
		this.y = y;
	}

	public void copy(Vector2D vec) {
		this.x = vec.x;
		this.y = vec.y;
	}

	public void set(float x, float y) {
		this.x = x;
		this.y = y;
	}

	/** Length of the vector */
	public float length() {
		return (float) Math.sqrt(x * x + y * y);
	}

	/** Normalize the vector */
	public void normalize() {
		float length = length();
		if (length == 0.0f) {
			x = 0.0f;
			y = 0.0f;
			return;
		}
		float length_reciprocal = 1.0f / length();
		x *= length_reciprocal;
		y *= length_reciprocal;
	}

	// Here we overload the + operator so we can add vectors together
	public Vector2D add(Vector2D vVector) {
		// Return the added vectors result.
		return new Vector2D(vVector.x + x, vVector.y + y);
	}

	// Here we overload the + operator so we can add vectors together
	public void add_own(Vector2D vVector) {
		// Return the added vectors result.
		x += vVector.x;
		y += vVector.y;
	}

	// Here we overload the - operator so we can subtract vectors
	public Vector2D sub(Vector2D vVector) {
		// Return the subtracted vectors result
		return new Vector2D(x - vVector.x, y - vVector.y);
	}

	// Here we overload the + operator so we can add vectors together
	public void sub_own(Vector2D vVector) {
		// Return the added vectors result.
		x -= vVector.x;
		y -= vVector.y;
	}

	// Here we overload the * operator so we can multiply by scalars
	public Vector2D mult(float num) {
		// Return the scaled vector
		return new Vector2D(x * num, y * num);
	}

	// Here we overload the * operator so we can multiply by scalars
	public Vector2D mult_v(Vector2D vec) {
		// Return the scaled vector
		return new Vector2D(x * vec.x, y * vec.y);
	}

	// Here we overload the * operator so we can multiply by scalars
	public void mult_f_own(float num) {
		// Return the scaled vector
		x *= num;
		y *= num;
	}

	// Here we overload the + operator so we can add vectors together
	public void mult_own(Vector2D vVector) {
		// Return the added vectors result.
		x *= vVector.x;
		y *= vVector.y;
	}

	// Here we overload the / operator so we can divide by a scalar
	public Vector2D div(float num) {
		// Return the scale vector
		return new Vector2D(x / num, y / num);
	}

	// Here we overload the + operator so we can add vectors together
	public void div_own(Vector2D vVector) {
		// Return the added vectors result.
		x /= vVector.x;
		y /= vVector.y;
	}

	public void div_f_own(float num) {
		x /= num;
		y /= num;
	}

	public int getSize() {
		return SIZE;
	}

	public String[] getFieldNames() {
		return FIELD_NAMES;
	}

	public float getX() {
		return (x);
	}

	public float getY() {
		return (y);
	}

	public void setX(float x) {
		this.x = x;
	}

	public void setY(float y) {
		this.y = y;
	}
}
