package com.example.apilist.model

import androidx.room.TypeConverter

class Converters {
    @TypeConverter
    fun fromListToString(list: List<String>) : String {
        return list.joinToString(",")
    }

    @TypeConverter
    fun fromStringToList(string:String) : List<String> {
        return string.split(",")
    }

    @TypeConverter
    fun fromAbilityToString(pep: Ability) : String {
        return "${pep.name},${pep.text},${pep.type}"
    }

    @TypeConverter
    fun fromStringToAbility(string:String) : Ability {
        val name=string.split(",")[0]
        val text=string.split(",")[1]
        val type=string.split(",")[2]
        return Ability(name, text, type)
    }

    @TypeConverter
    fun fromAbilityListToString(ability: List<Ability>) : String {
        val stringList = mutableListOf<String>()
        for (i in 0 until ability.size) {
            stringList[i] = ability[i].toString()
        }
        return fromListToString(stringList)
    }

    @TypeConverter
    fun fromStringToAbilityList(string:String) : List<Ability> {
        val stringList = string.split(";")
        return stringList.map { fromStringToAbility(it) }
    }

    @TypeConverter
    fun fromAttackToString(attack: Attack) : String {
        return "${attack.convertedEnergyCost},${attack.damage},${attack.name},${attack.text}"
    }

    @TypeConverter
    fun fromStringToAttack(string:String) : Attack {
        val energyCost=string.split(",")[0].toInt()
        val damage=string.split(",")[1]
        val name=string.split(",")[2]
        val text=string.split(",")[3]
        return Attack(energyCost, damage, name, text)
    }

    @TypeConverter
    fun fromAttackListToString(attack: List<Attack>) : String {
        val stringList = mutableListOf<String>()
        for (i in 0 until attack.size) {
            stringList[i] = attack[i].toString()
        }
        return fromListToString(stringList)
    }

    @TypeConverter
    fun fromStringToAttackList(string:String) : List<Attack> {
        val stringList = string.split(";")
        return stringList.map { fromStringToAttack(it) }
    }

    @TypeConverter
    fun fromIntListToString(nationalDexNumbers: List<Int>) : String {
        val string = nationalDexNumbers.joinToString(",")
        return string
    }
    @TypeConverter
    fun fromStringToIntList(string:String) : List<Int> {
        val stringList = string.split(",")
        val intList = stringList.map { it.toInt() }
        return intList
    }

    @TypeConverter
    fun fromResistanceToString(resistance: Resistance) : String {
        return "${resistance.type},${resistance.value}"
    }

    @TypeConverter
    fun fromStringToResistance(string:String) : Resistance {
        val type=string.split(",")[0]
        val value=string.split(",")[1]
        return Resistance(type, value)
    }

    @TypeConverter
    fun fromResistanceListToString(resistance: List<Resistance>) : String {
        val stringList = mutableListOf<String>()
        for (i in 0 until resistance.size) {
            stringList[i] = resistance[i].toString()
        }
        return fromListToString(stringList)
    }

    @TypeConverter
    fun fromStringToResistanceList(string:String) : List<Resistance> {
        val stringList = string.split(";")
        return stringList.map { fromStringToResistance(it) }
    }

    @TypeConverter
    fun fromWeaknesseToString(weaknesse: Weaknesse) : String {
        return "${weaknesse.type},${weaknesse.value}"
    }

    @TypeConverter
    fun fromStringToWeaknesse(string:String) : Weaknesse {
        val type=string.split(",")[0]
        val value=string.split(",")[1]
        return Weaknesse(type, value)
    }

    @TypeConverter
    fun fromWeaknesseListToString(weaknesse: List<Weaknesse>) : String {
        val stringList = mutableListOf<String>()
        for (i in 0 until weaknesse.size) {
            stringList[i] = weaknesse[i].toString()
        }
        return fromListToString(stringList)
    }

    @TypeConverter
    fun fromStringToWeaknesseList(string:String) : List<Weaknesse> {
        val stringList = string.split(";")
        return stringList.map { fromStringToWeaknesse(it) }
    }

    @TypeConverter
    fun fromImagesToString(images: Images) : String {
        return "${images.large},${images.small}"
    }

    @TypeConverter
    fun fromStringToImages(string:String) : Images {
        val large=string.split(",")[0]
        val small=string.split(",")[1]
        return Images(large, small)
    }


}