# Visualizador2D

Visualizar2D é uma classe utilitária (um JPanel) que permite que o usuário defina uma área de trabalho (coordenadas x_min, y_min, x_max e y_max) e, a partir daí, facilita a conversão entre uma coordenada de tela (pixels) e as coordenadas que a classe representam.

Dois apps usam essa classe:

- CopiarGrafico: permite que o usuário forneça uma imagem com um gráfico (plot). O JPanel desenha essa imagem e aí o usuário pode clicar na tela e o app vai informando os pontos clicados
- GerarHistograma: permite que o usuário desenhe uma forma de histograma na tela. Com esses dados o app gera um histograma simples a partir dos pontos desenhados considerando 100 bins distribuídos no intervalo de 0 a 100 e um total de 1.000.000 de pontos.