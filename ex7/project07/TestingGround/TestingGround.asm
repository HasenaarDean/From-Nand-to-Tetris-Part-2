@17
D=A
@SP
A=M
M=D
@SP
M=M+1
@17
D=A
@SP
A=M
M=D
@SP
M=M+1
@SP
A=M
A=A-1
D=M

@y_1
M=D

@SP
M=M-1
@SP
A=M-1
D=M

@x_1
M=D

@xandnoty_1
M=D
@y_1
D=M
D=!D
@xandnoty_1
M=D&M

@y_1
D=M
@notxandy_1
M=D
@x_1
D=M
D=!D
@notxandy_1
M=D&M
D=M

@xxory_1
M=D
@xandnoty_1
D=M
@xxory_1
M=D|M
D=M

@MANAGE_OVERFLOW_1
D;JLT



@y_1
D=M
@x_1
D=M-D
@TRUE_CONDITION_1
D;JEQ
D=0
@SP
A=M
A=A-1
M=D

@FALSE_CONDITION_1
0;JMP

(TRUE_CONDITION_1)
D=-1
@SP
A=M
A=A-1
M=D
@END_1
0;JMP

(FALSE_CONDITION_1)
@END_1
0;JMP


(MANAGE_OVERFLOW_1)
D=0
@SP
A=M
A=A-1
M=D


(END_1)
@17
D=A
@SP
A=M
M=D
@SP
M=M+1
@16
D=A
@SP
A=M
M=D
@SP
M=M+1
@SP
A=M
A=A-1
D=M

@y_2
M=D

@SP
M=M-1
@SP
A=M-1
D=M

@x_2
M=D

@xandnoty_2
M=D
@y_2
D=M
D=!D
@xandnoty_2
M=D&M

@y_2
D=M
@notxandy_2
M=D
@x_2
D=M
D=!D
@notxandy_2
M=D&M
D=M

@xxory_2
M=D
@xandnoty_2
D=M
@xxory_2
M=D|M
D=M

@MANAGE_OVERFLOW_2
D;JLT



@y_2
D=M
@x_2
D=M-D
@TRUE_CONDITION_2
D;JEQ
D=0
@SP
A=M
A=A-1
M=D

@FALSE_CONDITION_2
0;JMP

(TRUE_CONDITION_2)
D=-1
@SP
A=M
A=A-1
M=D
@END_2
0;JMP

(FALSE_CONDITION_2)
@END_2
0;JMP


(MANAGE_OVERFLOW_2)
D=0
@SP
A=M
A=A-1
M=D


(END_2)
@16
D=A
@SP
A=M
M=D
@SP
M=M+1
@17
D=A
@SP
A=M
M=D
@SP
M=M+1
@SP
A=M
A=A-1
D=M

@y_3
M=D

@SP
M=M-1
@SP
A=M-1
D=M

@x_3
M=D

@xandnoty_3
M=D
@y_3
D=M
D=!D
@xandnoty_3
M=D&M

@y_3
D=M
@notxandy_3
M=D
@x_3
D=M
D=!D
@notxandy_3
M=D&M
D=M

@xxory_3
M=D
@xandnoty_3
D=M
@xxory_3
M=D|M
D=M

@MANAGE_OVERFLOW_3
D;JLT



@y_3
D=M
@x_3
D=M-D
@TRUE_CONDITION_3
D;JEQ
D=0
@SP
A=M
A=A-1
M=D

@FALSE_CONDITION_3
0;JMP

(TRUE_CONDITION_3)
D=-1
@SP
A=M
A=A-1
M=D
@END_3
0;JMP

(FALSE_CONDITION_3)
@END_3
0;JMP


(MANAGE_OVERFLOW_3)
D=0
@SP
A=M
A=A-1
M=D


(END_3)
@892
D=A
@SP
A=M
M=D
@SP
M=M+1
@891
D=A
@SP
A=M
M=D
@SP
M=M+1
@SP
A=M
A=A-1
D=M

@y_4
M=D

@SP
M=M-1
@SP
A=M-1
D=M

@x_4
M=D

@xandnoty_4
M=D
@y_4
D=M
D=!D
@xandnoty_4
M=D&M

@y_4
D=M
@notxandy_4
M=D
@x_4
D=M
D=!D
@notxandy_4
M=D&M
D=M

@xxory_4
M=D
@xandnoty_4
D=M
@xxory_4
M=D|M
D=M

