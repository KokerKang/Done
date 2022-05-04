# multiAgents.py
# --------------
# Licensing Information:  You are free to use or extend these projects for
# educational purposes provided that (1) you do not distribute or publish
# solutions, (2) you retain this notice, and (3) you provide clear
# attribution to UC Berkeley, including a link to http://ai.berkeley.edu.
# 
# Attribution Information: The Pacman AI projects were developed at UC Berkeley.
# The core projects and autograders were primarily created by John DeNero
# (denero@cs.berkeley.edu) and Dan Klein (klein@cs.berkeley.edu).
# Student side autograding was added by Brad Miller, Nick Hay, and
# Pieter Abbeel (pabbeel@cs.berkeley.edu).


from util import manhattanDistance
from game import Directions
import random, util

from game import Agent

class ReflexAgent(Agent):
    """
    A reflex agent chooses an action at each choice point by examining
    its alternatives via a state evaluation function.

    The code below is provided as a guide.  You are welcome to change
    it in any way you see fit, so long as you don't touch our method
    headers.
    """


    def getAction(self, gameState):
        """
        You do not need to change this method, but you're welcome to.

        getAction chooses among the best options according to the evaluation function.

        Just like in the previous project, getAction takes a GameState and returns
        some Directions.X for some X in the set {NORTH, SOUTH, WEST, EAST, STOP}
        """
        # Collect legal moves and child states
        legalMoves = gameState.getLegalActions()

        # Choose one of the best actions
        scores = [self.evaluationFunction(gameState, action) for action in legalMoves]
        bestScore = max(scores)
        bestIndices = [index for index in range(len(scores)) if scores[index] == bestScore]
        chosenIndex = random.choice(bestIndices) # Pick randomly among the best

        "Add more of your code here if you want to"

        return legalMoves[chosenIndex]

    def evaluationFunction(self, currentGameState, action):
        """
        Design a better evaluation function here.

        The evaluation function takes in the current and proposed child
        GameStates (pacman.py) and returns a number, where higher numbers are better.

        The code below extracts some useful information from the state, like the
        remaining food (newFood) and Pacman position after moving (newPos).
        newScaredTimes holds the number of moves that each ghost will remain
        scared because of Pacman having eaten a power pellet.

        Print out these variables to see what you're getting, then combine them
        to create a masterful evaluation function.
        """
        
        # Useful information you can extract from a GameState (pacman.py)
        childGameState = currentGameState.getPacmanNextState(action)
        newPos = childGameState.getPacmanPosition()
        newFood = childGameState.getFood()
        newGhostStates = childGameState.getGhostStates()
        newScaredTimes = [ghostState.scaredTimer for ghostState in newGhostStates]
        
        
        
        "*** YOUR CODE HERE ***"
        ghostDistance = float("inf")
        for ghostPos in newGhostStates:
            ghostPositions = ghostPos.getPosition()
            distance = manhattanDistance(newPos, ghostPositions)
            ghostDistance = min(ghostDistance, distance)
        
        if ghostDistance == 0:
            return -float("inf")
        
        score = childGameState.getScore()
        score += ghostDistance

        if action == Directions.STOP:
            score -= 10
        
        for ghostState in newGhostStates:
            if ghostDistance <= 3 & ghostState.scaredTimer == 0:
                score -= 100
            elif ghostDistance <= 5 & ghostState.scaredTimer < 5:
                score += 200
            elif ghostDistance == 0 and ghostState.scaredTimer > 0:
                score += 300
        
        foodDistance = float("inf")
        foodCoordi = newFood.asList()
        for food in foodCoordi:
            distance = manhattanDistance(food, newPos)
            foodDistance = min(foodDistance, distance)
        
        score -= 2 * foodDistance

        if(childGameState.getNumFood() < currentGameState.getNumFood()):
            score += 100

        if childGameState.getNumFood() == 0:
            return float("inf")
        
        return score

def scoreEvaluationFunction(currentGameState):
    """
    This default evaluation function just returns the score of the state.
    The score is the same one displayed in the Pacman GUI.

    This evaluation function is meant for use with adversarial search agents
    (not reflex agents).
    """
    return currentGameState.getScore()

