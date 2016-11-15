/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package formas_normais;

import java.util.ArrayList;
import trabalhoglc.Simplificacao;
import formas_normais.Chomsky;

/**
 *
 * @author vinic
 */
public class Greibach {

    private String ordem;
    private String vars;
    private Chomsky chomsky;
    private Simplificacao simplificacao;
    int cont;
    int cont1;

    public Greibach() {
        cont = 1;
        cont1 =1;
        simplificacao = new Simplificacao();
        chomsky = new Chomsky();
    }

    public ArrayList asplicarGreibach(ArrayList<String> gramatica) {
        gramatica = simplificacao.simplificar(gramatica);
//        System.out.println("1ªEtapa: " + gramatica);
//        gramatica = chomsky.aplicarChomsky(gramatica);
//        System.out.println("Chonsky: "+ gramatica);
        gramatica = segundaEtapa(gramatica);
//        System.out.println("2ªEtapa:");
//        for(String producao:gramatica)
//        System.out.println(producao);
        gramatica = terceiraEtapa(gramatica);
//       System.out.println("3ªEtapa: " + gramatica);
//        gramatica = quartaEtapa(gramatica);
//        System.out.println("4ªEtapa: " + gramatica);
        gramatica = quintaEtapa(gramatica);
//        System.out.println("5ªEtapa: " + gramatica);
        return gramatica;
    }

    public ArrayList segundaEtapa(ArrayList<String> gramatica) {
        ArrayList<String> auxiliar = new ArrayList();
        auxiliar = gramatica;
        for (String prod : gramatica) {
            int i = gramatica.indexOf(prod);
            String x =auxiliar.get(i);
            if(x.charAt(0)!='X'){
            String newVar = getNewVar('a');
            char oldVar = prod.charAt(0);
            auxiliar = substituiVarNaLista(newVar, oldVar + "", auxiliar);           
            }            
        }
        gramatica= auxiliar;
        return auxiliar;
    }

    public ArrayList terceiraEtapa(ArrayList<String> gramatica) {
        ArrayList<String> auxiliar = new ArrayList();
        int oldSize = -1;
        int nowSize = auxiliar.size();

        while (nowSize > oldSize) {
//            System.out.println("repeticao do while");
            for(int i=0;i<gramatica.size();i++){
                String prod = gramatica.get(i);
//                System.out.println("producao:     " + prod);
                if (VerificaArMaiorQueAs(prod) == 1) {
//                    System.out.println(prod + "  <- errada");
//                    System.out.println("");
                    ArrayList<String> producoesDeS = producoesDeVar(AsDeProd(prod), gramatica);
                    String aux = prod;
                    int cont = 0;
                 
//                    System.out.println("remover:"+prod);
                    gramatica.remove(prod);
                    
                    for (String producao : producoesDeS) {
                        String primeiraParte = aux.substring(0, prod.indexOf(":"));
                        String As = AsDeProd(aux);
//                    System.out.println(As);
                        prod = (primeiraParte + ":" + producao + aux.substring((primeiraParte.length() + As.length()) + 1));
//                        System.out.println("nova    " + prod);
                        gramatica.add(prod);
//                        System.out.println("adicionada");
                        cont++;
//                       System.out.println(gramatica);
                    }
                }

               
            }
            gramatica = quartaEtapa(gramatica);
            
            oldSize = nowSize;
            nowSize = gramatica.size();
            auxiliar = new ArrayList();
//            System.out.println(gramatica);
        }
        return gramatica;

    }

    ArrayList quartaEtapa(ArrayList<String> gramatica) {
        ArrayList<String> auxiliar = new ArrayList();
        String A = "";
        String B = "";
        for(int i=0;i<gramatica.size();i++){
            String prod = gramatica.get(i);
            boolean nEntroIf = true;
            String aux = prod;
            if (VerificaArMaiorQueAs(prod) == 2) {
                nEntroIf = false;
//                System.out.println("prod removido por ter recursao: "+prod);
                A = prod.substring(0, prod.indexOf(":"));
                B = getNewVar('b');
                String ar = AsDeProd(prod);
                
                String bra = (B + ":" + prod.substring(prod.indexOf(":") + ar.length() + 1));
                auxiliar.add(bra);
                gramatica.remove(prod);
                i--;
                prod = (B + ":" + prod.substring(prod.indexOf(":") + ar.length() + 1) + B);
//                
//                System.out.println("novas producoes da recursao: ");
//                System.out.println(bra);
//                System.out.println(prod);
                auxiliar.add(prod);
                
                auxiliar = chomsky.concatenaArrayList(auxiliar, colocaBrFinalAr(gramatica, A, B));
            }
            if(nEntroIf)
            auxiliar.add(prod);
            
        }
        return auxiliar;
    }

