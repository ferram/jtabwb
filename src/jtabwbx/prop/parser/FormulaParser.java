// Generated from Formula.g4 by ANTLR 4.5

  package jtabwbx.prop.parser; 

import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class FormulaParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.5", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, ID=3, WS=4, AND=5, OR=6, NOT=7, IMP=8, EQ=9;
	public static final int
		RULE_formula = 0, RULE_wff = 1;
	public static final String[] ruleNames = {
		"formula", "wff"
	};

	private static final String[] _LITERAL_NAMES = {
		null, "'('", "')'", null, null, "'&'", "'|'", "'~'", "'->'", "'<=>'"
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, null, null, "ID", "WS", "AND", "OR", "NOT", "IMP", "EQ"
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
	public String getGrammarFileName() { return "Formula.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public FormulaParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}
	public static class FormulaContext extends ParserRuleContext {
		public WffContext wff() {
			return getRuleContext(WffContext.class,0);
		}
		public TerminalNode EOF() { return getToken(FormulaParser.EOF, 0); }
		public FormulaContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_formula; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof FormulaListener ) ((FormulaListener)listener).enterFormula(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof FormulaListener ) ((FormulaListener)listener).exitFormula(this);
		}
	}

	public final FormulaContext formula() throws RecognitionException {
		FormulaContext _localctx = new FormulaContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_formula);
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
			if ( listener instanceof FormulaListener ) ((FormulaListener)listener).enterPar(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof FormulaListener ) ((FormulaListener)listener).exitPar(this);
		}
	}
	public static class NegContext extends WffContext {
		public WffContext wff() {
			return getRuleContext(WffContext.class,0);
		}
		public NegContext(WffContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof FormulaListener ) ((FormulaListener)listener).enterNeg(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof FormulaListener ) ((FormulaListener)listener).exitNeg(this);
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
			if ( listener instanceof FormulaListener ) ((FormulaListener)listener).enterOr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof FormulaListener ) ((FormulaListener)listener).exitOr(this);
		}
	}
	public static class PropContext extends WffContext {
		public TerminalNode ID() { return getToken(FormulaParser.ID, 0); }
		public PropContext(WffContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof FormulaListener ) ((FormulaListener)listener).enterProp(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof FormulaListener ) ((FormulaListener)listener).exitProp(this);
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
			if ( listener instanceof FormulaListener ) ((FormulaListener)listener).enterAnd(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof FormulaListener ) ((FormulaListener)listener).exitAnd(this);
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
			if ( listener instanceof FormulaListener ) ((FormulaListener)listener).enterEq(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof FormulaListener ) ((FormulaListener)listener).exitEq(this);
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
			if ( listener instanceof FormulaListener ) ((FormulaListener)listener).enterImp(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof FormulaListener ) ((FormulaListener)listener).exitImp(this);
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
			setState(15);
			switch (_input.LA(1)) {
			case NOT:
				{
				_localctx = new NegContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;

				setState(8);
				match(NOT);
				setState(9);
				wff(7);
				}
				break;
			case T__0:
				{
				_localctx = new ParContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(10);
				match(T__0);
				setState(11);
				wff(0);
				setState(12);
				match(T__1);
				}
				break;
			case ID:
				{
				_localctx = new PropContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(14);
				match(ID);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			_ctx.stop = _input.LT(-1);
			setState(31);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,2,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(29);
					switch ( getInterpreter().adaptivePredict(_input,1,_ctx) ) {
					case 1:
						{
						_localctx = new AndContext(new WffContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_wff);
						setState(17);
						if (!(precpred(_ctx, 6))) throw new FailedPredicateException(this, "precpred(_ctx, 6)");
						setState(18);
						((AndContext)_localctx).op = match(AND);
						setState(19);
						wff(7);
						}
						break;
					case 2:
						{
						_localctx = new OrContext(new WffContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_wff);
						setState(20);
						if (!(precpred(_ctx, 5))) throw new FailedPredicateException(this, "precpred(_ctx, 5)");
						setState(21);
						((OrContext)_localctx).op = match(OR);
						setState(22);
						wff(6);
						}
						break;
					case 3:
						{
						_localctx = new ImpContext(new WffContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_wff);
						setState(23);
						if (!(precpred(_ctx, 4))) throw new FailedPredicateException(this, "precpred(_ctx, 4)");
						setState(24);
						((ImpContext)_localctx).op = match(IMP);
						setState(25);
						wff(4);
						}
						break;
					case 4:
						{
						_localctx = new EqContext(new WffContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_wff);
						setState(26);
						if (!(precpred(_ctx, 3))) throw new FailedPredicateException(this, "precpred(_ctx, 3)");
						setState(27);
						((EqContext)_localctx).op = match(EQ);
						setState(28);
						wff(3);
						}
						break;
					}
					} 
				}
				setState(33);
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
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\3\13%\4\2\t\2\4\3\t"+
		"\3\3\2\3\2\3\2\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\5\3\22\n\3\3\3\3\3\3\3"+
		"\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\7\3 \n\3\f\3\16\3#\13\3\3\3\2\3\4"+
		"\4\2\4\2\2(\2\6\3\2\2\2\4\21\3\2\2\2\6\7\5\4\3\2\7\b\7\2\2\3\b\3\3\2\2"+
		"\2\t\n\b\3\1\2\n\13\7\t\2\2\13\22\5\4\3\t\f\r\7\3\2\2\r\16\5\4\3\2\16"+
		"\17\7\4\2\2\17\22\3\2\2\2\20\22\7\5\2\2\21\t\3\2\2\2\21\f\3\2\2\2\21\20"+
		"\3\2\2\2\22!\3\2\2\2\23\24\f\b\2\2\24\25\7\7\2\2\25 \5\4\3\t\26\27\f\7"+
		"\2\2\27\30\7\b\2\2\30 \5\4\3\b\31\32\f\6\2\2\32\33\7\n\2\2\33 \5\4\3\6"+
		"\34\35\f\5\2\2\35\36\7\13\2\2\36 \5\4\3\5\37\23\3\2\2\2\37\26\3\2\2\2"+
		"\37\31\3\2\2\2\37\34\3\2\2\2 #\3\2\2\2!\37\3\2\2\2!\"\3\2\2\2\"\5\3\2"+
		"\2\2#!\3\2\2\2\5\21\37!";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}