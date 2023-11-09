package com.example.recyclerview;

import java.util.ArrayList;
import java.util.List;

public class PiratasRepositorio {

    List<Pirata> piratas = new ArrayList<>();

    interface Callback {
        void cuandoFinalice(List<Pirata> piratas);
    }

    PiratasRepositorio(){
        piratas.add(new Pirata("Marshall D. Teach", "También conocido como Barbanegra, es el capitan de los piratas de barbanegra y es el único en poder consumir 2 frutas del diablo la de los terremotos y la de la oscuridad.", "Capitan"));
        piratas.add(new Pirata("Edward Newgate", "También conocido como Barbablanca apesar de no tener barba... Solamente tiene un bigote blanco muy grandes, el hombre mas fuerte del mundo antes de morir tenia la fruta del diablo de los terremotos y peleaba de tu a tu con el antiguo rey de los piratas", "Capitan"));
        piratas.add(new Pirata("Marco el Fénix", "Es el primer comandante de los piratas de Barbablanca, consumió la fruta del animal mitológico el ave Fénix y eso lo hace practicamente inmortal, con sus llamas se puede regenerar y tambien", "Médico"));
        piratas.add(new Pirata("Roronoa Zoro", "Es alcoholico y racista y el vicecapitán de la banda de los sombreros de paja, utiliza es estilo 3 katanas y es descendiente de Ryuma el mejor espadachin de la historia", "Espadachin"));
    }

    List<Pirata> obtener() {
        return piratas;
    }

    void insertar(Pirata pirata, Callback callback){
        piratas.add(pirata);
        callback.cuandoFinalice(piratas);
    }

    void eliminar(Pirata pirata, Callback callback) {
        piratas.remove(pirata);
        callback.cuandoFinalice(piratas);
    }

    void actualizar(Pirata pirata, float valoracion, Callback callback) {
        pirata.peligrosidad = valoracion;
        callback.cuandoFinalice(piratas);
    }
}
