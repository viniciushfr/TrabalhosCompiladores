Simulador receber 2 arquivos de entrada, um com a maquina seguindo o formato:
1ª linha: estado inicial expl: S1
2ª linha: estados finais
3ª linha: simbulo branco
4ª não lembro insira um '%'
A partir da quinta sao as instrucoes da seguinte maneira: 
Estado atual , Sibulo Lido, Simbulo escrito, Mover para, Proximo estado - exmplo:
s0,a,A,D,s1

Exemplo de um arquivo mt:
------------------------------
s0
s4
_
%
s0,%,%,D,s0
s0,a,A,D,s1
s0,B,B,D,s3
s0,_,_,D,s4
s1,a,a,D,s1
s1,B,B,D,s1
s1,b,B,E,s2
s2,a,a,E,s2
s2,B,B,E,s2
s2,A,A,D,s0
s3,B,B,D,s3
s3,_,_,E,s4
------------------------------
E um com as fitas de entrada.
Exemplo de um arquivo in:
------------------------------
aabb
bba
a
aabbb
aaabbb
aaaabbbb
------------------------------
