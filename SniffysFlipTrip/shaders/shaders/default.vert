attribute vec4 a_position;
attribute vec2 a_texCoords;

uniform mat4 u_projectionViewMatrix;

varying vec2 v_texCoords;


void main() {
    v_texCoords = a_texCoords;
    gl_Position = a_position;
}
