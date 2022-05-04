.globl dot

.text
# =======================================================
# FUNCTION: Dot product of 2 int arrays
# Arguments:
#   a0 (int*) is the pointer to the start of arr0
#   a1 (int*) is the pointer to the start of arr1
#   a2 (int)  is the number of elements to use
#   a3 (int)  is the stride of arr0
#   a4 (int)  is the stride of arr1
# Returns:
#   a0 (int)  is the dot product of arr0 and arr1
# Exceptions:
#   - If the length of the array is less than 1,
#     this function terminates the program with error code 36
#   - If the stride of either array is less than 1,
#     this function terminates the program with error code 37
# =======================================================
dot:
	
	
	# Prologue
	li t0, 0
	li t1, 1

	blt a2, t1, exception
	blt a3, t1, exception_stride
	blt a4, t1, exception_stride
	
	li t1, 0
	slli t3, a3, 2
	slli t4, a4, 2

loop_start:
	bge t0, a2, loop_end 	# check index condition 
	lw t5, 0(a0)			# get first array value
	lw t6, 0(a1)
	mul t5, t5, t6
	add t1, t1, t5
	add a0, a0, t3			# pointing next array index
	add a1, a1, t4			# updating index value
	addi t0, t0, 1
	j loop_start











loop_end:
	# Epilogue
	add a0, t1, x0, 

	ret


exception:
	li a0 36
	j exit

exception_stride:
	li a0 37
	j exit