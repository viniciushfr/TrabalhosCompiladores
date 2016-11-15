
class Arquivo(object):
    url_mt =''
    url_in =''
    estado_inicial =''
    estados_finais=[]
    branco=''
    inicio_fita=''
    entrada=[]
    transicao_list = []
    def __init__(self, url1, url2):    
        self.url_mt = url1
        self.url_in = url2
        Arquivo.leitura_mt(self)
        Arquivo.leitura_in(self)
        
    def leitura_mt(self):
        arq = open(self.url_mt,'r')
        arquivo = arq.readlines()
        i =0
        while i != len(arquivo):
            if i == 0:
                self.estado_inicial = arquivo[i][:-1]
            elif i == 1:
                self.estados_finais = arquivo[i][:-1].split(',')
            elif i == 2:
                self.branco = arquivo[i][:-1]
            elif i == 3:
                self.inicio_fita = arquivo[i]
            elif i >= 4:
                transicao = Transicao()
                linha = arquivo[i][:-1]
                list_aux = linha.split(',')
                transicao.estado_atual = list_aux[0]
                transicao.leitura = list_aux[1]
                transicao.escrita = list_aux[2]
                transicao.direcao = list_aux[3]
                transicao.proximo_estado = list_aux[4]
                self.transicao_list.append(transicao)
            i=i+1    

    def leitura_in(self):
        arq = open(self.url_in,'r')
        arquivo = arq.readlines()
        i =0
        while i != len(arquivo):
            self.entrada.append(self.inicio_fita[:-1] + arquivo[i][:-1])
            i = i+1   
            
            
class Transicao():
    estado_atual =''
    leitura =''
    escrita=''
    direcao=''
    proximo_estado=''