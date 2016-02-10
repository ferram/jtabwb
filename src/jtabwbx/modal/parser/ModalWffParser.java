// Generated from ModalWff.g4 by ANTLR 4.5

  package jtabwbx.modal.parser; 

import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class ModalWffParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.5", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, WS=3, AND=4, OR=5, NOT=6, IMP=7, EQ=8, NEC=9, POS=10, 
		ID=11;
	public static final int
		RULE_modalFormula = 0, RULE_wff = 1;
	public static final String[] ruleNames = {
		"modalFormula", "wff"
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

	@Override
	public String getGrammarFileName() { return "ModalWff.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public ModalWffParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}
	public static class ModalFormulaContext extends ParserRuleContext {
		public WffContext wff() {
			return getRuleContext(WffContext.class,0);
		}
		public TerminalNode EOF() { return getToken(ModalWffParser.EOF, 0); }
		public ModalFormulaContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_modalFormula; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ModalWffListener ) ((ModalWffListener)listener).enterModalFormula(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ModalWffListener ) ((ModalWffListener)listener).exitModalFormula(this);
		}
	}

	public final ModalFormulaContext modalFormula() throws RecognitionException {
		ModalFormulaContext _localctx = new ModalFormulaContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_modalFormula);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(4);
			wff(0);
			setState(5);
			match(EOF);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class WffContext extends ParserRuleContext {
		public WffContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_wff; }
	 
		public WffContext() { }
		public void copyFrom(WffContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class ParContext extends WffContext {
		public WffContext wff() {
			return getRuleContext(WffContext.class,0);
		}
		public ParContext(WffContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ModalWffListener ) ((ModalWffListener)listener).enterPar(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ModalWffListener ) ((ModalWffListener)listener).exitPar(this);
		}
	}
	public static class NegContext extends WffContext {
		public WffContext wff() {
			return getRuleContext(WffContext.class,0);
		}
		public NegContext(WffContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ModalWffListener ) ((ModalWffListener)listener).enterNeg(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ModalWffListener ) ((ModalWffListener)listener).exitNeg(this);
		}
	}
	public static class OrContext extends WffContext {
		public Token op;
		public List<WffContext> wff() {
			return getRuleContexts(WffContext.class);
		}
		public WffContext wff(int i) {
			return getRuleContext(WffContext.class,i);
		}
		public OrContext(WffContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ModalWffListener ) ((ModalWffListener)listener).enterOr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ModalWffListener ) ((ModalWffListener)listener).exitOr(this);
		}
	}
	public static class PropContext extends WffContext {
		public TerminalNode ID() { return getToken(ModalWffParser.ID, 0); }
		public PropContext(WffContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ModalWffListener ) ((ModalWffListener)listener).enterProp(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ModalWffListener ) ((ModalWffListener)listener).exitProp(this);
		}
	}
	public static class AndContext extends WffContext {
		public Token op;
		public List<WffContext> wff() {
			return getRuleContexts(WffContext.class);
		}
		public WffContext wff(int i) {
			return getRuleContext(WffContext.class,i);
		}
		public AndContext(WffContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ModalWffListener ) ((ModalWffListener)listener).enterAnd(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ModalWffListener ) ((ModalWffListener)listener).exitAnd(this);
		}
	}
	public static class BoxContext extends WffContext {
		public WffContext wff() {
			return getRuleContext(WffContext.class,0);
		}
		public BoxContext(WffContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ModalWffListener ) ((ModalWffListener)listener).enterBox(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ModalWffListener ) ((ModalWffListener)listener).exitBox(this);
		}
	}
	public static class EqContext extends WffContext {
		public Token op;
		public List<WffContext> wff() {
			return getRuleContexts(WffContext.class);
		}
		public WffContext wff(int i) {
			return getRuleContext(WffContext.class,i);
		}
		public EqContext(WffContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ModalWffListener ) ((ModalWffListener)listener).enterEq(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ModalWffListener ) ((ModalWffListener)listener).exitEq(this);
		}
	}
	public static class DiaContext extends WffContext {
		public WffContext wff() {
			return getRuleContext(WffContext.class,0);
		}
		public DiaContext(WffContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ModalWffListener ) ((ModalWffListener)listener).enterDia(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ModalWffListener ) ((ModalWffListener)listener).exitDia(this);
		}
	}
	public static class ImpContext extends WffContext {
		public Token op;
		public List<WffContext> wff() {
			return getRuleContexts(WffContext.class);
		}
		public WffContext wff(int i) {
			return getRuleContext(WffContext.class,i);
		}
		public ImpContext(WffContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ModalWffListener ) ((ModalWffListener)listener).enterImp(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ModalWffListener ) ((ModalWffListener)listener).exitImp(this);
		}
	}

	public final WffContext wff() throws RecognitionException {
		return wff(0);
	}

	private WffContext wff(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		WffContext _localctx = new WffContext(_ctx, _parentState);
		WffContext _prevctx = _localctx;
		int _startState = 2;
		enterRecursionRule(_localctx, 2, RULE_wff, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(19);
			switch (_input.LA(1)) {
			case NOT:
				{
				_localctx = new NegContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;

				setState(8);
				match(NOT);
				setState(9);
				wff(9);
				}
				break;
			case NEC:
				{
				_localctx = new BoxContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(10);
				match(NEC);
				setState(11);
				wff(8);
				}
				break;
			case POS:
				{
				_localctx = new DiaContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(12);
				match(POS);
				setState(13);
				wff(7);
				}
				break;
			case T__0:
				{
				_localctx = new ParContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(14);
				match(T__0);
				setState(15);
				wff(0);
				setState(16);
				match(T__1);
				}
				break;
			case ID:
				{
				_localctx = new PropContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(18);
				match(ID);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			_ctx.stop = _input.LT(-1);
			setState(35);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,2,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(33);
					switch ( getInterpreter().adaptivePredict(_input,1,_ctx) ) {
					case 1:
						{
						_localctx = new AndContext(new WffContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_wff);
						setState(21);
						if (!(precpred(_ctx, 6))) throw new FailedPredicateException(this, "precpred(_ctx, 6)");
						setState(22);
						((AndContext)_localctx).op = match(AND);
						setState(23);
						wff(7);
						}
						break;
					case 2:
						{
						_localctx = new OrContext(new WffContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_wff);
						setState(24);
						if (!(precpred(_ctx, 5))) throw new FailedPredicateException(this, "precpred(_ctx, 5)");
						setState(25);
						((OrContext)_localctx).op = match(OR);
						setState(26);
						wff(6);
						}
						break;
					case 3:
						{
						_localctx = new ImpContext(new WffContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_wff);
						setState(27);
						if (!(precpred(_ctx, 4))) throw new FailedPredicateException(this, "precpred(_ctx, 4)");
						setState(28);
						((ImpContext)_localctx).op = match(IMP);
						setState(29);
						wff(4);
						}
						break;
					case 4:
						{
						_localctx = new EqContext(new WffContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_wff);
						setState(30);
						if (!(precpred(_ctx, 3))) throw new FailedPredicateException(this, "precpred(_ctx, 3)");
						setState(31);
						((EqContext)_localctx).op = match(EQ);
						setState(32);
						wff(3);
						}
						break;
					}
					} 
				}
				setState(37);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,2,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public boolean sempred(RuleContext _localctx, int ruleIndex, int predIndex) {
		switch (ruleIndex) {
		case 1:
			return wff_sempred((WffContext)_localctx, predIndex);
		}
		return true;
	}
	private boolean wff_sempred(WffContext _localctx, int predIndex) {
		switch (predIndex) {
		case 0:
			return precpred(_ctx, 6);
		case 1:
			return precpred(_ctx, 5);
		case 2:
			return precpred(_ctx, 4);
		case 3:
			return precpred(_ctx, 3);
		}
		return true;
	}

	public static final String _serializedATN =
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\3\r)\4\2\t\2\4\3\t"+
		"\3\3\2\3\2\3\2\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\5\3\26"+
		"\n\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\7\3$\n\3\f\3\16\3"+
		"\'\13\3\3\3\2\3\4\4\2\4\2\2.\2\6\3\2\2\2\4\25\3\2\2\2\6\7\5\4\3\2\7\b"+
		"\7\2\2\3\b\3\3\2\2\2\t\n\b\3\1\2\n\13\7\b\2\2\13\26\5\4\3\13\f\r\7\13"+
		"\2\2\r\26\5\4\3\n\16\17\7\f\2\2\17\26\5\4\3\t\20\21\7\3\2\2\21\22\5\4"+
		"\3\2\22\23\7\4\2\2\23\26\3\2\2\2\24\26\7\r\2\2\25\t\3\2\2\2\25\f\3\2\2"+
		"\2\25\16\3\2\2\2\25\20\3\2\2\2\25\24\3\2\2\2\26%\3\2\2\2\27\30\f\b\2\2"+
		"\30\31\7\6\2\2\31$\5\4\3\t\32\33\f\7\2\2\33\34\7\7\2\2\34$\5\4\3\b\35"+
		"\36\f\6\2\2\36\37\7\t\2\2\37$\5\4\3\6 !\f\5\2\2!\"\7\n\2\2\"$\5\4\3\5"+
		"#\27\3\2\2\2#\32\3\2\2\2#\35\3\2\2\2# \3\2\2\2$\'\3\2\2\2%#\3\2\2\2%&"+
		"\3\2\2\2&\5\3\2\2\2\'%\3\2\2\2\5\25#%";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}