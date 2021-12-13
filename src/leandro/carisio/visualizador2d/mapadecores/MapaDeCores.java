package leandro.carisio.visualizador2d.mapadecores;

import java.awt.Color;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class MapaDeCores {
	private int totalDeCores;
	private Map<Intervalo, Color> mapa;

	public MapaDeCores(int quantidadeDeCores) {
		setTotalDeCores(quantidadeDeCores);
	}

	public int getTotalDeCores() {
		while (totalDeCores % 5 != 0) {
			totalDeCores++;
		}
		return totalDeCores;
	}
	public void setTotalDeCores(int totalDeCores) {
		this.totalDeCores = totalDeCores;
		setMapa(null);
	}
	public Map<Intervalo, Color> getMapa() {
		if (mapa == null) {
			mapa = novoMapaDeCores(0,1);
		}
		return mapa;
	}
	public void setMapa(Map<Intervalo, Color> mapa) {
		this.mapa = mapa;
	}
	public Map<Intervalo, Color> novoMapaDeCores(double valorInicial, double valorFinal) {
		Map<Intervalo, Color> m = new HashMap<Intervalo, Color>();
		double deltaX = (valorFinal-valorInicial)/getTotalDeCores();
		int i = 0, cont = 0;
		int qtdDeCoresNoIntervalo = getTotalDeCores()/5;
		
		for (; i < qtdDeCoresNoIntervalo; i++) {
			Intervalo intervalo = new Intervalo(valorInicial + i*deltaX, valorInicial + (i+1)*deltaX);
			Color c = new Color(0, 0, 128 + (int)((cont*(128))/qtdDeCoresNoIntervalo));
			m.put(intervalo, c);
			cont++;
		}
		cont = 0;
		for (; i < 2*qtdDeCoresNoIntervalo; i++) {
			Intervalo intervalo = new Intervalo(valorInicial + i*deltaX, valorInicial + (i+1)*deltaX);
			Color c = new Color(0, (int)((cont*255.0)/qtdDeCoresNoIntervalo), 255);
			m.put(intervalo, c);
			cont++;
		}
		cont = 0;
		for (; i < 3*qtdDeCoresNoIntervalo; i++) {
			Intervalo intervalo = new Intervalo(valorInicial + i*deltaX, valorInicial + (i+1)*deltaX);
			Color c = new Color((int)((cont*255.0)/qtdDeCoresNoIntervalo), 255, 255 - (int)((cont*255.0)/qtdDeCoresNoIntervalo));
			m.put(intervalo, c);
			cont++;
		}
		cont = 0;
		for (; i < 4*qtdDeCoresNoIntervalo; i++) {
			Intervalo intervalo = new Intervalo(valorInicial + i*deltaX, valorInicial + (i+1)*deltaX);
			Color c = new Color(255, 255 - (int)((cont*255.0)/qtdDeCoresNoIntervalo), 0);
			m.put(intervalo, c);
			cont++;
		}
		cont = 0;
		for (; i < 5*qtdDeCoresNoIntervalo; i++) {
			Intervalo intervalo = new Intervalo(valorInicial + i*deltaX, valorInicial + (i+1)*deltaX);
			Color c = new Color(255 - (int)((cont*(128))/qtdDeCoresNoIntervalo) ,0,0);
			m.put(intervalo, c);
			cont++;
		}
		return m;
	}
	public Color getCor(double valor) {
		Iterator<Intervalo> i = getMapa().keySet().iterator();
		Intervalo intervalo = null;
		while (i.hasNext()) {
			intervalo = i.next();
			if (intervalo.isPontoPertence(valor)) {
				break;
			}
		}
		return getMapa().get(intervalo);
	}
}