package com.batalha.naval;

import java.util.ArrayList;
import java.util.List;

public abstract class Unidade {
    protected final int tamanho;
    protected final List<Coordenada> coordenadas;

    public Unidade(int tamanho) {
        this.tamanho = tamanho;
        this.coordenadas = new ArrayList<>();
    }

    public int getTamanho() {
        return tamanho;
    }

    public List<Coordenada> getCoordenadas() {
        return coordenadas;
    }

    public abstract void adicionarCoordenada(Coordenada coordenada);
    public abstract boolean estaNaCoordenada(List<Integer> posicao);
}
