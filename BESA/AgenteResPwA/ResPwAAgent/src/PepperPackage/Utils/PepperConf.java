/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PepperPackage.Utils;

/**
 *
 * @author juans
 */
public enum PepperConf {
    SPEED(1.0f,0.8f),PITCH(1.0f,1.0f),LEDINTENSITY(1,0.1f),TALKSPEED(100,50),DURATION(0.1f,1);

    private final float max,min;

    private PepperConf(float max,float min){
        this.max=max;
        this.min=min;
    }
    
    public float getMax(){
        return max;
    }
 
    
    public float getMin(){
        return min;
    }
}
