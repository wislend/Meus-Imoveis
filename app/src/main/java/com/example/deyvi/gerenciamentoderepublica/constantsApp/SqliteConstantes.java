package com.example.deyvi.gerenciamentoderepublica.constantsApp;

public interface SqliteConstantes {

     String LOG = "BANCO";
     String ERRO_SELECT_ENDERECO = "NÃO FOI POSSIVEL ENCONTRAR O ENDERCO COM ID";
     String ERRO_SELECT_IMOVEL = "NÃO FOI POSSIVEL ENCONTRAR O IMOVEL COM NOME";
     String ERRO_SELECT_MOVEL = "NÃO FOI POSSIVEL SELECIONAR TODOS IMOVEIS";
     String SUCESSFUL_SELECT_IMOVEL = "IMOVEL ENCONTRADO";
     String SUCESSFUL_SELECT_IMOVEL_ALL = "LISTA DE IMOVEL ENCONTRADO";
     String ERRO_SALVAR_LOCADOR = "NÃO FOI POSSIVEL SALVAR CLIENTE.";
     String ERRO_SALVAR_MORADOR = "NAO FOI POSSIVEL SALVAR MORADOR";
     String ERRO_SALVAR_ENDERECO = "NAO FOI POSSIVEL SALVAR ENDERECO";
     String ERRO_SALVAR_IMOVEL = "NAO FOI POSSIVEL SALVAR IMOVEL";
     String ENDERECO_SALVO = "ENDERECO_SALVO";
     String MORADOR_SALVO = "MORADOR_SALVO";
     String LOCADOR_SALVO = "LOCADOR_SALVO";
     String ENDERECO_IMOVEL = "ENDERECO_IMOVEL";
     String ERRO_ENDERECO_EXISTS = "NÃO FOI POSSIVEL ENCONTRAR O IMOVEL COM NOME";
     String ERRO_QUARTO_EXISTS = "NAO FOI POSSIVEL VERIFICAR QUARTO POR NOME";
     String ERRO_MOVEL_EXISTS = "NAO FOI POSSIVEL VERIFICAR MOVEL POR NOME";
     String ERRO_MORADOR_EXISTS = "NÃO FOI POSSIVEL ENCONTRAR O MORADOR COM TELEFONE";
     String IMOVEL_JA_CADASTRADO = "Verificamos que você já possui um imovél cadastrado com esse nome";
     String MORADOR_JA_CADASTRADO = "Morador já cadastrado em outro quarto.";
     String QUARTO_SALVO_SUCESSO = "Quarto Salvo com sucesso!";
     String ERRO_LISTA_IMOVEIS = "erro ao tentar selecionar a lista de imoveis";

               //UPDATE
     String ATUALIZACAO_MOVEL_SUCESSO = "atualizado com sucesso";
     String ERRO_ATUALIZAR_MOVEL = "não foi possivel atualizar movel";
}
