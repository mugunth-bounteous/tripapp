package com.mugunth.bankapp.util

import com.mugunth.bankapp.util.ConnectionUtil.getConnection
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.SQLException
import java.sql.Statement

object ExecuteQueryUtil {
    fun executeQuery(query: String?): ResultSet? {
        var statement: PreparedStatement? = null
        var rs: ResultSet? = null
        val connection = getConnection()
        return try {
            statement = connection!!.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)
            rs = statement.executeQuery()
            //connection.close();
            println(rs)
            rs
        } catch (e: SQLException) {
            println(e.message)
            null
        }
    }

    fun updateQuery(query: String?): Boolean? {
        var statement: PreparedStatement? = null
        val rs: Boolean
        val connection = getConnection()
        return try {
            statement = connection!!.prepareStatement(query)
            rs = statement.execute()
            println(rs)
            rs
        } catch (e: SQLException) {
            println(e.message)
            null
        }
    }
}
