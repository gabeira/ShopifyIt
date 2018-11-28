package com.shopifyit.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Index

@Entity(tableName = "repositories", indices = [Index("id")])
data class Repository(
    @PrimaryKey @ColumnInfo(name = "id") var id: Int,
    @ColumnInfo(name = "name") var name: String,
    @ColumnInfo(name = "html_url") var html_url: String,
    @ColumnInfo(name = "created_at") var created_at: String,
    @ColumnInfo(name = "stargazers_count") var stargazers_count: Int,
    @ColumnInfo(name = "fork") var fork: Boolean
)