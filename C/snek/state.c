#include <stdbool.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "snake_utils.h"
#include "state.h"

/* Helper function definitions */
static char get_board_at(game_state_t* state, int x, int y);
static void set_board_at(game_state_t* state, int x, int y, char ch);
static bool is_tail(char c);
static bool is_snake(char c);
static char body_to_tail(char c);
static int incr_x(char c);
static int incr_y(char c);
static void find_head(game_state_t* state, int snum);
static char next_square(game_state_t* state, int snum);
static void update_tail(game_state_t* state, int snum);
static void update_head(game_state_t* state, int snum);

/* Helper function to get a character from the board (already implemented for you). */
static char get_board_at(game_state_t* state, int x, int y) {
  return state->board[y][x];
}

/* Helper function to set a character on the board (already implemented for you). */
static void set_board_at(game_state_t* state, int x, int y, char ch) {
  state->board[y][x] = ch;
}

/* Task 1 */
game_state_t* create_default_state() {
  // TODO: Implement this function.
  game_state_t* init;
  init = (game_state_t*) malloc(sizeof(game_state_t));
  init->x_size = 14;
  init->y_size = 10;
  init->num_snakes = 1;
  char** board;
  board = (char**) malloc(10 * sizeof(char*)); // free here
  for (int i = 0; i < 10; i++) {
    board[i] = (char*) malloc(14 * sizeof(char)); // free here
  }
  for (int i = 0; i < 10; i++) {
    for (int j = 0; j < 14; j++) {
      if (i == 0) {
        board[i][j] = '#';
      }
      else if (i == 9) {
        board[i][j] = '#';
      }
      else if (j == 0) {
        board[i][j] = '#';
      }
      else if (j == 13) {
        board[i][j] = '#';
      }
      else if (i == 2 && j == 9) {
        board[i][j] = '*';
      }
      else if (i == 4 && j == 5) {
        board[i][j] = '>';
      }
      else if (i == 4 && j == 4) {
        board[i][j] = 'd';
      }
      else {
        board[i][j] = ' ';
      }
    }
  }

  init->board = board;
  snake_t* init1;
  init1 = (snake_t*) malloc(sizeof(snake_t)); // free here.
  init1->head_x = 5;
  init1->head_y = 4;
  init1->tail_y = 4;
  init1->tail_x = 4;
  init1->live = true;
  init->snakes = init1;

  return init;
}

/* Task 2 */
void free_state(game_state_t* state) {
  // reference 
  // https://stackoverflow.com/questions/11071190/free-memory-allocated-in-a-different-function
  free(state->snakes);
  for (int i = 0; i < state->y_size; i++) {
      free(state->board[i]);
  }
  free(state->board);
  free(state);
  return;
}

/* Task 3 */
void print_board(game_state_t* state, FILE* fp) {
  // TODO: Implement this function.
  for(int i = 0; i < state->y_size; i++) {
    for (int j = 0; j < state->x_size; j++) {
      fprintf(fp, "%c", state->board[i][j]);      
    }
    fprintf(fp,"\n");
  }
  return;
}

/* Saves the current state into filename (already implemented for you). */
void save_board(game_state_t* state, char* filename) {
  FILE* f = fopen(filename, "w");
  print_board(state, f);
  fclose(f);
}

/* Task 4.1 */
static bool is_tail(char c) {
  // TODO: Implement this function.
  if (c == 'w' || c == 'a' || c == 's' || c == 'd') {
    return true;
  }
  else {
    return false;
  }
}

static bool is_snake(char c) {
  // TODO: Implement this function.
  if (is_tail(c)) {
    return true;
  } 
  else if (c == '^' || c == '<' || c == '>' || c == 'v' || c == 'x') {
    return true;
  }
  return false;
}

static char body_to_tail(char c) {
  // TODO: Implement this function.
  if (c == '^') {
    return 'w';
  } 
  else if (c == '<') {
    return 'a';
  }
  else if (c == '>') {
    return 'd';
  }
  return 's';
}

static int incr_x(char c) {
  // TODO: Implement this function.
  if (c == 'd' || c == '>') {
    return 1;
  }
  else if(c == 'a' || c == '<') {
    return -1;
  }
  return 0;
}

static int incr_y(char c) {
  // TODO: Implement this function.
  if (c == 'v' || c == 's') {
    return 1;
  }
  else if(c == '^' || c == 'w') {
    return -1;
  }
  return 0;
}

/* Task 4.2 */
static char next_square(game_state_t* state, int snum) {
  // TODO: Implement this function.
  int head_x = state->snakes[snum].head_x;
  int head_y = state->snakes[snum].head_y;
  char head_char = get_board_at(state, head_x, head_y);
  head_x += incr_x(head_char);
  head_y += incr_y(head_char);
  char result = get_board_at(state, head_x, head_y);

  return result;
}

