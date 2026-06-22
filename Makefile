# only works with the Java extension of yacc: 
# byacc/j from http://troi.lincom-asg.com/~rjamison/byacc/

#JFLEX  = java -jar jflex.jar
BYACCJ = ./yacc.linux -tv -J
JFLEX = jflex
#BYACCJ = byaccj -tv -J
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
	@echo "   1. TESTANDO ESCOPO"
	@echo "=============================================="
	@echo "[SUCESSO]"
	@java Parser teste_escopo_sucesso.mjava
	@echo "\n[ERRO]"
	@java Parser teste_escopo_erro.mjava
	@echo "\n=============================================="
	@echo "   2. TESTANDO TIPOS"
	@echo "=============================================="
	@echo "[SUCESSO]"
	@java Parser teste_tipo_sucesso.mjava
	@echo "\n[ERRO]"
	@java Parser teste_tipo_erro.mjava
	@echo "\n=============================================="
	@echo "   3. TESTANDO DECLARAÇÃO PRÉVIA"
	@echo "=============================================="
	@echo "[SUCESSO]"
	@java Parser teste_declaracao_sucesso.mjava
	@echo "\n[ERRO]"
	@java Parser teste_declaracao_erro.mjava
	@echo "\n=============================================="
	@echo "   4. TESTANDO POLIMORFISMO"
	@echo "=============================================="
	@echo "[SUCESSO]"
	@java Parser teste_polimorfismo_sucesso.mjava
	@echo "\n[ERRO]"
	@java Parser teste_polimorfismo_erro.mjava