#version 330

// Interpolated values from the vertex shaders
in vec4 color;

out vec4 outputFragment;

void main()
{
    // Output color = color
    outputFragment = color;
}
