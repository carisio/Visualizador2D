package leandro.carisio.visualizador2d;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.event.MouseInputListener;

public class Visualizador2D extends JPanel implements MouseWheelListener, KeyListener, MouseInputListener {
	// Cor de fundo
	private Color corDeFundo;
	// Coordenadas da área de desenho
	private double xMin=-10, xMax=10, yMin=-10, yMax=10;
	private boolean mostrarBorda = true;
	private Color corDaBorda;
	// Ponto de cruzamento do eixo de coordenadas
	private double xEixo = 0, yEixo = 0;
	private boolean mostrarEixo = true;
	private Color corDoEixo;
	// Zoom
	private double zoom = 1;
	// Deslocamento dos eixos
	private int dx=0, dy=0;
	// Instância de Graphics2D
	private Graphics2D graphics2d;
	
	public Visualizador2D() {
		setFocusable(true);
		addMouseWheelListener(this);
		addMouseListener(this);
		addKeyListener(this);
	}
	
	public Graphics2D getGraphics2d() {
		return graphics2d;
	}

	public void setGraphics2d(Graphics2D graphics2d) {
		this.graphics2d = graphics2d;
	}

	public double getXMin() {
		return xMin;
	}

	public void setXMin(double min) {
		xMin = min;
	}

	public double getXMax() {
		return xMax;
	}

	public void setXMax(double max) {
		xMax = max;
	}

	public double getYMin() {
		return yMin;
	}

	public void setYMin(double min) {
		yMin = min;
	}

	public double getYMax() {
		return yMax;
	}

	public void setYMax(double max) {
		yMax = max;
	}

	public double getZoom() {
		return zoom;
	}

	public void setZoom(double zoom) {
		this.zoom = zoom;
	}

	public double getXEixo() {
		return xEixo;
	}

	public void setXEixo(double eixo) {
		xEixo = eixo;
	}

	public double getYEixo() {
		return yEixo;
	}

	public void setYEixo(double eixo) {
		yEixo = eixo;
	}

	public boolean isMostrarEixo() {
		return mostrarEixo;
	}

	public void setMostrarEixo(boolean mostrarEixo) {
		this.mostrarEixo = mostrarEixo;
	}

	public Color getCorDoEixo() {
		if (corDoEixo == null) {
			corDoEixo = Color.GRAY;
		}

		return corDoEixo;
	}

	public void setCorDoEixo(Color corDoEixo) {
		this.corDoEixo = corDoEixo;
	}

	public boolean isMostrarBorda() {
		return mostrarBorda;
	}

	public void setMostrarBorda(boolean mostrarBorda) {
		this.mostrarBorda = mostrarBorda;
	}

	public Color getCorDaBorda() {
		if (corDaBorda == null) {
			corDaBorda = Color.DARK_GRAY;
		}

		return corDaBorda;
	}

	public void setCorDaBorda(Color corDaBorda) {
		this.corDaBorda = corDaBorda;
	}

	public int getDx() {
		return dx;
	}

	public void setDx(int dx) {
		this.dx = dx;
	}

	public int getDy() {
		return dy;
	}

	public void setDy(int dy) {
		this.dy = dy;
	}

	public int[] converteDeCoordenadaRealParaCoordenadaDeTela(double xReal, double yReal) {
		double xTela = ((xReal-getXMin())*getWidth())/(getXMax()-getXMin());
		double yTela = getHeight() - ((yReal-getYMin())*getHeight())/(getYMax()-getYMin());
		
		return new int[]{(int)xTela, (int)yTela};
	}
	public double[] converteDeCoordenadaDeTelaParaCoordenadaReal(int xTela, int yTela) {
		xTela-=getDx();
		yTela-=getDy();

		double w = getWidth()*getZoom();
		double h = getHeight()*getZoom();

		double xReal = xTela * (getXMax() - getXMin()) / w + getXMin();
		double yReal = (h - yTela)*(getYMax()-getYMin())/h + getYMin();
		
		return new double[]{xReal, yReal};
	}
	
	public Color getCorDeFundo() {
		if (corDeFundo == null) {
			corDeFundo = Color.BLACK;
		}

		return corDeFundo;
	}

	public void setCorDeFundo(Color corDeFundo) {
		this.corDeFundo = corDeFundo;
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2 = (Graphics2D)g;
		setGraphics2d(g2);

		g2.setBackground(getCorDeFundo());
		g2.translate(getDx(), getDy());
		g2.scale(getZoom(), getZoom());
		desenhaBorda();
		desenhaEixos();
		doContinuaDesenho(g2);
	}

	public void doContinuaDesenho(Graphics2D g) {
	}

	public void desenhaEixos() {
		if (isMostrarEixo()) {
			getGraphics2d().setColor(getCorDoEixo());
			desenhaLinha(getXEixo(), getYMin(), getXEixo(), getYMax());
			desenhaLinha(getXMin(), getYEixo(), getXMax(), getYEixo());
		}
	}

	public void desenhaBorda() {
		if (isMostrarBorda()) {
			getGraphics2d().setColor(getCorDaBorda());
			desenhaRetangulo(getXMin(), getYMin(), getXMax(), getYMax());
		}
	}

