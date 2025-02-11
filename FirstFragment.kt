package com.example.test

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.os.Build
import android.os.Vibrator
import android.os.VibrationEffect
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.annotation.OptIn
import androidx.lifecycle.lifecycleScope
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.rtsp.RtspMediaSource
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
import java.lang.Math.toRadians
import kotlin.math.cos
import kotlin.math.sin
import org.videolan.libvlc.LibVLC
import org.videolan.libvlc.Media
import org.videolan.libvlc.MediaPlayer
import org.videolan.libvlc.interfaces.IVLCVout


class FirstFragment : Fragment() {

    private lateinit var libVLC: LibVLC
    private lateinit var mediaPlayer: MediaPlayer
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


    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)






        binding.btn.setOnTouchListener { v, event ->

            Log.i("EVENT",""+event.action)
            val vibrator = v.context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator

            when(event.action){

                MotionEvent.ACTION_DOWN ->{


                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        vibrator.vibrate(VibrationEffect.createOneShot(25, VibrationEffect.DEFAULT_AMPLITUDE))
                    } else {
                        vibrator.vibrate(25)
                    }

                    senddatawithloop("btn")

                    v.setBackgroundResource(R.drawable.pressedarrow)

                    val scaleDownX = ObjectAnimator.ofFloat(v, "scaleX", 0.8f)
                    val scaleDownY = ObjectAnimator.ofFloat(v, "scaleY", 0.8f)
                    scaleDownX.duration = 100
                    scaleDownY.duration = 100

                    AnimatorSet().apply {
                        playTogether(scaleDownX, scaleDownY)
                        start()
                    }



                }

                MotionEvent.ACTION_UP -> {

                    v.setBackgroundResource(R.drawable.arrowone)

                    val scaleUpX = ObjectAnimator.ofFloat(v, "scaleX", 1f)
                    val scaleUpY = ObjectAnimator.ofFloat(v, "scaleY", 1f)
                    scaleUpX.duration = 100
                    scaleUpY.duration = 100

                    AnimatorSet().apply {
                        playTogether(scaleUpX, scaleUpY)
                        start()
                    }

                    jobs["btn"]?.cancel()
                    jobs.remove("btn")
                    v.performClick()
                }
            }


            true
          }

        binding.btn2.setOnTouchListener { v, event ->
            val vibrator = v.context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator

            Log.i("EVENT",""+event.action)
            when(event.action){
                MotionEvent.ACTION_DOWN -> {

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        vibrator.vibrate(VibrationEffect.createOneShot(25, VibrationEffect.DEFAULT_AMPLITUDE))
                    } else {
                        vibrator.vibrate(25)
                    }

                    v.setBackgroundResource(R.drawable.pressedarrow)
                    val scaleDownX = ObjectAnimator.ofFloat(v, "scaleX", 0.8f)
                    val scaleDownY = ObjectAnimator.ofFloat(v, "scaleY", 0.8f)
                    scaleDownX.duration = 100
                    scaleDownY.duration = 100

                    AnimatorSet().apply {
                        playTogether(scaleDownX, scaleDownY)
                        start()
                    }


                    senddatawithloop("btn2")
                }

                MotionEvent.ACTION_UP -> {
                    v.setBackgroundResource(R.drawable.arrowone)
                    val scaleUpX = ObjectAnimator.ofFloat(v, "scaleX", 1f)
                    val scaleUpY = ObjectAnimator.ofFloat(v, "scaleY", 1f)
                    scaleUpX.duration = 100
                    scaleUpY.duration = 100

                    AnimatorSet().apply {
                        playTogether(scaleUpX, scaleUpY)
                        start()
                    }
                    jobs["btn2"]?.cancel()
                    jobs.remove("btn2")
                    v.performClick()
                }
            }
            true
        }

        binding.btn3.setOnTouchListener { v, event ->
            val vibrator = v.context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator

            Log.i("EVENT",""+event.action)

            when(event.action) {

                MotionEvent.ACTION_DOWN -> {

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        vibrator.vibrate(VibrationEffect.createOneShot(25, VibrationEffect.DEFAULT_AMPLITUDE))
                    } else {
                        vibrator.vibrate(25)
                    }

                    v.setBackgroundResource(R.drawable.pressedarrow)
                    val scaleDownX = ObjectAnimator.ofFloat(v, "scaleX", 0.8f)
                    val scaleDownY = ObjectAnimator.ofFloat(v, "scaleY", 0.8f)
                    scaleDownX.duration = 100
                    scaleDownY.duration = 100

                    AnimatorSet().apply {
                        playTogether(scaleDownX, scaleDownY)
                        start()
                    }


                    senddatawithloop("btn3")
                }

                MotionEvent.ACTION_UP -> {

                    v.setBackgroundResource(R.drawable.arrowone)
                    val scaleUpX = ObjectAnimator.ofFloat(v, "scaleX", 1f)
                    val scaleUpY = ObjectAnimator.ofFloat(v, "scaleY", 1f)
                    scaleUpX.duration = 100
                    scaleUpY.duration = 100

                    AnimatorSet().apply {
                        playTogether(scaleUpX, scaleUpY)
                        start()
                    }
                        jobs["btn3"]?.cancel()
                        jobs.remove("btn3")
                        v.performClick()
                    }
            }
            true
        }

        binding.btn4.setOnTouchListener { v, event ->
            val vibrator = v.context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator

            Log.i("EVENT",""+event.action)

            when(event.action) {


                MotionEvent.ACTION_DOWN -> {

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        vibrator.vibrate(VibrationEffect.createOneShot(25, VibrationEffect.DEFAULT_AMPLITUDE))
                    } else {
                        vibrator.vibrate(25)
                    }

                    v.setBackgroundResource(R.drawable.pressedarrow)
                    val scaleDownX = ObjectAnimator.ofFloat(v, "scaleX", 0.8f)
                    val scaleDownY = ObjectAnimator.ofFloat(v, "scaleY", 0.8f)
                    scaleDownX.duration = 100
                    scaleDownY.duration = 100

                    AnimatorSet().apply {
                        playTogether(scaleDownX, scaleDownY)
                        start()
                    }


                    senddatawithloop("btn4")
                }

                MotionEvent.ACTION_UP -> {

                    v.setBackgroundResource(R.drawable.arrowone)
                    val scaleUpX = ObjectAnimator.ofFloat(v, "scaleX", 1f)
                    val scaleUpY = ObjectAnimator.ofFloat(v, "scaleY", 1f)
                    scaleUpX.duration = 100
                    scaleUpY.duration = 100

                    AnimatorSet().apply {
                        playTogether(scaleUpX, scaleUpY)
                        start()
                    }
                        jobs["btn4"]?.cancel()
                        jobs.remove("btn4")
                        v.performClick()
                    }
            }
            true
        }
        binding.btn5.setOnTouchListener { v, event ->
            val vibrator = v.context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator

            Log.i("EVENT",""+event.action)

            when(event.action) {


                MotionEvent.ACTION_DOWN -> {

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        vibrator.vibrate(VibrationEffect.createOneShot(25, VibrationEffect.DEFAULT_AMPLITUDE))
                    } else {
                        vibrator.vibrate(25)
                    }

                    v.setBackgroundResource(R.drawable.pressedarrow)
                    val scaleDownX = ObjectAnimator.ofFloat(v, "scaleX", 0.8f)
                    val scaleDownY = ObjectAnimator.ofFloat(v, "scaleY", 0.8f)
                    scaleDownX.duration = 100
                    scaleDownY.duration = 100

                    AnimatorSet().apply {
                        playTogether(scaleDownX, scaleDownY)
                        start()
                    }


                    senddatawithloop("btn5")
                }

                MotionEvent.ACTION_UP -> {

                    v.setBackgroundResource(R.drawable.arrowone)
                    val scaleUpX = ObjectAnimator.ofFloat(v, "scaleX", 1f)
                    val scaleUpY = ObjectAnimator.ofFloat(v, "scaleY", 1f)
                    scaleUpX.duration = 100
                    scaleUpY.duration = 100

                    AnimatorSet().apply {
                        playTogether(scaleUpX, scaleUpY)
                        start()
                    }
                    jobs["btn5"]?.cancel()
                    jobs.remove("btn5")
                    v.performClick()
                }
            }
            true
        }
        binding.btn6.setOnTouchListener { v, event ->
            val vibrator = v.context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator

            Log.i("EVENT",""+event.action)

            when(event.action) {


                MotionEvent.ACTION_DOWN -> {

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        vibrator.vibrate(VibrationEffect.createOneShot(25, VibrationEffect.DEFAULT_AMPLITUDE))
                    } else {
                        vibrator.vibrate(25)
                    }

                    v.setBackgroundResource(R.drawable.pressedarrow)
                    val scaleDownX = ObjectAnimator.ofFloat(v, "scaleX", 0.8f)
                    val scaleDownY = ObjectAnimator.ofFloat(v, "scaleY", 0.8f)
                    scaleDownX.duration = 100
                    scaleDownY.duration = 100

                    AnimatorSet().apply {
                        playTogether(scaleDownX, scaleDownY)
                        start()
                    }


                    senddatawithloop("btn6")
                }

                MotionEvent.ACTION_UP -> {

                    v.setBackgroundResource(R.drawable.arrowone)
                    val scaleUpX = ObjectAnimator.ofFloat(v, "scaleX", 1f)
                    val scaleUpY = ObjectAnimator.ofFloat(v, "scaleY", 1f)
                    scaleUpX.duration = 100
                    scaleUpY.duration = 100

                    AnimatorSet().apply {
                        playTogether(scaleUpX, scaleUpY)
                        start()
                    }
                    jobs["btn6"]?.cancel()
                    jobs.remove("btn6")
                    v.performClick()
                }
            }
            true
        }
        binding.btn7.setOnTouchListener { v, event ->
            val vibrator = v.context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator

            Log.i("EVENT",""+event.action)

            when(event.action) {


                MotionEvent.ACTION_DOWN -> {


                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        vibrator.vibrate(VibrationEffect.createOneShot(25, VibrationEffect.DEFAULT_AMPLITUDE))
                    } else {
                        vibrator.vibrate(25)
                    }

                    v.setBackgroundResource(R.drawable.pressedarrow)
                    val scaleDownX = ObjectAnimator.ofFloat(v, "scaleX", 0.8f)
                    val scaleDownY = ObjectAnimator.ofFloat(v, "scaleY", 0.8f)
                    scaleDownX.duration = 100
                    scaleDownY.duration = 100

                    AnimatorSet().apply {
                        playTogether(scaleDownX, scaleDownY)
                        start()
                    }


                    senddatawithloop("btn7")
                }

                MotionEvent.ACTION_UP -> {

                    v.setBackgroundResource(R.drawable.arrowone)
                    val scaleUpX = ObjectAnimator.ofFloat(v, "scaleX", 1f)
                    val scaleUpY = ObjectAnimator.ofFloat(v, "scaleY", 1f)
                    scaleUpX.duration = 100
                    scaleUpY.duration = 100

                    AnimatorSet().apply {
                        playTogether(scaleUpX, scaleUpY)
                        start()
                    }
                    jobs["btn7"]?.cancel()
                    jobs.remove("btn7")
                    v.performClick()
                }
            }
            true
        }
        binding.btn8.setOnTouchListener { v, event ->
            val vibrator = v.context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator

            Log.i("EVENT",""+event.action)

            when(event.action) {


                MotionEvent.ACTION_DOWN -> {

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        vibrator.vibrate(VibrationEffect.createOneShot(25, VibrationEffect.DEFAULT_AMPLITUDE))
                    } else {
                        vibrator.vibrate(25)
                    }

                    v.setBackgroundResource(R.drawable.pressedarrow)
                    val scaleDownX = ObjectAnimator.ofFloat(v, "scaleX", 0.8f)
                    val scaleDownY = ObjectAnimator.ofFloat(v, "scaleY", 0.8f)
                    scaleDownX.duration = 100
                    scaleDownY.duration = 100

                    AnimatorSet().apply {
                        playTogether(scaleDownX, scaleDownY)
                        start()
                    }


                    senddatawithloop("btn8")
                }

                MotionEvent.ACTION_UP -> {

                    v.setBackgroundResource(R.drawable.arrowone)
                    val scaleUpX = ObjectAnimator.ofFloat(v, "scaleX", 1f)
                    val scaleUpY = ObjectAnimator.ofFloat(v, "scaleY", 1f)
                    scaleUpX.duration = 100
                    scaleUpY.duration = 100

                    AnimatorSet().apply {
                        playTogether(scaleUpX, scaleUpY)
                        start()
                    }
                    jobs["btn8"]?.cancel()
                    jobs.remove("btn8")
                    v.performClick()
                }
            }
            true
        }
        setupVLCPlayer()

    }

    private fun setupVLCPlayer() {
        libVLC = LibVLC(requireContext(), arrayListOf("--no-drop-late-frames", "--no-skip-frames"))
        mediaPlayer = MediaPlayer(libVLC)
        mediaPlayer.attachViews(binding.vlcVideo, null, false, false)

        // Örnek bir video URL'si ile medya oluştur
        val media = Media(libVLC, Uri.parse("udp://@:1234"))
        media.setHWDecoderEnabled(true, false)
        media.addOption(":network-caching=600")
        mediaPlayer.media = media
        media.release()

        // Oynatmayı başlat
        mediaPlayer.play()
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

        val yaw = toRadians(yw.toDouble()).toFloat()
        val pi = toRadians(p.toDouble()).toFloat()

        when(buttonname) {

            "btn5" -> {
                p += 0.1f
            }
            "btn6" -> {
                yw += 0.1f
            }
            "btn7" -> {
                yw -= 0.1f
            }
            "btn8" -> {
                p -= 0.1f
            }
            "btn" -> {
                x += 0.3f*cos(yaw)* cos(pi)
                y += 0.3f*sin(yaw) * cos(pi)
                z += 0.2f*sin(pi)
            }
            "btn2" -> {
                x -= 00.0f*sin(yaw)
                y += 0.10f*cos(yaw)
            }
            "btn3" -> {
                x += 00.0f*sin(yaw)
                y -= 0.10f*cos(yaw)
            }
            "btn4" -> {
                x -= 0.1f*cos(yaw)* cos(pi)
                y -= 0.1f*sin(yaw) * cos(pi)
                z -= 0.1f*sin(pi)
            }

        }
            Log.i("btn2", "x: $x, y: $y, z: $z")

            val randomValues = floatArrayOf(
                x, y, z, 0f, p,
                yw, 4f, 5f, 6f, 7f,
                8f, 9f, 1f, 2f, 3f,
                4f, 5f, 6f, 7f, 8f
            )

            sendUdpData("192.168.137.1", 5555, *randomValues)

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
        mediaPlayer.release()
        libVLC.release()
        super.onDestroyView()
        _binding = null
    }
}