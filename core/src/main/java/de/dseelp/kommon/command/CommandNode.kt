package de.dseelp.kommon.command

import de.dseelp.kommon.command.arguments.Argument

/**
 * @author DSeeLP
 */
data class CommandNode<S: Any>(
    val name: String? = null,
    val aliases: Array<String> = arrayOf(),
    val argumentIdentifier: Argument<S, *>? = null,
    val target: CommandNode<S>? = null,
    val arguments: Array<Argument<S, *>> = arrayOf(),
    val childs: Array<CommandNode<S>> = arrayOf(),
    val executor: (CommandContext<S>.() -> Unit)?,
    val ignoreCase: Boolean = true,
    val checkAccess: (CommandContext<S>.() -> Boolean) = { true },
    val noAccess: (CommandContext<S>.(node: CommandNode<S>) -> Unit)?,
    val parameters: Map<String, Any> = mapOf(),
    val mappers: Map<String, CommandContext<S>.(input: Any) -> Any?> = mapOf()
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as CommandNode<*>

        if (name != other.name) return false
        if (!aliases.contentEquals(other.aliases)) return false
        if (argumentIdentifier != other.argumentIdentifier) return false
        if (target != other.target) return false
        if (!arguments.contentEquals(other.arguments)) return false
        if (!childs.contentEquals(other.childs)) return false
        if (executor != other.executor) return false
        if (ignoreCase != other.ignoreCase) return false
        if (checkAccess != other.checkAccess) return false
        if (noAccess != other.noAccess) return false

        return true
    }

    override fun hashCode(): Int {
        var result = name?.hashCode() ?: 0
        result = 31 * result + aliases.contentHashCode()
        result = 31 * result + (argumentIdentifier?.hashCode() ?: 0)
        result = 31 * result + (target?.hashCode() ?: 0)
        result = 31 * result + arguments.contentHashCode()
        result = 31 * result + childs.contentHashCode()
        result = 31 * result + (executor?.hashCode() ?: 0)
        result = 31 * result + ignoreCase.hashCode()
        result = 31 * result + checkAccess.hashCode()
        result = 31 * result + (noAccess?.hashCode() ?: 0)
        return result
    }

    operator fun get(key: String) = parameters[key]
}