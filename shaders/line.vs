#version 330
#extension GL_ARB_explicit_attrib_location : enable

uniform mat4 modelMatrix;
uniform mat4 projectionMatrix;

uniform vec4 linecolor;

layout(location = 0) in vec3 position;

out vec4 color;

void main()
{
    color = linecolor;

    vec4 newpos = modelMatrix * vec4(position,1);
    gl_Position = projectionMatrix * newpos;
}
