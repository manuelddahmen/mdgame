package one.empty3.apps.mdgame;
import one.empty3.library.*;
import one.empty3.library.core.lighting.Colors;
import one.empty3.library.core.tribase.Plan3D;

import java.awt.*;

import static one.empty3.library.Point3D.*;

public class Decor1 extends RepresentableConteneur {
    private Plan3D sol;
    private Plan3D[] murs = new Plan3D[4];
    private Representable perso;
    private Representable bonus;
    private Representable ennemis;

    public Decor1() {
        sol = new Plan3D();
        sol.getP0().setElem(n(-1, 0, -1));
        sol.getvX().setElem(n(2, 0, 0));
        sol.getvY().setElem(n(0, 0, 2));
        int [] x = new int[] {-1, -1, 1, 1};
        int [] y = new int[] {-1, 1, 1, -1};
        int [] vx = new int[] {2, 0, -2, 0};
        int [] vy = new int[] {0, 2, 0, 2};
        for (int i = 0; i < murs.length; i++) {
            murs[i] = new Plan3D();
            murs[i].getP0().setElem(n(x[i], y[i], 0));
            murs[i].getvX().setElem(n(0, 2, 0));
            murs[i].getvX().setElem(n(vx[i], vy[i], 0));
        }
        perso = new Cube(0.02, n(0,0,0));

        bonus = new RepresentableConteneur();
        for(int i=0; i<10; i++) {
            Point3D point3D = random2(1.0);
            point3D.setY(0.0);
            Sphere sphere = new Sphere(point3D, 0.05);
            sphere.texture(new ColorTexture(Color.RED));
            ((RepresentableConteneur)bonus).add(sphere);
        }
        ennemis = new RepresentableConteneur();
        for (int i=0; i<10; i++) {
            Point3D point3D = random2(1.0);
            point3D.setY(0.0);
            Cube cube = new Cube(0.05, point3D);
            cube.texture(new TextureCol(Colors.random()));
            ((RepresentableConteneur) ennemis).add(cube);
        }
    }
    public void init() {
        for (Plan3D mur : murs) {
            add(mur);
        }
        add(sol);
        add(perso);
        add(bonus);
        add(ennemis);
    }
}