/* Task 4.3 */
static void update_head(game_state_t* state, int snum) {
  // TODO: Implement this function.
  int head_x = state->snakes[snum].head_x;
  int head_y = state->snakes[snum].head_y;
  char head_char = get_board_at(state, head_x, head_y);
  head_x += incr_x(head_char);
  head_y += incr_y(head_char);
  set_board_at(state, head_x, head_y, head_char);
  state->snakes[snum].head_x = head_x;
  state->snakes[snum].head_y = head_y;

  return;
}

/* Task 4.4 */
static void update_tail(game_state_t* state, int snum) {
  // TODO: Implement this function.
  int tail_x = state->snakes[snum].tail_x;
  int tail_y = state->snakes[snum].tail_y;
  char tail_char = get_board_at(state, tail_x, tail_y);
  set_board_at(state, tail_x, tail_y, ' ');

  tail_x += incr_x(tail_char);
  tail_y += incr_y(tail_char);
  
  tail_char = get_board_at(state, tail_x, tail_y);
  char update_char = body_to_tail(tail_char);
  set_board_at(state, tail_x, tail_y, update_char);

  // update tale value
  state->snakes[snum].tail_x = tail_x;
  state->snakes[snum].tail_y = tail_y;
  return;
}

/* Task 4.5 */
void update_state(game_state_t* state, int (*add_food)(game_state_t* state)) {
  // TODO: Implement this function.
  for (int i = 0; i < state->num_snakes; i++) {
    char next = next_square(state, i);
    
    int x_cord = state->snakes[i].head_x;
    int y_cord = state->snakes[i].head_y;
    // next square is body char
    if (is_snake(next)) {
      state->snakes[i].live = false;
      set_board_at(state, x_cord, y_cord, 'x');
      continue;
    }
    
    // next square is wall 
    if (next == '#') {
      state->snakes[i].live = false;
      set_board_at(state, x_cord, y_cord, 'x');
      continue;
    }

    if (next == '*') {
      update_head(state, i);
      add_food(state);
      continue;
    }

    update_head(state, i);
    update_tail(state, i);
  }
  return;
}

/* Task 5 */
game_state_t* load_board(char* filename) {
  // TODO: Implement this function.
  FILE* f = fopen(filename, "r");
  int y = 0;
  int x = 0;
  while(true) {
    char c = fgetc(f);
    if(c == EOF) {
      break;
    }
    if (c == '\n') {
      y++;
    }
  }
  
  f = fopen(filename, "r");
  while(true) {
    char c = fgetc(f);
    if (c == '\n') {
      break;
    }
    x++;
  }

  game_state_t* init;
  init = (game_state_t*) malloc(sizeof(game_state_t));
  init->x_size = x;
  init->y_size = y;
  char** board;
  board = (char**) malloc(y * sizeof(char*)); 
  for (int i = 0; i < y; i++) {
    board[i] = (char*) malloc(x + 1 * sizeof(char)); 
  }

  int x_cord = 0;
  int y_cord = 0;
  f = fopen(filename, "r");
  while(true) {
    char c = fgetc(f);
    if (c == EOF) {
      break;
    }
    if (c == '\n') {
      board[y_cord][x_cord] = '\0';
      y_cord++;
      x_cord = 0;
    }
    else {
      board[y_cord][x_cord] = c;
      x_cord++;
    }
  }

  init->board = board;
  fclose(f);

  return init;
}

/* Task 6.1 */
static void find_head(game_state_t* state, int snum) {
  // TODO: Implement this function.
  int head_x = state->snakes[snum].tail_x;
  int head_y = state->snakes[snum].tail_y;
  char head_char = get_board_at(state, head_x, head_y);
  while(is_snake(next_square(state, snum))) {
      head_char = get_board_at(state, head_x, head_y);
      head_x += incr_x(head_char);
      head_y += incr_y(head_char);
      
      state->snakes[snum].head_x = head_x;
      state->snakes[snum].head_y = head_y;
  }
  if (head_char == 'x') {
    state->snakes[snum].live = false;
  }
  if (head_char != 'x') {
    state->snakes[snum].live = true;
  }
  
  return;
}

/* Task 6.2 */
game_state_t* initialize_snakes(game_state_t* state) {
  // TODO: Implement this function.
  int count = 0;
  snake_t* snakes = (snake_t*) malloc(sizeof(snake_t));
  for(int y = 0; y < state->y_size; y++) {
    for (int x = 0; x < state->x_size; x++) {
      if (is_tail(state->board[y][x])) {
        snakes = (snake_t*) realloc(snakes, (count + 1) * sizeof(snake_t));
        snakes[count].tail_x = x;
        snakes[count].tail_y = y;
        snakes[count].head_x = x;
        snakes[count].head_y = y;
        count++;
      }
    }
  }  
  state->snakes = snakes;
  state->num_snakes = count;
  
  for (int i = 0; i < state->num_snakes; i++) {
    find_head(state, i);
  }
  return state;
}
