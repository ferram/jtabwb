// Generated from ModalWff.g4 by ANTLR 4.5

  package jtabwbx.modal.parser; 

import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class ModalWffLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.5", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, WS=3, AND=4, OR=5, NOT=6, IMP=7, EQ=8, NEC=9, POS=10, 
		ID=11;
	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	public static final String[] ruleNames = {
		"T__0", "T__1", "WS", "AND", "OR", "NOT", "IMP", "EQ", "NEC", "POS", "ID", 
		"ID_LETTER", "DIGIT"
	};

	private static final String[] _LITERAL_NAMES = {
		null, "'('", "')'", null, "'&'", "'|'", "'~'", "'->'", "'<=>'", "'box'", 
		"'dia'"
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, null, null, "WS", "AND", "OR", "NOT", "IMP", "EQ", "NEC", "POS", 
		"ID"
	};
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}


	public ModalWffLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "ModalWff.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	public static final String _serializedATN =
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\2\rI\b\1\4\2\t\2\4"+
		"\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t"+
		"\13\4\f\t\f\4\r\t\r\4\16\t\16\3\2\3\2\3\3\3\3\3\4\6\4#\n\4\r\4\16\4$\3"+
		"\4\3\4\3\5\3\5\3\6\3\6\3\7\3\7\3\b\3\b\3\b\3\t\3\t\3\t\3\t\3\n\3\n\3\n"+
		"\3\n\3\13\3\13\3\13\3\13\3\f\3\f\3\f\7\fA\n\f\f\f\16\fD\13\f\3\r\3\r\3"+
		"\16\3\16\2\2\17\3\3\5\4\7\5\t\6\13\7\r\b\17\t\21\n\23\13\25\f\27\r\31"+
		"\2\33\2\3\2\4\4\2\13\f\"\"\5\2C\\aac|I\2\3\3\2\2\2\2\5\3\2\2\2\2\7\3\2"+
		"\2\2\2\t\3\2\2\2\2\13\3\2\2\2\2\r\3\2\2\2\2\17\3\2\2\2\2\21\3\2\2\2\2"+
		"\23\3\2\2\2\2\25\3\2\2\2\2\27\3\2\2\2\3\35\3\2\2\2\5\37\3\2\2\2\7\"\3"+
		"\2\2\2\t(\3\2\2\2\13*\3\2\2\2\r,\3\2\2\2\17.\3\2\2\2\21\61\3\2\2\2\23"+
		"\65\3\2\2\2\259\3\2\2\2\27=\3\2\2\2\31E\3\2\2\2\33G\3\2\2\2\35\36\7*\2"+
		"\2\36\4\3\2\2\2\37 \7+\2\2 \6\3\2\2\2!#\t\2\2\2\"!\3\2\2\2#$\3\2\2\2$"+
		"\"\3\2\2\2$%\3\2\2\2%&\3\2\2\2&\'\b\4\2\2\'\b\3\2\2\2()\7(\2\2)\n\3\2"+
		"\2\2*+\7~\2\2+\f\3\2\2\2,-\7\u0080\2\2-\16\3\2\2\2./\7/\2\2/\60\7@\2\2"+
		"\60\20\3\2\2\2\61\62\7>\2\2\62\63\7?\2\2\63\64\7@\2\2\64\22\3\2\2\2\65"+
		"\66\7d\2\2\66\67\7q\2\2\678\7z\2\28\24\3\2\2\29:\7f\2\2:;\7k\2\2;<\7c"+
		"\2\2<\26\3\2\2\2=B\5\31\r\2>A\5\31\r\2?A\5\33\16\2@>\3\2\2\2@?\3\2\2\2"+
		"AD\3\2\2\2B@\3\2\2\2BC\3\2\2\2C\30\3\2\2\2DB\3\2\2\2EF\t\3\2\2F\32\3\2"+
		"\2\2GH\4\62;\2H\34\3\2\2\2\6\2$@B\3\b\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}