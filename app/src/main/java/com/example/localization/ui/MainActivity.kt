package com.example.localization.ui


import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.widget.Button
import android.widget.Toast
import com.example.localization.R
import java.io.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class MainActivity : AppCompatActivity(), LocationListener {

    val FINE_REQUEST = 12345
    val COARSE_REQUEST = 123


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //-------------------------------------------------

        val btnObter = findViewById<Button>(R.id.btn_registrar)
        btnObter.setOnClickListener {

            this.obterLocalizacaoByGps()
            //this.obterLocalizacaoByNetwork()
        }

        val btnLista = this.findViewById<Button>(R.id.btn_verRegistros)
        btnLista.setOnClickListener {
            val intent = Intent(this, LocalizationList::class.java)
            startActivity(intent)
        }

    }

    override fun onLocationChanged(p0: Location) {

    }

    fun obterLocalizacaoByNetwork() {

        val locationManager = this.getSystemService(LOCATION_SERVICE) as LocationManager
        val isNetEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
        if (isNetEnabled) {

            if(checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

                locationManager.requestLocationUpdates(
                    LocationManager.NETWORK_PROVIDER,
                    2000L,
                    0f,
                    this
                )

                var location: Location? = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)

                /**
                 * Pega a data e hora atual e converte para o formato Indicado:
                 */
                val currentTime = LocalDateTime.now()
                val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")
                val formatedCurrentTime = currentTime.format(formatter)
                val stringData = formatedCurrentTime.toString()
                //--------------------------------------------------------------
                /**
                 * Grava o arquivo na pasta /sdcard/Android/data/com.example.localization/files/Documents
                 * com o nome data e hora + extensão crd
                 */
                val writeFile = File(this.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS),
                    "$stringData.crd"
                )
                val fos = FileOutputStream(writeFile)
                val txtLocation: String = "Latitude: ${location!!.latitude}\nLongitude: ${location.longitude}"
                fos.write(txtLocation.toByteArray())
                fos.close()
                Toast.makeText(this, "Localização obtida e gravada com Sucesso!",
                    Toast.LENGTH_LONG).show()
            }
            else {
                this.requestPermissions(arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION), COARSE_REQUEST)
            }
        } else {
            Toast.makeText(this, "Erro ao obter sua localização. Habilite sua internet ou GPS.",
                Toast.LENGTH_LONG).show()
        }
    }

    private fun obterLocalizacaoByGps() {

        val locationManager = this.getSystemService(LOCATION_SERVICE) as LocationManager
        val isGpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        if (isGpsEnabled) {

            if(checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

                locationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER,
                    2000L,
                    0f,
                    this
                )

                var location: Location? = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)

                /**
                 * Pega a data e hora atual e converte para o formato Indicado:
                 */
                val currentTime = LocalDateTime.now()
                val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")
                val formatedCurrentTime = currentTime.format(formatter)
                val stringData = formatedCurrentTime.toString()

                /**
                 * Grava o arquivo na pasta /sdcard/Android/data/com.example.localization/files/Documents
                 * com o nome data e hora + extensão crd
                 */
                val writeFile = File(this.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS),
                    "$stringData.crd"
                )
                val fos = FileOutputStream(writeFile)
                val txtLocation: String = "Latitude: ${location!!.latitude}\nLongitude: ${location.longitude}"
                fos.write(txtLocation.toByteArray())
                fos.close()
                Toast.makeText(this, "Localização obtida e gravada com Sucesso!",
                    Toast.LENGTH_LONG).show()
            }
            else {
                this.requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), FINE_REQUEST)
            }
        } else{
            obterLocalizacaoByNetwork()
        }
    }
}