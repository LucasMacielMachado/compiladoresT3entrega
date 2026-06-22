# Tarefa 3 - Análise Semântica (MiniJava)

Implementação da análise semântica da linguagem MiniJava em uma única passagem. Arrays ficaram de fora conforme as simplificações do trabalho.

A Tabela de Símbolos usa um ArrayList básico de `TS_entry`. O controle de escopo é feito concatenando o caminho numa string (ex: `Oficina.organizar.qtd`). Pro polimorfismo dar certo, o `TS_entry` tem um ponteiro pra classe pai, permitindo que o parser suba na hierarquia pra achar métodos herdados que não estão no escopo local.

O analisador valida:
- Uso de identificadores não declarados e redeclaração no mesmo escopo
- Vazamento de variáveis locais entre métodos
- Tipagem estrita nas atribuições (barrando boolean em int, por exemplo)
- Operações matemáticas restritas a int e IF/WHILE restritos a boolean
- SOUT travado para imprimir apenas inteiros
- Subtipos e herança nas atribuições e chamadas de método

Foram feitos 8 testes `.mjava` com tema de manutenção automotiva. São 4 pares (um de sucesso e um forçando o erro semântico) pra testar todos os cenários de escopo, tipos, ordem de declaração e polimorfismo.

Pra compilar o JFlex/Byaccj e rodar todos os testes em sequência:

make clean
make test