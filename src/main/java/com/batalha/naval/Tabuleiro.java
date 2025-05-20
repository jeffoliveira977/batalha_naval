package com.batalha.naval;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Tabuleiro {
    private final int tamanho;
    private final char[][] tabuleiro;
    private final List<Navio> navios;
    public static final char AGUA = '~';
    public static final char NAVIO = '#';
    public static final char ACERTO = 'X';
    public static final char ERRO = 'o';
    private final int[][] direcoes = {{-1, -1}, {-1, 0}, {-1, 1}, {0, -1}, {0, 1}, {1, -1}, {1, 0}, {1, 1}};
    private final Random random = new Random();

    public Tabuleiro(int tamanho) {
        this.tamanho = tamanho;
        this.tabuleiro = new char[tamanho][tamanho];
        this.navios = new ArrayList<>();
        inicializarTabuleiro();
    }

    private void inicializarTabuleiro() {
        for (int i = 0; i < tamanho; i++) {
            for (int j = 0; j < tamanho; j++) {
                tabuleiro[i][j] = AGUA;
            }
        }
    }

    public int getTamanho() {
        return tamanho;
    }

    public char[][] getTabuleiro() {
        return tabuleiro;
    }

    public char getCelula(List<Integer> posicao) {
        int linha = posicao.get(0);
        int coluna = posicao.get(1);
    
        if (isCoordenadaValida(posicao)) {
            return tabuleiro[linha][coluna];
        } 

        return ' ';
    }

    public void setCelula(List<Integer> posicao, char celula) {
        int linha = posicao.get(0);
        int coluna = posicao.get(1);
    
        if (isCoordenadaValida(posicao)) {
            tabuleiro[linha][coluna] = celula;
        }
    }

    public List<Navio> getNavios() {
        return this.navios;
    }

    // Função para desenhar as partes da tabela (cabeça, corpo, rodapé)
    private void desenharParteDaTabela(int tamanho, String inicio, String separador, String fim) {
        System.out.print(inicio);
        for (int i = 0; i < tamanho; i++) {
            System.out.print("───────");
            if (i < tamanho - 1) {
                System.out.print(separador);
            }
        }
        System.out.println(fim);
    }

    // Função para desenhar o tabuleiro
    public void desenhar(boolean mostrarNavios) {
        System.out.println();

        // Desenhar o alfabeto para as colunas
        System.out.print("   ");
        for (int i = 0; i < tamanho; i++) {
           System.out.printf("    %d   ", i+1);
        }

        System.out.println();

        // Desenhar a cabeça do tabuleiro
        desenharParteDaTabela(tamanho, "   ┌", "┬", "┐");

        for (int i = 0; i < tamanho; i++) {

            // Desenhar a numeração das linhas
            if (i < 9) {
                System.out.printf(" %d │", i+1);
            } else {
                System.out.printf("%d │", i+1);
            }     

            // Desenhar os valores no tabuleiro
            for (int j = 0; j < tamanho; j++) {
                char celula = tabuleiro[i][j];
                if (!mostrarNavios && celula == NAVIO) {
                    System.out.printf("   %c ", AGUA);
                } else {
                    System.out.printf("   %c ", celula);
                }
                if (j < tamanho-1) {
                    System.out.print("  │");
                }
            }

            // Desenhar o corpo do tabuleiro
            System.out.println("  │");
            if (i < tamanho-1) {
                desenharParteDaTabela(tamanho, "   ├", "┼", "┤");
            }
        }

        // Desenhar o rodapé do tabuleiro
        desenharParteDaTabela(tamanho, "   └", "┴", "┘");
    }

    // Função para criar os navios
    public void criarNavios(int numNavios) {
        for (int i = 0; i < numNavios; i++) {
            Navio navio = new Navio(3);
            posicionarNavio(navio, 100);
            navios.add(navio);
        }
    }

    // Função para posicionar os navios no tabuleiro recursivamente
    private boolean posicionarNavio(Navio navio, int tentativas) {

        if (tentativas <= 0) {
            System.err.println("Não foi possível posicionar o navio.");
            return false;
        }

        boolean horizontal = random.nextBoolean();

        int tamanhoNavio = navio.getTamanho();
        int tamanhoMinimo = tamanho-tamanhoNavio+1;

        int linha = random.nextInt(horizontal? tamanho : tamanhoMinimo);
        int coluna = random.nextInt(horizontal? tamanhoMinimo: tamanho);

        List<Coordenada> localizacao = new ArrayList<>();
        boolean valido = true;

        // Verifica se as coordenadas estão livres e se não há navios adjacentes
        for (int i = 0; i < tamanhoNavio; i++) {
            int novaLinha = horizontal? linha : linha+i;
            int novaColuna = horizontal? coluna+i : coluna;
            List<Integer> posicao = List.of(novaLinha, novaColuna);

            if (getCelula(posicao) == AGUA && isNavioAdjacente(posicao)) {
                valido = false;
                break;
            }

            localizacao.add(new Coordenada(posicao));
        }

        if(!valido) {
            return posicionarNavio(navio, tentativas-1);
        }

        for (Coordenada coord : localizacao) {
            setCelula(coord.getPosicao(), NAVIO);
            navio.adicionarCoordenada(coord);
        }

        return true;
    }

    // Função para verificar se há navio adjacente para evitar colisões
    public boolean isNavioAdjacente(List<Integer> posicao) {
        int linha = posicao.get(0);
        int coluna = posicao.get(1);

        for (int[] dir : direcoes) {
            List<Integer> novaPosicao = List.of(linha+dir[0], coluna+dir[1]);
            if (getCelula(novaPosicao) == NAVIO) {
                return true;
            }
        }

        return false;
    }

    // Função para verificar se as coordenadas estão no limite do tabuleiro
    public boolean isCoordenadaValida(List<Integer> posicao) {
        int linha = posicao.get(0);
        int coluna = posicao.get(1);
        return linha >= 0 && linha < tamanho && coluna >= 0 && coluna < tamanho;
    }

    // Função para verificar se a coordenada está livre (se é água ou navio)
    public boolean isCoordenadaLivre(List<Integer> posicao) {
        char celula = getCelula(posicao);
        return celula == AGUA || celula == NAVIO;
    }

    public boolean verificarDisparo(List<Integer> posicao) {
        System.out.println("Disparando em: [" + (posicao.get(0)+1) + ", " + (posicao.get(1)+1) + "]");

        switch (getCelula(posicao)) {
            case NAVIO:
                setCelula(posicao, ACERTO);
                for (Navio navio : navios) {
                    if (navio.estaNaCoordenada(posicao)) {
                        navio.marcarAtingido(posicao);
                        if (navio.isAfundado()) {
                            System.out.println("Navio afundado!");
                            return true;
                        }
                        break;
                    }
                }
                System.out.println("Acertou um navio!");
                return true;
            case AGUA:
                setCelula(posicao, ERRO);
                System.out.println("Água!");
                return false;
            case ACERTO:
            case ERRO:
                System.out.println("Já foi disparado nessa posição.");
                return false;
            default:
                System.out.println("Coordenadas fora do tabuleiro.");
                return false;
        }
    }

    // Função para verificar se todos navios foram afundados
    public boolean todosNaviosAfundados() {
        for (Navio navio : navios) {
            if (!navio.isAfundado()) {
                return false;
            }
        }
        return true;
    }
}