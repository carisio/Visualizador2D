package leandro.carisio.visualizador2d;

import java.awt.Color;
import java.awt.Graphics2D;

public class VisualizadorGraficoXY extends Visualizador2D {
	private double[][] pontos;
	private Color corDaLinha;
	private boolean ajustaMaxMinAutomaticamente;

	public VisualizadorGraficoXY(double[][] pontos, boolean ajustaMaxMinAutomaticamente) {
		setAjustaMaxMinAutomaticamente(ajustaMaxMinAutomaticamente);
		setPontos(pontos);
	}

	public boolean isAjustaMaxMinAutomaticamente() {
		return ajustaMaxMinAutomaticamente;
	}

	public void setAjustaMaxMinAutomaticamente(boolean ajustaMaxMinAutomaticamente) {
		this.ajustaMaxMinAutomaticamente = ajustaMaxMinAutomaticamente;
	}

	public void setMostrarCoordenadas(boolean mostrarCoordenadas) {
		if (mostrarCoordenadas) {
			addMouseMotionListener(new EventoParaMostrarCoordenadas(this));
		}
	}

	public double[][] getPontos() {
		if (pontos == null) {
			pontos = new double[0][2];
		}
		return pontos;
	}
	public void setPontos(double[][] pontos) {
		this.pontos = pontos;
		if (isAjustaMaxMinAutomaticamente()) {
			ajustaMaximosEMinimos();
		}
	}
	
	public Color getCorDaLinha() {
		if (corDaLinha == null) {
			corDaLinha = Color.RED;
		}
		return corDaLinha;
	}

	public void setCorDaLinha(Color corDaLinha) {
		this.corDaLinha = corDaLinha;
	}

	public void ajustaMaximosEMinimos() {
		double[][] p = getPontos();
		double xMax = p[p.length-1][0], xMin = p[0][0], 
				yMax = Double.MIN_VALUE, yMin = Double.MAX_VALUE;
		
		for (int i = 0; i < p.length; i++) {
			double valor = p[i][1];
			if (valor > yMax) {
				yMax = valor;
			}
			if (valor < yMin) {
				yMin = valor;
			}
		}
		setXMax(xMax);
		setYMax(yMax);
		setXMin(yMin);
		setYMin(yMin);
		setXEixo(xMin);
		setYEixo(0);
	}
	
	@Override
	public void doContinuaDesenho(Graphics2D g) {
		super.doContinuaDesenho(g);
		g.setColor(getCorDaLinha());
		double[][] p = getPontos();
		for (int i = 1; i < p.length; i++) {
			desenhaLinha(p[i-1][0], p[i-1][1], p[i][0], p[i][1]);
		}
	}	
}