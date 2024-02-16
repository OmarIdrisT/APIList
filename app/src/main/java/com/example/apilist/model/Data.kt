package com.example.apilist.model

data class Data(
    val abilities: List<Ability>,
    val attacks: List<Attack>,
    val convertedRetreatCost: Int,
    val evolvesFrom: String,
    val evolvesTo: List<String>,
    val flavorText: String,
    val hp: String,
    val id: String,
    val images: Images,
    val level: String,
    val name: String,
    val nationalPokedexNumbers: List<Int>,
    val number: String,
    var rarity: String,
    val regulationMark: String,
    val resistances: List<Resistance>,
    val retreatCost: List<String>,
    val rules: List<String>,
    val subtypes: List<String>,
    val supertype: String,
    val types: List<String>,
    val weaknesses: List<Weaknesse>
)