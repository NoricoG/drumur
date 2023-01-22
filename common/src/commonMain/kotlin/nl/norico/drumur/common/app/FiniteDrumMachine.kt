package nl.norico.drumur.common.app

import org.slf4j.LoggerFactory

class FiniteDrumMachine {
    val userInput = State()
    val pauseState = State()

    private val logger = LoggerFactory.getLogger(this::class.java)

    init {
        Action(userInput, "Play random sound", { d -> d.playRandomSound() }, userInput)
        Action(userInput, "Play all sounds", { d -> d.playAllSounds() }, userInput)
        Action(userInput, "Random instrument", { d -> d.randomInstrument() }, userInput)
        for (i in 0 until 3) {
            Action(userInput, "Random sound $i", { d -> d.randomSound(i) }, userInput)
        }
        Action(userInput, "Play instrument", { d -> d.playInstrument() }, userInput)
        Action(userInput, "Random pattern", { d -> d.randomPattern() }, userInput)
        Action(userInput, "Play pattern", { d -> d.playPattern(4) }, userInput)
        Action(userInput, "Multiple random patterns", { d -> d.randomPatterns() }, userInput)

        Action(pauseState, "Unpauze", {d -> d.playRandomSound() }, userInput)
    }

    fun getInitialState(): State {
        return this.userInput
    }

    fun execute(patternGenerator: PatternGenerator, action: Action): State {
        logger.info(action.name)
        return action.execute(patternGenerator)
    }
}

class Action(
    val startState: State,
    val name: String,
    val function: ((PatternGenerator) -> Unit),
    val nextState: State
) {

    init {
        startState.actions.add(this)
    }

    fun execute(patternGenerator: PatternGenerator): State {
        this.function(patternGenerator)
        return this.nextState
    }

    override fun toString(): String  {
        return this.name
    }
}

class State() {
    var actions = mutableListOf<Action>()
}
