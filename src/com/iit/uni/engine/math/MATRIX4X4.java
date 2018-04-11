package com.iit.uni.engine.math;

public class MATRIX4X4 {

	private float[] matrix = new float[16];

	// member variables
	public float entries0;
	public float entries1;
	public float entries2;
	public float entries3;
	public float entries4;
	public float entries5;
	public float entries6;
	public float entries7;
	public float entries8;
	public float entries9;
	public float entries10;
	public float entries11;
	public float entries12;
	public float entries13;
	public float entries14;
	public float entries15;

	public MATRIX4X4() {
		identity();
	}

	public MATRIX4X4(float e0, float e1, float e2, float e3, float e4, float e5, float e6, float e7, float e8, float e9,
			float e10, float e11, float e12, float e13, float e14, float e15) {
		entries0 = e0;
		entries1 = e1;
		entries2 = e2;
		entries3 = e3;
		entries4 = e4;
		entries5 = e5;
		entries6 = e6;
		entries7 = e7;
		entries8 = e8;
		entries9 = e9;
		entries10 = e10;
		entries11 = e11;
		entries12 = e12;
		entries13 = e13;
		entries14 = e14;
		entries15 = e15;
	}

	public void identity() {
		entries0 = 1.0f;
		entries4 = 0.0f;
		entries8 = 0.0f;
		entries12 = 0.0f;
		entries1 = 0.0f;
		entries5 = 1.0f;
		entries9 = 0.0f;
		entries13 = 0.0f;
		entries2 = 0.0f;
		entries6 = 0.0f;
		entries10 = 1.0f;
		entries14 = 0.0f;
		entries3 = 0.0f;
		entries7 = 0.0f;
		entries11 = 0.0f;
		entries15 = 1.0f;
	}

	public void translateRotateYScale(Vector3D trans, float rotY, Vector3D scale) {
		float sin = (float) Math.sin(Math.toRadians(rotY));
		float cos = (float) Math.cos(Math.toRadians(rotY));

		entries0 = scale.x * cos;
		entries4 = 0.0f;
		entries8 = sin * scale.z;
		entries12 = trans.x;

		entries1 = 0.0f;
		entries5 = scale.y;
		entries9 = 0.0f;
		entries13 = trans.y;

		entries2 = -sin * scale.x;
		entries6 = 0.0f;
		entries10 = scale.z * cos;
		entries14 = trans.z;

		entries3 = 0.0f;
		entries7 = 0.0f;
		entries11 = 0.0f;
		entries15 = 1.0f;
	}

	public void translate(Vector3D trans) {
		identity();

		entries12 = trans.x;
		entries13 = trans.y;
		entries14 = trans.z;
	}

	public void rotate_x(float angle) {

		float s = (float) Math.sin(Math.toRadians(angle));
		float c = (float) Math.cos(Math.toRadians(angle));

		identity();

		entries5 = c;
		entries6 = s;
		entries9 = -s;
		entries10 = c;
	}

	public void rotate_y(float angle) {
		identity();
		// float s = (float) Math.toRadians(angle);
		// float c = (float) Math.toRadians(angle);

		float s = (float) Math.sin(Math.toRadians(angle));
		float c = (float) Math.cos(Math.toRadians(angle));

		entries0 = c;
		entries2 = -s;
		entries8 = s;
		entries10 = c;
	}

	public void rotate_z(float angle) {

		float s = (float) Math.sin(Math.toRadians(angle));
		float c = (float) Math.cos(Math.toRadians(angle));

		identity();

		entries0 = c;
		entries1 = s;
		entries4 = -s;
		entries5 = c;
	}

	public void scale(Vector3D scale) {
		identity();

		entries0 = scale.x;
		entries5 = scale.y;
		entries10 = scale.z;
	}

	public void transformPoint(Vector3D vec) {
		Vector3D v = vec;

		float x = v.x;
		float y = v.y;
		float z = v.z;

		v.x = x * entries0 + y * entries4 + z * entries8 + entries12;

		v.y = x * entries1 + y * entries5 + z * entries9 + entries13;

		v.z = x * entries2 + y * entries6 + z * entries10 + entries14;
	}

	public float[] getMatrix() {
		matrix[0] = entries0;
		matrix[1] = entries1;
		matrix[2] = entries2;
		matrix[3] = entries3;
		matrix[4] = entries4;
		matrix[5] = entries5;
		matrix[6] = entries6;
		matrix[7] = entries7;
		matrix[8] = entries8;
		matrix[9] = entries9;
		matrix[10] = entries10;
		matrix[11] = entries11;
		matrix[12] = entries12;
		matrix[13] = entries13;
		matrix[14] = entries14;
		matrix[15] = entries15;
		return matrix;
	}