@MANAGE_OVERFLOW_4
D;JLT



@y_4
D=M
@x_4
D=M-D
@TRUE_CONDITION_4
D;JLT
D=0

@SP
A=M
A=A-1
M=D

@FALSE_CONDITION_4
0;JMP

(TRUE_CONDITION_4)
D=-1
@SP
A=M
A=A-1
M=D
@END_4
0;JMP

(FALSE_CONDITION_4)
@END_4
0;JMP


(MANAGE_OVERFLOW_4)
@y_4
D=M
@Y_IS_THE_NEGATIVE_4
D;JLT
@TRUE_CONDITION_4
0;JMP

(Y_IS_THE_NEGATIVE_4)
D=0
@SP
A=M
A=A-1
M=D


(END_4)
@891
D=A
@SP
A=M
M=D
@SP
M=M+1
@892
D=A
@SP
A=M
M=D
@SP
M=M+1
@SP
A=M
A=A-1
D=M

@y_5
M=D

@SP
M=M-1
@SP
A=M-1
D=M

@x_5
M=D

@xandnoty_5
M=D
@y_5
D=M
D=!D
@xandnoty_5
M=D&M

@y_5
D=M
@notxandy_5
M=D
@x_5
D=M
D=!D
@notxandy_5
M=D&M
D=M

@xxory_5
M=D
@xandnoty_5
D=M
@xxory_5
M=D|M
D=M

@MANAGE_OVERFLOW_5
D;JLT



@y_5
D=M
@x_5
D=M-D
@TRUE_CONDITION_5
D;JLT
D=0

@SP
A=M
A=A-1
M=D

@FALSE_CONDITION_5
0;JMP

(TRUE_CONDITION_5)
D=-1
@SP
A=M
A=A-1
M=D
@END_5
0;JMP

(FALSE_CONDITION_5)
@END_5
0;JMP


(MANAGE_OVERFLOW_5)
@y_5
D=M
@Y_IS_THE_NEGATIVE_5
D;JLT
@TRUE_CONDITION_5
0;JMP

(Y_IS_THE_NEGATIVE_5)
D=0
@SP
A=M
A=A-1
M=D


(END_5)
@891
D=A
@SP
A=M
M=D
@SP
M=M+1
@891
D=A
@SP
A=M
M=D
@SP
M=M+1
@SP
A=M
A=A-1
D=M

@y_6
M=D

@SP
M=M-1
@SP
A=M-1
D=M

@x_6
M=D

@xandnoty_6
M=D
@y_6
D=M
D=!D
@xandnoty_6
M=D&M

@y_6
D=M
@notxandy_6
M=D
@x_6
D=M
D=!D
@notxandy_6
M=D&M
D=M

@xxory_6
M=D
@xandnoty_6
D=M
@xxory_6
M=D|M
D=M

@MANAGE_OVERFLOW_6
D;JLT



@y_6
D=M
@x_6
D=M-D
@TRUE_CONDITION_6
D;JLT
D=0

@SP
A=M
A=A-1
M=D

@FALSE_CONDITION_6
0;JMP

(TRUE_CONDITION_6)
D=-1
@SP
A=M
A=A-1
M=D
@END_6
0;JMP

(FALSE_CONDITION_6)
@END_6
0;JMP


(MANAGE_OVERFLOW_6)
@y_6
D=M
@Y_IS_THE_NEGATIVE_6
D;JLT
@TRUE_CONDITION_6
0;JMP

(Y_IS_THE_NEGATIVE_6)
D=0
@SP
A=M
A=A-1
M=D


(END_6)
@32767
D=A
@SP
A=M
M=D
@SP
M=M+1
@32766
D=A
@SP
A=M
M=D
@SP
M=M+1
@SP
A=M
A=A-1
D=M

@y_7
M=D

@SP
M=M-1
@SP
A=M-1
D=M

@x_7
M=D

@xandnoty_7
M=D
@y_7
D=M
D=!D
@xandnoty_7
M=D&M

@y_7
D=M
@notxandy_7
M=D
@x_7
D=M
D=!D
@notxandy_7
M=D&M
D=M

@xxory_7
M=D
@xandnoty_7
D=M
@xxory_7
M=D|M
D=M

@MANAGE_OVERFLOW_7
D;JLT



@y_7
D=M
@x_7
D=M-D
@TRUE_CONDITION_7
D;JGT
D=0
@SP
A=M
A=A-1
M=D

