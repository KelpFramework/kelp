package de.dseelp.kommon.command.arguments

/**
 * @author DSeeLP
 */
data class ParsedArgument<T: Any>(val name: String, val optional: Boolean, val value: T?)