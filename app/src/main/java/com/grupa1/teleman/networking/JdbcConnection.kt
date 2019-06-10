package com.grupa1.teleman.networking

import android.widget.Toast
import com.grupa1.teleman.MainActivity
import com.grupa1.teleman.files.ConnectionConfig
import org.jetbrains.anko.*
import org.jetbrains.anko.custom.async
import java.sql.Connection
import java.sql.DriverManager
import java.sql.ResultSet
import java.sql.SQLException

open class JdbcConnection(IP: String, PORT: String, database: String, username: String, password: String) {
    var connection: Connection? = null

    constructor(connCfg: ConnectionConfig) : this(
            connCfg.databaseAddress,
            connCfg.databasePort,
            connCfg.databaseName,
            connCfg.databaseUsername,
            connCfg.databasePassword)

    init {
        try {
            Class.forName("org.drizzle.jdbc.DrizzleDriver").newInstance()
            val connStr = "jdbc:mysql:thin://$IP:$PORT/$database"
            if (connection == null) {
                val one = doAsyncResult{ DriverManager.getConnection(connStr, username, password) }
                connection = one.get()
                val x=0
            }
        } catch (ex: SQLException) {
            System.out.println("SQLException: " + ex.message)
            System.out.println("SQLState: " + ex.sqlState)
            System.out.println("VendorError: " + ex.errorCode)
            System.out.println("")
        } catch (ex2: java.lang.Exception){
            ex2.printStackTrace()
        }

    }


    val isConnectionOpenned: Boolean
        get() {
            if (connection != null) {
                try {
                    if (connection!!.isClosed) {
                        return true
                    }
                } catch (ex: Exception) {
                    ex.printStackTrace()
                }

            }
            return false
        }

    fun executeQuery(query: String): ResultSet? {
        try {
            val task = doAsyncResult { connection!!.createStatement().executeQuery(query) }
            val result = task.get()
            if (result.isBeforeFirst)
                return result
        } catch (ex: Exception) {
            ex.printStackTrace()
        }

        return null
    }

    fun closeConnection() {
        if (connection != null) {
            try {
                connection!!.close()
            } catch (ex: Exception) {
                ex.printStackTrace()
            }

        }
    }
}
object JdbcConnectionExecution{
    fun bar(): ResultSet?{

        return null
    }
}