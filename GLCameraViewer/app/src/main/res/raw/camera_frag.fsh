#extension GL_OES_EGL_image_external:require

precision mediump float;
varying vec2 aCoord;
uniform samplerExternalOES vTexture;

void main(){
    //gl_FragColor = texture2D(vTexture, aCoord);
    //    vec4 rgba=texture2D(vTexture, aCoord);
    //    float c = rgba.r*0.3+rgba.g*0.59+rgba.b*0.11;
    //    gl_FragColor = vec4(c,c,c,rgba.a);
    float x = aCoord.x;
    if(x<0.5){
        x+=0.25;
    }else{
        x-=0.25;
    }
    gl_FragColor = texture2D(vTexture, vec2(x,aCoord.y));
}
