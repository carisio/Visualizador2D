package leandro.carisio.app;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;

import leandro.carisio.visualizador2d.EventoParaMostrarCoordenadas;
import leandro.carisio.visualizador2d.IAtividade;
import leandro.carisio.visualizador2d.Visualizador2D;

public class CopiarGrafico extends Visualizador2D {
	private static final long serialVersionUID = 2494851794490363830L;
	
	
	private Image fundo = null; 
	private IAtividade imprimeValores = null;

	public CopiarGrafico(IAtividade imprimeValores) {
		this.imprimeValores = imprimeValores;
		
		String nomeArquivoFundo = JOptionPane.showInputDialog("Digite o nome do arquivo: ");
		try {
			fundo = ImageIO.read(new File(nomeArquivoFundo));
		} catch (IOException e) {
			fundo = null;
		}

		double xmin = Integer.parseInt(JOptionPane.showInputDialog("Digite xmin: "));
		double xmax = Integer.parseInt(JOptionPane.showInputDialog("Digite xmax: "));
		double ymin = Integer.parseInt(JOptionPane.showInputDialog("Digite ymin: "));
		double ymax = Integer.parseInt(JOptionPane.showInputDialog("Digite ymax: "));

		setXMin(xmin);
		setXMax(xmax);
		setYMin(ymin);
		setYMax(ymax);
		
		addMouseMotionListener(new EventoParaMostrarCoordenadas(this));
	}
	
	@Override
	public void mousePressed(MouseEvent ev) {
		super.mousePressed(ev);
		
		double[] coord = converteDeCoordenadaDeTelaParaCoordenadaReal(ev.getX(), ev.getY());
		imprimeValores.executa(coord);
	}
	
	@Override
	public void doContinuaDesenho(Graphics2D g) {
		super.doContinuaDesenho(g);
		
		g.drawImage(fundo,0,0,(int)getSize().getWidth(), (int)getSize().getHeight(), null);
	}
	
	public static void main(String[] args) {
		JFrame jframe = new JFrame();
		
		final JTextArea textArea = new JTextArea();
		
		IAtividade imprimeValores = new IAtividade() {
			@Override
			public Object executa(Object o) {
				double[] coord = (double[])(o);
				textArea.setText(textArea.getText() + "\n" + coord[0] + "\t" + coord[1]);
				return null;
			}
		};

		JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, new CopiarGrafico(imprimeValores), new JScrollPane(textArea));
		
		jframe.setContentPane(splitPane);
		jframe.setSize(700, 700);
		jframe.setExtendedState(jframe.getExtendedState()|JFrame.MAXIMIZED_BOTH);
		jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jframe.setVisible(true);
		
		splitPane.setDividerLocation(0.75);
	}
}
