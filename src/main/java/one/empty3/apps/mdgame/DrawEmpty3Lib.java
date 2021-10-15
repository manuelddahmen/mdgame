package one.empty3.apps.mdgame;

import one.empty3.library.*;
import one.empty3.library.core.nurbs.ParametricSurface;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

public class DrawEmpty3Lib {
    private GLFW gl;

    public DrawEmpty3Lib() {

    }
    public void setGL(GLFW gl) {
        this.gl = gl;
    }
    public void draw(Representable representable) {
        if(representable instanceof TRI) {
            TRI tri = (TRI) representable;

            GL20.glBegin(GL11.GL_TRIANGLES);
            GL20.glVertex2f(200, 100);
            GL20.glVertex2f(300, 400);
            GL20.glVertex2f(100, 400);
            GL20.glEnd();

        } else if(representable instanceof RepresentableConteneur) {
            ((RepresentableConteneur)representable).getListRepresentable().forEach(representable1 -> draw(representable1));
        } else if(representable instanceof Cube){
            ((Cube)representable).generate().getTriangles().forEach(tri -> draw(tri));
        } else if(representable instanceof ParametricSurface) {
            ParametricSurface representable1 = (ParametricSurface) representable;
            for(double u = representable1.getStartU(); u<representable1.getEndU(); u+=representable1.getIncrU())
                for(double v = representable1.getStartV(); u<representable1.getEndV(); u+=representable1.getIncrV()) {
                    Polygon elementSurface = representable1.getElementSurface(u, representable1.getIncrU(), v, representable1.getIncrV());
                    draw(elementSurface);
                }
        } else if(representable instanceof Polygon) {
            StructureMatrix<Point3D> points = ((Polygon) representable).getPoints();
            Point3D isocentre = ((Polygon) representable).getIsocentre();
            for(int i=0; i<points.getData1d().size(); i++) {
                int IP1 = (i+1)%(points.getData1d().size());
                draw(new TRI(points.getElem(i), points.getElem(IP1), isocentre));
            }
        }
    }
}
