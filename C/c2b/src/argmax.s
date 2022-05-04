.globl argmax

.text
# =================================================================
# FUNCTION: Given a int array, return the index of the largest
#   element. If there are multiple, return the one
#   with the smallest index.
# Arguments:
#   a0 (int*) is the pointer to the start of the array
#   a1 (int)  is the # of elements in the array
# Returns:
#   a0 (int)  is the first index of the largest element
# Exceptions:
#   - If the length of the array is less than 1,
#     this function terminates the program with error code 36
# =================================================================
argmax:
	# Prologue
	li t0, 0 				# set index 0
	li t3, 1 				# exception check value
	blt a1, t3, exception	# conditional for length is less than 1,
	lw t2, 0(a0)
	li t3, 0
loop_start:
	bge t0, a1, loop_end 	# check index condition 
	lw t1, 0(a0)			# get first array value
	blt t2, t1, loop_continue # if array value is less than t1, then go to loop continue
	addi a0, a0, 4			# pointing next array index
	addi t0, t0, 1			# updating index value
	j loop_start

loop_continue:
	add t2, x0, t1
	add t3, x0, t0
	addi a0, a0, 4
	addi t0, t0, 1
	j loop_start

loop_end:
	# Epilogue
	add a0, x0, t3
	ret

exception:
	li a0 36
	j exit
