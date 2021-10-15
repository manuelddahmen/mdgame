package one.empty3.apps.mdgame;

import one.empty3.library.*;
import one.empty3.library.core.nurbs.ParametricSurface;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

public class DrawEmpty3Lib {
    private Camera camera;

    public DrawEmpty3Lib() {

    }
    public void setCameraPosition(Camera camera) {
        this.camera = camera;
        Point3D lookAt = camera.getLookat().moins(camera.getEye());
        Point3D positionPlayer = camera.getLookat().moins(camera.getEye());
        Point3D vertical = camera.getLookat().moins(camera.getEye());
        GL20.glLoadMatrixd(new double[]{lookAt.getX(), lookAt.getY(), lookAt.getZ(), 1.0,
                positionPlayer.getX(), positionPlayer.getY(), positionPlayer.getZ(), 1.0,
                vertical.getX(), vertical.getY(), vertical.getZ(), 1.0,
                0.0,0.0,0.0,1.0});
        GL20.glScalef(1200,900,100);
    }
    public void draw( Representable representable) {
        double [] colorAt = Lumiere.getDoubles(representable.texture().getColorAt(0.5, 0.5));
        GL20.glColor3d(colorAt[0],colorAt[1],colorAt[2]);
        if(representable instanceof TRI) {
            TRI tri = (TRI) representable;

            GL20.glBegin(GL11.GL_TRIANGLES);
            for(int i=0; i<3; i++) {
                GL20.glVertex3d(tri.getSommet().getElem(i).getX(),
                        tri.getSommet().getElem(i).getY(), tri.getSommet().getElem(i).getZ());
            }
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
