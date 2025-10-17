package com.exxlexxlee.atomicswap.core.database.converter

import androidx.room.TypeConverter
import com.exxlexxlee.atomicswap.core.swap.model.AmountType
import com.exxlexxlee.atomicswap.core.swap.model.Make
import com.exxlexxlee.atomicswap.core.swap.model.SwapState
import com.exxlexxlee.atomicswap.core.swap.model.Take
import com.exxlexxlee.atomicswap.core.swap.model.Token
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.math.BigDecimal

object Converters {
    private val gson = Gson()

    // BigDecimal
    @TypeConverter
    @JvmStatic
    fun bigDecimalToString(value: BigDecimal?): String? = value?.toPlainString()

    @TypeConverter
    @JvmStatic
    fun stringToBigDecimal(value: String?): BigDecimal? = value?.let { BigDecimal(it) }

    // Token
    @TypeConverter
    @JvmStatic
    fun tokenToString(value: Token?): String? = value?.let { gson.toJson(it) }

    @TypeConverter
    @JvmStatic
    fun stringToToken(value: String?): Token? = value?.let {
        gson.fromJson(it, object : TypeToken<Token>() {}.type)
    }

    // AmountType
    @TypeConverter
    @JvmStatic
    fun amountTypeToString(value: AmountType?): String? = value?.let { gson.toJson(it) }

    @TypeConverter
    @JvmStatic
    fun stringToAmountType(value: String?): AmountType? = value?.let {
        gson.fromJson(it, object : TypeToken<AmountType>() {}.type)
    }

//    // PriceType
//    @TypeConverter
//    @JvmStatic
//    fun priceTypeToString(value: PriceType?): String? = value?.let { gson.toJson(it) }
//
//    @TypeConverter
//    @JvmStatic
//    fun stringToPriceType(value: String?): PriceType? = value?.let {
//        gson.fromJson(it, object : TypeToken<PriceType>() {}.type)
//    }

    // Make
    @TypeConverter
    @JvmStatic
    fun makeToString(value: Make?): String? = value?.let { gson.toJson(it) }

    @TypeConverter
    @JvmStatic
    fun stringToMake(value: String?): Make? = value?.let {
        gson.fromJson(it, object : TypeToken<Make>() {}.type)
    }

    // Take
    @TypeConverter
    @JvmStatic
    fun takeToString(value: Take?): String? = value?.let { gson.toJson(it) }

    @TypeConverter
    @JvmStatic
    fun stringToTake(value: String?): Take? = value?.let {
        gson.fromJson(it, object : TypeToken<Take>() {}.type)
    }

    // SwapState as its int step
    @TypeConverter
    @JvmStatic
    fun swapStateToInt(value: SwapState?): Int? = value?.step

    @TypeConverter
    @JvmStatic
    fun intToSwapState(value: Int?): SwapState? = value?.let { SwapState.fromValue(it) }
}