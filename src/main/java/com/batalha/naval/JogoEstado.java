package com.batalha.naval;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class JogoEstado {

    private static final String NOME_ARQUIVO = "batalha_naval.data"; 


    // Função para verificar se o arquivo existe
    public static boolean existeArquivo() {
        File arquivo = new File(NOME_ARQUIVO);
        return arquivo.exists();
    }

    // Função para salvar o jogo
    public static void salvar(Tabuleiro tabuleiroJogador, Tabuleiro tabuleiroComputador) {

        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(NOME_ARQUIVO));
            writer.write("TABULEIRO_JOGADOR\n");
            escreverTabuleiro(writer, tabuleiroJogador);
            writer.write("NAVIOS_JOGADOR\n");
            escreverNavios(writer, tabuleiroJogador.getNavios());

            writer.write("TABULEIRO_COMPUTADOR\n");
            escreverTabuleiro(writer, tabuleiroComputador);
            writer.write("NAVIOS_COMPUTADOR\n");
            escreverNavios(writer, tabuleiroComputador.getNavios());

            System.out.println("Jogo salvo com sucesso!");
            
        } catch (IOException e) {
            System.err.println("Erro ao salvar o jogo: " + e.getMessage());
         } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    System.err.println("Erro ao fechar o arquivo: " + e.getMessage());
                }
            }
        }
    }

    // Função para carregar o jogo
    public static boolean carregar(Tabuleiro tabuleiroJogador, Tabuleiro tabuleiroComputador) {
        try (BufferedReader reader = new BufferedReader(new FileReader(NOME_ARQUIVO))) {
            String linha;
            
            tabuleiroJogador.getNavios().clear();
            tabuleiroComputador.getNavios().clear();

            while ((linha = reader.readLine()) != null) {
                 switch (linha) {
                    case "TABULEIRO_JOGADOR":
                        lerTabuleiro(reader, tabuleiroJogador);
                        break;
                    case "NAVIOS_JOGADOR":
                        lerNavios(reader, tabuleiroJogador);
                        break;
                    case "TABULEIRO_COMPUTADOR":
                        lerTabuleiro(reader, tabuleiroComputador);
                        break;
                    case "NAVIOS_COMPUTADOR":
                        lerNavios(reader, tabuleiroComputador);
                        break;
                    default:
                        System.err.println("Linha desconhecida no arquivo: " + linha);
                        break;
                }
            }
            System.out.println("Jogo carregado com sucesso");
            return true;

        } catch (IOException e) {
            System.err.println("Erro ao carregar o jogo: " + e.getMessage());
            return false;
        }
    }

    // Função para escrever o tabuleiro
    private static void escreverTabuleiro(BufferedWriter writer, Tabuleiro tabuleiro) throws IOException {
        int tamanho = tabuleiro.getTamanho();
        for (int i = 0; i < tamanho; i++) {
            for (int j = 0; j < tamanho; j++) {
                writer.write(tabuleiro.getTabuleiro()[i][j]);
                if (j < tamanho-1) {
                    writer.write(",");
                }
            }
            writer.newLine();
        }
    }

    // Função para escrever os navios
    private static void escreverNavios(BufferedWriter writer, List<Navio> navios) throws IOException {
        for (Navio navio : navios) {
            writer.write(navio.getTamanho() + ",");
            List<Coordenada> coordenadas = navio.getCoordenadas();
            for (Coordenada atual : coordenadas) {
                writer.write(atual.getLinha() + "-" + atual.getColuna() + "-" + atual.isAtingido());
                if (coordenadas.indexOf(atual) < coordenadas.size()-1) {
                    writer.write(";");
                }
            }
            writer.write("," + navio.isAfundado());
            writer.newLine();
        }
    }

    // Função para ler o tabuleiro
    private static void lerTabuleiro(BufferedReader reader, Tabuleiro tabuleiro) throws IOException {
        int tamanho = tabuleiro.getTamanho();
        for (int i = 0; i < tamanho; i++) {
            String linha = reader.readLine();
            if (linha != null) {
                String[] celulas = linha.split(",");
                for (int j = 0; j < tamanho && j < celulas.length; j++) {
                    tabuleiro.setCelula(List.of(i, j), celulas[j].charAt(0));
                }
            }
        }
    }

    // Função para ler os navios
    private static void lerNavios(BufferedReader reader, Tabuleiro tabuleiro) throws IOException {
        String linha;
        while ((linha = reader.readLine()) != null && !linha.startsWith("TABULEIRO_")) {
            String[] partesNavio = linha.split(",");
            if (partesNavio.length >= 2) {
                
                // Criar navio 
                int tamanho = Integer.parseInt(partesNavio[0]);
                Navio navio = new Navio(tamanho);
                String[] coordenadas = partesNavio[1].split(";");
                for (String coord : coordenadas) {
                    String[] valores = coord.split("-");
                    if (valores.length == 3) {

                        // Cria uma nova coordenada
                        int l = Integer.parseInt(valores[0]);
                        int c = Integer.parseInt(valores[1]);
                        Coordenada localizacao = new Coordenada(l, c);

                        // Marca a coordenada como atingida
                        boolean atingido = Boolean.parseBoolean(valores[2]);
                        localizacao.setAtingido(atingido);

                        // Adiciona a coordenada ao navio
                        navio.adicionarCoordenada(localizacao);
                    }
                }

                // Marca o navio como afundado
                if (partesNavio.length > 2) {
                    navio.setAfundado(Boolean.parseBoolean(partesNavio[2]));
                }
                tabuleiro.getNavios().add(navio);
            }
        }
    }
}
