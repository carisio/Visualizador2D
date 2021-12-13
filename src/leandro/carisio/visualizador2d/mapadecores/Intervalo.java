package leandro.carisio.visualizador2d.mapadecores;

public class Intervalo {
	private double inicio;
	private double fim;
	
	public Intervalo(double i, double f) {
		setInicio(i);
		setFim(f);
	}
	
	@Override
	public int hashCode() {
		return new Double(inicio + fim).hashCode();
	}
	public boolean isPontoPertence(double d) {
		return d <= getFim() && d >= getInicio();
	}
	protected double getInicio() {
		return inicio;
	}
	protected void setInicio(double inicio) {
		this.inicio = inicio;
	}
	protected double getFim() {
		return fim;
	}
	protected void setFim(double fim) {
		this.fim = fim;
	}
}