@FALSE_CONDITION_7
0;JMP

(TRUE_CONDITION_7)
D=-1
@SP
A=M
A=A-1
M=D
@END_7
0;JMP

(FALSE_CONDITION_7)
@END_7
0;JMP


(MANAGE_OVERFLOW_7)
@x_7
D=M
@X_IS_THE_NEGATIVE_7
D;JLT
@TRUE_CONDITION_7
0;JMP

(X_IS_THE_NEGATIVE_7)
D=0
@SP
A=M
A=A-1
M=D

(END_7)
@32766
D=A
@SP
A=M
M=D
@SP
M=M+1
@32767
D=A
@SP
A=M
M=D
@SP
M=M+1
@SP
A=M
A=A-1
D=M

@y_8
M=D

@SP
M=M-1
@SP
A=M-1
D=M

@x_8
M=D

@xandnoty_8
M=D
@y_8
D=M
D=!D
@xandnoty_8
M=D&M

@y_8
D=M
@notxandy_8
M=D
@x_8
D=M
D=!D
@notxandy_8
M=D&M
D=M

@xxory_8
M=D
@xandnoty_8
D=M
@xxory_8
M=D|M
D=M

@MANAGE_OVERFLOW_8
D;JLT



@y_8
D=M
@x_8
D=M-D
@TRUE_CONDITION_8
D;JGT
D=0
@SP
A=M
A=A-1
M=D

@FALSE_CONDITION_8
0;JMP

(TRUE_CONDITION_8)
D=-1
@SP
A=M
A=A-1
M=D
@END_8
0;JMP

(FALSE_CONDITION_8)
@END_8
0;JMP


(MANAGE_OVERFLOW_8)
@x_8
D=M
@X_IS_THE_NEGATIVE_8
D;JLT
@TRUE_CONDITION_8
0;JMP

(X_IS_THE_NEGATIVE_8)
D=0
@SP
A=M
A=A-1
M=D

(END_8)
@32766
D=A
@SP
A=M
M=D
@SP
M=M+1
@32766
D=A
@SP
A=M
M=D
@SP
M=M+1
@SP
A=M
A=A-1
D=M

@y_9
M=D

@SP
M=M-1
@SP
A=M-1
D=M

@x_9
M=D

@xandnoty_9
M=D
@y_9
D=M
D=!D
@xandnoty_9
M=D&M

@y_9
D=M
@notxandy_9
M=D
@x_9
D=M
D=!D
@notxandy_9
M=D&M
D=M

@xxory_9
M=D
@xandnoty_9
D=M
@xxory_9
M=D|M
D=M

@MANAGE_OVERFLOW_9
D;JLT



@y_9
D=M
@x_9
D=M-D
@TRUE_CONDITION_9
D;JGT
D=0
@SP
A=M
A=A-1
M=D

@FALSE_CONDITION_9
0;JMP

(TRUE_CONDITION_9)
D=-1
@SP
A=M
A=A-1
M=D
@END_9
0;JMP

(FALSE_CONDITION_9)
@END_9
0;JMP


(MANAGE_OVERFLOW_9)
@x_9
D=M
@X_IS_THE_NEGATIVE_9
D;JLT
@TRUE_CONDITION_9
0;JMP

(X_IS_THE_NEGATIVE_9)
D=0
@SP
A=M
A=A-1
M=D

(END_9)
@57
D=A
@SP
A=M
M=D
@SP
M=M+1
@31
D=A
@SP
A=M
M=D
@SP
M=M+1
@53
D=A
@SP
A=M
M=D
@SP
M=M+1
@SP
A=M
A=A-1
D=M
@SP
M=M-1
@SP
A=M-1
M=M+D
@112
D=A
@SP
A=M
M=D
@SP
M=M+1
@SP
A=M
A=A-1
D=M
@SP
M=M-1
@SP
A=M-1
M=M-D
@SP
A=M
A=A-1
D=M
@SP
M=M-1
@SP
A=M
M=-D
@SP
M=M+1
@SP
A=M
A=A-1
D=M
@SP
M=M-1
@SP
A=M-1
M=M&D
@82
D=A
@SP
A=M
M=D
@SP
M=M+1
@SP
A=M
A=A-1
D=M

@SP
M=M-1
@SP
A=M-1
M=M|D
@SP
A=M
A=A-1
D=M
@SP
M=M-1
@SP
A=M
M=!D
@SP
M=M+1
