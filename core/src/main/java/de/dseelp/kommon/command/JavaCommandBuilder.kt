package de.dseelp.kommon.command

import de.dseelp.kommon.command.arguments.Argument
import java.util.function.Consumer
import java.util.function.Function

/**
 * @author DSeeLP
 */
class JavaCommandBuilder<S : Any> private constructor(private val builder: CommandBuilder<S>) {

    constructor(
        name: String? = null,
        aliases: Array<String> = arrayOf()
    ) : this(CommandBuilder(name, aliases = aliases))

    constructor(argument: Argument<S, *>) : this(CommandBuilder(argument = argument))

    fun then(builder: JavaCommandBuilder<S>): JavaCommandBuilder<S> {
        this.builder.node(builder.builder)
        return this
    }

    fun then(node: CommandNode<S>): JavaCommandBuilder<S> {
        this.builder.node(node)
        return this
    }

    fun noAccess(consumer: Consumer<Pair<CommandContext<S>, CommandNode<S>>>): JavaCommandBuilder<S> {
        builder.noAccess { consumer.accept(this to it) }
        return this
    }

    fun checkAccess(function: Function<CommandContext<S>, Boolean>): JavaCommandBuilder<S> {
        builder.checkAccess { function.apply(this) }
        return this
    }

    fun execute(consumer: Consumer<CommandContext<S>>): JavaCommandBuilder<S> {
        builder.execute { consumer.accept(this) }
        return this
    }

    fun forward(node: CommandNode<S>): JavaCommandBuilder<S> {
        builder.forward(node)
        return this
    }

    fun build(): CommandNode<S> = builder.build()
}