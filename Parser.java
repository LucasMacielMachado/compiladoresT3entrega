//### This file created by BYACC 1.8(/Java extension  1.15)
//### Java capabilities added 7 Jan 97, Bob Jamison
//### Updated : 27 Nov 97  -- Bob Jamison, Joe Nieten
//###           01 Jan 98  -- Bob Jamison -- fixed generic semantic constructor
//###           01 Jun 99  -- Bob Jamison -- added Runnable support
//###           06 Aug 00  -- Bob Jamison -- made state variables class-global
//###           03 Jan 01  -- Bob Jamison -- improved flags, tracing
//###           16 May 01  -- Bob Jamison -- added custom stack sizing
//###           04 Mar 02  -- Yuval Oren  -- improved java performance, added options
//###           14 Mar 02  -- Tomas Hurka -- -d support, static initializer workaround
//### Please send bug reports to tom@hukatronic.cz
//### static char yysccsid[] = "@(#)yaccpar	1.8 (Berkeley) 01/20/90";






//#line 2 "miniJava.y"
  import java.io.*;
//#line 19 "Parser.java"




public class Parser
{

boolean yydebug;        //do I want debug output?
int yynerrs;            //number of errors so far
int yyerrflag;          //was there an error?
int yychar;             //the current working character

//########## MESSAGES ##########
//###############################################################
// method: debug
//###############################################################
void debug(String msg)
{
  if (yydebug)
    System.out.println(msg);
}

//########## STATE STACK ##########
final static int YYSTACKSIZE = 500;  //maximum stack size
int statestk[] = new int[YYSTACKSIZE]; //state stack
int stateptr;
int stateptrmax;                     //highest index of stackptr
int statemax;                        //state when highest index reached
//###############################################################
// methods: state stack push,pop,drop,peek
//###############################################################
final void state_push(int state)
{
  try {
		stateptr++;
		statestk[stateptr]=state;
	 }
	 catch (ArrayIndexOutOfBoundsException e) {
     int oldsize = statestk.length;
     int newsize = oldsize * 2;
     int[] newstack = new int[newsize];
     System.arraycopy(statestk,0,newstack,0,oldsize);
     statestk = newstack;
     statestk[stateptr]=state;
  }
}
final int state_pop()
{
  return statestk[stateptr--];
}
final void state_drop(int cnt)
{
  stateptr -= cnt; 
}
final int state_peek(int relative)
{
  return statestk[stateptr-relative];
}
//###############################################################
// method: init_stacks : allocate and prepare stacks
//###############################################################
final boolean init_stacks()
{
  stateptr = -1;
  val_init();
  return true;
}
//###############################################################
// method: dump_stacks : show n levels of the stacks
//###############################################################
void dump_stacks(int count)
{
int i;
  System.out.println("=index==state====value=     s:"+stateptr+"  v:"+valptr);
  for (i=0;i<count;i++)
    System.out.println(" "+i+"    "+statestk[i]+"      "+valstk[i]);
  System.out.println("======================");
}


//########## SEMANTIC VALUES ##########
//public class ParserVal is defined in ParserVal.java


String   yytext;//user variable to return contextual strings
ParserVal yyval; //used to return semantic vals from action routines
ParserVal yylval;//the 'lval' (result) I got from yylex()
ParserVal valstk[];
int valptr;
//###############################################################
// methods: value stack push,pop,drop,peek.
//###############################################################
void val_init()
{
  valstk=new ParserVal[YYSTACKSIZE];
  yyval=new ParserVal();
  yylval=new ParserVal();
  valptr=-1;
}
void val_push(ParserVal val)
{
  if (valptr>=YYSTACKSIZE)
    return;
  valstk[++valptr]=val;
}
ParserVal val_pop()
{
  if (valptr<0)
    return new ParserVal();
  return valstk[valptr--];
}
void val_drop(int cnt)
{
int ptr;
  ptr=valptr-cnt;
  if (ptr<0)
    return;
  valptr = ptr;
}
ParserVal val_peek(int relative)
{
int ptr;
  ptr=valptr-relative;
  if (ptr<0)
    return new ParserVal();
  return valstk[ptr];
}
final ParserVal dup_yyval(ParserVal val)
{
  ParserVal dup = new ParserVal();
  dup.ival = val.ival;
  dup.dval = val.dval;
  dup.sval = val.sval;
  dup.obj = val.obj;
  return dup;
}
//#### end semantic value section ####
public final static short CLASS=257;
public final static short PUBLIC=258;
public final static short STATIC=259;
public final static short VOID=260;
public final static short MAIN=261;
public final static short APP=262;
public final static short STRING=263;
public final static short INT=264;
public final static short BOOLEAN=265;
public final static short IF=266;
public final static short ELSE=267;
public final static short WHILE=268;
public final static short SOUT=269;
public final static short NEW=270;
public final static short TRUE=271;
public final static short FALSE=272;
public final static short THIS=273;
public final static short LENGTH=274;
public final static short RETURN=275;
public final static short AND=276;
public final static short EQ=277;
public final static short NEQ=278;
public final static short Identifier=279;
public final static short Number=280;
public final static short YYERRCODE=256;
final static short yylhs[] = {                           -1,
    0,    4,    4,    8,    5,    9,    6,   11,   11,   13,
   12,   10,   10,   16,   17,   15,   18,   15,   20,   15,
   15,   15,   15,   15,   15,   22,   14,   14,   23,   21,
   21,    2,    2,    1,    1,    7,    7,    7,    7,    7,
   19,   19,    3,    3,    3,    3,    3,    3,    3,    3,
    3,    3,    3,    3,    3,    3,    3,   24,   24,   25,
   25,
};
final static short yylen[] = {                            2,
    2,    2,    0,    0,   17,    0,    7,    2,    0,    0,
   13,    2,    0,    3,    0,    5,    0,    5,    0,    6,
    3,    8,    6,    6,    0,    0,    4,    0,    0,    5,
    0,    1,    1,    1,    1,    3,    7,    5,    5,    4,
    2,    0,    3,    3,    3,    3,    3,    3,    6,    1,
    1,    1,    1,    1,    4,    2,    3,    2,    0,    3,
    0,
};
final static short yydefred[] = {                         3,
    0,    0,    0,    0,    1,    2,    6,    0,    0,    0,
   13,    0,    0,    0,   33,   32,   35,    0,   34,    0,
   12,    0,    0,    0,    7,    8,    0,   14,    0,    0,
   10,    0,    0,    0,    0,    0,    0,    0,    0,   26,
    0,    4,    0,    0,    0,    0,   27,    0,    0,    0,
    0,   42,    0,    0,    0,    0,    0,    0,   42,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   29,    0,   51,   52,   54,
   53,   50,    0,    0,    0,    0,    0,   15,    0,   21,
   41,   17,    0,    0,    0,    0,    0,   36,    5,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,   19,    0,    0,    0,    0,    0,
   40,   30,    0,   57,    0,    0,    0,    0,    0,    0,
    0,    0,   42,   42,   16,   42,   18,   11,    0,   38,
   39,   55,    0,    0,    0,    0,    0,    0,    0,    0,
   42,   37,    0,   58,   49,    0,    0,   60,
};
final static short yydgoto[] = {                          1,
   18,   19,   85,    2,    5,    6,   91,   45,    9,   13,
   20,   26,   33,   38,   54,   21,  114,  116,   67,  136,
   47,   43,  100,  150,  154,
};
final static short yysindex[] = {                         0,
    0, -188, -271, -109,    0,    0,    0, -229,  -93, -226,
    0, -203, -233, -172,    0,    0,    0, -189,    0, -102,
    0,   66,   53, -233,    0,    0, -143,    0, -158,   35,
    0,   32,   87, -151, -233,   90, -147,   97,   26,    0,
   27,    0,  104,  -82,  -50, -233,    0,  111,  112,  115,
  -58,    0, -116,  -98,  134,  140,  141,  128,    0,   67,
  -85,  -33,  -33,  -33,  139,  -33,  -73,  144,  -33,  -33,
  -33,  -33,  -33,  -67,   85,    0,  -79,    0,    0,    0,
    0,    0,  -33,  -33,    2,   18,   25,    0,   94,    0,
    0,    0,  100,   34,   41,   50,  119,    0,    0,  104,
  171,  167,   57,  -33,  -33,  -33,  -33,  -33,  -33,  -65,
  -50,  -50,  156,  -82,    0,  -82,   92,  -50,  -50,  161,
    0,    0,  181,    0,  162,  -41,  -21,  -21,  167,  167,
  183,  -40,    0,    0,    0,    0,    0,    0,  -39,    0,
    0,    0,  -33,  -50,  -50,  -50,  -50,  -50,  125,  185,
    0,    0,  -33,    0,    0,  -50,  125,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0, -101,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  189,    0,    0,    0,    0,    0,
    0,    0,  192,  -34,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  192,
    0,  -32,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  -34,    0,  -34,    0,    0,    0,    0,
    0,    0,    0,    0,   75,  132,   64,   70,  -25,   -5,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  193,    0,  -30,  -27,  -22,    0,  195,    0,
    0,    0,    0,    0,    0,  -20,  195,    0,
};
final static short yygindex[] = {                         0,
   98,   74,  216,    0,    0,    0,  113,    0,    0,    0,
    0,    0,    0,    0,  -63,    0,    0,    0,  116,    0,
  142,    0,    0,    0,   86,
};
final static int YYTABLESIZE=401;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         83,
  108,  106,   66,  107,  110,  109,   84,    7,   56,   56,
   56,   56,   56,    8,   56,   47,   47,   47,   47,   47,
  108,   47,   25,    9,  110,  109,   56,   56,   10,   11,
   15,   16,   12,   47,   47,   48,   48,   48,   48,   48,
   52,   48,  111,  108,  106,   17,  107,  110,  109,   59,
  135,   90,  137,   48,   48,   59,   14,   98,  112,  108,
  106,  105,  107,  110,  109,  113,  108,  106,    3,  107,
  110,  109,   59,    4,  118,  108,  106,  105,  107,  110,
  109,  119,  108,  106,  105,  107,  110,  109,   22,   23,
  120,  108,  106,  105,  107,  110,  109,  124,  108,  106,
  105,  107,  110,  109,   45,   27,   45,   45,   45,  105,
   46,   28,   46,   46,   46,   43,  105,   53,   43,   30,
   31,   29,   45,   45,   34,   32,   35,   36,   46,   46,
   39,   40,   37,   43,   43,  108,  106,   41,  107,  110,
  109,  108,  106,   61,  107,  110,  109,   46,   42,   44,
   62,   63,  115,  105,   64,   24,    9,   60,  117,  105,
  108,  106,   68,  107,  110,  109,  108,  106,  153,  107,
  110,  109,   44,   70,   74,   44,   69,  121,  105,   71,
   72,   15,   16,   48,  105,   49,   50,   53,   73,   53,
   44,   75,   55,   76,   56,   57,   51,   88,   55,  101,
   56,   57,   92,  108,  106,   58,  107,  110,  109,   99,
  123,   58,  110,  131,  134,   55,  138,   56,   57,  141,
   65,  142,  143,  132,  133,  155,  144,  148,   58,   28,
  139,  140,   31,   59,  104,   61,   77,   78,   79,   80,
   25,  122,  158,   56,   23,   81,   82,   24,  145,  146,
   47,  147,   20,    0,   22,    0,  151,    0,    0,    0,
  152,    0,    0,    0,    0,    0,  156,    0,    0,    0,
   48,    0,    0,    0,    0,    0,    0,  104,   86,   87,
    0,   89,    0,    0,   93,   94,   95,   96,   97,    0,
    0,    0,    0,  104,    0,    0,    0,    0,  102,  103,
  104,    0,    0,    0,    0,    0,    0,    0,    0,  104,
    0,    0,    0,    0,    0,    0,  104,    0,    0,  125,
  126,  127,  128,  129,  130,  104,    0,    0,    0,    0,
    0,    0,  104,    0,    0,    0,    0,    0,    0,   45,
    0,    0,    0,    0,    0,   46,    0,    0,    0,    0,
   43,    0,    0,    0,    0,    0,    0,    0,  149,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  157,  104,
    0,    0,    0,    0,    0,  104,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  104,    0,    0,    0,    0,    0,
  104,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         33,
   42,   43,   61,   45,   46,   47,   40,  279,   41,   42,
   43,   44,   45,  123,   47,   41,   42,   43,   44,   45,
   42,   47,  125,  125,   46,   47,   59,   60,  258,  123,
  264,  265,  259,   59,   60,   41,   42,   43,   44,   45,
  123,   47,   41,   42,   43,  279,   45,   46,   47,  123,
  114,  125,  116,   59,   60,  123,  260,  125,   41,   42,
   43,   60,   45,   46,   47,   41,   42,   43,  257,   45,
   46,   47,  123,  262,   41,   42,   43,   60,   45,   46,
   47,   41,   42,   43,   60,   45,   46,   47,  261,  279,
   41,   42,   43,   60,   45,   46,   47,   41,   42,   43,
   60,   45,   46,   47,   41,   40,   43,   44,   45,   60,
   41,   59,   43,   44,   45,   41,   60,   44,   44,  263,
  279,   24,   59,   60,   93,   91,   40,  279,   59,   60,
   41,  279,   35,   59,   60,   42,   43,   41,   45,   46,
   47,   42,   43,   46,   45,   46,   47,   44,  123,  123,
   40,   40,   59,   60,   40,  258,  258,   45,   59,   60,
   42,   43,  279,   45,   46,   47,   42,   43,   44,   45,
   46,   47,   41,   40,   59,   44,  275,   59,   60,   40,
   40,  264,  265,  266,   60,  268,  269,  114,   61,  116,
   59,  125,  266,  279,  268,  269,  279,   59,  266,  279,
  268,  269,   59,   42,   43,  279,   45,   46,   47,  125,
   40,  279,   46,  279,   59,  266,  125,  268,  269,   59,
  279,   41,   40,  111,  112,   41,  267,  267,  279,   41,
  118,  119,   41,   41,  276,   41,  270,  271,  272,  273,
  275,  100,  157,  276,  275,  279,  280,  275,  133,  134,
  276,  136,  275,   -1,  275,   -1,  144,   -1,   -1,   -1,
  148,   -1,   -1,   -1,   -1,   -1,  151,   -1,   -1,   -1,
  276,   -1,   -1,   -1,   -1,   -1,   -1,  276,   63,   64,
   -1,   66,   -1,   -1,   69,   70,   71,   72,   73,   -1,
   -1,   -1,   -1,  276,   -1,   -1,   -1,   -1,   83,   84,
  276,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  276,
   -1,   -1,   -1,   -1,   -1,   -1,  276,   -1,   -1,  104,
  105,  106,  107,  108,  109,  276,   -1,   -1,   -1,   -1,
   -1,   -1,  276,   -1,   -1,   -1,   -1,   -1,   -1,  276,
   -1,   -1,   -1,   -1,   -1,  276,   -1,   -1,   -1,   -1,
  276,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  143,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  153,  276,
   -1,   -1,   -1,   -1,   -1,  276,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,  276,   -1,   -1,   -1,   -1,   -1,
  276,
};
}
final static short YYFINAL=1;
final static short YYMAXTOKEN=280;
final static String yyname[] = {
"end-of-file",null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,"'!'",null,null,null,null,null,null,"'('","')'","'*'","'+'",
"','","'-'","'.'","'/'",null,null,null,null,null,null,null,null,null,null,null,
"';'","'<'","'='",null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,"'['",null,"']'",null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,"'{'",null,"'}'",null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,"CLASS","PUBLIC","STATIC","VOID",
"MAIN","APP","STRING","INT","BOOLEAN","IF","ELSE","WHILE","SOUT","NEW","TRUE",
"FALSE","THIS","LENGTH","RETURN","AND","EQ","NEQ","Identifier","Number",
};
final static String yyrule[] = {
"$accept : Program",
"Program : ClassDeclarationList MainClass",
"ClassDeclarationList : ClassDeclarationList ClassDeclaration",
"ClassDeclarationList :",
"$$1 :",
"MainClass : APP '{' PUBLIC STATIC VOID MAIN '(' STRING '[' ']' Identifier ')' '{' $$1 Statement '}' '}'",
"$$2 :",
"ClassDeclaration : CLASS Identifier $$2 '{' VarDeclarationList MethodDeclarationList '}'",
"MethodDeclarationList : MethodDeclarationList MethodDeclaration",
"MethodDeclarationList :",
"$$3 :",
"MethodDeclaration : PUBLIC Type Identifier $$3 '(' ParamList ')' '{' DeclOrStatList RETURN Exp ';' '}'",
"VarDeclarationList : VarDeclarationList VarDeclaration",
"VarDeclarationList :",
"VarDeclaration : Type Identifier ';'",
"$$4 :",
"DeclOrStatList : Identifier Identifier ';' $$4 DeclOrStatList",
"$$5 :",
"DeclOrStatList : BaseType Identifier ';' $$5 DeclOrStatList",
"$$6 :",
"DeclOrStatList : Identifier '=' Exp ';' $$6 StatementList",
"DeclOrStatList : '{' StatementList '}'",
"DeclOrStatList : IF '(' Exp ')' Statement ELSE Statement StatementList",
"DeclOrStatList : WHILE '(' Exp ')' Statement StatementList",
"DeclOrStatList : SOUT '(' Exp ')' ';' StatementList",
"DeclOrStatList :",
"$$7 :",
"ParamList : Type Identifier $$7 ParamListAux",
"ParamList :",
"$$8 :",
"ParamListAux : ',' Type Identifier $$8 ParamListAux",
"ParamListAux :",
"BaseType : BOOLEAN",
"BaseType : INT",
"Type : BaseType",
"Type : Identifier",
"Statement : '{' StatementList '}'",
"Statement : IF '(' Exp ')' Statement ELSE Statement",
"Statement : WHILE '(' Exp ')' Statement",
"Statement : SOUT '(' Exp ')' ';'",
"Statement : Identifier '=' Exp ';'",
"StatementList : StatementList Statement",
"StatementList :",
"Exp : Exp AND Exp",
"Exp : Exp '<' Exp",
"Exp : Exp '+' Exp",
"Exp : Exp '-' Exp",
"Exp : Exp '*' Exp",
"Exp : Exp '/' Exp",
"Exp : Exp '.' Identifier '(' RealParamList ')'",
"Exp : Number",
"Exp : TRUE",
"Exp : FALSE",
"Exp : Identifier",
"Exp : THIS",
"Exp : NEW Identifier '(' ')'",
"Exp : '!' Exp",
"Exp : '(' Exp ')'",
"RealParamList : Exp RealParamListAux",
"RealParamList :",
"RealParamListAux : ',' Exp RealParamListAux",
"RealParamListAux :",
};

