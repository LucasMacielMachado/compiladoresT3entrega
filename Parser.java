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
public final static short EXTENDS=258;
public final static short PUBLIC=259;
public final static short STATIC=260;
public final static short VOID=261;
public final static short MAIN=262;
public final static short APP=263;
public final static short STRING=264;
public final static short INT=265;
public final static short BOOLEAN=266;
public final static short IF=267;
public final static short ELSE=268;
public final static short WHILE=269;
public final static short SOUT=270;
public final static short NEW=271;
public final static short TRUE=272;
public final static short FALSE=273;
public final static short THIS=274;
public final static short LENGTH=275;
public final static short RETURN=276;
public final static short AND=277;
public final static short EQ=278;
public final static short NEQ=279;
public final static short Identifier=280;
public final static short Number=281;
public final static short YYERRCODE=256;
final static short yylhs[] = {                           -1,
    0,    4,    4,    8,    5,    9,    6,   12,    6,   11,
   11,   14,   13,   10,   10,   17,   18,   16,   19,   16,
   21,   16,   16,   16,   16,   16,   16,   23,   15,   15,
   24,   22,   22,    2,    2,    1,    1,    7,    7,    7,
    7,    7,   20,   20,    3,    3,    3,    3,    3,    3,
    3,    3,    3,    3,    3,    3,    3,    3,    3,   25,
   25,   26,   26,
};
final static short yylen[] = {                            2,
    2,    2,    0,    0,   17,    0,    7,    0,    9,    2,
    0,    0,   13,    2,    0,    3,    0,    5,    0,    5,
    0,    6,    3,    8,    6,    6,    0,    0,    4,    0,
    0,    5,    0,    1,    1,    1,    1,    3,    7,    5,
    5,    4,    2,    0,    3,    3,    3,    3,    3,    3,
    6,    1,    1,    1,    1,    1,    4,    2,    3,    2,
    0,    3,    0,
};
final static short yydefred[] = {                         3,
    0,    0,    0,    0,    1,    2,    0,    0,    0,    0,
    0,    8,   15,    0,    0,    0,    0,   15,   35,   34,
   37,    0,   36,    0,   14,    0,    0,    0,    0,    7,
   10,    0,    0,   16,    0,    0,    9,   12,    0,    0,
    0,    0,    0,    0,    0,    0,   28,    0,    4,    0,
    0,    0,    0,   29,    0,    0,    0,    0,   44,    0,
    0,    0,    0,    0,    0,   44,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,   31,    0,   53,   54,   56,   55,   52,    0,
    0,    0,    0,    0,   17,    0,   23,   43,   19,    0,
    0,    0,    0,    0,   38,    5,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   21,    0,    0,    0,    0,    0,   42,   32,    0,
   59,    0,    0,    0,    0,    0,    0,    0,    0,   44,
   44,   18,   44,   20,   13,    0,   40,   41,   57,    0,
    0,    0,    0,    0,    0,    0,    0,   44,   39,    0,
   60,   51,    0,    0,   62,
};
final static short yydgoto[] = {                          1,
   22,   23,   92,    2,    5,    6,   98,   52,   10,   16,
   24,   15,   31,   40,   45,   61,   25,  121,  123,   74,
  143,   54,   50,  107,  157,  161,
};
final static short yysindex[] = {                         0,
    0, -233, -272,  -98,    0,    0, -232, -227, -247,  -82,
 -203,    0,    0, -172,  -11,  -66, -144,    0,    0,    0,
    0, -190,    0, -111,    0,   81,  -66,   63,  -66,    0,
    0, -139, -104,    0, -153,   47,    0,    0,   38,   92,
 -131,  -66,  110, -128,  115,   35,    0,   51,    0,  133,
  -77,  -65,  -66,    0,  123,  142,  144,  -58,    0,  -93,
  -85,  155,  161,  166,  148,    0,   85,  -68,  -33,  -33,
  -33,  154,  -33,  -94,  159,  -33,  -33,  -33,  -33,  -33,
  -73,   95,    0,  -61,    0,    0,    0,    0,    0,  -33,
  -33,    2,   18,   25,    0,   94,    0,    0,    0,  100,
   34,   41,   50,  119,    0,    0,  133,  181,  179,   57,
  -33,  -33,  -33,  -33,  -33,  -33,  -53,  -65,  -65,  171,
  -77,    0,  -77,  109,  -65,  -65,  176,    0,    0,  196,
    0,  186,  -41,   27,   27,  179,  179,  202,  -24,    0,
    0,    0,    0,    0,    0,  -22,    0,    0,    0,  -33,
  -65,  -65,  -65,  -65,  -65,  125,  209,    0,    0,  -33,
    0,    0,  -65,  125,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,    0,  120,    0,    0,    0,
    0,    0,    0,    0,    0, -102,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0, -102,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  210,    0,    0,    0,    0,    0,    0,    0,  213,
  -19,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  213,    0,  -32,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  -19,    0,  -19,    0,    0,    0,    0,    0,    0,    0,
    0,   75,  167,   64,   70,  -25,   -5,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  218,
    0,  -16,  -10,   -9,    0,  227,    0,    0,    0,    0,
    0,    0,   -7,  227,    0,
};
final static short yygindex[] = {                         0,
   91,    5,  185,    0,    0,    0,   98,    0,    0,  252,
  244,    0,    0,    0,    0,  -70,    0,    0,    0,   40,
    0,  170,    0,    0,    0,  114,
};
final static int YYTABLESIZE=402;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         90,
  115,  113,   73,  114,  117,  116,   91,    7,   58,   58,
   58,   58,   58,   30,   58,   49,   49,   49,   49,   49,
   37,   49,   11,    3,    8,    9,   58,   58,   66,    4,
   97,   11,   12,   49,   49,   50,   50,   50,   50,   50,
   13,   50,  118,  115,  113,   59,  114,  117,  116,   66,
  142,  105,  144,   50,   50,   60,   14,   66,  119,  115,
  113,  112,  114,  117,  116,  120,  115,  113,  115,  114,
  117,  116,  117,  116,  125,  115,  113,  112,  114,  117,
  116,  126,  115,  113,  112,  114,  117,  116,   17,   28,
  127,  115,  113,  112,  114,  117,  116,  131,  115,  113,
  112,  114,  117,  116,   47,   81,   47,   47,   47,  112,
   48,   18,   48,   48,   48,   45,  112,   26,   45,   35,
   32,   34,   47,   47,   36,   60,   38,   60,   48,   48,
   41,   42,   44,   45,   45,  115,  113,   39,  114,  117,
  116,  115,  113,   68,  114,  117,  116,   29,   43,   67,
   46,   47,  122,  112,   29,   48,   11,   49,  124,  112,
  115,  113,   69,  114,  117,  116,  115,  113,  160,  114,
  117,  116,   62,   51,   63,   64,   53,  128,  112,  152,
  153,   70,  154,   71,  112,   65,   75,   19,   20,   55,
   76,   56,   57,   62,   77,   63,   64,  163,   19,   20,
   78,   62,   58,   63,   64,   79,   65,   46,   80,   82,
   46,   83,   95,   21,   65,  139,  140,   99,  108,  106,
  130,   72,  146,  147,  117,   46,  138,  115,  113,  141,
  114,  117,  116,  145,  148,  111,  149,   84,   85,   86,
   87,  150,    6,  151,   58,  155,   88,   89,  158,  162,
   30,   49,  159,   33,   93,   94,   27,   96,   61,   25,
  100,  101,  102,  103,  104,   26,   22,   63,   24,   27,
   33,   50,    0,    0,  109,  110,  129,  165,  111,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  111,  132,  133,  134,  135,  136,
  137,  111,    0,    0,    0,    0,    0,    0,    0,    0,
  111,    0,    0,    0,    0,    0,    0,  111,    0,    0,
    0,    0,    0,    0,    0,    0,  111,    0,    0,    0,
    0,    0,    0,  111,  156,    0,    0,    0,    0,    0,
   47,    0,    0,    0,  164,    0,   48,    0,    0,    0,
    0,   45,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  111,    0,    0,    0,    0,    0,  111,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  111,    0,    0,    0,    0,
    0,  111,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         33,
   42,   43,   61,   45,   46,   47,   40,  280,   41,   42,
   43,   44,   45,  125,   47,   41,   42,   43,   44,   45,
  125,   47,  125,  257,  123,  258,   59,   60,  123,  263,
  125,  259,  280,   59,   60,   41,   42,   43,   44,   45,
  123,   47,   41,   42,   43,  123,   45,   46,   47,  123,
  121,  125,  123,   59,   60,   51,  260,  123,   41,   42,
   43,   60,   45,   46,   47,   41,   42,   43,   42,   45,
   46,   47,   46,   47,   41,   42,   43,   60,   45,   46,
   47,   41,   42,   43,   60,   45,   46,   47,  261,  280,
   41,   42,   43,   60,   45,   46,   47,   41,   42,   43,
   60,   45,   46,   47,   41,   66,   43,   44,   45,   60,
   41,  123,   43,   44,   45,   41,   60,  262,   44,   29,
   40,   59,   59,   60,  264,  121,  280,  123,   59,   60,
   93,   40,   42,   59,   60,   42,   43,   91,   45,   46,
   47,   42,   43,   53,   45,   46,   47,  259,  280,   52,
   41,  280,   59,   60,  259,   41,  259,  123,   59,   60,
   42,   43,   40,   45,   46,   47,   42,   43,   44,   45,
   46,   47,  267,  123,  269,  270,   44,   59,   60,  140,
  141,   40,  143,   40,   60,  280,  280,  265,  266,  267,
  276,  269,  270,  267,   40,  269,  270,  158,  265,  266,
   40,  267,  280,  269,  270,   40,  280,   41,   61,  125,
   44,  280,   59,  280,  280,  118,  119,   59,  280,  125,
   40,  280,  125,  126,   46,   59,  280,   42,   43,   59,
   45,   46,   47,  125,   59,  277,   41,  271,  272,  273,
  274,   40,  123,  268,  277,  268,  280,  281,  151,   41,
   41,  277,  155,   41,   70,   71,  276,   73,   41,  276,
   76,   77,   78,   79,   80,  276,  276,   41,  276,   18,
   27,  277,   -1,   -1,   90,   91,  107,  164,  277,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,  277,  111,  112,  113,  114,  115,
  116,  277,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
  277,   -1,   -1,   -1,   -1,   -1,   -1,  277,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,  277,   -1,   -1,   -1,
   -1,   -1,   -1,  277,  150,   -1,   -1,   -1,   -1,   -1,
  277,   -1,   -1,   -1,  160,   -1,  277,   -1,   -1,   -1,
   -1,  277,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
  277,   -1,   -1,   -1,   -1,   -1,  277,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,  277,   -1,   -1,   -1,   -1,
   -1,  277,
};
}
final static short YYFINAL=1;
final static short YYMAXTOKEN=281;
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
null,null,null,null,null,null,null,null,null,"CLASS","EXTENDS","PUBLIC",
"STATIC","VOID","MAIN","APP","STRING","INT","BOOLEAN","IF","ELSE","WHILE",
"SOUT","NEW","TRUE","FALSE","THIS","LENGTH","RETURN","AND","EQ","NEQ",
"Identifier","Number",
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
"$$3 :",
"ClassDeclaration : CLASS Identifier EXTENDS Identifier $$3 '{' VarDeclarationList MethodDeclarationList '}'",
"MethodDeclarationList : MethodDeclarationList MethodDeclaration",
"MethodDeclarationList :",
"$$4 :",
"MethodDeclaration : PUBLIC Type Identifier $$4 '(' ParamList ')' '{' DeclOrStatList RETURN Exp ';' '}'",
"VarDeclarationList : VarDeclarationList VarDeclaration",
"VarDeclarationList :",
"VarDeclaration : Type Identifier ';'",
"$$5 :",
"DeclOrStatList : Identifier Identifier ';' $$5 DeclOrStatList",
"$$6 :",
"DeclOrStatList : BaseType Identifier ';' $$6 DeclOrStatList",
"$$7 :",
"DeclOrStatList : Identifier '=' Exp ';' $$7 StatementList",
"DeclOrStatList : '{' StatementList '}'",
"DeclOrStatList : IF '(' Exp ')' Statement ELSE Statement StatementList",
"DeclOrStatList : WHILE '(' Exp ')' Statement StatementList",
"DeclOrStatList : SOUT '(' Exp ')' ';' StatementList",
"DeclOrStatList :",
"$$8 :",
"ParamList : Type Identifier $$8 ParamListAux",
"ParamList :",
"$$9 :",
"ParamListAux : ',' Type Identifier $$9 ParamListAux",
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

