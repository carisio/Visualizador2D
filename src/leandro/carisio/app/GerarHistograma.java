package leandro.carisio.app;

import leandro.carisio.visualizador2d.EventoParaMostrarCoordenadas;
import leandro.carisio.visualizador2d.Visualizador2D;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class GerarHistograma extends Visualizador2D {
    private ArrayList<int[]> pontosClicadosTela = new ArrayList<int[]>();

    // Para executar é um aplicativo Java padrão
    // Basta clicar com o mouse e arrastar pra ir desenhando a forma do histograma
    // Ao desenhar o formato desejado, aperte a tecla S. O sistema irá imprimir
    // um histograma (100 bins, centralizados de 0.5 até 99.5) considerando um total
    // de 1.000.000 de amostras. E aí ele vai desenhar na tela a pdf
    // Para extrair um novo histograma, aperte a tecla C e repita.
    public GerarHistograma() {
        // Seta x de 0 a 100 pra facilitar já a questão dos bins
        // E seta y de 0 a 1 pra facilitar já mostrar o pdf no final
        setXMin(0);
        setXMax(100);
        setYMin(0);
        setYMax(1);

        this.addMouseMotionListener(this);

        this.addMouseMotionListener(new EventoParaMostrarCoordenadas(this));
    }
    @Override
    public void mouseDragged(MouseEvent ev) {
        this.pontosClicadosTela.add(new int[]{ev.getX(), ev.getY()});
        this.repaint();
    }

    public void mouseClicked(MouseEvent ev) {
        this.pontosClicadosTela.add(new int[]{ev.getX(), ev.getY()});
        this.repaint();
    }

    public void keyPressed(KeyEvent event) {
        super.keyPressed(event);

        if (event.getKeyCode() == KeyEvent.VK_S) {
            this.geraHistogramaECopiaParaAreaTransferencia();
        }
        if (event.getKeyCode() == KeyEvent.VK_C) {
            this.pontosClicadosTela = new ArrayList<int[]>();
            this.repaint();
        }
    }

    private String getStringPontosHistograma(ArrayList<double[]> pontosHistograma) {
        String retorno = "";
        for (double[] pontoHistograma: pontosHistograma) {
            retorno += (int)pontoHistograma[1] + "\n";
        }
        return retorno;
    }

    private void geraHistogramaECopiaParaAreaTransferencia() {
        // São 100 bins
        double bins[] = new double[100];
        // Total de amostras: 1.000.000
        int totalAmostras = 1000000;
        double frequenciaTotal = 0;
        // Cria os 100 bins e pega apenas a última amostra pra cada um deles
        // Pra facilitar, estamos considerando que a tela tem 100 de coordenada real,
        // então basta converter as coordenadas e pegar a coordenada x de tela para encontrar o bin
        // Considera a última amostra como uma amostra de tela, mas poderia ser também a outra.
        // É indiferente pois calcularemos a proporção depois
        for (int[] coordenadasClicadas: this.pontosClicadosTela) {
            double[] coord = converteDeCoordenadaDeTelaParaCoordenadaReal(coordenadasClicadas[0], coordenadasClicadas[1]);
            coord[1] = Math.min(Math.max(coord[1], this.getXMin()), this.getXMax());
            int bin = Math.min(Math.max((int)(coord[0]), 0), bins.length-1);
            bins[bin] = coord[1];
        }
        // Calcula o total para calcular a proporção
        for (double freq: bins) {
            frequenciaTotal += freq;
        }
        ArrayList<double[]> pontosHistograma = new ArrayList<double[]>();

        // Define a quantidade de amostras para cada bin
        // Esse primeiro loop é uma aproximação. Por conta de erros de arredondamento
        // o total não vai dar totalAmostras e, por isso, é necessário corrigir em seguida
        int amostrasInseridas = 0;
        for (int i = 0; i < bins.length; i++) {
            double pontoHistograma[] = new double[2];
            pontoHistograma[0] = i + 0.5; // Adiciona 0.5 para considerar o centro do bin
            pontoHistograma[1] = (int)(totalAmostras*bins[i]/frequenciaTotal);
            amostrasInseridas += pontoHistograma[1];
            pontosHistograma.add(pontoHistograma);
        }
        // Gera o total de amostras faltantes e coloca no bin que mais tem amostras
        int totalAmostrasFaltantes = totalAmostras - amostrasInseridas;
        int binParaInserirAmostrasRestantes = 0;
        double maior = 0;
        for (int i = 0; i < pontosHistograma.size(); i++) {
            if (pontosHistograma.get(i)[1] > maior) {
                binParaInserirAmostrasRestantes = i;
                maior = pontosHistograma.get(i)[1];
            }
        }
        pontosHistograma.get(binParaInserirAmostrasRestantes)[1] += totalAmostras - amostrasInseridas;

        // Reinicia o pontos de tela pois mostraremos na tela agora a fdp:
        this.pontosClicadosTela = new ArrayList<int[]>();
        for (double[] pontoHistograma: pontosHistograma) {
            if (pontoHistograma[1] > 0) {
                // Como as coordenadas de tela refletem um y de 0 a 1, é necessário uniformizar pontoHistograma[1]
                int[] pontoHistogramaTela = this.converteDeCoordenadaRealParaCoordenadaDeTela(
                        pontoHistograma[0], pontoHistograma[1]/totalAmostras);
                this.pontosClicadosTela.add(pontoHistogramaTela);
            }
        }

        this.repaint();

        StringSelection stringSelection = new StringSelection(getStringPontosHistograma(pontosHistograma));
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(stringSelection, null);
    }

    public void doContinuaDesenho(Graphics2D g) {
        desenhaInstrucoes(g);

        // Desenha os pontos
        g.setColor(Color.RED);
        for (int[] ponto: this.pontosClicadosTela) {
            g.drawArc(ponto[0], ponto[1], 10, 10, 0, 360);
        }

        desenhaGrid(g);
    }
    private void desenhaInstrucoes(Graphics2D g) {
        g.setFont(new Font("Arial", Font.PLAIN, 14));
        g.setColor(Color.BLACK);
        int dx = 60; int dy = 20;
        g.drawString("Clique (ou clique e arraste) para desenhar o formato da sua pdf.", dx, dy);
        g.drawString("Use o botão direito do mouse para ver alternativas.", dx, 2*dy);
        g.drawString("Você pode gerar um histograma com 100 bins(0.5 até 99.5)", dx, 3*dy);
        g.drawString("com 1.000.000 de amostras a partir do desenho feito.", dx, 4*dy);
        g.drawString("Gerado o histograma, é exibida na tela a pdf dos dados.", dx, 5*dy);
        g.drawString("Atalhos:", dx, 6*dy);
        g.drawString("(S) gera o histograma e salva na área de transferência", dx, 7*dy);
        g.drawString("(C) apaga os dados e inicia novamente", dx, 8*dy);
    }
    private void desenhaGrid(Graphics2D g) {
        g.setColor(Color.GRAY);
        g.setStroke(new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL,
                0, new float[]{9}, 0));
        for (int i = 1; i <= 10; i++) {
            desenhaLinha(getXMin() + i/10. * getXMax(), getYMin(), getXMin() + i/10. * getXMax(), getYMax());
            desenhaLinha(getXMin(), getYMin() + i/10. * getYMax(), getXMax(), getYMin() + i/10. * getYMax());

            int[] labelY = converteDeCoordenadaRealParaCoordenadaDeTela(0, i/10. * getYMax());
            g.drawString(100*i/10. + "%", labelY[0], labelY[1]);

            int[] labelX = converteDeCoordenadaRealParaCoordenadaDeTela(i/10. * getXMax(), 0);
            g.drawString(10*i + "", labelX[0]+5, labelX[1]);
        }
    }
    public static void main(String[] args) {
        JFrame app = new JFrame();
        app.setContentPane(new GerarHistograma());
        app.setVisible(true);
        app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        app.setExtendedState(app.getExtendedState() | JFrame.MAXIMIZED_BOTH);
    }
}