class MultiAgentSearchAgent(Agent):
    """
    This class provides some common elements to all of your
    multi-agent searchers.  Any methods defined here will be available
    to the MinimaxPacmanAgent, AlphaBetaPacmanAgent & ExpectimaxPacmanAgent.

    You *do not* need to make any changes here, but you can if you want to
    add functionality to all your adversarial search agents.  Please do not
    remove anything, however.

    Note: this is an abstract class: one that should not be instantiated.  It's
    only partially specified, and designed to be extended.  Agent (game.py)
    is another abstract class.
    """

    def __init__(self, evalFn = 'scoreEvaluationFunction', depth = '2'):
        self.index = 0 # Pacman is always agent index 0
        self.evaluationFunction = util.lookup(evalFn, globals())
        self.depth = int(depth)

class MinimaxAgent(MultiAgentSearchAgent):
    """
    Your minimax agent (question 2)
    """
    def getAction(self, gameState):
        """
        Returns the minimax action from the current gameState using self.depth
        and self.evaluationFunction.

        Here are some method calls that might be useful when implementing minimax.

        gameState.getLegalActions(agentIndex):
        Returns a list of legal actions for an agent
        agentIndex=0 means Pacman, ghosts are >= 1

        gameState.getNextState(agentIndex, action):
        Returns the child game state after an agent takes an action

        gameState.getNumAgents():
        Returns the total number of agents in the game

        gameState.isWin():
        Returns whether or not the game state is a winning state

        gameState.isLose():
        Returns whether or not the game state is a losing state
        """
        "*** YOUR CODE HERE ***"
        
        
        def stateChecker(s):
           if s.isLose() or s.isWin():
              return True
           else:
              return False

        def maxValue(gameState, agentIndex, depth):
            if depth == 0 or stateChecker(gameState):
                return [self.evaluationFunction(gameState), Directions.STOP]
            result = [-float("inf"), Directions.STOP]
          
            for action in gameState.getLegalActions():
                temp = minValue(gameState.getNextState(agentIndex, action), agentIndex + 1, depth)
                if temp[0] > result[0]:
                    result[1] = action
                result[0] = max(temp[0], result[0])
                
            return result


        def minValue(gameState, agentIndex, depth):
            if depth == 0 or stateChecker(gameState):
                return [self.evaluationFunction(gameState), Directions.STOP]
            
            result = [float("inf"), Directions.STOP]
            
            if agentIndex + 1 == gameState.getNumAgents():
               useMax = True
            else:
               useMax = False

            for action in gameState.getLegalActions(agentIndex):
                if(useMax):
                    temp = maxValue(gameState.getNextState(agentIndex, action), 0, depth - 1)
                else:
                    temp = minValue(gameState.getNextState(agentIndex, action), agentIndex + 1, depth)
                if temp[0] < result[0]:
                    result[1] = action
                result[0] = min(temp[0], result[0])
            return result
        if self.depth == 0 or stateChecker(gameState):
            return Directions.STOP
        result = maxValue(gameState, 0, self.depth)
        return result[1]
        

class AlphaBetaAgent(MultiAgentSearchAgent):
    """
    Your minimax agent with alpha-beta pruning (question 3)
    """

    def getAction(self, gameState):
        def stateChecker(s):
           if s.isLose() or s.isWin():
              return True
           else:
              return False

        def maxValue(gameState, agentIndex, depth, alpha, beta):
            if depth == 0 or stateChecker(gameState):
                return [self.evaluationFunction(gameState), Directions.STOP]
            result = [-float("inf"), Directions.STOP]
          
            for action in gameState.getLegalActions():
                temp = minValue(gameState.getNextState(agentIndex, action), agentIndex + 1, depth, alpha, beta)
                if temp[0] > result[0]:
                    result[1] = action
                result[0] = max(temp[0], result[0])
                if result[0] > beta:
                    return result
                alpha = max(alpha, result[0])
            return result


        def minValue(gameState, agentIndex, depth, alpha, beta):
            if depth == 0 or stateChecker(gameState):
                return [self.evaluationFunction(gameState), Directions.STOP]
            
            result = [float("inf"), Directions.STOP]
            
            if agentIndex + 1 == gameState.getNumAgents():
               useMax = True
            else:
               useMax = False

            for action in gameState.getLegalActions(agentIndex):
                if(useMax):
                    temp = maxValue(gameState.getNextState(agentIndex, action), 0, depth - 1, alpha, beta)
                else:
                    temp = minValue(gameState.getNextState(agentIndex, action), agentIndex + 1, depth, alpha, beta)
                if temp[0] < result[0]:
                    result[1] = action
                result[0] = min(temp[0], result[0])
                if alpha > result[0]:
                    return result
                beta = min(beta, result[0])
            return result
        
        if self.depth == 0 or stateChecker(gameState):
            return Directions.STOP
        
        result = maxValue(gameState, 0, self.depth, -float("inf"), float("inf"))
        return result[1]

