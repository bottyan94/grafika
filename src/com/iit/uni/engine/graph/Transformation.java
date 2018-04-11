package com.iit.uni.engine.graph;

import org.joml.Matrix4f;
import org.joml.Vector3f;

public class Transformation {

	private final Matrix4f projectionMatrix;

	private final Matrix4f worldMatrix;

	private final Matrix4f orthoMatrix;

	public Transformation() {
		worldMatrix = new Matrix4f();
		projectionMatrix = new Matrix4f();
		orthoMatrix = new Matrix4f();
	}

	public final Matrix4f getProjectionMatrix(float fov, float width, float height, float zNear, float zFar) {
		float aspectRatio = width / height;
		projectionMatrix.identity();
		projectionMatrix.perspective(fov, aspectRatio, zNear, zFar);
		return projectionMatrix;
	}

	public Matrix4f getWorldMatrix(Vector3f offset, Vector3f rotation, float scale) {
		worldMatrix.identity().translate(offset).rotateX((float) Math.toRadians(rotation.x))
				.rotateY((float) Math.toRadians(rotation.y)).rotateZ((float) Math.toRadians(rotation.z)).scale(scale);
		return worldMatrix;
	}

	public final Matrix4f getOrthoProjectionMatrix(float left, float right, float bottom, float top) {
		orthoMatrix.identity();
		orthoMatrix.setOrtho2D(left, right, bottom, top);
		return orthoMatrix;
	}
}
