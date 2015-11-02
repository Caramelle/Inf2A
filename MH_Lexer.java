
// File:   MH_Lexer.java
// Date:   October 2015

// Java template file for lexer component of Informatics 2A Assignment 1 (2015).
// Concerns lexical classes and lexer for the language MH (`Micro-Haskell').


import java.io.* ;

class MH_Lexer extends GenLexer implements LEX_TOKEN_STREAM 
{

	static class VarAcceptor extends GenAcceptor implements DFA 
	{
		public String lexClass() {return "VAR" ;};
		public int numberOfStates() {return 3 ;};

		int nextState (int state, char c) 
		{
			switch (state) 
			{ 
				case 0: if (CharTypes.isSmall(c)) return 1; else return 2; //small letter
				case 1: if (CharTypes.isLarge(c) || CharTypes.isDigit(c) || (int) c == 39) return 1; else return 2; //capital letter 
				default: return 2;
			}
		}
		boolean accepting (int state) {return (state == 1) ;}
		boolean dead (int state) {return (state == 2) ;}
	}

	static class NumAcceptor extends GenAcceptor implements DFA 
	{
		public String lexClass() {return "NUM" ;} ;
		public int numberOfStates() {return 3 ;} ;

		int nextState (int state, char c) 
		{
			switch (state) 
			{
				case 0: if (CharTypes.isDigit(c)) return 1; else return 2 ; //small letter
				case 1: if (CharTypes.isDigit(c)) return 1; else return 2 ; //capital letter 
				default: return 2;
			}
		}
		boolean accepting (int state) {return (state == 1) ;}
		boolean dead (int state) {return (state == 2) ;}
	}

	static class BooleanAcceptor extends GenAcceptor implements DFA 
	{
		public String lexClass() {return "BOOLEAN";};
	    public int numberOfStates() { return 10 ;} ;

	    int nextState(int state, char c)
	    {
	        switch(state)
	        {
	            case 0: if (c == 'T') return 1; else if (c == 'F') return 4; else return 9;
	            case 1: if (c == 'r') return 2; else return 9;
	            case 2: if (c == 'u') return 3; else return 9;
	            case 3: if (c == 'e') return 8; else return 9;
	            case 4: if (c == 'a') return 5; else return 9;
	            case 5: if (c == 'l') return 6; else return 9;
	            case 6: if (c == 's') return 7; else return 9;
	            case 7: if (c == 'e') return 8; else return 9;
	            default: return 9;
	        }
	    }

	    boolean accepting (int state) {return (state == 8) ;}
	    boolean dead (int state) {return (state == 9) ;}
	}

	static class SymAcceptor extends GenAcceptor implements DFA 
	{
		public String lexClass() {return "SYM" ;} ;
		public int numberOfStates() {return 3 ;} ;

		int nextState (int state, char c) 
		{
			switch (state) 
			{
				case 0: if (CharTypes.isSymbolic(c)) return 1; else return 2; 
				case 1: if (CharTypes.isSymbolic(c)) return 1; else return 2; 
				default: return 2;
			}
		}
		boolean accepting (int state) {return (state == 1) ;}
		boolean dead (int state) {return (state == 2) ;}
	}

	static class WhitespaceAcceptor extends GenAcceptor implements DFA 
	{
		public String lexClass() {return "";};
		public int numberOfStates() {return 3;};

		int nextState (int state, char c) 
		{
			switch (state) 
			{
				case 0: if (c == ' ') return 1; else return 2; 
				case 1: if (c == ' ') return 1; else return 2; 
				default: return 2;
			}
		}
		boolean accepting (int state) {return (state == 1) ;}
		boolean dead (int state) {return (state == 2) ;}
	}

	static class CommentAcceptor extends GenAcceptor implements DFA 
	{
		public String lexClass() {return "" ;} ;
		public int numberOfStates() {return 6 ;} ;

		int nextState (int state, char c) 
		{
			switch (state) 
			{
				case 0: if (c == '-') return 1; else return 5; 
				case 1: if (c == '-') return 2; else return 5;
				case 2: if (c == '-') return 2; else return 3;
				case 3: if (!CharTypes.isSymbolic(c) && c != '\n') return 4; else return 5;
				case 4: if (c != '\n') return 4; else return 5;
				default: return 5 ;
			}
		}
		boolean accepting (int state) {return (state == 1) ;}
		boolean dead (int state) {return (state == 2) ;}
	}
	

