package com.example.uts_pam.database


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "users")
data class User(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int = 0,

    @ColumnInfo(name = "nama")
    var nama: String,

    @ColumnInfo(name = "github")
    var github: String,

    @ColumnInfo(name = "nik")
    var nik: String,

    @ColumnInfo(name = "email")
    var email: String,

    @ColumnInfo(name = "password")
    var password: String
)
