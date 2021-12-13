package leandro.carisio.visualizador2d;

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.text.DecimalFormat;

public class EventoParaMostrarCoordenadas implements MouseMotionListener {
	private Visualizador2D visualizador2D;

	public Visualizador2D getVisualizador2D() {
		return visualizador2D;
	}

	public void setVisualizador2D(Visualizador2D visualizador2D) {
		this.visualizador2D = visualizador2D;
	}

	public EventoParaMostrarCoordenadas(Visualizador2D visualizador2D) {
		setVisualizador2D(visualizador2D);
	}

	@Override
	public void mouseDragged(MouseEvent e) {
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		double coor[] = getVisualizador2D().converteDeCoordenadaDeTelaParaCoordenadaReal(e.getX(), e.getY());
		getVisualizador2D().setToolTipText(converteDeCoordenadasParaString(coor));
	}
	
	public String converteDeCoordenadasParaString(double[] coordenadas) {
		DecimalFormat dc = new DecimalFormat("##0.##E0");
		return "(x, y) = (" + dc.format(coordenadas[0]) + ", " + dc.format(coordenadas[1]) + ")";
	}
}