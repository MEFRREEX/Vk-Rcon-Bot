package com.mefrreex.vkbot.rcon

import com.mefrreex.vkbot.rcon.exception.AuthenticationException
import com.mefrreex.vkbot.rcon.exception.ConnectionException
import com.mefrreex.vkbot.rcon.packet.RconPacket
import com.mefrreex.vkbot.rcon.packet.RconPacketType
import java.io.IOException
import java.net.InetSocketAddress
import java.nio.charset.Charset

import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;

class RconClient(
    host: String,
    port: Int,
    private val charset: Charset = StandardCharsets.UTF_8
) {
    private val address = InetSocketAddress(host, port)
    private var socketChannel: SocketChannel? = null
    private var password: String? = null

    /**
     * Connect to the server using the provided password
     * @param password Password for authentication
     */
    fun connect(password: String?) {
        this.password = password
        this.connect()
    }

    /**
     * Connect to the server using the stored password
     * @throws AuthenticationException If authentication fails
     */
    fun connect() {
        try {
            socketChannel = SocketChannel.open().apply {
                socket().setSoTimeout(1500)
                connect(address)
            }
        } catch (e: IOException) {
            throw AuthenticationException("Authorization error", e)
        }

        val packet = RconPacket(RconPacketType.AUTH_REQUEST)
        packet.payload = password!!.toByteArray()

        val response = this.sendPacket(packet) ?: throw ConnectionException("Server is offline")

        if (response.requestId == -1) {
            throw AuthenticationException("Wrong password")
        }
    }

    /**
     * Send a command to the server
     * @param command Command to send
     * @return Server response
     */
    fun sendCommand(command: String): String {
        val packet = RconPacket(RconPacketType.COMMAND_EXECUTE)
        packet.payload = command.toByteArray(charset)

        val response = sendPacket(packet)
        return String(response!!.payload, charset)
    }

    /**
     * Send the packet to the server
     * @param packet RconPacket to send
     * @param socketChannel SocketChannel to use for sending
     * @return A packet sent by the server in response
     */
    private fun sendPacket(packet: RconPacket, socketChannel: SocketChannel?): RconPacket? {
        return try {
            packet.send(socketChannel!!)
        } catch (e: IOException) {
            null
        }
    }

    /**
     * Send the packet to the socketChannel
     * @param packet RconPacket to send
     * @return A packet sent by the server in response
     */
    fun sendPacket(packet: RconPacket): RconPacket? {
        return this.sendPacket(packet, socketChannel)
    }
}