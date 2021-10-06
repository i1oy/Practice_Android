package com.example.babymovementtracker.data.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.babymovementtracker.data.Record
import java.time.LocalDateTime

@Dao
interface RecordDao {
    @Query("SELECT * FROM RECORDS ORDER BY TAPPED DESC")
    fun getAll(): List<Record>

    @Query("SELECT * FROM RECORDS WHERE TAPPED >= :startTime and TAPPED <= :endTime")
    fun loadAllByDateTime(startTime: LocalDateTime, endTime: LocalDateTime): List<Record>

    @Insert
    fun insertAll(vararg records: Record)

    @Delete
    fun delete(record: Record)
}