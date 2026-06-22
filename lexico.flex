%%

%byaccj
%line

%{
  private Parser yyparser;

  public Yylex(java.io.Reader r, Parser yyparser) {
    this(r);
    this.yyparser = yyparser;
    yyline = 1;
  }

  public int getLine() {
      return yyline;
  }
%}

NL  = \n | \r | \r\n

%%

"$TRACE_ON"  { yyparser.setDebug(true);  }
"$TRACE_OFF" { yyparser.setDebug(false); }
"$MOSTRA_TS" { yyparser.listarTS(); }

class         { return Parser.CLASS; } 
extends       { return Parser.EXTENDS; }
"class App"   { return Parser.APP; }
public        { return Parser.PUBLIC; }
static        { return Parser.STATIC; }
void          { return Parser.VOID; }
main          { return Parser.MAIN; }
String        { return Parser.STRING; }
int           { return Parser.INT; }
boolean       { return Parser.BOOLEAN; } 
if            { return Parser.IF; }
else          { return Parser.ELSE; } 
while         { return Parser.WHILE; }
"System.out.println" { return Parser.SOUT; }
new           { return Parser.NEW; }
true          { return Parser.TRUE; }
false         { return Parser.FALSE; }
this          { return Parser.THIS; }
length        { return Parser.LENGTH; }
return        { return Parser.RETURN; }

[a-zA-Z][a-zA-Z0-9_]* { yyparser.yylval = new ParserVal(yytext()); return Parser.Identifier;}
[0-9]+                { yyparser.yylval = new ParserVal(Integer.parseInt(yytext())); return Parser.Number;}

"&&"          { return Parser.AND; }
"=="          { return Parser.EQ; }
"!="          { return Parser.NEQ; }

";" |
"{" |
"}" |
"=" |
"(" |
")" |
"[" |
"]" |
";" |
"*" |
"/" |
"+" |
"," |
"-" | 
"<" |
"!" |
"\."    { return (int) yycharat(0); }


[ \t]+      { }
{NL}+       { yyline++; } 
"//" .* { }
.           { System.err.println("Error: unexpected character '"+yytext()+"' na linha "+yyline); return YYEOF; }