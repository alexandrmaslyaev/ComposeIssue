package com.example.composeissuemasliaev.container

import androidx.annotation.DrawableRes

data class State(
    val frontState: FrontState,
    val backState: BackState?,
    val flagOne: Boolean,
    val flagTwo: Boolean,
) {

    data class FrontState(
        val someString: String,
        val type: Type,
    )

    data class BackState(
        val fieldOne: Field.FieldOne,
        val fieldTwo: Field.FieldTwo,
        val fieldThree: Field.FieldThree,
    )

    sealed interface Field {

        val value: String

        data class FieldOne(override val value: String) : Field

        data class FieldTwo(override val value: String) : Field

        data class FieldThree(override val value: String) : Field
    }

    enum class Type(
        val valueResId: String,
        @DrawableRes val iconResId: Int? = null,
    ) {
        Type1("String 1"),
        Type2("String 2"),
    }
}

