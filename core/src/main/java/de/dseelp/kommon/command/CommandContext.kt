package de.dseelp.kommon.command

import de.dseelp.kommon.command.arguments.ParsedArgument
import kotlin.reflect.KVisibility
import kotlin.reflect.full.declaredMemberFunctions

/**
 * @author DSeeLP
 */
data class CommandContext<S : Any>(
    val args: Map<String, ParsedArgument<*>>,
    val parameters: Map<String, Any>,
    val mappers: Map<String, CommandContext<S>.(input: Any) -> Any?>
) {
    lateinit var sender: S
        internal set

    val mappedArgs: Map<String, ParsedArgument<*>> =
        args.filter { mappers.containsKey(it.key) }
            .map {
                val newValue = it.value.value.let { value -> if (value == null) null else mappers[it.key]!!.invoke(this, value) }
                it.key to it.value.copyParsed(newValue)
            }.toMap()

    private fun ParsedArgument<*>.copyParsed(newValue: Any?): ParsedArgument<*> {
        for (current in this::class.declaredMemberFunctions) {
            if (current.visibility != KVisibility.PUBLIC) continue
            if (current.name != "copy") continue
            val parameters = current.parameters.toTypedArray()
            return current.callBy(mapOf(
                parameters[0] to this,
                parameters[3] to newValue
            )) as ParsedArgument<*>
        }
        return this
    }

    inline operator fun <reified T> get(key: String): T {
        if (!args.containsKey(key)) throw IllegalArgumentException("An argument with the name $key doesn't exist")
        val value = args[key]!!
        //if (value.optional) throw IllegalArgumentException("$key is optional use optional(key: String) instead")
        if (value.value is T) return value.value
        return getMapped(key)
        //throw IllegalArgumentException("$key is not of the type ${T::class.simpleName}")
    }

    inline fun <reified T> getMapped(key: String): T {
        if (!args.containsKey(key)) throw IllegalArgumentException("An argument with the name $key doesn't exist")
        val value = mappedArgs[key]!!
        //if (value.optional) throw IllegalArgumentException("$key is optional use optional(key: String) instead")
        if (value.value is T) return value.value
        throw IllegalArgumentException("$key is not of the type ${T::class.simpleName}")
    }

    /*inline fun <reified T> optional(key: String): T? {
        if (!args.containsKey(key)) throw IllegalArgumentException("An argument with the name $key doesn't exist")
        val value = args[key]!!
        if (value.value == null) return null
        if (value.value is T) return value.value
        return optionalMapped(key)
        //throw IllegalArgumentException("$key is not of the type ${T::class.simpleName}")
    }

    inline fun <reified T> optionalMapped(key: String): T? {
        if (!args.containsKey(key)) throw IllegalArgumentException("An argument with the name $key doesn't exist")
        val value = mappedArgs[key]!!
        if (value.value == null) return null
        if (value.value is T) return value.value
        throw IllegalArgumentException("$key is not of the type ${T::class.simpleName}")
    }*/

    fun <T> get(key: String, type: Class<T>): T {
        if (!args.containsKey(key)) throw IllegalArgumentException("An argument with the name $key doesn't exist")
        val value = args[key]!!
        //if (value.optional) throw IllegalArgumentException("$key is optional use optional(key: String) instead")
        @Suppress("UNCHECKED_CAST")
        if (type == value.value!!::class.java) return value.value as T
        return getMapped(key, type)
        //throw IllegalArgumentException("$key is not of the type ${type.simpleName}")
    }

    fun <T> getMapped(key: String, type: Class<T>): T {
        if (!args.containsKey(key)) throw IllegalArgumentException("An argument with the name $key doesn't exist")
        val value = mappedArgs[key]!!
        //if (value.optional) throw IllegalArgumentException("$key is optional use optional(key: String) instead")
        @Suppress("UNCHECKED_CAST")
        if (type == value.value!!::class.java) return value.value as T
        throw IllegalArgumentException("$key is not of the type ${type.simpleName}")
    }

    /*fun <T> optional(key: String, type: Class<T>): T? {
        if (!args.containsKey(key)) throw IllegalArgumentException("An argument with the name $key doesn't exist")
        val value = args[key]!!
        if (value.value == null) return null
        @Suppress("UNCHECKED_CAST")
        if (type == value.value::class.java) return value.value as T
        throw IllegalArgumentException("$key is not of the type ${type.simpleName}")
    }*/
}