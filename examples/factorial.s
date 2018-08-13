        ;; factorial program, to calculate N!

        ;; global constants:
        ;;   R3 holds 'N', the thing we are computing factorial of
        ;;   R2 holds 1, the increment value used below
        MOV R3 12               ; N = 12
        MOV R2 1                ;
        
        ;; local variables
        ;;   R1 holds 'i', which is a counter from 0 .. N
        ;;   R0 holds 'n', which is always equal to i! 
        MOV R1 0                ; i = 0;
        MOV R0 1                ; n = 1;

        ;;  program body
        ;;  loop invariant (see SWEN90010 next semester): n = i!
        SUB R4 R3 R1            ; while(i != N)
        JZ  R4 4                ; {
        ADD R1 R1 R2            ;   i = i + 1;
        MUL R0 R0 R1            ;   n = n * i;
        JMP -4                  ; }
        RET R0                  ; return n;
