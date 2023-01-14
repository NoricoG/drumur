package nl.norico.drumur.common.app

class FiniteDrumMachine {
    val userInput = State()
    var currentState = userInput

    init {
        Action(userInput, "Play random sound", { d -> d.playRandomSound() }, userInput)
        Action(userInput, "Play all sounds", { d -> d.playAllSounds() }, userInput)
        Action(userInput, "Random instrument", { d -> d.randomInstrument() }, userInput)
        Action(userInput, "Play instrument", { d -> d.playInstrument() }, userInput)
        Action(userInput, "Random pattern", { d -> d.randomPattern() }, userInput)
        Action(userInput, "Play pattern", { d -> d.playPattern(3) }, userInput)
        Action(userInput, "Multiple random patterns", { d -> d.randomPatterns() }, userInput)
    }

    fun execute(patternGenerator: PatternGenerator, action: Action) {
        this.currentState = action.execute(patternGenerator)
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
