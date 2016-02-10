grammar Formula;

@header{
  package jtabwbx.prop.parser; 
}


formula:  wff EOF ;

wff :   '~' wff            # Neg
    |   wff op='&' wff     # And
    |   wff op='|' wff     # Or
    |   <assoc=right>  wff op='->'  wff  # Imp
    |   <assoc=right>  wff op='<=>' wff  # Eq
    |   '(' wff ')'                      # Par
    |   ID                               # Prop 
    ;


ID : ID_LETTER (ID_LETTER | DIGIT)* ; 
fragment ID_LETTER : 'a'..'z'|'A'..'Z'|'_' ;
fragment DIGIT : '0'..'9' ;

WS  :   [ \t\n]+ -> skip ; // skip whitespaces

AND : '&' ;
OR  : '|' ;
NOT : '~' ;
IMP : '->' ;
EQ : '<=>' ;

