package model

interface Step<T, E> {
    fun execute(input: T): E
}

class LambdaStep<T, E>(private val executor: (T) -> E) : Step<T, E> {
    override fun execute(input: T) = executor(input)
}

class StepExecutor<T, E>(private val step: Step<T, E>) : Step<T, E> {
    fun <K> addStep(nextStep: Step<E, K>): StepExecutor<T, K> {
        val extendedStep = LambdaStep<T, K> {
            val result = step.execute(it)

            nextStep.execute(result)
        }

        return StepExecutor(extendedStep)
    }

    override fun execute(input: T) = step.execute(input)
}

class StepExecutorBuilder {
    fun <T, E> addStep(step: Step<T, E>): StepExecutor<T, E> {
        return StepExecutor(step)
    }
}
