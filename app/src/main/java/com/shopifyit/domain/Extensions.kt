package com.shopifyit.domain

import com.shopifyit.data.model.Repository
import java.text.SimpleDateFormat
import java.util.*

fun Date.toStringDateFormat(): String = SimpleDateFormat(SHORT_MONTH_WITH_DAY_TIME, Locale.ENGLISH).format(this.time)
fun String.parseIsoDate(): Date = SimpleDateFormat(RFC_822_DATE_TIME_PATTERN, Locale.getDefault()).parse(this)
fun Boolean.toStringYesOrNo(): String = if (this) "Y" else "N"

private const val RFC_822_DATE_TIME_PATTERN = "yyyy-MM-dd'T'HH:mm:ss'Z'"
private const val SHORT_MONTH_WITH_DAY_TIME = "MMMM d yyyy - HH:mm:ss"

fun List<Repository>.getSortedRepositories(): List<Repository> = this.sortedByDescending { it.created_at }