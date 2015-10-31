
// File:   MH_Lexer.java
// Date:   October 2015

// Java template file for lexer component of Informatics 2A Assignment 1 (2015).
// Concerns lexical classes and lexer for the language MH (`Micro-Haskell').


import java.io.* ;

class MH_Lexer extends GenLexer implements LEX_TOKEN_STREAM 
{

	static class VarAcceptor extends GenAcceptor implements DFA 
	{
		public String lexClass() {return "VAR" ;} ;
		public int numberOfStates() {return 5 ;} ;

		int nextState (int state, char c) 
		{
			switch (state) 
			{
				case 0: if (c < 'z' && c > 'a') return 0; else return 4 ; //small letter
				case 1: if (c < 'Z' && z > 'A') return 2; else return 3 ; //capital letter 
				case 2: if (s < '9' && c > '0') return 3; else return 3 ; //digit
				case 3: if ((int) c == 39) return 3 ; //not sure if single quote or apostrophe on my keyboard
				default: return 4 ;
			}
		}
		boolean accepting (int state) {return (state == 3) ;}
		boolean dead (int state) {return (state == 4) ;}}
	}

	static class NumAcceptor extends GenAcceptor implements DFA 
	{
		public String lexClass() {return "NUM" ;} ;
		public int numberOfStates() {return 2 ;} ;

		int nextState (int state, char c) 
		{
			switch (state) 
			{
				case 0: if (c < '9' && c > '0') return 1; else return 2 ; //small letter
				case 1: if (c < '9' && z > '0') return 1; else return 2 ; //capital letter 
				default: return 2 ;
			}
		}
		boolean accepting (int state) {return (state == 1) ;}
		boolean dead (int state) {return (state == 2) ;}}
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
    
	}

	static class WhitespaceAcceptor extends GenAcceptor implements DFA 
	{
    // add code here
	}

	static class CommentAcceptor extends GenAcceptor implements DFA 
	{
    // add code here
	}

	static class TokAcceptor extends GenAcceptor implements DFA 
	{

		String tok ;
		int tokLen ;
		TokAcceptor (String tok) {this.tok = tok ; tokLen = tok.length() ;}

		// add code here
	}

    // add definition of MH_acceptors here


    MH_Lexer (Reader reader) {
	super(reader,MH_acceptors) ;
    }

}
