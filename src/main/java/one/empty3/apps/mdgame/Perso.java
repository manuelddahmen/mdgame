package one.empty3.apps.mdgame;

import one.empty3.library.*;

import javax.sound.midi.SysexMessage;

public class Perso {
    private Point3D position= new Point3D(0., 0., 0.);
    private Point3D speed= new Point3D(0., 0., 0.);
    private Point3D orientation = new Point3D(1., 0., 0.);
    //private Point3D acc;
    private Camera camera;
    private boolean dead = false;

    public Perso(Camera camera) {
        this.camera = camera;
    }

    public void right(double i) {

    }

    public void left(double v) {
    }

    public void dec(double v) {
        speed = speed.mult(0.5);
    }

    public void acc(double v) {
        speed = speed.mult(2);
    }

    public void life() throws InterruptedException {
        long timeElapsed = System.nanoTime();

        while(!dead) {
            long now = System.nanoTime();
            Thread.sleep(10);

            timeElapsed = System.nanoTime()-now;

            position = position.plus(speed.mult(timeElapsed/1E9));


        }
    }
}
