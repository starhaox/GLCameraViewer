#extension GL_OES_EGL_image_external:require

precision mediump float;
varying vec2 aCoord;
uniform samplerExternalOES vTexture;

void main(){
    gl_FragColor = texture2D(vTexture, aCoord);
//    float x = aCoord.x;
//    if(x<0.5){
//        x+=0.25;
//    }else{
//        x-=0.25;
//    }
//    gl_FragColor = texture2D(vTexture, vec2(x,aCoord.y));
}