//#line 183 "miniJava.y"

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
//#line 483 "Parser.java"
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
//#line 29 "miniJava.y"
{ escopoAtual = "App.main"; }
break;
case 5:
//#line 30 "miniJava.y"
{ escopoAtual = "Global"; }
break;
case 6:
//#line 33 "miniJava.y"
{ 
                        classeAtual = val_peek(0).sval; 
                        escopoAtual = val_peek(0).sval; 
                        TS_entry nodo = ts.pesquisa(val_peek(0).sval);
                        if (nodo != null) yyerror("(sem) Classe >" + val_peek(0).sval + "< ja declarada");
                        else ts.insert(new TS_entry(val_peek(0).sval, Tp_CLASSE, ClasseID.TipoBase));
                   }
break;
case 7:
//#line 41 "miniJava.y"
{ escopoAtual = "Global"; classeAtual = ""; }
break;
case 8:
//#line 42 "miniJava.y"
{
                        classeAtual = val_peek(2).sval; 
                        escopoAtual = val_peek(2).sval; 
                        
                        TS_entry pai = ts.pesquisa(val_peek(0).sval);
                        if (pai == null) yyerror("(sem) Classe pai >" + val_peek(0).sval + "< nao declarada");
                        
                        TS_entry novaClasse = new TS_entry(val_peek(2).sval, Tp_CLASSE, ClasseID.TipoBase);
                        if (pai != null) novaClasse.setPai(pai);
                        
                        if (ts.pesquisa(val_peek(2).sval) != null) yyerror("(sem) Classe >" + val_peek(2).sval + "< ja declarada");
                        else ts.insert(novaClasse);
                   }
