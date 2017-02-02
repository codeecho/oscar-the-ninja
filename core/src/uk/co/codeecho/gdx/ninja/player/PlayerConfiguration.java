package uk.co.codeecho.gdx.ninja.player;

public class PlayerConfiguration {

    private float speed = 8;
    private float acceleration = 30;
    private float jumpForce = 800;
    private float minJumpSpeed = 7;

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public float getAcceleration() {
        return acceleration;
    }

    public void setAcceleration(float acceleration) {
        this.acceleration = acceleration;
    }

    public float getJumpForce() {
        return jumpForce;
    }

    public void setJumpForce(float jumpForce) {
        this.jumpForce = jumpForce;
    }

    public float getMinJumpSpeed() {
        return minJumpSpeed;
    }

    public void setMinJumpSpeed(float minJumpSpeed) {
        this.minJumpSpeed = minJumpSpeed;
    }
    
    
    
}
