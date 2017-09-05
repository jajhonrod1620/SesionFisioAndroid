package com.fisio.jajho.contadorfisio;

import android.content.DialogInterface;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Contador extends AppCompatActivity {
    TextView tiempo;
    CountDownTimer cuenta_atras;
    Button pausa, cambiar;
    Integer conteo = 15000;
    MediaPlayer alarma;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        alarma = MediaPlayer.create(this, R.raw.sound);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contador);

        tiempo = (TextView) findViewById(R.id.tiempo);
        pausa = (Button) findViewById(R.id.pausar);
        cambiar = (Button) findViewById(R.id.cambiar);
        iniciar();

        pausa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cuenta_atras.cancel();
                cambiar.setEnabled(true);
                pausa.setEnabled(false);
            }
        });
        cambiar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cuenta_atras.cancel();
                getConteoNuevo();
            }
        });
    }

    private void iniciar() {
            tiempo.setTextColor(Color.BLUE);
            cuenta_atras = new CountDownTimer(conteo, 1) {
                public void onTick(long mili) {
                    long v = mili / 1000;
                    tiempo.setText("" + v);
                    if (v == 0) {
                        tiempo.setTextColor(Color.GRAY);
                        alarma.start();
                    }
                }
                @Override
                public void onFinish() {
                    iniciar();
                }
            }.start();
    }

    public void getConteoNuevo(){

        LayoutInflater li = LayoutInflater.from(this);
        View getEmpIdView = li.inflate(R.layout.dialog_nuevo_tiempo, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        // set dialog_get_emp_id.xml to alertdialog builder
        alertDialogBuilder.setView(getEmpIdView);

        final EditText editConteo = (EditText) getEmpIdView.findViewById(R.id.editTextDialogConteo);
        // set dialog message
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("OK",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        conteo = 0;
                        conteo = Integer.parseInt(editConteo.getText().toString());
                        conteo = conteo*1000;
                        iniciar();
                    }
                }).create()
                .show();
    }
}
