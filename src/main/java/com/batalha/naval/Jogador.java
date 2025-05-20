package com.batalha.naval;

import java.util.List;
import java.util.Scanner;

public abstract class Jogador {
    protected Tabuleiro tabuleiro;
    protected Tabuleiro tabuleiroOponente;
    protected int tamanho;

    public Jogador(Tabuleiro tabuleiro, Tabuleiro tabuleiroOponente) {
        this.tabuleiro = tabuleiro;
        this.tabuleiroOponente = tabuleiroOponente;
    }

    public Tabuleiro getTabuleiro() {
        return this.tabuleiro;
    }

    public abstract List<Integer> realizarDisparo(Scanner scanner);
    public abstract void registrarDisparo(List<Integer> disparo);
}