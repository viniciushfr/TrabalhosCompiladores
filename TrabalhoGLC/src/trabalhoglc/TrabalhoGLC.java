/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trabalhoglc;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author a120071
 */
public class TrabalhoGLC {
 
    public static void main(String[] args) throws FileNotFoundException {
        Simplificacao simplificar = new Simplificacao(); 
        ArrayList<String> gramatica = simplificar.derivacoes("C:\\Users\\vinic\\Documents\\guilherme_vinicius\\TrabalhoTeoria\\Exemplos\\exemplo2.glc");
//        System.out.println(gramatica);
        simplificar.simplificar(gramatica);
//        System.out.println(gramatica);
        
    }
}
