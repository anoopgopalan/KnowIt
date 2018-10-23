package com.app.knowit.data

/**
 * All the entities related to the [FactApi]
 */
data class Info(val title: String?, val description: String?, val imageHref: String?)
data class Fact(val title: String?, val rows: List<Info>)