/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package formas_normais;

import java.util.ArrayList;
import trabalhoglc.Simplificacao;
/**
 *
 * @author vinic
 */
public class Chomsky {

    private String variaveis;
    private ArrayList<String> gramatica;
    private Simplificacao simplificacao;
    public Chomsky() {
        variaveis = "ABCDEFGHIJKLMNOPQRSTUVXWYZΑΒΓΔΕϜΖΗΘΙΚΛΜΝΞΟΠϺϞΡΣΤΥΦΧΨΩϠ";
        simplificacao= new Simplificacao();
        char letra = 'A';
        letra++;
    }
    
    public ArrayList aplicarChomsky (ArrayList<String> gramatica){
        gramatica = simplificacao.simplificar(gramatica);
//        System.out.println("1ªEtapa:  " + gramatica);
        gramatica = segundaEtapa(gramatica);
//        System.out.println("2ªEtapa:  " + gramatica);
        gramatica = TerceiraEtapa(gramatica);
//        System.out.println("3ªEtapa:  " + gramatica);
        return gramatica;
        
    }
    
    
    private ArrayList segundaEtapa(ArrayList<String> gramatica) {
        ArrayList<String> producoes = new ArrayList();
        for (int i = 0; i < gramatica.size(); i++) {
            char lastTerminal=' ';
            String replace = "";
            String nova ="";
            boolean nadd = true;
            String prod = gramatica.get(i);
            String x = prod.substring(prod.indexOf(':')+1);
            for (int d = gramatica.get(i).length() - 1; d >= 0; d--) {
                if (Character.isLowerCase(prod.charAt(d)) && x.length() > 1  && prod.charAt(d)!=lastTerminal) {
                    lastTerminal= prod.charAt(d);
                    String newVar;
                    if((retornaVarDaProducao(prod.charAt(d)+"",producoes))==null){
                        newVar = getNewVar(gramatica);
                        nova = (newVar + ":" + prod.charAt(d));
                        prod = prod.replace(prod.charAt(d),newVar.charAt(0));
                        producoes.add(nova);
                         nadd=false;
                    }else{
                        newVar = retornaVarDaProducao(prod.charAt(d)+"", producoes);                       
                        prod = prod.replace(prod.charAt(d),newVar.charAt(0));  
                    }                     
                }

            }
                    producoes.add(i,prod);
        }

        this.gramatica = producoes;
        return producoes;
    }

    private ArrayList TerceiraEtapa(ArrayList<String> gramatica) {
        ArrayList<String> novasProducoes = new ArrayList();
        for (String prod : gramatica) {

            novasProducoes = concatenaArrayList(novasProducoes, ProdTam2(prod));
        }
        this.gramatica = novasProducoes;
        return novasProducoes;
    }

    private ArrayList ProdTam2(String producao) {

        ArrayList<String> novas = new ArrayList();

        int tamProd = (producao.substring(producao.indexOf(":"))).length() - 1;

        while (tamProd > 2) {
            String doisUltimos = producao.substring(producao.length() - 2);
            String primeiraMetade = producao.substring(0, producao.length() - 2);

            String nova = (getNewVar(this.gramatica) + ":" + doisUltimos);

            novas.add(nova);
            producao = (primeiraMetade + nova.substring(0, nova.indexOf(":")));

            tamProd = (producao.substring(producao.indexOf(":"))).length() - 1;
        }
        novas.add(producao);

        return novas;
    }

    private String TodasVariaveis(ArrayList<String> producoes) {
        String todasVar = "";
        for (String aux : producoes) {
            todasVar = todasVar + aux.substring(0, aux.indexOf(":"));
        }
        return subtrairString(variaveis, todasVar);

    }

    public ArrayList concatenaArrayList(ArrayList<String> primeiro, ArrayList<String> segundo) {
        for (String prod : segundo) {
            primeiro.add(prod);
        }
        return primeiro;
    }

    private String getNewVar(ArrayList<String> producoes) {
        variaveis = TodasVariaveis(producoes);
        String retorno = variaveis.charAt(0) + "";
        variaveis= variaveis.substring(1);
        return retorno;
    }

    private String subtrairString(String primeira, String segunda) {
        for (int i = 0; i < segunda.length(); i++) {
            primeira = primeira.replace(segunda.charAt(i) + "", "");
        }
        return primeira;
    }
    private String retornaVarDaProducao (String prod, ArrayList<String> gramatica){
        for(String p:gramatica){
            if(p.substring(2).equals(prod))
                return p.substring(0,1);
            
        }
        return null;
    }
}
