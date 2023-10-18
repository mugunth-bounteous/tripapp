package com.mugunth.bankapp.util

import com.mugunth.bankapp.util.ConnectionUtil
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.SQLException
import java.sql.Statement

object ExecuteQueryUtil {
    fun executeQuery(query: String?): ResultSet? {
        var statement: PreparedStatement? = null
        var rs: ResultSet? = null
        val conn:ConnectionUtil= ConnectionUtil()
        val connection = conn.getConnection()
        return try {
            statement = connection!!.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)
            rs = statement.executeQuery()
            //connection.close();
//            println("In Execute query")
//            print(rs)
            return rs;
        } catch (e: SQLException) {
            println(e.message)
            null
        } finally {
//            conn.closeConnection()
        }
    }

    fun updateQuery(query: String?): Boolean? {
        var statement: PreparedStatement? = null
        val rs: Boolean
        val conn:ConnectionUtil=ConnectionUtil()
        val connection = conn.getConnection()
        return try {
            statement = connection!!.prepareStatement(query)
            rs = statement.execute()
//            println("In update query")
//            print(rs)
            return rs;
        } catch (e: SQLException) {
            println(e.message)
            null
        }finally {
//            conn.closeConnection()
        }
    }
}
