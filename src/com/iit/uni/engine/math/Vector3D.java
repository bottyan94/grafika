package com.iit.uni.engine.math;

/***********************************
 * This is our 3D point class. This will be used to store the vertices of our
 * model
 * 
 * @author Mileff Peter
 *
 ***********************************/
public class Vector3D {

	public float x;
	public float y;
	public float z;

	public static final String[] FIELD_NAMES = new String[] { "x", "y", "z" };

	public static final int SIZE = 12;

	public Vector3D() {
		this(0, 0, 0);
	}

	public Vector3D(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	/** Length of the vector */
	public float length() {
		return (float) Math.sqrt(x * x + y * y + z * z);
	}

	/** Normalize the vector */
	public void normalize() {
		float length = length();
		if (length == 0.0f) {
			x = 0.0f;
			y = 0.0f;
			z = 0.0f;
			return;
		}
		float length_reciprocal = 1.0f / length();
		x *= length_reciprocal;
		y *= length_reciprocal;
		z *= length_reciprocal;
	}

	// Here we overload the + operator so we can add vectors together
	public Vector3D add(Vector3D vVector) {
		// Return the added vectors result.
		return new Vector3D(vVector.x + x, vVector.y + y, vVector.z + z);
	}

	// Here we overload the + operator so we can add vectors together
	public void add_own(Vector3D vVector) {
		// Return the added vectors result.
		x += vVector.x;
		y += vVector.y;
		z += vVector.z;
	}

	// Here we overload the - operator so we can subtract vectors
	public Vector3D sub(Vector3D vVector) {
		// Return the subtracted vectors result
		return new Vector3D(x - vVector.x, y - vVector.y, z - vVector.z);
	}

	// Here we overload the + operator so we can add vectors together
	public void sub_own(Vector3D vVector) {
		// Return the added vectors result.
		x -= vVector.x;
		y -= vVector.y;
		z -= vVector.z;
	}

	// Here we overload the * operator so we can multiply by scalars
	public Vector3D mult(float num) {
		// Return the scaled vector
		return new Vector3D(x * num, y * num, z * num);
	}

	// Here we overload the * operator so we can multiply by scalars
	public Vector3D mult_v(Vector3D vec) {
		// Return the scaled vector
		return new Vector3D(x * vec.x, y * vec.y, z * vec.z);
	}

	// Here we overload the * operator so we can multiply by scalars
	public void mult_f_own(float num) {
		// Return the scaled vector
		x *= num;
		y *= num;
		z *= num;
	}

	// Here we overload the + operator so we can add vectors together
	public void mult_own(Vector3D vVector) {
		// Return the added vectors result.
		x *= vVector.x;
		y *= vVector.y;
		z *= vVector.z;
	}

	// Here we overload the / operator so we can divide by a scalar
	public Vector3D div(float num) {
		// Return the scale vector
		return new Vector3D(x / num, y / num, z / num);
	}

	// Here we overload the + operator so we can add vectors together
	public void div_own(Vector3D vVector) {
		// Return the added vectors result.
		x /= vVector.x;
		y /= vVector.y;
		z /= vVector.z;
	}

	public void div_f_own(float num) {
		x /= num;
		y /= num;
		z /= num;
	}

	public float dotProduct(Vector3D v) {
		return x * v.x + y * v.y + z * v.z;
	}

	public double magnitude() {
		return Math.sqrt(x * x + y * y + z * z);
	}

	public Vector3D cross(Vector3D v) {
		Vector3D result = new Vector3D();

		// Get the X value
		result.x = ((y * v.z) - (z * v.y));
		// Get the Y value
		result.y = ((z * v.x) - (x * v.z));
		// Get the Z value
		result.z = ((x * v.y) - (y * v.x));

		return result;
	}

	public void positive() {
		if (x < 0)
			x = -x;
		if (y < 0)
			y = -y;
		if (z < 0)
			z = -z;
	}

	public void copy(Vector3D vec) {
		this.x = vec.x;
		this.y = vec.y;
		this.z = vec.z;
	}

	public String[] getFieldNames() {
		return FIELD_NAMES;
	}

	public int getSize() {
		return SIZE;
	}

	public float getX() {
		return (x);
	}

	public float getY() {
		return (y);
	}

	public float getZ() {
		return (z);
	}

	public void setX(float x) {
		this.x = x;
	}

	public void setY(float y) {
		this.y = y;
	}

	public void setZ(float z) {
		this.z = z;
	}
}
