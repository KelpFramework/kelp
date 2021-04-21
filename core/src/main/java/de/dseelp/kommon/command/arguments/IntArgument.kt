package de.dseelp.kommon.command.arguments

import de.dseelp.kommon.command.CommandContext

/**
 * @author DSeeLP
 */
class IntArgument<S: Any>(name: String, val completer: CommandContext<S>.() -> Array<Int> = { arrayOf() }) : Argument<S, Int>(name) {
    constructor(name: String): this(name, { arrayOf() })

    override fun get(value: String): Int? = value.toIntOrNull()
    override fun getErrorMessage(): String = "%s is not an Integer"
    override fun complete(context: CommandContext<S>, value: String): Array<String> = completer.invoke(context).map { it.toString() }.filter { value.startsWith(it, true) }.toTypedArray()
}