package com.mygdx.game;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.mygdx.game.model.CartaBaralho;
import com.mygdx.game.model.Constantes;
import com.mygdx.game.model.Constantes.Valores;
import com.mygdx.game.model.EstadoJogo;
import com.mygdx.game.model.Jogo;
import com.mygdx.game.model.Zona;
import com.mygdx.game.model.Zona.TipoZona;

public class JogoEscova extends Jogo {
    public JogoEscova(EstadoJogo estado) {
        super(estado);
    }

    @Override
    public Zona defineDeck() {
        return ZonaBuilder.buildDeck(1, new Constantes.Valores[] { Valores.OITO, Valores.NOVE, Valores.DEZ, Valores.CORINGA });
    }

    @Override
    public void aposDefineValores(Map<Valores, Integer> valores) {
        valores.put(Valores.DAMA, 8);
        valores.put(Valores.VALETE, 9);
        valores.put(Valores.REI, 10);
    }

    @Override
    public List<Zona> defineZonas() {
        List<Zona> zonas = new ArrayList<>();
        for (String playerName : getUltimoMemento().getPlayerNames()) {
            zonas.add(ZonaBuilder.buildZona("Mao" + playerName, new String[] { playerName }, getUltimoMemento(), 3,
                    TipoZona.PRIVADA));
            zonas.add(ZonaBuilder.buildZona("Monte" + playerName, new String[] { playerName }, getUltimoMemento(), 0,
                    TipoZona.PROTEGIDA));
        }
        zonas.add(ZonaBuilder.buildZona("Mesa", getUltimoMemento().getPlayerNames(), getUltimoMemento(), 4, TipoZona.PUBLICA));
        zonas.add(getUltimoMemento().getDeck());
        return zonas;
    }

    @Override
    public String validaMove(List<Zona> origens, Zona destino) {
        String msg = super.validaMove(origens, destino);
        if (msg == null) {
            int soma = 0;
            boolean so1Mao = false;
            if (isMoveu()) {
                return "Voce j� jogou nesta rodada!";
            }
            if (destino.getZonaPrimeiraOrdem().getName().equals("Mesa")) {
                if (!(origens.get(0) instanceof CartaBaralho)) {
                    return "Origem deve ser uma carta.";
                }
                if (origens.size() != 1) {
                    return "S� pode por uma carta na mesa.";
                }
            } else {
                for (Zona origem : origens) {
                    if (origem instanceof CartaBaralho) {
                        CartaBaralho carta = (CartaBaralho) origem;
                        soma += getUltimoMemento().getValores().get(carta.getValor());
                        if (carta.getParent().getName().startsWith("Mao")) {
                            if (so1Mao == true) {
                                return "S� vale usar uma carta da m�o";
                            }
                            so1Mao = true;
                        }
                    } else {
                        return "Origem " + origem.getName() + "n�o � uma carta";
                    }
                }
                if (soma != 15) {
                    return "Esta jogada n�o soma 15";
                }
            }
        }
        return msg;
    }

    @Override
    public void aposReceberEstado() {
        // TODO ta dando nullpointer aki pois ainda nao foram definidas as
        // zonas, pensar uma forma de alteracoes de variaveis de EstadoJogo nao
        // sejam brodcast toda hora.
        // TODO nome do player devria sermais facil de acessar aqui.
        /*if (getUltimoMemento().getZonaByName("Mao" + nomePlayer).getComponents().length == 0) {
            System.out.println("Hora de dar as cartas!");
        }*/
    }
}