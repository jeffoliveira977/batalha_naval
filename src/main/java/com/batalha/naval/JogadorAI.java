package com.batalha.naval;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

public class JogadorAI extends Jogador {
    private final Random random = new Random();
    private final ArrayList<List<Integer>> alvos = new ArrayList<>();
    private final Set<String> alvosRegistrados = new HashSet<>();
    private final int[][] direcoes = {{-1, 0}, {1, 0}, {0, -1},{0, 1}};
    
    public JogadorAI(Tabuleiro tabuleiro, Tabuleiro tabuleiroOponente) {
        super(tabuleiro, tabuleiroOponente);
        this.tamanho = tabuleiro.getTamanho();
    }

    // Função para realizar disparo
    @Override
    public List<Integer> realizarDisparo(Scanner scanner) {
        List<Integer> alvo;

        // Primeiro verifica os alvos registrados
        while (!alvos.isEmpty()) {
            alvo = alvos.remove(0);
            if (tabuleiroOponente.isCoordenadaLivre(alvo)) {
                return alvo;
            }
        }

        // Se não houver, escolhe aleatoriamente
        int linha, coluna;
        int tentativas = 0;
        do {
            linha = random.nextInt(tamanho);
            coluna = random.nextInt(tamanho);
            alvo = List.of(linha, coluna);
            tentativas++;
        } while (!tabuleiroOponente.isCoordenadaLivre(alvo) && tentativas < tamanho*tamanho);

        return alvo;
    }

    // Função para registrar disparos
    @Override
    public void registrarDisparo(List<Integer> posicao) {

        // Verificar nas direções cima/baixo e direita/esquerda se o disparo acertou um navio
        for (int[] dir : direcoes) {
            int linha = posicao.get(0) + dir[0];
            int coluna = posicao.get(1) + dir[1];
            List<Integer> alvo = List.of(linha, coluna);

            if (tabuleiroOponente.isCoordenadaValida(alvo)) {
                // Verifica se o alvo já foi registrado
                String chave = linha + "," + coluna;
                if (!alvosRegistrados.contains(chave)) {
                    alvosRegistrados.add(chave);
                    alvos.add(alvo);
                } 
            }
        }

        // Embaralhar os alvos para evitar padrões previsíveis
        Collections.shuffle(alvos);  
    }
}