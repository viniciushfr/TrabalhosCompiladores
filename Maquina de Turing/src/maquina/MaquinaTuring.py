import Arquivo
class MaquinaTuring(object):
    estado_inicial =''
    estados_finais =[]
    simbolo_branco =''
    inicio_fita =''
    transicao_list = []
    leitura_arquivos =None
    entrada =[]
    fita_saida=[]
    def __init__(self, url1,url2):
        leitura_arquivos = Arquivo.Arquivo(url1,url2)
        self.estado_inicial = leitura_arquivos.estado_inicial
        self.estados_finais = leitura_arquivos.estados_finais
        self.simbolo_branco = leitura_arquivos.branco
        self.inicio_fita = leitura_arquivos.inicio_fita
        self.transicao_list = leitura_arquivos.transicao_list
        self.entrada = leitura_arquivos.entrada

    def proximo_estado(self,estado_atual,leitura):
        retorno=[]
        
        for transicao in self.transicao_list:
            if(transicao.estado_atual == estado_atual and transicao.leitura == leitura):
                retorno.append(transicao.direcao)
                retorno.append(transicao.escrita)
                retorno.append(transicao.proximo_estado)
                return retorno
        return retorno
    
    
    def processar_fita(self, fita_str):
        fita = list(fita_str)
        leitura = 0
        estado_atual = self.estado_inicial
        resultado = -1
        while resultado ==  -1:
            
            if(self.verifica_estado_final(estado_atual)):
                self.fita_saida = fita
                parada = 1
                break                                   
            aux = self.proximo_estado(estado_atual,fita[leitura])
            if(len(aux) == 0):
                self.fita_saida = fita
                parada = 0
                break
            fita[leitura]=aux[1]
            if(aux[0] =='D'):
                leitura = leitura+1;
                if(leitura == len(fita)-1):
                    fita.append(self.simbolo_branco)
                estado_atual = aux[2]                
            elif(aux[0] =='E'):
                if(fita[leitura]==self.inicio_fita):
                    parada = 0
                    break
                leitura = leitura -1    
                estado_atual = aux[2]
        return parada    
        
    def processar_arquivo(self):
        arquivo = open('saida.out', 'r')
        texto = arquivo.readlines()
       
        for fita in self.entrada:
            resultado = self.processar_fita(fita)
            if resultado == 1:
                print (self.fita_saida)
                print ('Aceito\n')
                texto.append(fita + ';1')
            elif resultado == 0:
                print (self.fita_saida)
                print ('Rejeitado\n')
                texto.append(fita + ';0')
            arquivo = open('saida.out', 'w')    
            arquivo.writelines(texto)
            arquivo.close()
            
            
            
            
    def verifica_estado_final(self,estado):
        for x in self.estados_finais:
            if(x == estado):
                return True
        return False    
            
            
            
            
            
            
            
            
            