    ArrayList colocaBrFinalAr(ArrayList<String> gramatica, String ar, String br) {
        ArrayList<String> auxiliar = new ArrayList();
//        System.out.println(gramatica);
        for (String prod : gramatica) {
            if (prod.substring(0, prod.indexOf(":")).equals(ar)) {
//                System.out.println("entrou no br final ar: " +prod);
                prod = (prod + br);
                auxiliar.add(prod);
//                System.out.println( prod);
            }

        }  
//        System.out.println("");
        return auxiliar;
    }

    ArrayList quintaEtapa(ArrayList<String> gramatica) {
        ArrayList<String> auxiliar = new ArrayList();
        for (String prod : gramatica) {
            String aux = prod;
            String as = AsDeProd(aux);
            String primeiraParte = aux.substring(0, aux.indexOf(":"));
            String segundaParte = aux.substring(as.length()+primeiraParte.length()+1 );
            if (!AsDeProd(prod).equals("")) {
                ArrayList<String> producoesDeVar = producoesDeVar(AsDeProd(prod), gramatica);
                int cont = 0;
                String nova;
                
                for (String p : producoesDeVar) {                   
                    prod = (primeiraParte + ":"+ p + segundaParte);
                    if (cont != producoesDeVar.size()-1) {
                        auxiliar.add(prod);
                    }
                    cont++;
                }
            }
            auxiliar.add(prod);
        }

        return auxiliar;
    }

    private ArrayList substituiVarNaLista(CharSequence newVar, CharSequence oldVar, ArrayList<String> gramatica) {
        ArrayList<String> vetorAuxiliar = new ArrayList();
        for (String prod : gramatica) {
            if (prod.contains(oldVar)) {
                prod = prod.replace(oldVar, newVar);
            }
            vetorAuxiliar.add(prod);
        }
        return vetorAuxiliar;
    }

    private String getNewVar(char x) {
        String newVar = "";
        if (x == 'a') {            
            newVar = ("X(" + cont + ")");
            cont++;
        }else if(x == 'b') {
            newVar = ("B(" + cont1 + ")");
            cont1++;
        }
        return newVar;
    }


    private ArrayList producoesDeVar(String var, ArrayList<String> gramatica) {
        ArrayList<String> retorno = new ArrayList();
        for (String prod : gramatica) {
            if ((prod.substring(0, prod.indexOf(":"))).equals(var)) {
                retorno.add(prod.substring(prod.indexOf(":") + 1));
            }
        }
        return retorno;
    }

    int VerificaArMaiorQueAs(String producao) {
        char varUsada = 'X';
//        char varRecursao ='B';
//        System.out.println(producao.charAt(producao.indexOf(":") + 1));
        if (producao.charAt(producao.indexOf(":") + 1)==varUsada && producao.charAt(0)==varUsada){
//            System.out.println("entrou if");
            String varProdus = producao.substring(producao.indexOf(":") + 1);
//        System.out.println(varProdus);
//        System.out.println(producao.substring(2,producao.indexOf(":")-1));

            int primeiro = Integer.parseInt(producao.substring(2, producao.indexOf(":") - 1));

            int i = 0;

            while (varProdus.charAt(i) != ')') {
                i++;
            }
            int segundo = Integer.parseInt(varProdus.substring(2, i));

//            System.out.println(primeiro);
//            System.out.println(segundo);
            if (primeiro > segundo) {
                return 1;
            }
            if (primeiro == segundo) {
                return 2;
            }
        }
        return 0;
    }

    String AsDeProd(String prod) {
//        char varUsada='X';
        if (!Character.isLowerCase(prod.charAt(prod.indexOf(":") + 1))) {
            String varProdus = prod.substring(prod.indexOf(":") + 1);
            int i = 0;
            while (varProdus.charAt(i) != ')') {
                i++;
            }
            String retorno = varProdus.substring(0, i + 1);
//            System.out.println("retorno" + retorno);
            return retorno;
        }
        return "";
    }
}
