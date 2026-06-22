%{
  import java.io.*;
%}

%token CLASS, EXTENDS, PUBLIC, STATIC, VOID, MAIN, APP
%token STRING, INT, BOOLEAN, IF, ELSE, WHILE, SOUT
%token NEW, TRUE, FALSE, THIS, LENGTH, RETURN
%token AND, EQ, NEQ
%token Identifier, Number

%nonassoc '<' EQ NEQ
%left AND
%left '+' '-' 
%left '*' '/'
%right '!'
%left '.'

%type <sval> Identifier
%type <ival> Number
%type <obj> Type BaseType Exp

%%

Program : ClassDeclarationList MainClass ;

ClassDeclarationList : ClassDeclarationList ClassDeclaration | ;
                         
MainClass : APP '{' PUBLIC STATIC VOID MAIN '(' STRING '[' ']' Identifier ')' '{' 
            { escopoAtual = "App.main"; } 
            Statement '}' '}' { escopoAtual = "Global"; }
          ;

ClassDeclaration : CLASS Identifier { 
                        classeAtual = $2; 
                        escopoAtual = $2; 
                        TS_entry nodo = ts.pesquisa($2);
                        if (nodo != null) yyerror("(sem) Classe >" + $2 + "< ja declarada");
                        else ts.insert(new TS_entry($2, Tp_CLASSE, ClasseID.TipoBase));
                   } 
                   '{' VarDeclarationList MethodDeclarationList '}' 
                   { escopoAtual = "Global"; classeAtual = ""; }
                 | CLASS Identifier EXTENDS Identifier {
                        classeAtual = $2; 
                        escopoAtual = $2; 
                        
                        TS_entry pai = ts.pesquisa($4);
                        if (pai == null) yyerror("(sem) Classe pai >" + $4 + "< nao declarada");
                        
                        TS_entry novaClasse = new TS_entry($2, Tp_CLASSE, ClasseID.TipoBase);
                        if (pai != null) novaClasse.setPai(pai);
                        
                        if (ts.pesquisa($2) != null) yyerror("(sem) Classe >" + $2 + "< ja declarada");
                        else ts.insert(novaClasse);
                   }
                   '{' VarDeclarationList MethodDeclarationList '}'
                   { escopoAtual = "Global"; classeAtual = ""; }
                 ;

MethodDeclarationList : MethodDeclarationList MethodDeclaration | ;

MethodDeclaration : PUBLIC Type Identifier { 
                        metodoAtual = $3; 
                        escopoAtual = classeAtual + "." + $3; 
                        if (ts.pesquisa(escopoAtual) != null) yyerror("(sem) Metodo >" + $3 + "< ja declarado");
                        else ts.insert(new TS_entry(escopoAtual, (TS_entry)$2, ClasseID.NomeFuncao));
                    } 
                    '(' ParamList ')' '{' DeclOrStatList RETURN Exp ';' '}' 
                    { 
                        if ((TS_entry)$11 != (TS_entry)$2 && (TS_entry)$11 != Tp_ERRO) 
                            yyerror("(sem) Retorno do metodo >" + metodoAtual + "< incompativel.");
                        escopoAtual = classeAtual; 
                        metodoAtual = ""; 
                    }
                  ;

VarDeclarationList : VarDeclarationList VarDeclaration | ;

VarDeclaration : Type Identifier ';' { 
                    String idEscopo = escopoAtual + "." + $2;
                    if (ts.pesquisa(idEscopo) != null) yyerror("(sem) Atributo >" + $2 + "< ja declarado");
                    else ts.insert(new TS_entry(idEscopo, (TS_entry)$1, ClasseID.VarLocal)); 
                 }
               ;

