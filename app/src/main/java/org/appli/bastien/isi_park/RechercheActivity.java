package org.appli.bastien.isi_park;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;

public class RechercheActivity extends BaseActivity {

    @BindView(R.id.recherche_adresse)
    EditText adresse;
    @BindView(R.id.recherche_nom)
    EditText nom;
    @BindView(R.id.recherche_cb)
    CheckBox cb;
    @BindView(R.id.recherche_espece)
    CheckBox espece;
    @BindView(R.id.recherche_dispo)
    SeekBar dispo;
    @BindView(R.id.recherche_label_dispo)
    TextView label_dispo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recherche);
        dispo.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                label_dispo.setText("Nombre de places disponibles (" + progress + ")");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    @OnClick(R.id.button_recherche_valider)
    public void button_valider_recherche() {
        Intent intent = new Intent(this, RechercheResultatsActivity.class);
        intent.putExtra("adresse", adresse.getText().toString());
        intent.putExtra("nom", nom.getText().toString());
        intent.putExtra("cb", cb.isChecked());
        intent.putExtra("espece", espece.isChecked());
        intent.putExtra("dispo", dispo.getProgress());
        startActivity(intent);
    }
}
