.globl write_matrix

.text
# ==============================================================================
# FUNCTION: Writes a matrix of integers into a binary file
# FILE FORMAT:
#   The first 8 bytes of the file will be two 4 byte ints representing the
#   numbers of rows and columns respectively. Every 4 bytes thereafter is an
#   element of the matrix in row-major order.
# Arguments:
#   a0 (char*) is the pointer to string representing the filename
#   a1 (int*)  is the pointer to the start of the matrix in memory
#   a2 (int)   is the number of rows in the matrix
#   a3 (int)   is the number of columns in the matrix
# Returns:
#   None
# Exceptions:
#   - If you receive an fopen error or eof,
#     this function terminates the program with error code 27
#   - If you receive an fclose error or eof,
#     this function terminates the program with error code 28
#   - If you receive an fwrite error or eof,
#     this function terminates the program with error code 30
# ==============================================================================
write_matrix:

	addi sp, sp, -28
    sw s0, 0(sp)
    sw s1, 4(sp)
    sw s2, 8(sp)
    sw s3, 12(sp)
    sw s4, 16(sp)
    sw s5, 20(sp)
    sw ra, 24(sp)

    add s0, x0, a0   
    add s1, x0, a1   
    add s2, x0, a2   
    add s3, x0, a3   

    # set open
    add a0, x0, s0
    addi a1, x0, 1
    jal fopen
	addi t0, x0, -1
    beq a0, t0, openerror
    add s4, x0, a0
	
	# malloc for permission
	addi a0, x0, 8
    jal malloc
    add s5, a0, x0
    sw s2, 0(s5)
    sw s3, 4(s5)
	add a0, x0, s4
    add a1, x0, s5
    addi a2, x0, 1
    addi a3, x0, 4
    jal fwrite
    addi t0, x0, 1
    blt a0, t0, writeerror

    addi t0, x0, 4
    add s5, s5, t0

    
    add a0, x0, s4
    add a1, x0, s5
    addi a2, x0, 1
    addi a3, x0, 4
    jal fwrite
	addi t0, x0, 1
    blt a0, t0, writeerror
    
	addi t0, x0, 4
	add a0, x0, s4
    add a1, x0, s1
    mul t0, s2, s3
    add a2, x0, t0
    addi t0, x0, 4
    add a3, x0, t0
    jal fwrite
    mul t0, s2, s3
    blt a0, t0, writeerror

    add a0, x0, s4
    jal fclose
    addi t0, x0, -1
    beq a0, t0, closeerror

    # Epilogue
    lw s0, 0(sp)
    lw s1, 4(sp)
    lw s2, 8(sp)
    lw s3, 12(sp)
    lw s4, 16(sp)
    lw s5, 20(sp)
    lw ra, 24(sp)
    addi sp, sp, 28

    ret

closeerror:
    addi a0, x0, 28
    jal exit
writeerror:
    addi a0, x0, 30
    jal exit
openerror:
    addi a0, x0, 27
    jal exit