DeclOrStatList : Identifier Identifier ';' {
                    String idEscopo = escopoAtual + "." + $2;
                    TS_entry tipoObj = ts.pesquisa($1);
                    if (tipoObj == null) { yyerror("(sem) Tipo/Classe >" + $1 + "< nao declarado"); tipoObj = Tp_ERRO; }
                    if (ts.pesquisa(idEscopo) != null) yyerror("(sem) Variavel >" + $2 + "< ja declarada");
                    else ts.insert(new TS_entry(idEscopo, tipoObj, ClasseID.VarLocal));
                 } DeclOrStatList
               | BaseType Identifier ';' {
                    String idEscopo = escopoAtual + "." + $2;
                    if (ts.pesquisa(idEscopo) != null) yyerror("(sem) Variavel >" + $2 + "< ja declarada");
                    else ts.insert(new TS_entry(idEscopo, (TS_entry)$1, ClasseID.VarLocal));
                 } DeclOrStatList
               | Identifier '=' Exp ';' { validaAtribuicao($1, (TS_entry)$3); } StatementList
               | '{' StatementList '}'
               | IF '(' Exp ')' Statement ELSE Statement StatementList { if ((TS_entry)$3 != Tp_BOOL) yyerror("(sem) Condicao do IF deve ser boolean"); }
               | WHILE '(' Exp ')' Statement StatementList { if ((TS_entry)$3 != Tp_BOOL) yyerror("(sem) Condicao do WHILE deve ser boolean"); }
               | SOUT '(' Exp ')' ';' StatementList { if ((TS_entry)$3 != Tp_INT) yyerror("(sem) SOUT aceita apenas INT"); }
               | ;

ParamList : Type Identifier { 
                String idEscopo = escopoAtual + "." + $2;
                ts.insert(new TS_entry(idEscopo, (TS_entry)$1, ClasseID.NomeParam));
            } ParamListAux
          | ;

ParamListAux : ',' Type Identifier { 
                  String idEscopo = escopoAtual + "." + $3;
                  ts.insert(new TS_entry(idEscopo, (TS_entry)$2, ClasseID.NomeParam));
               } ParamListAux
             | ; 

BaseType : BOOLEAN { $$ = Tp_BOOL; }
         | INT { $$ = Tp_INT; }
         ;
    
Type : BaseType { $$ = $1; }
     | Identifier { 
            TS_entry nodo = ts.pesquisa($1);
            if (nodo == null) yyerror("(sem) Classe >" + $1 + "< nao declarada");
            $$ = nodo != null ? nodo : Tp_ERRO;
       }
     ;

Statement : '{' StatementList '}' 
          | IF '(' Exp ')' Statement ELSE Statement { if ((TS_entry)$3 != Tp_BOOL) yyerror("(sem) Condicao IF deve ser boolean"); }
          | WHILE '(' Exp ')' Statement { if ((TS_entry)$3 != Tp_BOOL) yyerror("(sem) Condicao WHILE deve ser boolean"); }
          | SOUT '(' Exp ')' ';' { if ((TS_entry)$3 != Tp_INT) yyerror("(sem) SOUT aceita apenas INT"); }
          | Identifier '=' Exp ';' { validaAtribuicao($1, (TS_entry)$3); }
          ;

StatementList : StatementList Statement | ;

Exp : Exp AND Exp { $$ = validaTipo(AND, (TS_entry)$1, (TS_entry)$3); }
    | Exp '<' Exp { $$ = validaTipo('<', (TS_entry)$1, (TS_entry)$3); }
    | Exp '+' Exp { $$ = validaTipo('+', (TS_entry)$1, (TS_entry)$3); }
    | Exp '-' Exp { $$ = validaTipo('-', (TS_entry)$1, (TS_entry)$3); }
    | Exp '*' Exp { $$ = validaTipo('*', (TS_entry)$1, (TS_entry)$3); }
    | Exp '/' Exp { $$ = validaTipo('/', (TS_entry)$1, (TS_entry)$3); }
    | Exp '.' Identifier '(' RealParamList ')' { 
            TS_entry classeExp = (TS_entry)$1;
            if (classeExp == Tp_ERRO) $$ = Tp_ERRO;
            else {
                TS_entry met = null;
                TS_entry classeAtualBusca = classeExp;
                
                while (classeAtualBusca != null) {
                    met = ts.pesquisa(classeAtualBusca.getId() + "." + $3);
                    if (met != null) break;
                    classeAtualBusca = classeAtualBusca.getPai();
                }
                
                if (met == null) { yyerror("(sem) Metodo >" + $3 + "< nao encontrado na classe " + classeExp.getId() + " nem nas superclasses"); $$ = Tp_ERRO; }
                else $$ = met.getTipo();
            }
      }
    | Number { $$ = Tp_INT; }
    | TRUE { $$ = Tp_BOOL; }
    | FALSE { $$ = Tp_BOOL; }
    | Identifier { 
            TS_entry var = buscaVariavelEscopo($1);
            if (var == null) { yyerror("(sem) Variavel >" + $1 + "< nao declarada"); $$ = Tp_ERRO; }
            else $$ = var.getTipo();
      }
    | THIS { $$ = ts.pesquisa(classeAtual); }
    | NEW Identifier '(' ')' { 
            TS_entry classe = ts.pesquisa($2);
            if (classe == null) { yyerror("(sem) Classe >" + $2 + "< nao encontrada"); $$ = Tp_ERRO; }
            else $$ = classe;
      }
    | '!' Exp { if ((TS_entry)$2 != Tp_BOOL) yyerror("(sem) Op '!' exige boolean"); $$ = Tp_BOOL; }
    | '(' Exp ')' { $$ = $2; }
    ;

