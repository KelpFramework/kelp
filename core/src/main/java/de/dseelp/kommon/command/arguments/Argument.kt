package de.dseelp.kommon.command.arguments

import de.dseelp.kommon.command.CommandContext

/**
 * @author DSeeLP
 */
abstract class Argument<S : Any, T: Any>(val name: String) {
    abstract fun get(value: String): T?
    abstract fun complete(context: CommandContext<S>, value: String): Array<String>
    open fun getErrorMessage(): String = "The string %s does not match the argument"

    protected fun Array<String>.filterPossibleMatches(value: String): Array<String> {
        if (value.isBlank() || value.isEmpty()) return this
        return this.filter { it.startsWith(value, ignoreCase = true) }.toTypedArray()
    }

    protected fun Collection<String>.filterPossibleMatches(value: String) = toTypedArray().filterPossibleMatches(value)
}