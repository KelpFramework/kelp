package de.dseelp.kommon.command.arguments

import de.dseelp.kommon.command.CommandContext
import java.util.*

/**
 * @author DSeeLP
 */
class UUIDArgument<S: Any>(name: String, val completer: CommandContext<S>.() -> Array<UUID> = { arrayOf() }) : Argument<S, UUID>(name) {
    constructor(name: String): this(name, { arrayOf() })

    override fun get(value: String): UUID? = value.toUUIDOrNull()
    override fun getErrorMessage(): String = "%s is not a UUID"
    override fun complete(context: CommandContext<S>, value: String): Array<String> = completer.invoke(context).map { it.toString() }.filter { value.startsWith(it, true) }.toTypedArray()
}

fun String.toUUIDOrNull(): UUID? = try {
    UUID.fromString(this)
}catch (ex: IllegalArgumentException) {
    null
}