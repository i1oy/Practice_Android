package com.example.babymovementtracker.data

import androidx.compose.runtime.Immutable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity(
    tableName = "records",
    indices = [
        Index("uid", unique = true)
    ]
)

@Immutable
data class Record(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "uid") val uid: Long = 0,
    @ColumnInfo(name = "tapped") val tapped: LocalDateTime
)
