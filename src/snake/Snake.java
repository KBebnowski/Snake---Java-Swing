/*
// GRA SNAKE
//
// Opis:
// Czerwone jablko zwieksza dlugosc weza i score, czyli nasz wynik
// zamiane stron anuluje czerowne i zlote jablko
// podczas zamiany stron waz moze nachodzic na swoj ogon
// zlote jablko anuluje zmiane stron, spowalnia oraz zwieksza dlugosc weza o 2
// zielone jablko resetuje zmiane predkosci
// po zjedzeniu zamiany stron, przyspieszenia oraz spowolnienia nie pokazuja sie one ponownie
// kazde zjedzenie powoduje zresetowanie miejsc przedmiotow
// po zjedzeniu przedmitow korzystnych dla gracza glowa weza zaswieci sie na zolto
// po zjedzeniu przedmiotow niekorzystnych dla gracza glowa weza zaswieci sie na zielono
// gra posiada 4 poziomy, z kazdym kolejnym poziomem wzrasta predkosc poruszania sie weza
// 1 level jest wstepem dlatego mapa jest przejrzysta, istnieje tylko jeden item, a waz porusza sie powoli
// w 2 levelu zastosowano tlo ktore powoduje ze ciezej odnalezc czerwona kropke, nowicjusz moze zjesc wiec zielona.
// zielone jablko pojawia sie od poczatku 2 levela zeby gracz po jego zjedzeniu zauwazyl ze plansza wrocila do poprzedniego poziomu
// dzieki temu bedzie wiedzial ze zjedzenie zielonego jablka powoduje zmniejszenie weza, ktore nie jest widoczne na 1 rzut oka
// w 3 lvl-u mapa jest bardziej przejrzysta lecz od poczatku pojawia sie item zamiany kierunkow, celowo jest on slabo widoczny na planszy
// kiedy dlugosc ogona przekracza dlugosc 14 pojawiaja sie itemy zwiekszajace i zmniejszajace predkosc pozwala to przygotowac sie graczowi
// na poziom ostatni, w ktorym szybkosc jest jeszcze wieksza
//
// Created by Kamil Bębnowski 27.08.19r.
*/

package snake;

import javax.swing.*;
import java.awt.*;

public class Snake extends JFrame {

    public Snake(){
        add(new Game());
        setResizable(false);
        pack();

        setTitle("Snake");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        JFrame start = new Snake();
        start.setVisible(true);
    }
}
