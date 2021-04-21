package de.dseelp.kommon.command

/**
 * @author DSeeLP
 */
data class ParsedResult<S: Any>(
    val root: CommandNode<S>,
    val context: CommandContext<S>,
    val node: CommandNode<S>? = null,
    val failed: Boolean,
    val cause: FailureCause? = null
) {
    enum class FailureCause {
        USAGE,
    }

    fun execute(sender: S, bypassAccess: Boolean = false) {
        if (node == null) return
        val context = context.copy(parameters = node.parameters).apply { this.sender = sender }
        if (!node.checkAccess.invoke(context) && !bypassAccess) {
            (if (root === node) root.noAccess ?: {} else node.noAccess ?: (root.noAccess ?: {})).invoke(context, node)
            return
        }
        node.executor?.invoke(context)
    }
}

