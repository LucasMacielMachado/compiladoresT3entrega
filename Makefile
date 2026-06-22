# only works with the Java extension of yacc: 
# byacc/j from http://troi.lincom-asg.com/~rjamison/byacc/

#JFLEX  = java -jar jflex.jar
#BYACCJ = ./yacc.linux -tv -J
JFLEX = jflex
BYACCJ = byaccj -tv -J
JAVAC  = javac

# targets:

all: Parser.class

run: Parser.class
	java Parser

build: clean Parser.class

clean:
	rm -f *~ *.class *.o *.s Yylex.java Parser.java y.output

Parser.class: Yylex.java Parser.java
	$(JAVAC) *.java

Yylex.java: lexico.flex
	$(JFLEX) lexico.flex

Parser.java: miniJava.y Yylex.java
	$(BYACCJ) miniJava.y

test: all
	@echo "\n=============================================="
	@echo "[1/5] TESTANDO SUCESSO BÁSICO"
	@echo "=============================================="
	@java Parser p1_sucesso.mjava
	@echo "\n=============================================="
	@echo "[2/5] TESTANDO SUCESSO - POLIMORFISMO"
	@echo "=============================================="
	@java Parser sucesso_polimorfismo.mjava
	@echo "\n=============================================="
	@echo "[3/5] TESTANDO ERRO - ESCOPO FECHADO"
	@echo "=============================================="
	@java Parser p1_erro_escopo.mjava
	@echo "\n=============================================="
	@echo "[4/5] TESTANDO ERRO - TIPOS INCOMPATÍVEIS"
	@echo "=============================================="
	@java Parser p1_erro_tipo.mjava
	@echo "\n=============================================="
	@echo "[5/5] TESTANDO ERRO - DECLARAÇÃO PRÉVIA"
	@echo "=============================================="
	@java Parser p1_erro_declaracao.mjava