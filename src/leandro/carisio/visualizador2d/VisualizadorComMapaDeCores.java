package leandro.carisio.visualizador2d;

import java.awt.Graphics2D;

import leandro.carisio.visualizador2d.mapadecores.MapaDeCores;

public class VisualizadorComMapaDeCores extends Visualizador2D {
	private double[][] valores;
	private double valorMaximo;
	private double valorMinimo;
	private MapaDeCores mapaDeCores;
	
	public VisualizadorComMapaDeCores(double[][] valores, double xmin, double ymin, double xmax, double ymax) {
		setXMin(xmin);
		setYMin(ymin);
		setXMax(xmax);
		setYMax(ymax);
		setValores(valores);
	}
	public MapaDeCores getMapaDeCores() {
		if (mapaDeCores == null) {
			mapaDeCores = new MapaDeCores(100);
			mapaDeCores.setMapa(mapaDeCores.novoMapaDeCores(getValorMinimo(), getValorMaximo()));
		}
		return mapaDeCores;
	}
	public double getValorMaximo() {
		return valorMaximo;
	}
	public void setValorMaximo(double valorMaximo) {
		this.valorMaximo = valorMaximo;
	}
	public double getValorMinimo() {
		return valorMinimo;
	}
	public void setValorMinimo(double valorMinimo) {
		this.valorMinimo = valorMinimo;
	}
	public double[][] getValores() {
		if (valores == null) {
			valores = new double[0][0];
		}
		return valores;
	}
	public void setValores(double[][] valores) {
		this.valores = valores;
		setMaiorValorEMenorValor(valores);
	}
	public void setMaiorValorEMenorValor(double[][] matriz) {
		double maior = Double.MIN_VALUE;
		double menor = Double.MAX_VALUE;
		for (int i = 0; i < getNx(); i++) {
			for (int j = 0; j < getNy(); j++) {
				if (matriz[i][j] > maior) {
					maior = matriz[i][j];
				}
				if (matriz[i][j] < menor) {
					menor = matriz[i][j];
				}
			}
		}
		setValorMaximo(maior);
		setValorMinimo(menor);
	}
	public int getNx() {
		return getValores().length;
	}
	public int getNy() {
		return getValores()[0].length;
	}
	public double getDeltaX() {
		return (getXMax() - getXMin())/getNx();
	}
	public double getDeltaY() {
		return (getYMax() - getYMin())/getNy();
	}
	@Override
	public void doContinuaDesenho(Graphics2D g) {
		super.doContinuaDesenho(g);
		double dx = getDeltaX();
		double dy = getDeltaY();
		for (int i = 0; i < getNx(); i++) {
			for (int j = 0; j < getNy(); j++) {
				g.setColor(getMapaDeCores().getCor(getValores()[i][j]));
				preencheRetangulo(i*dx, j*dy, (i+1)*dx, (j+1)*dy);
			}
		}
	}
	
	public double getValor(double x, double y) {
		return getValores()[getIndiceI(x)][getIndiceJ(y)];
	}
	public int getIndiceI(double x) {
		int i = (int)Math.floor((x-getXMin())/(getXMax()-getXMin()) * getNx());
		if (i >= getNx()) i = getNx()-1;
		if (i < 0) i = 0;
		return i;
	}
	public int getIndiceJ(double y) {
		int j= (int)Math.floor((y-getYMin())/(getYMax()-getYMin()) * getNy());
		if (j >= getNy()) j = getNy()-1;
		if (j < 0) j = 0;
		return j;
	}
}