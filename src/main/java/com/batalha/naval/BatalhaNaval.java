package com.batalha.naval;

import java.util.List;
import java.util.Scanner;

public class BatalhaNaval {
    private final Jogador jogadorPessoa;
    private final Jogador jogadorAI;
    private final Scanner scanner = new Scanner(System.in);
  
    public BatalhaNaval() {

        Tabuleiro tabuleiroJogador = new Tabuleiro(10);
        Tabuleiro tabuleiroAI = new Tabuleiro(10);
        tabuleiroJogador.criarNavios(3);
        tabuleiroAI.criarNavios(3);

        jogadorPessoa = new JogadorPessoa(tabuleiroJogador, tabuleiroAI);
        jogadorAI = new JogadorAI(tabuleiroAI, tabuleiroJogador);
    }

   public static void main(String[] args) {
        BatalhaNaval jogo = new BatalhaNaval();
        jogo.iniciarJogo();
    }

    public void iniciarJogo() {
        System.out.println("\nBem-vindo ao Batalha Naval!\n");
        System.out.println("Legenda:");
        System.out.println("~ - Água");
        System.out.println("# - Navio");
        System.out.println("o - Disparo na água");
        System.out.println("X - Navio atingido\n");

        System.out.println("[1] - Deseja carregar um jogo salvo?");
        System.out.println("[2] - Iniciar um novo jogo");
        System.out.println("[3] - Sair do jogo");

        switch (scanner.nextLine()) {
            case "1":
                if (JogoEstado.existeArquivo()) {
                    carregarJogo();
                } else {
                    System.out.println("Nenhum jogo salvo encontrado.");
                    iniciarJogo();
                }   break;
            case "2":
                System.out.println("Iniciando um novo jogo.");
                break;
            case "3":
                System.out.println("Saindo do jogo.");
                return;
            default:
                System.out.println("Opção inválida. Tente novamente.");
                iniciarJogo();
                break;
        }

        jogar();
    }

    public void carregarJogo() {
        if (JogoEstado.carregar(jogadorPessoa.getTabuleiro(), jogadorAI.getTabuleiro())) {
            System.out.println("Jogo carregado com sucesso!");
        } else {
            System.out.println("Erro ao carregar o jogo.");
        }
    }

    public void jogar() {
        try (scanner) {
            int numerosDisparos = 0;
            while (true) {
                
                System.out.print("\n                                 ");
                System.out.print("Seu Tabuleiro\n");
                jogadorPessoa.getTabuleiro().desenhar(true);
                
                System.out.print("\n                                 ");
                System.out.print("Tabuleiro da AI\n");
                jogadorAI.getTabuleiro().desenhar(false);
                
                System.out.println("\nSua vez de jogar:");
                
                System.out.println("\n[1] - Atacar");
                System.out.println("[2] - Sair do jogo");
                
                String resposta = scanner.nextLine();
                if (resposta.equals("2")) {
                    JogoEstado.salvar(jogadorPessoa.getTabuleiro(), jogadorAI.getTabuleiro());
                    break;
                } else if (!resposta.equals("1")) {
                    System.out.println("Opção inválida. Tente novamente.");
                    continue;
                }
                
                List<Integer> disparo = jogadorPessoa.realizarDisparo(scanner);
                
                if (jogadorAI.getTabuleiro().verificarDisparo(disparo)) {
                    if (jogadorAI.getTabuleiro().todosNaviosAfundados()) {
                        System.out.println("\nParabéns! Você venceu!");
                        break;
                    }
                }
                
                System.out.println("\nVez da AI jogar:");
                pausar(1000);
                
                disparo = jogadorAI.realizarDisparo(scanner);
                
                if(jogadorPessoa.getTabuleiro().verificarDisparo(disparo)) {
                    if (jogadorPessoa.getTabuleiro().todosNaviosAfundados()) {
                        System.out.println("\nVocê perdeu!");
                        break;
                    }
                    
                    jogadorAI.registrarDisparo(disparo);
                }
                
                pausar(1000);
                numerosDisparos++;
            }
            
            System.out.println("\nFim do Jogo.");
            System.out.println("Número de disparos: " + numerosDisparos);
        }
    }

    public static void pausar(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}