break;
case 9:
//#line 56 "miniJava.y"
{ escopoAtual = "Global"; classeAtual = ""; }
break;
case 12:
//#line 61 "miniJava.y"
{ 
                        metodoAtual = val_peek(0).sval; 
                        escopoAtual = classeAtual + "." + val_peek(0).sval; 
                        if (ts.pesquisa(escopoAtual) != null) yyerror("(sem) Metodo >" + val_peek(0).sval + "< ja declarado");
                        else ts.insert(new TS_entry(escopoAtual, (TS_entry)val_peek(1).obj, ClasseID.NomeFuncao));
                    }
break;
case 13:
//#line 68 "miniJava.y"
{ 
                        if ((TS_entry)val_peek(2).obj != (TS_entry)val_peek(11).obj && (TS_entry)val_peek(2).obj != Tp_ERRO) 
                            yyerror("(sem) Retorno do metodo >" + metodoAtual + "< incompativel.");
                        escopoAtual = classeAtual; 
                        metodoAtual = ""; 
                    }
break;
case 16:
//#line 78 "miniJava.y"
{ 
                    String idEscopo = escopoAtual + "." + val_peek(1).sval;
                    if (ts.pesquisa(idEscopo) != null) yyerror("(sem) Atributo >" + val_peek(1).sval + "< ja declarado");
                    else ts.insert(new TS_entry(idEscopo, (TS_entry)val_peek(2).obj, ClasseID.VarLocal)); 
                 }
