Simulador receber 2 arquivos de entrada, um com a maquina seguindo o formato:<br />
1ª linha: estado inicial expl: S1.<br />
2ª linha: estados finais.<br />
3ª linha: simbulo branco.<br />
4ª não lembro insira um '%'.<br />
A partir da quinta sao as instrucoes da seguinte maneira: <br />
Estado atual , Sibulo Lido, Simbulo escrito, Mover para, Proximo estado - exmplo:<br />
s0,a,A,D,s1<br />

Exemplo de um arquivo mt:<br />
------------------------------
s0<br />
s4<br />
_<br />
%<br />
s0,%,%,D,s0<br />
s0,a,A,D,s1<br />
s0,B,B,D,s3<br />
s0,_,_,D,s4<br />
s1,a,a,D,s1<br />
s1,B,B,D,s1<br />
s1,b,B,E,s2<br />
s2,a,a,E,s2<br />
s2,B,B,E,s2<br />
s2,A,A,D,s0<br />
s3,B,B,D,s3<br />
s3,_,_,E,s4<br />

------------------------------
E um com as fitas de entrada.<br />
Exemplo de um arquivo in:<br />
------------------------------
aabb<br />
bba<br />
a<br />
aabbb<br />
aaabbb<br />
aaaabbbb<br />

------------------------------
