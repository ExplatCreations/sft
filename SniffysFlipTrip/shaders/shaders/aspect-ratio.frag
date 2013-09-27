varying vec4 v_color;
varying vec2 v_texCoords;

uniform vec2 u_windowSize;
uniform vec2 u_renderSize;
uniform sampler2D u_texture;

uniform bool u_isWide;

void main() {
    vec2 ratios = u_windowSize/u_renderSize;
    float factor;
    if (u_isWide) {
        factor = ratios.y;
    } else {
        factor = ratios.x;
    }

    vec2 pos = (gl_FragCoord.xy - u_windowSize/2.0)/(factor*u_renderSize) + 0.5;

    vec4 centerColor = texture2D(u_texture, pos);
    float testCoord;
    if (u_isWide) {
        testCoord = pos.x;
    } else {
        testCoord = pos.y;
    }
    float amt = float(fract(testCoord) == testCoord);
    gl_FragColor = amt*centerColor;
}