break;
case 17:
//#line 85 "miniJava.y"
{
                    String idEscopo = escopoAtual + "." + val_peek(1).sval;
                    TS_entry tipoObj = ts.pesquisa(val_peek(2).sval);
                    if (tipoObj == null) { yyerror("(sem) Tipo/Classe >" + val_peek(2).sval + "< nao declarado"); tipoObj = Tp_ERRO; }
                    if (ts.pesquisa(idEscopo) != null) yyerror("(sem) Variavel >" + val_peek(1).sval + "< ja declarada");
                    else ts.insert(new TS_entry(idEscopo, tipoObj, ClasseID.VarLocal));
                 }
break;
case 19:
//#line 92 "miniJava.y"
{
                    String idEscopo = escopoAtual + "." + val_peek(1).sval;
                    if (ts.pesquisa(idEscopo) != null) yyerror("(sem) Variavel >" + val_peek(1).sval + "< ja declarada");
                    else ts.insert(new TS_entry(idEscopo, (TS_entry)val_peek(2).obj, ClasseID.VarLocal));
                 }
break;
case 21:
//#line 97 "miniJava.y"
{ validaAtribuicao(val_peek(3).sval, (TS_entry)val_peek(1).obj); }
break;
case 24:
//#line 99 "miniJava.y"
{ if ((TS_entry)val_peek(5).obj != Tp_BOOL) yyerror("(sem) Condicao do IF deve ser boolean"); }
break;
case 25:
//#line 100 "miniJava.y"
{ if ((TS_entry)val_peek(3).obj != Tp_BOOL) yyerror("(sem) Condicao do WHILE deve ser boolean"); }
break;
case 26:
//#line 101 "miniJava.y"
{ if ((TS_entry)val_peek(3).obj != Tp_INT) yyerror("(sem) SOUT aceita apenas INT"); }
break;
case 28:
//#line 104 "miniJava.y"
{ 
                String idEscopo = escopoAtual + "." + val_peek(0).sval;
                ts.insert(new TS_entry(idEscopo, (TS_entry)val_peek(1).obj, ClasseID.NomeParam));
            }
