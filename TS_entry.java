public class TS_entry {
   private String id;
   private ClasseID classe;  
   private TS_entry tipo;
   private TS_entry classePai;

   public TS_entry(String umId, TS_entry umTipo, ClasseID umaClasse) {
      id = umId;
      tipo = umTipo;
      classe = umaClasse;
   }

   public String getId() { return id; }
   public TS_entry getTipo() { return tipo; }
   
   public void setPai(TS_entry pai) { this.classePai = pai; }
   public TS_entry getPai() { return this.classePai; }
    
   public String toString() {
       StringBuilder aux = new StringBuilder("");
       aux.append("Id: ").append(String.format("%-25s", id));
       aux.append("\tClasse: ").append(String.format("%-15s", classe));
       aux.append("\tTipo: ").append(tipo2str(this.tipo)); 
       if (classePai != null) {
           aux.append("\tPai: ").append(classePai.getId());
       }
       return aux.toString();
   }

   public String getTipoStr() {
       return tipo2str(this); 
   }

   public String tipo2str(TS_entry tipo) {
      if (tipo == null)  return "null"; 
      else if (tipo==Parser.Tp_INT)    return "int"; 
      else if (tipo==Parser.Tp_BOOL)   return "boolean"; 
      else if (tipo==Parser.Tp_ERRO)   return "_erro_";
      else if (tipo==Parser.Tp_CLASSE) return "classe_base";
      else                             return "erro/tp";
   }
}