/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package formas_normais;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import trabalhoglc.Simplificacao;

/**
 *
 * @author vinic
 */
public class teste {

    public static void main(String[] args) throws FileNotFoundException {
        Simplificacao simplificacao = new Simplificacao();
        ArrayList<String> g = new ArrayList();
//        g = simplificacao.derivacoes();
        g.add("S:AA");
        g.add("S:a");
        g.add("A:SS");
        g.add("A:b");
//        g.add("E:aF"); 
//        g.add("E:bA"); 
//        g.add("F:m"); 
//        g.add("F:n"); 
//        g.add("A:Ei");
//        g.add("A:j");
//        g.add("B:b");
//        g.add("B:Ab");
//        g.add("A:aSb");
//        g.add("B:bB");
//        g.add("B:aSB");
//        g.add("B:a");
//        g.add("B:aSb");
//        
//        Chomsky chomsky = new Chomsky();
//        System.out.println("entrada:  " + g);
//        chomsky.aplicarChomsky(g);
        
        
        Greibach greibach = new Greibach();
        System.out.println("entrada: " + g);
        g=greibach.asplicarGreibach(g);

       for(String producao:g){
           System.out.println(producao);
       }
//        System.out.println(greibach.AsDeProd("X(2):aX(2)yX(2)a"));
//        System.out.println(greibach.VerificaArMaiorQueAs("X(0):X(2)yX(2)"));
////        System.out.println(greibach.producoesDeVar("X(2)",g));
//          System.out.println(greibach.quartaEtapa(g));
//        

    }

}
