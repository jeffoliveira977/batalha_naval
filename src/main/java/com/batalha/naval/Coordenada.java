package com.batalha.naval;

import java.util.List;

public class Coordenada {
    private final int linha;
    private final int coluna;
    private boolean atingido;

    public Coordenada(int linha, int coluna) {
        this.linha = linha;
        this.coluna = coluna;
        this.atingido = false;
    }

    public Coordenada(List<Integer> posicao) {
        this(posicao.get(0), posicao.get(1));
    }

    public int getLinha() {
        return this.linha;
    }

    public int getColuna() {
        return this.coluna;
    }

    public List<Integer> getPosicao() {
        return List.of(this.linha, this.coluna);
    }

    public boolean isAtingido() {
        return this.atingido;
    }

    public void setAtingido(boolean atingido) {
        this.atingido = atingido;
    }
}