	/*******************************************************************************************************************************************************************************
	 * Getting matrix inverse
	 * 
	 * @return
	 */
	public MATRIX4X4 getInverse() {
		float fA0 = entries0 * entries5 - entries1 * entries4;
		float fA1 = entries0 * entries6 - entries2 * entries4;
		float fA2 = entries0 * entries7 - entries3 * entries4;
		float fA3 = entries1 * entries6 - entries2 * entries5;
		float fA4 = entries1 * entries7 - entries3 * entries5;
		float fA5 = entries2 * entries7 - entries3 * entries6;
		float fB0 = entries8 * entries13 - entries9 * entries12;
		float fB1 = entries8 * entries14 - entries10 * entries12;
		float fB2 = entries8 * entries15 - entries11 * entries12;
		float fB3 = entries9 * entries14 - entries10 * entries13;
		float fB4 = entries9 * entries15 - entries11 * entries13;
		float fB5 = entries10 * entries15 - entries11 * entries14;

		float fDet = fA0 * fB5 - fA1 * fB4 + fA2 * fB3 + fA3 * fB2 - fA4 * fB1 + fA5 * fB0;
		if (Math.abs(fDet) <= 0.001f) {
			MATRIX4X4 ret = new MATRIX4X4();
			ret.identity();
			return ret;
		}

		MATRIX4X4 kInv = new MATRIX4X4();
		kInv.entries0 = +entries5 * fB5 - entries6 * fB4 + entries7 * fB3;
		kInv.entries4 = -entries4 * fB5 + entries6 * fB2 - entries7 * fB1;
		kInv.entries8 = +entries4 * fB4 - entries5 * fB2 + entries7 * fB0;
		kInv.entries12 = -entries4 * fB3 + entries5 * fB1 - entries6 * fB0;
		kInv.entries1 = -entries1 * fB5 + entries2 * fB4 - entries3 * fB3;
		kInv.entries5 = +entries0 * fB5 - entries2 * fB2 + entries3 * fB1;
		kInv.entries9 = -entries0 * fB4 + entries1 * fB2 - entries3 * fB0;
		kInv.entries13 = +entries0 * fB3 - entries1 * fB1 + entries2 * fB0;
		kInv.entries2 = +entries13 * fA5 - entries14 * fA4 + entries15 * fA3;
		kInv.entries6 = -entries12 * fA5 + entries14 * fA2 - entries15 * fA1;
		kInv.entries10 = +entries12 * fA4 - entries13 * fA2 + entries15 * fA0;
		kInv.entries14 = -entries12 * fA3 + entries13 * fA1 - entries14 * fA0;
		kInv.entries3 = -entries9 * fA5 + entries10 * fA4 - entries11 * fA3;
		kInv.entries7 = +entries8 * fA5 - entries10 * fA2 + entries11 * fA1;
		kInv.entries11 = -entries8 * fA4 + entries9 * fA2 - entries11 * fA0;
		kInv.entries15 = +entries8 * fA3 - entries9 * fA1 + entries10 * fA0;

		float fInvDet = 1.0f / fDet;
		kInv.entries0 *= fInvDet;
		kInv.entries1 *= fInvDet;
		kInv.entries2 *= fInvDet;
		kInv.entries3 *= fInvDet;
		kInv.entries4 *= fInvDet;
		kInv.entries5 *= fInvDet;
		kInv.entries6 *= fInvDet;
		kInv.entries7 *= fInvDet;
		kInv.entries8 *= fInvDet;
		kInv.entries9 *= fInvDet;
		kInv.entries10 *= fInvDet;
		kInv.entries11 *= fInvDet;
		kInv.entries12 *= fInvDet;
		kInv.entries13 *= fInvDet;
		kInv.entries14 *= fInvDet;
		kInv.entries15 *= fInvDet;

		return kInv;
	}

