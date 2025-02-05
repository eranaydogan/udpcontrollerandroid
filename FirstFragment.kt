package com.example.test

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.example.test.databinding.FragmentFirstBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.yield
import java.net.DatagramPacket
import java.net.DatagramSocket
import java.net.InetAddress
import java.nio.ByteBuffer
import java.nio.ByteOrder

class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null
    private val binding get() = _binding!!

    // Değişkenleri sınıf seviyesinde tanımla (her butona basıldığında artmaya devam edecek)
    private var x = 0.0f
    private var y = 0.0f
    private var z = 0.0f
    private var p = 0.0f
    private var yw = 0.0f

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root
    }

    private val jobs = mutableMapOf<String, Job?>()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.btn.setOnTouchListener { v, event ->

            Log.i("EVENT",""+event.action)

            when(event.action){

                MotionEvent.ACTION_DOWN ->{

                    senddatawithloop("btn")

                }

                MotionEvent.ACTION_UP -> {
                    jobs["btn"]?.cancel()
                    jobs.remove("btn")
                    v.performClick()
                }
            }


            true
          }

        binding.btn2.setOnTouchListener { v, event ->

            Log.i("EVENT",""+event.action)
            when(event.action){
                MotionEvent.ACTION_DOWN -> {

                    senddatawithloop("btn2")
                }

                MotionEvent.ACTION_UP -> {
                    jobs["btn2"]?.cancel()
                    jobs.remove("btn2")
                    v.performClick()
                }
            }
            true
        }

        binding.btn3.setOnTouchListener { v, event ->

            Log.i("EVENT",""+event.action)

            when(event.action) {


                MotionEvent.ACTION_DOWN -> {

                    senddatawithloop("btn3")
                }

                MotionEvent.ACTION_UP -> {
                        jobs["btn3"]?.cancel()
                        jobs.remove("btn3")
                        v.performClick()
                    }
            }
            true
        }

        binding.btn4.setOnTouchListener { v, event ->

            Log.i("EVENT",""+event.action)

            when(event.action) {


                MotionEvent.ACTION_DOWN -> {

                    senddatawithloop("btn4")
                }

                MotionEvent.ACTION_UP -> {
                        jobs["btn4"]?.cancel()
                        jobs.remove("btn4")
                        v.performClick()
                    }
            }
            true
        }

    }

    fun senddatawithloop(buttonname: String) {

        jobs[buttonname]?.cancel()
        jobs[buttonname] = lifecycleScope.launch(Dispatchers.IO) {

            while(true){
                delay(10)
                senddata(buttonname)
                yield()

            }

        }

    }

    suspend fun senddata(buttonname: String){

        when(buttonname) {

            "btn" -> {
                x += 0.10f
                y += 0.0f
                z += 0.0f
            }
            "btn2" -> {
                x -= 00.0f
                y += 0.10f

            }
            "btn3" -> {
                x += 00.0f
                y -= 0.10f

            }
            "btn4" -> {
                x -= 0.10f
                y -= 00.0f
                z -= 00.0f
            }
        }
            Log.i("btn2", "x: $x, y: $y, z: $z")

            val randomValues = floatArrayOf(
                x, y, z, 1f, 2f,
                3f, 4f, 5f, 6f, 7f,
                8f, 9f, 1f, 2f, 3f,
                4f, 5f, 6f, 7f, 8f
            )

            sendUdpData("192.168.142.173", 5555, *randomValues)


    }

    fun sendUdpData(ip: String, port: Int, vararg values: Float) {
        if (values.size != 20) {
            throw IllegalArgumentException("Exactly 20 float values are required")
        }

        val buffer = ByteBuffer.allocate(20 * 4) // 20 floats, each 4 bytes
        buffer.order(ByteOrder.LITTLE_ENDIAN)
        values.forEach { buffer.putFloat(it) }

        val address = InetAddress.getByName(ip)
        val socket = DatagramSocket()
        val packet = DatagramPacket(buffer.array(), buffer.capacity(), address, port)

        socket.send(packet)
        socket.close()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