RealParamList : Exp RealParamListAux | ;

RealParamListAux : ',' Exp RealParamListAux | ;

%%

  private Yylex lexer;
  private TabSimb ts;

  public static TS_entry Tp_INT = new TS_entry("int", null, ClasseID.TipoBase);
  public static TS_entry Tp_BOOL = new TS_entry("boolean", null, ClasseID.TipoBase);
  public static TS_entry Tp_ERRO = new TS_entry("_erro_", null, ClasseID.TipoBase);
  public static TS_entry Tp_CLASSE = new TS_entry("classe_base", null, ClasseID.TipoBase);

  private String escopoAtual = "Global";
  private String classeAtual = "";
  private String metodoAtual = "";

  private int yylex () {
    int yyl_return = -1;
    try {
      yylval = new ParserVal(0);
      yyl_return = lexer.yylex();
    } catch (IOException e) {
      System.err.println("IO error :"+e.getMessage());
    }
    return yyl_return;
  }

  public void yyerror (String error) {
    System.err.printf("Erro (linha: %2d) \tMensagem: %s\n", lexer.getLine(), error);
  }

  public Parser(Reader r) {
    lexer = new Yylex(r, this);
    ts = new TabSimb();
    ts.insert(Tp_ERRO);
    ts.insert(Tp_INT);
    ts.insert(Tp_BOOL);
  }

  public void setDebug(boolean debug) { yydebug = debug; }
  public void listarTS() { ts.listar(); }

  private TS_entry buscaVariavelEscopo(String id) {
      TS_entry var = ts.pesquisa(escopoAtual + "." + id); 
      if (var == null && !classeAtual.isEmpty()) {
          var = ts.pesquisa(classeAtual + "." + id); 
      }
      return var;
  }

  private boolean isSubtipo(TS_entry tipoFilho, TS_entry tipoPai) {
      if (tipoFilho == tipoPai) return true;
      if (tipoFilho == Tp_ERRO || tipoPai == Tp_ERRO) return true; 
      
      TS_entry atual = tipoFilho;
      while (atual != null) {
          if (atual == tipoPai) return true;
          atual = atual.getPai(); 
      }
      return false;
  }

  private void validaAtribuicao(String id, TS_entry tipoExp) {
      TS_entry var = buscaVariavelEscopo(id);
      if (var == null) yyerror("(sem) Variavel >" + id + "< nao declarada");
      else if (!isSubtipo(tipoExp, var.getTipo())) 
          yyerror("(sem) Tipos incompativeis para atribuicao: esperava " + var.getTipo().getId() + " mas recebeu " + tipoExp.getId());
  }

  private TS_entry validaTipo(int operador, TS_entry A, TS_entry B) {
      if (A == Tp_ERRO || B == Tp_ERRO) return Tp_ERRO;
      switch (operador) {
          case '+': case '-': case '*': case '/':
              if (A == Tp_INT && B == Tp_INT) return Tp_INT;
              yyerror("(sem) Tipos incompativeis para op aritmetica");
              break;
          case '<':
              if (A == Tp_INT && B == Tp_INT) return Tp_BOOL;
              yyerror("(sem) Tipos incompativeis para op relacional");
              break;
          case AND:
              if (A == Tp_BOOL && B == Tp_BOOL) return Tp_BOOL;
              yyerror("(sem) Tipos incompativeis para op logica");
              break;
      }
      return Tp_ERRO;
  }

  public static void main(String args[]) throws IOException {
    Parser yyparser;
    if (args.length > 0) {
      yyparser = new Parser(new FileReader(args[0]));
    } else {
      System.out.println("Modo interativo. Digite o codigo:");
      yyparser = new Parser(new InputStreamReader(System.in));
    }
    yyparser.yyparse();
    yyparser.listarTS();
    System.out.println("\nAnalise concluida!");
  }