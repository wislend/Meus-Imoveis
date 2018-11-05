package com.example.deyvi.gerenciamentoderepublica.activitys;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.deyvi.gerenciamentoderepublica.R;
import com.example.deyvi.gerenciamentoderepublica.Util.validacion.Calendar.DatePickerFragment;
import com.example.deyvi.gerenciamentoderepublica.Util.validacion.MaskEditUtil;
import com.example.deyvi.gerenciamentoderepublica.activitys.base.BaseActivity;
import com.example.deyvi.gerenciamentoderepublica.application.DbLogs;
import com.example.deyvi.gerenciamentoderepublica.bll.Moradores;
import com.example.deyvi.gerenciamentoderepublica.bll.Moveis;
import com.example.deyvi.gerenciamentoderepublica.bll.Quartos;
import com.example.deyvi.gerenciamentoderepublica.constantsApp.SqliteConstantes;
import com.example.deyvi.gerenciamentoderepublica.entitys.Morador;
import com.example.deyvi.gerenciamentoderepublica.entitys.Movel;
import com.example.deyvi.gerenciamentoderepublica.entitys.Quarto;
import com.google.android.flexbox.FlexboxLayout;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


@SuppressLint("Registered")
@EActivity(R.layout.activity_cadastro_quarto)
public class CadastroQuartoActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener {


    @ViewById
    EditText dataEntrada;

    @ViewById
    EditText dataSaida;

    @ViewById
    EditText edtTelefone;

    @ViewById
    EditText edtWhats;

    @ViewById
    EditText edtMorador;

    @Extra
    Long imovelId;

    @ViewById
    EditText edtEmail;

    @ViewById
    EditText edtPreco;

    @ViewById
    TextView addCheckBox;

    @ViewById
    EditText edtIdentificacaoQuarto;

    @ViewById
    RadioGroup radioOcupadoVago;

    @ViewById
    LinearLayout conteanerCadastroMorador;

    @ViewById
    EditText edtNumero;

    @ViewById
    EditText edtNomeChecked;

    @ViewById
    ImageButton imgAddChecked;

    @ViewById
    android.support.v7.widget.Toolbar toolbar;

    @ViewById
    FlexboxLayout viewContainerCheckBox;

    private Long idQuarto;

    private boolean vago = true;

    @SuppressLint("UseSparseArrays")
    HashMap<String, Boolean> checkBoxGroup = new HashMap<>();
    //id dinamico para os checkeds
    int chId = 1000;

    List<Movel> listMoveis = new ArrayList<>();

