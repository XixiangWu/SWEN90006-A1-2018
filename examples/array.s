        ;; array program. Fills an array with the values 0 ... N-1
        ;; and then interates through the array, summing its elements

        ;; global constants:
        ;;   R3 holds 'N', the length of the array
        ;;   R2 holds 1, the increment value used below
        MOV R3 10               ; N = 10
        MOV R2 1                ;

        ;;  create the array
        ;; local variables
        ;;   R1 holds 'i', which is a counter from 0 .. N-1
        ;;   R0 holds 'p', the address of the array's ith element
        MOV R1 0                ; i = 0;
        MOV R0 100    

        SUB R4 R3 R1            ; while(i != N)
        JZ  R4 5                ; {
        STR R0 0  R1            ;   *p = i;
        ADD R1 R1 R2            ;   i = i + 1;
        ADD R0 R0 R2            ;   p = p + 1;
        JMP -5                  ; }

        ;;  sum up the array
        ;; local variables
        ;;   R1 holds 'i', which is a counter from 0 .. N-1
        ;;   R0 holds 'p', the address of the array's ith element
        ;;   R5 holds 'sum', which always holds the sum of the array's first i elements
        MOV R1 0                ; i = 0;
        MOV R0 100    
        MOV R5 0                ; sum = 0;

        SUB R4 R3 R1            ; while(i != N)
        JZ  R4 6                ; {
        LDR R4 R0 0             ;   
        ADD R5 R5 R4            ;   sum = sum + *p;
        ADD R0 R0 R2            ;   p = p + 1;
        ADD R1 R1 R2            ;   i = i + 1;
        JMP -6                  ; }
        RET R5                  ; return sum;
