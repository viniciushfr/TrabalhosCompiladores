#include <iostream>
#include <string>
#include <stack>
#include <map>
#include <vector>
#include <fstream>
#include <sstream>

using namespace std;

void split(const string &s, char delim, vector<string> &elems);
vector<string> split(const string &s, char delim);

string input;
stack<char> pilha;

//ANALIZADOR SINTATICO ------------------------------------------------------------------------------------------------------------------------------
map<char, map<char, string> > tabela;


void encherTabela(){
    fstream fs("gramatica.txt", fstream::in|fstream::out);
    while(fs.good()){
        string linha;
        getline(fs,linha);
        vector<string> derivacoes = split(split(linha,':')[1],'|');
        //cout << linha << endl;
    }
    
    tabela['E']['i'] = "TA";
    tabela['E']['-'] = "TA";
    tabela['A']['+'] = "+TA";
    tabela['A']['$'] = "&";
    tabela['T']['i'] = "FB";
    tabela['T']['-'] = "FB";
    tabela['B']['+'] = "&";
    tabela['B']['*'] = "*FB";
    tabela['B']['$'] = "&";
    tabela['F']['i'] = "i";
    tabela['F']['-'] = "-F";
}



int entradaIndex =-1;
string proximoToken(){
    
}

void printPilha(){
    stack<char> aux = pilha;
    while(!aux.empty()){
        cout << aux.top() << " ";
        aux.pop();
    }
    cout << endl;
}
bool isTerminal(char x){
    if(x == '+' ||x=='*'||x=='-'||x=='i'){
        return true;
    }else{
        return false;
    }
}
int main(){
    encherTabela();
    pilha.push('$');
    pilha.push('E');
    char entrada[]={'i','+','i','*','i','+','i','+','i','*','i','*','i','$'};
    //pilha.push('A');
    //cout<<pegarDerivacao('+');
 
    int ponteiro = 0;
    
    printPilha();
    while(pilha.top()!='$'){
        char topo = pilha.top();
        if(isTerminal(topo)){
            if(topo == entrada[ponteiro]){
                pilha.pop();
    
                ponteiro++;    
                
            }else{
                cout << "ERRO1" << endl;
                return 0;
            }
        }else{
            //string derivacao = pegarDerivacao(entrada[ponteiro]);
            string derivacao = tabela[pilha.top()][entrada[ponteiro]];
            //cout << pilha.top() << " - " << entrada[ponteiro] << " - " << derivacao << endl;
            if(derivacao.size()!=0){
                pilha.pop();
                if(derivacao!="&"){
                    for(int j=derivacao.size()-1;j>=0;j--){
                        char empilhar = derivacao[j];
                        pilha.push(empilhar);
                    }
                }
                printPilha();
            }else{
                cout << "ERRO2" << endl;
                return 0;
            }
        }
    }
    if(entrada[ponteiro]=='$'){
        cout << "DEU BOM PORRA" << endl;
    }else{
        cout << "DEU BOM NAO CARAI" << endl;
    }
}
//FIM DO ANALIZADOR SINTATICO -----------------------------------------------------------------------------------------------------------------------

void split(const string &s, char delim, vector<string> &elems) {
    stringstream ss(s);
    string item;
    while (getline(ss, item, delim)) {
        elems.push_back(item);
    }
}


vector<string> split(const string &s, char delim) {
    vector<string> elems;
    split(s, delim, elems);
    return elems;
}