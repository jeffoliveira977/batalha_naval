package com.batalha.naval;
import java.util.List;

public class Navio extends Unidade {
    private boolean afundado;

    public Navio(int tamanho) {
        super(tamanho);
        this.afundado = false;
    }

    @Override
    public void adicionarCoordenada(Coordenada coordenada) {
        coordenadas.add(coordenada);
    }

    @Override
    public boolean estaNaCoordenada(List<Integer> posicao) {
        for (Coordenada coord : coordenadas) {
            if (coord.getLinha() == posicao.get(0) && coord.getColuna() == posicao.get(1)) {
                return true;
            }
        }
        return false;
    }

    public void marcarAtingido(List<Integer> posicao) {
        for (Coordenada coord : coordenadas) {
            if (coord.getLinha() == posicao.get(0) && coord.getColuna() == posicao.get(1)) {
                coord.setAtingido(true);
                break;
            }
        }
        verificarAfundamento();
    }

    public boolean isAfundado() {
        return this.afundado;
    }

    public void setAfundado(boolean afundado) {
        this.afundado = afundado;
    }

    private void verificarAfundamento() {
        if (coordenadas.isEmpty()) {
            return;
        }

        boolean todosAtingidos = true;
        for (Coordenada coordenada : coordenadas) {
            if (!coordenada.isAtingido()) {
                todosAtingidos = false;
                break;
            }
        }

        if (todosAtingidos) {
            afundado = true;
        }
    }
}