	public MATRIX4X4 mul(MATRIX4X4 rhs) {
		// Optimise for matrices in which bottom row is (0, 0, 0, 1) in both
		// matrices
		if (entries3 == 0.0f && entries7 == 0.0f && entries11 == 0.0f && entries15 == 1.0f && rhs.entries3 == 0.0f
				&& rhs.entries7 == 0.0f && rhs.entries11 == 0.0f && rhs.entries15 == 1.0f) {
			return new MATRIX4X4(entries0 * rhs.entries0 + entries4 * rhs.entries1 + entries8 * rhs.entries2,
					entries1 * rhs.entries0 + entries5 * rhs.entries1 + entries9 * rhs.entries2,
					entries2 * rhs.entries0 + entries6 * rhs.entries1 + entries10 * rhs.entries2, 0.0f,
					entries0 * rhs.entries4 + entries4 * rhs.entries5 + entries8 * rhs.entries6,
					entries1 * rhs.entries4 + entries5 * rhs.entries5 + entries9 * rhs.entries6,
					entries2 * rhs.entries4 + entries6 * rhs.entries5 + entries10 * rhs.entries6, 0.0f,
					entries0 * rhs.entries8 + entries4 * rhs.entries9 + entries8 * rhs.entries10,
					entries1 * rhs.entries8 + entries5 * rhs.entries9 + entries9 * rhs.entries10,
					entries2 * rhs.entries8 + entries6 * rhs.entries9 + entries10 * rhs.entries10, 0.0f,
					entries0 * rhs.entries12 + entries4 * rhs.entries13 + entries8 * rhs.entries14 + entries12,
					entries1 * rhs.entries12 + entries5 * rhs.entries13 + entries9 * rhs.entries14 + entries13,
					entries2 * rhs.entries12 + entries6 * rhs.entries13 + entries10 * rhs.entries14 + entries14, 1.0f);
		}

		// Optimise for when bottom row of 1st matrix is (0, 0, 0, 1)
		if (entries3 == 0.0f && entries7 == 0.0f && entries11 == 0.0f && entries15 == 1.0f) {
			return new MATRIX4X4(
					entries0 * rhs.entries0 + entries4 * rhs.entries1 + entries8 * rhs.entries2
							+ entries12 * rhs.entries3,
					entries1 * rhs.entries0 + entries5 * rhs.entries1 + entries9 * rhs.entries2
							+ entries13 * rhs.entries3,
					entries2 * rhs.entries0 + entries6 * rhs.entries1 + entries10 * rhs.entries2
							+ entries14 * rhs.entries3,
					rhs.entries3,
					entries0 * rhs.entries4 + entries4 * rhs.entries5 + entries8 * rhs.entries6
							+ entries12 * rhs.entries7,
					entries1 * rhs.entries4 + entries5 * rhs.entries5 + entries9 * rhs.entries6
							+ entries13 * rhs.entries7,
					entries2 * rhs.entries4 + entries6 * rhs.entries5 + entries10 * rhs.entries6
							+ entries14 * rhs.entries7,
					rhs.entries7,
					entries0 * rhs.entries8 + entries4 * rhs.entries9 + entries8 * rhs.entries10
							+ entries12 * rhs.entries11,
					entries1 * rhs.entries8 + entries5 * rhs.entries9 + entries9 * rhs.entries10
							+ entries13 * rhs.entries11,
					entries2 * rhs.entries8 + entries6 * rhs.entries9 + entries10 * rhs.entries10
							+ entries14 * rhs.entries11,
					rhs.entries11,
					entries0 * rhs.entries12 + entries4 * rhs.entries13 + entries8 * rhs.entries14
							+ entries12 * rhs.entries15,
					entries1 * rhs.entries12 + entries5 * rhs.entries13 + entries9 * rhs.entries14
							+ entries13 * rhs.entries15,
					entries2 * rhs.entries12 + entries6 * rhs.entries13 + entries10 * rhs.entries14
							+ entries14 * rhs.entries15,
					rhs.entries15);
		}

		// Optimise for when bottom row of 2nd matrix is (0, 0, 0, 1)
		if (rhs.entries3 == 0.0f && rhs.entries7 == 0.0f && rhs.entries11 == 0.0f && rhs.entries15 == 1.0f) {
			return new MATRIX4X4(entries0 * rhs.entries0 + entries4 * rhs.entries1 + entries8 * rhs.entries2,
					entries1 * rhs.entries0 + entries5 * rhs.entries1 + entries9 * rhs.entries2,
					entries2 * rhs.entries0 + entries6 * rhs.entries1 + entries10 * rhs.entries2,
					entries3 * rhs.entries0 + entries7 * rhs.entries1 + entries11 * rhs.entries2,
					entries0 * rhs.entries4 + entries4 * rhs.entries5 + entries8 * rhs.entries6,
					entries1 * rhs.entries4 + entries5 * rhs.entries5 + entries9 * rhs.entries6,
					entries2 * rhs.entries4 + entries6 * rhs.entries5 + entries10 * rhs.entries6,
					entries3 * rhs.entries4 + entries7 * rhs.entries5 + entries11 * rhs.entries6,
					entries0 * rhs.entries8 + entries4 * rhs.entries9 + entries8 * rhs.entries10,
					entries1 * rhs.entries8 + entries5 * rhs.entries9 + entries9 * rhs.entries10,
					entries2 * rhs.entries8 + entries6 * rhs.entries9 + entries10 * rhs.entries10,
					entries3 * rhs.entries8 + entries7 * rhs.entries9 + entries11 * rhs.entries10,
					entries0 * rhs.entries12 + entries4 * rhs.entries13 + entries8 * rhs.entries14 + entries12,
					entries1 * rhs.entries12 + entries5 * rhs.entries13 + entries9 * rhs.entries14 + entries13,
					entries2 * rhs.entries12 + entries6 * rhs.entries13 + entries10 * rhs.entries14 + entries14,
					entries3 * rhs.entries12 + entries7 * rhs.entries13 + entries11 * rhs.entries14 + entries15);
		}

		return new MATRIX4X4(
				entries0 * rhs.entries0 + entries4 * rhs.entries1 + entries8 * rhs.entries2 + entries12 * rhs.entries3,
				entries1 * rhs.entries0 + entries5 * rhs.entries1 + entries9 * rhs.entries2 + entries13 * rhs.entries3,
				entries2 * rhs.entries0 + entries6 * rhs.entries1 + entries10 * rhs.entries2 + entries14 * rhs.entries3,
				entries3 * rhs.entries0 + entries7 * rhs.entries1 + entries11 * rhs.entries2 + entries15 * rhs.entries3,
				entries0 * rhs.entries4 + entries4 * rhs.entries5 + entries8 * rhs.entries6 + entries12 * rhs.entries7,
				entries1 * rhs.entries4 + entries5 * rhs.entries5 + entries9 * rhs.entries6 + entries13 * rhs.entries7,
				entries2 * rhs.entries4 + entries6 * rhs.entries5 + entries10 * rhs.entries6 + entries14 * rhs.entries7,
				entries3 * rhs.entries4 + entries7 * rhs.entries5 + entries11 * rhs.entries6 + entries15 * rhs.entries7,
				entries0 * rhs.entries8 + entries4 * rhs.entries9 + entries8 * rhs.entries10
						+ entries12 * rhs.entries11,
				entries1 * rhs.entries8 + entries5 * rhs.entries9 + entries9 * rhs.entries10
						+ entries13 * rhs.entries11,
				entries2 * rhs.entries8 + entries6 * rhs.entries9 + entries10 * rhs.entries10
						+ entries14 * rhs.entries11,
				entries3 * rhs.entries8 + entries7 * rhs.entries9 + entries11 * rhs.entries10
						+ entries15 * rhs.entries11,
				entries0 * rhs.entries12 + entries4 * rhs.entries13 + entries8 * rhs.entries14
						+ entries12 * rhs.entries15,
				entries1 * rhs.entries12 + entries5 * rhs.entries13 + entries9 * rhs.entries14
						+ entries13 * rhs.entries15,
				entries2 * rhs.entries12 + entries6 * rhs.entries13 + entries10 * rhs.entries14
						+ entries14 * rhs.entries15,
				entries3 * rhs.entries12 + entries7 * rhs.entries13 + entries11 * rhs.entries14
						+ entries15 * rhs.entries15);
	}

	public Vector3D mul(Vector3D rhs) {
		// Optimise for matrices in which bottom row is (0, 0, 0, 1)
		if (entries3 == 0.0f && entries7 == 0.0f && entries11 == 0.0f && entries15 == 1.0f) {
			return new Vector3D(entries0 * rhs.x + entries4 * rhs.y + entries8 * rhs.z + entries12 * 1.0f,

					entries1 * rhs.x + entries5 * rhs.y + entries9 * rhs.z + entries13 * 1.0f,

					entries2 * rhs.x + entries6 * rhs.y + entries10 * rhs.z + entries14 * 1.0f);
		}

		return new Vector3D(entries0 * rhs.x + entries4 * rhs.y + entries8 * rhs.z + entries12 * 1.0f,

				entries1 * rhs.x + entries5 * rhs.y + entries9 * rhs.z + entries13 * 1.0f,

				entries2 * rhs.x + entries6 * rhs.y + entries10 * rhs.z + entries14 * 1.0f);
		/*
		 * entries3*rhs.x + entries7*rhs.y + entries11*rhs.z + entries15*0.0f);
		 */

	}
}
