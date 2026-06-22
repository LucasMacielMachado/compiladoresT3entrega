LucasMacielMachado
Lucca Bibiano Garcia 

# Análise Semântica (MiniJava)


Este repositório contém uma implementação de análise semântica para a linguagem MiniJava, feita em uma única passada. Para simplificar, suporte a arrays foi omitido conforme o enunciado do trabalho.

Como funciona (visão geral):
- A Tabela de Símbolos é implementada como um `ArrayList` de `TS_entry`.
- O controle de escopo é realizado concatenando o caminho dos identificadores em uma string (ex.: `Oficina.organizar.qtd`).
- Para suportar polimorfismo, cada `TS_entry` pode apontar para a classe pai, permitindo que o analisador suba na hierarquia e encontre métodos herdados que não estão no escopo local.

O analisador valida os principais aspectos semânticos, por exemplo:
- Identificadores não declarados e redeclarações no mesmo escopo.
- Vazamento de variáveis locais entre métodos.
- Tipagem estrita em atribuições (por exemplo, impedir atribuir `boolean` a `int`).
- Operações aritméticas apenas com `int`; condições de `if`/`while` apenas com `boolean`.
- `System.out.println` (SOUT) limitado para imprimir inteiros conforme as regras do trabalho.
- Subtipos e herança em atribuições e chamadas de método.

Testes
- Foram adicionados 8 arquivos de teste `.mjava`  4 casos de sucesso e 4 casos que forçam erros semânticos.  
Como compilar e rodar os testes utilizando o Makefile 

```sh
make clean
make test
```