    @AfterViews
    public void afterViews() {
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_24dp);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }


        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        edtTelefone.addTextChangedListener(MaskEditUtil.insert(edtTelefone, MaskEditUtil.MaskType.TELEFONE));
        edtWhats.addTextChangedListener(MaskEditUtil.insert(edtWhats,MaskEditUtil.MaskType.TELEFONE));
        radioOcupadoVago.setOnCheckedChangeListener(this);

    }


    void configureCheckedBox() {
        CheckBox[] ch = new CheckBox[listMoveis.size()];
        for (int i = 0; i < listMoveis.size(); i++) {
            ch[i] = new CheckBox(this);
            ch[i].setId(chId++);
            ch[i].setText(listMoveis.get(i).getNome());
            ch[i].setChecked(listMoveis.get(i).isChecked());
            viewContainerCheckBox.addView(ch[i]);

            if (checkBoxGroup.get(ch[i].getText().toString()) != null) {
                checkBoxGroup.remove(ch[i].getText().toString());
                checkBoxGroup.put(ch[i].getText().toString(), ch[i].isChecked());
            } else {
                checkBoxGroup.put(ch[i].getText().toString(), ch[i].isChecked());
            }

        }


        for (int i = 0; i < listMoveis.size(); i++) {
            ch[i].setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (checkBoxGroup.get(buttonView.getText().toString()) != null) {
                        checkBoxGroup.remove(buttonView.getText().toString());
                        checkBoxGroup.put(buttonView.getText().toString(), isChecked);
                    } else {
                        checkBoxGroup.put(buttonView.getText().toString(), isChecked);
                    }

                    String stado = isChecked ? "checado" : "não checado";
                    Toast.makeText(CadastroQuartoActivity.this, stado + " " + buttonView.getText(), Toast.LENGTH_SHORT).show();
                }
            });
        }

    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }

    @Override
    public void onBackPressed() {
        CadastroQuartoActivity.super.onBackPressed();
    }

    @Click
    void imgAddChecked() {
        String nomeChecked = edtNomeChecked.getText().toString();

        if (nomeChecked.isEmpty()) {
            return;
        }

        Movel movel = new Movel();
        movel.setNome(edtNomeChecked.getText().toString());

        for (Movel movelList : listMoveis) {
            if (movelList.getNome().equals(movel.getNome())) {
                Toast.makeText(this, "Você já adicionou este movél", Toast.LENGTH_SHORT).show();
                return;
            }
        }

        listMoveis.clear();
        movel.setCheckad(true);
        listMoveis.add(movel);
        edtNomeChecked.setText("");
        configureCheckedBox();

    }


    @Background
    public void salvarQuarto() {
        Quartos quartos = new Quartos();
        Quarto quarto = new Quarto();
        try {
            quarto.setNome(edtIdentificacaoQuarto.getText().toString());
            quarto.setPreco(Double.parseDouble(edtPreco.getText().toString()));
            quarto.setImovelId(imovelId);
            quarto.setQuantidadeCamas(1);
            quarto.setNumero(Integer.parseInt(String.valueOf(edtNumero.getText().toString().isEmpty() ? 0 : edtNumero.getText().toString())));
            quarto.setDescricao(edtIdentificacaoQuarto.getText().toString());
        } catch (Exception e) {
            Toast.makeText(this, "O erro ocorreu: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }


        try {
            if (quartos.quartoExiste(quarto.getNumero())) {
                responseSalvarQuarto("O quarto já foi cadastrado.", null);
            } else {

                if (!vago) {
                    quarto.setStatus(1);
                    salvarMorador(quarto);
                } else {
                    quarto.setStatus(0);
                    idQuarto = quartos.salvarQuarto(quarto);
                    responseSalvarQuarto(SqliteConstantes.QUARTO_SALVO_SUCESSO, null);
                    salvarMovel();
                    VisaoGeral_.intent(this).start();
                }

            }
        } catch (Exception e) {
            responseSalvarQuarto("Erro ao verificar existencia do quarto.", e);
        }


    }

    @UiThread
    void responseSalvarQuarto(String callback, Exception e) {
        if (e != null) {
            DbLogs.Log(SqliteConstantes.ERRO_SALVAR_QUARTO, e, "");
            return;
        }
        Toast.makeText(this, callback, Toast.LENGTH_SHORT).show();
    }


    @Background
    public void salvarMorador(Quarto quarto) {
        Moradores moradores = new Moradores();
        Quartos quartos = new Quartos();
        Morador morador = new Morador();
        try {
            morador.setNome(edtMorador.getText().toString());
            morador.setDataEntrada(dataEntrada.getText().toString());
            morador.setEmail(edtEmail.getText().toString());
            morador.setTelefone(MaskEditUtil.unmask(edtTelefone.getText().toString()));
            morador.setWhats(MaskEditUtil.unmask(edtWhats.getText().toString()));
            morador.setDataSaida(dataSaida.getText().toString());
        } catch (Exception e) {
            Toast.makeText(this, "um erro ocorreu: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }


        try {
            if (moradores.moradorExiste(morador.getTelefone())) {
                responseMoradorUiThread(SqliteConstantes.MORADOR_JA_CADASTRADO, null);
            } else {
                idQuarto = quartos.salvarQuarto(quarto);
                morador.setQuartoId(idQuarto);
                responseSalvarQuarto(SqliteConstantes.QUARTO_SALVO_SUCESSO, null);
                responseMoradorUiThread("Morador salvo com sucesso", null);
                moradores.salvarMorador(morador);
                salvarMovel();
                VisaoGeral_.intent(this).start();
            }
        } catch (Exception e) {
            responseMoradorUiThread("Erro: ", e);

        }

    }

    @UiThread
    void responseMoradorUiThread(String callback, Exception e) {
        if (e != null) {
            DbLogs.Log(SqliteConstantes.ERRO_VERIFICAR_EXISTENCIA_MORADOR, e, "");

        }
        dismissProgressDialog();
        Toast.makeText(this, callback, Toast.LENGTH_SHORT).show();
    }

    @Background
    void salvarMovel() {
        Moveis moveis = new Moveis();
        for (String checkedBox : checkBoxGroup.keySet()) {
            boolean value = checkBoxGroup.get(checkedBox);
            Movel movel = new Movel();
            movel.setNome(checkedBox);
            movel.setCheckad(value);
            if (idQuarto != null) {
                movel.setQuartoId(idQuarto);
            }

            try {
                if (!moveis.movelExiste(movel.getNome())) {
                    moveis.salvarMovel(movel);
                    responseMovelUiThread("Movel salvo", null);
                } else {
                    moveis.atualizarMovel(movel);
                    responseMovelUiThread("Movel atualizado", null);
                }
            } catch (Exception e) {
                responseMovelUiThread("Erro ao verificar existencia do movel", e);
            }

        }
    }

    @UiThread
    void responseMovelUiThread(String callback, Exception e) {
        dismissProgressDialog();
        if (e != null) {
            DbLogs.Log(SqliteConstantes.ERRO_VERIFICAR_EXISTENCIA_MOVEL, e, "");
            return;
        }
        Toast.makeText(this, callback, Toast.LENGTH_SHORT).show();

    }

    @Background
    void validarMorador() {
        Moradores moradores = new Moradores();
        try {
            boolean existe = moradores.moradorExiste(edtWhats.getText().toString());
            validarMoradorRespostaUiThread(null, existe);
        } catch (Exception e) {
            validarMoradorRespostaUiThread(e, false);
        }

    }

    @UiThread
    void validarMoradorRespostaUiThread(Exception e, boolean existe) {
        if (e != null) {
            DbLogs.Log(SqliteConstantes.ERRO_VERIFICAR_EXISTENCIA_MORADOR, e, "");
            return;
        }

        if (!existe) {
            salvarQuarto();
        } else {
            Toast.makeText(this, "Morador já cadastrado em outro quarto.", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        if (checkedId == R.id.rbOcupado) {
            conteanerCadastroMorador.setVisibility(View.VISIBLE);
            vago = false;
        } else if (checkedId == R.id.rbVago) {
            vago = true;
            conteanerCadastroMorador.setVisibility(View.GONE);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.cadastrar_quarto_activity, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_salvar) {
            if (validation()) {
                if (listMoveis.size() == 0) {
                    new AlertDialog.Builder(this)
                            .setTitle("Você não adicionou nenhum móvel no quarto.")
                            .setMessage("Tem certeza que deseja continuar?")
                            .setPositiveButton("Continuar", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    if (validation()) {
                                        salvarQuarto();
                                    }

                                }
                            }).setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    }).show();
                } else {
                    if (validation()) {
                        salvarQuarto();
                    }
                }
            }
        }
        return true;
    }


    public void setEditTextDatePicker(final EditText text) {
        DatePickerFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "date picker");
        newFragment.setOnDateSelectedListener(new DatePickerFragment.OnDateSelectedListener() {
            @Override
            public void onDateSelected(DatePicker view, String formattedDate) {
                text.setText(formattedDate);
            }
        });
    }

    @Click
    void dataSaida() {
        setEditTextDatePicker(dataSaida);
    }

    @Click
    void dataEntrada() {
        setEditTextDatePicker(dataEntrada);
    }

    public boolean validation() {

        boolean valid = true;

        if (!vago) {

            if (TextUtils.isEmpty(dataEntrada.getText())) {
                dataEntrada.setError(getString(R.string.campo_obrigatorio));
                valid = false;
            }

            if (TextUtils.isEmpty(dataSaida.getText())) {
                dataSaida.setError(getString(R.string.campo_obrigatorio));
                valid = false;
            }


            if (TextUtils.isEmpty(edtTelefone.getText())) {
                edtTelefone.setError(getString(R.string.campo_obrigatorio));
                valid = false;
            }

            if (TextUtils.isEmpty(edtWhats.getText())) {
                edtWhats.setError(getString(R.string.campo_obrigatorio));
                valid = false;
            }

            if (TextUtils.isEmpty(edtIdentificacaoQuarto.getText())) {
                edtIdentificacaoQuarto.setError(getString(R.string.campo_obrigatorio));
                valid = false;
            }

            if (TextUtils.isEmpty(edtNumero.getText())) {
                edtNumero.setError(getString(R.string.campo_obrigatorio));
                valid = false;
            }
        }


        if (TextUtils.isEmpty(edtIdentificacaoQuarto.getText())) {
            edtIdentificacaoQuarto.setError(getString(R.string.campo_obrigatorio));
            valid = false;
        }

        if (TextUtils.isEmpty(edtNumero.getText())) {
            edtNumero.setError(getString(R.string.campo_obrigatorio));
            valid = false;
        }

        if (TextUtils.isEmpty(edtPreco.getText())) {
            edtPreco.setError(getString(R.string.campo_obrigatorio));
            valid = false;
        }


        return valid;
    }
}