break;
case 31:
//#line 110 "miniJava.y"
{ 
                  String idEscopo = escopoAtual + "." + val_peek(0).sval;
                  ts.insert(new TS_entry(idEscopo, (TS_entry)val_peek(1).obj, ClasseID.NomeParam));
               }
break;
case 34:
//#line 116 "miniJava.y"
{ yyval.obj = Tp_BOOL; }
break;
case 35:
//#line 117 "miniJava.y"
{ yyval.obj = Tp_INT; }
break;
case 36:
//#line 120 "miniJava.y"
{ yyval.obj = val_peek(0).obj; }
break;
case 37:
//#line 121 "miniJava.y"
{ 
            TS_entry nodo = ts.pesquisa(val_peek(0).sval);
            if (nodo == null) yyerror("(sem) Classe >" + val_peek(0).sval + "< nao declarada");
            yyval.obj = nodo != null ? nodo : Tp_ERRO;
       }
break;
case 39:
//#line 129 "miniJava.y"
{ if ((TS_entry)val_peek(4).obj != Tp_BOOL) yyerror("(sem) Condicao IF deve ser boolean"); }
break;
case 40:
//#line 130 "miniJava.y"
{ if ((TS_entry)val_peek(2).obj != Tp_BOOL) yyerror("(sem) Condicao WHILE deve ser boolean"); }
break;
case 41:
//#line 131 "miniJava.y"
{ if ((TS_entry)val_peek(2).obj != Tp_INT) yyerror("(sem) SOUT aceita apenas INT"); }
break;
case 42:
//#line 132 "miniJava.y"
{ validaAtribuicao(val_peek(3).sval, (TS_entry)val_peek(1).obj); }
break;
case 45:
//#line 137 "miniJava.y"
{ yyval.obj = validaTipo(AND, (TS_entry)val_peek(2).obj, (TS_entry)val_peek(0).obj); }
break;
case 46:
//#line 138 "miniJava.y"
{ yyval.obj = validaTipo('<', (TS_entry)val_peek(2).obj, (TS_entry)val_peek(0).obj); }
break;
case 47:
//#line 139 "miniJava.y"
{ yyval.obj = validaTipo('+', (TS_entry)val_peek(2).obj, (TS_entry)val_peek(0).obj); }
break;
case 48:
//#line 140 "miniJava.y"
{ yyval.obj = validaTipo('-', (TS_entry)val_peek(2).obj, (TS_entry)val_peek(0).obj); }
break;
case 49:
//#line 141 "miniJava.y"
{ yyval.obj = validaTipo('*', (TS_entry)val_peek(2).obj, (TS_entry)val_peek(0).obj); }
break;
case 50:
//#line 142 "miniJava.y"
{ yyval.obj = validaTipo('/', (TS_entry)val_peek(2).obj, (TS_entry)val_peek(0).obj); }
break;
case 51:
//#line 143 "miniJava.y"
{ 
            TS_entry classeExp = (TS_entry)val_peek(5).obj;
            if (classeExp == Tp_ERRO) yyval.obj = Tp_ERRO;
            else {
                TS_entry met = null;
                TS_entry classeAtualBusca = classeExp;
                
                while (classeAtualBusca != null) {
                    met = ts.pesquisa(classeAtualBusca.getId() + "." + val_peek(3).sval);
                    if (met != null) break;
                    classeAtualBusca = classeAtualBusca.getPai();
                }
                
                if (met == null) { yyerror("(sem) Metodo >" + val_peek(3).sval + "< nao encontrado na classe " + classeExp.getId() + " nem nas superclasses"); yyval.obj = Tp_ERRO; }
                else yyval.obj = met.getTipo();
            }
      }