class ExpectimaxAgent(MultiAgentSearchAgent):
    """
      Your expectimax agent (question 4)
    """

    def getAction(self, gameState):
        """
        Returns the expectimax action using self.depth and self.evaluationFunction

        All ghosts should be modeled as choosing uniformly at random from their
        legal moves.
        """
        "*** YOUR CODE HERE ***"
        def stateChecker(s):
           if s.isLose() or s.isWin():
              return True
           else:
              return False

        def maxValue(gameState, agentIndex, depth):
            if depth == 0 or stateChecker(gameState):
                return [self.evaluationFunction(gameState), Directions.STOP]
            result = [-float("inf"), Directions.STOP]
          
            for action in gameState.getLegalActions():
                temp = expectValue(gameState.getNextState(agentIndex, action), agentIndex + 1, depth)
                if temp[0] > result[0]:
                    result[1] = action
                result[0] = max(temp[0], result[0])
                
            return result


        def expectValue(gameState, agentIndex, depth):
            if depth == 0 or stateChecker(gameState):
                return [self.evaluationFunction(gameState), Directions.STOP]
            
            result = [0, Directions.STOP]
            
            if agentIndex + 1 == gameState.getNumAgents():
               useMax = True
            else:
               useMax = False

            for action in gameState.getLegalActions(agentIndex):
                if(useMax):
                    temp = maxValue(gameState.getNextState(agentIndex, action), 0, depth - 1)
                else:
                    temp = expectValue(gameState.getNextState(agentIndex, action), agentIndex + 1, depth)
                
                result[0] += float(temp[0])
            if (len(gameState.getLegalActions(agentIndex))) == 0:
                return self.evaluationFunction(gameState)
            result[0] = result[0] / len(gameState.getLegalActions(agentIndex))
            return result
        
        
        if self.depth == 0 or stateChecker(gameState):
            return Directions.STOP
        result = maxValue(gameState, 0, self.depth)
        return result[1]
        
        
def betterEvaluationFunction(currentGameState):
    """
    Your extreme ghost-hunting, pellet-nabbing, food-gobbling, unstoppable
    evaluation function (question 5).

    DESCRIPTION: <write something here so we know what you did>
    """
    "*** YOUR CODE HERE ***"
    
    newPos = currentGameState.getPacmanPosition()
    newFood = currentGameState.getFood()
    newGhostStates = currentGameState.getGhostStates()
    newScaredTimes = [ghostState.scaredTimer for ghostState in newGhostStates]
    
    score = currentGameState.getScore()
    
    temp = float("inf")
    foodCoordi = newFood.asList()
    for food in foodCoordi:
        distance = manhattanDistance(food, newPos)
        temp = min(temp, distance)
    
    if currentGameState.getNumFood() != 0:
        score += 4 / temp
    
    ghostScore = 0
    for ghostPos in newGhostStates:
        ghostPositions = ghostPos.getPosition()
        distance = manhattanDistance(newPos, ghostPositions)
        if distance <= 1:
            if ghostPos.scaredTimer != 0:
                ghostScore += 50
            else:
                ghostScore -= 100
        if ghostPos.scaredTimer != 0:
            score += 5
            
    score += ghostScore
    return score
# Abbreviation
better = betterEvaluationFunction
