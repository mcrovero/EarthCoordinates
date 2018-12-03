package app.mattiacrovero.earthcoordinates

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), SensorEventListener {

    companion object {
        // change this constants to change performance
        const val ROTATION_UPDATE_FREQUENCY = SensorManager.SENSOR_DELAY_NORMAL
        const val ROTATION_MAX_UPDATE_FREQUENCY = SensorManager.SENSOR_DELAY_NORMAL

        const val ACCELEROMETER_UPDATE_FREQUENCY = SensorManager.SENSOR_DELAY_UI
        const val ACCELEROMETER_MAX_UPDATE_FREQUENCY = SensorManager.SENSOR_DELAY_UI
    }

    private lateinit var sensorManager: SensorManager

    private var rotationMatrix = FloatArray(9)

    private var earthMode = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager

        btn_mode.setOnClickListener {
            earthMode = !earthMode
            updateUI()
        }

        // Initialize UI
        updateUI()
    }

    private fun updateUI() {
        if(earthMode){
            btn_mode.text = "Change to device relative coordinates"
            img_device.visibility = View.GONE
            img_earth.visibility = View.VISIBLE
        } else {
            btn_mode.text = "Change to earth coordinates"
            img_device.visibility = View.VISIBLE
            img_earth.visibility = View.GONE
        }
    }

    override fun onResume() {
        super.onResume()
        sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION)?.also { accelerometer ->
            sensorManager.registerListener(
                this,
                accelerometer,
                ACCELEROMETER_UPDATE_FREQUENCY,
                ACCELEROMETER_MAX_UPDATE_FREQUENCY
            )
        }
        sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR)?.also { gyroscope ->
            sensorManager.registerListener(
                this,
                gyroscope,
                ROTATION_UPDATE_FREQUENCY,
                ROTATION_MAX_UPDATE_FREQUENCY
            )
        }
    }

    override fun onPause() {
        super.onPause()
        // Remove sensor listener
        sensorManager.unregisterListener(this)
    }

    override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {
        // Nothing to do
    }

    // Called every time a new sensor value is available
    override fun onSensorChanged(event: SensorEvent) {
        when(event.sensor.type) {
            Sensor.TYPE_LINEAR_ACCELERATION -> newAcceleration(event.values)
            Sensor.TYPE_ROTATION_VECTOR -> newRotationVector(event.values)
        }
    }

    // Manage acceleration value
    private fun newAcceleration(acceleration : FloatArray) {
        var vector = acceleration
        Log.d("acceleration", "${vector[0]} ${vector[1]} ${vector[2]}")
        if(earthMode) {
            vector = LinearAlgebraHelper.rotateVector3(vector,rotationMatrix)
        }
        val direction = Vector3.fromFloatArray(vector)
        canvas_view.updateDirection(direction)

    }

    // Manage gyroscope value
    private fun newRotationVector(quaternion: FloatArray) {
        SensorManager.getRotationMatrixFromVector(rotationMatrix, quaternion)
    }
}
