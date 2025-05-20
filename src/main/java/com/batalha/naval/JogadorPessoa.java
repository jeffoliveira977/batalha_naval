package com.batalha.naval;

import java.util.List;
import java.util.Scanner;

public class JogadorPessoa extends Jogador {

    public JogadorPessoa(Tabuleiro tabuleiro, Tabuleiro tabuleiroOponente) {
        super(tabuleiro, tabuleiroOponente);
        this.tamanho = tabuleiro.getTamanho();
    }

    @Override
    public List<Integer> realizarDisparo(Scanner scanner) {
        int linha, coluna;
        while (true) {
            try {
                System.out.printf("Digite a linha (1-%d): ", tamanho);
                linha = scanner.nextInt();
                System.out.printf("Digite a coluna (1-%d): ", tamanho);
                coluna = scanner.nextInt();

                List<Integer> posicao = List.of(linha-1, coluna-1);
                if (!tabuleiro.isCoordenadaValida(posicao)) {             
                    System.err.println("Coordenadas fora do tabuleiro. Tente novamente.");
                    continue;
                }
                
                if (!tabuleiroOponente.isCoordenadaLivre(posicao)) {
                    System.err.println("Posição já atingida. Tente novamente.");
                    continue;
                }
                
                return posicao;
            } catch (java.util.InputMismatchException e) {
                System.out.println("Entrada inválida. Digite números inteiros.");
                scanner.next();
            }
        }
    }

    @Override
    public void registrarDisparo(List<Integer> posicao) {
    }
}
