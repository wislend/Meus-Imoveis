package com.example.deyvi.gerenciamentoderepublica.bll;


import com.example.deyvi.gerenciamentoderepublica.entitys.Quarto;

public class Quartos {

    private com.example.deyvi.gerenciamentoderepublica.dal.Quartos quartos;

    public Quartos() {
        quartos = new com.example.deyvi.gerenciamentoderepublica.dal.Quartos();
    }

    public Long salvarQuarto(Quarto quarto){
        return quartos.save(quarto);
    }

    public boolean quartoExiste(Integer numero){
        return quartos.exists(numero);
    }


}