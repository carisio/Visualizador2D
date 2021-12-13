package leandro.carisio.visualizador2d;

import java.awt.Graphics;
/*
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GraphicsConfiguration;

import javax.media.j3d.Alpha;
import javax.media.j3d.AmbientLight;
import javax.media.j3d.Appearance;
import javax.media.j3d.Background;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.Geometry;
import javax.media.j3d.Material;
import javax.media.j3d.PhysicalBody;
import javax.media.j3d.PhysicalEnvironment;
import javax.media.j3d.PointLight;
import javax.media.j3d.RotationInterpolator;
import javax.media.j3d.Shape3D;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.media.j3d.View;
import javax.media.j3d.ViewPlatform;
import javax.vecmath.Color3f;
import javax.vecmath.Point3d;
import javax.vecmath.Point3f;
import javax.vecmath.Vector3d;

import com.sun.j3d.utils.geometry.GeometryInfo;
import com.sun.j3d.utils.geometry.NormalGenerator;
import com.sun.j3d.utils.universe.SimpleUniverse;
*/
public class VisualizadorDeFuncaoDe2Variaveis extends Visualizador2D {
	private double x0, y0, x1, y1;
	private double[][] funcao;

	public VisualizadorDeFuncaoDe2Variaveis(double x0, double y0, double x1,
			double y1, double[][] funcao) {
		setFuncao(funcao);
		setX0(x0);
		setY0(y0);
		setX1(x1);
		setY1(y1);
		//init();
	}

	@Override
	public void paint(Graphics g) {
	}
/*
	public void init() {
		// create canvas
		GraphicsConfiguration gc = SimpleUniverse.getPreferredConfiguration();
		Canvas3D cv = new Canvas3D(gc);
		setLayout(new BorderLayout());
		add(cv, BorderLayout.CENTER);
		BranchGroup bg = createSceneGraph(cv);
		bg.compile();
		SimpleUniverse su = new SimpleUniverse(cv);
		su.getViewingPlatform().setNominalViewingTransform();
		su.addBranchGraph(bg);
	}

	public BranchGroup createSceneGraph(Canvas3D cv) {
		BranchGroup root = new BranchGroup();
		// View
		ViewPlatform platform = new ViewPlatform();
		root.addChild(platform);
		View view = new View();
		view.addCanvas3D(cv);
		view.setCompatibilityModeEnable(true);
		view.attachViewPlatform(platform);
		Transform3D projection = new Transform3D();
		projection.frustum(-0.1, 0.1, -0.1, 0.1, 0.2, 10);
		view.setLeftProjection(projection);
		Transform3D viewing = new Transform3D();
		Point3d eye = new Point3d(0, 0, 1);
		Point3d look = new Point3d(0, 0, -1);
		Vector3d up = new Vector3d(0, 1, 0);
		viewing.lookAt(eye, look, up);
		view.setVpcToEc(viewing);
		PhysicalBody body = new PhysicalBody();
		view.setPhysicalBody(body);
		PhysicalEnvironment env = new PhysicalEnvironment();
		view.setPhysicalEnvironment(env);
		
		// object
		Appearance ap = new Appearance();
		ap.setMaterial(new Material());
		Shape3D shape = new Shape3D(createGeometry(), ap);

		Transform3D tr = new Transform3D();
		tr.setScale(1000);
		tr.setTranslation(new Vector3d(0.1, 0.1, 0.0));
		TransformGroup tg = new TransformGroup(tr);
		tg.addChild(shape);
		root.addChild(tg);

		// background and light
		BoundingSphere bounds = new BoundingSphere();
		Background background = new Background(1.0f, 1.0f, 1.0f);
		background.setApplicationBounds(bounds);
		root.addChild(background);

		AmbientLight light = new AmbientLight(true, new Color3f(Color.green));
		light.setInfluencingBounds(bounds);
		root.addChild(light);

		PointLight ptlight = new PointLight(new Color3f(Color.red),
				new Point3f(3f, 3f, 3f), new Point3f(1f, 0f, 0f));
		ptlight.setInfluencingBounds(bounds);
		root.addChild(ptlight);
		return root;
	}

	public Geometry createGeometry() {
		double[][] f = getFuncao();
		int Nx = f.length;
		int Ny = f[0].length;
		double deltaX = (getX1() - getX0()) / Nx;
		double deltaY = (getY1() - getY0()) / Ny;
		Point3f[] pts = new Point3f[Nx * Ny];
		int idx = 0;
		for (int i = 0; i < Nx; i++) {
			for (int j = 0; j < Ny; j++) {
				float x = (float) (getX0() + i * deltaX);
				float y = (float) (getY0() + j * deltaY);
				float z = (float) (f[i][j]);
				pts[idx++] = new Point3f(x, y, z);
				System.out.println(x + ",,,,,,,,," + y + ",,,,,,,," + z);

				// float x = (i - Nx/2)*0.2f;
				// float z = (j - Ny/2)*0.2f;
				// float y = 2f * (float)(Math.cos(x*x) * Math.sin(z*z))/
				// ((float)Math.exp(0.25*(x*x+z*z)))-1.0f;
				// pts[idx++] = new Point3f(x, y, z);

			}
		}

		int[] coords = new int[2 * Ny * (Nx - 1)];
		idx = 0;
		for (int i = 1; i < Nx; i++) {
			for (int j = 0; j < Ny; j++) {
				coords[idx++] = i * Ny + j;
				coords[idx++] = (i - 1) * Ny + j;
			}
		}

		int[] stripCounts = new int[Nx - 1];
		for (int i = 0; i < Nx - 1; i++)
			stripCounts[i] = 2 * Ny;

		GeometryInfo gi = new GeometryInfo(GeometryInfo.TRIANGLE_STRIP_ARRAY);
		gi.setCoordinates(pts);
		gi.setCoordinateIndices(coords);
		gi.setStripCounts(stripCounts);

		NormalGenerator ng = new NormalGenerator();
		ng.generateNormals(gi);

		return gi.getGeometryArray();
	}
*/
	public double getX0() {
		return x0;
	}

	public void setX0(double x0) {
		this.x0 = x0;
	}

	public double getY0() {
		return y0;
	}

	public void setY0(double y0) {
		this.y0 = y0;
	}

	public double getX1() {
		return x1;
	}

	public void setX1(double x1) {
		this.x1 = x1;
	}

	public double getY1() {
		return y1;
	}

	public void setY1(double y1) {
		this.y1 = y1;
	}

	public double[][] getFuncao() {
		return funcao;
	}

	public void setFuncao(double[][] funcao) {
		this.funcao = funcao;
	}

}