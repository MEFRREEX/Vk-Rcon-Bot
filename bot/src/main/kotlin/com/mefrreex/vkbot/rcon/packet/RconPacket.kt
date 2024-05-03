package com.mefrreex.vkbot.rcon.packet

import java.io.DataInputStream
import java.io.IOException
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.channels.SocketChannel
import java.util.*


class RconPacket(
    val packetType: Int,
    val requestId: Int = Random().nextInt()
) {
    var payload = ByteArray(1)

    @Throws(IOException::class)
    fun send(socketChannel: SocketChannel): RconPacket {
        try {
            this.write(socketChannel)
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return read(socketChannel)
    }

    @Throws(IOException::class)
    private fun write(socketChannel: SocketChannel) {
        val length = payload.size + 14

        val buffer = ByteBuffer.allocate(length)
        buffer.order(ByteOrder.LITTLE_ENDIAN)

        buffer.putInt(length - 4)
        buffer.putInt(requestId)
        buffer.putInt(packetType)
        buffer.put(payload)

        buffer.put(0.toByte())
        buffer.put(0.toByte())

        socketChannel.write(ByteBuffer.wrap(buffer.array()))
    }

    @Throws(IOException::class)
    private fun read(socketChannel: SocketChannel): RconPacket {
        val stream = socketChannel.socket().getInputStream()

        val buffer = ByteBuffer.allocate(4 * 3)
        stream.read(buffer.array())
        buffer.order(ByteOrder.LITTLE_ENDIAN)

        val length = buffer.getInt()
        val requestId = buffer.getInt()
        val type = buffer.getInt()

        val payload = ByteArray(length - 10)
        val dataStream = DataInputStream(stream)
        dataStream.readFully(payload)
        dataStream.read(ByteArray(2))

        val packet = RconPacket(type, requestId)
        packet.payload = payload
        return packet
    }
}