	public void desenhaRetangulo(double x1, double y1, double x2, double y2) {
		int[] r1 = converteDeCoordenadaRealParaCoordenadaDeTela(x1, y1);
		int[] r2 = converteDeCoordenadaRealParaCoordenadaDeTela(x2, y2);
		int x = Math.min(r1[0], r2[0]);
		int y = Math.min(r1[1], r2[1]);
		getGraphics2d().drawRect(x, y, Math.abs(r2[0]-r1[0]), Math.abs(r2[1]-r1[1]));
	}
	public void desenhaRetanguloPreenchido(double x1, double y1, double x2, double y2) {
		int[] r1 = converteDeCoordenadaRealParaCoordenadaDeTela(x1, y1);
		int[] r2 = converteDeCoordenadaRealParaCoordenadaDeTela(x2, y2);
		int x = Math.min(r1[0], r2[0]);
		int y = Math.min(r1[1], r2[1]);
		getGraphics2d().fillRect(x, y, Math.abs(r2[0]-r1[0]), Math.abs(r2[1]-r1[1]));
	}
	public void preencheRetangulo(double x1, double y1, double x2, double y2) {
		int[] r1 = converteDeCoordenadaRealParaCoordenadaDeTela(x1, y1);
		int[] r2 = converteDeCoordenadaRealParaCoordenadaDeTela(x2, y2);
		int x = Math.min(r1[0], r2[0]);
		int y = Math.min(r1[1], r2[1]);
		getGraphics2d().fillRect(x, y, Math.abs(r2[0]-r1[0]), Math.abs(r2[1]-r1[1]));
	}
	public void desenhaLinha(double x1, double y1, double x2, double y2) {
		int[] l1 = converteDeCoordenadaRealParaCoordenadaDeTela(x1, y1);
		int[] l2 = converteDeCoordenadaRealParaCoordenadaDeTela(x2, y2);
		getGraphics2d().drawLine(l1[0], l1[1], l2[0], l2[1]);
	}
	public void desenhaCirculo(double xCentro, double yCentro, double raio) {
		int[] pontoSuperiorEsquerdo = converteDeCoordenadaRealParaCoordenadaDeTela(xCentro-raio, yCentro+raio);
		int[] pontoInferiorDireito = converteDeCoordenadaRealParaCoordenadaDeTela(xCentro+raio, yCentro-raio);
		getGraphics2d().drawArc(
				pontoSuperiorEsquerdo[0],
				pontoSuperiorEsquerdo[1],
				pontoInferiorDireito[0]-pontoSuperiorEsquerdo[0], 
				pontoInferiorDireito[1]-pontoSuperiorEsquerdo[1],
				0,
				360);
	}
	public void desenhaCirculoPreenchido(double xCentro, double yCentro, double raio) {
		int[] pontoSuperiorEsquerdo = converteDeCoordenadaRealParaCoordenadaDeTela(xCentro-raio, yCentro+raio);
		int[] pontoInferiorDireito = converteDeCoordenadaRealParaCoordenadaDeTela(xCentro+raio, yCentro-raio);
		getGraphics2d().fillArc(
				pontoSuperiorEsquerdo[0],
				pontoSuperiorEsquerdo[1],
				pontoInferiorDireito[0]-pontoSuperiorEsquerdo[0], 
				pontoInferiorDireito[1]-pontoSuperiorEsquerdo[1],
				0,
				360);
	}
	public void deslocaParaBaixo() {
		setDy(getDy() + getHeight()/10);
		repaint();
	}
	public void deslocaParaCima() {
		setDy(getDy() - getHeight()/10);
		repaint();
	}
	public void deslocaParaDireita() {
		setDx(getDx() + getWidth()/10);
		repaint();
	}
	public void deslocaParaEsquerda() {
		setDx(getDx() - getWidth()/10);
		repaint();
	}
	public void maisZoom() {
		setZoom(1.25*getZoom());
	}
	public void menosZoom() {
		setZoom(getZoom()/1.25);
	}
	public static void main(String[] args) {
		JFrame jframe = new JFrame();
		jframe.setContentPane(new Visualizador2D());
		jframe.setSize(300,300);
		jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jframe.setVisible(true);
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent ev) {
		if (ev.getWheelRotation() > 0) {
			maisZoom();
		} else {
			menosZoom();
		}
		repaint();
	}

	@Override
	public void keyPressed(KeyEvent ev) {
		int code = ev.getKeyCode();
		if (code == 38) {
			deslocaParaCima();
		} else if (code == 40) {
			deslocaParaBaixo();
		} else if (code == 37) {
			deslocaParaEsquerda();
		} else if (code == 39) {
			deslocaParaDireita();
		}
	}

	@Override
	public void keyReleased(KeyEvent ev) {
	}

	@Override
	public void keyTyped(KeyEvent ev) {
	}

	@Override
	public void mouseClicked(MouseEvent ev) {
		requestFocus();
	}

	@Override
	public void mouseEntered(MouseEvent ev) {
		requestFocus();
	}

	@Override
	public void mouseExited(MouseEvent ev) {
	}

	@Override
	public void mousePressed(MouseEvent ev) {
		requestFocus();
	}

	@Override
	public void mouseReleased(MouseEvent ev) {
	}

	@Override
	public void mouseDragged(MouseEvent ev) {
	}

	@Override
	public void mouseMoved(MouseEvent ev) {
	}
}