grammar ModalWff;

@header{
  package jtabwbx.modal.parser; 
}


modalFormula:  wff EOF ;

wff :   '~' wff            # Neg
    |   'box' wff          # Box
    |   'dia' wff          # Dia
    |   wff op='&' wff     # And
    |   wff op='|' wff     # Or
    |   <assoc=right>  wff op='->'  wff  # Imp
    |   <assoc=right>  wff op='<=>' wff  # Eq
    |   '(' wff ')'                      # Par
    |   ID                               # Prop 
    ;


WS  :   [ \t\n]+ -> skip ; // skip whitespaces

AND : '&' ;
OR  : '|' ;
NOT : '~' ;
IMP : '->' ;
EQ : '<=>' ;
NEC: 'box' ;
POS: 'dia' ;

ID : ID_LETTER (ID_LETTER | DIGIT)* ; 
fragment ID_LETTER : 'a'..'z'|'A'..'Z'|'_' ;
fragment DIGIT : '0'..'9' ;