//#line 165 "miniJava.y"

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

  // Busca a variavel subindo do escopo local para o atributo da classe
  private TS_entry buscaVariavelEscopo(String id) {
      TS_entry var = ts.pesquisa(escopoAtual + "." + id); 
      if (var == null && !classeAtual.isEmpty()) {
          var = ts.pesquisa(classeAtual + "." + id); 
      }
      return var;
  }

  private void validaAtribuicao(String id, TS_entry tipoExp) {
      TS_entry var = buscaVariavelEscopo(id);
      if (var == null) yyerror("(sem) Variavel >" + id + "< nao declarada");
      else if (var.getTipo() != tipoExp && var.getTipo() != Tp_ERRO && tipoExp != Tp_ERRO) 
          yyerror("(sem) Tipos incompativeis para atribuicao: " + var.getTipoStr() + " = " + tipoExp.getTipoStr());
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
//#line 465 "Parser.java"
//###############################################################
// method: yylexdebug : check lexer state
//###############################################################
void yylexdebug(int state,int ch)
{
String s=null;
  if (ch < 0) ch=0;
  if (ch <= YYMAXTOKEN) //check index bounds
     s = yyname[ch];    //now get it
  if (s==null)
    s = "illegal-symbol";
  debug("state "+state+", reading "+ch+" ("+s+")");
}





//The following are now global, to aid in error reporting
int yyn;       //next next thing to do
int yym;       //
int yystate;   //current parsing state from state table
String yys;    //current token string


//###############################################################
// method: yyparse : parse input and execute indicated items
//###############################################################
int yyparse()
{
boolean doaction;
  init_stacks();
  yynerrs = 0;
  yyerrflag = 0;
  yychar = -1;          //impossible char forces a read
  yystate=0;            //initial state
  state_push(yystate);  //save it
  val_push(yylval);     //save empty value
  while (true) //until parsing is done, either correctly, or w/error
    {
    doaction=true;
    if (yydebug) debug("loop"); 
    //#### NEXT ACTION (from reduction table)
    for (yyn=yydefred[yystate];yyn==0;yyn=yydefred[yystate])
      {
      if (yydebug) debug("yyn:"+yyn+"  state:"+yystate+"  yychar:"+yychar);
      if (yychar < 0)      //we want a char?
        {
        yychar = yylex();  //get next token
        if (yydebug) debug(" next yychar:"+yychar);
        //#### ERROR CHECK ####
        if (yychar < 0)    //it it didn't work/error
          {
          yychar = 0;      //change it to default string (no -1!)
          if (yydebug)
            yylexdebug(yystate,yychar);
          }
        }//yychar<0
      yyn = yysindex[yystate];  //get amount to shift by (shift index)
      if ((yyn != 0) && (yyn += yychar) >= 0 &&
          yyn <= YYTABLESIZE && yycheck[yyn] == yychar)
        {
        if (yydebug)
          debug("state "+yystate+", shifting to state "+yytable[yyn]);
        //#### NEXT STATE ####
        yystate = yytable[yyn];//we are in a new state
        state_push(yystate);   //save it
        val_push(yylval);      //push our lval as the input for next rule
        yychar = -1;           //since we have 'eaten' a token, say we need another
        if (yyerrflag > 0)     //have we recovered an error?
           --yyerrflag;        //give ourselves credit
        doaction=false;        //but don't process yet
        break;   //quit the yyn=0 loop
        }

    yyn = yyrindex[yystate];  //reduce
    if ((yyn !=0 ) && (yyn += yychar) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn] == yychar)
      {   //we reduced!
      if (yydebug) debug("reduce");
      yyn = yytable[yyn];
      doaction=true; //get ready to execute
      break;         //drop down to actions
      }
    else //ERROR RECOVERY
      {
      if (yyerrflag==0)
        {
        yyerror("syntax error");
        yynerrs++;
        }
      if (yyerrflag < 3) //low error count?
        {
        yyerrflag = 3;
        while (true)   //do until break
          {
          if (stateptr<0)   //check for under & overflow here
            {
            yyerror("stack underflow. aborting...");  //note lower case 's'
            return 1;
            }
          yyn = yysindex[state_peek(0)];
          if ((yyn != 0) && (yyn += YYERRCODE) >= 0 &&
                    yyn <= YYTABLESIZE && yycheck[yyn] == YYERRCODE)
            {
            if (yydebug)
              debug("state "+state_peek(0)+", error recovery shifting to state "+yytable[yyn]+" ");
            yystate = yytable[yyn];
            state_push(yystate);
            val_push(yylval);
            doaction=false;
            break;
            }
          else
            {
            if (yydebug)
              debug("error recovery discarding state "+state_peek(0)+" ");
            if (stateptr<0)   //check for under & overflow here
              {
              yyerror("Stack underflow. aborting...");  //capital 'S'
              return 1;
              }
            state_pop();
            val_pop();
            }
          }
        }
      else            //discard this token
        {
        if (yychar == 0)
          return 1; //yyabort
        if (yydebug)
          {
          yys = null;
          if (yychar <= YYMAXTOKEN) yys = yyname[yychar];
          if (yys == null) yys = "illegal-symbol";
          debug("state "+yystate+", error recovery discards token "+yychar+" ("+yys+")");
          }
        yychar = -1;  //read another
        }
      }//end error recovery
    }//yyn=0 loop
    if (!doaction)   //any reason not to proceed?
      continue;      //skip action
    yym = yylen[yyn];          //get count of terminals on rhs
    if (yydebug)
      debug("state "+yystate+", reducing "+yym+" by rule "+yyn+" ("+yyrule[yyn]+")");
    if (yym>0)                 //if count of rhs not 'nil'
      yyval = val_peek(yym-1); //get current semantic value
    yyval = dup_yyval(yyval); //duplicate yyval if ParserVal is used as semantic value
    switch(yyn)
      {
//########## USER-SUPPLIED ACTIONS ##########
case 4:
//#line 30 "miniJava.y"
{ escopoAtual = "App.main"; }
break;
case 5:
//#line 31 "miniJava.y"
{ escopoAtual = "Global"; }
break;
case 6:
//#line 34 "miniJava.y"
{ 
                        classeAtual = val_peek(0).sval; 
                        escopoAtual = val_peek(0).sval; 
                        TS_entry nodo = ts.pesquisa(val_peek(0).sval);
                        if (nodo != null) yyerror("(sem) Classe >" + val_peek(0).sval + "< ja declarada");
                        else ts.insert(new TS_entry(val_peek(0).sval, Tp_CLASSE, ClasseID.TipoBase));
                   }
break;
case 7:
//#line 42 "miniJava.y"
{ escopoAtual = "Global"; classeAtual = ""; }
break;
case 10:
//#line 47 "miniJava.y"
{ 
                        metodoAtual = val_peek(0).sval; 
                        escopoAtual = classeAtual + "." + val_peek(0).sval; 
                        if (ts.pesquisa(escopoAtual) != null) yyerror("(sem) Metodo >" + val_peek(0).sval + "< ja declarado");
                        else ts.insert(new TS_entry(escopoAtual, (TS_entry)val_peek(1).obj, ClasseID.NomeFuncao));
                    }
break;
case 11:
//#line 54 "miniJava.y"
{ 
                        /* Corrigido de $12 para $11 para pegar o 'Exp' corretamente*/
                        if ((TS_entry)val_peek(2).obj != (TS_entry)val_peek(11).obj && (TS_entry)val_peek(2).obj != Tp_ERRO) 
                            yyerror("(sem) Retorno do metodo >" + metodoAtual + "< incompativel.");
                        escopoAtual = classeAtual; 
                        metodoAtual = ""; 
                    }
break;
case 14:
//#line 66 "miniJava.y"
{ 
                    String idEscopo = escopoAtual + "." + val_peek(1).sval;
                    if (ts.pesquisa(idEscopo) != null) yyerror("(sem) Atributo >" + val_peek(1).sval + "< ja declarado");
                    else ts.insert(new TS_entry(idEscopo, (TS_entry)val_peek(2).obj, ClasseID.VarLocal)); 
                 }
break;
case 15:
//#line 73 "miniJava.y"
{
                    String idEscopo = escopoAtual + "." + val_peek(1).sval;
                    TS_entry tipoObj = ts.pesquisa(val_peek(2).sval);
                    if (tipoObj == null) { yyerror("(sem) Tipo/Classe >" + val_peek(2).sval + "< nao declarado"); tipoObj = Tp_ERRO; }
                    if (ts.pesquisa(idEscopo) != null) yyerror("(sem) Variavel >" + val_peek(1).sval + "< ja declarada");
                    else ts.insert(new TS_entry(idEscopo, tipoObj, ClasseID.VarLocal));
                 }
break;
case 17:
//#line 80 "miniJava.y"
{
                    String idEscopo = escopoAtual + "." + val_peek(1).sval;
                    if (ts.pesquisa(idEscopo) != null) yyerror("(sem) Variavel >" + val_peek(1).sval + "< ja declarada");
                    else ts.insert(new TS_entry(idEscopo, (TS_entry)val_peek(2).obj, ClasseID.VarLocal));
                 }
break;
case 19:
//#line 85 "miniJava.y"
{ validaAtribuicao(val_peek(3).sval, (TS_entry)val_peek(1).obj); }
break;
case 22:
//#line 87 "miniJava.y"
{ if ((TS_entry)val_peek(5).obj != Tp_BOOL) yyerror("(sem) Condicao do IF deve ser boolean"); }
break;
case 23:
//#line 88 "miniJava.y"
{ if ((TS_entry)val_peek(3).obj != Tp_BOOL) yyerror("(sem) Condicao do WHILE deve ser boolean"); }
break;
case 24:
//#line 89 "miniJava.y"
{ if ((TS_entry)val_peek(3).obj != Tp_INT) yyerror("(sem) SOUT aceita apenas INT"); }
break;
case 26:
//#line 92 "miniJava.y"
{ 
                String idEscopo = escopoAtual + "." + val_peek(0).sval;
                ts.insert(new TS_entry(idEscopo, (TS_entry)val_peek(1).obj, ClasseID.NomeParam));
            }
break;
case 29:
//#line 98 "miniJava.y"
{ 
                  String idEscopo = escopoAtual + "." + val_peek(0).sval;
                  ts.insert(new TS_entry(idEscopo, (TS_entry)val_peek(1).obj, ClasseID.NomeParam));
               }
break;
case 32:
//#line 104 "miniJava.y"
{ yyval.obj = Tp_BOOL; }
break;
case 33:
//#line 105 "miniJava.y"
{ yyval.obj = Tp_INT; }
break;
case 34:
//#line 108 "miniJava.y"
{ yyval.obj = val_peek(0).obj; }
break;
case 35:
//#line 109 "miniJava.y"
{ 
            TS_entry nodo = ts.pesquisa(val_peek(0).sval);
            if (nodo == null) yyerror("(sem) Classe >" + val_peek(0).sval + "< nao declarada");
            yyval.obj = nodo != null ? nodo : Tp_ERRO;
       }
break;
case 37:
//#line 117 "miniJava.y"
{ if ((TS_entry)val_peek(4).obj != Tp_BOOL) yyerror("(sem) Condicao IF deve ser boolean"); }
break;
case 38:
//#line 118 "miniJava.y"
{ if ((TS_entry)val_peek(2).obj != Tp_BOOL) yyerror("(sem) Condicao WHILE deve ser boolean"); }
break;
case 39:
//#line 119 "miniJava.y"
{ if ((TS_entry)val_peek(2).obj != Tp_INT) yyerror("(sem) SOUT aceita apenas INT"); }
break;
case 40:
//#line 120 "miniJava.y"
{ validaAtribuicao(val_peek(3).sval, (TS_entry)val_peek(1).obj); }
break;
case 43:
//#line 125 "miniJava.y"
{ yyval.obj = validaTipo(AND, (TS_entry)val_peek(2).obj, (TS_entry)val_peek(0).obj); }
break;
case 44:
//#line 126 "miniJava.y"
{ yyval.obj = validaTipo('<', (TS_entry)val_peek(2).obj, (TS_entry)val_peek(0).obj); }
break;
case 45:
//#line 127 "miniJava.y"
{ yyval.obj = validaTipo('+', (TS_entry)val_peek(2).obj, (TS_entry)val_peek(0).obj); }
break;
case 46:
//#line 128 "miniJava.y"
{ yyval.obj = validaTipo('-', (TS_entry)val_peek(2).obj, (TS_entry)val_peek(0).obj); }
break;
case 47:
//#line 129 "miniJava.y"
{ yyval.obj = validaTipo('*', (TS_entry)val_peek(2).obj, (TS_entry)val_peek(0).obj); }
break;
case 48:
//#line 130 "miniJava.y"
{ yyval.obj = validaTipo('/', (TS_entry)val_peek(2).obj, (TS_entry)val_peek(0).obj); }
break;
case 49:
//#line 131 "miniJava.y"
{ 
            /* Simplificacao de polimorfismo basico*/
            TS_entry classeExp = (TS_entry)val_peek(5).obj;
            if (classeExp == Tp_ERRO) yyval.obj = Tp_ERRO;
            else {
                String metodoBusca = classeExp.getId() + "." + val_peek(3).sval;
                TS_entry met = ts.pesquisa(metodoBusca);
                if (met == null) { yyerror("(sem) Metodo >" + val_peek(3).sval + "< nao encontrado na classe " + classeExp.getId()); yyval.obj = Tp_ERRO; }
                else yyval.obj = met.getTipo();
            }
      }
break;
case 50:
//#line 142 "miniJava.y"
{ yyval.obj = Tp_INT; }
break;
case 51:
//#line 143 "miniJava.y"
{ yyval.obj = Tp_BOOL; }
break;
case 52:
//#line 144 "miniJava.y"
{ yyval.obj = Tp_BOOL; }
break;
case 53:
//#line 145 "miniJava.y"
{ 
            TS_entry var = buscaVariavelEscopo(val_peek(0).sval);
            if (var == null) { yyerror("(sem) Variavel >" + val_peek(0).sval + "< nao declarada"); yyval.obj = Tp_ERRO; }
            else yyval.obj = var.getTipo();
      }
break;
case 54:
//#line 150 "miniJava.y"
{ yyval.obj = ts.pesquisa(classeAtual); }
break;
case 55:
//#line 151 "miniJava.y"
{ 
            TS_entry classe = ts.pesquisa(val_peek(2).sval);
            if (classe == null) { yyerror("(sem) Classe >" + val_peek(2).sval + "< nao encontrada"); yyval.obj = Tp_ERRO; }
            else yyval.obj = classe;
      }
break;
case 56:
//#line 156 "miniJava.y"
{ if ((TS_entry)val_peek(0).obj != Tp_BOOL) yyerror("(sem) Op '!' exige boolean"); yyval.obj = Tp_BOOL; }
break;
case 57:
//#line 157 "miniJava.y"
{ yyval.obj = val_peek(1).obj; }
break;
//#line 825 "Parser.java"
//########## END OF USER-SUPPLIED ACTIONS ##########
    }//switch
    //#### Now let's reduce... ####
    if (yydebug) debug("reduce");
    state_drop(yym);             //we just reduced yylen states
    yystate = state_peek(0);     //get new state
    val_drop(yym);               //corresponding value drop
    yym = yylhs[yyn];            //select next TERMINAL(on lhs)
    if (yystate == 0 && yym == 0)//done? 'rest' state and at first TERMINAL
      {
      if (yydebug) debug("After reduction, shifting from state 0 to state "+YYFINAL+"");
      yystate = YYFINAL;         //explicitly say we're done
      state_push(YYFINAL);       //and save it
      val_push(yyval);           //also save the semantic value of parsing
      if (yychar < 0)            //we want another character?
        {
        yychar = yylex();        //get next character
        if (yychar<0) yychar=0;  //clean, if necessary
        if (yydebug)
          yylexdebug(yystate,yychar);
        }
      if (yychar == 0)          //Good exit (if lex returns 0 ;-)
         break;                 //quit the loop--all DONE
      }//if yystate
    else                        //else not done yet
      {                         //get next state and push, for next yydefred[]
      yyn = yygindex[yym];      //find out where to go
      if ((yyn != 0) && (yyn += yystate) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn] == yystate)
        yystate = yytable[yyn]; //get new state
      else
        yystate = yydgoto[yym]; //else go to new defred
      if (yydebug) debug("after reduction, shifting from state "+state_peek(0)+" to state "+yystate+"");
      state_push(yystate);     //going again, so push state & val...
      val_push(yyval);         //for next action
      }
    }//main loop
  return 0;//yyaccept!!
}
//## end of method parse() ######################################



//## run() --- for Thread #######################################
/**
 * A default run method, used for operating this parser
 * object in the background.  It is intended for extending Thread
 * or implementing Runnable.  Turn off with -Jnorun .
 */
public void run()
{
  yyparse();
}
//## end of method run() ########################################



//## Constructors ###############################################
/**
 * Default constructor.  Turn off with -Jnoconstruct .

 */
public Parser()
{
  //nothing to do
}


/**
 * Create a parser, setting the debug to true or false.
 * @param debugMe true for debugging, false for no debug.
 */
public Parser(boolean debugMe)
{
  yydebug=debugMe;
}
//###############################################################



}
//################### END OF CLASS ##############################