	static class TokAcceptor extends GenAcceptor implements DFA 
	{
		String tok ;
		int tokLen ;
		TokAcceptor (String tok) {this.tok = tok ; tokLen = tok.length() ;}
		public String lexClass() {return tok;};
		public int numberOfStates() {return 9;};
		
		int nextState (int state, char c)
		{
			switch (state)
			{
				case 0: if (tokLen == 1) return 1; 
					else if (tokLen == 2) return 2;
						else if (tokLen == 4) return 3;
							else if (tokLen == 7) return 6;
								else return 8;
				case 1: if (tok.charAt(state-1) == '(' || tok.charAt(state-1) == ')' || tok.charAt(state-1) == ';') return 7; else return 8;
				case 2: if (tok.charAt(state-2)== 'i' && tok.charAt(state-1) == 'f') return 7; else return 8;
				case 3: if (tok.charAt(state-3) == 'e' && tok.charAt(state-2) == 'l' && tok.charAt(state-1) == 's' && tok.charAt(state) == 'e') return 7; else return 4;
				case 4: if (tok.charAt(state-3) == 't' && tok.charAt(state-2) == 'h' && tok.charAt(state-1) == 'e' && tok.charAt(state) == 'n') return 7; else return 5;
				case 5: if (tok.charAt(state-3) == 'B' && tok.charAt(state-2) == 'o' && tok.charAt(state-1) == 'o' && tok.charAt(state) == 'l') return 7; else return 8;
				case 6: if (tok.charAt(state-6) == 'I' && tok.charAt(state-5) == 'n' && tok.charAt(state-4) == 't' && tok.charAt(state-3) == 'e' && tok.charAt(state-2) == 'g' && tok.charAt(state-1) == 'e' && tok.charAt(state) == 'r') return 7; else return 8;		
				default: return 8;
			}
		}
			
		boolean accepting (int state) {return (state == 7) ;}
		boolean dead (int state) {return (state == 8) ;}
		
	}
	
	
		static DFA VarAcceptor = new VarAcceptor();
	    static DFA NumAcceptor = new NumAcceptor();
	    static DFA BooleanAcceptor = new BooleanAcceptor();
	    static DFA SymAcceptor = new SymAcceptor();
	    static DFA WhitespaceAcceptor = new WhitespaceAcceptor();
	    static DFA CommentAcceptor = new CommentAcceptor();
	    static DFA LParanthesisAcceptor = new TokAcceptor ("(");
	    static DFA RParanthesisAcceptor = new TokAcceptor (")");
	    static DFA SemicolonAcceptor = new TokAcceptor (";");
	    static DFA IfAcceptor = new TokAcceptor ("if");
	    static DFA BoolAcceptor = new TokAcceptor ("Bool");
	    static DFA ElseAcceptor = new TokAcceptor ("else");
	    static DFA ThenAcceptor = new TokAcceptor ("then");
	    static DFA IntegerAcceptor = new TokAcceptor ("Integer"); 
	    static DFA[] MH_acceptors = new DFA[] {LParanthesisAcceptor, RParanthesisAcceptor, SemicolonAcceptor,IntegerAcceptor, BoolAcceptor, IfAcceptor, ElseAcceptor, ThenAcceptor, VarAcceptor, NumAcceptor, BooleanAcceptor, SymAcceptor, WhitespaceAcceptor, CommentAcceptor} ;
	

	    MH_Lexer (Reader reader) 
	    {
	    	super(reader,MH_acceptors) ;
	    }
}

    
class MH_LexerDemo
{
    public static void main (String[] args) 
    throws LexError, StateOutOfRange, IOException 
    {
	    System.out.print ("MHLexer> ") ;
	    Reader reader = new BufferedReader (new InputStreamReader (System.in)) ;
	    GenLexer demoLexer = new MH_Lexer (reader) ;
	    LexToken currTok = demoLexer.pullProperToken() ;
	    System.out.println (currTok);
	    while (currTok != null) 
	    {
	    	System.out.println ("fgtref");
	        System.out.println (currTok.value() + " \t" + currTok.lexClass()) ;
	        currTok = demoLexer.pullProperToken() ;
	    } ;
	    System.out.println ("END OF INPUT.") ;
    }

}
    



    