break;
case 52:
//#line 160 "miniJava.y"
{ yyval.obj = Tp_INT; }
break;
case 53:
//#line 161 "miniJava.y"
{ yyval.obj = Tp_BOOL; }
break;
case 54:
//#line 162 "miniJava.y"
{ yyval.obj = Tp_BOOL; }
break;
case 55:
//#line 163 "miniJava.y"
{ 
            TS_entry var = buscaVariavelEscopo(val_peek(0).sval);
            if (var == null) { yyerror("(sem) Variavel >" + val_peek(0).sval + "< nao declarada"); yyval.obj = Tp_ERRO; }
            else yyval.obj = var.getTipo();
      }
break;
case 56:
//#line 168 "miniJava.y"
{ yyval.obj = ts.pesquisa(classeAtual); }
break;
case 57:
//#line 169 "miniJava.y"
{ 
            TS_entry classe = ts.pesquisa(val_peek(2).sval);
            if (classe == null) { yyerror("(sem) Classe >" + val_peek(2).sval + "< nao encontrada"); yyval.obj = Tp_ERRO; }
            else yyval.obj = classe;
      }
break;
case 58:
//#line 174 "miniJava.y"
{ if ((TS_entry)val_peek(0).obj != Tp_BOOL) yyerror("(sem) Op '!' exige boolean"); yyval.obj = Tp_BOOL; }
break;
case 59:
//#line 175 "miniJava.y"
{ yyval.obj = val_peek(1).obj; }
break;
//#line 868 "Parser.java"
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
