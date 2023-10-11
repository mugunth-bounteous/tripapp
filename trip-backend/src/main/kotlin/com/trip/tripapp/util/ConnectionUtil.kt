package com.mugunth.bankapp.util

import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException

object ConnectionUtil {
    private var connection: Connection? = null
    fun getConnection(): Connection? {
        if (connection == null) {
            val url = "jdbc:mysql://localhost:3306/tripapp"
            val username = "root"
            val password = "cosmic"
            try {
                Class.forName("com.mysql.cj.jdbc.Driver")
            } catch (e: ClassNotFoundException) {
                throw RuntimeException(e)
            }
            try {
                connection = DriverManager.getConnection(
                    url, username, password
                )
            } catch (e: SQLException) {
                throw RuntimeException(e)
            }
        }
        return connection
    }

    fun closeConnection() {
        try {
            connection!!.close()
            connection = null
        } catch (e: SQLException) {
            throw RuntimeException(e)
        }
    }
}
